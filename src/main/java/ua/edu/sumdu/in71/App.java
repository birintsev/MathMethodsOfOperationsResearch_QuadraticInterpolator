package ua.edu.sumdu.in71;

import javafx.geometry.Point2D;

import java.util.*;
import java.util.function.Function;

public class App {
    public static void main(String[] args) {
        QuadraticInterpolator quadraticInterpolator = new QuadraticInterpolator(
                aDouble -> (3- aDouble) * Math.exp(-1 * aDouble),
                2.5d,
                1d,
                3e-2d,
                3e-3d
                );
        Point2D minimum = quadraticInterpolator.getMin();
        System.out.println("The function reaches its minimum at the point " + minimum);
    }
}