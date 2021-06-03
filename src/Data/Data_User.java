package Data;

import static Data.Data_Login.encrypt;
import Koneksi.connect;
import java.sql.*;
import javax.swing.JOptionPane;
/*
    @author llawl
 */
public class Data_User {
    private static Connection conn = new connect().configDB();
    public static void CreateData(String Username, String Nama, String Email, String NoTelp, String Alamat, String Pass){
        String SQL = "insert into admin values (?,?,?,?,?,?)";
        try{
            if(Username.equals("") | Nama.equals("") | Email.equals("") | NoTelp.equals("") | Alamat.equals("") | Pass.equals("")){
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong! \nData Gagal Disimpan");
            }else{   
                PreparedStatement State = conn.prepareStatement(SQL);
                State.setString(1, Username);
                State.setString(2, Nama);
                State.setString(3, Email);
                State.setString(4, NoTelp);
                State.setString(5, Alamat);
                State.setString(6, encrypt(Pass));
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Data gagal Disimpan " +yusuf);
        }
    }
    /*
    public void UpdateData(String Username, String Nama, String Email, String NoTelp, String Alamat, String Pass){
        String SQL = "update admin set Nama_Admin=?, Email=?, NoTelp=?, Alamat=?, Password=? where Username='"+Username+"'";
        try{
            PreparedStatement State = conn.prepareStatement(SQL);
            State.setString(1, encrypt(Pass));
            State.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Data gagal Disimpan " +yusuf);
        }
    }
    
    public void DeleteData(String Username){
        int ok = JOptionPane.showConfirmDialog(null, "Hapus","Request Confirmation",JOptionPane.YES_NO_OPTION);
        if(ok==0){
            String SQL = "DELETE FROM admin where Username = '"+Username+"'";
            try{
                PreparedStatement State = conn.prepareStatement(SQL);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Data gagal DiHapus " +yusuf);
            }
        }
    }*/
}
