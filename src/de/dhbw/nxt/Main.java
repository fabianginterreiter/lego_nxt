package de.dhbw.nxt;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Map map = new Map(3, 3);
		map.tileAt(1, 1).setNotPassable();

		Robot robot = new Robot(map);
		robot.calibrate();

		robot.moveTo(2, 2);
	}
}
