package fr.unice.polytech.pfce;

abstract class ImmutableAVLTree<E extends Comparable<E>> {

    protected final E element;
    protected final ImmutableAVLTree<E> left, right;
    protected final int height;
    public final int size;

    protected ImmutableAVLTree(E element, ImmutableAVLTree<E> left, ImmutableAVLTree<E> right, int height, int size) {
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = height;
        this.size = size;
    }

    public abstract ImmutableAVLTree<E> add(E element);
    public abstract Pair<E, ImmutableAVLTree<E>> pollFirst();
    public abstract ImmutableAVLTree<E> remove(E element);
    public abstract int index(E element);
    public abstract int subSize(E fromElement, E toElement);

    public boolean checkBalanceOfTree() {
        System.out.println(this.toString());
        return checkBalanceOfTree(this);
    }

    private boolean checkBalanceOfTree(ImmutableAVLTree current) {
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

    public int getDepth(ImmutableAVLTree n) {
        int leftHeight = 0, rightHeight = 0;

        if (n.right != null)
            rightHeight = getDepth(n.right);
        if (n.left != null)
            leftHeight = getDepth(n.left);

        return Math.max(rightHeight, leftHeight) + 1;
    }

    public boolean checkOrderingOfTree() {

        return checkOrderingOfTree(this, element);

    }

    private boolean checkOrderingOfTree(ImmutableAVLTree current, Comparable borne) {
        if (current.left != null) {
            if (current.left.element.compareTo(current.element) > 0)
                return false;
            else
                return checkOrderingOfTree(current.left, current.element);
        } else if (current.right != null) {
            if (current.right.element.compareTo(borne) < 0 // Check upper bound
                    && current.right.element.compareTo(current.element) < 0)
                return false;
            else
                return checkOrderingOfTree(current.right, current.element);
        } else if (current.left == null && current.right == null)
            return true;

        return true;
    }

    protected abstract String toString(String indent);

    public String toString() {
        return this.toString("");
    }

}

final class Pair<A, B> {
    public final A first;
    public final B second;

    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }

}

final class AVLNil<E extends Comparable<E>> extends ImmutableAVLTree<E> {

    public AVLNil() {
        super(null, null, null, 0, 0);
    }

    public ImmutableAVLTree<E> add(E element) {
        return new AVLNode<E>(element, this, this);
    }

    public Pair<E, ImmutableAVLTree<E>> pollFirst() {
        return new Pair<E, ImmutableAVLTree<E>>(null, this);
    }

    public ImmutableAVLTree<E> remove(E element) {
        return this;
    }

    public int index(E element) {
        return 0;
    }

    public int subSize(E fromElement, E toElement) {
        return 0;
    }

    protected String toString(String indent) {
        return indent + "$\n";
    }

}

final class AVLNode<E extends Comparable<E>> extends ImmutableAVLTree<E> {

    public AVLNode(E element, ImmutableAVLTree<E> left, ImmutableAVLTree<E> right) {
        super(element, left, right, Math.max(left.height, right.height)+1, left.size+right.size+1);
    }

    private ImmutableAVLTree<E> balanceLeft(E element, ImmutableAVLTree<E> left, ImmutableAVLTree<E> right) {
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

    private ImmutableAVLTree<E> balanceRight(E element, ImmutableAVLTree<E> left, ImmutableAVLTree<E> right) {
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

    public ImmutableAVLTree<E> add(E element) {
        int c = element.compareTo(this.element);
        if (c < 0) {
            ImmutableAVLTree<E> left = this.left.add(element);
            if (left != this.left) {
                return balanceLeft(this.element, left, this.right);
            }
        }
        else if (c > 0) {
            ImmutableAVLTree<E> right = this.right.add(element);
            if (right != this.right) {
                return balanceRight(this.element, this.left, right);
            }
        }
        return this;
    }

    public Pair<E, ImmutableAVLTree<E>> pollFirst() {
        if (this.left instanceof AVLNil) {
            return new Pair<E, ImmutableAVLTree<E>>
                    ( this.element
                            , this.right
                    )
                    ;
        } else {
            Pair<E, ImmutableAVLTree<E>> p = this.left.pollFirst();
            return new Pair<E, ImmutableAVLTree<E>>
                    ( p.first
                            , balanceRight(this.element, p.second, this.right)
                    )
                    ;
        }
    }

    public ImmutableAVLTree<E> remove(E element) {
        int c = element.compareTo(this.element);
        if (c < 0) {
            ImmutableAVLTree<E> left = this.left.remove(element);
            if (left != this.left) {
                return balanceRight(this.element, left, this.right);
            }
        }
        else if (c > 0) {
            ImmutableAVLTree<E> right = this.right.remove(element);
            if (right != this.right) {
                return balanceLeft(this.element, this.left, right);
            }
        }
        else if (this.right instanceof AVLNil) {
            return this.left;
        }
        else {
            Pair<E, ImmutableAVLTree<E>> p = this.right.pollFirst();
            return balanceLeft(p.first, this.left, p.second);
        }
        return this;
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

}
