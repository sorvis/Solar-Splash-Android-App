 package com.example.android.BluetoothChat;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class view 
{
	private BluetoothChat _BluetoothChat;
	private FileWriter _fileWriter;
	private ToggleButton _toggleGoButton;
	private SeekBar _throttleBar;
	private SeekBar _SVOLBar;
	private SeekBar _MCURBar;
	private SeekBar _PCURBar;
	private SeekBar _BCURBar;
	private SeekBar _BRPMBar;
	private SeekBar _SRPMBar;
	private TextView _textView5;
	
	//MenuItem _menuShowInput;
	int _throttle;
	NumberFormat format = new DecimalFormat("0000");
	
	public view(BluetoothChat bluetoothChat, FileWriter fileWriter)
	{
		_BluetoothChat = bluetoothChat;
		_fileWriter = fileWriter;
		
		//_menuShowInput = (MenuItem) _BluetoothChat.findViewById(R.id.menuShowInput);
		
		_toggleGoButton  = (ToggleButton) _BluetoothChat.findViewById(R.id.toggleGo);
		_throttleBar = (SeekBar) _BluetoothChat.findViewById(R.id.throttleBar);
		_SVOLBar = (SeekBar) _BluetoothChat.findViewById(R.id.SVOLBar);
		_MCURBar = (SeekBar) _BluetoothChat.findViewById(R.id.MCURBar);
		_PCURBar = (SeekBar) _BluetoothChat.findViewById(R.id.PCURBar);
		_BCURBar = (SeekBar) _BluetoothChat.findViewById(R.id.BCURBar);
		_BRPMBar = (SeekBar) _BluetoothChat.findViewById(R.id.BRPMBar);
		_SRPMBar = (SeekBar) _BluetoothChat.findViewById(R.id.SRPMBar);
		_textView5 = (TextView) _BluetoothChat.findViewById(R.id.textView5);
		
		setupSeekBar(_throttleBar, 0);
		setupSeekBar(_SVOLBar, 0);
		setupSeekBar(_MCURBar, 0);
		setupSeekBar(_PCURBar, 0);
		setupSeekBar(_BCURBar, 0);
		setupSeekBar(_BRPMBar, 0);
		setupSeekBar(_SRPMBar, 0);
		
		_SVOLBar.setEnabled(false);
		_MCURBar.setEnabled(false);
		_PCURBar.setEnabled(false);
		_BCURBar.setEnabled(false);
		_BRPMBar.setEnabled(false);
		_SRPMBar.setEnabled(false);
		
		_throttleBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener()
		{
	        @Override
	        public void onProgressChanged(final SeekBar seekBar,final int progress,
	            final boolean fromUser)
	        {
	            if (fromUser)
	            {
	            	throttleChanged(progress);
	            }
	        }

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
		});
	
	}
	public void set_textView5(String message)
	{
		_textView5.setText(message);
	}
	public void menuShowInputClicked()
	{
		_BluetoothChat.displayPopUp("showing input");
	}
	private void setupSeekBar(SeekBar seekBar, int v) 
	{
		Resources res = _BluetoothChat.getResources();
		Drawable d = new AnimSeekBarDrawable(res, v < seekBar.getMax() / 2, seekBar.getMax());
		seekBar.setProgressDrawable(d);
		seekBar.setProgress(v);
	}
	private void throttleChanged(int value)
	{
		//set_MCURBar(value);
		//set_SVOLBar(value);
		_BluetoothChat.changeThrottle(value);
	}
	public void set_SRPMBar(int value)
	{
		_SRPMBar.setProgress(value);
	}
	public void set_BRPMBar(int value)
	{
		_BRPMBar.setProgress(value);
	}
	public void set_PCURBar(int value)
	{
		_PCURBar.setProgress(value);
	}
	public void set_BCURBar(int value)
	{
		_BCURBar.setProgress(value);
	}
	public void set_MCURBar(int value)
	{
		_MCURBar.setProgress(value);
	}
	public void set_SVOLBar(int value)
	{
		_SVOLBar.setProgress(value);
	}
	
	public void throttleChange(view view1)
	{
		_throttle = _throttleBar.getProgress();
		_BluetoothChat.sendMessage("THRO_"+format.format(_throttle));

		// do constant logging of throttle
		//_fileWriter.writeLine(Integer.toString(_throttle));
	}
	
	public void clickToggleGo(View view1)
	{
		//CharSequence text = _toggleGoButton.getText();
		if((_toggleGoButton.getText()).equals(_toggleGoButton.getTextOn()))
		{
			_BluetoothChat.sendMessage("OPER_1\r");
			_BluetoothChat.newRun();
		}
		else
		{
			_BluetoothChat.sendMessage("OPER_0\r");
			_BluetoothChat.closeFile();
		}
	}
	public void writeToDisplay(String line)
	{
		_BluetoothChat.mConversationArrayAdapter.add(line);
	}

}
