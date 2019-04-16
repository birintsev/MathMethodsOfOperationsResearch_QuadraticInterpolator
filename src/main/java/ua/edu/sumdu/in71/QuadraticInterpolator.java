package ua.edu.sumdu.in71;

import javafx.geometry.Point2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public class QuadraticInterpolator {

    private Function<Double, Double> function;
    private double xStart;
    private double step;
    private double xAccuracy;
    private double yAccuracy;

    public QuadraticInterpolator(Function<Double, Double> function, double xStart, double step, double xAccuracy, double yAccuracy) {
        this.function = function;
        this.xStart = xStart;
        this.step = step;
        this.xAccuracy = xAccuracy;
        this.yAccuracy = yAccuracy;
    }

    public Point2D getMin() {
        List<javafx.geometry.Point2D> points = getSortedStartPoints(xStart, step, function);
        double parTop;
        double dxX = Double.MAX_VALUE;
        double dyY = Double.MAX_VALUE;
        javafx.geometry.Point2D parabolaTop;
        while (dxX >= xAccuracy || dyY >= yAccuracy) {
            parTop = getXTopofParabola(points, function);
            parabolaTop = new javafx.geometry.Point2D(parTop, function.apply(parTop));
            dxX = Math.abs((points.get(0).getX() - parabolaTop.getX()) / parabolaTop.getX());
            dyY = Math.abs((points.get(0).getY() - parabolaTop.getY()) / parabolaTop.getY());
            points.set(2, parabolaTop);
            points.sort(Comparator.comparingDouble(javafx.geometry.Point2D::getY));
        }
        return points.get(0);
    }

    private List<javafx.geometry.Point2D> getSortedStartPoints(double xStart, double step, Function<Double,Double> function) {
        javafx.geometry.Point2D p1 = new javafx.geometry.Point2D(xStart, function.apply(xStart));
        javafx.geometry.Point2D p2 = new javafx.geometry.Point2D(xStart + step, function.apply(xStart + step));
        double thirdX = p1.getY() < p2.getY() ? xStart - step : xStart + 2 * step;
        javafx.geometry.Point2D p3 = new javafx.geometry.Point2D(thirdX, function.apply(thirdX));
        List<javafx.geometry.Point2D> points = Arrays.asList(p1, p2, p3);
        points.sort(Comparator.comparingDouble(javafx.geometry.Point2D::getY));
        return points;
    }

    private double getXTopofParabola(List<javafx.geometry.Point2D> point2D, Function<Double, Double> function) {
        double a = point2D.get(0).getX();
        double b = point2D.get(1).getX();
        double c = point2D.get(2).getX();
        double fa = function.apply(a);
        double fb = function.apply(b);
        double fc = function.apply(c);
        return ((a + b) / 2)
                + (((fa- fb) * (b - c) * (c - a) / 2)
                / (fa * (b - c) + fb * (c - a) + fc * (a - b)));
    }
}