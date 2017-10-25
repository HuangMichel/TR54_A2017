package main;

/**
 * @author Michel
 *
 */

public class LauncherFollower {

	public static void main(String[] args) {
		
		RobotController robot = new RobotController();
		SensorController sensor = new SensorController();
		RobotSuiveur suiveur = new RobotSuiveur();
		float D = (float) 0.2;
		float a = (float) 2;
		float Ts = (float) 300;
		float v_max = 500;
		//suiveur.FollowLeaderV0(v_max, robot, sensor);
		//suiveur.FollowLeaderV1(D, a, Ts, v_max, robot, sensor);
		suiveur.FollowLeaderV2(D, a, Ts, v_max, robot, sensor);
	}

}
