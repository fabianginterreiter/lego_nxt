package de.dhbw.nxt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

	public String readAction() throws IOException {
	   synchronized (this.dis) {
	      return this.dis.readUTF();
	   }
	}
	
	public int readJobId() throws IOException {
	   synchronized (this.dis) {
	      return this.dis.readInt();
	   }
	}
	
	public int[] readJobPositions() throws IOException {
	   synchronized (this.dis) {
	      return new int[] {
	               this.dis.readInt(), this.dis.readInt(),
	               this.dis.readInt(), this.dis.readInt()
	      };
	   }
	}
	
	public void writeJobId(int id) throws IOException {
	   synchronized (this.dos) {
	      this.dos.writeInt(id);
	      this.dos.flush();
	   }
	}
	
	public void writeJobStatus(int id, String status) throws IOException {
	   synchronized (this.dos) {
	      this.dos.writeUTF("update-status");
	      this.dos.writeUTF(status);
	      this.dos.flush();
	   }
	}
	
	@Override
	public void run() {
		try {
			while (true) {
			   String action = this.readAction();
			   
			   if (action.equals("add-job")) {
	            RConsole.println("[BT] Receiving positions...");
			      int[] pos = this.readJobPositions();

			      RConsole.println("[BT] Sending Job ID...");

	            Job job = new Job(pos[0], pos[1], pos[2], pos[3]);
               this.robot.getQueue().addJob(job);
	            this.writeJobId(job.getId());
			   } else if (action.equals("remove-job")) {
               RConsole.println("[BT] Receiving positions...");

               int id = this.readJobId();
			      this.robot.getQueue().removeJob(id);
			   } else {
			      RConsole.println("[SYS] Unknown Action: " + action);
			   }
			}
		} catch (IOException e1) {
			RConsole.println("IO Error occurred while doing BT communications!");
			e1.printStackTrace();
		}
	}
}
