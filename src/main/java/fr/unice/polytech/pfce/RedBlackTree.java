package fr.unice.polytech.pfce;

import java.util.NoSuchElementException;

/**
 * RedBlackTree
 */
public class RedBlackTree<T extends Comparable<? super T>> implements BinaryTree<T> {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        private T val; 
        private Node left, right;
        private boolean color;
        private int size;

        public Node(T val, boolean color, int size) {
            this.val = val;
            this.color = color;
            this.size = size;
        }
    }

    public RedBlackTree() {

    }

    private boolean isRed(Node x) {
        if(x == null) return false; // NIL are black
        return x.color == RED;
    }

    private int size(Node x) {
        if(x == null) return 0;
        return x.size;
    }

    @Override
    public boolean insert(T x) {

        root = insert(root, x);
        root.color = BLACK;

        return true;
    }

    private Node insert(Node x, T v) {
        if (x == null) return new Node(v, RED, 1);

        int cmp = v.compareTo(x.val);
        if      (cmp < 0) x.left  = insert(x.left,  v); 
        else if (cmp > 0) x.right = insert(x.right, v); 
        else              x.val   = v;

        // fix-up any right-leaning links
        if (isRed(x.right) && !isRed(x.left))      x = rotateLeft(x);
        if (isRed(x.left)  &&  isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left)  &&  isRed(x.right))     flipColors(x);
        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }

    private Node rotateRight(Node h) {
        // assert (h != null) && isRed(h.left);
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = x.right.color;
        x.right.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    private Node rotateLeft(Node h) {
        // assert (h != null) && isRed(h.right);
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = x.left.color;
        x.left.color = RED;
        x.size = h.size;
        h.size = size(h.left) + size(h.right) + 1;
        return x;
    }

    // flip the colors of a node and its two children
    private void flipColors(Node h) {
        // h must have opposite color of its two children
        // assert (h != null) && (h.left != null) && (h.right != null);
        // assert (!isRed(h) &&  isRed(h.left) &&  isRed(h.right))
        //    || (isRed(h)  && !isRed(h.left) && !isRed(h.right));
        h.color = !h.color;
        h.left.color = !h.left.color;
        h.right.color = !h.right.color;
    }

    // Assuming that h is red and both h.left and h.left.left
    // are black, make h.left or one of its children red.
    private Node moveRedLeft(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.left) && !isRed(h.left.left);

        flipColors(h);
        if (isRed(h.right.left)) { 
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
            flipColors(h);
        }
        return h;
    }

    // Assuming that h is red and both h.right and h.right.left
    // are black, make h.right or one of its children red.
    private Node moveRedRight(Node h) {
        // assert (h != null);
        // assert isRed(h) && !isRed(h.right) && !isRed(h.right.left);
        flipColors(h);
        if (isRed(h.left.left)) { 
            h = rotateRight(h);
            flipColors(h);
        }
        return h;
    }

    private Node balance(Node h) {
        // assert (h != null);

        if (isRed(h.right))                      h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right))     flipColors(h);

        h.size = size(h.left) + size(h.right) + 1;
        return h;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public T findMin() {
        if (isEmpty()) throw new NoSuchElementException("calls findMin() with empty symbol table");
        return findMin(root).val;

    }

    private Node findMin(Node x) {
        if (x.left == null) return x; 
        else                return findMin(x.left); 
    }

    @Override
    public T findMax() {
        if (isEmpty()) throw new NoSuchElementException("calls findMax() with empty symbol table");
        return findMax(root).val;
    }

    private Node findMax(Node x) {
        if (x.left == null) return x; 
        else                return findMax(x.right); 
    }

    @Override
    public void remove(T x) {
        if (x == null) throw new IllegalArgumentException("argument to remove() is null");
        if (!contains(x)) return;

        // if both children of root are black, set root to red
        if (!isRed(root.left) && !isRed(root.right))
            root.color = RED;

        root = remove(root, x);
        if (!isEmpty()) root.color = BLACK;
        // assert check();
    }

    private Node remove(Node h, T v) {

        if (v.compareTo(h.val) < 0)  {
            if (!isRed(h.left) && !isRed(h.left.left))
                h = moveRedLeft(h);
            h.left = remove(h.left, v);
        }
        else {
            if (isRed(h.left))
                h = rotateRight(h);
            if (v.compareTo(h.val) == 0 && (h.right == null))
                return null;
            if (!isRed(h.right) && !isRed(h.right.left))
                h = moveRedRight(h);
            if (v.compareTo(h.val) == 0) {
                Node x = findMin(h.right);
                h.val = x.val;
                // h.val = get(h.right, min(h.right).key);
                // h.key = min(h.right).key;
                h.right = removeMin(h.right);
            }
            else h.right = remove(h.right, v);
        }
        return balance(h);
    }

    private Node removeMin(Node h) { 
        if (h.left == null)
            return null;

        if (!isRed(h.left) && !isRed(h.left.left))
            h = moveRedLeft(h);

        h.left = removeMin(h.left);
        return balance(h);
    }

    @Override
    public boolean contains(T x) {
        return contains(root, x);
    }

    private boolean contains(Node x, T v) {
        while (x != null) {
            int cmp = v.compareTo(x.val);
            if      (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else              return true;
        }
        return false;
    }

    
}