package fr.unice.polytech.pfce;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * AVLTreeTest
 */
public class ImmutableAVLTreeTest {

    private ImmutableAVLTree<Integer> t;
    private final int[] values = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Before
    public void setUp() {
        t = new AVLNil<>();
    }

    @After
    public void checkValidity() {

        if(t.element == null) return;

        //assertTrue("Tree should be balanced", t.checkBalanceOfTree());
        //assertTrue("Tree should be ordered", t.checkOrderingOfTree());
    }

    /*@Test
    public void createEmptyTree() {
        assertTrue(t.isEmpty());
    }*/

    /*@Test
    public void createTreeFromInput() {
        t = new AVLNil(values);
    }*/

    @Test
    public void insertValue() {

        int n = 10;
        int[] a = new int[n];

        for (int i = 0; i < n; ++i) {
            a[i] = i;
        }

        ShuffleArray.shuffleArray(a);

        for(int i = 0; i < n; i++) {
            t = t.add(a[i]);
            BTreePrinter.printNode(t);
            // checkValidity();
        }

    }

    @Test
    public void removeValue() {
        int n = 10;
        int[] a = new int[n];

        for (int i = 0; i < n; ++i) {
            a[i] = i;
        }

        ShuffleArray.shuffleArray(a);

        for(int i = 0; i < n; i++) {
            t = t.add(a[i]);
            BTreePrinter.printNode(t);
            // checkValidity();
        }

        ShuffleArray.shuffleArray(a);

        for(int i = 0; i < n; i++) {
            t = t.remove(a[i]);
            BTreePrinter.printNode(t);
            // checkValidity();
        }
    }
}