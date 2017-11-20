package network;

import java.nio.ByteBuffer;
import java.util.Collection;

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
		
		String m = new String(message);
		if(m.contentEquals("ready")) {
			this.ready =  true;
		}else {
			this.data = ByteBuffer.wrap(message).getFloat();
		}
		
	}

	@Override
	public void onBroadcastReceived2(Collection<Float> message) {
		// null
		
	}
}
