package com.example.android.BluetoothChat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileWriter 
{
	private File _file;
	private OutputStream _OutputStream;
	
	public FileWriter(File file)
	{
		_file = file;
		//_file = new File(Environment.getExternalStorageDirectory(), "DemoFile.csv");
		
		try 
		{
			_OutputStream = new FileOutputStream(_file);
		} catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeLine(String line)
	{
		try 
		{
			_OutputStream.write((line+"\n").getBytes());
		} catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeFile()
	{
		try {
			_OutputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
