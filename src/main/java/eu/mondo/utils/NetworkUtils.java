package eu.mondo.utils;

import java.io.IOException;
import java.net.InetAddress;

public class NetworkUtils {
	
	public static String getLocalIpAddress() throws IOException{
		String hostName = InetAddress.getLocalHost().getHostName();
	    return InetAddress.getByName(hostName).getHostAddress();
	}

}
