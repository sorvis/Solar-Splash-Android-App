package com.example.android.BluetoothChat.Web;

import com.example.android.BluetoothChat.BluetoothChat;
import com.example.android.BluetoothChat.view;

public class Main 
{
	private String _hostAddress; // address of web-server including file name on host
	private int _updateTimeSecs; // amount of time between updates sent to web-server
	private InteruptCreator _interupt;
	private ServerContactor _webPage;
	private BluetoothChat _BluetoothChat;
	private view _view;
	
	public Main(String hostAddress, int updateTimeSecs, com.example.android.BluetoothChat.BluetoothChat bluetoothChat, view View)
	{
		_hostAddress = hostAddress;
		_updateTimeSecs = updateTimeSecs;
		_webPage = new ServerContactor(this, _hostAddress);
		_BluetoothChat = bluetoothChat;
		_view = View;
		
		// start interupt thread
		_interupt = new InteruptCreator(_updateTimeSecs*100, this);
		Thread serverConnectionThread= new Thread(_interupt);
		serverConnectionThread.start();
	}
	public void interupt()
	{
		final String test = _webPage.getPage();
		_BluetoothChat.sendTest(test);
		_BluetoothChat.runOnUiThread(new Runnable() {
		    public void run() {
		    	_view.set_textView5(test);
		    }
		});
	}
}
