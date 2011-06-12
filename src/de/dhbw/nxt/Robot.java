package de.dhbw.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import de.dhbw.nxt.Navigator.Direction;

public class Robot {
	UltrasonicSensor ultraSonicSensor;
	private Navigator navigator;
	Direction direction;
	private LightSensor lightSensor;
	private Map map;
	private int[] currentPos;

	public Robot(Map map) {
		this.map = map;
		this.currentPos = new int[] { 0, 0 };
		
		this.lightSensor = new LightSensor(SensorPort.S1);
		this.lightSensor.setFloodlight(true);
		
		this.ultraSonicSensor = new UltrasonicSensor(SensorPort.S4);

		this.navigator = new Navigator(this);
	}

	public UltrasonicSensor getUltrasonicSensor() {
		return this.ultraSonicSensor;
	}

	public LightSensor getLightSensor() {
		return this.lightSensor;
	}

	public void moveTo(int x, int y) {
		int[][] path = map.findPath(0, 0, 1, 2);

		for(int i = 0; i < path.length; i++) {
			if (this.currentPos[0] < path[i][0]) {
				this.navigator.moveToWestField();
			} else if (this.currentPos[0] > path[i][0]) {
				this.navigator.moveToEastField();
			}
			
			if (this.currentPos[1] < path[i][1]) {
				this.navigator.moveToSouthField();
			} else if (this.currentPos[1] > path[i][1]) {
				this.navigator.moveToNorthField();
			}
			
			this.currentPos = path[i];
		}
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
