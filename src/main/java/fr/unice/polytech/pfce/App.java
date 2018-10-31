package fr.unice.polytech.pfce;


import fr.unice.polytech.pfce.immutable.AVLNil;
import fr.unice.polytech.pfce.immutable.AVLNode;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class App {

    private static final int NB_REPEAT = 200;
    private static final int POW_TO = 14;
    private static final Random randomGenerator = new Random();

    private static long tStart;
    private static long tEnd;
    private static BinaryTree<Integer> avlNil = new AVLNil<>();

    public static void main( String[] args ) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        for (int j = 0; j < 1000; j++) {
            System.out.println("On chauffe la jvm");
        }
        // XYChart chart = QuickChart.getChart("SCC Solvers Benchmark", "Graph density (Edges/V²)", "Time to solve (ms));
        XYChart insertion = new XYChartBuilder().width(1920).height(1080).title("Comparaison entre tas et arbres de recherche pour une file de priorité : Insertion").xAxisTitle("Nombre d'éléments").yAxisTitle("Temps de l'opération (ns)").theme(Styler.ChartTheme.GGPlot2).build();
        XYChart findMin = new XYChartBuilder().width(1920).height(1080).title("Comparaison entre tas et arbres de recherche pour une file de priorité : Recherche").xAxisTitle("Nombre d'éléments").yAxisTitle("Temps de l'opération (ns)").theme(Styler.ChartTheme.GGPlot2).build();
        XYChart remove = new XYChartBuilder().width(1920).height(1080).title("Comparaison entre tas et arbres de recherche pour une file de priorité : Suppresion du minimum").xAxisTitle("Nombre d'éléments supprimés").yAxisTitle("Temps de l'opération (ns)").theme(Styler.ChartTheme.GGPlot2).build();
        XYChart creationSorted = new XYChartBuilder().width(1920).height(1080).title("Comparaison entre tas et arbres de recherche pour une file de priorité : Création à partir d'un tableau trié").xAxisTitle("Taille du tableau").yAxisTitle("Temps de l'opération (ns)").theme(Styler.ChartTheme.GGPlot2).build();

        insertion.getStyler().setPlotGridLinesVisible(false);
        insertion.getStyler().setPlotGridHorizontalLinesVisible(false);
        insertion.getStyler().setYAxisLogarithmic(true);
        insertion.getStyler().setXAxisLogarithmic(true);
        findMin.getStyler().setXAxisLogarithmic(true);
        findMin.getStyler().setYAxisLogarithmic(true);
        remove.getStyler().setYAxisLogarithmic(true);
        remove.getStyler().setXAxisLogarithmic(true);
        Integer[] data = generateData((int) Math.pow(2, POW_TO) - 1);
        int[] axis = fillFrom1To((int) Math.pow(2, POW_TO) - 1);

        //---------------Insertion----------------------//
        int[][] moy = computeInsertionAndFind(avlNil, AVLNil.class, NB_REPEAT, data);
        // System.out.println(Arrays.toString(moy));
        // insertion.addSeries("AVLImmutable", axis, moy[0]);
        findMin.addSeries("AVLImmutable", axis, moy[1]);

        BinaryTree<Integer> avlFullCopy = avlNil;

        BinaryTree<Integer> avl = new AVLTreeST<>();
        moy = computeInsertionAndFind(avl,avl.getClass(),  NB_REPEAT, data);
        // System.out.println(Arrays.toString(moy));
        insertion.addSeries("AVLMutable", axis, moy[0]);
        findMin.addSeries("AVLMutable", axis, moy[1]);

        BinaryTree<Integer> redblack = new RedBlackBST<>();
        moy = computeInsertionAndFind(redblack, redblack.getClass(), NB_REPEAT, data);
        // System.out.println(Arrays.toString(moy));
        insertion.addSeries("RedBlack", axis, moy[0]);
        findMin.addSeries("RedBlack", axis, moy[1]);

        HeapMin heapMin = new HeapMin();
        moy = computeInsertionAndFind(heapMin, heapMin.getClass(), NB_REPEAT, data);
        // System.out.println(Arrays.toString(moy));
        insertion.addSeries("Heap", axis, moy[0]);
        findMin.addSeries("Heap", axis, moy[1]);

        new SwingWrapper<>(insertion).displayChart();
        new SwingWrapper<>(findMin).displayChart();
        //---------------SuppressionMin----------------------//
        // We go from n to 1
        // reverseArray(axis);
        int[] moy2 = computeRemoveMin(avlNil, avlFullCopy, NB_REPEAT, data);
        // System.out.println(Arrays.toString(moy));
        remove.addSeries("AVLImmutable", axis, moy2);

        moy2 = computeRemoveMin(avl, avl.getClass(), NB_REPEAT, data);
        // System.out.println(Arrays.toString(moy));
        remove.addSeries("AVLMutable", axis, moy2);

        moy2 = computeRemoveMin(redblack, redblack.getClass(), NB_REPEAT, data);
        // System.out.println(Arrays.toString(moy));
        remove.addSeries("RedBlack", axis, moy2);

        moy2 = computeRemoveMin(heapMin, NB_REPEAT, data);
        // System.out.println(Arrays.toString(moy));
        remove.addSeries("Heap", axis, moy2);

        new SwingWrapper<>(remove).displayChart();
    }

    private static Integer[] generateData(int size) {
        Integer[] a = new Integer[size];

        for (int i = 0; i < size; ++i) {
            a[i] = i;
        }

        ShuffleArray.shuffleArray(a);

        return a;
    }

    private static <T extends BinaryTree<Integer>> int[][] computeInsertionAndFind(BinaryTree<Integer> tree, Class<T> cz, int tries, Integer[] data) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        int[] min = new int[data.length];
        int[][] moy = new int[2][data.length];
        int[][] cum = new int[2][data.length];
        int[] max = new int[data.length];

        for (int i = 0; i < tries; i++) {

            for (int j = 0; j < data.length; j++) {
                tStart = System.nanoTime();
                tree = tree.insert(data[j]);
                tEnd = System.nanoTime();
                int computed = (int) (tEnd - tStart);

                if(i == 0) min[j] = computed;
                else min[j] = min(min[j], computed);
                moy[0][j] += computed;
                max[j] = max(max[j], computed);

                tStart = System.nanoTime();
                tree.findMin();
                tEnd = System.nanoTime();
                computed = (int) (tEnd - tStart);
                moy[1][j] += computed;

            }
            tree = cz.getConstructor().newInstance();
        }

        cum[0][0] = moy[0][0] /= tries;
        cum[1][0] = moy[1][0] /= tries;
        for (int j = 1; j < data.length-1; j++) {
            moy[0][j-1] /= tries;
            moy[1][j-1] /= tries;
            cum[0][j] = cum[0][j-1] + moy[0][j];
            cum[1][j] = cum[1][j-1] + moy[1][j];
        }
        cum[0][data.length-1] = cum[0][data.length-2] + moy[0][data.length-1];
        cum[1][data.length-1] = cum[1][data.length-2] + moy[1][data.length-1];

        if(cz.getName().equals("AVLNil"))
            avlNil = tree;
        return cum;
    }

    private static <T extends BinaryTree<Integer>> int[] computeRemoveMin(BinaryTree<Integer> tree, Class<T> cz, int tries, Comparable[] data) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        int[] min = new int[data.length];
        int[] moy = new int[data.length];
        int[] cum = new int[data.length];
        int[] max = new int[data.length];

        for (int i = 0; i < tries; i++) {

            for (int j = 0; j < data.length; j++) {
                tStart = System.nanoTime();
                tree = tree.removeMin(); // should be optimized
                tEnd = System.nanoTime();
                int computed = (int) (tEnd - tStart);

                if(i == 0) min[j] = computed;
                else min[j] = min(min[j], computed);
                moy[j] += computed;
                max[j] = max(max[j], computed);

            }
            tree = cz.getConstructor(Comparable[].class).newInstance((Object) data);
        }

        cum[0] = moy[0] /= tries;
        for (int j = 1; j < data.length-1; j++) {
            moy[j-1] /= tries;
            cum[j] = cum[j-1] + moy[j];
        }
        cum[data.length-1] = cum[data.length-2] + moy[data.length-1];

        return cum;
    }

    private static int[] computeRemoveMin(BinaryTree<Integer> tree, BinaryTree<Integer> full, int tries, Integer[] data) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        int[] min = new int[data.length];
        int[] moy = new int[data.length];
        int[] cum = new int[data.length];
        int[] max = new int[data.length];

        for (int i = 0; i < tries; i++) {

            for (int j = 0; j < data.length; j++) {
                tStart = System.nanoTime();
                tree = tree.removeMin(); // should be optimized
                tEnd = System.nanoTime();
                int computed = (int) (tEnd - tStart);

                if(i == 0) min[j] = computed;
                else min[j] = min(min[j], computed);
                moy[j] += computed;
                max[j] = max(max[j], computed);

            }
            tree = full;
        }

        cum[0] = moy[0] /= tries;
        for (int j = 1; j < data.length-1; j++) {
            moy[j-1] /= tries;
            cum[j] = cum[j-1] + moy[j];
        }
        cum[data.length-1] = cum[data.length-2] + moy[data.length-1];

        return cum;
    }

    private static int[] computeRemoveMin(HeapMin tree, int tries, Integer[] data) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        int[] min = new int[data.length];
        int[] moy = new int[data.length];
        int[] cum = new int[data.length];
        int[] max = new int[data.length];

        for (int i = 0; i < tries; i++) {

            for (int j = 0; j < data.length; j++) {
                tStart = System.nanoTime();
                tree = (HeapMin) tree.removeMin(); // should be optimized
                tEnd = System.nanoTime();
                int computed = (int) (tEnd - tStart);

                if(i == 0) min[j] = computed;
                else min[j] = min(min[j], computed);
                moy[j] += computed;
                max[j] = max(max[j], computed);

            }
            tree = new HeapMin(data);
        }

        cum[0] = moy[0] /= tries;
        for (int j = 1; j < data.length-1; j++) {
            moy[j-1] /= tries;
            cum[j] = cum[j-1] + moy[j];
        }
        cum[data.length-1] = cum[data.length-2] + moy[data.length-1];

        return cum;
    }

    public static int[] fillFrom1To(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = i+1;
        }
        return a;
    }

    public static void reverseArray(int[] arr) {
        for(int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
    }

}
