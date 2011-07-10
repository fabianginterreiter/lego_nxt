package de.dhbw.nxt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

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
		LCD.clear();
		LCD.drawString("Connecting", 0, 0);
		LCD.refresh();	
		
		// TODO Auto-generated method stub
        this.connection = Bluetooth.waitForConnection();
        
		LCD.clear();
		LCD.drawString("Connected", 0, 0);
		LCD.refresh();	

		this.dis = this.connection.openDataInputStream();
		this.dos = this.connection.openDataOutputStream();
	}

	@Override
	public void run() {

		while (true) {
			try {
				LCD.clear();
				LCD.drawString("Receiving", 0, 0);
				LCD.refresh();

				int fx = this.dis.readInt();
				int fy = this.dis.readInt();
				int dx = this.dis.readInt();
				int dy = this.dis.readInt();

				LCD.clear();
				LCD.drawString("Sending", 0, 0);
				LCD.refresh();

				Job job = new Job(fx, fy, dx, dy);
				this.dos.writeInt(job.getId());
				this.dos.flush();

				LCD.clear();
				LCD.drawString("Sending", 0, 0);
				LCD.refresh();
				
				synchronized (this.newJobs) {
					this.newJobs.add(job);
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
