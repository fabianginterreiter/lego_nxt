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
		this.pilot = new DifferentialPilot(5.6, 11.2, Motor.A, Motor.B);
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
		this.driveForward();

		while (this.lightSensor.readNormalizedValue() <= THRESHOLD) { }
		while (this.lightSensor.readNormalizedValue() > THRESHOLD) { }

		this.stopMovement();
	}
	
	public static int ROTATE_DEGREES = 90;

	public boolean onBlackLine() {
		return this.lightSensor.readNormalizedValue() <= THRESHOLD;
	}
	
	public void moveToLeftField() {
		while (!this.onBlackLine()) {
			this.driveForward();
		}
		this.stopMovement();
		
		this.pilot.travel(-5.5);
		this.turnLeft();
		
		if (!onBlackLine()) {
			while (!onBlackLine()) { this.driveForward(); }
		}
	
		while (onBlackLine()) { this.driveForward(); }
		while (!onBlackLine()) { this.driveForward(); };

		this.pilot.travel(-5.5);
	}

	public void moveToFrontField() {
		while (!this.onBlackLine()) {
			this.driveForward();
		}

		while (onBlackLine()) { this.driveForward(); }
		while (!onBlackLine()) { this.driveForward(); };

		this.pilot.travel(-5.5);
	}
	
	public void moveToBackField() {
		while (!this.onBlackLine()) {
			this.driveForward();
		}
		this.stopMovement();
		
		this.pilot.travel(-5.5);
		this.turnLeft();
		this.turnLeft();
		
		if (!onBlackLine()) {
			while (!onBlackLine()) { this.driveForward(); }
		}
		
		while (onBlackLine()) { this.driveForward(); }
		while (!onBlackLine()) { this.driveForward(); };

		this.pilot.travel(-5.5);
	}
	
	public void moveToRightField() {
		while (!this.onBlackLine()) {
			this.driveForward();
		}
		this.stopMovement();
		
		this.pilot.travel(-5.5);
		this.turnRight();
		
		if (!onBlackLine()) {
			while (!onBlackLine()) { this.driveForward(); }
		}
	
		while (onBlackLine()) { this.driveForward(); }
		while (!onBlackLine()) { this.driveForward(); };

		this.pilot.travel(-5.5);
	}
	
	public void turnRight() {
		this.pilot.rotate(-ROTATE_DEGREES);
	}
	
	public void turnLeft() {
		this.pilot.rotate(ROTATE_DEGREES);
	}
	
	public void stopMovement() {
		this.pilot.stop();
	}
	
	public void driveForward() {
		this.pilot.forward();
	}
}
