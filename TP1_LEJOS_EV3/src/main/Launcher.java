package main;

import lejos.utility.Delay;

/**
 * @author Michel
 *
 */

public class Launcher {

	public static void main(String[] args) {
		
		RobotController robot = new RobotController();
		SensorController sensor = new SensorController();
		RobotSuiveur suiveur = new RobotSuiveur();
		int D = 20;
		float a = (float) 3;
		float Ts = (float) 0.4;
		float v_max = 500;
		suiveur.FollowLeaderV0(D, a, Ts, v_max, robot, sensor);
		suiveur.FollowLeaderV1(D, a, Ts, v_max, robot, sensor);
		suiveur.FollowLeaderV2(D, a, Ts, v_max, robot, sensor);
	}

}
