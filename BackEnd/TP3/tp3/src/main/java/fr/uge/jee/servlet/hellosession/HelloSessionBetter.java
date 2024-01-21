package fr.uge.jee.servlet.hellosession;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet("/hellosessionbetter")
public class HelloSessionBetter extends HttpServlet {

    //private final Token token = new Token();

    private final Object lock = new Object();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        var session = request.getSession(true);
        PrintWriter writer = response.getWriter();

        if(session.getAttribute(session.getId()) == null){
            session.setAttribute(session.getId(),1);
        }
        else{
            var nb = (int) session.getAttribute(session.getId());
            session.setAttribute(session.getId(),nb + 1);
        }


        writer.println("<!DOCTYPE html><html><h1>Bonjour " + session.getAttribute(session.getId()) +
                "-eme fois</h1></html>");
        writer.flush();
    }

}
