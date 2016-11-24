package com.gwn.xcbl.bl.bill.dsq.post;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gwn.xcbl.bl.mail.Emailer;
import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.bl.social.disqus.api.DsqApi;
import com.gwn.xcbl.bl.social.disqus.api.DsqApiKeys;
import com.gwn.xcbl.bl.social.disqus.api.DsqApiResourcePosts;
import com.gwn.xcbl.bl.social.disqus.api.response.DsqApiPost;
import com.gwn.xcbl.bl.social.disqus.api.response.DsqApiResponse;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillPost;
import com.gwn.xcbl.data.model.AppData;
import com.gwn.xcbl.data.shared.ILongId;

public class DsqBillPostNotifyThreadPostersRun implements Runnable {

	private static Log log = LogFactory.getLog(DsqBillPostNotifyThreadPostersRun.class);
	
	private ServletContext servletCtx;
	
	private long ignoreUserId;
	
	private DsqBillPost billPost;
	
	public DsqBillPostNotifyThreadPostersRun(ServletContext servletCtx, long ignoreUserId, DsqBillPost billPost) {
		this.servletCtx = servletCtx;
		this.ignoreUserId = ignoreUserId;
		this.billPost = billPost;
	}
	
	@Override
	public void run() {
		HibernateUtil.beginTransaction();
		
		DsqApiKeys keys = new DsqApiKeys(AppData.getInstance().getDisqusApiKey(), AppData.getInstance().getDisqusApiSecret());
		DsqApiResourcePosts postsResource = new DsqApiResourcePosts(new DsqApi(AppData.getInstance().getDisqusApiUrl(), keys));
		DsqBillPostEmailNotifyBuilder emailBuilder = new DsqBillPostEmailNotifyBuilder(servletCtx);
		
		try {
			List<User> users = new ArrayList<User>();
			
			List<DsqBillPost> billPosts = DAOFactory.getInstance().getDsqBillPostDAO().findByBill(billPost.getBill().getId());
			for (DsqBillPost p : billPosts) {
				if (!p.getId().equals(billPost.getId())) {
					User user = DAOFactory.getInstance().getUserDAO().findByAccountId(p.getAccount().getId());
					if (!user.getId().equals(ignoreUserId) && !ILongId.Utils.doesExists(users, user)) {
						users.add(user);
					}
				}
			}
			
			if (users.size() > 0) {
				DsqApiPost apiPost = null;
				
				DsqApiResponse<DsqApiPost> postDetailsResponse = postsResource.callPostsDetails(billPost.getDsqPostId());
				if (postDetailsResponse.getResponse() != null) {
					apiPost = postDetailsResponse.getResponse();
				}
				
				if (apiPost != null) {
					for (User user : users) {
						Email email = emailBuilder.buildEmail(user, billPost, apiPost);
						Emailer.sendEmail(email);
					}
				} else {
					log.warn("Did not notify thread posters of bill post (id: " + billPost.getId() + ") because could not get DISQUS post details (DISQUS post id: " + billPost.getDsqPostId() + ").");
				}
			}
		} catch (Exception e) {
		} finally {
			HibernateUtil.closeSession();
		}
	}
}
