package com.gwn.xcbl.web.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.gwn.xcbl.common.AppUris;

@ApplicationPath(AppUris.ROOT_PATH_NAME + "/rest")
public class RestServices extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	
	public RestServices() {
		singletons.add(new AuthRS());
		singletons.add(new BaAlertRS());
		singletons.add(new BillRS());
		singletons.add(new DsqBillPostRS());
		singletons.add(new GeoZipCodeRS());
		singletons.add(new ProviderRS());
		singletons.add(new RecaptchaRS());
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
