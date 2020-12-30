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


@WebServlet(name="ThemMonAn",urlPatterns= {"/ThemMonAn"})
public class ThemMonAnServlet extends HttpServlet {
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

        //Kiểm tra parameter
        boolean hasError = false;
        if(request.getQueryString() == null){
            request.setAttribute("errorString", "Lỗi thiếu parameter");
            RequestDispatcher dispatcher  = this.getServletContext().getRequestDispatcher("/Admin/MonAn.jsp");
            dispatcher.forward(request, response);
        }

        String tenMonAn = request.getParameter("tenMonAn");
        int soluong = Integer.parseInt(request.getParameter("soluong"));
        float dongia = Float.parseFloat(request.getParameter("dongia"));
        String linkImg = request.getParameter("linkImg");
        String mota = request.getParameter("mota");
        System.out.println("tenMonAn, linkImg, soluong, dongia: "+tenMonAn+" "+linkImg+" "+soluong+" "+dongia);
        if("".equals(tenMonAn) || "".equals(soluong) || "".equals(dongia)) {
            hasError = true;
            request.setAttribute("errorString", "Lỗi thiếu parameter");
        }else{
            int checkExist = 0;
            try {
                // DBUtils.ThemVaoCart(conn, idKH, idMonAn, soluong, dongia, thanhtien);
                checkExist = DBUtils.KiemTraTonTaiMonAn(conn,tenMonAn);
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                request.setAttribute("errorString", "Lỗi thực thi kiểm tra tồn tại Món ăn");
            }
            System.out.println("checkExist " + checkExist);
            if(checkExist == 0){
                //Thêm mới món ăn
                try {
                    DBUtils.ThemMonAn(conn, tenMonAn, soluong, dongia, linkImg,mota);
                } catch (SQLException e) {
                    e.printStackTrace();
                    hasError = true;
                    request.setAttribute("errorString", "Lỗi thêm món ăn");
                }
                System.out.println("Thêm thành công");
            }
            else{
                //Cập nhật Cart
                try {
                    DBUtils.CapNhatMonAn(conn, checkExist, tenMonAn, soluong, dongia, linkImg,mota);
                } catch (SQLException e) {
                    e.printStackTrace();
                    hasError = true;
                    request.setAttribute("errorString", "Lỗi cập nhật món ăn");
                }
                System.out.println("Cập nhật thành công");
            }
        }
        String text = "Cập nhật thành công!";
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(text);
    }
}
