package com.example.android.BluetoothChat;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GPS_Getter 
{
	BluetoothChat _BluetoothChat;
	
	public GPS_Getter(BluetoothChat bluetoothChat)
	{
		_BluetoothChat = bluetoothChat;
		bluetoothChat.displayLine("GPS started.");
		setUpGPS();
	}
	private void setUpGPS()
	{
		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) _BluetoothChat.getSystemService(Context.LOCATION_SERVICE);

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() 
		{
		    public void onLocationChanged(Location location) 
		    {
		      // Called when a new location is found by the network location provider.
		      //makeUseOfNewLocation(location);
		    	_BluetoothChat.displayLine("Location: " +location.getLatitude()+", "+location.getLongitude());
		    	_BluetoothChat.displayLine("Speed: "+location.getSpeed());
		    }

		    public void onStatusChanged(String provider, int status) {}

		    public void onProviderEnabled(String provider) {}

		    public void onProviderDisabled(String provider) {}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) 
			{
				// TODO Auto-generated method stub
				
			}
		  };

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

		//double location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER).getLatitude();
		//String networkProvider = LocationManager.NETWORK_PROVIDER;
	}

}
