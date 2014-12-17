package eu.mondo.utils;

import java.io.IOException;
import java.net.Socket;

public class NetworkUtils {
	
	public static String getLocalIpAddress() throws IOException{
		Socket socket = new Socket("google.com", 80);
		String result = socket.getLocalAddress().getHostAddress();
		socket.close();
		return result;
	}

}
