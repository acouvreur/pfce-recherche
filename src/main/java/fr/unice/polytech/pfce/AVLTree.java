package fr.unice.polytech.pfce;

import java.util.Arrays;

class AVLTree {

    protected static class AVLNode {
        protected int element;
        protected AVLNode left;
        protected AVLNode right;
        protected int height;

        public AVLNode(int e) {
            this(e, null, null);
        }

        public AVLNode(int theElement, AVLNode lt, AVLNode rt) {
            element = theElement;
            left = lt;
            right = rt;
        }
    }

    public AVLNode root;

    public AVLTree() {
        root = null;
    }

    // https://www.geeksforgeeks.org/sorted-array-to-balanced-bst/
    public AVLTree(int[] values) {

        Arrays.sort(values);

        root = fromSortedArray(values, 0, values.length - 1);
    }

    private AVLNode fromSortedArray(int[] arr, int start, int end) {

        if (start > end) {
            return null;
        }

        int mid = (start + end) / 2;
        AVLNode node = new AVLNode(arr[mid]);

        node.left = fromSortedArray(arr, start, mid - 1);
        node.right = fromSortedArray(arr, mid + 1, end);

        return node;
    }

    public int height(AVLNode t) {
        return t == null ? -1 : t.height;
    }

    public int max(int a, int b) {
        return a > b ? a : b;
    }

    public boolean insert(int x) {
        try {
            root = insert(x, root);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected AVLNode insert(int x, AVLNode t) throws Exception {
        if (t == null)
            t = new AVLNode(x);
        else if (x < t.element) {
            t.left = insert(x, t.left);

            if (height(t.left) - height(t.right) == 2) {
                if (x < t.left.element) {
                    t = rotateWithLeftChild(t);
                } else {
                    t = doubleWithLeftChild(t);
                }
            }
        } else if (x > t.element) {
            t.right = insert(x, t.right);

            if (height(t.right) - height(t.left) == 2)
                if (x > t.right.element) {
                    t = rotateWithRightChild(t);
                } else {
                    t = doubleWithRightChild(t);
                }
        } else {
            throw new Exception("Attempting to insert duplicate value");
        }

        t.height = max(height(t.left), height(t.right)) + 1;
        return t;
    }

    protected AVLNode rotateWithLeftChild(AVLNode k2) {
        AVLNode k1 = k2.left;

        k2.left = k1.right;
        k1.right = k2;

        k2.height = max(height(k2.left), height(k2.right)) + 1;
        k1.height = max(height(k1.left), k2.height) + 1;

        return (k1);
    }

    protected AVLNode doubleWithLeftChild(AVLNode k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    protected AVLNode rotateWithRightChild(AVLNode k1) {
        AVLNode k2 = k1.right;

        k1.right = k2.left;
        k2.left = k1;

        k1.height = max(height(k1.left), height(k1.right)) + 1;
        k2.height = max(height(k2.right), k1.height) + 1;

        return (k2);
    }

    protected AVLNode doubleWithRightChild(AVLNode k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public int findMin() {
        return findMin(root).element;
    }

    public int findMax() {
        return findMax(root).element;
    }

    private AVLNode findMin(AVLNode t) {
        if (t == null)
            return t;

        while (t.left != null)
            t = t.left;
        return t;
    }

    private AVLNode findMax(AVLNode t) {
        if (t == null)
            return t;

        while (t.right != null)
            t = t.right;
        return t;
    }

    public void remove(int x) {
        root = remove(x, root);
    }

    public AVLNode remove(int x, AVLNode t) {

        if (t == null) {
            return null;
        }

        if (x < t.element)
            t.left = remove(x, t.left);
        else if (x > t.element)
            t.right = remove(x, t.right);
        else {
            // node with only one child or no child
            if ((t.left == null) || (t.right == null)) {
                AVLNode temp = null;
                if (temp == t.left)
                    temp = t.right;
                else
                    temp = t.left;

                if (temp == null) {
                    temp = t;
                    t = null;
                } else // One child case
                    t = temp;
            } else {
                AVLNode temp = findMin(t.right);

                t.element = temp.element;
                t.right = remove(temp.element, t.right);
            }
        }

        // If the tree had only one node then return  
        if (t == null)  
            return t;  
  
        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE  
        t.height = max(height(t.left), height(t.right)) + 1;  
  
        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether  
        // this node became unbalanced)  
        int balance = getDepth(t);  
  
        // If this node becomes unbalanced, then there are 4 cases  
        // Left Left Case  
        if (balance > 1 && getDepth(t.left) >= 0)  
            return rotateWithRightChild(t);  
  
        // Left Right Case  
        if (balance > 1 && getDepth(t.left) < 0)  
        {  
            t.left = rotateWithLeftChild(t.left);  
            return rotateWithRightChild(t);  
        }  
  
        // Right Right Case  
        if (balance < -1 && getDepth(t.right) <= 0)  
            return rotateWithLeftChild(t);  
  
        // Right Left Case  
        if (balance < -1 && getDepth(t.right) > 0)  
        {  
            t.right = rotateWithRightChild(t.right);  
            return rotateWithLeftChild(t);  
        }  
  
        return t;
    }

    public boolean contains(int x) {
        return contains(x, root);
    }

    /**
     * Internal find method; search for an element starting at the given node.
     *
     * @param x Element to find
     * @param t Root of the tree
     * @return True if the element is found, false otherwise
     */
    protected boolean contains(int x, AVLNode t) {
        if (t == null) {
            return false;

        } else if (x < t.element) {
            return contains(x, t.left);
        } else if (x > t.element) {
            return contains(x, t.right);
        }

        return true;
    }

    /***********************************************************************/
    // Diagnostic functions for the tree

    public boolean checkBalanceOfTree() {
        return checkBalanceOfTree(root);
    }

    private boolean checkBalanceOfTree(AVLNode current) {

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

    public int getDepth(AVLNode n) {
        int leftHeight = 0, rightHeight = 0;

        if (n.right != null)
            rightHeight = getDepth(n.right);
        if (n.left != null)
            leftHeight = getDepth(n.left);

        return Math.max(rightHeight, leftHeight) + 1;
    }

    public boolean checkOrderingOfTree() {

        return checkOrderingOfTree(root, root.element);

    }

    private boolean checkOrderingOfTree(AVLNode current, int borne) {
        if (current.left != null) {
            if (current.left.element > current.element)
                return false;
            else
                return checkOrderingOfTree(current.left, current.element);
        } else if (current.right != null) {
            if (current.right.element < borne // Check upper bound
                    && current.right.element < current.element)
                return false;
            else
                return checkOrderingOfTree(current.right, current.element);
        } else if (current.left == null && current.right == null)
            return true;

        return true;
    }
}
