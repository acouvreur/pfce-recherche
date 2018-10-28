package fr.unice.polytech.pfce;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * AVLTreeTest
 */
public class AVLTreeTest {

    private AVLTree t;
    private final int[] values = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Before
    public void setUp() {
        t = new AVLTree();
    }

    @After
    public void checkValidity() {

        if(t.root == null) return;

        assertTrue("Tree should be balanced", t.checkBalanceOfTree());
        assertTrue("Tree should be ordered", t.checkOrderingOfTree());
    }

    @Test
    public void createEmptyTree() {
        assertTrue(t.isEmpty());
    }

    @Test 
    public void createTreeFromInput() {
        t = new AVLTree(values);
    }

    @Test
    public void insertValue() {

        int n = 1000;
        int[] a = new int[n];

        for (int i = 0; i < n; ++i) {
            a[i] = i;
        }

        ShuffleArray.shuffleArray(a);

        for(int i = 0; i < n; i++) {
            t.insert(a[i]);
            checkValidity();
        }
        
    }

    @Test
    public void removeValue() {
        int n = 1000;
        int[] a = new int[n];

        for (int i = 0; i < n; ++i) {
            a[i] = i;
        }

        ShuffleArray.shuffleArray(a);

        for(int i = 0; i < n; i++) {
            t.insert(a[i]);
            checkValidity();
        }

        ShuffleArray.shuffleArray(a);

        for(int i = 0; i < n; i++) {
            t.remove(a[i]);
            checkValidity();
        }
    }
}