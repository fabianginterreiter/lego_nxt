package de.dhbw.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.Motor;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Robot robot = new Robot();

		LightSensor light = robot.getLightSensor();
		light.setFloodlight(true);
		
		LCD.drawString("Light %: ", 0, 0);
		
		LCD.drawString("Press LEFT", 0, 2);
		LCD.drawString("to quit", 0, 3);
		while (!Button.LEFT.isPressed()) {
			LCD.drawInt(light.readNormalizedValue(), 4, 9, 0);
		}
		
		robot.driveToNextField();
		robot.driveToNextField();
	}

}
