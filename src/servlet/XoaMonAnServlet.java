package servlet;

import beans.User;
import utils.DBUtils;
import utils.MyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(name="XoaMonAn",urlPatterns= {"/XoaMonAn"})
public class XoaMonAnServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Check login
        Connection conn = MyUtils.getStoredConnection(request);
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
            request.setAttribute("errorString", "Bạn không có quyền vào admin page");
            RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/views/homeView.jsp");
            dispatcher.forward(request, response);
            return;
        }

        boolean hasError = false;
        if(request.getQueryString() == null){
            request.setAttribute("errorString", "Lỗi thiếu parameter");
            RequestDispatcher dispatcher  = this.getServletContext().getRequestDispatcher("/Admin/MonAn.jsp");
            dispatcher.forward(request, response);
        }

        int idMonAn = Integer.parseInt(request.getParameter("idMonAn"));
        try {
            DBUtils.XoaMonAn(conn, idMonAn);
        } catch (SQLException e) {
            e.printStackTrace();
            hasError = true;
            request.setAttribute("errorString", "Lỗi xoá món hăn");
        }
        System.out.println("Xoá thành công");
        String text = "Xoá thành công!";
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(text);
    }
}
