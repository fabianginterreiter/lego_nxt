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

		Map map = new Map(3, 3);
		map.tileAt(1, 1).setNotPassable();

		int[] currentPos = { 0, 0 };
		
		int[][] path = map.findPath(0, 0, 3, 3);
		for(int i = 0; i < path.length; i++) {
			if (currentPos[0] < path[i][0]) {
				robot.moveToEastField();
			} else if (currentPos[0] > path[i][0]) {
				robot.moveToWestField();
			}
			
			if (currentPos[1] < path[i][1]) {
				robot.moveToSouthField();
			} else if (currentPos[1] > path[i][1]) {
				robot.moveToNorthField();
			}
			
			currentPos = path[i];
		}
		
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
