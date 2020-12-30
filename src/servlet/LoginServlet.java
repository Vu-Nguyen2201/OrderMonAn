package servlet;
import beans.User;
import utils.DBUtils;
import utils.MyUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebServlet(name="Login",urlPatterns= {"/login"})
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        if(user != null){
            session.invalidate();
            request.setAttribute("errorString", "Vui lòng logout trước khi login mới!");
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/homeView.jsp");
            dispatcher.forward(request, response);
            return;
        }
        RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/views/login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("user");
        String password = request.getParameter("pass");

        Connection conn = MyUtils.getStoredConnection(request);

        User user = null;
        boolean hasError = false;
        String errorString = null;

        if (username == null || password == null || username.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required username and password!";
        }else {
            try {
                user = DBUtils.login(conn, username, password);
                if (user == null) {
                    hasError = true;
                    errorString = "User Name or password invalid";
                    System.out.println("Login fail!");
                }else {
                    System.out.println("Login success!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }

        if (hasError) {
            user = null;
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/login.jsp");

            dispatcher.forward(request, response);
        }
        else {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }
}
