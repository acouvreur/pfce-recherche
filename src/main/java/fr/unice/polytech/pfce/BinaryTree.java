package fr.unice.polytech.pfce;

public interface BinaryTree<T extends Comparable<? super T>> {
    boolean insert (T x);

    boolean isEmpty();

    T findMin();

    T findMax();

    void remove(T x);

    boolean contains(T x);
}
