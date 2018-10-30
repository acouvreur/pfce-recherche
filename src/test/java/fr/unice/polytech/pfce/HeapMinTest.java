package fr.unice.polytech.pfce;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class HeapMinTest {

	@Test
	public void testBuilding() {
		HeapMin heapMin = new HeapMin();
		for(int i = 9; i>=0;i--) {
			heapMin.insert(i);
			assertEquals(i, heapMin.getMin());
		}
		assertEquals(9, heapMin.getMax());
		assertTrue(heapMin.coherent());
	}
	
	@Test
	public void testInsert() {
		HeapMin heapMin = new HeapMin();
		Random r = new Random();
		for(int i = 10; i>=1;i--) {
			heapMin.insert(r.nextInt()+1);
		}
		heapMin.insert(Integer.MIN_VALUE);
		assertEquals(Integer.MIN_VALUE, heapMin.getMin());
		assertTrue(heapMin.coherent());
	}
	
	@Test
	public void testDeletion() {
		HeapMin heapMin = new HeapMin();
		Random r = new Random();
		heapMin.insert(Integer.MIN_VALUE);
		for(int i = 10; i>=1;i--) {
			heapMin.insert(r.nextInt()+1);
		}
		assertTrue(heapMin.coherent());
		heapMin.remove(0);
		assertNotEquals(Integer.MIN_VALUE, heapMin.getMin());
		assertTrue(heapMin.coherent());

	}
	
	
	@Test
	public void testFromArray() {
		HeapMin heapMin;
		Random r = new Random();
		int[] tab= new int[10];
		for(int i=0;i<10;i++) {
			tab[i]=i;
		}
		heapMin = new HeapMin(tab);
		assertTrue(heapMin.coherent());
	}

}
