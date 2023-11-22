package fr.uge.jee.servlet.hellosession;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.UUID;

@WebServlet("/hellosession")
public class HelloSession extends HttpServlet {

    private final Token token = new Token();
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        UUID token = null;

        if (request.getCookies() != null) {
            if (Arrays.stream(request.getCookies()).map(Cookie::getName).anyMatch(x -> x.contains("TOKEN"))) {
                var value = Arrays.stream(request.getCookies()).map(Cookie::getValue).findFirst();
                if(value.isPresent()) {
                    token = UUID.fromString(value.get());
                }
                this.token.addToken(token);
            } else {
                token = UUID.randomUUID();
                response.addCookie(new Cookie("TOKEN", token.toString()));
                this.token.addToken(token);
            }
        }
        else {
            response.addCookie(new Cookie("TOKEN", UUID.randomUUID().toString()));
        }
        PrintWriter writer = response.getWriter();
        writer.println("<!DOCTYPE html><html><h1>Bonjour " + (token == null ? 1 : this.token.getNb(token)) +
                "-eme fois</h1></html>");
        writer.flush();
    }
}
