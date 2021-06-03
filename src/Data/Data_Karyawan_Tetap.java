package Data;

import Koneksi.connect;
import java.sql.*;
import javax.swing.JOptionPane;

/*
    @author llawl
 */
public class Data_Karyawan_Tetap {
    private static Connection conn = new connect().configDB();
    public static void CreateData(String NIK, String Nama, String Email, String NoTelp, String Alamat, String KJ, String Tanggal_Masuk){
        String SQL = "insert into karyawan_tetap values (?,?,?,?,?,?,?)";
        try{
            if(NIK.equals("") | Nama.equals("") | Email.equals("") | NoTelp.equals("") | Alamat.equals("")
                | KJ.equals("")){
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong! \nData Gagal Disimpan");
            }else{
                PreparedStatement State = conn.prepareStatement(SQL);
                State.setString(1, NIK);
                State.setString(2, Nama);
                State.setString(3, Email);
                State.setString(4, NoTelp);
                State.setString(5, Alamat);
                State.setString(6, KJ);
                State.setString(7, Tanggal_Masuk);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Data gagal Disimpan " +yusuf);
        }
    }
    public static void UpdateData(String NIK, String Nama, String Email, String NoTelp, String Alamat, String KJ, String Tanggal_Masuk){
        String SQL = "update karyawan_tetap set Nama=?, Email=?, NoTelp=?, Alamat=?, Kode_Jabatan=?, Tanggal_Masuk=? where NIK='"+NIK+"'";
        try{
            if(NIK.equals("") | Nama.equals("") | Email.equals("") | NoTelp.equals("") | Alamat.equals("")
                | KJ.equals("")){
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong! \nData Gagal Disimpan");
            }else{
                PreparedStatement State = conn.prepareStatement(SQL);
                State.setString(1, Nama);
                State.setString(2, Email);
                State.setString(3, NoTelp);
                State.setString(4, Alamat);
                State.setString(5, KJ);
                State.setString(6, Tanggal_Masuk);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
                }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Data gagal Diubah " +yusuf);
        }
    }
    public static void DeleteData(String NIK){
        int ok = JOptionPane.showConfirmDialog(null, "Hapus","Request Confirmation",JOptionPane.YES_NO_OPTION);
        if(ok==0){
            String SQL = "DELETE FROM karyawan_tetap where NIK = '"+NIK+"'";
            try{
                PreparedStatement State = conn.prepareStatement(SQL);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Data gagal DiHapus " +yusuf);
            }
        }
    }
    public static void CreateDataSlipGaji(String Kode, String Admin, String Tgl, String NIK, String Nama, String KJ, int Gapok, int Absen, int Total){
        String SQL = "insert into slip_gaji_karyawan_tetap values (?,?,?,?,?,?,?,?,?)";
        try{
             if(NIK.equals("") | Nama.equals("") | Kode.equals("") | Admin.equals("") | KJ.equals("") | Total == 0){
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong! \nData Gagal Disimpan");
            }else{
                PreparedStatement State = conn.prepareStatement(SQL);
                State.setString(1, Kode);
                State.setString(2, Admin);
                State.setString(3, Tgl);
                State.setString(4, NIK);
                State.setString(5, Nama);
                State.setString(6, KJ);
                State.setInt(7, Gapok);
                State.setInt(8, Absen);
                State.setInt(9, Total);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
             }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Data gagal Disimpan " +yusuf);
        }
    }
    public static void UpdateDataSlipGaji(String Kode, String Admin, String Tgl, String NIK, String Nama, String KJ, int Gapok, int Absen, int Total){
        String SQL = "update slip_gaji_karyawan_tetap set Username_Admin=?, Tanggal_Slip=?, NIK=?, Nama=?, Kode_Jabatan=?, "
                + "Gaji_Pokok=?, Jumlah_Absen=?, Total_Gaji=? where Kode_Slip='"+Kode+"'";
        try{
             if(NIK.equals("") | Nama.equals("") | Kode.equals("") | Admin.equals("") | KJ.equals("") | Total == 0){
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong! \nData Gagal Disimpan");
            }else{                 
                PreparedStatement State = conn.prepareStatement(SQL);
                State.setString(1, Admin);
                State.setString(2, Tgl);
                State.setString(3, NIK);
                State.setString(4, Nama);
                State.setString(5, KJ);
                State.setInt(6, Gapok);
                State.setInt(7, Absen);
                State.setInt(8, Total);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Data gagal Diubah " +yusuf);
        }
    }
    public static void DeleteDataSlipGaji(String Kode){
        int ok = JOptionPane.showConfirmDialog(null, "Hapus","Request Confirmation",JOptionPane.YES_NO_OPTION);
        if(ok==0){
            String SQL = "DELETE FROM slip_gaji_karyawan_tetap where Kode_Slip = '"+Kode+"'";
            try{
                PreparedStatement State = conn.prepareStatement(SQL);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Data gagal DiHapus " +yusuf);
            }
        }
    }
    public static void CreateDataSlipTHR(String Kode, String Admin, String Tgl, String NIK, String Nama, String KJ, int Gapok, int Total){
        String SQL = "insert into slip_thr_karyawan_tetap values (?,?,?,?,?,?,?,?)";
        try{
             if(NIK.equals("") | Nama.equals("") | Kode.equals("") | Admin.equals("") | KJ.equals("") | Total == 0){
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong! \nData Gagal Disimpan");
            }else{ 
                PreparedStatement State = conn.prepareStatement(SQL);
                State.setString(1, Kode);
                State.setString(2, Admin);
                State.setString(3, Tgl);
                State.setString(4, NIK);
                State.setString(5, Nama);
                State.setString(6, KJ);
                State.setInt(7, Gapok);
                State.setInt(8, Total);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Data gagal Disimpan " +yusuf);
        }
    }
    public static void UpdateDataSlipTHR(String Kode, String Admin, String Tgl, String NIK, String Nama, String KJ, int Gapok, int Total){
        String SQL = "update slip_thr_karyawan_tetap set Username_Admin=?, Tanggal_Slip=?, NIK=?, Nama=?, Kode_Jabatan=?, Gaji_Pokok=?, Total_THR=? where Kode_Slip='"+Kode+"'";
        try{
             if(NIK.equals("") | Nama.equals("") | Kode.equals("") | Admin.equals("") | KJ.equals("") | Total == 0){
                JOptionPane.showMessageDialog(null, "Data Tidak Boleh Kosong! \nData Gagal Disimpan");
            }else{  
                PreparedStatement State = conn.prepareStatement(SQL);
                State.setString(1, Admin);
                State.setString(2, Tgl);
                State.setString(3, NIK);
                State.setString(4, Nama);
                State.setString(5, KJ);
                State.setInt(6, Gapok);
                State.setInt(7, Total);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil Diubah");
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Data gagal Diubah " +yusuf);
        }
    }
    public static void DeleteDataSlipTHR(String Kode){
        int ok = JOptionPane.showConfirmDialog(null, "Hapus","Request Confirmation",JOptionPane.YES_NO_OPTION);
        if(ok==0){
            String SQL = "DELETE FROM slip_thr_karyawan_tetap where Kode_Slip = '"+Kode+"'";
            try{
                PreparedStatement State = conn.prepareStatement(SQL);
                State.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Berhasil DiHapus");
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Data gagal DiHapus " +yusuf);
            }
        }
    }
}
