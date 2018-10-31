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
			assertEquals((int) heapMin.findMin(), i);
		}
		assertEquals((int) heapMin.findMax(), 9);
	}
	
	@Test
	public void testInsert() {
		HeapMin heapMin = new HeapMin();
		Random r = new Random();
		for(int i = 10; i>=1;i--) {
			heapMin.insert(r.nextInt()+1);
		}
		heapMin.insert(Integer.MIN_VALUE);
		assertNotEquals((int) heapMin.findMin(), Integer.MIN_VALUE);
	}
	
	@Test
	public void testDeletion() {
		HeapMin heapMin = new HeapMin();
		Random r = new Random();
		heapMin.insert(Integer.MIN_VALUE);
		for(int i = 10; i>=1;i--) {
			heapMin.insert(r.nextInt()+1);
		}
		heapMin.remove(0);
		assertNotEquals((int) heapMin.findMin(), Integer.MIN_VALUE);
	}

}
