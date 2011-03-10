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
		this.map = new Map(20, 30);
	}

	@After
	public void tearDown() throws Exception {
		
	}
	
	@Test
	public void shouldHaveTheCorrectWidth() {
		assertThat(this.map.getWidth(), is(20));
	}

	@Test
	public void shouldHaveTheCorrectHeight() {
		assertThat(this.map.getHeight(), is(30));
	}
}
