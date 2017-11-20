package lejos.music;

import java.io.IOException;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import network.BroadcastManager;


public class LauncherManager {
	/**
	 * Starts a robot, according to the pressed button, a different track will be played
	 * @param args
	 * @throws IOException
	 */
	private static float Time;
	 
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
	
	private static void playTrack(Track track) throws IOException {	
		LCD.drawString("Send ready to receiver", 0, 1);
		BroadcastManager.getInstance().broadcast("ready".getBytes());
		LCD.clear();
		LCD.drawString("Playing...", 0, 2);
		while(!track.isOver()) {
			Time = track.getTime();
			LCD.drawString(String.format("SendTime %.4f", Time), 0, 3);
			BroadcastManager.getInstance().broadcast(Float.toString(Time).getBytes());
			track.play();
		}
	}
}
