package fr.uge.jee.servlet.rectangle;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@WebServlet("/rectangle")
public class RectangleServlet extends HttpServlet {

    private String home;
    @Override
    public void init(){
        try {
            InputStream inputStream = getServletContext().getResourceAsStream("/WEB-INF/templates/rectangle-form.html");
            home = readFromInputStream(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFromInputStream(InputStream inputStream) throws IOException {
        var lines = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines();
        return lines.collect(Collectors.joining("\n"));
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(home);
        writer.flush();
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        // erreur 500 de code si c'est pas un nombre
        var width = request.getParameter("width");
        var height = request.getParameter("height");
        if(!height.matches("-?\\d+") || !width.matches("-?\\d+")){
            response.sendRedirect("rectangle");
        }
        else {
            var intWidth = Integer.parseInt(width);
            var intHeight = Integer.parseInt(height);
            PrintWriter writer = response.getWriter();
            writer.println("<!DOCTYPE html><html><h2>Area of the rectangle : " +
                    intWidth * intHeight +
                    "</h2</html>");
            writer.flush();
        }
    }
}