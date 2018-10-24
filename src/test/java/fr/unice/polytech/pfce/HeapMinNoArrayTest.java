package fr.unice.polytech.pfce;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class HeapMinNoArrayTest {

	@Test
	public void testBuilding() {
		HeapMinNoArray heapMin = new HeapMinNoArray();
		for(int i = 9; i>=0;i--) {
			heapMin.insert(i);
			assertEquals(i, heapMin.getMin());
		}
		assertEquals(9, heapMin.getMax());
	}
	
	@Test
	public void testInsert() {
		HeapMinNoArray heapMin = new HeapMinNoArray();
		Random r = new Random();
		for(int i = 10; i>=1;i--) {
			heapMin.insert(r.nextInt()+1);
		}
		heapMin.insert(Integer.MIN_VALUE);
		assertEquals(Integer.MIN_VALUE, heapMin.getMin());
	}
	
	@Test
	public void testDeletion() {
		HeapMinNoArray heapMin = new HeapMinNoArray();
		Random r = new Random();
		heapMin.insert(Integer.MIN_VALUE);
		for(int i = 10; i>=1;i--) {
			heapMin.insert(r.nextInt()+1);
		}
		heapMin.remove(0);
		assertNotEquals(Integer.MIN_VALUE, heapMin.getMin());
	}

}
