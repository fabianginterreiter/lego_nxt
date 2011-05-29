package de.dhbw.nxt;

import lejos.nxt.Motor;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		Motor.A.setSpeed(500);
		Motor.B.setSpeed(500);
		Motor.A.forward();
		Motor.B.forward();
		
		Thread.sleep(500);	
		
		Motor.A.stop();
		Motor.B.stop();
	}

}
