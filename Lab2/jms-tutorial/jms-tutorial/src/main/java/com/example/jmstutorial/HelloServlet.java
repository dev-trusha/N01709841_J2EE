package com.example.jmstutorial;

import java.io.*;

import jakarta.annotation.Resource;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/send")
public class HelloServlet extends HttpServlet {

    @Resource(lookup = "java:/jms/queue/TestQueue")
    private Queue queue;

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String message = req.getParameter("message");

        try (JMSContext context = connectionFactory.createContext()) {
            context.createProducer().send(queue, message);
        }

        //store message for JSP
        getServletContext().setAttribute("lastMessage", message);

        resp.sendRedirect("index.jsp");
    }

}
