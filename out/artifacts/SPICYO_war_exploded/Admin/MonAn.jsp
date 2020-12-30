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
<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
<style>
    body {
        color: white;
        background: #F5F7FA;
        font-family: 'Open Sans', sans-serif;
    }
    .table-wrapper {
        width: 700px;
        margin: 30px auto;
        background: #fff;
        padding: 20px;
        box-shadow: 0 1px 1px rgba(0,0,0,.05);
    }
    .table-title {
        padding-bottom: 10px;
        margin: 0 0 10px;
    }
    .table-title h2 {
        margin: 6px 0 0;
        font-size: 22px;
    }
    .table-title .add-new {
        float: right;
        height: 30px;
        font-weight: bold;
        font-size: 12px;
        text-shadow: none;
        min-width: 100px;
        border-radius: 50px;
        line-height: 13px;
    }
    .table-title .add-new i {
        margin-right: 4px;
    }
    table.table {
        table-layout: fixed;
    }
    table.table tr th, table.table tr td {
        border-color: #e9e9e9;
    }
    table.table th i {
        font-size: 13px;
        margin: 0 5px;
        cursor: pointer;
    }
    table.table th:last-child {
        width: 100px;
    }
    table.table td a {
        cursor: pointer;
        display: inline-block;
        margin: 0 5px;
        min-width: 24px;
    }
    table.table td a.add {
        color: #27C46B;
    }
    table.table td a.edit {
        color: #FFC107;
    }
    table.table td a.delete {
        color: #E34724;
    }
    table.table td i {
        font-size: 19px;
    }
    table.table td a.add i {
        font-size: 24px;
        margin-right: -1px;
        position: relative;
        top: 3px;
    }
    table.table .form-control {
        height: 32px;
        line-height: 32px;
        box-shadow: none;
        border-radius: 2px;
    }
    table.table .form-control.error {
        border-color: #f50000;
    }
    table.table td .add {
        display: none;
    }
</style>
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
        <jsp:include page="_headerAdmin.jsp"></jsp:include>
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
                                <div class="table-title">
                                    <div class="row">
                                        <div class="col-sm-8"><h2>Món ăn<b>Details</b></h2></div>
                                        <div class="col-sm-4">
                                            <button type="button" class="btn btn-info add-new"><i class="fa fa-plus"></i> Add New</button>
                                        </div>
                                    </div>
                                </div>
                                <table class="table table-dark table-hover">
                                    <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Tên món ăn</th>
                                        <th>Mô tả</th>
                                        <th>Link ảnh</th>
                                        <th>Số lượng</th>
                                        <th>Đơn giá</th>
                                        <th>Edit</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${not empty listMonAn}">
                                        <c:forEach items="${listMonAn}" var="monan" varStatus="status">
                                            <tr data-idMonAn="${monan.idMonAn}">
                                                <td>${status.count}</td>
                                                <td>${monan.tenMonAn}</td>
                                                <td>${monan.mota}</td>
<%--                                                <td>${monan.linkImg}</td>--%>

                                                <td><img class="img-responsive" src="${monan.linkImg}" style="width: 50px; height: 50px" alt="#" /></td>
                                                <td>${monan.soluong}</td>
                                                <td>${monan.dongia}</td>
                                                <td>
                                                    <a class="add" title="Add" data-toggle="tooltip"><i class="material-icons">&#xE03B;</i></a>
                                                    <a class="edit" title="Edit" data-toggle="tooltip"><i class="material-icons">&#xE254;</i></a>
                                                    <a class="delete" title="Delete" data-toggle="tooltip"><i class="material-icons">&#xE872;</i></a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
        <!-- end slider section -->


            <!-- end Our Client -->
            <jsp:include page="_footerAdmin.jsp"></jsp:include>
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


<script>
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
        var actions = $("table td:last-child").html();
        // Append table with add row form on add new button click
        $(".add-new").click(function(){
            $(this).attr("disabled", "disabled");
            var index = $("table tbody tr:last-child").index();
            var stt = index + 2;
            var row = '<tr>' +
                '<td>'+stt+'</td>' +
                '<td><input type="text" class="form-control" name="tenMonAn" id="tenMonAn"></td>' +
                '<td><input type="text" class="form-control" name="mota" id="mota"></td>' +
                '<td><input type="text" class="form-control" name="linkImg" id="linkImg"></td>' +
                '<td><input type="text" class="form-control" name="soluong" id="soluong"></td>' +
                '<td><input type="text" class="form-control" name="dongia" id="dongia"></td>' +
                '<td>' + actions + '</td>' +
                '</tr>';
            $("table").append(row);
            $("table tbody tr").eq(index + 1).find(".add, .edit").toggle();
            $('[data-toggle="tooltip"]').tooltip();
        });
        // Add row on add button click
        $(document).on("click", ".add", function(){
            var empty = false;
            var input = $(this).parents("tr").find('input[type="text"]');
            input.each(function(){
                if(!$(this).val()){
                    $(this).addClass("error");
                    empty = true;
                } else{
                    $(this).removeClass("error");
                }
            });
            $(this).parents("tr").find(".error").first().focus();
            if(!empty){
                //Thực hiện Get
                tenMonAn = $("#tenMonAn").val();
                mota = $("#mota").val();
                linkImg = $("#linkImg").val();
                soluong = $("#soluong").val();
                dongia = $("#dongia").val();
                alert("tenMonAn, linkImg, soluong, dongia, mota: "+tenMonAn+" "+linkImg+" "+soluong+" "+dongia+" "+mota);
                // alert("$(this).attr('data-soluongGioHang')" + $(this).attr('data-soluongGioHang'));
                setTimeout(
                $.get("ThemMonAn",
                    {
                        'type':'them',
                        'tenMonAn': tenMonAn,
                        'linkImg': linkImg,
                        'soluong': soluong,
                        'dongia': dongia,
                        'mota': mota,
                    },
                    function(data,status){
                        alert("Data: " + data + "\nStatus: " + status);
                        location.reload();
                    })
                    ,1000);

                input.each(function(){
                    $(this).parent("td").html($(this).val());
                });
                $(this).parents("tr").find(".add, .edit").toggle();
                $(".add-new").removeAttr("disabled");

            }
        });
        // Edit row on edit button click
        $(document).on("click", ".edit", function(){
            _row = $(this).parents("tr").find("td:not(:first-child):not(:last-child)");
            _tenMonAnCol = _row.eq(0);
            _motaCol = _row.eq(1);
            _linkImgCol = _row.eq(2);
            _soluongCol = _row.eq(3);
            _dongiaCol = _row.eq(4);
            _tenMonAnCol.html('<input type="text" class="form-control" name="tenMonAn" id="tenMonAn" value="' + _tenMonAnCol.text() + '">');
            _motaCol.html('<input type="text" class="form-control" name="mota" id="mota" value="' + _motaCol.text() + '">');
            _linkImgCol.html('<input type="text" class="form-control" name="linkImg" id="linkImg" value="' + _linkImgCol.children("img").attr('src') + '">');
            _soluongCol.html('<input type="text" class="form-control" name="soluong" id="soluong" value="' + _soluongCol.text() + '">');
            _dongiaCol.html('<input type="text" class="form-control" name="dongia" id="dongia" value="' + _dongiaCol.text() + '">');

            // $(this).parents("tr").find("td:not(:first-child):not(:last-child)").each(function(){
            //     $(this).html('<input type="text" class="form-control" value="' + $(this).text() + '">');
            // });
            $(this).parents("tr").find(".add, .edit").toggle();
            $(".add-new").attr("disabled", "disabled");
        });
        // Delete row on delete button click
        $(document).on("click", ".delete", function(){
            //Thực hiện get monan
            if (confirm('Bạn có muốn xoá món ăn này không?')) {
                idMonAn = $(this).parents("tr").attr('data-idMonAn');
                // alert(idMonAn);
                if(idMonAn!=null){
                    $.get("XoaMonAn",
                        {
                            'type':'xoa',
                            'idMonAn': idMonAn,
                        },
                        function(data,status){
                            alert("Data: " + data + "\nStatus: " + status);
                            location.reload();
                        });
                }
                $(this).parents("tr").remove();
                $(".add-new").removeAttr("disabled");
            } else {
                //Không xoá
                return;
            }
        });
    });
</script>

</body>

</html>