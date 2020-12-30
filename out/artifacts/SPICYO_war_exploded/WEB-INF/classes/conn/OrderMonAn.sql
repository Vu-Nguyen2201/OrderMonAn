create database OrderMonAn
go

use OrderMonAn
go

create table NGUOIDUNG
(
	idKH int identity(1,1) primary key,
	tenKH nvarchar(150) not null,
	username varchar(50) not null,
	password varchar(500) not null,
	sdt varchar(20) not null,
	diachi nvarchar(250) not null,
	phanquyen int not null, /* admin 0, khach hang 1 */
	status int not null default 1,
)
go

insert into NGUOIDUNG(tenKH, username, password, sdt, diachi, phanquyen) values('Nguyễn Văn A', 'nguyena', '1234', '0132652982', 'Quận 12', 1);
insert into NGUOIDUNG(tenKH, username, password, sdt, diachi, phanquyen) values('Nguyễn Văn B', 'nguyenb', '1234', '0123421695', 'Quận 1', 1);
go

create table MONAN
(
	idMonAn int identity(1,1) primary key,
	tenMonAn nvarchar(150) not null,
	soluong int not null default 0,
	dongia float not null default 0,
	linkImg text null,
	status int not null default 1,
)
go

insert into MONAN(tenMonAn, soluong, dongia) values('Gà không lối thoát', 10, '20000'); /* https://www.24h.com.vn/am-thuc/cuoi-ngat-voi-loat-ten-goi-sang-chanh-cua-nhung-mon-an-quen-thuoc-hang-ngay-c460a972582.html */
insert into MONAN(tenMonAn, soluong, dongia) values('Heo chạy trên rừng', 10, '120000');
insert into MONAN(tenMonAn, soluong, dongia) values('Ngưu ma vương hút thuốc', 10, '200000');
insert into MONAN(tenMonAn, soluong, dongia) values('Gà cãi nước sôi', 10, '150000');
go

create table HOADON
(
	idHoaDon int identity(1,1) primary key,
	idKH int foreign key references NGUOIDUNG (idKH),
	tongSoluong int not null default 0,
	thanhtien float not null default 0,
	status int not null default 1,
)
go

insert into HOADON(idKH, tongSoLuong, thanhTien) values(1, 4, 490000);
go

create table CHITIETHOADON
(
	idChiTietHoaDon int identity(1,1) primary key,
	idHoaDon int foreign key references HOADON(idHoaDon),
	idMonAn int foreign key references MONAN (idMonAn),
	soluong int not null,
	dongia float not null,
	thanhtien float not null,
	status int not null default 1,
)
go

insert into CHITIETHOADON(idHoaDon, idMonAn, soluong, dongia, thanhtien) values(1, 1, 1, 20000, 20000);
insert into CHITIETHOADON(idHoaDon, idMonAn, soluong, dongia, thanhtien) values(1, 2, 1, 120000, 120000);
insert into CHITIETHOADON(idHoaDon, idMonAn, soluong, dongia, thanhtien) values(1, 3, 1, 200000, 200000);
insert into CHITIETHOADON(idHoaDon, idMonAn, soluong, dongia, thanhtien) values(1, 4, 1, 150000, 150000);
go

create table GIOHANG
(
	idGioHang int identity(1,1) primary key,
	idKH int foreign key references NGUOIDUNG (idKH),
	idMonAn int foreign key references MONAN (idMonAn),
	soluong int not null,
	dongia float not null,
	thanhtien float not null,
	status int not null default 1,
)
go

insert into GIOHANG(idKH, idMonAn, soluong, dongia, thanhtien) values(2, 1, 1, 20000, 20000);
insert into GIOHANG(idKH, idMonAn, soluong, dongia, thanhtien) values(2, 2, 1, 120000, 120000);
insert into GIOHANG(idKH, idMonAn, soluong, dongia, thanhtien) values(2, 3, 1, 200000, 200000);
insert into GIOHANG(idKH, idMonAn, soluong, dongia, thanhtien) values(2, 4, 1, 150000, 150000);
go