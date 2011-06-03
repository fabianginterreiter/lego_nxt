package de.dhbw.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Robot {
	private LightSensor lightSensor;
	private UltrasonicSensor ultraSonicSensor;
	private DifferentialPilot pilot;
	private Direction direction;

	private static int THRESHOLD = 50;

	public static int ROTATE_DEGREES = 90;

	enum Direction {
		NORTH, SOUTH, EAST, WEST
	};

	public Robot() {
		this.lightSensor = new LightSensor(SensorPort.S1);
		this.lightSensor.setFloodlight(true);

		this.ultraSonicSensor = new UltrasonicSensor(SensorPort.S2);

		this.pilot = new DifferentialPilot(5.6, 11.2, Motor.A, Motor.B);
		this.pilot.setTravelSpeed(12);
		this.pilot.setRotateSpeed(35);

		this.direction = Direction.NORTH;
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

	public boolean onBlackLine() {
		return this.lightSensor.readValue() <= THRESHOLD;
	}
	
	public void moveToNorthField() {
		switch (this.direction) {
		case NORTH:
			this.moveToFrontField();
			break;
		case SOUTH:
			this.moveToBackField();
			break;
		case EAST:
			this.moveToLeftField();
			break;
		case WEST:
			this.moveToRightField();
			break;
		default:
			break;
		}
	}

	public void moveToSouthField() {
		switch (this.direction) {
		case SOUTH:
			this.moveToFrontField();
			break;
		case NORTH:
			this.moveToBackField();
			break;
		case WEST:
			this.moveToLeftField();
			break;
		case EAST:
			this.moveToRightField();
			break;
		default:
			break;
		}
	}

	public void moveToEastField() {
		switch (this.direction) {
		case SOUTH:
			this.moveToLeftField();
			break;
		case NORTH:
			this.moveToRightField();
			break;
		case WEST:
			this.moveToBackField();
			break;
		case EAST:
			this.moveToFrontField();
			break;
		default:
			break;
		}
	}

	public void moveToWestField() {
		switch (this.direction) {
		case SOUTH:
			this.moveToRightField();
			break;
		case NORTH:
			this.moveToLeftField();
			break;
		case WEST:
			this.moveToFrontField();
			break;
		case EAST:
			this.moveToBackField();
			break;
		default:
			break;
		}
	}

	public void repositionInField() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.pilot.travel(-7);
	}

	public void moveToNextField() {
		while (!onBlackLine()) {
			this.driveForward();
		}
		while (onBlackLine()) {
			this.driveForward();
		}
		while (!onBlackLine()) {
			this.driveForward();
		}
		this.stopMovement();
	}
	
	public void moveToLeftField() {
		while (!this.onBlackLine()) {
			this.driveForward();
		}

		this.stopMovement();
		this.repositionInField();

		this.turnLeft();

		this.moveToNextField();
		this.repositionInField();
	}

	public void moveToFrontField() {
		this.moveToNextField();
		this.repositionInField();
	}

	public void moveToBackField() {
		while (!this.onBlackLine()) {
			this.driveForward();
		}

		this.stopMovement();
		this.repositionInField();

		this.turnLeft();
		this.turnLeft();

		this.moveToNextField();
		this.repositionInField();
	}

	public void moveToRightField() {
		while (!this.onBlackLine()) {
			this.driveForward();
		}

		this.stopMovement();
		this.repositionInField();

		this.turnRight();

		this.moveToNextField();
		this.repositionInField();
	}

	public void turnRight() {
		this.pilot.rotate(-ROTATE_DEGREES);

		switch (this.direction) {
		case NORTH:
			this.direction = Direction.EAST;
			break;
		case SOUTH:
			this.direction = Direction.WEST;
			break;
		case EAST:
			this.direction = Direction.SOUTH;
			break;
		case WEST:
			this.direction = Direction.NORTH;
			break;
		default:
			break;
		}

	}

	public void turnLeft() {
		this.pilot.rotate(ROTATE_DEGREES);

		switch (this.direction) {
		case NORTH:
			this.direction = Direction.WEST;
			break;
		case SOUTH:
			this.direction = Direction.EAST;
			break;
		case EAST:
			this.direction = Direction.NORTH;
			break;
		case WEST:
			this.direction = Direction.SOUTH;
			break;
		default:
			break;
		}
	}

	public void stopMovement() {
		this.pilot.stop();
	}

	public void driveForward() {
		this.pilot.forward();
	}

	public void calibrate() {
		LCD.drawString("Calibrating low", 0, 2);
		LCD.drawString("Press LEFT", 0, 2);
		Button.LEFT.waitForPressAndRelease();
		this.lightSensor.calibrateLow();

		LCD.clear();

		LCD.drawString("Calibrating high", 0, 2);
		LCD.drawString("Press LEFT", 0, 2);
		Button.LEFT.waitForPressAndRelease();
		this.lightSensor.calibrateHigh();

		LCD.clear();

		LCD.drawString("Light %: ", 0, 0);

		LCD.drawString("Press LEFT", 0, 2);
		LCD.drawString("to quit", 0, 3);
		while (!Button.LEFT.isPressed()) {
			LCD.drawInt(this.lightSensor.readValue(), 4, 9, 0);
		}
	}
}
