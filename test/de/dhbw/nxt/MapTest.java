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
			"##########\n" +
			"#        #\n" +
			"#  # ##  #\n" +
			"#  ## #  #\n" +
			"#        #\n" +
			"##########\n";
		
		String[] rows = mapContent.split("\n");

		this.map = new Map(rows[0].length(), rows.length);
		
		for (int i = 0; i < rows.length - 1; i++) {
			for (int j = 0; j < rows[i].length() - 1; j++) {
				if (rows[i].charAt(j) == '#') {
					this.map.tileAt(j, i).setNotPassable();
				}
			}
		}
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void shouldHaveTheCorrectWidth() {
		assertThat(this.map.getWidth(), is(10));
	}

	@Test
	public void shouldHaveTheCorrectHeight() {
		assertThat(this.map.getHeight(), is(6));
	}
	
	@Test
	public void shouldFindEasyPaths() {
		int[][] expected = new int[][] {
			{ 1, 1 },
			{ 2, 1 },
			{ 3, 1 }
		};
		
		assertThat(this.map.findPath(1, 1, 3, 1), is(expected));
	}

	@Test
	public void shouldFindComplexPaths() {
		int[][] expected = new int[][] {
			{ 1, 1 },
			{ 2, 1 },
			{ 3, 1 }
		};
		
		assertThat(this.map.findPath(1, 1, 5, 3), is(expected));
	}
}
