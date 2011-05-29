package de.dhbw.nxt;

import lejos.nxt.Motor;
import lejos.robotics.navigation.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// DifferentialPilot nimmt den Durchmesser und Abstand der RŠder.
		// Dabei wird der Abstand der RŠder von AUSSEN gemessen.
		// Bewegungen sind aber sehr ungenau... :(
		DifferentialPilot pilot = new DifferentialPilot(5.6f, 11.2f, Motor.A, Motor.B);
		pilot.setAcceleration(400);
		pilot.rotate(90);
	}
}
