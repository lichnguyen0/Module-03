import org.thuvien.logic.QuadraticSolver;

public class TestMain {
        public static void main(String[] args) {
            double a = 1, b = -3, c = 2;
            double[] nghiem = QuadraticSolver.solve(a, b, c);

            if (nghiem != null) {
                if (nghiem.length == 1)
                    System.out.println("Nghiệm kép: x = " + nghiem[0]);
                else
                    System.out.println("Nghiệm: x1 = " + nghiem[0] + ", x2 = " + nghiem[1]);
            } else {
                System.out.println("Phương trình vô nghiệm thực");
            }
        }
    }


