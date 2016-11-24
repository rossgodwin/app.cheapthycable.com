package com.gwn.xcbl.bl.social.disqus.api;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.reflect.TypeToken;
import com.gwn.xcbl.bl.social.disqus.api.response.DsqApiResponse;
import com.gwn.xcbl.bl.social.disqus.api.response.DsqApiThread;

public class DsqApiResourceThreads {

	private DsqApi api;
	
	public DsqApiResourceThreads(DsqApi api) {
		this.api = api;
	}
	
	/**
	 * https://disqus.com/api/docs/threads/details/
	 * 
	 * @param threadId
	 * @return
	 */
	public String getThreadDetailsUrl(long threadId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		api.addApiKeysParams(params);
		params.add(new BasicNameValuePair("thread", String.valueOf(threadId)));
		
		try {
			URIBuilder bldr = new URIBuilder(api.getApiUrl() + "/threads/details.json");
			bldr.setParameters(params);
			String url = bldr.toString();
			return url;
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	public DsqApiResponse<DsqApiThread> callThreadsDetails(long threadId) {
		final String url = getThreadDetailsUrl(threadId);
		DsqApiResponse<DsqApiThread> obj = api.call(url, new TypeToken<DsqApiResponse<DsqApiThread>>(){}.getType());
		return obj;
	}
}
