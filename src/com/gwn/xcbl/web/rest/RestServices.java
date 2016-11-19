package com.gwn.xcbl.web.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest")
public class RestServices extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	
	public RestServices() {
		singletons.add(new AuthRS());
		singletons.add(new BaAlertRS());
		singletons.add(new BillRS());
		singletons.add(new DsqBillCommentRS());
		singletons.add(new GeoZipCodeRS());
		singletons.add(new ProviderRS());
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
