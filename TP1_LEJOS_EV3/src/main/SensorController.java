/**
 * 
 */
package main;

import java.io.File;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.filter.MeanFilter;

/**
 * @author Michel
 *
 */
public class SensorController {
	
	private EV3UltrasonicSensor ultrasonicsensor;
	
	private EV3ColorSensor colorsensor;
	
	public SensorController() {
		ultrasonicsensor = new EV3UltrasonicSensor(SensorPort.S2);
		LCD.clear();
		colorsensor = new EV3ColorSensor(SensorPort.S2);
	}
	
	/*
	 * Fonction qui donne la distance retourne la distance en metre
	 */
	public float distance() {
		float[] sample = new float[1];
		ultrasonicsensor.getDistanceMode().fetchSample(sample, 0);
		return sample[0];
	}
	
	/*
	 * Fonction qui affiche le resultat de la fontion distance
	 */
	public void displayDistance() {
		LCD.drawString(String.valueOf(distance()), 2, 2);
	}
	
	/*
	 * Fonction qui permet de enrengistrer les distances dans un fichier csv
	 */
	public void saveCSV() {
		
	}
	
	/*
	 * Fonction qui permet de retourner la moyenne de n distance
	 * parametre entier n echantillons
	 */
	public float distance(int n) {
		float[] sample = new float[n];
		MeanFilter dist = new MeanFilter(ultrasonicsensor.getDistanceMode(),n);
		dist.fetchSample(sample, 0);
		return sample[0];
	}
	
	/*
	 * Fonction qui determine la couleur
	 */
	public int getColor() {
		return this.colorsensor.getColorID();
	}
	
	
	
}
