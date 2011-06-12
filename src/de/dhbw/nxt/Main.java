package de.dhbw.nxt;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Map map = new Map(3, 3);

		Robot robot = new Robot(map);
		robot.calibrate();

//		map.tileAt(1, 0).setTemporarilyNotPassable();

//		robot.moveTo(2, 0);
//		robot.moveTo(0, 0);
//		robot.moveTo(2, 0);

		Job j = new Job(2, 2, 0, 1);
		robot.getJobs().addJob(j);
		
		robot.processQueue();
	}
}
