package mathe3;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;

public class Plotter {

    private static final int HEIGHT = 768;
    private static final int WIDTH = 768;
    private final Random random = new Random();

    private final Plot plot;
    private final double step;
    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;

    public Plotter(List<Point> points, int nPoints, double padding) {
        this(calculateBounds(points), nPoints, padding);
        points(points, Color.BLUE);
    }

    public Plotter(double[] minMax, int nPoints, double padding) {
        minX = minMax[0];
        maxX = minMax[1];
        minY = minMax[2] - padding;
        maxY = minMax[3] + padding;
        step = (maxX - minX) / nPoints;
        plot = Plot
                .plot(Plot.plotOpts().height(HEIGHT).width(WIDTH))
                .xAxis("iter", Plot.axisOpts().range(minX, maxX))
                .yAxis("x", Plot.axisOpts().range(minY, maxY))
                .series(randomName(), Plot.data()
                        .xy(0, minY)
                        .xy(0, maxY), Plot.seriesOpts().line(Plot.Line.DASHED).lineWidth(2).color(Color.BLACK))
                .series(randomName(), Plot.data()
                        .xy(minX, 0)
                        .xy(maxX, 0), Plot.seriesOpts().line(Plot.Line.DASHED).lineWidth(2).color(Color.BLACK));
    }

    private String randomName() {
        return UUID.randomUUID().toString();
    }

    private Color randomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    private static double[] calculateBounds(List<Point> points) {
        double xMin = 0;
        double xMax = 0;
        double yMin = 0;
        double yMax = 0;
        for (Point point : points) {
            if (point.x > xMax) xMax = point.x;
            if (point.x < xMin) xMin = point.x;
            if (point.y < yMin) yMin = point.y;
            if (point.y > yMax) yMax = point.y;
        }
        double maxDiff = Math.max(xMax - xMin, yMax - yMin);
        return new double[]{xMin, xMin + maxDiff, yMin, yMin + maxDiff};
    }

    public Plotter function(DoubleUnaryOperator fx, Color seriesColor) {
        return function(fx, minX, maxX, seriesColor);
    }

    public Plotter functionExceptRange(DoubleUnaryOperator px, double left, double right) {
        Plot.Data leftData = calculateFunctionPoints(px, minX, left, 0.05);
        Plot.Data rightData = calculateFunctionPoints(px, right, maxX, 0.05);

        Plot.DataSeriesOptions opts = Plot
                .seriesOpts()
                .color(Color.GRAY)
                .line(Plot.Line.DASHED)
                .lineWidth(1);
        plot.series(randomName(), leftData, opts);
        plot.series(randomName(), rightData, opts);
        return this;
    }

    public Plotter function(DoubleUnaryOperator px, double left, double right, Color seriesColor) {
        Plot.Data data = calculateFunctionPoints(px, left, right, step);
        plot.series(randomName(), data, Plot.seriesOpts().color(seriesColor));
        return this;
    }

    private Plot.Data calculateFunctionPoints(DoubleUnaryOperator px, double left, double right, double step) {
        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();
        for (double x = left; x <= right; x += step) {
            xs.add(x);
            ys.add(px.applyAsDouble(x));
        }
        return Plot.data().xy(xs, ys);
    }

    public Plotter point(Point point) {
        return points(List.of(point), Color.BLUE);
    }

    public Plotter points(List<Point> points, Color blue) {
        List<Double> xs = points.stream().map(p -> p.x).collect(Collectors.toList());
        List<Double> ys = points.stream().map(p -> p.y).collect(Collectors.toList());
        plot.series(randomName(),
                Plot.data().xy(xs, ys),
                Plot.seriesOpts().color(blue)
                        .marker(Plot.Marker.CIRCLE)
                        .line(Plot.Line.SOLID));
        return this;
    }

    public void save(String name) {
        try {
            plot.save(name, "png");
        } catch (IOException e) {
            throw new IllegalStateException("Unable to save file");
        }
    }
}
