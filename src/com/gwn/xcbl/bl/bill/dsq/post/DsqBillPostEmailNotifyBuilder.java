package com.gwn.xcbl.bl.bill.dsq.post;

import java.net.URISyntaxException;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gwn.xcbl.bl.bill.BillCommentsUrlIntf;
import com.gwn.xcbl.bl.mail.EmailConstants;
import com.gwn.xcbl.bl.mail.EmailFromAddressFactory;
import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.bl.mail.data.model.EmailBodyPart;
import com.gwn.xcbl.bl.mail.data.model.EmailRecipient;
import com.gwn.xcbl.bl.social.disqus.api.response.DsqApiPost;
import com.gwn.xcbl.bl.thymeleaf.TlfUtils;
import com.gwn.xcbl.common.AppConstants;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.hibernate.entity.bill.dsq.DsqBillPost;
import com.gwn.xcbl.data.model.AppData;
import com.gwn.xcbl.web.AppServletContextUtils;

public class DsqBillPostEmailNotifyBuilder {

	private ServletContext servletCtx;
	
	public DsqBillPostEmailNotifyBuilder(ServletContext servletCtx) {
		this.servletCtx = servletCtx;
	}
	
	public Email buildEmail(User user, DsqBillPost billPost, DsqApiPost apiPost) throws URISyntaxException {
		TemplateEngine engine = TlfUtils.getTemplateEngine(servletCtx);
		
		String logoRealPath = AppServletContextUtils.getLogoRealPath(servletCtx);
		String logoFileName = FilenameUtils.getName(logoRealPath);
		String respondUrl = BillCommentsUrlIntf.Utils.getUrl(AppData.getInstance().getDomainUrl(), billPost.getBill().getId());
		
		Context ctx = new Context();
		TlfUtils.addVariableAppName(ctx);
		TlfUtils.addVariableLogoImg(ctx, logoFileName);
		ctx.setVariable(EmailConstants.VARIABLE_DSQ_POST_TEXT, apiPost.getRaw_message());
		ctx.setVariable(EmailConstants.VARIABLE_BILL_COMMENT_RESPOND_URL, respondUrl);
		ctx.setVariable(EmailConstants.VARIABLE_PRODUCT_URL, AppData.getInstance().getDomainUrl());
		
		String htmlContent = engine.process("emails/bill-post-notify.html", ctx);
		
		Email email = new Email();
		email.setFrom(EmailFromAddressFactory.getNoReplyAddress());
		email.getRecipients().add(new EmailRecipient(null, user.getEmail()));
		email.setSubject("Bill Discussion Post in " + AppConstants.APP_NAME);
		email.setHtmlMsg(htmlContent);
		email.setLogoBodyPart(new EmailBodyPart("inline", logoRealPath, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		
		return email;
	}
}
