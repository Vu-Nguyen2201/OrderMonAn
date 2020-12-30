package servlet;

import beans.GioHang;
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

@WebServlet(name="ThanhToan",urlPatterns= {"/ThanhToan"})
public class ThanhToanServlet extends HttpServlet {
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
            RequestDispatcher dispatcher  = this.getServletContext().getRequestDispatcher("/views/GioHang.jsp");
            dispatcher.forward(request, response);
        }
        int tongSoLuong = Integer.parseInt(request.getParameter("tongSoLuong"));
        float tongThanhTien = Float.parseFloat(request.getParameter("tongThanhTien"));
        int idHoaDon = 0;
        try {
            idHoaDon = DBUtils.ThemHoaDon(conn,user.getIdUser(),tongSoLuong,tongThanhTien);
        } catch (SQLException e) {
            e.printStackTrace();
            hasError = true;
            request.setAttribute("errorString", "Lỗi thực thi kiểm tra tồn tại Cart");
        }
        if(idHoaDon == 0){
            request.setAttribute("errorString", "Lỗi thiếu parameter");
            RequestDispatcher dispatcher  = this.getServletContext().getRequestDispatcher("/views/GioHang.jsp");
            dispatcher.forward(request, response);
        }else{
            List<GioHang> listGioHang = null;
            try {
                listGioHang = DBUtils.layDanhSachCart(conn, user.getIdUser());
            }catch(SQLException e) {
                e.printStackTrace();
                hasError = true;
            }
            for(GioHang giohang: listGioHang){
                try {
                    DBUtils.ThemChiTietHoaDon(conn,idHoaDon,giohang.getIdMonAn(),giohang.getSoluong(),giohang.getDongia(),giohang.getThanhtien());
                    DBUtils.XoaCart(conn,giohang.getIdGioHang());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        String text = "Thanh toán thành công!";
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(text);
    }
}
