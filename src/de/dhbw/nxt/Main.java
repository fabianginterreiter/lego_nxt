package de.dhbw.nxt;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		LineDetector l = new LineDetector(new LightSensor(SensorPort.S1));

		Thread.sleep(500);

		Motor.A.setPower(1);
		Motor.B.setPower(1);
		Motor.A.forward();
		Motor.B.forward();

		while (l.checkForLine() != true) {
		}

		Motor.A.stop();
		Motor.B.stop();
	}

}
