

public class Calculator {
    public static double calculate(double firstOperand, double secondOperand, String operator) throws Exception {
        switch (operator) {
            case "Addition":
                return firstOperand + secondOperand;
            case "Subtraction":
                return firstOperand - secondOperand;
            case "Multiplication":
                return firstOperand * secondOperand;
            case "Division":
                if (secondOperand == 0) {
                    throw new Exception("Cannot divide by zero");
                }
                return firstOperand / secondOperand;
            default:
                throw new Exception("Invalid operator");
        }
    }
}
