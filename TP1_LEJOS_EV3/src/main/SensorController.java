/**
 * 
 */
package main;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.Color;
import lejos.robotics.filter.MeanFilter;

/**
 * @author Michel
 *
 */
public class SensorController {
	
	private EV3UltrasonicSensor ultrasonicsensor;
	
	private EV3ColorSensor colorsensor;
	
	/*
	 * Constructeur par default
	 */
	public SensorController() {
		ultrasonicsensor = new EV3UltrasonicSensor(SensorPort.S2);
		LCD.clear();
		colorsensor = new EV3ColorSensor(SensorPort.S3);
		colorsensor.setFloodlight(true);
	}
	
	/*
	 * Fonction qui donne la distance retourne la distance en metre
	 *@return la distance entre la position du capteur et l'obstacle
	 */
	public float distance() {
		float[] sample = new float[1];
		ultrasonicsensor.getDistanceMode().fetchSample(sample, 0);
		return sample[0];
	}
	
	/*
	 * Fonction qui affiche le resultat
	 */
	public void displayDistance(float distance) {
		LCD.drawString(String.valueOf(distance), 0, 0);
	}
	
	
	/*
	 * Fonction qui permet de retourner la moyenne de n distance
	 * @param n nombre d'echantillon utilise pour calculer la distance
	 * @return la distance entre la position du capteur et l'obstacle
	 */
	public float distance(int n) {
		float[] sample = new float[n];
		MeanFilter dist = new MeanFilter(ultrasonicsensor.getDistanceMode(),n);
		dist.fetchSample(sample, 0);
		return sample[0];
	}
	
	/*
	 * Fonction qui determine la couleur
	 * @return une couleur 
	 */
	public int getColor() {
		/*SensorMode color = this.colorsensor.getColorIDMode();
		float[] colorSample = new float[color.sampleSize()];
		color.fetchSample(colorSample, 0);
		int colorID = (int)colorSample[0];
		return colorID;*/
		return this.colorsensor.getColorID();
	}
	
	/*
	 * Fonction qui permet de print les couleurs qu'il voit par le capteur couleurs
	 *@paral color couleur a afficher
	 */
	public void printColor(int color) {
		String colorName = "";
		switch(color) {
		case Color.NONE: 
			colorName = "None";
			break;
		
		case Color.BLACK:
			colorName = "Black";
			break;
			
		case Color.BLUE:
			colorName = "Blue";
			break;
			
		case Color.BROWN:
			colorName = "Brown";
			break;
			
		case Color.GREEN:
			colorName = "Green";
			break;
		
		case Color.RED:
			colorName = "Red";
			break;
			
		case Color.YELLOW:
			colorName = "Yellow";
			break;
			
		case Color.WHITE:
			colorName = "White";
			break;
		}
		
		LCD.drawString(colorName, 0, 0);
	}
	
	/*
	 * Fonction qui va permettre de shutdown les capteurs
	 */
	public void close() {
		this.colorsensor.close();
		this.ultrasonicsensor.close();
		LCD.clear();
	}
	
}
