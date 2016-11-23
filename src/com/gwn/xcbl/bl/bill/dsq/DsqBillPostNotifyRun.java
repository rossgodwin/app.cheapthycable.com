package com.gwn.xcbl.bl.bill.dsq;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

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

public class DsqBillPostNotifyRun implements Runnable {

	private ServletContext servletCtx;
	
	private long ignoreUserId;
	
	private DsqBillPost billPost;
	
	public DsqBillPostNotifyRun(ServletContext servletCtx, long ignoreUserId, DsqBillPost billPost) {
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
			
			for (User user : users) {
				DsqApiResponse<DsqApiPost> apiPost = postsResource.callPostsDetails(billPost.getDsqPostId());
				if (apiPost.getResponse() != null) {
					Email email = emailBuilder.buildEmail(user, billPost, apiPost.getResponse());
					Emailer.sendEmail(email);
				}
			}
		} catch (Exception e) {
		} finally {
			HibernateUtil.closeSession();
		}
	}
}
