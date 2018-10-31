package fr.unice.polytech.pfce.btree;

import static org.junit.Assert.assertTrue;

import fr.unice.polytech.pfce.immutable.AVLNil;
import fr.unice.polytech.pfce.immutable.AVLTree;
import fr.unice.polytech.pfce.AVLTreeST;
import fr.unice.polytech.pfce.ShuffleArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * AVLTreeTest
 */
public class AVLTreeImmutableTest {

    private AVLTree<Integer> ti;
    private final int[] values = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    @Before
    public void setUp() {
        ti = new AVLNil<>();
    }

    @After
    public void checkValidity() {

        assertTrue(ti.check());
    }

    @Test
    public void createEmptyTree() {
        // assertTrue(ti.isEmpty());
    }

    /*@Test
    public void createTreeFromInput() {
        t = new AVLTree(values);
    }*/

    @Test
    public void insertValue() {

        int n = 1000;
        int[] a = new int[n];

        for (int i = 0; i < n; ++i) {
            a[i] = i;
        }

        ShuffleArray.shuffleArray(a);

        for(int i = 0; i < n; i++) {
            ti = ti.insert(a[i]);
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
            ti = ti.insert(a[i]);
            checkValidity();
        }

        ShuffleArray.shuffleArray(a);

        for(int i = 0; i < n; i++) {
            ti = ti.remove(a[i]);
        }
    }
}