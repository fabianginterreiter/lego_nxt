package de.dhbw.nxt;

public class MapTile {
	private boolean passable;
	private float cost;
	private MapTile parent;
	private int y;
	private int x;
	
	public MapTile(int x, int y) {
		this.x = x;
		this.y = y;
		this.passable = true;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setNotPassable() {
		this.passable = false;
	}
	
	public boolean isPassable() {
		return this.passable;
	}

	public void setCost(float cost) {
		this.cost = cost;
	}

	public float getCost() {
		return cost;
	}

	public void setParent(MapTile parent) {
		this.parent = parent;
	}
	
	public MapTile getParent() {
		return this.parent;
	}

}
