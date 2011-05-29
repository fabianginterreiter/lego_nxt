package de.dhbw.nxt;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {
	private LightSensor lightSensor;
	private UltrasonicSensor ultraSonicSensor;
	private DifferentialPilot pilot;

	public Robot() {
		this.lightSensor = new LightSensor(SensorPort.S1);
		this.ultraSonicSensor = new UltrasonicSensor(SensorPort.S2);
		this.pilot = new DifferentialPilot(5.6f, 11.2f, Motor.A, Motor.B);
		
		Motor.A.setSpeed(400);
		Motor.B.setSpeed(400);
	}
	
	public UltrasonicSensor getUltrasonicSensor() {
		return this.ultraSonicSensor;
	}
	
	public LightSensor getLightSensor() {
		return this.lightSensor;
	}
	
	public DifferentialPilot getPilot() {
		return this.pilot;
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
		this.pilot.stop();
	}
	
	public void driveForward() {
		this.pilot.forward();
	}
}
