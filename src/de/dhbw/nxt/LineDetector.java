package de.dhbw.nxt;

import lejos.nxt.LightSensor;

public class LineDetector {
	private LightSensor sensor;
	private boolean hasLine;

	public LineDetector(LightSensor s) {
		this.sensor = s;
		this.hasLine = false;
	}

	public boolean checkForLine() {
		int currentValue = this.sensor.readNormalizedValue();

		if (currentValue < 300) {
			if (this.hasLine == false) {
				// call black
				this.hasLine = true;
				return true;
			}
		} else {
			if (this.hasLine == true) {
				this.hasLine = false;
			}
		}
		return false;
	}
}
