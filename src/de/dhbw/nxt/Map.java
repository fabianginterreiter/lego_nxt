package de.dhbw.nxt;

import java.util.ArrayList;

public class Map {

	private int width;
	private int height;

	private MapTile[][] tiles;
	
	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		
		this.tiles = new MapTile[this.width][this.height];
		
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				this.tiles[i][j] = new MapTile(i, j);
			}
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public MapTile tileAt(int x, int y) {
		return this.tiles[x][y];
	}

	private ArrayList<MapTile> closedList = new ArrayList<MapTile>();
	private ArrayList<MapTile> openList = new ArrayList<MapTile>();
	
	private double getCost(int startX, int startY, int endX, int endY) {
		// Pythagoras
		int x = startX - endX;
		int y = startY - endY;
		return Math.sqrt(x * x + y * y);
	}
	
	private boolean canMoveTo(int x, int y) {
		return x >= 0 && y >= 0
			&& x < this.width && y < this.height
			&& this.tileAt(x, y).isPassable();
	}
	
	public int[][] findPath(int startX, int startY, int endX, int endY) {
		this.closedList.clear();
		this.openList.clear();
		
		MapTile startTile = this.tileAt(startX, startY);
		startTile.setCost(0);
		startTile.setParent(null);
		this.openList.add(startTile);
		
		while (this.openList.size() != 0) {
			MapTile next = this.openList.remove(0);
			this.closedList.add(next);
			
			if (next.equals(this.tileAt(endX, endY))) {
				break;
			}
			
			this.checkNext(next.getX() - 1, next.getY(), next);
			this.checkNext(next.getX() + 1, next.getY(), next);
			this.checkNext(next.getX(), next.getY() - 1, next);
			this.checkNext(next.getX(), next.getY() + 1, next);
		}
		
		
		return new int[][] {};
	}

	private void checkNext(int x, int y, MapTile current) {
		if (this.canMoveTo(x, y)) {
			MapTile next = this.tileAt(x, y);
			
			if (current.getCost() + 1 < next.getCost()) {
				this.openList.remove(next);
				this.closedList.remove(next);
			}

			if (!this.openList.contains(next) && !this.closedList.contains(next)) {
				next.cost = current.getCost() + 1;
			}

		}
		
	}

}
