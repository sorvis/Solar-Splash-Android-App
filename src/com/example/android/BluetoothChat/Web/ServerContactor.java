package com.example.android.BluetoothChat.Web;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.net.Uri;

public class ServerContactor 
{
	private Main _main;
	private String _hostPath;
	Uri uri;
	
	public ServerContactor(Main main, String hostPath)
	{
		_main = main;
		_hostPath = hostPath;
	}
	public String getPage()
	{	
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(_hostPath);
		ResponseHandler<String> resHandler = new BasicResponseHandler();
		String page = null;
		try {
			page = httpClient.execute(httpGet, resHandler);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return page;
	}
}
