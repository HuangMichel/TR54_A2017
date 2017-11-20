package network;

import java.util.Collection;

/**
 * Broadcast listener interface
 * @author Alexandre Lombard
 */
public interface BroadcastListener {
	/**
	 * Centralized mode
	 * Triggered on broadcast received
	 * @param message the raw message
	 */
	public void onBroadcastReceived(byte[] message);
	
	/**
	 * Decentralized mode
	 * Triggered on broadcast received
	 * @param message
	 */
	public void onBroadcastReceived2(Collection<Float> message);
}
