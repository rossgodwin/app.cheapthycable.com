package com.gwn.xcbl.web.rewrite;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Direction;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;

import com.gwn.xcbl.bl.auth.pwd.reset.ResetPwdUrlIntf;
import com.gwn.xcbl.bl.auth.signup.SignupVerifiedUrlIntf;
import com.gwn.xcbl.bl.ba.BaAlertUnsubscribeSuccessfulUrlIntf;
import com.gwn.xcbl.bl.bill.dsq.post.DsqBillPostNotifyUnsubscribeSuccessfulUrlIntf;
import com.gwn.xcbl.common.AppUris;
import com.gwn.xcbl.data.model.AppData;

/**
 * http://www.ocpsoft.org/java/seo-friendly-angularjs-with-html5-pushstate-rewrite-and-twelve-lines-of-code/
 * http://www.ocpsoft.org/rewrite/
 * http://www.ocpsoft.org/rewrite/examples/
 * 
 * Possible Apache alternative:
 * http://ericduran.io/2013/05/31/angular-html5Mode-with-yeoman/
 *
 */
@RewriteConfiguration
public class PushStateCfgProvider extends HttpConfigurationProvider {
	
	@Override
	public Configuration getConfiguration(final ServletContext context) {
		/**
		 * Urls that do not require a authenticated user
		 */
		final List<String> publicUrls = new ArrayList<String>();
		publicUrls.add("/login");
		publicUrls.add("/signup");
		publicUrls.add("/forgot");
		publicUrls.add("/" + ResetPwdUrlIntf.API_URL);
		publicUrls.add("/" + SignupVerifiedUrlIntf.API_URL);
		publicUrls.add("/" + BaAlertUnsubscribeSuccessfulUrlIntf.API_URL);
		publicUrls.add("/" + DsqBillPostNotifyUnsubscribeSuccessfulUrlIntf.API_URL);
		
		final List<String> secureUrls = new ArrayList<String>();
		secureUrls.add("/" + AppUris.ROOT_PATH_NAME);
		
		String appPublicEp = AppData.getInstance().getAppPublicEp();
		String appSecureEp = AppData.getInstance().getAppSecureEp();
		
		ConfigurationBuilder cfgBldr = ConfigurationBuilder.begin();
		for (String publicUrl : publicUrls) {
			cfgBldr.addRule().when(Direction.isInbound().and(Path.matches(publicUrl))).perform((Forward.to(appPublicEp)));
		}
		for (String secureUrl : secureUrls) {
			cfgBldr.addRule().when(Direction.isInbound().and(Path.matches(secureUrl))).perform((Forward.to(appSecureEp)));
		}
		
		return cfgBldr;
	}

	@Override
	public int priority() {
		/* This provides ordering if you have multiple configurations */
		return 10;
	}
}
