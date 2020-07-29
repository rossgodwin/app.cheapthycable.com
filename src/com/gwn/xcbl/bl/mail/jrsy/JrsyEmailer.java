package com.gwn.xcbl.bl.mail.jrsy;

import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpStatus;

import com.gwn.xcbl.bl.mail.data.model.Email;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.multipart.FormDataMultiPart;

/**
 * Jersey helpful resources:
 * https://cloud.google.com/appengine/docs/flexible/java/sending-emails-with-mailgun
 * https://github.com/sargue/mailgun
 * http://stackoverflow.com/questions/28225145/adding-inline-images-to-mailgun-emails-using-scala-and-play-ws
 * http://skmzq.qiniucdn.com/data/20140113091129/index.html
 * https://gist.github.com/alejandrogr/8609888
 */
public class JrsyEmailer {

	private static Log log = LogFactory.getLog(JrsyEmailer.class);
	
	private JrsyEmailConfiguration config;
	
	public JrsyEmailer(JrsyEmailConfiguration config) {
		this.config = config;
	}
	
	public ClientResponse sendEmail(Email email) {
		Client client = Client.create();
		addFilter(config, client);
		WebResource webResource = client.resource(config.getApiBaseUrl() + "/" + config.getDomain() + "/messages");
		FormDataMultiPart formData = new JrsyEmailTransformer().transform(email);
		ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, formData);
		if (HttpStatus.SC_OK != response.getStatus()) {
			log.info("Response code: " + response.getStatusInfo().getStatusCode());
			log.info("Reason: " + response.getStatusInfo().getReasonPhrase());
		}
		return response;
	}
	
	private static void addFilter(JrsyEmailConfiguration config, Client client) {
		client.addFilter(new HTTPBasicAuthFilter("api", config.getApiKey()));
	}
}
