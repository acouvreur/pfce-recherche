package fr.unice.polytech.pfce;

import java.util.Arrays;

class AVLTreeG<T extends Comparable<? super T>> implements BinaryTree<T> {

    protected static class AVLNode<T> {
        protected T  element;
        protected AVLNode<T> left;
        protected AVLNode<T> right;
        protected int      height;

        public AVLNode(T e){
            this (e, null, null);
        }
        public AVLNode(T theElement, AVLNode<T> lt, AVLNode<T> rt){
            element = theElement;
            left = lt;
            right = rt;
        }
    }

    public AVLNode<T> root;

    public AVLTreeG(){
        root = null;
    }

    // https://www.geeksforgeeks.org/sorted-array-to-balanced-bst/
    public AVLTreeG(T[] values) {

        Arrays.sort(values);

        root = fromSortedArray(values, 0, values.length - 1);
    }

    private AVLNode<T> fromSortedArray(T[] arr, int start, int end) {

        if(start > end) {
            return null;
        }

        int mid = (start+end) / 2;
        AVLNode<T> node = new AVLNode<>(arr[mid]);

        /* Recursively construct the left subtree and make it 
         left child of root */
        node.left = fromSortedArray(arr, start, mid - 1); 

         /* Recursively construct the right subtree and make it 
          right child of root */
        node.right = fromSortedArray(arr, mid + 1, end); 

        return node; 
    }

    public int height (AVLNode<T> t){
        return t == null ? -1 : t.height;
    }

    public int max (int a, int b){
        return a > b ? a:b;
    }

    /**
     * Insert an element into the tree.
     *
     * @param x Element to insert into the tree
     * @return True - Success, the Element was added.
     *         False - Error, the element was a duplicate.
     */
    @Override
    public boolean insert(T x){
        try {
            root = insert (x, root);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    /**
     * Internal method to perform an actual insertion.
     *
     * @param x Element to add
     * @param t Root of the tree
     * @return New root of the tree
     * @throws Exception
     */
    protected AVLNode<T> insert (T x, AVLNode<T> t) throws Exception{
        if (t == null)
            t = new AVLNode<T>(x);
        else if (x.compareTo (t.element) < 0){
            t.left = insert (x, t.left);

            if (height (t.left) - height (t.right) == 2){
                if (x.compareTo (t.left.element) < 0){
                    t = rotateWithLeftChild (t);
                }
                else {
                    t = doubleWithLeftChild (t);
                }
            }
        }
        else if (x.compareTo (t.element) > 0){
            t.right = insert (x, t.right);

            if ( height (t.right) - height (t.left) == 2)
                if (x.compareTo (t.right.element) > 0){
                    t = rotateWithRightChild (t);
                }
                else{
                    t = doubleWithRightChild (t);
                }
        }
        else {
            throw new Exception("Attempting to insert duplicate value");
        }

        t.height = max (height (t.left), height (t.right)) + 1;
        return t;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     *
     * @param k2 Root of tree we are rotating
     * @return New root
     */
    protected AVLNode<T> rotateWithLeftChild (AVLNode<T> k2){
        AVLNode<T> k1 = k2.left;

        k2.left = k1.right;
        k1.right = k2;

        k2.height = max (height (k2.left), height (k2.right)) + 1;
        k1.height = max (height (k1.left), k2.height) + 1;

        return (k1);
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     *
     * @param k3 Root of tree we are rotating
     * @return New root
     */
    protected AVLNode<T> doubleWithLeftChild (AVLNode<T> k3){
        k3.left = rotateWithRightChild (k3.left);
        return rotateWithLeftChild (k3);
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     *
     * @param k1 Root of tree we are rotating.
     * @return New root
     */
    protected AVLNode<T> rotateWithRightChild (AVLNode<T> k1){
        AVLNode<T> k2 = k1.right;

        k1.right = k2.left;
        k2.left = k1;

        k1.height = max (height (k1.left), height (k1.right)) + 1;
        k2.height = max (height (k2.right), k1.height) + 1;

        return (k2);
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     *
     * @param k1 Root of tree we are rotating
     * @return New root
     */
    protected AVLNode<T> doubleWithRightChild (AVLNode<T> k1){
        k1.right = rotateWithLeftChild (k1.right);
        return rotateWithRightChild (k1);
    }

    /**
     * Determine if the tree is empty.
     *
     * @return True if the tree is empty
     */
    @Override
    public boolean isEmpty(){
        return (root == null);
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    @Override
    public T findMin()
    {
        if( isEmpty( ) ) return null;

        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    @Override
    public T findMax()
    {
        if( isEmpty( ) ) return null;
        return findMax( root ).element;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AVLNode<T> findMin(AVLNode<T> t)
    {
        if( t == null )
            return t;

        while( t.left != null )
            t = t.left;
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AVLNode<T> findMax(AVLNode<T> t )
    {
        if( t == null )
            return t;

        while( t.right != null )
            t = t.right;
        return t;
    }


// A version of remove from http://www.dreamincode.net/forums/topic/214510-working-example-of-avl-tree-remove-method/
// but it needs some attention and does not appear to be 100% correct

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    @Override
    public void remove(T x) {
        root = remove(x, root);
    }

    public AVLNode<T> remove(T x, AVLNode<T> t) {
        if (t==null)    {
            return null;
        }

        if (x.compareTo(t.element) < 0 ) {
            t.left = remove(x,t.left);
            int l = t.left != null ? t.left.height : 0;

            if((t.right != null) && (t.right.height - l >= 2)) {
                int rightHeight = t.right.right != null ? t.right.right.height : 0;
                int leftHeight = t.right.left != null ? t.right.left.height : 0;

                if(rightHeight >= leftHeight)
                    t = rotateWithLeftChild(t);
                else
                    t = doubleWithRightChild(t);
            }
        }
        else if (x.compareTo(t.element) > 0) {
            t.right = remove(x,t.right);
            int r = t.right != null ? t.right.height : 0;
            if((t.left != null) && (t.left.height - r >= 2)) {
                int leftHeight = t.left.left != null ? t.left.left.height : 0;
                int rightHeight = t.left.right != null ? t.left.right.height : 0;
                if(leftHeight >= rightHeight)
                    t = rotateWithRightChild(t);
                else
                    t = doubleWithLeftChild(t);
            }
        }
      /*
         Here, we have ended up when we are node which shall be removed.
         Check if there is a left-hand node, if so pick out the largest element out, and move down to the root.
       */
        else if(t.left != null) {
            t.element = findMax(t.left).element;
            remove(t.element, t.left);

            if((t.right != null) && (t.right.height - t.left.height >= 2)) {
                int rightHeight = t.right.right != null ? t.right.right.height : 0;
                int leftHeight = t.right.left != null ? t.right.left.height : 0;

                if(rightHeight >= leftHeight)
                    t = rotateWithLeftChild(t);
                else
                    t = doubleWithRightChild(t);
            }
        }

        else
            t = (t.left != null) ? t.left : t.right;

        if(t != null) {
            int leftHeight = t.left != null ? t.left.height : 0;
            int rightHeight = t.right!= null ? t.right.height : 0;
            t.height = Math.max(leftHeight,rightHeight) + 1;
        }
        return t;
    } //End of remove...

    @Override
    public boolean contains(T x){
        return contains(x, root);
    }

    /**
     * Internal find method; search for an element starting at the given node.
     *
     * @param x Element to find
     * @param t Root of the tree
     * @return True if the element is found, false otherwise
     */
    protected boolean contains(T x, AVLNode<T> t) {
        if (t == null){
            return false; // The node was not found

        } else if (x.compareTo(t.element) < 0){
            return contains(x, t.left);
        } else if (x.compareTo(t.element) > 0){
            return contains(x, t.right);
        }

        return true; // Can only reach here if node was found
    }

    /***********************************************************************/
    // Diagnostic functions for the tree

    public boolean checkBalanceOfTree() {
        return checkBalanceOfTree(root);
    }

    private boolean checkBalanceOfTree(AVLNode<T> current) {

        boolean balancedRight = true, balancedLeft = true;
        int leftHeight = 0, rightHeight = 0;

        if (current.right != null) {
            balancedRight = checkBalanceOfTree(current.right);
            rightHeight = getDepth(current.right);
        }

        if (current.left != null) {
            balancedLeft = checkBalanceOfTree(current.left);
            leftHeight = getDepth(current.left);
        }

        return balancedLeft && balancedRight && Math.abs(leftHeight - rightHeight) < 2;
    }

    public int getDepth(AVLNode<T> n) {
        int leftHeight = 0, rightHeight = 0;

        if (n.right != null)
            rightHeight = getDepth(n.right);
        if (n.left != null)
            leftHeight = getDepth(n.left);

        return Math.max(rightHeight, leftHeight)+1;
    }

    public boolean checkOrderingOfTree() {

        return checkOrderingOfTree(root, root.element);

    }

    private boolean checkOrderingOfTree(AVLNode<T> current, T borne) {
        if(current.left != null) {
            if(current.left.element.compareTo(current.element) > 0)
                return false;
            else
                return checkOrderingOfTree(current.left, current.element);
        } else  if(current.right != null) {
            if(current.right.element.compareTo(borne) < 0 // Chek upper bound
            && current.right.element.compareTo(current.element) < 0)
                return false;
            else
                return checkOrderingOfTree(current.right, current.element);
        } else if(current.left == null && current.right == null)
            return true;

        return true;
    }
}
