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

        UUID token = null;
        int count = 1;

        // si on met une SESSIONID un peu random ca va créer une nouvelle sessionID valide

        if (request.getCookies() != null) {
            if (Arrays.stream(request.getCookies()).map(Cookie::getName).anyMatch(x -> x.contains("TOKEN"))) {
                var value = Arrays.stream(request.getCookies()).filter(x -> x.getName().equals("TOKEN")).map(Cookie::getValue).findFirst();
                if (value.isPresent()) {
                    token = UUID.fromString(value.get());
                    synchronized (lock) {
                        count = (int) session.getAttribute(token.toString());
                        count++;
                    }
                }
            } else {
                token = UUID.randomUUID();
                response.addCookie(new Cookie("TOKEN", token.toString()));
            }
            if(token != null) {
                session.setAttribute(token.toString(), count);
            }
        } else {
            token = UUID.randomUUID();
            session.setAttribute(token.toString(), count);
            response.addCookie(new Cookie("TOKEN", token.toString()));
        }
        writer.println("<!DOCTYPE html><html><h1>Bonjour " + (token == null ? 1 : session.getAttribute(token.toString())) +
                "-eme fois</h1></html>");
        writer.flush();
    }

}
