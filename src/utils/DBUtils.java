package utils;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import beans.HoaDon;
import beans.MonAn;
import beans.GioHang;
import beans.User;


public class DBUtils {
	public static User login(Connection conn, String username, String password) throws SQLException {
 
        String sql = "Select * from NGUOIDUNG  where status=1 and username = '"+username+"' and password= '"+password+"'";
        // System.out.println(sql);
 
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            int idUser = rs.getInt("idKH");
            String name = rs.getString("tenKH");
            String sdt = rs.getString("sdt");
            String diachi = rs.getString("diachi");
            int phanquyen = rs.getInt("phanquyen");
            User user = new User();
            user.setIdUser(idUser);
            user.setUsername(username);
            user.setPassword(password);
            user.setName(name);
            user.setSdt(sdt);
            user.setDiachi(diachi);
            user.setPhanquyen(phanquyen);
            return user;
        }
        return null;
	}
	public static boolean findUserByName(Connection conn, String username) throws SQLException {
		 
		String sql = "Select idKH, tenKH, username, phanquyen from NGUOIDUNG  where status=1 and username = '"+username+"'";
 
		PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return true;
        }
        return false;
    }
    public static void createUser(Connection conn, String username, String password, String name, String sdt, String diachi, int phanquyen) throws SQLException {
        String sql = "insert into NGUOIDUNG (username, password, tenKH, sdt, diachi, phanquyen) " +
                "values(?,?,?,?,?,?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, username);
        pstm.setString(2, password);
        pstm.setNString(3, name);
        pstm.setString(4, sdt);
        pstm.setNString(5, diachi);
        pstm.setInt(6, phanquyen);
        pstm.executeUpdate();
    }

    public static List<MonAn> layDanhSachMonAn(Connection conn) throws SQLException{
        String sql = "Select * from MONAN where status=1";
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<MonAn> listMonAn = new ArrayList<MonAn>();
        while	(rs.next()) {
            int idMonAn = rs.getInt("idMonAn");
            String tenMonAn = rs.getString("tenMonAn");
            int soluong = (int) rs.getLong("soluong");
            float dongia = rs.getLong("dongia");
            String linkImg = rs.getString("linkImg");
            String mota = rs.getString("mota");

            MonAn monan = new MonAn();
            monan.setIdMonAn(idMonAn);
            monan.setTenMonAn(tenMonAn);
            monan.setSoluong(soluong);
            monan.setDongia(dongia);
            monan.setLinkImg(linkImg);
            monan.setMota(mota);
            listMonAn.add(monan);
        }
        return listMonAn;
    }
    public static int KiemTraTonTaiMonAn(Connection conn, String tenMonAn) throws SQLException{
        String sql = "select idMonAn from MONAN where status=1 and tenMonAn=?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setNString(1, tenMonAn);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getInt("idMonAn");
        }
        return 0;
    }
    public static void ThemMonAn(Connection conn, String tenMonAn, int soluong, float dongia, String linkImg, String mota) throws SQLException {
	    String sql = "insert into MONAN(tenMonAN, soluong, dongia, linkImg, mota) values(N'"+tenMonAn+"','"+soluong+"','"+dongia+"',N'"+linkImg+"',N'"+mota+"')";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.executeUpdate();
    }
    public static void CapNhatMonAn(Connection conn, int idMonAn, String tenMonAn, int soluong, float dongia, String linkImg, String mota) throws SQLException {
        String sql = "update MONAN set tenMonAN=N'"+tenMonAn+"',soluong='"+soluong+"',dongia='"+dongia+"',linkImg=N'"+linkImg+"',mota=N'"+mota+"' where idMonAn="+idMonAn;
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.executeUpdate();
    }
    public static void XoaMonAn(Connection conn, int idMonAn) throws SQLException {
        String sql = "update MONAN set status=0 where idMonAn="+idMonAn;
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.executeUpdate();
    }
    public static List<GioHang> layDanhSachCart(Connection conn, int idKH) throws SQLException{
        String sql = "Select GIOHANG.idGioHang, GIOHANG.idMonAn, GIOHANG.soluong, GIOHANG.dongia, GIOHANG.thanhtien, MONAN.tenMonAn, MONAN.linkImg from GIOHANG, MONAN where GIOHANG.idMonAn=MONAN.idMonAn and GIOHANG.status=1 and GIOHANG.idKH="+idKH;
        // System.out.println("sql" + sql);
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<GioHang> listGioHang = new ArrayList<GioHang>();
        while	(rs.next()) {
            int idGioHang = rs.getInt("idGioHang");
            int idMonAn = rs.getInt("idMonAn");
            String tenMonAn = rs.getString("tenMonAn");
            int soluong = (int) rs.getLong("soluong");
            float dongia = rs.getLong("dongia");
            float thanhtien = rs.getLong("thanhtien");
            String linkImg = rs.getString("linkImg");

            GioHang gioHang = new GioHang();
            gioHang.setIdGioHang(idGioHang);
            gioHang.setIdMonAn(idMonAn);
            gioHang.setTenMonAn(tenMonAn);
            gioHang.setSoluong(soluong);
            gioHang.setDongia(dongia);
            gioHang.setThanhtien(thanhtien);
            gioHang.setLinkImg(linkImg);
            listGioHang.add(gioHang);
        }
        return listGioHang;
    }
    public static int KiemTraTonTaiCart(Connection conn, int idKH, int idMonAn) throws SQLException{
        String sql = "select idGioHang from GIOHANG where status=1 and idKH=? and idMonAn=?";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, idKH);
        pstm.setInt(2, idMonAn);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getInt("idGioHang");
        }
        return 0;
    }
    public static void ThemVaoCart(Connection conn, int idKH, int idMonAn, int soluong, float dongia, float thanhtien) throws SQLException {
        String sql = "insert into GIOHANG (idKH, idMonAn, soluong, dongia, thanhtien) " +
                "values(?,?,?,?,?)";

        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, idKH);
        pstm.setInt(2, idMonAn);
        pstm.setInt(3, soluong);
        pstm.setFloat(4, dongia);
        pstm.setFloat(5, thanhtien);
        pstm.execute();
    }
    public static void CapNhatCart(Connection conn, int idKH, int idMonAn, int soluong, float dongia, float thanhtien) throws SQLException {
        String sql = "update GIOHANG set soluong=soluong+?, dongia=?, thanhtien=thanhtien+? where status=1 and idKH=? and idMonAn=?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, soluong);
        pstm.setFloat(2, dongia);
        pstm.setFloat(3, thanhtien);
        pstm.setInt(4, idKH);
        pstm.setInt(5, idMonAn);
        pstm.executeUpdate();
    }
    public static void XoaCart(Connection conn, int idGioHang) throws SQLException {
        String sql = "update GIOHANG set status=0 where idGioHang="+idGioHang;
        System.out.println(sql);
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.executeUpdate();
    }
    public static int ThemHoaDon(Connection conn, int idKH, int tongSoluong, float thanhtien) throws SQLException {
	    String sql ="insert into HOADON(idKH, tongSoluong, thanhtien) Output Inserted.idHoaDon values('"+idKH+"','"+tongSoluong+"','"+thanhtien+"')";
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        if (rs.next()) {
            return rs.getInt("idHoaDon");
        }
        return 0;
    }
    public static void ThemChiTietHoaDon(Connection conn, int idHoaDon, int idMonAn, int soluong, float dongia, float thanhtien) throws SQLException {
        String sql ="insert into CHITIETHOADON(idHoaDon, idMonAn, soluong, dongia, thanhtien) values('"+idHoaDon+"','"+idMonAn+"','"+soluong+"','"+dongia+"','"+thanhtien+"')";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.executeUpdate();
    }
    public static List<HoaDon> layDanhSachHoaDon(Connection conn) throws SQLException, ParseException {
        String sql = "Select * from HOADON, NGUOIDUNG where HOADON.idKH=NGUOIDUNG.idKH and HOADON.status=1";
        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        List<HoaDon> listHoaDon = new ArrayList<HoaDon>();
        while	(rs.next()) {
            int idHoaDon = rs.getInt("idHoaDon");
            int idKH = rs.getInt("idKH");
            String tenKH = rs.getString("tenKH");
            int tongSoluong = (int) rs.getLong("tongSoluong");
            float thanhtien = rs.getLong("thanhtien");

            //rs -> sql.Date: yyyy-MM-dd
            java.sql.Date dbSqlDate = rs.getDate("ngayLapHoaDon");

            //sql.Date -> String
            String ngayLapHoaDon = dbSqlDate.toString();

            //String -> util.Date
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            df = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date1 = df.parse(ngayLapHoaDon);

            //util.Date -> String: dd-MM-yyyy
            df = new SimpleDateFormat("dd-MM-yyyy");
            String ngayLapHoaDon1 = df.format(date1);


            HoaDon hoadon = new HoaDon();
            hoadon.setIdHoaDon(idHoaDon);
            hoadon.setIdKH(idKH);
            hoadon.setTenKH(tenKH);
            hoadon.setTongSoLuong(tongSoluong);
            hoadon.setThanhTien(thanhtien);
            hoadon.setNgayLapHoaDon(ngayLapHoaDon1);
            listHoaDon.add(hoadon);
        }
        return listHoaDon;
    }
}
