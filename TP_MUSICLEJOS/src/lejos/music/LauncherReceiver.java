package lejos.music;

import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import network.BroadcastReceiver;
import network.CentralisedListener;

public class LauncherReceiver {
	/**
	 * Starts a robot, according to the pressed button, a different track will be played
	 * @param args
	 * @throws IOException
	 */
	private static CentralisedListener listener = new CentralisedListener();
	private static float firstTime; //le temps qu'il recoit
	private static float secondTime;//le temps qui joue
	private static float dT = 0.1f;
	
	public static void main(String[] args) throws IOException {
		
		final TrackReader trackReader = new TrackReader();
		
		final Track violin1 = trackReader.read(LauncherReceiver.class.getResourceAsStream("/lejos/music/samples/score01/violin1.txt"));
		final Track violin2 = trackReader.read(LauncherReceiver.class.getResourceAsStream("/lejos/music/samples/score01/violin2.txt"));
		final Track violoncello = trackReader.read(LauncherReceiver.class.getResourceAsStream("/lejos/music/samples/score01/violoncello.txt"));
		final Track contrabass = trackReader.read(LauncherReceiver.class.getResourceAsStream("/lejos/music/samples/score01/contrabass.txt"));
		
		violin1.setBpm(90);
		violin2.setBpm(90);
		violoncello.setBpm(90);			
		contrabass.setBpm(90);
		
		BroadcastReceiver.getInstance().addListener(listener);
		
		final int button = Button.waitForAnyPress();
		
		if(button == Button.ID_UP) {
			playTrack(violin1);
		} else if(button == Button.ID_RIGHT) {
			playTrack(violin2);
		} else if(button == Button.ID_LEFT) {
			playTrack(violoncello);
		} else if(button == Button.ID_DOWN) {
			playTrack(contrabass);
		}
	}
	
	private static void playTrack(Track track) {	
		
		LCD.clear();
		LCD.drawString("Playing...", 0, 2);
		while(!track.isOver()) {
			secondTime = track.getTime();
			LCD.drawString(String.format("Timeplay %.4f", secondTime), 0, 3);
			firstTime= listener.getData();
			if(firstTime == 0) {
				LCD.drawString("Waiting ready by the manager", 0, 4);
			}else {
				LCD.drawString(String.format("Timercve %.4f", firstTime), 0, 5);
				if((Math.abs(firstTime - secondTime) > dT)) {
					track.setTime(firstTime);
				}
			}
			track.play();
		}
		
	}
}
