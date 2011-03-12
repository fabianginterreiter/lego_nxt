package de.dhbw.nxt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

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
	
	class Pythagoras implements Comparator<MapTile> {
		private int startX;
		private int startY;

		public Pythagoras(int startX, int startY) {
			this.startX = startX;
			this.startY = startY;
		}

		public double getDistance(MapTile tile) {
			int x = startX - tile.getX();
			int y = startY - tile.getY();
			return Math.sqrt(x * x + y * y);
		}
		
		@Override
		public int compare(MapTile o1, MapTile o2) {
			return (int) (getDistance(o1) - getDistance(o2));
		}
	}
	
	public int[][] findPath(int startX, int startY, int endX, int endY) {
		this.closedList.clear();
		this.openList.clear();
		
		MapTile startTile = this.tileAt(startX, startY);
		startTile.setCost(0);
		startTile.setParent(null);
		this.openList.add(startTile);
		
		MapTile next = null;
		
		while (this.openList.size() != 0) {
			next = this.openList.remove(0);
			this.closedList.add(next);
			
			if (next.equals(this.tileAt(endX, endY))) {
				break;
			}
			
			this.checkNext(next.getX() - 1, next.getY(), next);
			this.checkNext(next.getX() + 1, next.getY(), next);
			this.checkNext(next.getX(), next.getY() - 1, next);
			this.checkNext(next.getX(), next.getY() + 1, next);
		}

		while (next != null) {
			System.out.println(next.getX() + "-" + next.getY());
			next = next.getParent();
		}
		
		return new int[][] {};
	}

	private void checkNext(int x, int y, MapTile current) {
		if (this.canMoveTo(x, y)) {
			MapTile next = this.tileAt(x, y);

			if (this.closedList.contains(next)) {
				return;
			}

			float nextCost = current.getCost() + 1;
			
			if (this.openList.contains(next) && next.getCost() >= nextCost) {
				return;
			}
			
			next.setParent(current);
			next.setCost(nextCost);
			
			if (!this.openList.contains(next)) {
				this.openList.add(next);
			}
		}
		
	}
}
