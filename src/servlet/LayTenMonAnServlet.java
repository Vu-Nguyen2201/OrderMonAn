package servlet;

import beans.MonAn;
import beans.User;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name="LayTenMonAn",urlPatterns= {"/LayTenMonAn"})
public class LayTenMonAnServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        List<MonAn> listMonAn = null;
        try {
            listMonAn = DBUtils.layDanhSachMonAn(conn);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        ArrayList<String> listTen = new ArrayList<String>();
        String tenMon = "";
        for(int i = 0; i < listMonAn.size(); i++){
            tenMon = listMonAn.get(i).getTenMonAn();
            listTen.add(tenMon);
        }
        String json = new Gson().toJson(listTen);
        System.out.println(json);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
