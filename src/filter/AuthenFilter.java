//package filter;
//
//import beans.User;
//import utils.DBUtils;
//import utils.MyUtils;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.Collection;
//import java.util.Map;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//// @WebFilter(filterName = "AuthenFilter", urlPatterns = { "/*" })
//public class AuthenFilter implements Filter {
//
//    public AuthenFilter() {
//    }
//
//    @Override
//    public void init(FilterConfig fConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//    private boolean needAuthen(HttpServletRequest request) {
//
//        String servletPath = request.getServletPath();
//
//        String pathInfo = request.getPathInfo();
//
//        String urlPattern = servletPath;
//
//        if (pathInfo != null) {
//            urlPattern = servletPath + "/*";
//        }
//
//        Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext()
//                .getServletRegistrations();
//
//        Collection<? extends ServletRegistration> values = servletRegistrations.values();
//        for (ServletRegistration sr : values) {
//            Collection<String> mappings = sr.getMappings();
//            if (mappings.contains(urlPattern)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//    	HttpServletRequest req = (HttpServletRequest) request;
//        String loginURI = req.getContextPath() + "/login";
//        if(req.getContextPath().matches(".*[css|jpg|png|gif|js].*")){
//            System.out.println("Authen Filter: Matched");
//            chain.doFilter(request, response);
//            return;
//        }
//        if (this.needAuthen(req)) {
//        	System.out.println("Authen Filter: ServletPath: " + req.getServletPath());
//
//            if("/login".equals(req.getServletPath())) {
//            	System.out.println("Authen Filter: go to /login");
//                chain.doFilter(request, response);
//                return;
//            }
//            if("/create".equals(req.getServletPath())) {
//            	System.out.println("Authen Filter: go to /create");
//                chain.doFilter(request, response);
//                return;
//            }
//
//            HttpSession session = req.getSession();
//
//            if("/logout".equals(req.getServletPath())) {
//            	System.out.println("Authen Filter: go to /logout");
//            	session.invalidate();
//        		HttpServletResponse resp = (HttpServletResponse) response;
//                resp.sendRedirect(req.getContextPath() + "/login");
//                return;
//            }
//
//            User userInSession = MyUtils.getLoginedUser(session);
//
//            if (userInSession != null) {
//                Connection conn = MyUtils.getStoredConnection(request);
//            	try {
//                	User user = DBUtils.findUser(conn, userInSession.getIdUser());
//                    if(user.getIdUser() == userInSession.getIdUser()) {
//                        request.setAttribute("user", user);
//                        chain.doFilter(request, response);
//                        return;
//                	} else {
//                    	session.invalidate();
//                		HttpServletResponse resp = (HttpServletResponse) response;
//                        resp.sendRedirect(req.getContextPath() + "/login");
//                        return;
//                	}
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            } else {
//
//        		HttpServletResponse resp = (HttpServletResponse) response;
//                resp.sendRedirect(req.getContextPath() + "/login");
//                return;
//            }
//        }
//
////        chain.doFilter(request, response);
//    }
////    @Override
////    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
////
////        HttpServletRequest request = (HttpServletRequest) req;
////        HttpServletResponse response = (HttpServletResponse) res;
////        HttpSession session = request.getSession(false);
////        String loginURI = request.getContextPath() + "/login";
////        if(request.getContextPath().matches(".*[css|jpg|png|gif|js].*")){
////            System.out.println("Authen Filter: Matched");
////            chain.doFilter(request, response);
////            return;
////        }
//////        if(request.getRequestURI().contains("js") || request.getRequestURI().contains("css")) {
//////        	chain.doFilter(request, response);
//////        }
////        boolean loggedIn = session != null && session.getAttribute("user") != null;
////        boolean loginRequest = request.getRequestURI().equals(loginURI);
////
////        if (loggedIn || loginRequest) {
////            chain.doFilter(request, response);
////        } else {
////            response.sendRedirect(loginURI);
////        }
////    }
//}