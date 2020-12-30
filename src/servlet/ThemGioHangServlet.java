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

@WebServlet(name="ThemGioHang",urlPatterns= {"/ThemGioHang"})
public class ThemGioHangServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        System.out.println(request.getParameter("idMonAn") + "; " + request.getParameter("soluong") +"; " +request.getParameter("dongia"));
        boolean hasError = false;
        if(request.getQueryString() == null){
            request.setAttribute("errorString", "Lỗi thiếu parameter");
            RequestDispatcher dispatcher  = this.getServletContext().getRequestDispatcher("/views/homeView.jsp");
            dispatcher.forward(request, response);
        }
        String type = request.getParameter("type");
        int idMonAn = Integer.parseInt(request.getParameter("idMonAn"));
        int soluong = Integer.parseInt(request.getParameter("soluong"));
        float dongia = Float.parseFloat(request.getParameter("dongia"));
        int idKH = user.getIdUser();
        float thanhtien = soluong*dongia;

        if("".equals(idMonAn) || "".equals(soluong) || "".equals(dongia)) {
            hasError = true;
            request.setAttribute("errorString", "Lỗi thiếu parameter");
        }else{
            int checkExist = 0;
            try {
                // DBUtils.ThemVaoCart(conn, idKH, idMonAn, soluong, dongia, thanhtien);
                checkExist = DBUtils.KiemTraTonTaiCart(conn,idKH,idMonAn);
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                request.setAttribute("errorString", "Lỗi thực thi kiểm tra tồn tại Cart");
            }
            System.out.println("checkExist " + checkExist);
            if(checkExist == 0){
                //Thêm mới Cart
                try {
                     DBUtils.ThemVaoCart(conn, idKH, idMonAn, soluong, dongia, thanhtien);
                } catch (SQLException e) {
                    e.printStackTrace();
                    hasError = true;
                    request.setAttribute("errorString", "Lỗi thêm món ăn vào giỏ");
                }
                System.out.println("Thêm thành công");
            }
            else{
                //Cập nhật Cart
                try {
                    DBUtils.CapNhatCart(conn, idKH, idMonAn, soluong, dongia, thanhtien);
                } catch (SQLException e) {
                    e.printStackTrace();
                    hasError = true;
                    request.setAttribute("errorString", "Lỗi cập nhật món ăn trong giỏ");
                }
                System.out.println("Cập nhật thành công");
            }
        }
        String text = "Success?";
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(text);
//        RequestDispatcher dispatcher  = this.getServletContext().getRequestDispatcher("/views/homeView.jsp");
//        dispatcher.forward(request, response);
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

        String type = request.getParameter("type");
        int idMonAn = Integer.parseInt(request.getParameter("idMonAn"));
        int soluong = Integer.parseInt(request.getParameter("soluong"));
        float dongia = Float.parseFloat(request.getParameter("dongia"));
        int idKH = user.getIdUser();
        float thanhtien = soluong*dongia;

        if("".equals(idMonAn) || "".equals(soluong) || "".equals(dongia)) {
            hasError = true;
            request.setAttribute("errorString", "Lỗi thiếu parameter");
        }else{
            int checkExist = 0;
            try {
                // DBUtils.ThemVaoCart(conn, idKH, idMonAn, soluong, dongia, thanhtien);
                checkExist = DBUtils.KiemTraTonTaiCart(conn,idKH,idMonAn);
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                request.setAttribute("errorString", "Lỗi thực thi kiểm tra tồn tại Cart");
            }
            System.out.println("checkExist " + checkExist);
            if(checkExist == 0){
                //Thêm mới Cart
                try {
                    DBUtils.ThemVaoCart(conn, idKH, idMonAn, soluong, dongia, thanhtien);
                } catch (SQLException e) {
                    e.printStackTrace();
                    hasError = true;
                    request.setAttribute("errorString", "Lỗi thêm món ăn vào giỏ");
                }
                System.out.println("Thêm thành công");
            }
            else{
                //Cập nhật Cart
                try {
                    DBUtils.CapNhatCart(conn, idKH, idMonAn, soluong, dongia, thanhtien);
                } catch (SQLException e) {
                    e.printStackTrace();
                    hasError = true;
                    request.setAttribute("errorString", "Lỗi cập nhật món ăn trong giỏ");
                }
                System.out.println("Cập nhật thành công");
            }
        }
        String text = "Cập nhật thành công!";
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(text);
//        RequestDispatcher dispatcher  = this.getServletContext().getRequestDispatcher("/views/homeView.jsp");
//        dispatcher.forward(request, response);
    }
}
