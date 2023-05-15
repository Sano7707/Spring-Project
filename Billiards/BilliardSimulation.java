package Billiards;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BilliardSimulation {
    public static void main(String[] args) {
        int[] numReflections = { 3, 5, 7 }; // Different numbers of reflections

        for (int n : numReflections) {
            List<Point2D.Double> reflectionPoints = simulateBilliard(n);

            // Print the coordinates of all reflection points
            System.out.println("Reflection points for n = " + n + ":");
            for (Point2D.Double point : reflectionPoints) {
                System.out.println("(" + point.getX() + ", " + point.getY() + ")");
            }
            System.out.println();
        }
    }

    public static List<Point2D.Double> simulateBilliard(int n) {
        Random random = new Random();
        List<Point2D.Double> reflectionPoints = new ArrayList<>();

        // Step 1.1: Initialize random position inside the circle
        double x0 = random.nextDouble() * 2 - 1;  // Random x coordinate between -1 and 1
        double y0 = random.nextDouble() * 2 - 1;  // Random y coordinate between -1 and 1

        for (int i = 0; i < n; i++) {
            // Print the coordinates of the current reflection point
            reflectionPoints.add(new Point2D.Double(x0, y0));

            // Step 1.2: Calculate momentum direction
            double theta = random.nextDouble() * 2 * Math.PI;  // Random angle between 0 and 2pi
            double px0 = Math.cos(theta);
            double py0 = Math.sin(theta);

            // Step 1.3: Calculate next position using intersection with the circle equation
            double t = calculateIntersectionTime(x0, y0, px0, py0);
            double nextX = x0 + t * px0;
            double nextY = y0 + t * py0;

            // Update position for the next reflection
            x0 = nextX;
            y0 = nextY;
        }

        return reflectionPoints;
    }

    public static double calculateIntersectionTime(double x0, double y0, double px0, double py0) {
        double a = px0 * px0 + py0 * py0;
        double b = 2 * (x0 * px0 + y0 * py0);
        double c = x0 * x0 + y0 * y0 - 1;

        double discriminant = b * b - 4 * a * c;
        double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);

        return Math.max(t1, t2);
    }
}
