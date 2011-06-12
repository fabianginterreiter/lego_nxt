package de.dhbw.nxt;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MapTest {
	private Map map;

	@Before
	public void setUp() throws Exception {
		String mapContent =
			"        \n" +
			"  # ##  \n" +
			"  ## #  \n" +
			"        \n";
		
		String[] rows = mapContent.split("\n");

		this.map = new Map(rows[0].length(), rows.length);
		
		for (int i = 0; i < rows.length - 1; i++) {
			for (int j = 0; j < rows[i].length() - 1; j++) {
				if (rows[i].charAt(j) == '#') {
					this.map.tileAt(j, i).setPermanentlyNotPassable();
				}
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void shouldHaveTheCorrectWidth() {
		assertThat(this.map.getWidth(), is(8));
	}

	@Test
	public void shouldHaveTheCorrectHeight() {
		assertThat(this.map.getHeight(), is(4));
	}
	
	@Test
	public void shouldFindEasyPaths() {
		int[][] expected = new int[][] {
			{ 0, 0 },
			{ 1, 0 },
			{ 2, 0 }
		};
		
		assertThat(this.map.findPath(0, 0, 2, 0), is(expected));
	}

	@Test
	public void shouldFindComplexPaths() {
		int[][] expected = new int[][] {
			{ 0, 0 },
			{ 1, 0 },
			{ 1, 1 },
			{ 1, 2 },
			{ 1, 3 },
			{ 2, 3 },
			{ 3, 3 },
			{ 4, 3 },
			{ 4, 2 }
		};
		
		assertThat(this.map.findPath(0, 0, 4, 2), is(expected));
	}
}
