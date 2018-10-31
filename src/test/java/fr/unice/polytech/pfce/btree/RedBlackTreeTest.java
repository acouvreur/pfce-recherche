package fr.unice.polytech.pfce.btree;

import static org.junit.Assert.assertTrue;

import fr.unice.polytech.pfce.RedBlackBST;
import fr.unice.polytech.pfce.ShuffleArray;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * AVLTreeTest
 */
public class RedBlackTreeTest {

    private RedBlackBST<Integer> t;
    private final int[] values = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};

    @Before
    public void setUp() {
        t = new RedBlackBST<>();
    }

    @After
    public void checkValidity() {
        assertTrue(t.check());
    }

    @Test
    public void createEmptyTree() {
        assertTrue(t.isEmpty());
    }

    @Test
    public void createTreeFromInput() {
        t = new RedBlackBST<>(Arrays.stream( values ).boxed().toArray( Integer[]::new ));
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
        int n = 10;
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