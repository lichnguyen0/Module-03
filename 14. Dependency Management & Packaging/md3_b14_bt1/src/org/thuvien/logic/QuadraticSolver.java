package org.thuvien.logic;

public class QuadraticSolver {

    /**
     * Giải phương trình bậc 2: ax^2 + bx + c = 0
     * Trả về mảng 2 phần tử chứa nghiệm, hoặc null nếu vô nghiệm thực
     */
    public static double[] solve(double a, double b, double c) {
        if (a == 0) { // thực chất là phương trình bậc 1
            if (b == 0) return null; // vô nghiệm
            return new double[]{ -c / b };
        }

        double delta = b * b - 4 * a * c;

        if (delta < 0) return null; // vô nghiệm thực
        else if (delta == 0) return new double[]{ -b / (2 * a) }; // nghiệm kép
        else {
            double sqrtDelta = Math.sqrt(delta);
            double x1 = (-b + sqrtDelta) / (2 * a);
            double x2 = (-b - sqrtDelta) / (2 * a);
            return new double[]{ x1, x2 };
        }
    }
}
