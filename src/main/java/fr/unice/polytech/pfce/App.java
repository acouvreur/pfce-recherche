package fr.unice.polytech.pfce;

import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main( String[] args )
    {
        Random r = new Random();
        long timeMs1,timeMs2,timeMeanInsert,timeMeanDelete;
        //Mutable Heap test
        System.out.println("Mutable Heap test");
        HeapMin heap;
        long size;
        for(int i=1;i<=20;i++) {
        	size = (long) Math.pow(2, i);
        	timeMeanInsert=0;
        	timeMeanDelete=0;
        	for(int y=0;y<100;y++) {
        		heap= new HeapMin();
        		timeMs1 = System.nanoTime();
        		for(long z=0;z<size;z++) {
        			heap.insert(r.nextInt());
        		}
        		timeMs2 = System.nanoTime();
        		timeMeanInsert+=timeMs2-timeMs1;
        		timeMs1 = System.nanoTime();
        		for(long z=0;z<size;z++) {
        			heap.remove(0);
        		}
        		timeMs2 = System.nanoTime();
        		timeMeanDelete+=timeMs2-timeMs1;
        	}
        	timeMeanInsert=timeMeanInsert/100;
        	timeMeanDelete=timeMeanDelete/100;
        	System.out.println("Mean time for x^"+i+" insertion in heap : "+timeMeanInsert+"ns");
        	System.out.println("Mean time for x^"+i+" deletion in heap  : "+timeMeanDelete+"ns");
        }
        /*
        AVLTree avlTree;
        System.out.println("Mutable AVLTree test");
        for(int i=1;i<=20;i++) {
        	size = (long) Math.pow(2, i);
        	timeMeanInsert=0;
        	timeMeanDelete=0;
        	for(int y=0;y<100;y++) {
        		avlTree= new AVLTree();
        		timeMs1 = System.nanoTime();
        		for(long z=0;z<size;z++) {
        			avlTree.insert(r.nextInt());
        		}
        		timeMs2 = System.nanoTime();
        		timeMeanInsert+=timeMs2-timeMs1;
        		timeMs1 = System.nanoTime();
        		while(!redBlackTree.isEmpty()) {
        			avlTree.remove(avlTree.findMin());
        		}
        		timeMs2 = System.nanoTime();
        		timeMeanDelete+=timeMs2-timeMs1;
        	}
        	timeMeanInsert=timeMeanInsert/100;
        	timeMeanDelete=timeMeanDelete/100;
        	System.out.println("Mean time for x^"+i+" insertion in AVLTree : "+timeMeanInsert+"ns");
        	System.out.println("Mean time for x^"+i+" deletion in AVLTree  : "+timeMeanDelete+"ns");
        }
        ImmutableAVLTree<Integer> immutableAVLTree;
        System.out.println("Immutable AVLTree test");
        for(int i=1;i<=20;i++) {
        	size = (long) Math.pow(2, i);
        	timeMeanInsert=0;
        	timeMeanDelete=0;
        	for(int y=0;y<100;y++) {
        		immutableAVLTree= new AVLNil<>();
        		timeMs1 = System.nanoTime();
        		for(long z=0;z<size;z++) {
        			immutableAVLTree=immutableAVLTree.add(r.nextInt());
        		}
        		timeMs2 = System.nanoTime();
        		timeMeanInsert+=timeMs2-timeMs1;
        		timeMs1 = System.nanoTime();
        		while(!redBlackTree.isEmpty()) {
        			immutableAVLTree=immutableAVLTree.remove(immutableAVLTree.element);
        		}
        		timeMs2 = System.nanoTime();
        		timeMeanDelete+=timeMs2-timeMs1;
        	}
        	timeMeanInsert=timeMeanInsert/100;
        	timeMeanDelete=timeMeanDelete/100;
        	System.out.println("Mean time for x^"+i+" insertion in Immutable AVLTree : "+timeMeanInsert+"ns");
        	System.out.println("Mean time for x^"+i+" deletion in Immutable AVLTree  : "+timeMeanDelete+"ns");
        }
        */
        RedBlackTree<Integer> redBlackTree;
        System.out.println("Mutable RedBlackTree test");
        for(int i=1;i<=20;i++) {
        	size = (long) Math.pow(2, i);
        	timeMeanInsert=0;
        	timeMeanDelete=0;
        	for(int y=0;y<100;y++) {
        		redBlackTree= new RedBlackTree<>();
        		timeMs1 = System.nanoTime();
        		for(long z=0;z<size;z++) {
        			redBlackTree.insert(r.nextInt());
        		}
        		timeMs2 = System.nanoTime();
        		timeMeanInsert+=timeMs2-timeMs1;
        		timeMs1 = System.nanoTime();
        		while(!redBlackTree.isEmpty()) {
        			redBlackTree.remove(redBlackTree.findMin());;
        		}
        		timeMs2 = System.nanoTime();
        		timeMeanDelete+=timeMs2-timeMs1;
        	}
        	timeMeanInsert=timeMeanInsert/100;
        	timeMeanDelete=timeMeanDelete/100;
        	System.out.println("Mean time for x^"+i+" insertion in RedBlackTree : "+timeMeanInsert+"ns");
        	System.out.println("Mean time for x^"+i+" deletion in RedBlackTree  : "+timeMeanDelete+"ns");
        }
    }
}
