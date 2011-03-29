package de.dhbw.nxt;

import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		new Arbitrator(new Behavior[] { new DriveForward(), new DistanceEnforcer() }).start();
	}

}
