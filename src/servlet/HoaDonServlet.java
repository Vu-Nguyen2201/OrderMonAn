package servlet;

import beans.HoaDon;
import beans.MonAn;
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
import java.text.ParseException;
import java.util.List;

@WebServlet(name="HoaDon",urlPatterns= {"/HoaDon"})
public class HoaDonServlet extends HttpServlet {
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
//            request.setAttribute("errorString", "Bạn không có quyền vào admin page");
//            RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/views/homeView.jsp");
//            dispatcher.forward(request, response);
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        List<HoaDon> listHoaDon = null;
        boolean hasError = false;
        String errorString = null;
        try {
            listHoaDon = DBUtils.layDanhSachHoaDon(conn);
        }catch(SQLException | ParseException e) {
            e.printStackTrace();
            hasError = true;
            errorString = e.getMessage();
        }

        request.setAttribute("listHoaDon", listHoaDon);

        // RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp").forward(request, response);
        RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/Admin/HoaDon.jsp");
        dispatcher.forward(request, response);
        return;
    }
}
