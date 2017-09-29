package main;

/**
 * @author Michel
 *
 */

public class main {

	public static void main(String[] args) {
		
		RobotController robot = new RobotController();
		SensorController sensor = new SensorController();
		
		while(sensor.getColor()== 1) {
			robot.setMotorSpeed(20);
			robot.forward();
			if(sensor.getColor() == 0) {
				robot.rotate(360);
			}
		}
		robot.stop();
	}

}
