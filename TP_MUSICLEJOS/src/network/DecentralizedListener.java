package network;

import java.util.Collection;

import lejos.hardware.lcd.LCD;

public class DecentralizedListener implements BroadcastListener{

	private float data;
	
	public DecentralizedListener() {
		this.data = 0;
	}

	public float getData() {
		return data;
	}
	
	public void setData(float d) {
		this.data = d;
	}
	
	@Override
	public void onBroadcastReceived(byte[] message) {
		//null
	}

	@Override
	public void onBroadcastReceived2(Collection<Float> message) {
		float average = 0;
		for(Float f : message) {
			average = average + f;
		}
		data = average / message.size();
	}

}
