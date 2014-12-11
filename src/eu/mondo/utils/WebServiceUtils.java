package eu.mondo.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.common.collect.ImmutableList;

public class WebServiceUtils {

	public static HttpResponse call(String ip, int port, String path, NameValuePair... params) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			URI uri = URIUtils.createURI("http", ip, port, path, URLEncodedUtils.format(ImmutableList.copyOf(params), "UTF-8"), null);
			HttpGet httpGet = new HttpGet(uri);
			HttpResponse response = httpClient.execute(httpGet);
			httpClient.getConnectionManager().shutdown();
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new RuntimeException("Web service responded with not OK: " + response.getStatusLine());
			}
			return response;
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

}
