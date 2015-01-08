package eu.mondo.utils;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.google.common.collect.ImmutableList;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class WebServiceUtils {

	public static ClientResponse call(String host, int port, String path, NameValuePair... params) {
		try {
		    URI uri = URIUtils.createURI("http", host, port, path, URLEncodedUtils.format(ImmutableList.copyOf(params), "UTF-8"), null);
		    DefaultClientConfig config = new DefaultClientConfig(JacksonJsonProvider.class);
		    ClientResponse response = Client.create(config).resource(uri).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			Status status = response.getClientResponseStatus();
			if (status != Status.OK) {
				throw new RuntimeException("Web service responded with not OK: " + status);
			}
			return response;
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
