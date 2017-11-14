package network;

import java.nio.ByteBuffer;

import lejos.hardware.lcd.LCD;

public class CentralisedListener implements BroadcastListener{
	
	private float data;
	private boolean ready;
	
	public CentralisedListener() {
		this.ready = false;
		this.data = 0;
	}

	public float getData() {
		return data;
	}
	
	public boolean getReady() {
		return ready;
	}
	
	public void setData(float d) {
		this.data = d;
	}
	
	
	public void setReady(boolean r) {
		this.ready = r;
	}
	
	@Override
	public void onBroadcastReceived(byte[] message) {
		//data = ByteBuffer.wrap(message).getFloat();
		//LCD.drawString(String.valueOf(data), 0, 0);
		String m = new String(message);
		if(m.contentEquals("ready")) {
			this.ready = true;
			LCD.drawString(m, 0, 0);
		}else {
			data = Float.parseFloat(m);
			LCD.drawString(String.valueOf(data), 0, 1);
		}
	}
}
