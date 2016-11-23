package com.gwn.xcbl.bl.bill;

import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gwn.xcbl.bl.mail.EmailConstants;
import com.gwn.xcbl.bl.mail.EmailFromAddressFactory;
import com.gwn.xcbl.bl.mail.data.model.Email;
import com.gwn.xcbl.bl.mail.data.model.EmailBodyPart;
import com.gwn.xcbl.bl.mail.data.model.EmailRecipient;
import com.gwn.xcbl.bl.thymeleaf.TlfUtils;
import com.gwn.xcbl.common.AppConstants;
import com.gwn.xcbl.data.hibernate.HibernateUtil;
import com.gwn.xcbl.data.hibernate.dao.DAOFactory;
import com.gwn.xcbl.data.hibernate.entity.Account;
import com.gwn.xcbl.data.hibernate.entity.GeoZipCode;
import com.gwn.xcbl.data.hibernate.entity.Provider;
import com.gwn.xcbl.data.hibernate.entity.User;
import com.gwn.xcbl.data.hibernate.entity.bill.Bill;
import com.gwn.xcbl.data.hibernate.entity.bill.BillCableOptions;
import com.gwn.xcbl.data.hibernate.entity.bill.BillInternetOptions;
import com.gwn.xcbl.data.model.GeoZipCodeUtils;
import com.gwn.xcbl.data.shared.SetOperator;
import com.gwn.xcbl.data.shared.bill.BillSearchCritrDTO;
import com.gwn.xcbl.data.shared.bill.create.BillCreateDTO;
import com.gwn.xcbl.web.AppServletContextUtils;
import com.gwn.xcbl.web.HttpServletRequestHelper;

public class BillHelper {

	public static Bill saveBill(Account account, BillCreateDTO billDto) {
		Bill bill = new Bill();
		bill.setAccount(account);
		
		// TODO check to make sure either the selected provider or typedProviderName is not empty
		if (StringUtils.isNoneEmpty(billDto.getLookupProviderName())) {
			Provider provider = DAOFactory.getInstance().getProviderDAO().findByName(billDto.getLookupProviderName());
			if (provider != null) {
				bill.setProvider(provider);
			} else {
				bill.setProviderOther(billDto.getLookupProviderName());
			}
		}
//		if (billDto.getSelectedProvider() != null) {
//			Provider provider = DAOFactory.getInstance().getProviderDAO().findById(billDto.getSelectedProvider().getId(), true);
//			bill.setProvider(provider);
//		}
//		if (StringUtils.isNoneEmpty(billDto.getTypedProviderName())) {
//			bill.setProviderOther(billDto.getTypedProviderName());
//		}
		
		bill.setValidDate(LocalDate.now());
		
		List<GeoZipCode> geoZipCodes = DAOFactory.getInstance().getGeoZipCodeDAO().findByZipCode(billDto.getZipCode());
		// TODO if geoZipCodes is empty throw error
		bill.setGeoZipCode(geoZipCodes.get(0));
		
		bill.setTotalAmount(billDto.getTotalAmount());
		
		bill.setInternetService(billDto.isInternetService());
		bill.setCableService(billDto.isCableService());
		bill.setPhoneService(billDto.isPhoneService());
		bill.setComments(billDto.getComments());
		
		HibernateUtil.getSessionFactory().getCurrentSession().save(bill);
		
		if (billDto.isInternetService() && billDto.getInternetOptions() != null) {
			BillInternetOptions optns = new BillInternetOptions();
			optns.setBill(bill);
			optns.setModem(billDto.getInternetOptions().isModem());
			optns.setDownloadSpeedMbps(billDto.getInternetOptions().getDownloadSpeedMbps());
			optns.setDownloadSpeedMbpsRngLower(billDto.getInternetOptions().getDownloadSpeedMbpsRngLower());
			optns.setDownloadSpeedMbpsRngUpper(billDto.getInternetOptions().getDownloadSpeedMbpsRngUpper());
			
			HibernateUtil.getSessionFactory().getCurrentSession().save(optns);
			
			bill.setInternetOptions(optns);
		}
		
		if (billDto.isCableService() && billDto.getCableOptions() != null) {
			BillCableOptions optns = new BillCableOptions();
			optns.setBill(bill);
			optns.setBoxCount(billDto.getCableOptions().getBoxCount());
			optns.setDvrCount(billDto.getCableOptions().getDvrCount());
			optns.setSpecialChannels(billDto.getCableOptions().isSpecialChannels());
			
			HibernateUtil.getSessionFactory().getCurrentSession().save(optns);
			
			bill.setCableOptions(optns);
		}
		
		return bill;
	}
	
	public static Email buildBillCreateEmail(HttpServletRequest httpRequest, User user, Bill bill) throws URISyntaxException {
		ServletContext servletCtx = httpRequest.getServletContext();
		
		TemplateEngine engine = TlfUtils.getTemplateEngine(servletCtx);
		
		String baseUrl = HttpServletRequestHelper.getServerContextPath(httpRequest).toString();
		String logoRealPath = AppServletContextUtils.getLogoRealPath(servletCtx);
		String logoFileName = FilenameUtils.getName(logoRealPath);
		
		Context ctx = new Context();
		TlfUtils.addVariableAppName(ctx);
		TlfUtils.addVariableLogoImg(ctx, logoFileName);
		ctx.setVariable(EmailConstants.VARIABLE_PRODUCT_URL, baseUrl);
		ctx.setVariable(EmailConstants.VARIABLE_CITY_STATE_CODE_ZIP, GeoZipCodeUtils.getCityStateCodeZip(bill.getGeoZipCode()));
		TlfUtils.addVariableSocialMediaUrls(ctx);
		
		String htmlContent = engine.process("emails/bill-create.html", ctx);
		
		Email email = new Email();
		email.setFrom(EmailFromAddressFactory.getNoReplyAddress());
		email.getRecipients().add(new EmailRecipient(null, user.getEmail()));
		email.setSubject("Bill Recorded in " + AppConstants.APP_NAME);
		email.setHtmlMsg(htmlContent);
		email.setLogoBodyPart(new EmailBodyPart("inline", logoRealPath, MediaType.APPLICATION_OCTET_STREAM_TYPE));
		
		return email;
	}
	
	public static BillSearchCritrDTO buildSimilarBillSearchCritr(Bill bill) {
		BillSearchCritrDTO critr = new BillSearchCritrDTO();
		
		critr.setServicesSetOperator(SetOperator.MATCHES);
		critr.setInternetService(bill.isInternetService());
		critr.setCableService(bill.isCableService());
		critr.setPhoneService(bill.isPhoneService());
		
		BillCableOptions cableOptions = bill.getCableOptions();
		if (cableOptions != null) {
			critr.setCableOptionBoxCount(cableOptions.getBoxCount());
			critr.setCableOptionDvrCount(cableOptions.getDvrCount());
			critr.setCableOptionSpecialChannels(cableOptions.isSpecialChannels());
		}
		
		return critr;
	}
	
	public static String getServicesStr(Bill bill, String seperator) {
		List<String> services = new ArrayList<String>();
		if (bill.isInternetService()) {
			services.add("Internet");
		}
		if (bill.isCableService()) {
			services.add("Cable");
		}
		if (bill.isPhoneService()) {
			services.add("Phone");
		}
		String r = StringUtils.join(services, seperator);
		return r;
	}
	
	public static String getAllOptionsStr(Bill bill, String seperator) {
		List<String> options = new ArrayList<String>();
		
		BillInternetOptions internetOptions = bill.getInternetOptions();
		if (internetOptions != null) {
			if (internetOptions.isModem()) {
				options.add("Modem");
			}
			if (internetOptions.getDownloadSpeedMbpsRngLower() != null && internetOptions.getDownloadSpeedMbpsRngUpper() != null) {
				String r = MessageFormat.format("{0} - {1} Mbps", internetOptions.getDownloadSpeedMbpsRngLower(), internetOptions.getDownloadSpeedMbpsRngUpper());
				options.add(r);
			}
		}
		
		BillCableOptions cableOptions = bill.getCableOptions();
		if (cableOptions != null) {
			if (cableOptions.getDvrCount() != null && cableOptions.getDvrCount() > 0) {
				options.add(cableOptions.getDvrCount() + " DVR" + (cableOptions.getDvrCount() > 1 ? "s" : ""));
			}
			if (cableOptions.getBoxCount() != null && cableOptions.getBoxCount() > 0) {
				options.add(cableOptions.getBoxCount() + " Box" + (cableOptions.getBoxCount() > 1 ? "es" : ""));
			}
			if (cableOptions.isSpecialChannels()) {
				options.add("Special Channels");
			}
		}
		
		String r = StringUtils.join(options, seperator);
		return r;
	}
}
