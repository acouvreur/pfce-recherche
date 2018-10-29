package fr.unice.polytech.pfce;

import java.util.Arrays;

public class HeapMinImmutable {
	
	final int[] tab;
	final int size;

	public HeapMinImmutable() {
		tab = new int[1];
		size=-1;
	}
	
	private HeapMinImmutable(int size) {
		tab = new int[size+1];
		this.size=size;
	}
	
	private int parent(int i) {
		int parent;
		if(i%2==0) {
			parent=i/2;
		}else {
			parent=(i-1)/2;
		}
		return parent;
	}
	
	private int leftChild(int i) {
		if (2*i > size)
			return -1;
		return 2*i;
	}
	private int rightChild(int i) {
		if (2*i+1 > size)
			return -1;
		return 2*i+1;
	}
	
	public HeapMinImmutable insert(int n) {
		HeapMinImmutable heap = new HeapMinImmutable(size+1);
		for(int i=0;i<=size;i++) {
			heap.tab[i]=tab[i];
		}
		heap.tab[heap.size]=n;
		heap.percolateUp(heap.size);
		return heap;
	}
	private void swap(int i, int y) {
		int tmp = tab[i];
		tab[i]=tab[y];
		tab[y]=tmp;
	}
	
	private void percolateUp(int pos) {
		boolean done = true;
		int current = pos;
		if(tab[current]<tab[parent(current)]) {
			swap(current,parent(current));
			done = false;
			current = parent(current);
		}
		while(!done && current!=0) {
			done = true;
			if(tab[current]<tab[parent(current)]) {
				swap(current,parent(current));
				done = false;
				current = parent(current);
			}
		}
	}
	private void percolateDown(int pos) {
		boolean done = true;
		int current = pos;
		if(leftChild(current)!=-1 && tab[current]>tab[leftChild(current)]) {
			swap(current,leftChild(current));
			done = false;
			current = leftChild(current);
		}else if(rightChild(current)!=-1 && tab[current]>tab[rightChild(current)]) {
			swap(current,rightChild(current));
			done = false;
			current = rightChild(current);
		}
		while(!done && current!=-1) {
			done = true;
			if(leftChild(current)!=-1 && tab[current]>tab[leftChild(current)]) {
				swap(current,leftChild(current));
				done = false;
				current = leftChild(current);
			}else if(rightChild(current)!=-1 && tab[current]>tab[rightChild(current)]) {
				swap(current,rightChild(current));
				done = false;
				current = rightChild(current);
			}
		}
	}
	
	public HeapMinImmutable remove(int i) {
		if(i==size) {
			HeapMinImmutable heap = new HeapMinImmutable(size-1);
			for(int y=0;y<size;y++) {
				heap.tab[y]=tab[y];
			}
			return heap;
		}
		HeapMinImmutable heap = new HeapMinImmutable(size-1);
		for(int y=0;y<size;y++) {
			if(y!=i)
				heap.tab[y]=tab[y];
			else
				heap.tab[y]=tab[size];
		}
		heap.percolateDown(i);
		heap.percolateUp(i);
		return heap;
	}
	
	public int get(int i) {
		return tab[i];
	}
	
	public int getSize() {
		return size;
	}
	
	public int getMin() {
		return tab[0];
	}
	
	public int getMax() {
		int max=Integer.MIN_VALUE;
		for (int i=0;i<=size;i++) {
			if(tab[i]>max) {
				max = tab[i];
			}
		}
		return max;
	}
	
	public int popMin() {
		int min = getMin();
		remove(0);
		return min;
	}

}
