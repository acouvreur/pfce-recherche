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
        	System.out.println("Array Size;Heap Insert(ns)");
        	System.out.println(size+";"+timeMeanInsert);
        	System.out.println("Array Size;Heap Delete(ns)");
        	System.out.println(size+";"+timeMeanDelete);
        }
        /*
        AVLTree avlTree;
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
        	System.out.println("Array Size;AVLTree Insert(ns)");
        	System.out.println(size+";"+timeMeanInsert);
        	System.out.println("Array Size;AVLTree Delete(ns)");
        	System.out.println(size+";"+timeMeanDelete);
        }
        ImmutableAVLTree<Integer> immutableAVLTree;
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
        	System.out.println("Array Size;ImmutableAVLTree Insert(ns)");
        	System.out.println(size+";"+timeMeanInsert);
        	System.out.println("Array Size;ImmutableAVLTree Delete(ns)");
        	System.out.println(size+";"+timeMeanDelete);
        }
        */
        RedBlackTree<Integer> redBlackTree;
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
        	System.out.println("Array Size;RedBlackTree Insert(ns)");
        	System.out.println(size+";"+timeMeanInsert);
        	System.out.println("Array Size;RedBlackTree Delete(ns)");
        	System.out.println(size+";"+timeMeanDelete);
        }
    }
}
