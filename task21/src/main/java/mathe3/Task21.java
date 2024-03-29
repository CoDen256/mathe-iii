package mathe3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Task21 {

    private static final double START = 0;
    private static final double END = 2;
    private static final int N = 5000;
    private static final double APPROXIMATE_LENGTH = 29.608;

    public static void main(String[] args) {

        List<Point> points = generateEvenly(START, END, N)
                .mapToObj(Task21::calculatePoint)
                .collect(Collectors.toList());

        System.out.println(points);
        System.out.println(calculateLengthBetweenPoints(points));

   
        List<Double> pts = new ArrayList<>();
        double step = (END - START)/N;
        double current = START;
        Point prev = calculatePoint(0);
        for (int i = 1; i < N; i++) {
            Point point = calculatePoint(i);

            double length = length(prev, point);
            while (length > 0.1){

            }

            prev = point;
        }

    }


    private static DoubleStream generateEvenly(double start, double end, int n) {
        double step = (end - start) / n;
        return DoubleStream
                .iterate(start, s -> Math.max(start, Math.min(end, s + step))) // bound t to be start <= n <= end
                .limit(n + 1);
    }

    private static double calculateLengthBetweenPoints(List<Point> points){
        double sum = 0;
        Point prev = null;
        for (Point point : points){
            if (prev != null){
                double length = length(point, prev);
                sum += length;
                System.out.printf("Distance between %s - %s: %n", prev.toString(), point.toString());
                print("l", length);
                print("s", sum);
                System.out.println();
            }
            prev = point;
        }
        return sum;
    }

    private static Point calculatePoint(double t) {
        double x = x(t);
        double y = y(t);
        double z = z(t);

        print("t", t);
        print("x", x);
        print("y", y);
        print("z", z);
        System.out.println();
        return new Point(x, y, z, t);
    }

    private static double length(Point point1, Point point2){
        return Math.sqrt(
                Math.pow(point1.x - point2.x, 2) +
                Math.pow(point1.y - point2.y, 2) +
                Math.pow(point1.z - point2.z, 2)
        );
    }

    private static void print(String name, double number){
        System.out.printf("%s=%s%n", name, BigDecimal.valueOf(number).toPlainString());
    }

    private static double x(double t) {
        return 3 * Math.cos(Math.PI * t);
    }

    private static double y(double t) {
        return 3 * Math.sin(Math.PI * t);
    }

    private static double z(double t) {
        return 3 * Math.PI * Math.sqrt(4 - t*t);
    }

    private static class Point {
        public final double x;
        public final double y;
        public final double z;
        public final double t;

        public Point(double x, double y, double z, double t) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.t = t;
        }

        public String toString(){
            return String.format("(%f:%f, %f, %f)", t, x, y, z);
        }
    }
}
