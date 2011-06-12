package de.dhbw.nxt;

public class Job {
	static int nextID = 1;
	
	private static int getNextID() {
		return nextID++;
	}
	
	private int id;
	private int fetchX;
	private int fetchY;
	private int deliverX;
	private int deliverY;
	private boolean fetched;
	private boolean delivered;

	public Job(int fetchX, int fetchY, int deliverX, int deliverY) {
		this.id = Job.getNextID();
		this.fetchX = fetchX;
		this.fetchY = fetchY;
		this.deliverX = deliverX;
		this.deliverY = deliverY;
		
		this.fetched = false;
		this.delivered = false;
	}

	public int getId() {
		return id;
	}
	
	public int getDeliverX() {
		return deliverX;
	}
	
	public int getDeliverY() {
		return deliverY;
	}
	
	public int getFetchX() {
		return fetchX;
	}
	
	public int getFetchY() {
		return fetchY;
	}
	
	public boolean isFetched() {
		return this.fetched;
	}
	
	public boolean isDelivered() {
		return this.delivered;
	}

	public double distanceFrom(int[] currentPos) {
		int x;
		int y;
		
		if (!this.isFetched()) {
			x = this.fetchX - currentPos[0];
			y = this.fetchY - currentPos[1];
		} else {
			x = this.deliverX - currentPos[0];
			y = this.deliverY - currentPos[1];
		}
		
		return Math.sqrt(x * x + y * y);
		
	}

	public void setFetched() {
		this.fetched = true;
	}

	public void setDelivered() {
		this.delivered = true;
	}
	
	
}
