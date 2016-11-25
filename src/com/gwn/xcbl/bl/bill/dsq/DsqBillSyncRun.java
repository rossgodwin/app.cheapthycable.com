package com.gwn.xcbl.bl.bill.dsq;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

import com.gwn.xcbl.bl.bill.dsq.thread.DsqBillThreadHlpr;
import com.gwn.xcbl.bl.social.disqus.api.DsqApi;
import com.gwn.xcbl.bl.social.disqus.api.DsqApiKeys;
import com.gwn.xcbl.bl.social.disqus.api.DsqApiResourcePosts;
import com.gwn.xcbl.bl.social.disqus.api.DsqApiResourceThreads;
import com.gwn.xcbl.bl.social.disqus.api.response.DsqApiPost;
import com.gwn.xcbl.bl.social.disqus.api.response.DsqApiThread;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.dao.DAOUtils;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillPost;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillThread;
import com.gwn.xcbl.data.model.AppData;

public class DsqBillSyncRun implements Runnable {

	private static Log log = LogFactory.getLog(DsqBillSyncRun.class);
	
	private DsqApiResourcePosts postsResource;
	private DsqApiResourceThreads threadsResource;
	
	@Override
	public void run() {
		HibernateUtil.beginTransaction();
		
		log.info(DsqBillSyncRun.class.getSimpleName() + " Started");
		
		DsqApiKeys keys = new DsqApiKeys(AppData.getInstance().getDisqusApiKey(), AppData.getInstance().getDisqusApiSecret());
		postsResource = new DsqApiResourcePosts(new DsqApi(AppData.getInstance().getDisqusApiUrl(), keys));
		threadsResource = new DsqApiResourceThreads(new DsqApi(AppData.getInstance().getDisqusApiUrl(), keys));
		
		try {
			runBillPostsUpd(0, 2);
		} catch (Exception e) {
		} finally {
			HibernateUtil.closeSession();
			log.info(DsqBillSyncRun.class.getSimpleName() + " Finished");
		}
	}
	
	private void runBillPostsUpd(int ofst, int lmt) {
		List<DsqBillPost> billPosts = getBillPosts(ofst, lmt);
		if (billPosts.size() > 0) {
			int deleteCount = updBillPosts(billPosts);
			HibernateUtil.commit();
			runBillPostsUpd(ofst + lmt - deleteCount, lmt);
		} else {
			runBillThreadsUpd(0, lmt);
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<DsqBillPost> getBillPosts(int ofst, int lmt) {
		Query q = HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from " + DsqBillPost.class.getSimpleName() + " b order by b.id");
		DAOUtils.applyPaging(q, ofst, lmt);
		List<DsqBillPost> dbos = q.list();
		return dbos;
	}
	
	private int updBillPosts(List<DsqBillPost> billPosts) {
		int deleteCount = 0;
		for (DsqBillPost billPost : billPosts) {
			DsqApiPost apiPost = postsResource.callPostsDetails(billPost.getDsqPostId());
			if (apiPost != null) {
				if (apiPost.getId().equals(-1L) || apiPost.getIsDeleted()) {
					HibernateUtil.getSessionFactory().getCurrentSession().delete(billPost);
					deleteCount++;
				} else {
					billPost.setDsqThreadId(apiPost.getThread());
					HibernateUtil.getSessionFactory().getCurrentSession().update(billPost);
					ensureBillThread(billPost);
				}
			}
		}
		return deleteCount;
	}
	
	private void ensureBillThread(DsqBillPost billPost) {
		if (DAOFactory.getInstance().getDsqbillThreadDAO().findByDsqThread(billPost.getDsqThreadId()) == null) {
			DsqBillThread billThread = new DsqBillThread();
			billThread.setBill(billPost.getBill());
			billThread.setDsqThreadId(billPost.getDsqThreadId());
			HibernateUtil.getSessionFactory().getCurrentSession().save(billThread);
		}
	}
	
	private void runBillThreadsUpd(int ofst, int lmt) {
		List<DsqBillThread> billThreads = getBillThreads(ofst, lmt);
		if (billThreads.size() > 0) {
			int deleteCount = updBillThreads(billThreads);
			HibernateUtil.commit();
			runBillThreadsUpd(ofst + lmt - deleteCount, lmt);
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<DsqBillThread> getBillThreads(int ofst, int lmt) {
		Query q = HibernateUtil.getSessionFactory().getCurrentSession().createQuery("from " + DsqBillThread.class.getSimpleName() + " b order by b.id");
		DAOUtils.applyPaging(q, ofst, lmt);
		List<DsqBillThread> dbos = q.list();
		return dbos;
	}
	
	private int updBillThreads(List<DsqBillThread> billThreads) {
		int deleteCount = 0;
		for (DsqBillThread billThread : billThreads) {
			DsqApiThread apiThread = threadsResource.callThreadsDetails(billThread.getDsqThreadId());
			if (apiThread != null) {
				if (apiThread.getId().equals(-1L) || apiThread.getIsDeleted()) {
					HibernateUtil.getSessionFactory().getCurrentSession().delete(billThread);
					deleteCount++;
				} else {
					DsqBillThreadHlpr.update(billThread, apiThread);
					HibernateUtil.getSessionFactory().getCurrentSession().update(billThread);
				}
			}
		}
		return deleteCount;
	}
}
