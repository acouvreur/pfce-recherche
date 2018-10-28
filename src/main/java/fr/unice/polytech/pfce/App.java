package fr.unice.polytech.pfce;

/**
 * Hello world!
 *
 */
public class App 
{

    private static final Integer[] values = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public static void main( String[] args )
    {
        AVLTree<Integer> t = new AVLTree<>(values);
        System.out.println ("Infix Traversal:");
        System.out.println(t.serializeInfix());

        System.out.println ("Prefix Traversal:");
        System.out.println(t.serializePrefix());
    }
}
