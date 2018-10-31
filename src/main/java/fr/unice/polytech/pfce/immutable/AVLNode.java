package fr.unice.polytech.pfce.immutable;

import fr.unice.polytech.pfce.AVLTreeST;
import fr.unice.polytech.pfce.BinaryTree;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public final class AVLNode<E extends Comparable<E>> extends AVLTree<E> {

    public AVLNode(E element, AVLTree<E> left, AVLTree<E> right) {
        super(element, left, right, Math.max(left.height, right.height)+1, left.size+right.size+1);
    }

    /*
    public AVLNode(E[] values) {
        super(node.element, node.left, node.right, Math.max(node.left.height, node.right.height)+1, node.left.size+node.right.size+1);
        //Arrays.sort(values);

        AVLTree<E> node = fromSortedArray(values, 0, values.length - 1);

    }

    private AVLTree<E> fromSortedArray(E[] arr, int start, int end) {

        if(start > end) {
            return null;
        }

        int mid = (start+end) / 2;
        AVLTree<E> node = new AVLNode<E>(arr[mid], 0, 1);

        /* Recursively construct the left subtree and make it
         left child of root
        node.left = fromSortedArray(arr, start, mid - 1);

        if(node.left != null) {
            node.size += node.left.size;
        }

         /* Recursively construct the right subtree and make it
          right child of root
        node.right = fromSortedArray(arr, mid + 1, end);

        if(node.right != null) {
            node.size += node.right.size;
        }

        if(node.right != null || node.left != null)
            node.height++;

        return node;
    }
*/
    private AVLTree<E> balanceLeft(E element, AVLTree<E> left, AVLTree<E> right) {
        if (left.height <= right.height+1) {
            return new AVLNode<E>(element, left, right);
        }
        else if (left.left.height > right.height) {
            return new AVLNode<E>
                    ( left.element
                            , left.left
                            , new AVLNode<E>(element, left.right, right)
                    )
                    ;
        }
        else {
            return new AVLNode<E>
                    ( left.right.element
                            , new AVLNode<E>(left.element, left.left, left.right.left)
                            , new AVLNode<E>(element, left.right.right, right)
                    )
                    ;
        }
    }

    private AVLTree<E> balanceRight(E element, AVLTree<E> left, AVLTree<E> right) {
        if (left.height+1 >= right.height) {
            return new AVLNode<E>(element, left, right);
        }
        else if (left.height < right.right.height) {
            return new AVLNode<E>
                    ( right.element
                            , new AVLNode<E>(element, left, right.left)
                            , right.right
                    )
                    ;
        }
        else {
            return new AVLNode<E>
                    ( right.left.element
                            , new AVLNode<E>(element, left, right.left.left)
                            , new AVLNode<E>(right.element, right.left.right, right.right)
                    )
                    ;
        }
    }

    public AVLTree<E> insert(E element) {
        int c = element.compareTo(this.element);
        if (c < 0) {
            AVLTree<E> left = this.left.insert(element);
            if (left != this.left) {
                return balanceLeft(this.element, left, this.right);
            }
        }
        else if (c > 0) {
            AVLTree<E> right = this.right.insert(element);
            if (right != this.right) {
                return balanceRight(this.element, this.left, right);
            }
        }
        return this;
    }

    public Pair<E, AVLTree<E>> pollFirst() {
        if (this.left instanceof AVLNil) {
            return new Pair<E, AVLTree<E>>
                    ( this.element
                            , this.right
                    )
                    ;
        } else {
            Pair<E, AVLTree<E>> p = this.left.pollFirst();
            return new Pair<E, AVLTree<E>>
                    ( p.first
                            , balanceRight(this.element, p.second, this.right)
                    )
                    ;
        }
    }

    public AVLTree<E> remove(E element) {
        int c = element.compareTo(this.element);
        if (c < 0) {
            AVLTree<E> left = this.left.remove(element);
            if (left != this.left) {
                return balanceRight(this.element, left, this.right);
            }
        }
        else if (c > 0) {
            AVLTree<E> right = this.right.remove(element);
            if (right != this.right) {
                return balanceLeft(this.element, this.left, right);
            }
        }
        else if (this.right instanceof AVLNil) {
            return this.left;
        }
        else {
            Pair<E, AVLTree<E>> p = this.right.pollFirst();
            return balanceLeft(p.first, this.left, p.second);
        }
        return this;
    }

    @Override
    public BinaryTree<E> removeMin() {
        return remove(findMin());
    }

    @Override
    public E findMin() {
        return findMin(this);
    }

    private E findMin(AVLTree<E> e) {
        if(e.left == null) {
            return e.element;
        }
        return findMin(e.left);
    }

    @Override
    public E findMax() {
        return findMax(this);
    }

    private E findMax(AVLTree<E> e) {
        if(e.right == null) {
            return e.element;
        }
        return findMin(e.right);
    }

    @Override
     public E find(E key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        AVLTree<E> x = get(this, key);
        if (x == null) return null;
        return x.element;
    }

    /**
     * Returns value associated with the given key in the subtree or
     * {@code null} if no such key.
     *
     * @param x the subtree
     * @param key the key
     * @return value associated with the given key in the subtree or
     *         {@code null} if no such key
     */
    private AVLTree<E> get(AVLTree<E> x, E key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.element);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x;
    }

    public int index(E element) {
        if (element.compareTo(this.element) <= 0) {
            return this.left.index(element);
        }
        else {
            return this.left.size+1+this.right.index(element);
        }
    }

    public int subSize(E fromElement, E toElement) {
        if (toElement.compareTo(this.element) <= 0) {
            return this.left.subSize(fromElement, toElement);
        }
        else if (fromElement.compareTo(this.element) > 0) {
            return this.right.subSize(fromElement, toElement);
        }
        else {
            return index(toElement)-index(fromElement);
        }
    }

    protected String toString(String indent) {
        String indent1 = indent+"  ";
        return this.left.toString(indent1)
                + indent + this.element + "\n"
                + this.right.toString(indent1)
                ;
    }

    public boolean check() {
        if (!isBST()) System.out.println("Symmetric order not consistent");
        if (!isAVL()) System.out.println("AVL property not consistent");
        if (!isSizeConsistent()) System.out.println("Subtree counts not consistent");
        if (!isRankConsistent()) System.out.println("Ranks not consistent");
        return isBST() && isAVL() && isSizeConsistent() && isRankConsistent();
    }

    /**
     * Checks if AVL property is consistent.
     *
     * @return {@code true} if AVL property is consistent.
     */
    private boolean isAVL() {
        return isAVL(this);
    }

    /**
     * Checks if AVL property is consistent in the subtree.
     *
     * @param x the subtree
     * @return {@code true} if AVL property is consistent in the subtree
     */
    private boolean isAVL(AVLTree<E> x) {
        if (x == null || x.element == null) return true;
        int bf = balanceFactor(x);
        if (bf > 1 || bf < -1) return false;
        return isAVL(x.left) && isAVL(x.right);
    }

    private int balanceFactor(AVLTree<E> x) {
        return x.left.height - x.right.height;
    }

    /**
     * Checks if the symmetric order is consistent.
     *
     * @return {@code true} if the symmetric order is consistent
     */
    public boolean isBST() {
        return isBST(this, null, null);
    }

    /**
     * Checks if the tree rooted at x is a BST with all keys strictly between
     * findMin and findMax (if findMin or findMax is null, treat as empty constraint) Credit:
     * Bob Dondero's elegant solution
     *
     * @param x the subtree
     * @param min the minimum key in subtree
     * @param max the maximum key in subtree
     * @return {@code true} if if the symmetric order is consistent
     */
    private boolean isBST(AVLTree<E> x, E min, E max) {
        if (x == null || x.element == null) return true;
        if (min != null && x.element.compareTo(min) <= 0) return false;
        if (max != null && x.element.compareTo(max) >= 0) return false;
        return isBST(x.left, min, x.element) && isBST(x.right, x.element, max);
    }

    /**
     * Checks if size is consistent.
     *
     * @return {@code true} if size is consistent
     */
    private boolean isSizeConsistent() {
        return isSizeConsistent(this);
    }

    /**
     * Checks if the size of the subtree is consistent.
     *
     * @return {@code true} if the size of the subtree is consistent
     */
    private boolean isSizeConsistent(AVLTree<E> x) {
        if (x == null || x.element == null) return true;
        if (x.size != x.left.size + x.right.size + 1) return false;
        return isSizeConsistent(x.left) && isSizeConsistent(x.right);
    }

    /**
     * Checks if rank is consistent.
     *
     * @return {@code true} if rank is consistent
     */
    private boolean isRankConsistent() {
        for (int i = 0; i < this.size; i++)
            if (i != rank(select(i))) return false;
        for (E key : keys())
            if (key.compareTo(select(rank(key))) != 0) return false;
        return true;
    }

    public int rank(E key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, this);
    }

    /**
     * Returns the number of keys in the subtree less than key.
     *
     * @param key the key
     * @param x the subtree
     * @return the number of keys in the subtree less than key
     */
    private int rank(E key, AVLTree<E> x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.element);
        if (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + x.left.size + rank(key, x.right);
        else return x.left.size;
    }

    public Iterable<E> keys() {
        return keysInOrder();
    }

    /**
     * Returns all keys in the symbol table following an in-order traversal.
     *
     * @return all keys in the symbol table following an in-order traversal
     */
    public Iterable<E> keysInOrder() {
        Queue<E> queue = new PriorityQueue<>();
        keysInOrder(this, queue);
        return queue;
    }

    /**
     * Adds the keys in the subtree to queue following an in-order traversal.
     *
     * @param x the subtree
     * @param queue the queue
     */
    private void keysInOrder(AVLTree<E> x, Queue<E> queue) {
        if (x == null || x.element == null) return;
        keysInOrder(x.left, queue);
        queue.offer(x.element);
        keysInOrder(x.right, queue);
    }

    public E select(int k) {
        if (k < 0 || k >= this.size) throw new IllegalArgumentException("k is not in range 0-" + (this.size - 1));
        AVLTree<E> x = select(this, k);
        return x.element;
    }

    /**
     * Returns the node with key the kth smallest key in the subtree.
     *
     * @param x the subtree
     * @param k the kth smallest key in the subtree
     * @return the node with key the kth smallest key in the subtree
     */
    private AVLTree<E> select(AVLTree<E> x, int k) {
        if (x == null) return null;
        int t = x.left.size;
        if (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else return x;
    }
}
