package main;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.filter.MeanFilter;

/**
 * @author Michel
 *
 */

public class RobotController {

	private RegulatedMotor motor1;
	private RegulatedMotor motor2;

	private EV3UltrasonicSensor ultrasonicsensor;
	
	private EV3ColorSensor colorsensor;
	 
	/*
	 * Default construteur
	 */
	RobotController() {
		motor1 = new EV3LargeRegulatedMotor(MotorPort.B);
		motor2 = new EV3LargeRegulatedMotor(MotorPort.C);
		ultrasonicsensor = new EV3UltrasonicSensor(SensorPort.S2);
		LCD.clear();
		//colorsensor = new EV3ColorSensor(SensorPort.S);
	}

	/*
	 * Fonction qui permet de faire avancer le robot vers l'avant
	 */
	public void forward() {
		this.motor1.forward();
		this.motor2.forward();
	}

	/*
	 * Fonction qui permet de tourner le robot
	 * 
	 * @parametre angle de rotation en radian
	 */
	public void rotate(float angle) {
		float degree = (float) ((angle * 360) / (2 * Math.PI));
		this.motor1.rotate((int) degree);
		this.motor2.rotate((int) -degree);
	}

	/*
	 * Fonction qui stop le robot
	 */
	public void stop() {
		this.motor1.stop(true); /* Parametre true stopper immediatement pour passer a la ligne suivante */
		this.motor2.stop(true);
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
	
	/*
	 * Fonction qui permet de s'arreter quand le robot rencontre la couleur qui veut
	 *@parametre int color (0-7, NONE, BLACK, BLUE, GREEN, YELLOW, RED, WHITE, BROWN)
	 */
	public void meetColor(int color) {
		if(getColor() == color) {
			stop();
		}
	}
	
	/*
	 * Fonction qui set la vitesse du moteur
	 * @parametre int speed
	 */
	public void setMotorSpeed(int speed) {
		motor1.setSpeed(speed);
		motor2.setSpeed(speed);
	}
	
	/*
	 * Fonction qui va chercher la ligne de couleur
	 * @parametre int color
	 */
	public void goMeetColor(int color) {
		while(getColor() != color) {
			setMotorSpeed(20);
			forward();
			meetColor(color);
		}
	}
}