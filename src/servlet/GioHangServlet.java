package servlet;
import java.sql.*;

import beans.GioHang;
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
import java.util.List;

@WebServlet(name="GioHang",urlPatterns= {"/GioHang"})
public class GioHangServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Xác th
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

        List<GioHang> listGioHang = null;
        boolean hasError = false;
        String errorString = null;
        Connection conn = MyUtils.getStoredConnection(request);
        try {
            listGioHang = DBUtils.layDanhSachCart(conn, user.getIdUser());
        }catch(SQLException e) {
            e.printStackTrace();
            hasError = true;
            errorString = e.getMessage();
        }
        float tongThanhTien = 0;
        int tongSoLuong = 0;
        for(int i = 0; i < listGioHang.size(); i++){
            tongThanhTien += listGioHang.get(i).getThanhtien();
            tongSoLuong += listGioHang.get(i).getSoluong();
        }
        request.setAttribute("listGioHang", listGioHang);
        request.setAttribute("tongThanhTien", tongThanhTien);
        request.setAttribute("tongSoLuong", tongSoLuong);
        // RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp").forward(request, response);
        RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/views/GioHang.jsp");
        dispatcher.forward(request, response);
        return;
    }
}
