package de.dhbw.nxt;

import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
import lejos.robotics.navigation.DifferentialPilot;

public class Navigator {
	enum Direction {
		NORTH, SOUTH, EAST, WEST
	}

	public DifferentialPilot pilot;
	private Direction direction;
	private Robot robot;
	public static int ROTATE_DEGREES = 90;
	static int THRESHOLD = 50;

	public Navigator(Robot robot) {
		this.pilot = new DifferentialPilot(5.6, 11.2, Motor.A, Motor.B);
		this.pilot.setTravelSpeed(12);
		this.pilot.setRotateSpeed(35);
		
		this.robot = robot;
		
		this.direction = Direction.NORTH;
	}
	
	public DifferentialPilot getPilot() {
		return this.pilot;
	}

	public boolean onBlackLine() {
		return this.getLightSensor().readValue() <= Navigator.THRESHOLD;
	}
	
	public LightSensor getLightSensor() {
		return this.robot.getLightSensor();
	}
	
	public UltrasonicSensor getUltrasonicSensor() {
		return this.robot.getUltrasonicSensor();
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
		pilot.travel(-7);
	}

	public void waitForFreeField() {
	    this.stopMovement();
	    
	    try {
	        float lastRange = this.getUltrasonicSensor().getRange();
	        float newRange = lastRange;
	        
	        if (newRange < 30)
	        {
	        	do {
	                LCD.clear();
	                LCD.drawString(newRange + "", 0, 0);
	                
	                lejos.nxt.Sound.beep();
	                Thread.sleep(5000);
	
	                newRange = this.getUltrasonicSensor().getRange();
	            } while (newRange < 30 && Math.abs(lastRange - newRange) > 3);
	        }
	    } catch (InterruptedException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}

	public void moveToNextField() {
		while (!this.onBlackLine()) {
			this.driveForward();
		}
		this.stopMovement();
	
		this.waitForFreeField();
	
		while (this.onBlackLine()) {
			this.driveForward();
		}
		while (!this.onBlackLine()) {
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
		pilot.rotate(-Navigator.ROTATE_DEGREES);
	
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
		pilot.rotate(Navigator.ROTATE_DEGREES);
	
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
		pilot.stop();
	}

	public void driveForward() {
		pilot.forward();
	}
}