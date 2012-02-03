package com.example.android.BluetoothChat.Web;

public class InteruptCreator implements Runnable 
{
	private boolean go = true;
	private int _time;
	private Main _Main;
	
	public InteruptCreator(int time, Main main)
	{		
		_time = time;
		_Main = main;
	}
	
	public void run()
	{
		while (go)
		{
			try
			{
				Thread.sleep(_time);
				_Main.interupt();
			}
			catch(InterruptedException e){}
		}
	}
}
