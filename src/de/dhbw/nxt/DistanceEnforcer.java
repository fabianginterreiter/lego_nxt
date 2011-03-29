package de.dhbw.nxt;

import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;

public class DistanceEnforcer implements Behavior {

	boolean stopMovement = false;
	UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
	
	public DistanceEnforcer() {

	}
	
	@Override
	public void action() {
		System.out.println("DistanceEnforcer action!");
		Motor.A.stop();
		Motor.B.stop();
		try{Thread.sleep(2000);}catch(Exception e) {}
	}

	@Override
	public void suppress() {
		System.out.println("DistanceEnforcer suppressed!");
		try{Thread.sleep(2000);}catch(Exception e) {}
	}

	@Override
	public boolean takeControl() {
		if (sensor.getDistance() < 30) {
			return true;
		}
		return false;
	}

}
