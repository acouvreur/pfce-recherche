package fr.unice.polytech.pfce;

import java.util.Arrays;

public class HeapMin {
	
	int[] tab;
	int size;

	public HeapMin() {
		tab = new int[256];
		size=-1;
	}
	
	private void extendTab() {
		tab = Arrays.copyOf(tab,tab.length+256);
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
	
	public void insert(int n) {
		size++;
		if(size>=tab.length) {extendTab();}
		tab[size]=n;
		percolateUp(size);
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
		if(tab[current]>tab[leftChild(current)] && leftChild(current)!=-1) {
			swap(current,leftChild(current));
			done = false;
			current = leftChild(current);
		}else if(tab[current]>tab[rightChild(current)] && rightChild(current)!=-1) {
			swap(current,rightChild(current));
			done = false;
			current = rightChild(current);
		}
		while(!done && current!=-1) {
			done = true;
			if(tab[current]>tab[leftChild(current)] && leftChild(current)!=-1) {
				swap(current,leftChild(current));
				done = false;
				current = leftChild(current);
			}else if(tab[current]>tab[rightChild(current)] && rightChild(current)!=-1) {
				swap(current,rightChild(current));
				done = false;
				current = rightChild(current);
			}
		}
	}
	
	public void remove(int i) {
		if(i==size) {
			size--;
			return;
		}
		tab[i]=tab[size];
		size--;
		percolateDown(i);
		percolateUp(i);
	}
	
	public int get(int i) {
		return tab[i];
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

}
