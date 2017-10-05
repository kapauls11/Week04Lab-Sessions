/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import business.UserService;
import business.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 698437
 */
public class LoginServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

               String action = (String) request.getParameter("action");
                String message = null;
                HttpSession session = request.getSession();

                //cookie check: if found prefill form values
                String username = Cookies.getCookieValue(request.getCookies(), "username");

                if (username != null && !username.isEmpty()) {
                    request.setAttribute("checked", "checked");
                    request.setAttribute("username", username);
                    request.setAttribute("message", message);
                }


                if (session.getAttribute("username") != null && action == null) {
                    response.sendRedirect("home");
                    return;
                }

                if (action != null && action.equals("logout")) {
                    message = "You have successfully logged out.";

                    session.removeAttribute("username");
                    session.invalidate();
                    request.setAttribute("message", message);
                }

             
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response); 


        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

               boolean rememberMe = false;
                String message = null;
                String username = (String) request.getParameter("username");
                String password = (String) request.getParameter("password");
                String rememberMeString = (String) request.getParameter("remember");
                if (rememberMeString != null) {
                    rememberMe = Boolean.parseBoolean(rememberMeString);
                }

                if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) 
                {

                     UserService userService = new UserService();
                      User user = new User();
                      user.setUsername(username);
                      user.setPassword(password);


                    if (userService.login(user) != null) {

                       HttpSession session = request.getSession();
                       session.setAttribute("username", user.getUsername());
                       session.setMaxInactiveInterval(60*1);  

                     //set username cookie
                      Cookie usernameCookie = new Cookie("username", user.getUsername());

                         if (rememberMe) {
                           usernameCookie.setMaxAge(60*60*24*365); 
                           usernameCookie.setPath("/");            
                           response.addCookie(usernameCookie);
                          }
                        //"remember me" checkbox is not checked
                         else {
                               if (usernameCookie != null) {
                                   usernameCookie.setMaxAge(0); //delete the cookie
                                    usernameCookie.setPath("/");
                                    response.addCookie(usernameCookie);
                                }
                              }

                         request.setAttribute("user", user);
                         response.sendRedirect("home");
                         return;
                       }
                   else {
                        
                        message = "Invalid username or password.";
                    }
                }
                   else {
                    message = "Username and password must be filled";
                   }

                request.setAttribute("username", username);
                request.setAttribute("password", password);
                request.setAttribute("message", message);
                request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo() {
            return "Short description";
        }

        
        
    }

