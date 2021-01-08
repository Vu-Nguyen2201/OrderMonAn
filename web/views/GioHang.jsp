<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<!-- basic -->
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- mobile metas -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="viewport" content="initial-scale=1, maximum-scale=1">
<!-- site metas -->
<title>Spicyo</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="author" content="">
<!-- bootstrap css -->
<link rel="stylesheet" href="css/bootstrap.min.css">
<!-- owl css -->
<link rel="stylesheet" href="css/owl.carousel.min.css">
<!-- style css -->
<link rel="stylesheet" href="css/style.css">
<!-- responsive-->
<link rel="stylesheet" href="css/responsive.css">
<!-- awesome fontfamily -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<!-- body -->

<body class="main-layout">
<!-- loader  -->
<div class="loader_bg">
    <div class="loader"><img src="images/loading.gif" alt="" /></div>
</div>

<div class="wrapper">
    <!-- end loader -->

    <c:if test = "${user.phanquyen == 1}">
        <div class="sidebar">
            <!-- Sidebar  -->
            <nav id="sidebar">

                <div id="dismiss" style="margin-top: 25px; width: 260px">
                    <i class="fa fa-arrow-left"></i>
                </div>

                <ul class="list-unstyled components" style="padding-top: 100px">
                    <li >
                        <a href="home">Home</a>
                    </li>
                    <li class="active">
                        <a href="Admin">Admin</a>
                    </li>
                    <li>
                        <a href="HoaDon">Hoá đơn</a>
                    </li>
                    <li>
                        <a href="MonAn">Món Ăn</a>
                    </li>
                </ul>

            </nav>
        </div>
    </c:if>

    <div id="content">
        <!-- header -->
        <jsp:include page="_header.jsp"></jsp:include>
        <!-- end header -->
        <!-- start slider section -->

        <div class="slider_section">
            <div class="container">
                <div class="row">
                    <div class="col-md-12">
                        <div class="full">
                            <c:if test="${not empty errorString }">
                                <div class="error">
                                    <div class="button" >${ errorString }</div>
                                </div>
                            </c:if>
                            <div id="main_slider" class="carousel vert slide" data-ride="carousel" data-interval="5000">
                            <table class="table table-dark table-hover">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên món</th>
                                    <th>Hình ảnh</th>
                                    <th>Số lượng</th>
                                    <th>Đơn giá</th>
                                    <th>Thành tiền</th>
                                    <th>Edit</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:if test="${not empty listGioHang}">
                                    <c:forEach items="${listGioHang}" var="giohang" varStatus="status">
                                        <tr data-idGioHang="${giohang.idGioHang}">
                                        <td>${status.count}</td>
                                        <td>${giohang.tenMonAn}</td>
                                        <td><img class="img-responsive" src="${giohang.linkImg}" style="width: 50px; height: 50px" alt="#" /></td>
                                        <td>${giohang.soluong}</td>
                                        <td>${giohang.dongia}</td>
                                        <td>${giohang.thanhtien}</td>
                                        <td>
                                            <a data-soluong="1" data-idMonAn="${giohang.idMonAn}" data-dongia="${giohang.dongia}" style="color: white" href="#" class="add" title="Add" data-toggle="tooltip"><i class="material-icons">&#xe145;</i></a>
                                            <a data-soluong="-1" data-soluongGioHang="${giohang.soluong}"  data-idMonAn="${giohang.idMonAn}" data-dongia="${giohang.dongia}" style="color: white" href="#" class="edit" title="Edit" data-toggle="tooltip"><i class="material-icons">&#xe15b;</i></a>
                                            <a data-idGioHang="${giohang.idGioHang}" style="color: white" href="#" class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>
                                        </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>
                                <div style="float: right; width: 200px">
                                    <div>
                                        <p style="float: left">Tổng số lượng: </p>
                                        <p style="float: left" id="TongSoLuong">${tongSoLuong}</p>
                                    </div>
                                    <div>
                                        <p style="float: left">Tổng tiền hàng: </p>
                                        <p style="float: left" id="TongThanhTien">${tongThanhTien}</p>
                                    </div>
                                    <a class="main_bt_border btnThanhToan" href="#">Thanh toán</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- end slider section -->

            <!-- end Our Client -->
            <jsp:include page="_footer.jsp"></jsp:include>
        </div>
    </div>
</div>
<div class="overlay"></div>
<!-- Javascript files-->
<script src="js/jquery.min.js"></script>
<script src="js/popper.min.js"></script>
<script src="js/bootstrap.bundle.min.js"></script>
<script src="js/owl.carousel.min.js"></script>
<script src="js/custom.js"></script>
<script src="js/jquery.mCustomScrollbar.concat.min.js"></script>

<script src="js/jquery-3.0.0.min.js"></script>
<style>
    #owl-demo .item{
        margin: 3px;
    }
    #owl-demo .item img{
        display: block;
        width: 100%;
        height: auto;
    }
</style>
<script type="text/javascript">
    $(document).ready(function() {
        $("#sidebar").mCustomScrollbar({
            theme: "minimal"
        });

        $('#dismiss, .overlay').on('click', function() {
            $('#sidebar').removeClass('active');
            $('.overlay').removeClass('active');
        });

        $('#sidebarCollapse').on('click', function() {
            $('#sidebar').addClass('active');
            $('.overlay').addClass('active');
            $('.collapse.in').toggleClass('in');
            $('a[aria-expanded=true]').attr('aria-expanded', 'false');
        });
    });
</script>

<script>
    $(document).ready(function() {
        $(document).on("click", ".add, .edit", function(){
            idMonAn = $(this).attr('data-idMonAn');
            soluong = $(this).attr('data-soluong');
            dongia = $(this).attr('data-dongia');
            // alert("idMonAn, soluong, dongia: "+idMonAn+" "+soluong+" "+dongia);
            // alert("$(this).attr('data-soluongGioHang')" + $(this).attr('data-soluongGioHang'));
            if($(this).attr('data-soluongGioHang') <= 1){
                alert("Số lượng giỏ hàng đã đạt tối thiểu.\n Bạn hãy bấm nút xoá!");
                return;
            }
            $.get("ThemGioHang",
                {
                    'type':'them',
                    'idMonAn': idMonAn,
                    'soluong': soluong,
                    'dongia': dongia,
                },
                function(data,status){
                    alert("Data: " + data + "\nStatus: " + status);
                    location.reload();
             });
        });
        $(document).on("click", ".delete", function(){
            if (confirm('Bạn có muốn xoá món ăn này khỏi giỏ hàng?')) {
                idGioHang = $(this).attr('data-idGioHang');
                $.get("XoaGioHang",
                    {
                        'type':'xoa',
                        'idGioHang': idGioHang,
                    },
                    function(data,status){
                        alert("Data: " + data + "\nStatus: " + status);
                        location.reload();
                    });
            } else {
                //Không xoá
                return;
            }
        });
        $('.btnThanhToan').click(function(){
            tongSoLuong = $("#TongSoLuong").text();
            tongThanhTien = $("#TongThanhTien").text();
            // alert("tongSoLuong, tongThanhTien"+tongSoLuong+" "+tongThanhTien);
            if(tongSoLuong <=0){
                alert("Vui lòng thêm món ăn vào giỏ hàng");
                return;
            }
            $.get("ThanhToan",
                {
                    'type':'thanhtoan',
                    'tongSoLuong': tongSoLuong,
                    'tongThanhTien': tongThanhTien,
                },
                function(data,status){
                    alert("Data: " + data + "\nStatus: " + status);
                });
        });
    })
</script>

</body>

</html>