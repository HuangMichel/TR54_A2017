package main;

import lejos.utility.Delay;

public class RobotLeader extends RobotController{
	public RobotLeader() {
		super();
		this.setMotorSpeed(500, 500);
	}
	
	public void move() {
		while(true) {
			this.forward();
			Delay.msDelay(5000);
			this.stop();
			Delay.msDelay(5000);
		}
	}
}
