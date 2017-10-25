package main;

import lejos.hardware.lcd.LCD;

public class RobotSuiveur{
	
	/*
	 * Fonction qui suit un robot leader version 0 qui s'appelle Tout ou Rien
	 * @param v_max vitesse max de d¨¦but
	 * @param robot issue de la classe RobotController
	 * @param sensor issue de la classe SensorController
	 */
	public void FollowLeaderV0(float v_max, RobotController robot, SensorController sensor) {
		boolean bool = true;
		float distance;
		String dataSpeedCSV = "dataSpeedV0.csv";
		String dataDistanceCSV = "dataDistanceV0.csv";
		robot.setMotorSpeed((int)v_max, (int)v_max);
		float v;
		
		while(bool) {
			distance = sensor.distance();
			if(distance>0.15) {
				LCD.drawString("Distance " + distance, 0, 0);
				v = v_max/2;
				robot.setMotorSpeed((int)v, (int)v);
			}else {
				LCD.drawString("distance < 0.15m", 0, 2);
				robot.stop();
				v = robot.getSpeed();
			}
			robot.saveCSVfile(v, dataSpeedCSV);
			robot.saveCSVfile(distance, dataDistanceCSV);
		}
		
		robot.stop();
		LCD.drawString("Stop totale du robot", 0, 4);
		robot.close();
		sensor.close();
	}
	
	/*
	 * Fonction qui suit un robot leader version 1
	 * @param D distance de r¨¦f¨¦rence entre 2 robot
	 * @param a parametre de calcul du pourcentage pour appliquer sur la vitesse du robot
	 * @param Ts temps du cycle
	 * @param v_max vitesse max de d¨¦but
	 * @param robot issue de la classe RobotController
	 * @param sensor issue de la classe SensorController
	 */
	public void FollowLeaderV1(float D, float a, float Ts, float v_max, RobotController robot, SensorController sensor) {
		boolean bool = true;
		float distance;
		String dataSpeedCSV = "dataSpeedV1.csv";
		String dataDistanceCSV = "dataDistanceV1.csv";
		float v = v_max;
		float pourcentage;
		
		robot.setMotorSpeed((int)v, (int)v);
		while(bool) {
			distance = sensor.distance();
			pourcentage = this.V1(a, D, Ts, distance);
			v = pourcentage * v_max;
			robot.saveCSVfile(v, dataSpeedCSV);
			robot.saveCSVfile(distance, dataDistanceCSV);
			LCD.drawString("Distance " + distance, 0, 0);
			LCD.drawString("Vitesse " + v, 0, 2);
			LCD.drawString("% " + pourcentage, 0, 4);
			robot.setMotorSpeed((int)v, (int)v);
		}
		
		robot.stop();
		LCD.drawString("Stop totale du robot", 0, 4);
		robot.close();
		sensor.close();
	}
	
	/*
	 * Fonction qui suit un robot leader version 3
	 * @param D distance de r¨¦f¨¦rence entre 2 robot
	 * @param a parametre de calcul du pourcentage pour appliquer sur la vitesse du robot
	 * @param Ts temps du cycle
	 * @param v_max vitesse max de d¨¦but
	 * @param robot issue de la classe RobotController
	 * @param sensor issue de la classe SensorController
	 */
	public void FollowLeaderV2(float D, float a, float Ts, float v_max, RobotController robot, SensorController sensor) {
		boolean bool = true;
		float distance;
		float v;
		float pourcentage;
		
		String dataSpeedCSV = "dataSpeedV2.csv";
		String dataDistanceCSV = "dataDistanceV2.csv";
		v = v_max;
		robot.setMotorSpeed((int)v, (int)v);
		
		while(bool) {
			distance = sensor.distance();
			LCD.drawString("Distance " + distance, 0, 0);

			pourcentage = this.V2(a, D, Ts, distance, v);
			LCD.drawString("%" + pourcentage, 0, 2);
			v = pourcentage * v_max;
			robot.setMotorSpeed((int)v, (int)v);
			LCD.drawString("Chgmt vitesse " + v, 0, 4);
			
			robot.saveCSVfile(v, dataSpeedCSV);
			robot.saveCSVfile(distance, dataDistanceCSV);

		}
		robot.stop();
		LCD.drawString("Stop totale du robot", 6, 6);
		robot.close();
		sensor.close();
	}

	/*
	 * Fonction qui calcule la vitesse ¨¤ 1 point
	 * @param D distance de r¨¦f¨¦rence entre 2 robot
	 * @param a parametre de calcul du pourcentage pour appliquer sur la vitesse du robot
	 * @param Ts temps du cycle
	 * @param distance, la distance entre les deux robots ¨¤ un instant t donnee
	 * @return pourcentage
	 */
	public float V1(float a, float D, float Ts, float distance) {
		return Math.max(Math.min(50, a * (distance - D)), 0);
	}
	
	/*
	 * Fonction qui calcule la vitesse ¨¤ 1 point
	 * @param D distance de r¨¦f¨¦rence entre 2 robot
	 * @param a parametre de calcul du pourcentage pour appliquer sur la vitesse du robot
	 * @param Ts temps du cycle
	 * @param distance, la distance entre les deux robots ¨¤ un instant t donnee
	 * @param speed la vitesse du robot ¨¤ un instant t donnee
	 * @return pourcentage
	 */
	public float V2(float a, float D, float Ts, float distance, float speed) {
		return Math.min(Math.max((float) (2.5*(distance-20)), Math.min(Math.max(a*(distance-D),0), speed)),50);
	}
}
