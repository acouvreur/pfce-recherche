package fr.unice.polytech.pfce;

import java.util.Arrays;

public class HeapMin implements BinaryTree<Integer> {
	
	int[] tab;
	int size;

	public HeapMin() {
		tab = new int[256];
		size=-1;
	}

	public HeapMin(Integer[] tab) {
		this.tab = Arrays.stream(tab).mapToInt(Integer::intValue).toArray();
		size=tab.length-1;
		for(int i=0;i<=size;i++)
			percolateDown(i);
		for(int i=size;i>0;i--)
			percolateUp(i);
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
	
	public BinaryTree<Integer> insert(Integer n) {
		size++;
		if(size>=tab.length) {extendTab();}
		tab[size]=n;
		percolateUp(size);
		return this;
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

	@Override
	public BinaryTree<Integer> remove(Integer i) {
		if(i==size) {
			size--;
			return this;
		}
		tab[i]=tab[size];
		size--;
		percolateDown(i);
		percolateUp(i);
		return this;
	}

    @Override
    public BinaryTree<Integer> removeMin() {
	    remove(0);
        return this;
    }

    public Integer find(Integer i) {
		return tab[i];
	}

	public int getSize() {
		return size;
	}

	@Override
	public Integer findMin() {
		return tab[0];
	}

	@Override
	public Integer findMax() {
		int max=Integer.MIN_VALUE;
		for (int i=0;i<=size;i++) {
			if(tab[i]>max) {
				max = tab[i];
			}
		}
		return max;
	}
	
	public int popMin() {
		int min = findMin();
		remove(0);
		return min;
	}

}
