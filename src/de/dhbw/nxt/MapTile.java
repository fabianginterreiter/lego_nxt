package de.dhbw.nxt;

public class MapTile {
	private float cost;
	private MapTile parent;
	private int y;
	private int x;
	private long blockedAt;
	
	public MapTile(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setPermanentlyNotPassable() {
		this.blockedAt = Long.MAX_VALUE;
	}
	
	public void setTemporarilyNotPassable() {
		this.blockedAt = System.currentTimeMillis();
	}
	
	public boolean isPassable() {
		// Temporarily not passable fields are only unpassable for 2 minutes
		return (System.currentTimeMillis() - this.blockedAt) > 120000;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getCost() {
		return cost;
	}

	public double distanceTo(int startX, int startY) {
		int x = startX - this.x;
		int y = startY - this.y;
		
		return Math.sqrt(x * x + y * y);
	}
	
	public void setParent(MapTile parent) {
		this.parent = parent;
	}
	
	public MapTile getParent() {
		return this.parent;
	}

}
