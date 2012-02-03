package com.example.android.BluetoothChat;

public class Data_Processor 
{
	private view _view;
	private BluetoothChat _BluetoothChat;
	private String[] _headers;
	private float[][] _data;
	private int currentPosition=0;
	private int dataStorageSize=20;
	
	private int _SVOL=-1;
	private int _MCUR=-1;
	private int _BCUR=-1;
	private int _PCUR=-1;
	private int _BRPM=-1;
	private int _SRPM=-1;
	
	public Data_Processor(view View, BluetoothChat bluetoothchat)
	{
		_view = View;
		_BluetoothChat = bluetoothchat;
	}
	public void updateHeader(String[] headers)
	{
		_headers = headers;
		_data = new float[dataStorageSize][_headers.length];
		
		// set labels
		int counter = 0;
		for(String item : _headers)
		{
			switch(Labels.get(item))
			{
			case SVOL:
				_SVOL = counter;
				break;
			case MCUR:
				_MCUR = counter;
				break;
			case BCUR:
				_BCUR = counter;
				break;
			case PCUR:
				_PCUR = counter;
				break;
			case BRPM:
				_BRPM = counter;
				break;
			case SRPM:
				_SRPM = counter;
				break;
			}
			counter ++;
		} // end foreach	
	}
	public void recieveData(float[] data)
	{
		//put data into array
		int counter = 0;
		for(float item : data)
		{
			_data[currentPosition][counter] = item;
			if(counter < dataStorageSize)
			{
			counter++;
			}
			else
			{
				counter = 0;
			}
		}//end foreach
		
		// display data
		updateDisplay();
	}
	private void updateDisplay()
	{
		if(_SVOL != -1)
		{
			_view.set_SVOLBar((int) _data[currentPosition][_SVOL]);
		}
		if(_MCUR != -1)
		{
			_view.set_MCURBar((int) _data[currentPosition][_MCUR]);
		}
		if(_BCUR != -1)
		{
			_view.set_BCURBar((int) _data[currentPosition][_BCUR]);
		}
		if(_PCUR != -1)
		{
			_view.set_PCURBar((int) _data[currentPosition][_PCUR]);
		}
		if(_BRPM != -1)
		{
			_view.set_BRPMBar((int) _data[currentPosition][_BRPM]);
		}
		if(_SRPM != -1)
		{
			_view.set_SRPMBar((int) _data[currentPosition][_SRPM]);
		}
	}
	public void changeThottle(int value)
	{
		_BluetoothChat.changeThrottle(value);
	}
	
	//enum for string switch statement
	public enum Labels
	{
		SVOL,MCUR,BCUR,PCUR,BRPM,SRPM,NOVALUE;
		public static Labels get(String str)
		{
	        try 
	        {
	            return valueOf(str);
	        } 
	        catch (Exception ex) 
	        {
	            return NOVALUE;
	        }
		}
	}
}
