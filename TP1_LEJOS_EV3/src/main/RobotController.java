package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.Color;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

/**
 * @author Michel
 *
 */

public class RobotController {

	private RegulatedMotor motor1;
	private RegulatedMotor motor2;
	 
	/*
	 * Construteur par default
	 */
	RobotController() {
		this.motor1 = new EV3LargeRegulatedMotor(MotorPort.B);
		this.motor2 = new EV3LargeRegulatedMotor(MotorPort.C);
	}

	/*
	 * Fonction qui permet de faire avancer le robot vers l'avant
	 */
	public void forward() {
		this.motor1.forward();
		this.motor2.forward();
	}

	/*
	 * Fonction qui permet de reculer le robot
	 */
	public void backward() {
		this.motor1.backward();
		this.motor2.backward();
	}
	
	/*
	 * Fonction qui donne la vitesse max du moteur
	 * @return speed max
	 */
	public float getSpeed() {
		return this.motor1.getMaxSpeed();
	}
	
	/*
	 * Fonction qui permet de tourner le robot sur lui meme
	 * 
	 * @param angle de rotation en radian
	 */
	public void rotate(float angle) {
		float degree = (float) ((angle * 180) / (Math.PI));
		this.motor1.rotate((int) degree, true);
		this.motor2.rotate((int) -degree, true);
	}
	
	/*
	 * Fonction qui permet de tourner a droite
	 * 
	 * @param angle de rotation en radian
	 */
	public void rotateRight(float angle) {
		float degree = (float) ((angle * 180) / (Math.PI));
		this.motor1.rotate((int) degree, true);
		this.motor2.rotate((int) -degree/2, true);
	}
	
	/*
	 * Fonction qui permet de tourner a gauche
	 * 
	 * @param angle de rotation en radian
	 */
	public void rotateLeft(float angle) {
		float degree = (float) ((angle * 180) / (Math.PI));
		this.motor1.rotate((int) -degree/2, true);
		this.motor2.rotate((int) degree, true);
	}

	/*
	 * Fonction qui stop le robot
	 */
	public void stop() {
		this.motor1.stop(true); /* Parametre true stopper immediatement pour passer a la ligne suivante */
		this.motor2.stop();
	}

	
	/*
	 * Fonction qui set la vitesse du moteur
	 * @param speed1 set la vitesse du motor1
	 * @param speed2 set la vitesse du motor2
	 */
	public void setMotorSpeed(int speed1, int speed2) {
		this.motor1.setSpeed(speed1);
		this.motor2.setSpeed(speed2);
		forward();
	}
	
	/*
	 * Fonction qui va permettre de shutdown les motors du robots
	 */
	public void close() {
		this.motor1.close();
		this.motor2.close();
	}
	
	/*
	 * Fonction qui permet de sauvergarder la distance dans un fichier csv
	 * @param d distance
	 * @param filePath nom du fichier ou du chemin
	 */
	public void saveCSVfile(float d, String filePath) {
		File csvFile = new File(filePath);
		
		//On regarde si le fichier existe
		if(!csvFile.exists()) {
			try {
				//Creation du fichier
				csvFile.createNewFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			// On creer un ecrivain
			FileWriter WcsvFile = new FileWriter(filePath, true);
			WcsvFile.write(Float.toString(d) + "\n");
			WcsvFile.flush();
			WcsvFile.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Fonction qui permet de d'avancer jusqu'a qu'il trouve la couleur bleu
	 * @param color capteur couleur du sensorController
	 */
	public void moveToColor(SensorController color) {
		boolean bool = true;
		int actualColor;
		while(bool) {
			actualColor = color.getColor();
			if(actualColor != Color.BLUE) {
				forward();
			}else{
				bool = false;
				stop();
			}
		}
	}
	
	/*
	 * Fonction qui permet de suivre un trac¨¦ de ligne noir
	 * @param sensor capteur couleur
	 */
	public void line_follower(SensorController sensor) {
		setMotorSpeed(500, 500);
		int isWhite = 0;
		int angle = 0;
		boolean bool = true;
		while(bool) {
			int color = sensor.getColor();
			sensor.printColor(color);
			if(color != Color.BLACK) {
				if(isWhite != 0) {
					angle += 25;
				}
				setMotorSpeed(250, 250);
				rotateRight(120+angle);
				Delay.msDelay(1500);
				color = sensor.getColor();
				sensor.printColor(color);
				if(color!= Color.BLACK) {
					rotateLeft(140+angle);
					Delay.msDelay(1500);
					isWhite++;
				}
			}else{
				isWhite = 0;
				angle = 0;
				sensor.printColor(color);
				setMotorSpeed(500, 500);
			}
			
			if(isWhite == 20) {
				bool = false;
			}
		}
		
		sensor.close();
		close();
	}
	
}