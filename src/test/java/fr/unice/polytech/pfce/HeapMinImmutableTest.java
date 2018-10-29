package fr.unice.polytech.pfce;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class HeapMinImmutableTest {

	@Test
	public void testBuilding() {
		HeapMinImmutable heapMin = new HeapMinImmutable();
		for(int i = 9; i>=0;i--) {
			heapMin=heapMin.insert(i);
			assertEquals(i, heapMin.getMin());
		}
		assertEquals(9, heapMin.getMax());
	}
	
	@Test
	public void testInsert() {
		HeapMinImmutable heapMin = new HeapMinImmutable();
		Random r = new Random();
		for(int i = 10; i>=1;i--) {
			heapMin=heapMin.insert(r.nextInt()+1);
		}
		heapMin=heapMin.insert(Integer.MIN_VALUE);
		assertEquals(Integer.MIN_VALUE, heapMin.getMin());
	}
	
	@Test
	public void testDeletion() {
		HeapMinImmutable heapMin = new HeapMinImmutable();
		Random r = new Random();
		heapMin=heapMin.insert(Integer.MIN_VALUE);
		for(int i = 10; i>=1;i--) {
			heapMin=heapMin.insert(r.nextInt()+1);
		}
		heapMin=heapMin.remove(0);
		assertNotEquals(Integer.MIN_VALUE, heapMin.getMin());
	}

}
