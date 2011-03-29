package de.dhbw.nxt;

import lejos.nxt.Motor;
import lejos.robotics.navigation.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		// Tachopilot nimmt den Durchmesser und Abstand der RŠder.
		// Dabei wird der Abstand der RŠder von AUSSEN gemessen.
		// Bewegungen sind aber sehr ungenau... :(
		TachoPilot pilot = new TachoPilot(5.6f, 11.2f, Motor.A, Motor.B);
		pilot.setSpeed(200);
		pilot.rotate(90);
	}
}
