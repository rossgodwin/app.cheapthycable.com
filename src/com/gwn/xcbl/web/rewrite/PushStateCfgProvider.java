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
		
		final List<String> secureUrls = new ArrayList<String>();
		secureUrls.add("/app");
		
//		String appPublicEp = null;
//		String appSecureEp = null;
//		if (AppData.getInstance().isEnvProd()) {
//			appPublicEp = "/client/dist/index-app-public.jsp";
//			appSecureEp = "/client/dist/index-app-secure.jsp";
//		} else {
//			appPublicEp = "/client/src/index-app-public.jsp";
//			appSecureEp = "/client/src/index-app-secure.jsp";
//		}
		String appPublicEp = AppData.getInstance().getAppPublicEp();
		String appSecureEp = AppData.getInstance().getAppSecureEp();
		
		// logs the domain used for testing
//		return ConfigurationBuilder.begin()
//	               .addRule()
//	               .when(Direction.isInbound().and(Domain.matches("{domain}")))
//	               .perform(Log.message(Level.INFO, "Client requested path: {domain}"))
//	               .where("domain").matches(".*");
		
//		ConfigurationBuilder prodCfgBldr = ConfigurationBuilder.begin();
//		for (String publicUrl : publicUrls) {
//			prodCfgBldr.addRule().when(Direction.isInbound().and(Path.matches(publicUrl))).perform(Forward.to(prodAppPublicEp));
//		}
//		for (String secureUrl : secureUrls) {
//			prodCfgBldr.addRule().when(Direction.isInbound().and(Path.matches(secureUrl))).perform(Forward.to(prodAppSecureEp));
//		}
//		
//		ConfigurationBuilder cfgBldr = ConfigurationBuilder.begin();
//		cfgBldr.addRule().when(Domain.matches(prodDomain)).perform(Subset.evaluate(prodCfgBldr));
//		for (String publicUrl : publicUrls) {
//			cfgBldr.addRule().when(Direction.isInbound().and(Path.matches(publicUrl))).perform((Forward.to(devAppPublicEp)));
//		}
//		for (String secureUrl : secureUrls) {
//			cfgBldr.addRule().when(Direction.isInbound().and(Path.matches(secureUrl))).perform((Forward.to(devAppSecureEp)));
//		}
		
		ConfigurationBuilder cfgBldr = ConfigurationBuilder.begin();
		for (String publicUrl : publicUrls) {
			cfgBldr.addRule().when(Direction.isInbound().and(Path.matches(publicUrl))).perform((Forward.to(appPublicEp)));
		}
		for (String secureUrl : secureUrls) {
			cfgBldr.addRule().when(Direction.isInbound().and(Path.matches(secureUrl))).perform((Forward.to(appSecureEp)));
		}
		
		return cfgBldr;
		
//		final String loginUrl = "/login";
//		final String signUpUrl = "/signup";
//		final String appUrl = "/app";
//		
//		return ConfigurationBuilder.begin()
//				.addRule().when(Domain.matches(prodDomain))
//				.perform(Subset.evaluate(ConfigurationBuilder.begin()
//						.addRule().when(Direction.isInbound().and(Path.matches(loginUrl))).perform((Forward.to(prodAppPublicEp)))
//						.addRule().when(Direction.isInbound().and(Path.matches(signUpUrl))).perform((Forward.to(prodAppPublicEp)))
//						.addRule().when(Direction.isInbound().and(Path.matches(appUrl))).perform((Forward.to(prodAppSecureEp)))
//				))
//				.addRule().when(Direction.isInbound().and(Path.matches(loginUrl))).perform((Forward.to(devAppPublicEp)))
//				.addRule().when(Direction.isInbound().and(Path.matches(signUpUrl))).perform((Forward.to(devAppPublicEp)))
//				.addRule().when(Direction.isInbound().and(Path.matches(appUrl))).perform((Forward.to(devAppSecureEp)));
	}

	@Override
	public int priority() {
		/* This provides ordering if you have multiple configurations */
		return 10;
	}
}
