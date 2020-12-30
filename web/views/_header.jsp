<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
     <header>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-3">
                    <div class="full" style="float:left">
                        <c:if test = "${user.phanquyen == 1}">
                            <div style="float:left; margin-top: 25px; margin-right: 15px">
                                <button type="button" id="sidebarCollapse">
                                    <img src="images/menu_icon.png" alt="#">
                                </button>
                            </div>
                        </c:if>
                        <a class="logo" href="home"><img src="images/logo.png" alt="#" /></a>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="full">
                        <div class="right_header_info">
                            <ul>
                                <li class="dinone">Contact Us<img style="margin-right: 15px;margin-left: 15px;" src="images/phone_icon.png" alt="#"><a href="#">000-001-002</a></li>
                                <li class="dinone"><img style="margin-right: 15px;" src="images/mail_icon.png" alt="#"><a href="#">demo@gmail.com</a></li>
                                <li class="dinone"><img style="margin-right: 15px;height: 21px;position: relative;top: -2px;" src="images/location_icon.png" alt="#"><a href="#">1 Võ Văn Ngân</a></li>

                                <c:if test="${empty user }">
                                	<li class="button_user">
                                		<a class="button active login" href="login">Đăng nhập</a>
                                		<a class="button" href="create">Đăng kí</a>
                                	</li>
                                </c:if>
                                <c:if test="${not empty user }">
                                	<li class="button_user">
                                		<a class="button active" href="${pageContext.request.contextPath}/">${user.username }</a>
                                		<a class="button" href="logout">Log out</a>
                                	</li>
                                    <li>
                                        <a href="GioHang"><img src="images/shopping-cart.png" alt="#"></a>
                                    </li>
                                </c:if>
                                <li><img style="margin-right: 15px; visibility: hidden" src="images/search_icon.png" alt="#"></li>

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </header>
<script type="text/javascript">
    // $(document).ready(function() {
    //     $(this).attr("data-id")
    //     $("#sidebar").mCustomScrollbar({
    //         theme: "minimal"
    //     });
    // });
</script>