package servlet;

import beans.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name="Admin",urlPatterns= {"/Admin"})
public class AdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        if(user == null){
            session.invalidate();
            request.setAttribute("errorString", "Vui lòng login");
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/login.jsp");
            dispatcher.forward(request, response);
            return;
        }
        else if(user.getPhanquyen()!=1){
//            request.setAttribute("errorString", "Bạn không có quyền vào admin page");
//            RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/views/homeView.jsp");
//            dispatcher.forward(request, response);
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/Admin/Admin.jsp");
        dispatcher.forward(request, response);
    }
}
