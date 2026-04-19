package com.cookieservlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userName = request.getParameter("userName");

        // Create cookies
        Cookie nameCookie = new Cookie("user", userName);
        Cookie visitCookie = new Cookie("visit", "1");

        // COOKIE EXPIRY (1 minute = 60 seconds)
        nameCookie.setMaxAge(60);
        visitCookie.setMaxAge(60);

        response.addCookie(nameCookie);
        response.addCookie(visitCookie);

        out.println("<html><body>");
        out.println("<h2>Welcome " + userName + "!</h2>");
        out.println("<p>First visit recorded.</p>");
        out.println("<a href='CookieServlet'>Refresh Page</a>");
        out.println("</body></html>");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Cookie[] cookies = request.getCookies();

        String user = null;
        int visit = 0;

        if (cookies != null) {
            for (Cookie c : cookies) {

                if (c.getName().equals("user")) {
                    user = c.getValue();
                }

                if (c.getName().equals("visit")) {
                    visit = Integer.parseInt(c.getValue());
                }
            }
        }

        out.println("<html><body>");

        if (user != null) {

            visit++;

            // update visit cookie
            Cookie visitCookie = new Cookie("visit", String.valueOf(visit));
            visitCookie.setMaxAge(60);
            response.addCookie(visitCookie);

            out.println("<h2>Welcome back " + user + "!</h2>");
            out.println("<h3>You have visited this page " + visit + " times</h3>");

            out.println("<p>Cookie will expire in 1 minute of inactivity.</p>");

        } else {

            out.println("<h2>Please Login First</h2>");
            out.println("<form method='post' action='CookieServlet'>");
            out.println("Name: <input type='text' name='userName' required>");
            out.println("<input type='submit' value='Submit'>");
            out.println("</form>");
        }

        out.println("<br><a href='CookieServlet'>Refresh</a>");
        out.println("</body></html>");
    }
}