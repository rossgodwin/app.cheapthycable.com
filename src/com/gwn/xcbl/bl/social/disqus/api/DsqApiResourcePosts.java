package com.gwn.xcbl.bl.social.disqus.api;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.reflect.TypeToken;
import com.gwn.xcbl.bl.social.disqus.api.response.DsqApiPost;
import com.gwn.xcbl.bl.social.disqus.api.response.DsqApiResponse;

public class DsqApiResourcePosts {

	private DsqApi api;
	
	public DsqApiResourcePosts(DsqApi api) {
		this.api = api;
	}
	
	/**
	 * https://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientFormLogin.java
	 * https://disqus.com/api/docs/posts/details/
	 * 
	 * @param postId
	 * @return
	 */
	public String getPostDetailsUrl(long postId) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		api.addApiKeysParams(params);
		params.add(new BasicNameValuePair("post", String.valueOf(postId)));
		
		try {
			URIBuilder bldr = new URIBuilder(api.getApiUrl() + "/posts/details.json");
			bldr.setParameters(params);
			String url = bldr.toString();
			return url;
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	public DsqApiResponse<DsqApiPost> callPostsDetails(long postId) {
		final String url = getPostDetailsUrl(postId);
		DsqApiResponse<DsqApiPost> obj = api.call(url, new TypeToken<DsqApiResponse<DsqApiPost>>(){}.getType());
		return obj;
	}
}
