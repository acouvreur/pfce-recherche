package fr.unice.polytech.pfce.immutable;

import fr.unice.polytech.pfce.BinaryTree;

public final class AVLNil<E extends Comparable<E>> extends AVLTree<E> {

    public AVLNil() {
        super(null, null, null, 0, 0);
    }

    public AVLTree<E> insert(E element) {
        return new AVLNode<E>(element, this, this);
    }

    public Pair<E, AVLTree<E>> pollFirst() {
        return new Pair<E, AVLTree<E>>(null, this);
    }

    public AVLTree<E> remove(E element) {
        return this;
    }

    @Override
    public BinaryTree<E> removeMin() {
        return this;
    }

    @Override
    public E findMin() {
        return null;
    }

    @Override
    public E findMax() {
        return null;
    }

    @Override
    public E find(E data) {
        return null;
    }

    public int index(E element) {
        return 0;
    }

    public int subSize(E fromElement, E toElement) {
        return 0;
    }

    @Override
    public boolean check() {
        return true;
    }

    protected String toString(String indent) {
        return indent + "$\n";
    }

}
