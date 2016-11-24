package com.gwn.xcbl.bl.ba.email;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.utils.URIBuilder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gwn.xcbl.bl.ba.BaAlertUnsubscribeSrvltIntf;
import com.gwn.xcbl.bl.mail.EmailConstants;
import com.gwn.xcbl.bl.mail.EmailFromAddressFactory;
import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.bl.mail.data.model.EmailBodyPart;
import com.gwn.xcbl.bl.mail.data.model.EmailRecipient;
import com.gwn.xcbl.bl.thymeleaf.TlfUtils;
import com.gwn.xcbl.common.AppConstants;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.hibernate.entity.ba.BaAlert;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.model.AppData;
import com.gwn.xcbl.web.AppServletContextUtils;

public class BaEmailBuilder {

	private ServletContext servletCtx;
	
	public BaEmailBuilder(ServletContext servletCtx) {
		this.servletCtx = servletCtx;
	}
	
	public Email buildEmail(BaAlert alert, List<Bill> bills) throws URISyntaxException {
		Account account = alert.getAccount();
		
		TemplateEngine engine = TlfUtils.getTemplateEngine(servletCtx);
		
		String logoRealPath = AppServletContextUtils.getLogoRealPath(servletCtx);
		String logoFileName = FilenameUtils.getName(logoRealPath);
		List<BaEmailBill> emailBills = (List<BaEmailBill>)CollectionUtils.collect(bills, new BaEmailBillTransformer());
		User user = DAOFactory.getInstance().getUserDAO().findByAccountId(account.getId());
		
		Context ctx = new Context();
		TlfUtils.addVariableAppName(ctx);
		TlfUtils.addVariableLogoImg(ctx, logoFileName);
		ctx.setVariable(EmailConstants.VARIABLE_BILL_COUNT, emailBills.size());
		ctx.setVariable(EmailConstants.VARIABLE_BILLS, emailBills);
		TlfUtils.addVariableSocialMediaUrls(ctx);
		ctx.setVariable(EmailConstants.VARIABLE_PRODUCT_URL, AppData.getInstance().getDomainUrl());
		ctx.setVariable(EmailConstants.VARIABLE_UNSUBSCRIBE_URL, getUnsubscribeUrl(alert));
		
		String htmlContent = engine.process("emails/bill-alert.html", ctx);
		
		Email email = new Email();
		email.setFrom(EmailFromAddressFactory.getNoReplyAddress());
		email.getRecipients().add(new EmailRecipient(null, user.getEmail()));
		email.setSubject(AppConstants.APP_NAME + " Bill Alert");
		email.setHtmlMsg(htmlContent);
		email.setLogoBodyPart(new EmailBodyPart("inline", logoRealPath, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		
		return email;
	}
	
	private static String getUnsubscribeUrl(BaAlert alert) throws URISyntaxException {
		URIBuilder bldr = new URIBuilder(AppData.getInstance().getDomainUrl() + "/" + BaAlertUnsubscribeSrvltIntf.URL);
		bldr.setParameters(BaAlertUnsubscribeSrvltIntf.Util.getParams(alert));
		String rslt = bldr.toString();
		return rslt;
	}
}
