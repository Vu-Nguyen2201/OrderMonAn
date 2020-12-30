package servlet;
import java.sql.*;
import beans.MonAn;
import beans.User;
import utils.DBUtils;
import utils.MyUtils;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet(name="Home",urlPatterns= {"/home"})
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
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
		if(user == null){
			session.invalidate();
			request.setAttribute("errorString", "Vui lòng login");
			RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/views/login.jsp");
			dispatcher.forward(request, response);
			return;
		}

		List<MonAn> listMonAn = null;
		boolean hasError = false;
		String errorString = null;
		Connection conn = MyUtils.getStoredConnection(request);
		try {
			listMonAn = DBUtils.layDanhSachMonAn(conn);
		}catch(SQLException e) {
			e.printStackTrace();
			hasError = true;
			errorString = e.getMessage();
		}

		request.setAttribute("listMonAn", listMonAn);

		// RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/WEB-INF/views/homeView.jsp").forward(request, response);
		RequestDispatcher dispatcher= this.getServletContext().getRequestDispatcher("/views/homeView.jsp");
		dispatcher.forward(request, response);
		return;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
