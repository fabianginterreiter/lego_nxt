package de.dhbw.nxt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.RConsole;

public class BTHandler implements Runnable {
	private Robot robot;
	private BTConnection connection;
	private DataInputStream dis;
	private DataOutputStream dos;
	ArrayList<Job> newJobs;

	public BTHandler(Robot robot) {
		this.robot = robot;
		this.newJobs = new ArrayList<Job>();
	}

	public void connect() {
		RConsole.println("[BT] Connecting");

		// TODO Auto-generated method stub
		this.connection = Bluetooth.waitForConnection();

      RConsole.println("[BT] Connected");

		this.dis = this.connection.openDataInputStream();
		this.dos = this.connection.openDataOutputStream();
	}

	@Override
	public void run() {
		try {
			while (true) {
				RConsole.println("[BT] Receiving destination...");

				int fx = this.dis.readInt();
				int fy = this.dis.readInt();
				int dx = this.dis.readInt();
				int dy = this.dis.readInt();

				RConsole.println("[BT] Sending Job ID...");

				Job job = new Job(fx, fy, dx, dy);
				this.dos.writeInt(job.getId());
				this.dos.flush();

				this.robot.getQueue().addJob(job);
			}
		} catch (IOException e1) {
			RConsole.println("IO Error occurred while doing BT communications!");
			e1.printStackTrace();
		}
	}
}
