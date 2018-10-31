package fr.unice.polytech.pfce.immutable;

import fr.unice.polytech.pfce.BinaryTree;

public abstract class AVLTree<E extends Comparable<E>> implements BinaryTree<E> {
    protected final E element;
    protected final AVLTree<E> left, right;
    protected final int height;
    public final int size;

    protected AVLTree(E element, AVLTree<E> left, AVLTree<E> right, int height, int size) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = height;
        this.size = size;
    }

    public abstract AVLTree<E> insert(E element);
    public abstract Pair<E, AVLTree<E>> pollFirst();
    public abstract AVLTree<E> remove(E element);
    public abstract int index(E element);
    public abstract int subSize(E fromElement, E toElement);

    public abstract boolean check();

    protected abstract String toString(String indent);

    public String toString() {
        return this.toString("");
    }

}

