package de.dhbw.nxt;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class Robot {
	private LightSensor lightSensor;
	private UltrasonicSensor ultraSonicSensor;

	public Robot() {
		this.lightSensor = new LightSensor(SensorPort.S1);
		this.ultraSonicSensor = new UltrasonicSensor(SensorPort.S2);
		
		Motor.A.setSpeed(400);
		Motor.B.setSpeed(400);
	}
	
	public UltrasonicSensor getUltrasonicSensor() {
		return this.ultraSonicSensor;
	}
	
	public LightSensor getLightSensor() {
		return this.lightSensor;
	}
	
	private static int THRESHOLD = 550;
	
	public void driveToNextField() {
		if (this.lightSensor.readNormalizedValue() <= THRESHOLD) {
			this.driveForward();
			while (this.lightSensor.readNormalizedValue() < THRESHOLD) {
			}
		}
		
		this.driveForward();
		
		while (this.lightSensor.readNormalizedValue() > THRESHOLD) {
		}

		this.stopMovement();
	}
	
	public void stopMovement() {
		Motor.A.stop();
		Motor.B.stop();
	}
	
	public void driveForward() {
		Motor.A.forward();
		Motor.B.forward();
	}
}
