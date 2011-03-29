package de.dhbw.nxt;

import lejos.nxt.Motor;
import lejos.robotics.subsumption.Behavior;

public class DriveForward implements Behavior {

	@Override
	public void action() {
		System.out.println("DriveForward action!");
		Motor.A.forward();
		Motor.B.forward();
	}

	@Override
	public void suppress() {
		System.out.println("DriveForward suppressed!");
		Motor.A.stop();
		Motor.B.stop();
	}

	@Override
	public boolean takeControl() {
		return true;
	}
}
