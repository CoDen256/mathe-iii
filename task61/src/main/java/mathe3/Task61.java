package mathe3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Task61 {

    // GIVEN
    private static final double[][] A = {
            {17, 3, 2, -3},
            {4, 13, 1, 6},
            {5, -3, -19, 2},
            {1, 4, 5, 11}
    };

    private static final double[] B = {3, -2, 7, 9};
    private static final int M = B.length; // matrix dimension

    private static final double[] actualX = {
            2111/4183., -3344/4183., 10/4183., 4442/4183.
    }; // actual solution


    private static final double[][] startSamples = {
            {10, 10, 10, 10},
            {5, 5, 5, 5},
            {2, 2, 2, 2},
            {0, 0, 0, 0},
            {0.5, -0.8, 0, 1.1},
    } ;

    public static void main(String[] args) {

        for (int i = 0; i < startSamples.length; i++) {
            List<List<Double>> vectorIterations = computeVectors(
                        startSamples[i], 9);
            plot(vectorIterations).save("sample"+i);
        }
    }

    private static Plotter plot(List<List<Double>> vectorIterations) {
        List<List<Point>> xIterations = IntStream.range(0, vectorIterations.get(0).size())
                .mapToObj(x -> IntStream.range(0, vectorIterations.size())
                        .mapToObj(iteration -> new Point(iteration, vectorIterations.get(iteration).get(x)))
                        .collect(Collectors.toList())
                ).collect(Collectors.toList());

        int maxX = vectorIterations.size();
//        double maxY = vectorIterations.stream().flatMap(List::stream)
//                .max(Double::compareTo).get();
//        double minY = vectorIterations.stream().flatMap(List::stream)
//                .min(Double::compareTo).get();
        Plotter plot = new Plotter(new double[]{0, maxX, -1.25, 1.25}, maxX * 50000, 0.001)
                .points(xIterations.get(0), Color.BLUE)
                .points(xIterations.get(1), Color.ORANGE)
                .points(xIterations.get(2), Color.RED)
                .points(xIterations.get(3), Color.GREEN)
                .function(x -> actualX[0], Color.BLUE)
                .function(x -> actualX[1], Color.ORANGE)
                .function(x -> actualX[2], Color.RED)
                .function(x -> actualX[3], Color.GREEN);
        return plot;
    }

    private static List<List<Double>> computeVectors(double[] startVector, int total_vectors) {
        List<List<Double>> vectors = new ArrayList<>(); // computed vectors
        // add start vector to all vectors
        vectors.add(Arrays.stream(startVector).boxed().collect(Collectors.toList()));

        for (int i = 1; i < total_vectors + 1; i++) { // number of vectors
            List<Double> vectorsI = new ArrayList<>(); // vector for i iteration
            for (int k = 0; k < M; k++) {
                vectorsI.add(computeNextX(i, k, vectors)); // xk(i)
            }
            vectors.add(vectorsI);
        }
        return vectors;
    }

    // xk (i)
    private static double computeNextX(int i, int k, List<List<Double>> computedVectors) {
        double sum = 0;
        for (int j = 0; j < M; j++) {
            if (j == k) continue;
            sum += a(k, j) * computedVectors.get(i - 1).get(j); // xj(i-1)
        }
        return -1 / a(k, k) * (-b(k) + sum);
    }


    private static double a(int i, int j) {
        return A[i][j];
    }

    private static double b(int i) {
        return B[i];
    }
}
