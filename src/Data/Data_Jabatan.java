package Data;
/*
    @author llawl
 */
import Koneksi.connect;
import java.sql.*;
import javax.swing.JOptionPane;
public class Data_Jabatan {
    private static Connection conn = new connect().configDB();
    public static void CreateData(String KJ, String Desc, int Gapok){
        String SQL = "insert into jabatan values (?,?,?)";
        try{
            if(KJ.equals("") | Desc.equals("") | Gapok == 0){
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong! \nData Gagal Disimpan");
            }else{                   
                PreparedStatement State = conn.prepareStatement(SQL);
                State.setString(1, KJ);
                State.setString(2, Desc);
                State.setInt(3, Gapok);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            }
                
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Data gagal Disimpan " +yusuf);
        }
    }
    
    public static void UpdateData(String KJ, String Desc, int Gapok){
        String SQL = "update jabatan set Deskripsi_Jabatan=?, Gaji_Pokok=? where Kode_Jabatan='"+KJ+"'";
        try{
            if(KJ.equals("") | Desc.equals("")| Gapok == 0){
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong! \nData Gagal Disimpan");
            }else{ 
                PreparedStatement State = conn.prepareStatement(SQL);
                State.setString(1, Desc);
                State.setInt(2, Gapok);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Data gagal Diubah " +yusuf);
        }
    }
    
    public static void DeleteData(String KJ){
        int ok = JOptionPane.showConfirmDialog(null, "Hapus","Request Confirmation",JOptionPane.YES_NO_OPTION);
        if(ok==0){
            String SQL = "DELETE FROM jabatan where Kode_Jabatan = '"+KJ+"'";
            try{
                if(KJ.equals("")){
                    JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong! \nData Gagal Disimpan");
                }else{
                    PreparedStatement State = conn.prepareStatement(SQL);
                    State.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
                } 
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Data gagal DiHapus " +yusuf);
            }
        }
    }
}
