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

/**
 * Servlet implementation class create
 */
@WebServlet(name="CreateUser",urlPatterns= {"/create"})
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        request.setAttribute("user", user);
        if(user != null){
            session.invalidate();
            request.setAttribute("errorString", "Vui lòng logout trước khi create!");
            RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/homeView.jsp");
            dispatcher.forward(request, response);
            return;
        }

        RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/views/create.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");

        boolean hasError = false;
        User user = null;
        if("".equals(name) || "".equals(password) || "".equals(repassword) 
        		|| "".equals(username) || "".equals(phone) || "".equals(address)) {
        	hasError = true;
            request.setAttribute("errorString", "Vui lòng điền đẩy đủ thông tin");
        }
        else if(!password.equals(repassword)) {
            hasError = true;
        }
        else{
            // kiểm tra username tồn tại
            Connection conn = MyUtils.getStoredConnection(request);
            boolean exisedtUser = false;
            try {
                exisedtUser = DBUtils.findUserByName(conn, username);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(!exisedtUser){
                try {
                    DBUtils.createUser(conn, username, password, name, phone, address, 2);
                } catch (SQLException e) {
                    hasError = true;
                    request.setAttribute("errorString", "Tạo tài khoản không thành công");
                    e.printStackTrace();
                }
                response.sendRedirect(request.getContextPath() + "/home");
            }else{
                hasError = true;
                request.setAttribute("errorString", "User đã tồn tại");
            }
        }

        if(hasError) {
            RequestDispatcher dispatcher  = this.getServletContext().getRequestDispatcher("/views/create.jsp");
            dispatcher.forward(request, response);
        }
	}
}
