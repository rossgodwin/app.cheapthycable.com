package com.gwn.xcbl.bl.social.disqus.api;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
	
	/**
	 * 
	 * @param postId
	 * @return if {@link DsqApiPost#getId()} == -1L then post was deleted
	 */
	public DsqApiPost callPostsDetails(long postId) {
		final String url = getPostDetailsUrl(postId);
		DsqApiResponse<JsonElement> apiResponse = api.call(url, new TypeToken<DsqApiResponse<JsonElement>>(){}.getType());
		
		DsqApiPost obj = null;
		if (apiResponse.getCode() == 0) {
			obj = new Gson().fromJson(apiResponse.getResponse(), DsqApiPost.class);
		} else if (apiResponse.getCode() == 2) {
			if (apiResponse.getResponse().toString().contains("Invalid argument, 'post'")) {
				obj = DsqApiPost.idInstance(-1L);
			}
		}
		return obj;
	}
}
