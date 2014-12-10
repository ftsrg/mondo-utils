package eu.mondo.utils;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class WebServiceUtils {

	public static HttpResponse call(String ip, int port, String path) {
		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(MessageFormat.format("http://{0}:{1,number,#}{2}", ip, port, path));
			HttpResponse response = httpClient.execute(httpGet);
			httpClient.getConnectionManager().shutdown();
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			  throw new RuntimeException("Calling web service failed, status: " + response.getStatusLine());
			}
			return response;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
