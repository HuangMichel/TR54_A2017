package main;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

/**
 * @author Michel
 *
 */

public class RobotController {

	private RegulatedMotor motor1;
	private RegulatedMotor motor2;

	 
	/*
	 * Default construteur
	 */
	RobotController() {
		motor1 = new EV3LargeRegulatedMotor(MotorPort.B);
		motor2 = new EV3LargeRegulatedMotor(MotorPort.C);
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
	 * Fonction qui set la vitesse du moteur
	 * @parametre int speed
	 */
	public void setMotorSpeed(int speed) {
		motor1.setSpeed(speed);
		motor2.setSpeed(speed);
	}
	
}