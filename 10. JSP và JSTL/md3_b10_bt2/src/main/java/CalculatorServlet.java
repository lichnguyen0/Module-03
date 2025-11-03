import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CalculatorServlet", value = "/calculate")
public class CalculatorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        double firstOperand = Double.parseDouble(request.getParameter("firstOperand"));
        double secondOperand = Double.parseDouble(request.getParameter("secondOperand"));
        String operator = request.getParameter("operator");

        try {
            double result = Calculator.calculate(firstOperand, secondOperand, operator);

            out.println("<html><body>");
            out.println("<h2>Result:</h2>");
            out.println(firstOperand + " " + getSymbol(operator) + " " + secondOperand + " = " + result);
            out.println("</body></html>");
        } catch (Exception e) {
            out.println("<html><body>");
            out.println("<h2>Error:</h2>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("</body></html>");
        }
    }

    private String getSymbol(String operator) {
        switch (operator) {
            case "Addition": return "+";
            case "Subtraction": return "-";
            case "Multiplication": return "×";
            case "Division": return "÷";
            default: return "?";
        }
    }
}
