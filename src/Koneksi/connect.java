package Koneksi;
/*
@author llawl
 */
import java.sql.*;
public class connect {
        private static Connection config;
    public static Connection configDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException yusuf) {
            System.err.println("koneksi gagal "+yusuf); //perintah menampilkan error pada koneksi
        }            
        String url="jdbc:mysql://127.0.0.1:3306/sispek_anabatic"; //url database
        try{
            config = DriverManager.getConnection(url,"root","");
        }catch(SQLException yusuf){
            System.out.printf("Gagal KONEK DB"+yusuf);
        }
        return config;
    }
}
