package com.example.ejbtutorial;

import com.example.ejb.CalculatorBean;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/calculator")
public class CalculatorServlet extends HttpServlet {

    @EJB
    private CalculatorBean calculatorBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int sumResult = calculatorBean.add(10, 5);
        response.getWriter().println("Result of Sum = " + sumResult);
        int subResult = calculatorBean.substract(10, 5);
        response.getWriter().println("Result of Substration = " + subResult);
        int mulResult = calculatorBean.multiply(10, 5);
        response.getWriter().println("Result of Multiplication = " + mulResult);
        double divResult = calculatorBean.divide(10, 5);
        response.getWriter().println("Result of division = " + divResult);
    }
}
