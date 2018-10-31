package fr.unice.polytech.pfce;

public interface BinaryTree<T extends Comparable<T>> {

    BinaryTree<T> insert(T data);
    BinaryTree<T> remove(T data);
    BinaryTree<T> removeMin();

    T findMin();
    T findMax();
    T find(T data);

}
