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

@WebServlet(name="XoaGioHang",urlPatterns= {"/XoaGioHang"})
public class XoaGioHangServlet extends HttpServlet {
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

        // System.out.println(request.getParameter("idMonAn") + "; " + request.getParameter("soluong") +"; " +request.getParameter("dongia"));
        boolean hasError = false;
        if(request.getQueryString() == null){
            request.setAttribute("errorString", "Lỗi thiếu parameter");
            RequestDispatcher dispatcher  = this.getServletContext().getRequestDispatcher("/views/homeView.jsp");
            dispatcher.forward(request, response);
        }

        int idGioHang = Integer.parseInt(request.getParameter("idGioHang"));
        try {
            DBUtils.XoaCart(conn, idGioHang);
        } catch (SQLException e) {
            e.printStackTrace();
            hasError = true;
            request.setAttribute("errorString", "Lỗi xoá món hăn khỏi giỏ");
        }
        System.out.println("Xoá thành công");
        String text = "Xoá thành công!";
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(text);
    }
}
