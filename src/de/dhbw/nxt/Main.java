package de.dhbw.nxt;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Robot robot = new Robot();
		robot.calibrate();

		robot.moveToNorthField();
		robot.moveToNorthField();

		robot.moveToWestField();
		robot.moveToWestField();
		
		robot.moveToSouthField();
		robot.moveToSouthField();

		robot.moveToEastField();
		robot.moveToEastField();

		robot.moveToFrontField();
		robot.moveToWestField();
		
//		robot.moveToFrontField();
//		robot.moveToLeftField();
//		robot.moveToRightField();
//		robot.moveToBackField();

		
	}

}
