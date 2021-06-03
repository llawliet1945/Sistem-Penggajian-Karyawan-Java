package admin;

import Data.*;
import static Data.Data_Login.encrypt;
import Koneksi.connect;
import java.awt.Color;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.sql.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
/*
    @author Llawl, Wahyu Hadiwiyono
 */

public class Admin extends javax.swing.JFrame {
    public String kry;
    public String pgj;
    public String thr;
    String nik = "", nama = "", jabatan="";
    int gapok=0;
    public Date masuk, situ, Sini, Masuk;
    Date now = new Date();
    
    private DefaultTableModel tabmode, tabmodeL;
    private static Connection conn = new connect().configDB();
    static SimpleDateFormat Date = new SimpleDateFormat("yyyy/MM/dd");
    
    public Admin() {
        initComponents();
        String username = Data_Session.getUsername();
        nmprofile.setText(username);
        menuDashboard();
        DataAdmin();
        karyawan();
        THR();
        penggajian();
        txtTotalthr_THR_KT.disable();
        txtGajipokok_THR_KT.disable();
    }
    
    public int JumlahKT(){
        int count = 0;
        try{
            String SQL = "select * from karyawan_tetap";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while (Result.next()) {
                count++;
            }  
            lvalueKT.setText(Integer.toString(count));
        }catch(SQLException yusuf){
            
        }
        return count;
    }
    
    public int JumlahKK(){
        int count = 0;
        try{
            String SQL = "select * from karyawan_kontrak";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while (Result.next()) {
                count++;
            }  
            lvalueKK.setText(Integer.toString(count));
        }catch(SQLException yusuf){
            
        }
        return count;
    }
    
    public int JumlahKM(){
        int count = 0;
        try{
            String SQL = "select * from karyawan_magang";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while (Result.next()) {
                count++;
            }  
            lvalueKM.setText(Integer.toString(count));
        }catch(SQLException yusuf){
            
        }
        return count;
    }
    
    public void JumlahGajiKT(){
        Double hsl; 
        String moneys;
        try{
            String SQL = "SELECT SUM(Total_Gaji) AS JumlahGaji FROM slip_gaji_karyawan_tetap;";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while (Result.next()) {
                hsl = Result.getDouble(1);
                moneys = String.format("%,.0f", hsl);
                lvalueGKT.setText("Rp. " + moneys);
            }  
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, " data gagal dipanggil"+yusuf);
        }
    }
    
    public void JumlahGajiKK(){
        Double hsl; 
        String moneys;
        try{
            String SQL = "SELECT SUM(Total_Gaji) AS Jumlah_Gaji FROM slip_gaji_karyawan_kontrak;";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while (Result.next()) {
                hsl = Result.getDouble(1);
                moneys = String.format("%,.0f", hsl);
                lvalueGKK.setText("Rp. " + moneys);
            }  
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, " data gagal dipanggil"+yusuf);
        }
    }
    
    public void JumlahGajiKM(){
        Double hsl; 
        String moneys;
        try{
            String SQL = "SELECT SUM(Total_Gaji) AS Jumlah_Gajis FROM slip_gaji_karyawan_magang;";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while (Result.next()) {
                hsl = Result.getDouble(1);
                moneys = String.format("%,.0f", hsl);
                lvalueGKM.setText("Rp. " + moneys);
            }  
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, " data gagal dipanggil"+yusuf);
        }
    }
    
    public void Kosongin_KT(){
        txtNik_KT.setText("");
        txtNama_KT.setText("");
        txtEmail_KT.setText(""); 
        txtNotelp_KT.setText(""); 
        txtAlamat_KT.setText("");
        cbJabatan_KT.setSelectedIndex(0); 
        cbTglmsk_KT.setDate(now);
    }
    
    public void Kosongin_KK(){
        txtNik_KK.setText("");
        txtNama_KK.setText("");
        txtEmail_KK.setText(""); 
        txtNotelp_KK.setText(""); 
        txtAlamat_KK.setText("");
        cbJabatan_KK.setSelectedIndex(0); 
        cbTglmsk_KK.setDate(now);
        cbTglklr_KK.setDate(null);
    }
    
    public void Kosongin_KM(){
        txtNik_KM.setText("");
        txtNama_KM.setText("");
        txtEmail_KM.setText(""); 
        txtNotelp_KM.setText(""); 
        txtAlamat_KM.setText("");
        cbJabatan_KM.setSelectedIndex(0); 
        cbTglmsk_KM.setDate(now);
        cbTglklr_KM.setDate(null);
    }
    
    public void DataAdmin(){
        Object [] Baris ={"Username", "Nama Admin", "Email", "No Telepon", "Alamat", "Password"};
        tabmode = new DefaultTableModel(null, Baris);
        String Cari = txtSearch_Admin.getText();
        try{
            String SQL = "SELECT * FROM admin where Username like '&"+Cari+"&' or Nama_Admin like'%"+Cari+"%' order by Username asc";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while(Result.next()){
                tabmode.addRow(new Object[]{
                    Result.getString(1),
                    Result.getString(2), 
                    Result.getString(3),
                    Result.getString(4), 
                    Result.getString(5),
                    Result.getString(6)});     
                }tbAdmin.setModel(tabmode);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }

    public void DataJabatan(){
        Object [] Baris ={"Kode Jabatan", "Deskripsi Jabatan", "Gaji Pokok"};
        tabmode = new DefaultTableModel(null, Baris);
        String Cari = txtSearch_Jabatan.getText();
        try{
            String SQL = "SELECT * FROM jabatan where Kode_Jabatan like '%"+Cari+"%' or Deskripsi_Jabatan like'%"+Cari+"%' order by Kode_Jabatan asc";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while(Result.next()){
                tabmode.addRow(new Object[]{
                    Result.getString(1),
                    Result.getString(2), 
                    Result.getInt(3)});     
                }tbJabatan.setModel(tabmode);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }
    
    public void DataKT(){
        cbTglmsk_KT.disable();
        Object [] Baris ={"NIK", "Nama", "Email", "No Telepon", "Alamat", "Kode_Jabatan", "Tanggal_Masuk"};
        tabmode = new DefaultTableModel(null, Baris);
        String Cari = txtSearch_KT.getText();
        try{
            String SQL = "SELECT * FROM karyawan_tetap where NIK like '%"+Cari+"%' or Nama like'%"+Cari+"%' order by NIK asc";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while(Result.next()){
                tabmode.addRow(new Object[]{
                    Result.getString(1),
                    Result.getString(2), 
                    Result.getString(3),
                    Result.getString(4), 
                    Result.getString(5),
                    Result.getString(6),     
                    Result.getDate(7)});     
                }tbKT.setModel(tabmode);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }
    
    void DataGajiKT(){
        cbTglslip_penggajian_KT.disable();
        Object [] Baris ={"Kode Slip", "Username Admin", "Tanggal Slip", "NIK", "Nama", "Kode Jabatan", "Gaji Pokok", "Jumlah Absensi", "Total Gaji"};
        tabmode = new DefaultTableModel(null, Baris);
        String Cari = txtSearch_penggajian_KT.getText();
        try{
            String SQL = "SELECT * FROM slip_gaji_karyawan_tetap where Kode_Slip like '%"+Cari+"%' or NIK like'%"+Cari+"%' order by Kode_Slip asc";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while(Result.next()){
                tabmode.addRow(new Object[]{
                    Result.getString(1),
                    Result.getString(2), 
                    Result.getDate(3),
                    Result.getString(4), 
                    Result.getString(5),
                    Result.getString(6),     
                    Result.getInt(7),     
                    Result.getInt(8),     
                    Result.getInt(9)});     
                }tbPenggajianKT.setModel(tabmode);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }
    
    void SlipGajiKT(){
        try{
            String Query = "Select Kode_Slip from slip_gaji_karyawan_tetap order by Kode_Slip asc";
            Statement st = conn.createStatement();
            ResultSet Res = st.executeQuery(Query);
            txtKodeslip_penggajian_KT.setText("SKT0001");
            while(Res.next()){
                String Kode_Slip = Res.getString("Kode_Slip").substring(3);
                int AN = Integer.parseInt(Kode_Slip)+1;
                String Nol = "";
                if (AN<10){Nol="000";
                } else if (AN<100){Nol="00";
                }else if (AN<1000){Nol="0";
                }else if (AN<10000){Nol="";
                }
                txtKodeslip_penggajian_KT.setText("SKT" + Nol + AN);
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "Auto Number Gagal"+yusuf);
        }
    }
    
    void KosonginGajiKT(){
        cbTglslip_penggajian_KT.setDate(now); 
        txtNik_penggajian_KT.setText(""); 
        txtNama_penggajian_KT.setText(""); 
        cbKodejabatan_penggajian_KT.setSelectedItem(""); 
        txtGajipokok_penggajian_KT.setText(Integer.toString(0)); 
        txtJmlabsensi_penggajian_KT.setText(Integer.toString(0)); 
        txtTotalgaji_penggajian_KT.setText(Integer.toString(0));
    }
    
    void DataKTTerpilih(){
        popupKaryawanTetap obje = new popupKaryawanTetap();
        obje.KT = this;
        txtNik_penggajian_KT.setText(nik);
        txtNama_penggajian_KT.setText(nama);
        cbKodejabatan_penggajian_KT.setSelectedItem(jabatan);
        txtGajipokok_penggajian_KT.setText(Integer.toString(gapok));
        txtNik_THR_KT.setText(nik);
        txtNama_THR_KT.setText(nama);
        cbKodejabatan_THR_KT.setSelectedItem(jabatan);
        txtGajipokok_THR_KT.setText(Integer.toString(gapok));
    }
    
    public void HitungGajiKT(){
        int gaps = Integer.parseInt(txtGajipokok_penggajian_KT.getText());
        int absen = Integer.parseInt(txtJmlabsensi_penggajian_KT.getText());
        Double potongan = absen * 0.05;
        Double total = gaps - (potongan*gaps);
        int tot = (int)Math.round(total);
        txtTotalgaji_penggajian_KT.setText(Integer.toString(tot));
    }
    
    void DataTHRKT(){
        cbTglslip_THR_KT.disable();
        Object [] Baris ={"Kode Slip", "Username Admin", "Tanggal Slip", "NIK", "Nama", "Kode Jabatan", "Gaji Pokok", "Total Gaji"};
        tabmode = new DefaultTableModel(null, Baris);
        String Cari = txtSearch_THR_KT.getText();
        try{
            String SQL = "SELECT * FROM slip_thr_karyawan_tetap where Kode_Slip like '%"+Cari+"%' or NIK like'%"+Cari+"%' order by Kode_Slip asc";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while(Result.next()){
                tabmode.addRow(new Object[]{
                    Result.getString(1),
                    Result.getString(2), 
                    Result.getDate(3),
                    Result.getString(4), 
                    Result.getString(5),
                    Result.getString(6),     
                    Result.getInt(7),     
                    Result.getInt(8)});     
                }tbTHRKT.setModel(tabmode);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }
    
    void SlipTHRKT(){
        try{
            String Query = "Select Kode_Slip from slip_thr_karyawan_tetap order by Kode_Slip asc";
            Statement st = conn.createStatement();
            ResultSet Res = st.executeQuery(Query);
            txtKodeslip_THR_KT.setText("STKT0001");
            while(Res.next()){
                String Kode_Slip = Res.getString("Kode_Slip").substring(4);
                int AN = Integer.parseInt(Kode_Slip)+1;
                String Nol = "";
                if (AN<10){Nol="000";
                } else if (AN<100){Nol="00";
                }else if (AN<1000){Nol="0";
                }else if (AN<10000){Nol="";
                }
                txtKodeslip_THR_KT.setText("STKT" + Nol + AN);
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "Auto Number Gagal"+yusuf);
        }
    }
    
    void KosonginTHRKT(){
        txtKodeslip_THR_KT.setText(""); 
        cbTglslip_THR_KT.setDate(now); 
        txtNik_THR_KT.setText(""); 
        txtNama_THR_KT.setText(""); 
        cbKodejabatan_THR_KT.setSelectedItem(""); 
        txtGajipokok_THR_KT.setText(Integer.toString(0)); 
        txtTotalthr_THR_KT.setText(Integer.toString(0));
    }
    
    public void HitungTHRKT(){
        int gaps = Integer.parseInt(txtGajipokok_THR_KT.getText());
        txtTotalthr_THR_KT.setText(Integer.toString(gaps));
    }
    
    public void DataKK(){
        cbTglmsk_KK.disable();
        Object [] Baris ={"NIK", "Nama", "Email", "No Telepon", "Alamat", "Kode Jabatan", "Tanggal Masuk", "Tanggal Habis Kontrak"};
        tabmode = new DefaultTableModel(null, Baris);
        String Cari = txtSearch_KK.getText();
        try{
            String SQL = "SELECT * FROM karyawan_kontrak where NIK like '%"+Cari+"%' or Nama like'%"+Cari+"%' order by NIK asc";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while(Result.next()){
                tabmode.addRow(new Object[]{
                    Result.getString(1),
                    Result.getString(2), 
                    Result.getString(3),
                    Result.getString(4), 
                    Result.getString(5),
                    Result.getString(6),     
                    Result.getDate(7),     
                    Result.getDate(8)});     
                }tbKK.setModel(tabmode);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }
    
    void DataGajiKK(){
        cbTglslip_penggajian_KK.disable();
        Object [] Baris ={"Kode Slip", "Username Admin", "Tanggal Slip", "NIK", "Nama", "Kode Jabatan", "Gaji Pokok", "Jumlah Absensi", "Total Gaji"};
        tabmode = new DefaultTableModel(null, Baris);
        String Cari = txtSearch_penggajian_KK.getText();
        try{
            String SQL = "SELECT * FROM slip_gaji_karyawan_kontrak where Kode_Slip like '%"+Cari+"%' or NIK like'%"+Cari+"%' order by Kode_Slip asc";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while(Result.next()){
                tabmode.addRow(new Object[]{
                    Result.getString(1),
                    Result.getString(2), 
                    Result.getDate(3),
                    Result.getString(4), 
                    Result.getString(5),
                    Result.getString(6),     
                    Result.getInt(7),     
                    Result.getInt(8),     
                    Result.getInt(9)});     
                }tbPenggajianKK.setModel(tabmode);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }
    
    void SlipGajiKK(){
        try{
            String Query = "Select Kode_Slip from slip_gaji_karyawan_kontrak order by Kode_Slip asc";
            Statement st = conn.createStatement();
            ResultSet Res = st.executeQuery(Query);
            txtKodeslip_penggajian_KK.setText("SKK0001");
            while(Res.next()){
                String Kode_Slip = Res.getString("Kode_Slip").substring(3);
                int AN = Integer.parseInt(Kode_Slip)+1;
                String Nol = "";
                if (AN<10){Nol="000";
                } else if (AN<100){Nol="00";
                } else if (AN<1000){Nol="0";
                } else if (AN<10000){Nol="";
                }
                txtKodeslip_penggajian_KK.setText("SKK" + Nol + AN);
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "Auto Number Gagal"+yusuf);
        }
    }
    
    void KosonginGajiKK(){
        txtKodeslip_penggajian_KK.setText("");
        cbTglslip_penggajian_KK.setDate(now); 
        txtNik_penggajian_KK.setText(""); 
        txtNama_penggajian_KK.setText(""); 
        cbKodejabatan_penggajian_KK.setSelectedItem(""); 
        txtGajipokok_penggajian_KK.setText(Integer.toString(0)); 
        txtJmlabsensi_penggajian_KK.setText(Integer.toString(0)); 
        txtTotalgaji_penggajian_KK.setText(Integer.toString(0));
    }
    
    void DataKKTerpilih(){
        popupKaryawanKontrak object = new popupKaryawanKontrak();
        object.KK = this;
        txtNik_penggajian_KK.setText(nik);
        txtNama_penggajian_KK.setText(nama);
        cbKodejabatan_penggajian_KK.setSelectedItem(jabatan);
        txtGajipokok_penggajian_KK.setText(Integer.toString(gapok));
    }
    
    public void HitungGajiKK(){
        int gaps = Integer.parseInt(txtGajipokok_penggajian_KK.getText());
        int absen = Integer.parseInt(txtJmlabsensi_penggajian_KK.getText());
        Double potongan = absen * 0.05;
        Double total = gaps - (potongan*gaps);
        int tot = (int)Math.round(total);
        txtTotalgaji_penggajian_KK.setText(Integer.toString(tot));
    }
    
    public void DataTHRKK(){
        cbTglslip_THR_KK.disable();
        Object [] Baris ={"Kode Slip", "Username Admin", "Tanggal Slip", "NIK", "Nama", "Kode Jabatan", "Gaji Pokok", "Total THR"};
        tabmodeL = new DefaultTableModel(null, Baris);
        String Cari = txtSearch_THR_KK.getText();
        try{
            String SQL = "SELECT * FROM slip_thr_karyawan_kontrak where Kode_Slip like '%"+Cari+"%' or NIK like'%"+Cari+"%' order by Kode_Slip asc";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while(Result.next()){
                tabmodeL.addRow(new Object[]{
                    Result.getString(1),
                    Result.getString(2), 
                    Result.getDate(3),
                    Result.getString(4), 
                    Result.getString(5),
                    Result.getString(6),     
                    Result.getInt(7),     
                    Result.getInt(8)});     
                }tbTHRKK.setModel(tabmodeL);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }
    
    public void SlipTHRKK(){
        try{
            String Query = "Select Kode_Slip from slip_thr_karyawan_kontrak order by Kode_Slip asc";
            Statement st = conn.createStatement();
            ResultSet Res = st.executeQuery(Query);
            txtKodeslip_THR_KK.setText("STKK0001");
            while(Res.next()){
                String Kode_Slip = Res.getString("Kode_Slip").substring(4);
                int AN = Integer.parseInt(Kode_Slip)+1;
                String Nol = "";
                if (AN<10){Nol="000";
                } else if (AN<100){Nol="00";
                }else if (AN<1000){Nol="0";
                }else if (AN<10000){Nol="";
                }
                txtKodeslip_THR_KK.setText("STKK" + Nol + AN);
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "Auto Number Gagal"+yusuf);
        }
    }
    
    public void KosonginTHRKK(){
        cbTglslip_THR_KK.setDate(now); 
        txtNik_THR_KK.setText(""); 
        txtNama_THR_KK.setText(""); 
        cbKodejabatan_THR_KK.setSelectedItem(""); 
        txtGajipokok_THR_KK.setText(Integer.toString(0)); 
        txtTotalthr_THR_KK.setText(Integer.toString(0));
    }
    
    public void DataTHRKKTerpilih(){
        popupTHRKaryawanKontrak ini = new popupTHRKaryawanKontrak();
        ini.THRKK = this;
        txtNik_THR_KK.setText(nik);
        txtNama_THR_KK.setText(nama);
        cbKodejabatan_THR_KK.setSelectedItem(jabatan);
        txtGajipokok_THR_KK.setText(Integer.toString(gapok));
        masuk = Masuk;
    }
    
    public void HitungTHRKK(){
        int gaps = Integer.parseInt(txtGajipokok_THR_KK.getText());
        txtTotalthr_THR_KK.setText(Integer.toString(gaps));
    }
    
    public void DataKM(){
        cbTglmsk_KM.disable();
        Object [] Baris ={"NIK", "Nama", "Email", "No Telepon", "Alamat", "Kode Jabatan", "Tanggal Masuk", "Tanggal Selesai Magang"};
        tabmode = new DefaultTableModel(null, Baris);
        String Cari = txtSearch_KM.getText();
        try{
            String SQL = "SELECT * FROM karyawan_magang where NIK like '%"+Cari+"%' or Nama like'%"+Cari+"%' order by NIK asc";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while(Result.next()){
                tabmode.addRow(new Object[]{
                    Result.getString(1),
                    Result.getString(2), 
                    Result.getString(3),
                    Result.getString(4), 
                    Result.getString(5),
                    Result.getString(6),     
                    Result.getDate(7),     
                    Result.getDate(8)});     
                }tbKM.setModel(tabmode);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }
    
    void DataGajiKM(){
        cbTglslip_penggajian_KM.disable();
        Object [] Baris ={"Kode Slip", "Username Admin", "Tanggal Slip", "NIK", "Nama", "Kode Jabatan", "Gaji Pokok", "Jumlah Absensi", "Total Gaji"};
        tabmode = new DefaultTableModel(null, Baris);
        String Cari = txtSearch_penggajian_KM.getText();
        try{
            String SQL = "SELECT * FROM slip_gaji_karyawan_magang where Kode_Slip like '%"+Cari+"%' or NIK like'%"+Cari+"%' order by Kode_Slip asc";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while(Result.next()){
                tabmode.addRow(new Object[]{
                    Result.getString(1),
                    Result.getString(2), 
                    Result.getDate(3),
                    Result.getString(4), 
                    Result.getString(5),
                    Result.getString(6),     
                    Result.getInt(7),     
                    Result.getInt(8),     
                    Result.getInt(9)});     
                }tbPenggajianKM.setModel(tabmode);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }
    
    void SlipGajiKM(){
        try{
            String Query = "Select Kode_Slip from slip_gaji_karyawan_magang order by Kode_Slip asc";
            Statement st = conn.createStatement();
            ResultSet Res = st.executeQuery(Query);
            txtKodeslip_penggajian_KM.setText("SKM0001");
            while(Res.next()){
                String Kode_Slip = Res.getString("Kode_Slip").substring(3);
                int AN = Integer.parseInt(Kode_Slip)+1;
                String Nol = "";
                if (AN<10){Nol="000";
                } else if (AN<100){Nol="00";
                }else if (AN<1000){Nol="0";
                }else if (AN<10000){Nol="";
                }
                txtKodeslip_penggajian_KM.setText("SKM" + Nol + AN);
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "Auto Number Gagal"+yusuf);
        }
    }
    
    void KosonginGajiKM(){
        cbTglslip_penggajian_KM.setDate(now); 
        txtNik_penggajian_KM.setText(""); 
        txtNama_penggajian_KM.setText(""); 
        cbKodejabatan_penggajian_KM.setSelectedItem(""); 
        txtGajipokok_penggajian_KM.setText(Integer.toString(0)); 
        txtJmlabsensi_penggajian_KM.setText(Integer.toString(0)); 
        txtTotalgaji_penggajian_KM.setText(Integer.toString(0));
    }
    
    void DataKMTerpilih(){
        popupKaryawanMagang objec = new popupKaryawanMagang();
        objec.KM = this;
        txtNik_penggajian_KM.setText(nik);
        txtNama_penggajian_KM.setText(nama);
        cbKodejabatan_penggajian_KM.setSelectedItem(jabatan);
        txtGajipokok_penggajian_KM.setText(Integer.toString(gapok));
    }
    
    public void HitungGajiKM(){
        int gaps = Integer.parseInt(txtGajipokok_penggajian_KM.getText());
        int absen = Integer.parseInt(txtJmlabsensi_penggajian_KM.getText());
        Double potongan = absen * 0.05;
        Double total = gaps - (potongan*gaps);
        int tot = (int)Math.round(total);
        txtTotalgaji_penggajian_KM.setText(Integer.toString(tot));
    }
    
    private void Kosongin_Admin(){
        txtUsername_Admin.setText("");
        txtNama_Admin.setText("");
        txtEmail_Admin.setText("");
        txtNotelp_Admin.setText("");
        txtAlamat_Admin.setText("");
        txtPassword_Admin.setText("");
    }
    
    private void Kosongin_Jabatan(){
        cbKode_Jabatan_Jabatan.setSelectedIndex(0);
        txtJabatan.setText("");
        txtGajipokok.setText(Integer.toString(0));
    }
    
    public void menuDashboard(){
        colorActive(bdashboard);
        colorNoActive(badmin);
        colorNoActive(bkaryawan);
        colorNoActive(bjabatan);
        colorNoActive(bpenggajian);
        colorNoActive(bTHR);
        conDashboard.setVisible(true);
        JumlahKT();
        JumlahKK();
        JumlahKM();
        JumlahGajiKT();
        JumlahGajiKK();
        JumlahGajiKM();
        conAdmin.setVisible(false);
        conKaryawan.setVisible(false);
        conJabatan.setVisible(false);
        conPenggajian.setVisible(false);
        conTHR.setVisible(false);
        txtTotalthr_THR_KT.disable();
        txtGajipokok_THR_KT.disable();
        String username = Data_Session.getUsername();
        nmprofile.setText(username);
    }
    
    public void menuAdmin(){
        DataAdmin();
        colorNoActive(bdashboard);
        colorActive(badmin);
        colorNoActive(bkaryawan);
        colorNoActive(bjabatan);
        colorNoActive(bpenggajian);
        colorNoActive(bTHR);
        conDashboard.setVisible(false);
        conAdmin.setVisible(true);
        conKaryawan.setVisible(false);
        conJabatan.setVisible(false);
        conPenggajian.setVisible(false);
        conTHR.setVisible(false);
        String username = Data_Session.getUsername();
        nmprofile.setText(username);
    }

    public void menuKaryawan(){
        colorNoActive(bdashboard);
        colorNoActive(badmin);
        colorActive(bkaryawan);
        colorNoActive(bjabatan);
        colorNoActive(bpenggajian);
        colorNoActive(bTHR);
        conDashboard.setVisible(false);
        conAdmin.setVisible(false);
        conKaryawan.setVisible(true);
        conJabatan.setVisible(false);
        conPenggajian.setVisible(false);
        conTHR.setVisible(false);
        String username = Data_Session.getUsername();
        nmprofile.setText(username);
    }
    
    public void menuJabatan(){
        colorNoActive(bdashboard);
        colorNoActive(badmin);
        colorNoActive(bkaryawan);
        colorActive(bjabatan);
        colorNoActive(bpenggajian);
        colorNoActive(bTHR);
        conDashboard.setVisible(false);
        conAdmin.setVisible(false);
        conKaryawan.setVisible(false);
        conJabatan.setVisible(true);
        DataJabatan();
        conPenggajian.setVisible(false);
        conTHR.setVisible(false);
        txtGajipokok.setText(Integer.toString(0));
        String username = Data_Session.getUsername();
        nmprofile.setText(username);
    }
    
    public void menuPenggajian(){
        colorNoActive(bdashboard);
        colorNoActive(badmin);
        colorNoActive(bkaryawan);
        colorNoActive(bjabatan);
        colorActive(bpenggajian);
        colorNoActive(bTHR);
        conDashboard.setVisible(false);
        conAdmin.setVisible(false);
        conKaryawan.setVisible(false);
        conJabatan.setVisible(false);
        conPenggajian.setVisible(true);
        conTHR.setVisible(false);
        String username = Data_Session.getUsername();
        nmprofile.setText(username);
        
    }
    
    public void menuTHR(){
        colorNoActive(bdashboard);
        colorNoActive(badmin);
        colorNoActive(bkaryawan);
        colorNoActive(bjabatan);
        colorNoActive(bpenggajian);
        colorActive(bTHR);
        conDashboard.setVisible(false);
        conAdmin.setVisible(false);
        conKaryawan.setVisible(false);
        conJabatan.setVisible(false);
        conPenggajian.setVisible(false);
        conTHR.setVisible(true);
        txtKodeslip_THR_KT.disable();
        txtKodeslip_THR_KK.disable();
        SlipTHRKT();
        DataTHRKT();
        SlipTHRKK();
        DataTHRKK();
        String username = Data_Session.getUsername();
        nmprofile.setText(username);
    }
    
    private void karyawan(){
        kry = (String) cbKaryawan.getSelectedItem();
        if(kry == "TETAP"){
            karyawanTetap.setVisible(true);
            cbTglmsk_KT.setDate(now);
            DataKT();
            String SQL = "Select * from Jabatan where Kode_Jabatan like'KT%'";
            try{
                PreparedStatement state = conn.prepareStatement(SQL);
                ResultSet Result = state.executeQuery(SQL);
                cbJabatan_KT.addItem("");
                while (Result.next()) {
                    cbJabatan_KT.addItem(Result.getString("Kode_Jabatan"));
                }Result.close();
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Error "+yusuf);
            }
            karyawanKontrak.setVisible(false);
            karyawanMagang.setVisible(false);
            String username = Data_Session.getUsername();
            nmprofile.setText(username);
            cbJabatan_KK.removeAllItems();
            cbJabatan_KM.removeAllItems();
        } else if(kry == "KONTRAK"){
            cbJabatan_KT.removeAllItems();
            cbJabatan_KM.removeAllItems();
            karyawanTetap.setVisible(false);
            karyawanKontrak.setVisible(true);
            DataKK();
            cbTglmsk_KK.setDate(now);
            String SQL = "Select * from Jabatan where Kode_Jabatan like'KK%'";
            try{
                PreparedStatement state = conn.prepareStatement(SQL);
                ResultSet Result = state.executeQuery(SQL);
                cbJabatan_KK.addItem("");
                while (Result.next()) {
                    
                    cbJabatan_KK.addItem(Result.getString("Kode_Jabatan"));  
                }
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Error "+yusuf);
            }
            karyawanMagang.setVisible(false);
            String username = Data_Session.getUsername();
            nmprofile.setText(username);
            
        } else if(kry == "MAGANG"){
            cbJabatan_KT.removeAllItems();
            cbJabatan_KK.removeAllItems();
            karyawanTetap.setVisible(false);
            karyawanKontrak.setVisible(false);
            karyawanMagang.setVisible(true);
            DataKM();
            cbTglmsk_KM.setDate(now);
            String SQL = "Select * from Jabatan where Kode_Jabatan like'KM%'";
            try{
                PreparedStatement state = conn.prepareStatement(SQL);
                ResultSet Result = state.executeQuery(SQL);
                cbJabatan_KM.addItem("");
                while (Result.next()) {  
                    cbJabatan_KM.addItem(Result.getString("Kode_Jabatan"));  
                }
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Error "+yusuf);
            }
            String username = Data_Session.getUsername();
            nmprofile.setText(username);
        }
    }
    
    private void penggajian(){
        pgj = (String) cbPenggajian.getSelectedItem();
        txtKodeslip_penggajian_KT.disable();
        cbKodejabatan_penggajian_KT.disable();
        txtTotalgaji_penggajian_KT.disable();
        txtTotalgaji_penggajian_KT.setText(Integer.toString(0));
        txtGajipokok_penggajian_KT.disable();
        txtGajipokok_penggajian_KT.setText(Integer.toString(0));
        txtNik_penggajian_KT.disable();
        txtNama_penggajian_KT.disable();
        txtKodeslip_penggajian_KK.disable();
        cbKodejabatan_penggajian_KK.disable();
        txtGajipokok_penggajian_KK.disable();
        txtGajipokok_penggajian_KK.setText(Integer.toString(0));
        txtTotalgaji_penggajian_KK.setText(Integer.toString(0));
        txtTotalgaji_penggajian_KK.disable();
        txtNik_penggajian_KK.disable();
        txtNama_penggajian_KK.disable();
        txtKodeslip_penggajian_KM.disable();
        cbKodejabatan_penggajian_KM.disable();
        txtTotalgaji_penggajian_KM.disable();
        txtGajipokok_penggajian_KM.disable();
        txtGajipokok_penggajian_KM.setText(Integer.toString(0));
        txtTotalgaji_penggajian_KM.setText(Integer.toString(0));
        txtNik_penggajian_KM.disable();
        txtNama_penggajian_KM.disable();
        if(pgj == "TETAP"){
            penggajianTetap.setVisible(true);
            KosonginGajiKT();
            SlipGajiKT();
            DataGajiKT();
            cbTglslip_penggajian_KT.setDate(now);
            cbKodejabatan_penggajian_KK.removeAllItems();
            cbKodejabatan_penggajian_KM.removeAllItems();
            String SQL = "Select * from Jabatan where Kode_Jabatan like'KT%'";
            try{
                PreparedStatement state = conn.prepareStatement(SQL);
                ResultSet Result = state.executeQuery(SQL);
                cbKodejabatan_penggajian_KT.addItem("");
                while (Result.next()) {
                    cbKodejabatan_penggajian_KT.addItem(Result.getString("Kode_Jabatan"));
                }Result.close();
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Error "+yusuf);
            }
            penggajianKontrak.setVisible(false);
            penggajianMagang.setVisible(false);
            String username = Data_Session.getUsername();
            nmprofile.setText(username);
        } else if(pgj == "KONTRAK"){
            penggajianTetap.setVisible(false);
            penggajianKontrak.setVisible(true);
            penggajianMagang.setVisible(false);
            KosonginGajiKK();
            SlipGajiKK();
            DataGajiKK();
            cbTglslip_penggajian_KK.setDate(now);
            cbKodejabatan_penggajian_KT.removeAllItems();
            cbKodejabatan_penggajian_KM.removeAllItems();
            String SQL = "Select * from Jabatan where Kode_Jabatan like'KK%'";
            try{
                PreparedStatement state = conn.prepareStatement(SQL);
                ResultSet Result = state.executeQuery(SQL);
                cbKodejabatan_penggajian_KK.addItem("");
                while (Result.next()) {  
                    cbKodejabatan_penggajian_KK.addItem(Result.getString("Kode_Jabatan"));  
                }
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Error "+yusuf);
            }
            String username = Data_Session.getUsername();
            nmprofile.setText(username);
        } else if(pgj == "MAGANG"){
            penggajianTetap.setVisible(false);
            penggajianKontrak.setVisible(false);
            penggajianMagang.setVisible(true);
            KosonginGajiKM();
            SlipGajiKM();
            DataGajiKM();
            cbTglslip_penggajian_KM.setDate(now);
            cbKodejabatan_penggajian_KT.removeAllItems();
            cbKodejabatan_penggajian_KK.removeAllItems();            
            String SQL = "Select * from Jabatan where Kode_Jabatan like'KM%'";
            try{
                PreparedStatement state = conn.prepareStatement(SQL);
                ResultSet Result = state.executeQuery(SQL);
                cbKodejabatan_penggajian_KM.addItem("");
                while (Result.next()) {  
                    cbKodejabatan_penggajian_KM.addItem(Result.getString("Kode_Jabatan"));  
                }
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Error "+yusuf);
            }
            String username = Data_Session.getUsername();
            nmprofile.setText(username);
        }
    }
    
    private void THR(){
        thr = cbTHR.getSelectedItem().toString();
        txtKodeslip_THR_KT.disable();
        cbKodejabatan_THR_KT.disable();
        txtTotalthr_THR_KT.disable();
        txtGajipokok_THR_KT.disable();
        txtGajipokok_THR_KT.setText(Integer.toString(0));
        txtTotalthr_THR_KT.setText(Integer.toString(0));
        txtNik_THR_KT.disable();
        txtNama_THR_KT.disable();
        txtKodeslip_THR_KK.disable();
        cbKodejabatan_THR_KK.disable();
        txtTotalthr_THR_KK.disable();
        txtTotalthr_THR_KK.setText(Integer.toString(0));
        txtGajipokok_THR_KK.setText(Integer.toString(0));
        txtGajipokok_THR_KK.disable();
        txtNik_THR_KK.disable();
        txtNama_THR_KK.disable();
        if(thr == "TETAP"){
            THRTetap.setVisible(true);
            THRKontrak.setVisible(false);
            SlipTHRKT();
            DataTHRKT();
            cbTglslip_THR_KT.setDate(now);
            cbKodejabatan_THR_KK.removeAllItems();
            txtKodeslip_THR_KT.disable();
            txtTotalthr_THR_KT.disable();
            String SQL1 = "Select * from Jabatan where Kode_Jabatan like'KT%'";
            try{
                PreparedStatement st = conn.prepareStatement(SQL1);
                ResultSet Res = st.executeQuery(SQL1);
                cbKodejabatan_THR_KT.addItem("");
                while (Res.next()) {
                    cbKodejabatan_THR_KT.addItem(Res.getString("Kode_Jabatan"));
                }Res.close();
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Error "+yusuf);
            }
            String username = Data_Session.getUsername();
            nmprofile.setText(username);
        } else if(thr == "KONTRAK"){
            THRTetap.setVisible(false);
            THRKontrak.setVisible(true);
            SlipTHRKK();
            DataTHRKK();
            cbTglslip_THR_KK.setDate(now);
            cbKodejabatan_THR_KT.removeAllItems();
            txtKodeslip_THR_KK.disable();
            txtTotalthr_THR_KK.disable();
            String SQL = "Select * from Jabatan where Kode_Jabatan like'KK%'";
            try{
                PreparedStatement state = conn.prepareStatement(SQL);
                ResultSet Result = state.executeQuery(SQL);
                cbKodejabatan_THR_KK.addItem("");
                while (Result.next()) {
                    cbKodejabatan_THR_KK.addItem(Result.getString("Kode_Jabatan"));
                }Result.close();
            }catch(SQLException yusuf){
                JOptionPane.showMessageDialog(null, "Error "+yusuf);
            }
            
            String username = Data_Session.getUsername();
            nmprofile.setText(username);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelProfile = new javax.swing.JPanel();
        nmprofile = new javax.swing.JLabel();
        avatar = new javax.swing.JLabel();
        panelMenu = new javax.swing.JPanel();
        lAdministrator = new javax.swing.JLabel();
        garisMenu = new javax.swing.JPanel();
        bdashboard = new javax.swing.JPanel();
        lMenuDashboard = new javax.swing.JLabel();
        iconDashboard = new javax.swing.JLabel();
        badmin = new javax.swing.JPanel();
        lMenuAdmin = new javax.swing.JLabel();
        iconAdmin = new javax.swing.JLabel();
        bkaryawan = new javax.swing.JPanel();
        lMenuKaryawan = new javax.swing.JLabel();
        iconKaryawan = new javax.swing.JLabel();
        bjabatan = new javax.swing.JPanel();
        lMenuJabatan = new javax.swing.JLabel();
        iconJabatan = new javax.swing.JLabel();
        bpenggajian = new javax.swing.JPanel();
        lMenuPenggajian = new javax.swing.JLabel();
        iconPenggajian = new javax.swing.JLabel();
        bTHR = new javax.swing.JPanel();
        lMenuTHR = new javax.swing.JLabel();
        iconTHR = new javax.swing.JLabel();
        blogout = new javax.swing.JButton();
        panelContent = new javax.swing.JPanel();
        conTHR = new javax.swing.JPanel();
        lFormTHR = new javax.swing.JLabel();
        borderFormTHR = new javax.swing.JLabel();
        TabelTHR = new javax.swing.JLabel();
        cbTHR = new javax.swing.JComboBox<>();
        THRTetap = new javax.swing.JPanel();
        lkodeslip_THR_KT = new javax.swing.JLabel();
        borderkodeslip_THR_KT = new javax.swing.JLabel();
        txtKodeslip_THR_KT = new javax.swing.JTextField();
        ltglslip_THR_KT = new javax.swing.JLabel();
        bordertglslip_THR_KT = new javax.swing.JLabel();
        cbTglslip_THR_KT = new com.toedter.calendar.JDateChooser();
        lnik_THR_KT = new javax.swing.JLabel();
        bordernik_THR_KT = new javax.swing.JLabel();
        bCari_THR_KT = new javax.swing.JButton();
        txtNik_THR_KT = new javax.swing.JTextField();
        lnama_THR_KT = new javax.swing.JLabel();
        bordernama_THR_KT = new javax.swing.JLabel();
        txtNama_THR_KT = new javax.swing.JTextField();
        lkodejabatan_THR_KT = new javax.swing.JLabel();
        borderkodejabatan_THR_KT = new javax.swing.JLabel();
        cbKodejabatan_THR_KT = new javax.swing.JComboBox<>();
        lgajipokok_THR_KT = new javax.swing.JLabel();
        bordergajipokok_THR_KT = new javax.swing.JLabel();
        txtGajipokok_THR_KT = new javax.swing.JTextField();
        ltotalthr_THR_KT = new javax.swing.JLabel();
        bordertotalthr_THR_KT = new javax.swing.JLabel();
        txtTotalthr_THR_KT = new javax.swing.JTextField();
        garis_THR_KT = new javax.swing.JPanel();
        bTambah_THR_KT = new javax.swing.JButton();
        bUbah_THR_KT = new javax.swing.JButton();
        bHapus_THR_KT = new javax.swing.JButton();
        bBatal_THR_KT = new javax.swing.JButton();
        bHitungTHR_THR_KT = new javax.swing.JButton();
        bSearch_THR_KT = new javax.swing.JLabel();
        bordersearch_THR_KT = new javax.swing.JLabel();
        txtSearch_THR_KT = new javax.swing.JTextField();
        jScrollPane1_THR_KT = new javax.swing.JScrollPane();
        tbTHRKT = new javax.swing.JTable();
        bCetak_THR_KT = new javax.swing.JButton();
        THRKontrak = new javax.swing.JPanel();
        lkodeslip_THR_KK = new javax.swing.JLabel();
        txtKodeslip_THR_KK = new javax.swing.JTextField();
        borderkodeslip_THR_KK = new javax.swing.JLabel();
        ltglslip_THR_KK = new javax.swing.JLabel();
        bordertglslip_THR_KK = new javax.swing.JLabel();
        cbTglslip_THR_KK = new com.toedter.calendar.JDateChooser();
        lnik_THR_KK = new javax.swing.JLabel();
        bordernik_THR_KK = new javax.swing.JLabel();
        bCari_THR_KK = new javax.swing.JButton();
        txtNik_THR_KK = new javax.swing.JTextField();
        lnama_THR_KK = new javax.swing.JLabel();
        bordernama_THR_KK = new javax.swing.JLabel();
        txtNama_THR_KK = new javax.swing.JTextField();
        lkodejabatan_THR_KK = new javax.swing.JLabel();
        borderkodejabatan_THR_KK = new javax.swing.JLabel();
        cbKodejabatan_THR_KK = new javax.swing.JComboBox<>();
        lgajipokok_THR_KK = new javax.swing.JLabel();
        bordergajipokok_THR_KK = new javax.swing.JLabel();
        txtGajipokok_THR_KK = new javax.swing.JTextField();
        ltotalthr_THR_KK = new javax.swing.JLabel();
        bordertotalthr_THR_KK = new javax.swing.JLabel();
        txtTotalthr_THR_KK = new javax.swing.JTextField();
        garis_THR_KK = new javax.swing.JPanel();
        bTambah_THR_KK = new javax.swing.JButton();
        bUbah_THR_KK = new javax.swing.JButton();
        bHapus_THR_KK = new javax.swing.JButton();
        bBatal_THR_KK = new javax.swing.JButton();
        bHitungTHR_THR_KK = new javax.swing.JButton();
        bSearch_THR_KK = new javax.swing.JLabel();
        bordersearch_THR_KK = new javax.swing.JLabel();
        txtSearch_THR_KK = new javax.swing.JTextField();
        jScrollPane1_THR_KK = new javax.swing.JScrollPane();
        tbTHRKK = new javax.swing.JTable();
        bCetak_THR_KK = new javax.swing.JButton();
        conPenggajian = new javax.swing.JPanel();
        lFormPenggajian = new javax.swing.JLabel();
        borderFormPenggajian = new javax.swing.JLabel();
        TabelPenggajian = new javax.swing.JLabel();
        cbPenggajian = new javax.swing.JComboBox<>();
        penggajianTetap = new javax.swing.JPanel();
        lkodeslip_penggajian_KT = new javax.swing.JLabel();
        borderkodeslip_penggajian_KT = new javax.swing.JLabel();
        txtKodeslip_penggajian_KT = new javax.swing.JTextField();
        ltglslip_penggajian_KT = new javax.swing.JLabel();
        bordertglslip_penggajian_KT = new javax.swing.JLabel();
        cbTglslip_penggajian_KT = new com.toedter.calendar.JDateChooser();
        lnik_penggajian_KT = new javax.swing.JLabel();
        bordernik_penggajian_KT = new javax.swing.JLabel();
        bCari_penggajian_KT = new javax.swing.JButton();
        txtNik_penggajian_KT = new javax.swing.JTextField();
        lnama_penggajian_KT = new javax.swing.JLabel();
        bordernama_penggajian_KT = new javax.swing.JLabel();
        txtNama_penggajian_KT = new javax.swing.JTextField();
        lkodejabatan_penggajian_KT = new javax.swing.JLabel();
        borderkodejabatan_penggajian_KT = new javax.swing.JLabel();
        cbKodejabatan_penggajian_KT = new javax.swing.JComboBox<>();
        lgajipokok_penggajian_KT1 = new javax.swing.JLabel();
        bordergajipokok_penggajian_KT1 = new javax.swing.JLabel();
        txtGajipokok_penggajian_KT = new javax.swing.JTextField();
        ljmlabsensi_penggajian_KT = new javax.swing.JLabel();
        borderjmlabsensi_penggajian_KT = new javax.swing.JLabel();
        txtJmlabsensi_penggajian_KT = new javax.swing.JTextField();
        ltotalgaji_penggajian_KT = new javax.swing.JLabel();
        bordertotalgaji_penggajian_KT = new javax.swing.JLabel();
        txtTotalgaji_penggajian_KT = new javax.swing.JTextField();
        garis_penggajian_KT = new javax.swing.JPanel();
        bTambah_penggajian_KT = new javax.swing.JButton();
        bUbah_penggajian_KT = new javax.swing.JButton();
        bHapus_penggajian_KT = new javax.swing.JButton();
        bBatal_penggajian_KT = new javax.swing.JButton();
        bHitunggaji_penggajian_KT = new javax.swing.JButton();
        bSearch_penggajian_KT = new javax.swing.JLabel();
        bordersearch_penggajian_KT = new javax.swing.JLabel();
        txtSearch_penggajian_KT = new javax.swing.JTextField();
        jScrollPane1_penggajian_KT = new javax.swing.JScrollPane();
        tbPenggajianKT = new javax.swing.JTable();
        bCetak_PKT = new javax.swing.JButton();
        penggajianKontrak = new javax.swing.JPanel();
        lkodeslip_penggajian_KT1 = new javax.swing.JLabel();
        borderkodeslip_penggajian_KK = new javax.swing.JLabel();
        txtKodeslip_penggajian_KK = new javax.swing.JTextField();
        ltglslip_penggajian_KK = new javax.swing.JLabel();
        bordertglslip_penggajian_KK = new javax.swing.JLabel();
        cbTglslip_penggajian_KK = new com.toedter.calendar.JDateChooser();
        lnik_penggajian_KK = new javax.swing.JLabel();
        bordernik_penggajian_KK = new javax.swing.JLabel();
        bCari_penggajian_KK = new javax.swing.JButton();
        txtNik_penggajian_KK = new javax.swing.JTextField();
        lnama_penggajian_KK = new javax.swing.JLabel();
        bordernama_penggajian_KK = new javax.swing.JLabel();
        txtNama_penggajian_KK = new javax.swing.JTextField();
        lkodejabatan_penggajian_KK = new javax.swing.JLabel();
        borderkodejabatan_penggajian_KK = new javax.swing.JLabel();
        cbKodejabatan_penggajian_KK = new javax.swing.JComboBox<>();
        lgajipokok_penggajian_KK = new javax.swing.JLabel();
        bordergajipokok_penggajian_KK = new javax.swing.JLabel();
        txtGajipokok_penggajian_KK = new javax.swing.JTextField();
        ljmlabsensi_penggajian_KK = new javax.swing.JLabel();
        borderjmlabsensi_penggajian_KK = new javax.swing.JLabel();
        txtJmlabsensi_penggajian_KK = new javax.swing.JTextField();
        ltotalgaji_penggajian_KK = new javax.swing.JLabel();
        bordertotalgaji_penggajian_KK = new javax.swing.JLabel();
        txtTotalgaji_penggajian_KK = new javax.swing.JTextField();
        garis_penggajian_KK = new javax.swing.JPanel();
        bTambah_penggajian_KK = new javax.swing.JButton();
        bUbah_penggajian_KK = new javax.swing.JButton();
        bHapus_penggajian_KK = new javax.swing.JButton();
        bBatal_penggajian_KK = new javax.swing.JButton();
        bHitunggaji_penggajian_KK = new javax.swing.JButton();
        bSearch_penggajian_KK = new javax.swing.JLabel();
        bordersearch_penggajian_KK = new javax.swing.JLabel();
        txtSearch_penggajian_KK = new javax.swing.JTextField();
        jScrollPane1_penggajian_KK = new javax.swing.JScrollPane();
        tbPenggajianKK = new javax.swing.JTable();
        bCetak_PKK = new javax.swing.JButton();
        penggajianMagang = new javax.swing.JPanel();
        lkodeslip_penggajian_KM = new javax.swing.JLabel();
        borderkodeslip_penggajian_KM = new javax.swing.JLabel();
        txtKodeslip_penggajian_KM = new javax.swing.JTextField();
        ltglslip_penggajian_KM = new javax.swing.JLabel();
        bordertglslip_penggajian_KM = new javax.swing.JLabel();
        cbTglslip_penggajian_KM = new com.toedter.calendar.JDateChooser();
        lnik_penggajian_KM = new javax.swing.JLabel();
        bordernik_penggajian_KM = new javax.swing.JLabel();
        bCari_penggajian_KM = new javax.swing.JButton();
        txtNik_penggajian_KM = new javax.swing.JTextField();
        lnama_penggajian_KM = new javax.swing.JLabel();
        bordernama_penggajian_KM = new javax.swing.JLabel();
        txtNama_penggajian_KM = new javax.swing.JTextField();
        lkodejabatan_penggajian_KM = new javax.swing.JLabel();
        borderkodejabatan_penggajian_KM = new javax.swing.JLabel();
        cbKodejabatan_penggajian_KM = new javax.swing.JComboBox<>();
        lgajipokok_penggajian_KM = new javax.swing.JLabel();
        bordergajipokok_penggajian_KM = new javax.swing.JLabel();
        txtGajipokok_penggajian_KM = new javax.swing.JTextField();
        ljmlabsensi_penggajian_KM = new javax.swing.JLabel();
        borderjmlabsensi_penggajian_KM = new javax.swing.JLabel();
        txtJmlabsensi_penggajian_KM = new javax.swing.JTextField();
        ltotalgaji_penggajian_KM = new javax.swing.JLabel();
        bordertotalgaji_penggajian_KM = new javax.swing.JLabel();
        txtTotalgaji_penggajian_KM = new javax.swing.JTextField();
        garis_penggajian_KM = new javax.swing.JPanel();
        bTambah_penggajian_KM = new javax.swing.JButton();
        bUbah_penggajian_KM = new javax.swing.JButton();
        bHapus_penggajian_KM = new javax.swing.JButton();
        bBatal_penggajian_KM = new javax.swing.JButton();
        bHitunggaji_penggajian_KM = new javax.swing.JButton();
        bSearch_penggajian_KM = new javax.swing.JLabel();
        bordersearch_penggajian_KM = new javax.swing.JLabel();
        txtSearch_penggajian_KM = new javax.swing.JTextField();
        jScrollPane1_penggajian_KM = new javax.swing.JScrollPane();
        tbPenggajianKM = new javax.swing.JTable();
        bCetak_PKM = new javax.swing.JButton();
        conJabatan = new javax.swing.JPanel();
        lForm_User1 = new javax.swing.JLabel();
        borderForm_User1 = new javax.swing.JLabel();
        lTabel_Jabatan = new javax.swing.JLabel();
        lkodejabatan_Jabatan = new javax.swing.JLabel();
        borderJabatan_Jabatan = new javax.swing.JLabel();
        txtJabatan = new javax.swing.JTextField();
        ljabatan_Jabatan = new javax.swing.JLabel();
        borderKode_Jabatan_Jabatan = new javax.swing.JLabel();
        cbKode_Jabatan_Jabatan = new javax.swing.JComboBox<>();
        lgajipokok_Jabatan = new javax.swing.JLabel();
        bordergajipokok_Jabatan = new javax.swing.JLabel();
        txtGajipokok = new javax.swing.JTextField();
        garis_Jabatan = new javax.swing.JPanel();
        bTambah_Jabatan = new javax.swing.JButton();
        bUbah_Jabatan = new javax.swing.JButton();
        bHapus_Jabatan = new javax.swing.JButton();
        bBatal_Jabatan = new javax.swing.JButton();
        bSearch_Jabatan = new javax.swing.JLabel();
        bordersearch_Jabatan = new javax.swing.JLabel();
        txtSearch_Jabatan = new javax.swing.JTextField();
        jScrollPane_Jabatan = new javax.swing.JScrollPane();
        tbJabatan = new javax.swing.JTable();
        bCetak_Jabatan = new javax.swing.JButton();
        conKaryawan = new javax.swing.JPanel();
        lFormKaryawan = new javax.swing.JLabel();
        borderFormKaryawan = new javax.swing.JLabel();
        lTabelKaryawan = new javax.swing.JLabel();
        cbKaryawan = new javax.swing.JComboBox<>();
        karyawanTetap = new javax.swing.JPanel();
        lnik_KT = new javax.swing.JLabel();
        bordernik_KT = new javax.swing.JLabel();
        txtNik_KT = new javax.swing.JTextField();
        lnama_KT = new javax.swing.JLabel();
        bordernama_KT = new javax.swing.JLabel();
        txtNama_KT = new javax.swing.JTextField();
        lemail_KT = new javax.swing.JLabel();
        borderemail_KT = new javax.swing.JLabel();
        txtEmail_KT = new javax.swing.JTextField();
        lnotelp_KT = new javax.swing.JLabel();
        bordernotelp_KT = new javax.swing.JLabel();
        txtNotelp_KT = new javax.swing.JTextField();
        lalamat_KT = new javax.swing.JLabel();
        borderalamat_KT = new javax.swing.JLabel();
        jScrollPane_KT = new javax.swing.JScrollPane();
        txtAlamat_KT = new javax.swing.JTextArea();
        ltglmsk_KT = new javax.swing.JLabel();
        bordertglmsk_KT = new javax.swing.JLabel();
        cbTglmsk_KT = new com.toedter.calendar.JDateChooser();
        ljabatan_KT = new javax.swing.JLabel();
        borderjabatan_KT = new javax.swing.JLabel();
        cbJabatan_KT = new javax.swing.JComboBox<>();
        garis_KT = new javax.swing.JPanel();
        bTambah_KT = new javax.swing.JButton();
        bUbah_KT = new javax.swing.JButton();
        bHapus_KT = new javax.swing.JButton();
        bBatal_KT = new javax.swing.JButton();
        bSearch_KT = new javax.swing.JLabel();
        bordersearch_KT = new javax.swing.JLabel();
        txtSearch_KT = new javax.swing.JTextField();
        jScrollPane1_KT = new javax.swing.JScrollPane();
        tbKT = new javax.swing.JTable();
        bCetak_KT = new javax.swing.JButton();
        karyawanKontrak = new javax.swing.JPanel();
        lnik_KK = new javax.swing.JLabel();
        bordernik_KK = new javax.swing.JLabel();
        txtNik_KK = new javax.swing.JTextField();
        lnama_KK = new javax.swing.JLabel();
        bordernama_KK = new javax.swing.JLabel();
        txtNama_KK = new javax.swing.JTextField();
        lemail_KK = new javax.swing.JLabel();
        borderemail_KK = new javax.swing.JLabel();
        txtEmail_KK = new javax.swing.JTextField();
        lnotelp_KK = new javax.swing.JLabel();
        bordernotelp_KK = new javax.swing.JLabel();
        txtNotelp_KK = new javax.swing.JTextField();
        lalamat_KK = new javax.swing.JLabel();
        borderalamat_KK = new javax.swing.JLabel();
        jScrollPane_KK = new javax.swing.JScrollPane();
        txtAlamat_KK = new javax.swing.JTextArea();
        ltglmsk_KK = new javax.swing.JLabel();
        bordertglmsk_KK = new javax.swing.JLabel();
        cbTglmsk_KK = new com.toedter.calendar.JDateChooser();
        ltglklr_KK = new javax.swing.JLabel();
        bordertglklr_KK = new javax.swing.JLabel();
        cbTglklr_KK = new com.toedter.calendar.JDateChooser();
        ljabatan_KK = new javax.swing.JLabel();
        borderjabatan_KK = new javax.swing.JLabel();
        cbJabatan_KK = new javax.swing.JComboBox<>();
        garis_KK = new javax.swing.JPanel();
        bTambah_KK = new javax.swing.JButton();
        bUbah_KK = new javax.swing.JButton();
        bHapus_KK = new javax.swing.JButton();
        bBatal_KK = new javax.swing.JButton();
        bSearch_KK = new javax.swing.JLabel();
        bordersearch_KK = new javax.swing.JLabel();
        txtSearch_KK = new javax.swing.JTextField();
        jScrollPane1_KK = new javax.swing.JScrollPane();
        tbKK = new javax.swing.JTable();
        bCetak_KK = new javax.swing.JButton();
        karyawanMagang = new javax.swing.JPanel();
        lnik_KK1 = new javax.swing.JLabel();
        bordernik_KM = new javax.swing.JLabel();
        txtNik_KM = new javax.swing.JTextField();
        lnama_KM = new javax.swing.JLabel();
        bordernama_KM = new javax.swing.JLabel();
        txtNama_KM = new javax.swing.JTextField();
        lemail_KM = new javax.swing.JLabel();
        borderemail_KM = new javax.swing.JLabel();
        txtEmail_KM = new javax.swing.JTextField();
        lnotelp_KM = new javax.swing.JLabel();
        bordernotelp_KM = new javax.swing.JLabel();
        txtNotelp_KM = new javax.swing.JTextField();
        lalamat_KM = new javax.swing.JLabel();
        borderalamat_KM = new javax.swing.JLabel();
        jScrollPane_KM = new javax.swing.JScrollPane();
        txtAlamat_KM = new javax.swing.JTextArea();
        ltglmsk_KM = new javax.swing.JLabel();
        bordertglmsk_KM = new javax.swing.JLabel();
        cbTglmsk_KM = new com.toedter.calendar.JDateChooser();
        ltglklr_KM = new javax.swing.JLabel();
        bordertglklr_KM = new javax.swing.JLabel();
        cbTglklr_KM = new com.toedter.calendar.JDateChooser();
        ljabatan_KM = new javax.swing.JLabel();
        borderjabatan_KM = new javax.swing.JLabel();
        cbJabatan_KM = new javax.swing.JComboBox<>();
        garis_KM = new javax.swing.JPanel();
        bTambah_KM = new javax.swing.JButton();
        bUbah_KM = new javax.swing.JButton();
        bHapus_KM = new javax.swing.JButton();
        bBatal_KM = new javax.swing.JButton();
        bSearch_KM = new javax.swing.JLabel();
        bordersearch_KM = new javax.swing.JLabel();
        txtSearch_KM = new javax.swing.JTextField();
        jScrollPane1_KM = new javax.swing.JScrollPane();
        tbKM = new javax.swing.JTable();
        bCetak_KM = new javax.swing.JButton();
        conAdmin = new javax.swing.JPanel();
        lForm_Admin = new javax.swing.JLabel();
        borderForm_Admin = new javax.swing.JLabel();
        lTabel_Admin = new javax.swing.JLabel();
        lusername_Admin = new javax.swing.JLabel();
        borderusername_Admin = new javax.swing.JLabel();
        txtUsername_Admin = new javax.swing.JTextField();
        lnama_Admin = new javax.swing.JLabel();
        bordernama_Admin = new javax.swing.JLabel();
        txtNama_Admin = new javax.swing.JTextField();
        lemail_Admin = new javax.swing.JLabel();
        borderemail_Admin = new javax.swing.JLabel();
        txtEmail_Admin = new javax.swing.JTextField();
        lnotelp_Admin = new javax.swing.JLabel();
        bordernotelp_Admin = new javax.swing.JLabel();
        txtNotelp_Admin = new javax.swing.JTextField();
        lalamat_Admin = new javax.swing.JLabel();
        borderalamat_Admin = new javax.swing.JLabel();
        jScrollPane_Alamat = new javax.swing.JScrollPane();
        txtAlamat_Admin = new javax.swing.JTextArea();
        lpassword_Admin = new javax.swing.JLabel();
        borderpassword_Admin = new javax.swing.JLabel();
        txtPassword_Admin = new javax.swing.JPasswordField();
        garis_Admin = new javax.swing.JPanel();
        bTambah_Admin = new javax.swing.JButton();
        bBatal_Admin = new javax.swing.JButton();
        bSearch_Admin = new javax.swing.JLabel();
        bordersearch_Admin = new javax.swing.JLabel();
        txtSearch_Admin = new javax.swing.JTextField();
        jScrollPane_Admin = new javax.swing.JScrollPane();
        tbAdmin = new javax.swing.JTable();
        conDashboard = new javax.swing.JPanel();
        lDashboard = new javax.swing.JLabel();
        iconKT = new javax.swing.JLabel();
        lJumlahKaryawanTetap = new javax.swing.JLabel();
        lvalueKT = new javax.swing.JLabel();
        satu = new javax.swing.JLabel();
        iconGKT = new javax.swing.JLabel();
        lJumlahGajiKaryawanTetap = new javax.swing.JLabel();
        lTelahDibayar = new javax.swing.JLabel();
        lvalueGKT = new javax.swing.JLabel();
        dua = new javax.swing.JLabel();
        iconKK = new javax.swing.JLabel();
        lJumlahKaryawanKontrak = new javax.swing.JLabel();
        lvalueKK = new javax.swing.JLabel();
        tiga = new javax.swing.JLabel();
        iconGKK = new javax.swing.JLabel();
        lJumlahGajiKaryawanKontrak = new javax.swing.JLabel();
        lTelahDibayarKontrak = new javax.swing.JLabel();
        lvalueGKK = new javax.swing.JLabel();
        empat = new javax.swing.JLabel();
        iconKM = new javax.swing.JLabel();
        lJumlahKaryawanMagang = new javax.swing.JLabel();
        lvalueKM = new javax.swing.JLabel();
        lima = new javax.swing.JLabel();
        iconGKM = new javax.swing.JLabel();
        lJumlahGajiKaryawanMagang = new javax.swing.JLabel();
        lTelahDibayarMagang = new javax.swing.JLabel();
        lvalueGKM = new javax.swing.JLabel();
        enam = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelProfile.setBackground(new java.awt.Color(255, 255, 255));
        panelProfile.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nmprofile.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 26)); // NOI18N
        nmprofile.setForeground(new java.awt.Color(135, 33, 36));
        nmprofile.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nmprofile.setText("Wahyu Hadi");
        nmprofile.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        panelProfile.add(nmprofile, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 450, -1));

        avatar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 26)); // NOI18N
        avatar.setForeground(new java.awt.Color(135, 33, 36));
        avatar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/profile-avatar.png"))); // NOI18N
        panelProfile.add(avatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1033, 13, -1, -1));

        getContentPane().add(panelProfile, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 0, 1092, 68));

        panelMenu.setBackground(new java.awt.Color(207, 36, 42));
        panelMenu.setForeground(new java.awt.Color(207, 36, 42));
        panelMenu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lAdministrator.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 33)); // NOI18N
        lAdministrator.setForeground(new java.awt.Color(255, 255, 255));
        lAdministrator.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lAdministrator.setText("Administrator");
        panelMenu.add(lAdministrator, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 14, 274, -1));

        garisMenu.setBackground(new java.awt.Color(135, 33, 36));

        javax.swing.GroupLayout garisMenuLayout = new javax.swing.GroupLayout(garisMenu);
        garisMenu.setLayout(garisMenuLayout);
        garisMenuLayout.setHorizontalGroup(
            garisMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 254, Short.MAX_VALUE)
        );
        garisMenuLayout.setVerticalGroup(
            garisMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        panelMenu.add(garisMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 68, 254, 3));

        bdashboard.setBackground(new java.awt.Color(207, 36, 42));
        bdashboard.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bdashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bdashboardMouseClicked(evt);
            }
        });
        bdashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lMenuDashboard.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        lMenuDashboard.setForeground(new java.awt.Color(255, 255, 255));
        lMenuDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lMenuDashboard.setText("Dashboard");
        bdashboard.add(lMenuDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 24, 189, -1));

        iconDashboard.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        iconDashboard.setForeground(new java.awt.Color(255, 255, 255));
        iconDashboard.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sidebar-dashboard.png"))); // NOI18N
        bdashboard.add(iconDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 24, 44, -1));

        panelMenu.add(bdashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 78, 274, 77));

        badmin.setBackground(new java.awt.Color(207, 36, 42));
        badmin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        badmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                badminMouseClicked(evt);
            }
        });
        badmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lMenuAdmin.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        lMenuAdmin.setForeground(new java.awt.Color(255, 255, 255));
        lMenuAdmin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lMenuAdmin.setText("Admin");
        badmin.add(lMenuAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 20, 189, 36));

        iconAdmin.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        iconAdmin.setForeground(new java.awt.Color(255, 255, 255));
        iconAdmin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconAdmin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sidebar-admin.png"))); // NOI18N
        badmin.add(iconAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 20, 44, -1));

        panelMenu.add(badmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 155, 274, 77));

        bkaryawan.setBackground(new java.awt.Color(207, 36, 42));
        bkaryawan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bkaryawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bkaryawanMouseClicked(evt);
            }
        });
        bkaryawan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lMenuKaryawan.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        lMenuKaryawan.setForeground(new java.awt.Color(255, 255, 255));
        lMenuKaryawan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lMenuKaryawan.setText("Karyawan");
        bkaryawan.add(lMenuKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 20, 189, 36));

        iconKaryawan.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        iconKaryawan.setForeground(new java.awt.Color(255, 255, 255));
        iconKaryawan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sidebar-karyawan.png"))); // NOI18N
        bkaryawan.add(iconKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 17, 44, -1));

        panelMenu.add(bkaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 232, 274, 77));

        bjabatan.setBackground(new java.awt.Color(207, 36, 42));
        bjabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bjabatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bjabatanMouseClicked(evt);
            }
        });
        bjabatan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lMenuJabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        lMenuJabatan.setForeground(new java.awt.Color(255, 255, 255));
        lMenuJabatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lMenuJabatan.setText("Jabatan");
        bjabatan.add(lMenuJabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 20, 189, 36));

        iconJabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        iconJabatan.setForeground(new java.awt.Color(255, 255, 255));
        iconJabatan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconJabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sidebar-jabatan.png"))); // NOI18N
        bjabatan.add(iconJabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 20, 44, 36));

        panelMenu.add(bjabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 309, 274, 77));

        bpenggajian.setBackground(new java.awt.Color(207, 36, 42));
        bpenggajian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bpenggajian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bpenggajianMouseClicked(evt);
            }
        });
        bpenggajian.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lMenuPenggajian.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        lMenuPenggajian.setForeground(new java.awt.Color(255, 255, 255));
        lMenuPenggajian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lMenuPenggajian.setText("Penggajian");
        bpenggajian.add(lMenuPenggajian, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 20, 189, 36));

        iconPenggajian.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        iconPenggajian.setForeground(new java.awt.Color(255, 255, 255));
        iconPenggajian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconPenggajian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sidebar-penggajian.png"))); // NOI18N
        bpenggajian.add(iconPenggajian, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 20, 44, 36));

        panelMenu.add(bpenggajian, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 386, 274, 77));

        bTHR.setBackground(new java.awt.Color(207, 36, 42));
        bTHR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTHR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bTHRMouseClicked(evt);
            }
        });
        bTHR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lMenuTHR.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        lMenuTHR.setForeground(new java.awt.Color(255, 255, 255));
        lMenuTHR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lMenuTHR.setText("THR");
        bTHR.add(lMenuTHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(83, 20, 189, 36));

        iconTHR.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        iconTHR.setForeground(new java.awt.Color(255, 255, 255));
        iconTHR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        iconTHR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sidebar-thr.png"))); // NOI18N
        bTHR.add(iconTHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 20, 44, 36));

        panelMenu.add(bTHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 463, 274, 77));

        blogout.setBackground(new java.awt.Color(238, 238, 238));
        blogout.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 28)); // NOI18N
        blogout.setForeground(new java.awt.Color(207, 36, 42));
        blogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-keluar.png"))); // NOI18N
        blogout.setText("   Logout");
        blogout.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        blogout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        blogout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                blogoutMouseClicked(evt);
            }
        });
        panelMenu.add(blogout, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 650, 240, 103));

        getContentPane().add(panelMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 274, 768));

        panelContent.setBackground(new java.awt.Color(255, 255, 255));
        panelContent.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        conTHR.setBackground(new java.awt.Color(255, 255, 255));
        conTHR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lFormTHR.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        lFormTHR.setForeground(new java.awt.Color(135, 33, 36));
        lFormTHR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lFormTHR.setText("Form THR");
        conTHR.add(lFormTHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 20, 350, -1));

        borderFormTHR.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/border-contentkaryawan.png"))); // NOI18N
        conTHR.add(borderFormTHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 80, -1, -1));

        TabelTHR.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        TabelTHR.setForeground(new java.awt.Color(135, 33, 36));
        TabelTHR.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TabelTHR.setText("Tabel THR");
        conTHR.add(TabelTHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 431, 360, -1));

        cbTHR.setBackground(new java.awt.Color(255, 0, 0));
        cbTHR.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        cbTHR.setForeground(new java.awt.Color(255, 255, 255));
        cbTHR.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TETAP", "KONTRAK" }));
        cbTHR.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbTHR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbTHR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTHRActionPerformed(evt);
            }
        });
        conTHR.add(cbTHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 182, 39));

        THRTetap.setBackground(new java.awt.Color(255, 255, 255));
        THRTetap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lkodeslip_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodeslip_THR_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkodeslip_THR_KT.setText("Kode Slip");
        THRTetap.add(lkodeslip_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 105, 142, 35));

        borderkodeslip_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderkodeslip_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRTetap.add(borderkodeslip_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 105, -1, -1));

        txtKodeslip_THR_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtKodeslip_THR_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtKodeslip_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtKodeslip_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRTetap.add(txtKodeslip_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 106, 279, 33));

        ltglslip_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltglslip_THR_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltglslip_THR_KT.setText("Tanggal Slip");
        THRTetap.add(ltglslip_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 159, 142, 35));

        bordertglslip_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordertglslip_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRTetap.add(bordertglslip_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 159, -1, -1));

        cbTglslip_THR_KT.setBackground(new java.awt.Color(255, 255, 255));
        THRTetap.add(cbTglslip_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 160, 291, 33));

        lnik_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnik_THR_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnik_THR_KT.setText("NIK");
        THRTetap.add(lnik_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 212, 142, 35));

        bordernik_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-228x35.png"))); // NOI18N
        bordernik_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRTetap.add(bordernik_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 212, -1, -1));

        bCari_THR_KT.setBackground(new java.awt.Color(153, 0, 0));
        bCari_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        bCari_THR_KT.setForeground(new java.awt.Color(255, 255, 255));
        bCari_THR_KT.setText("Cari");
        bCari_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCari_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCari_THR_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCari_THR_KTActionPerformed(evt);
            }
        });
        THRTetap.add(bCari_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 213, 58, 34));

        txtNik_THR_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNik_THR_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNik_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        THRTetap.add(txtNik_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 213, 212, 33));

        lnama_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnama_THR_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnama_THR_KT.setText("Nama");
        THRTetap.add(lnama_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 266, 142, 35));

        bordernama_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordernama_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRTetap.add(bordernama_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 266, -1, -1));

        txtNama_THR_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNama_THR_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNama_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        THRTetap.add(txtNama_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 267, 279, 33));

        lkodejabatan_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodejabatan_THR_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkodejabatan_THR_KT.setText("Kode Jabatan");
        THRTetap.add(lkodejabatan_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 105, 183, 35));

        borderkodejabatan_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderkodejabatan_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        THRTetap.add(borderkodejabatan_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 105, -1, -1));

        cbKodejabatan_THR_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        cbKodejabatan_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbKodejabatan_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        THRTetap.add(cbKodejabatan_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(751, 103, 299, 39));

        lgajipokok_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lgajipokok_THR_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lgajipokok_THR_KT.setText("Gaji Pokok");
        THRTetap.add(lgajipokok_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 159, 183, 35));

        bordergajipokok_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordergajipokok_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        THRTetap.add(bordergajipokok_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 159, -1, -1));

        txtGajipokok_THR_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtGajipokok_THR_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtGajipokok_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtGajipokok_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRTetap.add(txtGajipokok_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 159, 279, 33));

        ltotalthr_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltotalthr_THR_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltotalthr_THR_KT.setText("Total THR");
        THRTetap.add(ltotalthr_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 212, 183, 35));

        bordertotalthr_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordertotalthr_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        THRTetap.add(bordertotalthr_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 212, -1, -1));

        txtTotalthr_THR_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtTotalthr_THR_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalthr_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTotalthr_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRTetap.add(txtTotalthr_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 212, 279, 33));

        garis_THR_KT.setBackground(new java.awt.Color(205, 205, 205));

        javax.swing.GroupLayout garis_THR_KTLayout = new javax.swing.GroupLayout(garis_THR_KT);
        garis_THR_KT.setLayout(garis_THR_KTLayout);
        garis_THR_KTLayout.setHorizontalGroup(
            garis_THR_KTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1009, Short.MAX_VALUE)
        );
        garis_THR_KTLayout.setVerticalGroup(
            garis_THR_KTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        THRTetap.add(garis_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 324, 1009, 3));

        bTambah_THR_KT.setBackground(new java.awt.Color(29, 160, 42));
        bTambah_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bTambah_THR_KT.setForeground(new java.awt.Color(255, 255, 255));
        bTambah_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-tambah.png"))); // NOI18N
        bTambah_THR_KT.setText("   Tambah");
        bTambah_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bTambah_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambah_THR_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambah_THR_KTActionPerformed(evt);
            }
        });
        THRTetap.add(bTambah_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 342, 172, 54));

        bUbah_THR_KT.setBackground(new java.awt.Color(29, 113, 160));
        bUbah_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bUbah_THR_KT.setForeground(new java.awt.Color(255, 255, 255));
        bUbah_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-ubah.png"))); // NOI18N
        bUbah_THR_KT.setText("   Ubah");
        bUbah_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bUbah_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bUbah_THR_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbah_THR_KTActionPerformed(evt);
            }
        });
        THRTetap.add(bUbah_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 342, 172, 54));

        bHapus_THR_KT.setBackground(new java.awt.Color(160, 29, 29));
        bHapus_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHapus_THR_KT.setForeground(new java.awt.Color(255, 255, 255));
        bHapus_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-hapus.png"))); // NOI18N
        bHapus_THR_KT.setText("   Hapus");
        bHapus_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHapus_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHapus_THR_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapus_THR_KTActionPerformed(evt);
            }
        });
        THRTetap.add(bHapus_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 342, 172, 54));

        bBatal_THR_KT.setBackground(new java.awt.Color(204, 204, 204));
        bBatal_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bBatal_THR_KT.setForeground(new java.awt.Color(96, 96, 96));
        bBatal_THR_KT.setText("Batal");
        bBatal_THR_KT.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 96, 96), 3, true));
        bBatal_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bBatal_THR_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatal_THR_KTActionPerformed(evt);
            }
        });
        THRTetap.add(bBatal_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 345, 172, 49));

        bHitungTHR_THR_KT.setBackground(new java.awt.Color(153, 0, 0));
        bHitungTHR_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHitungTHR_THR_KT.setForeground(new java.awt.Color(255, 255, 255));
        bHitungTHR_THR_KT.setText("Hitung THR");
        bHitungTHR_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHitungTHR_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHitungTHR_THR_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHitungTHR_THR_KTActionPerformed(evt);
            }
        });
        THRTetap.add(bHitungTHR_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(862, 342, 186, 54));

        bSearch_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        THRTetap.add(bSearch_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 430, 45, 50));

        bordersearch_THR_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        THRTetap.add(bordersearch_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 430, -1, -1));

        txtSearch_THR_KT.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch_THR_KT.setBorder(null);
        txtSearch_THR_KT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearch_THR_KTKeyTyped(evt);
            }
        });
        THRTetap.add(txtSearch_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 432, 270, 47));

        tbTHRKT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tbTHRKT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTHRKTMouseClicked(evt);
            }
        });
        jScrollPane1_THR_KT.setViewportView(tbTHRKT);

        THRTetap.add(jScrollPane1_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 494, 1057, 125));

        bCetak_THR_KT.setBackground(new java.awt.Color(203, 115, 12));
        bCetak_THR_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bCetak_THR_KT.setForeground(new java.awt.Color(255, 255, 255));
        bCetak_THR_KT.setText("Cetak Laporan");
        bCetak_THR_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCetak_THR_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCetak_THR_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetak_THR_KTActionPerformed(evt);
            }
        });
        THRTetap.add(bCetak_THR_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 632, 259, 54));

        conTHR.add(THRTetap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        THRKontrak.setBackground(new java.awt.Color(255, 255, 255));
        THRKontrak.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lkodeslip_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodeslip_THR_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkodeslip_THR_KK.setText("Kode Slip");
        THRKontrak.add(lkodeslip_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 105, 142, 35));

        txtKodeslip_THR_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtKodeslip_THR_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtKodeslip_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtKodeslip_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRKontrak.add(txtKodeslip_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 106, 279, 33));

        borderkodeslip_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderkodeslip_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRKontrak.add(borderkodeslip_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 105, -1, -1));

        ltglslip_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltglslip_THR_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltglslip_THR_KK.setText("Tanggal Slip");
        THRKontrak.add(ltglslip_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 159, 142, 35));

        bordertglslip_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordertglslip_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRKontrak.add(bordertglslip_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 159, -1, -1));

        cbTglslip_THR_KK.setBackground(new java.awt.Color(255, 255, 255));
        THRKontrak.add(cbTglslip_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 160, 291, 33));

        lnik_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnik_THR_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnik_THR_KK.setText("NIK");
        THRKontrak.add(lnik_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 212, 142, 35));

        bordernik_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-228x35.png"))); // NOI18N
        bordernik_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRKontrak.add(bordernik_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 212, -1, -1));

        bCari_THR_KK.setBackground(new java.awt.Color(153, 0, 0));
        bCari_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        bCari_THR_KK.setForeground(new java.awt.Color(255, 255, 255));
        bCari_THR_KK.setText("Cari");
        bCari_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCari_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCari_THR_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCari_THR_KKActionPerformed(evt);
            }
        });
        THRKontrak.add(bCari_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 213, 58, 34));

        txtNik_THR_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNik_THR_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNik_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        THRKontrak.add(txtNik_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 213, 212, 33));

        lnama_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnama_THR_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnama_THR_KK.setText("Nama");
        THRKontrak.add(lnama_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 266, 142, 35));

        bordernama_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordernama_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRKontrak.add(bordernama_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 266, -1, -1));

        txtNama_THR_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNama_THR_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNama_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        THRKontrak.add(txtNama_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 267, 279, 33));

        lkodejabatan_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodejabatan_THR_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkodejabatan_THR_KK.setText("Kode Jabatan");
        THRKontrak.add(lkodejabatan_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 105, 183, 35));

        borderkodejabatan_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderkodejabatan_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        THRKontrak.add(borderkodejabatan_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 105, -1, -1));

        cbKodejabatan_THR_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        cbKodejabatan_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbKodejabatan_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        THRKontrak.add(cbKodejabatan_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(751, 103, 299, 39));

        lgajipokok_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lgajipokok_THR_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lgajipokok_THR_KK.setText("Gaji Pokok");
        THRKontrak.add(lgajipokok_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 159, 183, 35));

        bordergajipokok_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordergajipokok_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        THRKontrak.add(bordergajipokok_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 159, -1, -1));

        txtGajipokok_THR_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtGajipokok_THR_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtGajipokok_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtGajipokok_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRKontrak.add(txtGajipokok_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 159, 279, 33));

        ltotalthr_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltotalthr_THR_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltotalthr_THR_KK.setText("Total THR");
        THRKontrak.add(ltotalthr_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 212, 183, 35));

        bordertotalthr_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordertotalthr_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        THRKontrak.add(bordertotalthr_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 212, -1, -1));

        txtTotalthr_THR_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtTotalthr_THR_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalthr_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTotalthr_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        THRKontrak.add(txtTotalthr_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 212, 279, 33));

        garis_THR_KK.setBackground(new java.awt.Color(205, 205, 205));

        javax.swing.GroupLayout garis_THR_KKLayout = new javax.swing.GroupLayout(garis_THR_KK);
        garis_THR_KK.setLayout(garis_THR_KKLayout);
        garis_THR_KKLayout.setHorizontalGroup(
            garis_THR_KKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1009, Short.MAX_VALUE)
        );
        garis_THR_KKLayout.setVerticalGroup(
            garis_THR_KKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        THRKontrak.add(garis_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 324, 1009, 3));

        bTambah_THR_KK.setBackground(new java.awt.Color(29, 160, 42));
        bTambah_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bTambah_THR_KK.setForeground(new java.awt.Color(255, 255, 255));
        bTambah_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-tambah.png"))); // NOI18N
        bTambah_THR_KK.setText("   Tambah");
        bTambah_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bTambah_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambah_THR_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambah_THR_KKActionPerformed(evt);
            }
        });
        THRKontrak.add(bTambah_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 342, 172, 54));

        bUbah_THR_KK.setBackground(new java.awt.Color(29, 113, 160));
        bUbah_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bUbah_THR_KK.setForeground(new java.awt.Color(255, 255, 255));
        bUbah_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-ubah.png"))); // NOI18N
        bUbah_THR_KK.setText("   Ubah");
        bUbah_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bUbah_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bUbah_THR_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbah_THR_KKActionPerformed(evt);
            }
        });
        THRKontrak.add(bUbah_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 342, 172, 54));

        bHapus_THR_KK.setBackground(new java.awt.Color(160, 29, 29));
        bHapus_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHapus_THR_KK.setForeground(new java.awt.Color(255, 255, 255));
        bHapus_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-hapus.png"))); // NOI18N
        bHapus_THR_KK.setText("   Hapus");
        bHapus_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHapus_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHapus_THR_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapus_THR_KKActionPerformed(evt);
            }
        });
        THRKontrak.add(bHapus_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 342, 172, 54));

        bBatal_THR_KK.setBackground(new java.awt.Color(204, 204, 204));
        bBatal_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bBatal_THR_KK.setForeground(new java.awt.Color(96, 96, 96));
        bBatal_THR_KK.setText("Batal");
        bBatal_THR_KK.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 96, 96), 3, true));
        bBatal_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bBatal_THR_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatal_THR_KKActionPerformed(evt);
            }
        });
        THRKontrak.add(bBatal_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 345, 172, 49));

        bHitungTHR_THR_KK.setBackground(new java.awt.Color(153, 0, 0));
        bHitungTHR_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHitungTHR_THR_KK.setForeground(new java.awt.Color(255, 255, 255));
        bHitungTHR_THR_KK.setText("Hitung THR");
        bHitungTHR_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHitungTHR_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHitungTHR_THR_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHitungTHR_THR_KKActionPerformed(evt);
            }
        });
        THRKontrak.add(bHitungTHR_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(862, 342, 186, 54));

        bSearch_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSearch_THR_KK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bSearch_THR_KKMouseClicked(evt);
            }
        });
        THRKontrak.add(bSearch_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 430, 45, 50));

        bordersearch_THR_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        THRKontrak.add(bordersearch_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 430, -1, -1));

        txtSearch_THR_KK.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch_THR_KK.setBorder(null);
        txtSearch_THR_KK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearch_THR_KKKeyTyped(evt);
            }
        });
        THRKontrak.add(txtSearch_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 432, 270, 47));

        tbTHRKK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tbTHRKK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbTHRKKMouseClicked(evt);
            }
        });
        jScrollPane1_THR_KK.setViewportView(tbTHRKK);

        THRKontrak.add(jScrollPane1_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 494, 1057, 125));

        bCetak_THR_KK.setBackground(new java.awt.Color(203, 115, 12));
        bCetak_THR_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bCetak_THR_KK.setForeground(new java.awt.Color(255, 255, 255));
        bCetak_THR_KK.setText("Cetak Laporan");
        bCetak_THR_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCetak_THR_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCetak_THR_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetak_THR_KKActionPerformed(evt);
            }
        });
        THRKontrak.add(bCetak_THR_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 632, 259, 54));

        conTHR.add(THRKontrak, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        panelContent.add(conTHR, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        conPenggajian.setBackground(new java.awt.Color(255, 255, 255));
        conPenggajian.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lFormPenggajian.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        lFormPenggajian.setForeground(new java.awt.Color(135, 33, 36));
        lFormPenggajian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lFormPenggajian.setText("Form Penggajian");
        conPenggajian.add(lFormPenggajian, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 20, 350, -1));

        borderFormPenggajian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/border-contentkaryawan.png"))); // NOI18N
        conPenggajian.add(borderFormPenggajian, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 80, -1, -1));

        TabelPenggajian.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        TabelPenggajian.setForeground(new java.awt.Color(135, 33, 36));
        TabelPenggajian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TabelPenggajian.setText("Tabel Penggajian");
        conPenggajian.add(TabelPenggajian, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 431, 360, -1));

        cbPenggajian.setBackground(new java.awt.Color(255, 0, 0));
        cbPenggajian.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        cbPenggajian.setForeground(new java.awt.Color(255, 255, 255));
        cbPenggajian.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TETAP", "KONTRAK", "MAGANG" }));
        cbPenggajian.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbPenggajian.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbPenggajian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPenggajianActionPerformed(evt);
            }
        });
        conPenggajian.add(cbPenggajian, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 182, 39));

        penggajianTetap.setBackground(new java.awt.Color(255, 255, 255));
        penggajianTetap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lkodeslip_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodeslip_penggajian_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkodeslip_penggajian_KT.setText("Kode Slip");
        penggajianTetap.add(lkodeslip_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 105, 142, 35));

        borderkodeslip_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderkodeslip_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianTetap.add(borderkodeslip_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 105, -1, -1));

        txtKodeslip_penggajian_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtKodeslip_penggajian_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtKodeslip_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtKodeslip_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianTetap.add(txtKodeslip_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 106, 279, 33));

        ltglslip_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltglslip_penggajian_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltglslip_penggajian_KT.setText("Tanggal Slip");
        penggajianTetap.add(ltglslip_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 159, 142, 35));

        bordertglslip_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordertglslip_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianTetap.add(bordertglslip_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 159, -1, -1));

        cbTglslip_penggajian_KT.setBackground(new java.awt.Color(255, 255, 255));
        penggajianTetap.add(cbTglslip_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 160, 291, 33));

        lnik_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnik_penggajian_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnik_penggajian_KT.setText("NIK");
        penggajianTetap.add(lnik_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 212, 142, 35));

        bordernik_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-228x35.png"))); // NOI18N
        bordernik_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianTetap.add(bordernik_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 212, -1, -1));

        bCari_penggajian_KT.setBackground(new java.awt.Color(153, 0, 0));
        bCari_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        bCari_penggajian_KT.setForeground(new java.awt.Color(255, 255, 255));
        bCari_penggajian_KT.setText("Cari");
        bCari_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCari_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCari_penggajian_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCari_penggajian_KTActionPerformed(evt);
            }
        });
        penggajianTetap.add(bCari_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 213, 58, 34));

        txtNik_penggajian_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNik_penggajian_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNik_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        penggajianTetap.add(txtNik_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 213, 212, 33));

        lnama_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnama_penggajian_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnama_penggajian_KT.setText("Nama");
        penggajianTetap.add(lnama_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 266, 142, 35));

        bordernama_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordernama_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianTetap.add(bordernama_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 266, -1, -1));

        txtNama_penggajian_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNama_penggajian_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNama_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        penggajianTetap.add(txtNama_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 267, 279, 33));

        lkodejabatan_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodejabatan_penggajian_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkodejabatan_penggajian_KT.setText("Kode Jabatan");
        penggajianTetap.add(lkodejabatan_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 105, 183, 35));

        borderkodejabatan_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderkodejabatan_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianTetap.add(borderkodejabatan_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 105, -1, -1));

        cbKodejabatan_penggajian_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        cbKodejabatan_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbKodejabatan_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianTetap.add(cbKodejabatan_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(751, 103, 299, 39));

        lgajipokok_penggajian_KT1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lgajipokok_penggajian_KT1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lgajipokok_penggajian_KT1.setText("Gaji Pokok");
        penggajianTetap.add(lgajipokok_penggajian_KT1, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 159, 183, 35));

        bordergajipokok_penggajian_KT1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordergajipokok_penggajian_KT1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianTetap.add(bordergajipokok_penggajian_KT1, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 159, -1, -1));

        txtGajipokok_penggajian_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtGajipokok_penggajian_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtGajipokok_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtGajipokok_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianTetap.add(txtGajipokok_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 159, 279, 33));

        ljmlabsensi_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ljmlabsensi_penggajian_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ljmlabsensi_penggajian_KT.setText("Jumlah Absensi");
        penggajianTetap.add(ljmlabsensi_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 212, 183, 35));

        borderjmlabsensi_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderjmlabsensi_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianTetap.add(borderjmlabsensi_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 212, -1, -1));

        txtJmlabsensi_penggajian_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtJmlabsensi_penggajian_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtJmlabsensi_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtJmlabsensi_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianTetap.add(txtJmlabsensi_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 212, 279, 33));

        ltotalgaji_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltotalgaji_penggajian_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltotalgaji_penggajian_KT.setText("Total Gaji");
        penggajianTetap.add(ltotalgaji_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 266, 183, 35));

        bordertotalgaji_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordertotalgaji_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianTetap.add(bordertotalgaji_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 266, -1, -1));

        txtTotalgaji_penggajian_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtTotalgaji_penggajian_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalgaji_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTotalgaji_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianTetap.add(txtTotalgaji_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 266, 279, 33));

        garis_penggajian_KT.setBackground(new java.awt.Color(205, 205, 205));

        javax.swing.GroupLayout garis_penggajian_KTLayout = new javax.swing.GroupLayout(garis_penggajian_KT);
        garis_penggajian_KT.setLayout(garis_penggajian_KTLayout);
        garis_penggajian_KTLayout.setHorizontalGroup(
            garis_penggajian_KTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1009, Short.MAX_VALUE)
        );
        garis_penggajian_KTLayout.setVerticalGroup(
            garis_penggajian_KTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        penggajianTetap.add(garis_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 324, 1009, 3));

        bTambah_penggajian_KT.setBackground(new java.awt.Color(29, 160, 42));
        bTambah_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bTambah_penggajian_KT.setForeground(new java.awt.Color(255, 255, 255));
        bTambah_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-tambah.png"))); // NOI18N
        bTambah_penggajian_KT.setText("   Tambah");
        bTambah_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bTambah_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambah_penggajian_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambah_penggajian_KTActionPerformed(evt);
            }
        });
        penggajianTetap.add(bTambah_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 342, 172, 54));

        bUbah_penggajian_KT.setBackground(new java.awt.Color(29, 113, 160));
        bUbah_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bUbah_penggajian_KT.setForeground(new java.awt.Color(255, 255, 255));
        bUbah_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-ubah.png"))); // NOI18N
        bUbah_penggajian_KT.setText("   Ubah");
        bUbah_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bUbah_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bUbah_penggajian_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbah_penggajian_KTActionPerformed(evt);
            }
        });
        penggajianTetap.add(bUbah_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 342, 172, 54));

        bHapus_penggajian_KT.setBackground(new java.awt.Color(160, 29, 29));
        bHapus_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHapus_penggajian_KT.setForeground(new java.awt.Color(255, 255, 255));
        bHapus_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-hapus.png"))); // NOI18N
        bHapus_penggajian_KT.setText("   Hapus");
        bHapus_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHapus_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHapus_penggajian_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapus_penggajian_KTActionPerformed(evt);
            }
        });
        penggajianTetap.add(bHapus_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 342, 172, 54));

        bBatal_penggajian_KT.setBackground(new java.awt.Color(204, 204, 204));
        bBatal_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bBatal_penggajian_KT.setForeground(new java.awt.Color(96, 96, 96));
        bBatal_penggajian_KT.setText("Batal");
        bBatal_penggajian_KT.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 96, 96), 3, true));
        bBatal_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bBatal_penggajian_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatal_penggajian_KTActionPerformed(evt);
            }
        });
        penggajianTetap.add(bBatal_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 345, 172, 49));

        bHitunggaji_penggajian_KT.setBackground(new java.awt.Color(153, 0, 0));
        bHitunggaji_penggajian_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHitunggaji_penggajian_KT.setForeground(new java.awt.Color(255, 255, 255));
        bHitunggaji_penggajian_KT.setText("Hitung Gaji");
        bHitunggaji_penggajian_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHitunggaji_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHitunggaji_penggajian_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHitunggaji_penggajian_KTActionPerformed(evt);
            }
        });
        penggajianTetap.add(bHitunggaji_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(862, 342, 186, 54));

        bSearch_penggajian_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSearch_penggajian_KT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bSearch_penggajian_KTMouseClicked(evt);
            }
        });
        penggajianTetap.add(bSearch_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 430, 45, 50));

        bordersearch_penggajian_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        penggajianTetap.add(bordersearch_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 430, -1, -1));

        txtSearch_penggajian_KT.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch_penggajian_KT.setBorder(null);
        txtSearch_penggajian_KT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearch_penggajian_KTKeyTyped(evt);
            }
        });
        penggajianTetap.add(txtSearch_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 432, 270, 47));

        tbPenggajianKT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tbPenggajianKT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPenggajianKTMouseClicked(evt);
            }
        });
        jScrollPane1_penggajian_KT.setViewportView(tbPenggajianKT);

        penggajianTetap.add(jScrollPane1_penggajian_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 494, 1057, 125));

        bCetak_PKT.setBackground(new java.awt.Color(203, 115, 12));
        bCetak_PKT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bCetak_PKT.setForeground(new java.awt.Color(255, 255, 255));
        bCetak_PKT.setText("Cetak Laporan");
        bCetak_PKT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCetak_PKT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCetak_PKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetak_PKTActionPerformed(evt);
            }
        });
        penggajianTetap.add(bCetak_PKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 632, 259, 54));

        conPenggajian.add(penggajianTetap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        penggajianKontrak.setBackground(new java.awt.Color(255, 255, 255));
        penggajianKontrak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                penggajianKontrakKeyTyped(evt);
            }
        });
        penggajianKontrak.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lkodeslip_penggajian_KT1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodeslip_penggajian_KT1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkodeslip_penggajian_KT1.setText("Kode Slip");
        penggajianKontrak.add(lkodeslip_penggajian_KT1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 105, 142, 35));

        borderkodeslip_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderkodeslip_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianKontrak.add(borderkodeslip_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 105, -1, -1));

        txtKodeslip_penggajian_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtKodeslip_penggajian_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtKodeslip_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtKodeslip_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianKontrak.add(txtKodeslip_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 106, 279, 33));

        ltglslip_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltglslip_penggajian_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltglslip_penggajian_KK.setText("Tanggal Slip");
        penggajianKontrak.add(ltglslip_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 159, 142, 35));

        bordertglslip_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordertglslip_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianKontrak.add(bordertglslip_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 159, -1, -1));

        cbTglslip_penggajian_KK.setBackground(new java.awt.Color(255, 255, 255));
        penggajianKontrak.add(cbTglslip_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 160, 291, 33));

        lnik_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnik_penggajian_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnik_penggajian_KK.setText("NIK");
        penggajianKontrak.add(lnik_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 212, 142, 35));

        bordernik_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-228x35.png"))); // NOI18N
        bordernik_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianKontrak.add(bordernik_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 212, -1, -1));

        bCari_penggajian_KK.setBackground(new java.awt.Color(153, 0, 0));
        bCari_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        bCari_penggajian_KK.setForeground(new java.awt.Color(255, 255, 255));
        bCari_penggajian_KK.setText("Cari");
        bCari_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCari_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCari_penggajian_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCari_penggajian_KKActionPerformed(evt);
            }
        });
        penggajianKontrak.add(bCari_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 213, 58, 34));

        txtNik_penggajian_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNik_penggajian_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNik_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        penggajianKontrak.add(txtNik_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 213, 212, 33));

        lnama_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnama_penggajian_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnama_penggajian_KK.setText("Nama");
        penggajianKontrak.add(lnama_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 266, 142, 35));

        bordernama_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordernama_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianKontrak.add(bordernama_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 266, -1, -1));

        txtNama_penggajian_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNama_penggajian_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNama_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        penggajianKontrak.add(txtNama_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 267, 279, 33));

        lkodejabatan_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodejabatan_penggajian_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkodejabatan_penggajian_KK.setText("Kode Jabatan");
        penggajianKontrak.add(lkodejabatan_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 105, 183, 35));

        borderkodejabatan_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderkodejabatan_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianKontrak.add(borderkodejabatan_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 105, -1, -1));

        cbKodejabatan_penggajian_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        cbKodejabatan_penggajian_KK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Manager", "Driver" }));
        cbKodejabatan_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbKodejabatan_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianKontrak.add(cbKodejabatan_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(751, 103, 299, 39));

        lgajipokok_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lgajipokok_penggajian_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lgajipokok_penggajian_KK.setText("Gaji Pokok");
        penggajianKontrak.add(lgajipokok_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 159, 183, 35));

        bordergajipokok_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordergajipokok_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianKontrak.add(bordergajipokok_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 159, -1, -1));

        txtGajipokok_penggajian_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtGajipokok_penggajian_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtGajipokok_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtGajipokok_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianKontrak.add(txtGajipokok_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 159, 279, 33));

        ljmlabsensi_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ljmlabsensi_penggajian_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ljmlabsensi_penggajian_KK.setText("Jumlah Absensi");
        penggajianKontrak.add(ljmlabsensi_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 212, 183, 35));

        borderjmlabsensi_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderjmlabsensi_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianKontrak.add(borderjmlabsensi_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 212, -1, -1));

        txtJmlabsensi_penggajian_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtJmlabsensi_penggajian_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtJmlabsensi_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtJmlabsensi_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianKontrak.add(txtJmlabsensi_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 212, 279, 33));

        ltotalgaji_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltotalgaji_penggajian_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltotalgaji_penggajian_KK.setText("Total Gaji");
        penggajianKontrak.add(ltotalgaji_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 266, 183, 35));

        bordertotalgaji_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordertotalgaji_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianKontrak.add(bordertotalgaji_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 266, -1, -1));

        txtTotalgaji_penggajian_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtTotalgaji_penggajian_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalgaji_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTotalgaji_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianKontrak.add(txtTotalgaji_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 266, 279, 33));

        garis_penggajian_KK.setBackground(new java.awt.Color(205, 205, 205));

        javax.swing.GroupLayout garis_penggajian_KKLayout = new javax.swing.GroupLayout(garis_penggajian_KK);
        garis_penggajian_KK.setLayout(garis_penggajian_KKLayout);
        garis_penggajian_KKLayout.setHorizontalGroup(
            garis_penggajian_KKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1009, Short.MAX_VALUE)
        );
        garis_penggajian_KKLayout.setVerticalGroup(
            garis_penggajian_KKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        penggajianKontrak.add(garis_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 324, 1009, 3));

        bTambah_penggajian_KK.setBackground(new java.awt.Color(29, 160, 42));
        bTambah_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bTambah_penggajian_KK.setForeground(new java.awt.Color(255, 255, 255));
        bTambah_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-tambah.png"))); // NOI18N
        bTambah_penggajian_KK.setText("   Tambah");
        bTambah_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bTambah_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambah_penggajian_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambah_penggajian_KKActionPerformed(evt);
            }
        });
        penggajianKontrak.add(bTambah_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 342, 172, 54));

        bUbah_penggajian_KK.setBackground(new java.awt.Color(29, 113, 160));
        bUbah_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bUbah_penggajian_KK.setForeground(new java.awt.Color(255, 255, 255));
        bUbah_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-ubah.png"))); // NOI18N
        bUbah_penggajian_KK.setText("   Ubah");
        bUbah_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bUbah_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bUbah_penggajian_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbah_penggajian_KKActionPerformed(evt);
            }
        });
        penggajianKontrak.add(bUbah_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 342, 172, 54));

        bHapus_penggajian_KK.setBackground(new java.awt.Color(160, 29, 29));
        bHapus_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHapus_penggajian_KK.setForeground(new java.awt.Color(255, 255, 255));
        bHapus_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-hapus.png"))); // NOI18N
        bHapus_penggajian_KK.setText("   Hapus");
        bHapus_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHapus_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHapus_penggajian_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapus_penggajian_KKActionPerformed(evt);
            }
        });
        penggajianKontrak.add(bHapus_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 342, 172, 54));

        bBatal_penggajian_KK.setBackground(new java.awt.Color(204, 204, 204));
        bBatal_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bBatal_penggajian_KK.setForeground(new java.awt.Color(96, 96, 96));
        bBatal_penggajian_KK.setText("Batal");
        bBatal_penggajian_KK.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 96, 96), 3, true));
        bBatal_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bBatal_penggajian_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatal_penggajian_KKActionPerformed(evt);
            }
        });
        penggajianKontrak.add(bBatal_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 345, 172, 49));

        bHitunggaji_penggajian_KK.setBackground(new java.awt.Color(153, 0, 0));
        bHitunggaji_penggajian_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHitunggaji_penggajian_KK.setForeground(new java.awt.Color(255, 255, 255));
        bHitunggaji_penggajian_KK.setText("Hitung Gaji");
        bHitunggaji_penggajian_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHitunggaji_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHitunggaji_penggajian_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHitunggaji_penggajian_KKActionPerformed(evt);
            }
        });
        penggajianKontrak.add(bHitunggaji_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(862, 342, 186, 54));

        bSearch_penggajian_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSearch_penggajian_KK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bSearch_penggajian_KKMouseClicked(evt);
            }
        });
        penggajianKontrak.add(bSearch_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 430, 45, 50));

        bordersearch_penggajian_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        penggajianKontrak.add(bordersearch_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 430, -1, -1));

        txtSearch_penggajian_KK.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch_penggajian_KK.setBorder(null);
        txtSearch_penggajian_KK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearch_penggajian_KKKeyTyped(evt);
            }
        });
        penggajianKontrak.add(txtSearch_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 432, 270, 47));

        tbPenggajianKK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tbPenggajianKK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPenggajianKKMouseClicked(evt);
            }
        });
        jScrollPane1_penggajian_KK.setViewportView(tbPenggajianKK);

        penggajianKontrak.add(jScrollPane1_penggajian_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 494, 1057, 125));

        bCetak_PKK.setBackground(new java.awt.Color(203, 115, 12));
        bCetak_PKK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bCetak_PKK.setForeground(new java.awt.Color(255, 255, 255));
        bCetak_PKK.setText("Cetak Laporan");
        bCetak_PKK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCetak_PKK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCetak_PKK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetak_PKKActionPerformed(evt);
            }
        });
        penggajianKontrak.add(bCetak_PKK, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 632, 259, 54));

        conPenggajian.add(penggajianKontrak, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        penggajianMagang.setBackground(new java.awt.Color(255, 255, 255));
        penggajianMagang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lkodeslip_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodeslip_penggajian_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkodeslip_penggajian_KM.setText("Kode Slip");
        penggajianMagang.add(lkodeslip_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 105, 142, 35));

        borderkodeslip_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderkodeslip_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianMagang.add(borderkodeslip_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 105, -1, -1));

        txtKodeslip_penggajian_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtKodeslip_penggajian_KM.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtKodeslip_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtKodeslip_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianMagang.add(txtKodeslip_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 106, 279, 33));

        ltglslip_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltglslip_penggajian_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltglslip_penggajian_KM.setText("Tanggal Slip");
        penggajianMagang.add(ltglslip_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 159, 142, 35));

        bordertglslip_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordertglslip_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianMagang.add(bordertglslip_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 159, -1, -1));

        cbTglslip_penggajian_KM.setBackground(new java.awt.Color(255, 255, 255));
        penggajianMagang.add(cbTglslip_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(207, 160, 291, 33));

        lnik_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnik_penggajian_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnik_penggajian_KM.setText("NIK");
        penggajianMagang.add(lnik_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 212, 142, 35));

        bordernik_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-228x35.png"))); // NOI18N
        bordernik_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianMagang.add(bordernik_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 212, -1, -1));

        bCari_penggajian_KM.setBackground(new java.awt.Color(153, 0, 0));
        bCari_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 16)); // NOI18N
        bCari_penggajian_KM.setForeground(new java.awt.Color(255, 255, 255));
        bCari_penggajian_KM.setText("Cari");
        bCari_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCari_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCari_penggajian_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCari_penggajian_KMActionPerformed(evt);
            }
        });
        penggajianMagang.add(bCari_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(442, 213, 58, 34));

        txtNik_penggajian_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNik_penggajian_KM.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNik_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        penggajianMagang.add(txtNik_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 213, 212, 33));

        lnama_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnama_penggajian_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnama_penggajian_KM.setText("Nama");
        penggajianMagang.add(lnama_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 266, 142, 35));

        bordernama_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordernama_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianMagang.add(bordernama_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(205, 266, -1, -1));

        txtNama_penggajian_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNama_penggajian_KM.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNama_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        penggajianMagang.add(txtNama_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(213, 267, 279, 33));

        lkodejabatan_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodejabatan_penggajian_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lkodejabatan_penggajian_KM.setText("Kode Jabatan");
        penggajianMagang.add(lkodejabatan_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 105, 183, 35));

        borderkodejabatan_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderkodejabatan_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianMagang.add(borderkodejabatan_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 105, -1, -1));

        cbKodejabatan_penggajian_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        cbKodejabatan_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbKodejabatan_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianMagang.add(cbKodejabatan_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(751, 103, 299, 39));

        lgajipokok_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lgajipokok_penggajian_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lgajipokok_penggajian_KM.setText("Gaji Pokok");
        penggajianMagang.add(lgajipokok_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 159, 183, 35));

        bordergajipokok_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordergajipokok_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianMagang.add(bordergajipokok_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 159, -1, -1));

        txtGajipokok_penggajian_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtGajipokok_penggajian_KM.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtGajipokok_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtGajipokok_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianMagang.add(txtGajipokok_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 159, 279, 33));

        ljmlabsensi_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ljmlabsensi_penggajian_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ljmlabsensi_penggajian_KM.setText("Jumlah Absensi");
        penggajianMagang.add(ljmlabsensi_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 212, 183, 35));

        borderjmlabsensi_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        borderjmlabsensi_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianMagang.add(borderjmlabsensi_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 212, -1, -1));

        txtJmlabsensi_penggajian_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtJmlabsensi_penggajian_KM.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtJmlabsensi_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtJmlabsensi_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianMagang.add(txtJmlabsensi_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 212, 279, 33));

        ltotalgaji_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltotalgaji_penggajian_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltotalgaji_penggajian_KM.setText("Total Gaji");
        penggajianMagang.add(ltotalgaji_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(546, 266, 183, 35));

        bordertotalgaji_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-295x35.png"))); // NOI18N
        bordertotalgaji_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        penggajianMagang.add(bordertotalgaji_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(753, 266, -1, -1));

        txtTotalgaji_penggajian_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtTotalgaji_penggajian_KM.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTotalgaji_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtTotalgaji_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        penggajianMagang.add(txtTotalgaji_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(761, 266, 279, 33));

        garis_penggajian_KM.setBackground(new java.awt.Color(205, 205, 205));

        javax.swing.GroupLayout garis_penggajian_KMLayout = new javax.swing.GroupLayout(garis_penggajian_KM);
        garis_penggajian_KM.setLayout(garis_penggajian_KMLayout);
        garis_penggajian_KMLayout.setHorizontalGroup(
            garis_penggajian_KMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1009, Short.MAX_VALUE)
        );
        garis_penggajian_KMLayout.setVerticalGroup(
            garis_penggajian_KMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        penggajianMagang.add(garis_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 324, 1009, 3));

        bTambah_penggajian_KM.setBackground(new java.awt.Color(29, 160, 42));
        bTambah_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bTambah_penggajian_KM.setForeground(new java.awt.Color(255, 255, 255));
        bTambah_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-tambah.png"))); // NOI18N
        bTambah_penggajian_KM.setText("   Tambah");
        bTambah_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bTambah_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambah_penggajian_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambah_penggajian_KMActionPerformed(evt);
            }
        });
        penggajianMagang.add(bTambah_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 342, 172, 54));

        bUbah_penggajian_KM.setBackground(new java.awt.Color(29, 113, 160));
        bUbah_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bUbah_penggajian_KM.setForeground(new java.awt.Color(255, 255, 255));
        bUbah_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-ubah.png"))); // NOI18N
        bUbah_penggajian_KM.setText("   Ubah");
        bUbah_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bUbah_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bUbah_penggajian_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbah_penggajian_KMActionPerformed(evt);
            }
        });
        penggajianMagang.add(bUbah_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 342, 172, 54));

        bHapus_penggajian_KM.setBackground(new java.awt.Color(160, 29, 29));
        bHapus_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHapus_penggajian_KM.setForeground(new java.awt.Color(255, 255, 255));
        bHapus_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-hapus.png"))); // NOI18N
        bHapus_penggajian_KM.setText("   Hapus");
        bHapus_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHapus_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHapus_penggajian_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapus_penggajian_KMActionPerformed(evt);
            }
        });
        penggajianMagang.add(bHapus_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 342, 172, 54));

        bBatal_penggajian_KM.setBackground(new java.awt.Color(204, 204, 204));
        bBatal_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bBatal_penggajian_KM.setForeground(new java.awt.Color(96, 96, 96));
        bBatal_penggajian_KM.setText("Batal");
        bBatal_penggajian_KM.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 96, 96), 3, true));
        bBatal_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bBatal_penggajian_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatal_penggajian_KMActionPerformed(evt);
            }
        });
        penggajianMagang.add(bBatal_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 345, 172, 49));

        bHitunggaji_penggajian_KM.setBackground(new java.awt.Color(153, 0, 0));
        bHitunggaji_penggajian_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHitunggaji_penggajian_KM.setForeground(new java.awt.Color(255, 255, 255));
        bHitunggaji_penggajian_KM.setText("Hitung Gaji");
        bHitunggaji_penggajian_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHitunggaji_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHitunggaji_penggajian_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHitunggaji_penggajian_KMActionPerformed(evt);
            }
        });
        penggajianMagang.add(bHitunggaji_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(862, 342, 186, 54));

        bSearch_penggajian_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSearch_penggajian_KM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bSearch_penggajian_KMMouseClicked(evt);
            }
        });
        penggajianMagang.add(bSearch_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 430, 45, 50));

        bordersearch_penggajian_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        penggajianMagang.add(bordersearch_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 430, -1, -1));

        txtSearch_penggajian_KM.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch_penggajian_KM.setBorder(null);
        txtSearch_penggajian_KM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearch_penggajian_KMKeyTyped(evt);
            }
        });
        penggajianMagang.add(txtSearch_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 432, 270, 47));

        tbPenggajianKM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tbPenggajianKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPenggajianKMMouseClicked(evt);
            }
        });
        jScrollPane1_penggajian_KM.setViewportView(tbPenggajianKM);

        penggajianMagang.add(jScrollPane1_penggajian_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 494, 1057, 125));

        bCetak_PKM.setBackground(new java.awt.Color(203, 115, 12));
        bCetak_PKM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bCetak_PKM.setForeground(new java.awt.Color(255, 255, 255));
        bCetak_PKM.setText("Cetak Laporan");
        bCetak_PKM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCetak_PKM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCetak_PKM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetak_PKMActionPerformed(evt);
            }
        });
        penggajianMagang.add(bCetak_PKM, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 632, 259, 54));

        conPenggajian.add(penggajianMagang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        panelContent.add(conPenggajian, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        conJabatan.setBackground(new java.awt.Color(255, 255, 255));
        conJabatan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lForm_User1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        lForm_User1.setForeground(new java.awt.Color(135, 33, 36));
        lForm_User1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lForm_User1.setText("Form Jabatan");
        conJabatan.add(lForm_User1, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 20, -1, -1));

        borderForm_User1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/border-contentAdmin.png"))); // NOI18N
        conJabatan.add(borderForm_User1, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 80, -1, -1));

        lTabel_Jabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        lTabel_Jabatan.setForeground(new java.awt.Color(135, 33, 36));
        lTabel_Jabatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTabel_Jabatan.setText("Tabel Jabatan");
        conJabatan.add(lTabel_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 267, -1, -1));

        lkodejabatan_Jabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lkodejabatan_Jabatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lkodejabatan_Jabatan.setText("Kode Jabatan");
        conJabatan.add(lkodejabatan_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 105, -1, 35));

        borderJabatan_Jabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-168x35.png"))); // NOI18N
        borderJabatan_Jabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conJabatan.add(borderJabatan_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(541, 105, -1, -1));

        txtJabatan.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtJabatan.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtJabatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtJabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conJabatan.add(txtJabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(549, 106, 152, 33));

        ljabatan_Jabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ljabatan_Jabatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ljabatan_Jabatan.setText("Jabatan");
        conJabatan.add(ljabatan_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(421, 105, -1, 35));

        borderKode_Jabatan_Jabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-155x35.png"))); // NOI18N
        borderKode_Jabatan_Jabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conJabatan.add(borderKode_Jabatan_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(232, 105, -1, -1));

        cbKode_Jabatan_Jabatan.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        cbKode_Jabatan_Jabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "KT - Direktur", "KT - HRD", "KT - Bussiness Manager", "KT - Operational Manager", "KT - Internal Audit", "KT - Corporate Secretary", "KT - Supervisor", "KT - Assistant Executive", " ", "KK - Admin", "KK - Receptionist", "KK - Office Boy", "KK - Office Girl", "KK - Security", " ", "KM - Back End Staff", "KM - Front End Staff", "KM - Network Staff", "KM - Aplication Staff", "KM - Quality Assurance Staff " }));
        cbKode_Jabatan_Jabatan.setBorder(null);
        cbKode_Jabatan_Jabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        conJabatan.add(cbKode_Jabatan_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 103, 159, 39));

        lgajipokok_Jabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lgajipokok_Jabatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lgajipokok_Jabatan.setText("Gaji Pokok");
        conJabatan.add(lgajipokok_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(735, 105, -1, 35));

        bordergajipokok_Jabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-168x35.png"))); // NOI18N
        bordergajipokok_Jabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conJabatan.add(bordergajipokok_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(879, 105, -1, -1));

        txtGajipokok.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtGajipokok.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtGajipokok.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtGajipokok.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conJabatan.add(txtGajipokok, new org.netbeans.lib.awtextra.AbsoluteConstraints(887, 106, 152, 33));

        garis_Jabatan.setBackground(new java.awt.Color(205, 205, 205));

        javax.swing.GroupLayout garis_JabatanLayout = new javax.swing.GroupLayout(garis_Jabatan);
        garis_Jabatan.setLayout(garis_JabatanLayout);
        garis_JabatanLayout.setHorizontalGroup(
            garis_JabatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1009, Short.MAX_VALUE)
        );
        garis_JabatanLayout.setVerticalGroup(
            garis_JabatanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        conJabatan.add(garis_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 162, 1009, 3));

        bTambah_Jabatan.setBackground(new java.awt.Color(29, 160, 42));
        bTambah_Jabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bTambah_Jabatan.setForeground(new java.awt.Color(255, 255, 255));
        bTambah_Jabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-tambah.png"))); // NOI18N
        bTambah_Jabatan.setText("   Tambah");
        bTambah_Jabatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bTambah_Jabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambah_Jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambah_JabatanActionPerformed(evt);
            }
        });
        conJabatan.add(bTambah_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 180, 172, 54));

        bUbah_Jabatan.setBackground(new java.awt.Color(29, 113, 160));
        bUbah_Jabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bUbah_Jabatan.setForeground(new java.awt.Color(255, 255, 255));
        bUbah_Jabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-ubah.png"))); // NOI18N
        bUbah_Jabatan.setText("   Ubah");
        bUbah_Jabatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bUbah_Jabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bUbah_Jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbah_JabatanActionPerformed(evt);
            }
        });
        conJabatan.add(bUbah_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 180, 172, 54));

        bHapus_Jabatan.setBackground(new java.awt.Color(160, 29, 29));
        bHapus_Jabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHapus_Jabatan.setForeground(new java.awt.Color(255, 255, 255));
        bHapus_Jabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-hapus.png"))); // NOI18N
        bHapus_Jabatan.setText("   Hapus");
        bHapus_Jabatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHapus_Jabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHapus_Jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapus_JabatanActionPerformed(evt);
            }
        });
        conJabatan.add(bHapus_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 180, 172, 54));

        bBatal_Jabatan.setBackground(new java.awt.Color(204, 204, 204));
        bBatal_Jabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bBatal_Jabatan.setForeground(new java.awt.Color(96, 96, 96));
        bBatal_Jabatan.setText("Batal");
        bBatal_Jabatan.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 96, 96), 3, true));
        bBatal_Jabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bBatal_Jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatal_JabatanActionPerformed(evt);
            }
        });
        conJabatan.add(bBatal_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 180, 418, 49));

        bSearch_Jabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSearch_Jabatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bSearch_JabatanMouseClicked(evt);
            }
        });
        conJabatan.add(bSearch_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 267, 45, 50));

        bordersearch_Jabatan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        conJabatan.add(bordersearch_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 267, -1, -1));

        txtSearch_Jabatan.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch_Jabatan.setBorder(null);
        txtSearch_Jabatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearch_JabatanKeyTyped(evt);
            }
        });
        conJabatan.add(txtSearch_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 269, 270, 47));

        tbJabatan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tbJabatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJabatanMouseClicked(evt);
            }
        });
        jScrollPane_Jabatan.setViewportView(tbJabatan);

        conJabatan.add(jScrollPane_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 330, 1057, 289));

        bCetak_Jabatan.setBackground(new java.awt.Color(203, 115, 12));
        bCetak_Jabatan.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bCetak_Jabatan.setForeground(new java.awt.Color(255, 255, 255));
        bCetak_Jabatan.setText("Cetak Laporan");
        bCetak_Jabatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCetak_Jabatan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCetak_Jabatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetak_JabatanActionPerformed(evt);
            }
        });
        conJabatan.add(bCetak_Jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 632, 259, 54));

        panelContent.add(conJabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        conKaryawan.setBackground(new java.awt.Color(255, 255, 255));
        conKaryawan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lFormKaryawan.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        lFormKaryawan.setForeground(new java.awt.Color(135, 33, 36));
        lFormKaryawan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lFormKaryawan.setText("Form Karyawan");
        conKaryawan.add(lFormKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 20, 330, -1));

        borderFormKaryawan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/border-contentkaryawan.png"))); // NOI18N
        conKaryawan.add(borderFormKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 80, -1, -1));

        lTabelKaryawan.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        lTabelKaryawan.setForeground(new java.awt.Color(135, 33, 36));
        lTabelKaryawan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTabelKaryawan.setText("Tabel Karyawan");
        conKaryawan.add(lTabelKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 431, 340, -1));

        cbKaryawan.setBackground(new java.awt.Color(255, 0, 0));
        cbKaryawan.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 18)); // NOI18N
        cbKaryawan.setForeground(new java.awt.Color(255, 255, 255));
        cbKaryawan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TETAP", "KONTRAK", "MAGANG" }));
        cbKaryawan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbKaryawan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbKaryawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKaryawanActionPerformed(evt);
            }
        });
        conKaryawan.add(cbKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, 182, 39));

        karyawanTetap.setBackground(new java.awt.Color(255, 255, 255));
        karyawanTetap.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lnik_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnik_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnik_KT.setText("NIK");
        karyawanTetap.add(lnik_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 105, 100, 35));

        bordernik_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        bordernik_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanTetap.add(bordernik_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 105, -1, -1));

        txtNik_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNik_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNik_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNik_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanTetap.add(txtNik_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 106, 178, 33));

        lnama_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnama_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnama_KT.setText("Nama");
        karyawanTetap.add(lnama_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 159, 100, 35));

        bordernama_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        bordernama_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanTetap.add(bordernama_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 159, -1, -1));

        txtNama_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNama_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNama_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        karyawanTetap.add(txtNama_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 160, 178, 33));

        lemail_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lemail_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lemail_KT.setText("Email");
        karyawanTetap.add(lemail_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 212, 100, 35));

        borderemail_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        borderemail_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanTetap.add(borderemail_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 212, -1, -1));

        txtEmail_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtEmail_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEmail_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        karyawanTetap.add(txtEmail_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 213, 178, 33));

        lnotelp_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnotelp_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnotelp_KT.setText("No. Telp");
        karyawanTetap.add(lnotelp_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 266, 100, 35));

        bordernotelp_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        bordernotelp_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanTetap.add(bordernotelp_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 266, -1, -1));

        txtNotelp_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNotelp_KT.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNotelp_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        karyawanTetap.add(txtNotelp_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 267, 178, 33));

        lalamat_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lalamat_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lalamat_KT.setText("Alamat");
        karyawanTetap.add(lalamat_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 105, 176, 35));

        borderalamat_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-156x89.png"))); // NOI18N
        borderalamat_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanTetap.add(borderalamat_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 105, -1, -1));

        jScrollPane_KT.setBorder(null);
        jScrollPane_KT.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_KT.setToolTipText("");
        jScrollPane_KT.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        txtAlamat_KT.setColumns(20);
        txtAlamat_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtAlamat_KT.setRows(5);
        jScrollPane_KT.setViewportView(txtAlamat_KT);

        karyawanTetap.add(jScrollPane_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(587, 108, 146, 83));

        ltglmsk_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltglmsk_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltglmsk_KT.setText("Tanggal Masuk");
        karyawanTetap.add(ltglmsk_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 212, 176, 35));

        bordertglmsk_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-155x35.png"))); // NOI18N
        bordertglmsk_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanTetap.add(bordertglmsk_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 212, -1, -1));

        cbTglmsk_KT.setBackground(new java.awt.Color(255, 255, 255));
        karyawanTetap.add(cbTglmsk_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(584, 213, 152, 33));

        ljabatan_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ljabatan_KT.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ljabatan_KT.setText("Jabatan");
        karyawanTetap.add(ljabatan_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(769, 105, 99, 35));

        borderjabatan_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-155x35.png"))); // NOI18N
        borderjabatan_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanTetap.add(borderjabatan_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(891, 105, -1, -1));

        cbJabatan_KT.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        cbJabatan_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbJabatan_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanTetap.add(cbJabatan_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(889, 103, 159, 39));

        garis_KT.setBackground(new java.awt.Color(205, 205, 205));

        javax.swing.GroupLayout garis_KTLayout = new javax.swing.GroupLayout(garis_KT);
        garis_KT.setLayout(garis_KTLayout);
        garis_KTLayout.setHorizontalGroup(
            garis_KTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1009, Short.MAX_VALUE)
        );
        garis_KTLayout.setVerticalGroup(
            garis_KTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        karyawanTetap.add(garis_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 324, 1009, 3));

        bTambah_KT.setBackground(new java.awt.Color(29, 160, 42));
        bTambah_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bTambah_KT.setForeground(new java.awt.Color(255, 255, 255));
        bTambah_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-tambah.png"))); // NOI18N
        bTambah_KT.setText("   Tambah");
        bTambah_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bTambah_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambah_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambah_KTActionPerformed(evt);
            }
        });
        karyawanTetap.add(bTambah_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 342, 172, 54));

        bUbah_KT.setBackground(new java.awt.Color(29, 113, 160));
        bUbah_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bUbah_KT.setForeground(new java.awt.Color(255, 255, 255));
        bUbah_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-ubah.png"))); // NOI18N
        bUbah_KT.setText("   Ubah");
        bUbah_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bUbah_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bUbah_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbah_KTActionPerformed(evt);
            }
        });
        karyawanTetap.add(bUbah_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 342, 172, 54));

        bHapus_KT.setBackground(new java.awt.Color(160, 29, 29));
        bHapus_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHapus_KT.setForeground(new java.awt.Color(255, 255, 255));
        bHapus_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-hapus.png"))); // NOI18N
        bHapus_KT.setText("   Hapus");
        bHapus_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHapus_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHapus_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapus_KTActionPerformed(evt);
            }
        });
        karyawanTetap.add(bHapus_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 342, 172, 54));

        bBatal_KT.setBackground(new java.awt.Color(204, 204, 204));
        bBatal_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bBatal_KT.setForeground(new java.awt.Color(96, 96, 96));
        bBatal_KT.setText("Batal");
        bBatal_KT.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 96, 96), 3, true));
        bBatal_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bBatal_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatal_KTActionPerformed(evt);
            }
        });
        karyawanTetap.add(bBatal_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 345, 418, 49));

        bSearch_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSearch_KT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bSearch_KTMouseClicked(evt);
            }
        });
        karyawanTetap.add(bSearch_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 430, 45, 50));

        bordersearch_KT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        karyawanTetap.add(bordersearch_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 430, -1, -1));

        txtSearch_KT.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch_KT.setBorder(null);
        txtSearch_KT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearch_KTKeyTyped(evt);
            }
        });
        karyawanTetap.add(txtSearch_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 432, 270, 47));

        tbKT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tbKT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKTMouseClicked(evt);
            }
        });
        jScrollPane1_KT.setViewportView(tbKT);

        karyawanTetap.add(jScrollPane1_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 494, 1057, 125));

        bCetak_KT.setBackground(new java.awt.Color(203, 115, 12));
        bCetak_KT.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bCetak_KT.setForeground(new java.awt.Color(255, 255, 255));
        bCetak_KT.setText("Cetak Laporan");
        bCetak_KT.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCetak_KT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCetak_KT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetak_KTActionPerformed(evt);
            }
        });
        karyawanTetap.add(bCetak_KT, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 632, 259, 54));

        conKaryawan.add(karyawanTetap, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        karyawanKontrak.setBackground(new java.awt.Color(255, 255, 255));
        karyawanKontrak.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lnik_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnik_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnik_KK.setText("NIK");
        karyawanKontrak.add(lnik_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 105, 100, 35));

        bordernik_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        bordernik_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanKontrak.add(bordernik_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 105, -1, -1));

        txtNik_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNik_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNik_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNik_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanKontrak.add(txtNik_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 106, 178, 33));

        lnama_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnama_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnama_KK.setText("Nama");
        karyawanKontrak.add(lnama_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 159, 100, 35));

        bordernama_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        bordernama_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanKontrak.add(bordernama_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 159, -1, -1));

        txtNama_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNama_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNama_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        karyawanKontrak.add(txtNama_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 160, 178, 33));

        lemail_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lemail_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lemail_KK.setText("Email");
        karyawanKontrak.add(lemail_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 212, 100, 35));

        borderemail_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        borderemail_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanKontrak.add(borderemail_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 212, -1, -1));

        txtEmail_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtEmail_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEmail_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        karyawanKontrak.add(txtEmail_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 213, 178, 33));

        lnotelp_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnotelp_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnotelp_KK.setText("No. Telp");
        karyawanKontrak.add(lnotelp_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 266, 100, 35));

        bordernotelp_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        bordernotelp_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanKontrak.add(bordernotelp_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 266, -1, -1));

        txtNotelp_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNotelp_KK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNotelp_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        karyawanKontrak.add(txtNotelp_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 267, 178, 33));

        lalamat_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lalamat_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lalamat_KK.setText("Alamat");
        karyawanKontrak.add(lalamat_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 105, 176, 35));

        borderalamat_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-156x89.png"))); // NOI18N
        borderalamat_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanKontrak.add(borderalamat_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 105, -1, -1));

        jScrollPane_KK.setBorder(null);
        jScrollPane_KK.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_KK.setToolTipText("");
        jScrollPane_KK.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        txtAlamat_KK.setColumns(20);
        txtAlamat_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtAlamat_KK.setRows(5);
        jScrollPane_KK.setViewportView(txtAlamat_KK);

        karyawanKontrak.add(jScrollPane_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(587, 108, 146, 83));

        ltglmsk_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltglmsk_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltglmsk_KK.setText("Tanggal Masuk");
        karyawanKontrak.add(ltglmsk_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 212, 176, 35));

        bordertglmsk_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-155x35.png"))); // NOI18N
        bordertglmsk_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanKontrak.add(bordertglmsk_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 212, -1, -1));

        cbTglmsk_KK.setBackground(new java.awt.Color(255, 255, 255));
        karyawanKontrak.add(cbTglmsk_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(584, 213, 152, 33));

        ltglklr_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltglklr_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltglklr_KK.setText("Tanggal Selesai");
        karyawanKontrak.add(ltglklr_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 265, 180, 35));

        bordertglklr_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-155x35.png"))); // NOI18N
        bordertglklr_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanKontrak.add(bordertglklr_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 265, -1, -1));

        cbTglklr_KK.setBackground(new java.awt.Color(255, 255, 255));
        karyawanKontrak.add(cbTglklr_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(584, 266, 152, 33));

        ljabatan_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ljabatan_KK.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ljabatan_KK.setText("Jabatan");
        karyawanKontrak.add(ljabatan_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(769, 105, 99, 35));

        borderjabatan_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-155x35.png"))); // NOI18N
        borderjabatan_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanKontrak.add(borderjabatan_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(891, 105, -1, -1));

        cbJabatan_KK.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        cbJabatan_KK.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Manager", "Driver" }));
        cbJabatan_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbJabatan_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanKontrak.add(cbJabatan_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(889, 103, 159, 39));

        garis_KK.setBackground(new java.awt.Color(205, 205, 205));

        javax.swing.GroupLayout garis_KKLayout = new javax.swing.GroupLayout(garis_KK);
        garis_KK.setLayout(garis_KKLayout);
        garis_KKLayout.setHorizontalGroup(
            garis_KKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1009, Short.MAX_VALUE)
        );
        garis_KKLayout.setVerticalGroup(
            garis_KKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        karyawanKontrak.add(garis_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 324, 1009, 3));

        bTambah_KK.setBackground(new java.awt.Color(29, 160, 42));
        bTambah_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bTambah_KK.setForeground(new java.awt.Color(255, 255, 255));
        bTambah_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-tambah.png"))); // NOI18N
        bTambah_KK.setText("   Tambah");
        bTambah_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bTambah_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambah_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambah_KKActionPerformed(evt);
            }
        });
        karyawanKontrak.add(bTambah_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 342, 172, 54));

        bUbah_KK.setBackground(new java.awt.Color(29, 113, 160));
        bUbah_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bUbah_KK.setForeground(new java.awt.Color(255, 255, 255));
        bUbah_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-ubah.png"))); // NOI18N
        bUbah_KK.setText("   Ubah");
        bUbah_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bUbah_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bUbah_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbah_KKActionPerformed(evt);
            }
        });
        karyawanKontrak.add(bUbah_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 342, 172, 54));

        bHapus_KK.setBackground(new java.awt.Color(160, 29, 29));
        bHapus_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHapus_KK.setForeground(new java.awt.Color(255, 255, 255));
        bHapus_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-hapus.png"))); // NOI18N
        bHapus_KK.setText("   Hapus");
        bHapus_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHapus_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHapus_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapus_KKActionPerformed(evt);
            }
        });
        karyawanKontrak.add(bHapus_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 342, 172, 54));

        bBatal_KK.setBackground(new java.awt.Color(204, 204, 204));
        bBatal_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bBatal_KK.setForeground(new java.awt.Color(96, 96, 96));
        bBatal_KK.setText("Batal");
        bBatal_KK.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 96, 96), 3, true));
        bBatal_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bBatal_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatal_KKActionPerformed(evt);
            }
        });
        karyawanKontrak.add(bBatal_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 345, 418, 49));

        bSearch_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSearch_KK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bSearch_KKMouseClicked(evt);
            }
        });
        karyawanKontrak.add(bSearch_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 430, 45, 50));

        bordersearch_KK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        karyawanKontrak.add(bordersearch_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 430, -1, -1));

        txtSearch_KK.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch_KK.setBorder(null);
        txtSearch_KK.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearch_KKKeyTyped(evt);
            }
        });
        karyawanKontrak.add(txtSearch_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 432, 270, 47));

        tbKK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tbKK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKKMouseClicked(evt);
            }
        });
        jScrollPane1_KK.setViewportView(tbKK);

        karyawanKontrak.add(jScrollPane1_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 494, 1057, 125));

        bCetak_KK.setBackground(new java.awt.Color(203, 115, 12));
        bCetak_KK.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bCetak_KK.setForeground(new java.awt.Color(255, 255, 255));
        bCetak_KK.setText("Cetak Laporan");
        bCetak_KK.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCetak_KK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCetak_KK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetak_KKActionPerformed(evt);
            }
        });
        karyawanKontrak.add(bCetak_KK, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 632, 259, 54));

        conKaryawan.add(karyawanKontrak, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        karyawanMagang.setBackground(new java.awt.Color(255, 255, 255));
        karyawanMagang.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lnik_KK1.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnik_KK1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnik_KK1.setText("NIK");
        karyawanMagang.add(lnik_KK1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 105, 100, 35));

        bordernik_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        bordernik_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanMagang.add(bordernik_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 105, -1, -1));

        txtNik_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNik_KM.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNik_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNik_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanMagang.add(txtNik_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 106, 178, 33));

        lnama_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnama_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnama_KM.setText("Nama");
        karyawanMagang.add(lnama_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 159, 100, 35));

        bordernama_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        bordernama_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanMagang.add(bordernama_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 159, -1, -1));

        txtNama_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNama_KM.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNama_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        karyawanMagang.add(txtNama_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 160, 178, 33));

        lemail_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lemail_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lemail_KM.setText("Email");
        karyawanMagang.add(lemail_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 212, 100, 35));

        borderemail_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        borderemail_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanMagang.add(borderemail_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 212, -1, -1));

        txtEmail_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtEmail_KM.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEmail_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        karyawanMagang.add(txtEmail_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 213, 178, 33));

        lnotelp_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnotelp_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnotelp_KM.setText("No. Telp");
        karyawanMagang.add(lnotelp_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 266, 100, 35));

        bordernotelp_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-195x35.png"))); // NOI18N
        bordernotelp_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanMagang.add(bordernotelp_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(157, 266, -1, -1));

        txtNotelp_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNotelp_KM.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNotelp_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        karyawanMagang.add(txtNotelp_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 267, 178, 33));

        lalamat_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lalamat_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lalamat_KM.setText("Alamat");
        karyawanMagang.add(lalamat_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 105, 176, 35));

        borderalamat_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-156x89.png"))); // NOI18N
        borderalamat_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        karyawanMagang.add(borderalamat_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 105, -1, -1));

        jScrollPane_KM.setBorder(null);
        jScrollPane_KM.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_KM.setToolTipText("");
        jScrollPane_KM.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        txtAlamat_KM.setColumns(20);
        txtAlamat_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtAlamat_KM.setRows(5);
        jScrollPane_KM.setViewportView(txtAlamat_KM);

        karyawanMagang.add(jScrollPane_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(587, 108, 146, 83));

        ltglmsk_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltglmsk_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltglmsk_KM.setText("Tanggal Masuk");
        karyawanMagang.add(ltglmsk_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(382, 212, 176, 35));

        bordertglmsk_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-155x35.png"))); // NOI18N
        bordertglmsk_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanMagang.add(bordertglmsk_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 212, -1, -1));

        cbTglmsk_KM.setBackground(new java.awt.Color(255, 255, 255));
        karyawanMagang.add(cbTglmsk_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(584, 213, 152, 33));

        ltglklr_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ltglklr_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ltglklr_KM.setText("Tanggal Selesai");
        karyawanMagang.add(ltglklr_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(378, 265, 180, 35));

        bordertglklr_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-155x35.png"))); // NOI18N
        bordertglklr_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanMagang.add(bordertglklr_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(582, 265, -1, -1));

        cbTglklr_KM.setBackground(new java.awt.Color(255, 255, 255));
        karyawanMagang.add(cbTglklr_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(584, 266, 152, 33));

        ljabatan_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        ljabatan_KM.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ljabatan_KM.setText("Jabatan");
        karyawanMagang.add(ljabatan_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(769, 105, 99, 35));

        borderjabatan_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-155x35.png"))); // NOI18N
        borderjabatan_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanMagang.add(borderjabatan_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(891, 105, -1, -1));

        cbJabatan_KM.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        cbJabatan_KM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Manager", "Driver" }));
        cbJabatan_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbJabatan_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        karyawanMagang.add(cbJabatan_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(889, 103, 159, 39));

        garis_KM.setBackground(new java.awt.Color(205, 205, 205));

        javax.swing.GroupLayout garis_KMLayout = new javax.swing.GroupLayout(garis_KM);
        garis_KM.setLayout(garis_KMLayout);
        garis_KMLayout.setHorizontalGroup(
            garis_KMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1009, Short.MAX_VALUE)
        );
        garis_KMLayout.setVerticalGroup(
            garis_KMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        karyawanMagang.add(garis_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 324, 1009, 3));

        bTambah_KM.setBackground(new java.awt.Color(29, 160, 42));
        bTambah_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bTambah_KM.setForeground(new java.awt.Color(255, 255, 255));
        bTambah_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-tambah.png"))); // NOI18N
        bTambah_KM.setText("   Tambah");
        bTambah_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bTambah_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambah_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambah_KMActionPerformed(evt);
            }
        });
        karyawanMagang.add(bTambah_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 342, 172, 54));

        bUbah_KM.setBackground(new java.awt.Color(29, 113, 160));
        bUbah_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bUbah_KM.setForeground(new java.awt.Color(255, 255, 255));
        bUbah_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-ubah.png"))); // NOI18N
        bUbah_KM.setText("   Ubah");
        bUbah_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bUbah_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bUbah_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bUbah_KMActionPerformed(evt);
            }
        });
        karyawanMagang.add(bUbah_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(236, 342, 172, 54));

        bHapus_KM.setBackground(new java.awt.Color(160, 29, 29));
        bHapus_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bHapus_KM.setForeground(new java.awt.Color(255, 255, 255));
        bHapus_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-hapus.png"))); // NOI18N
        bHapus_KM.setText("   Hapus");
        bHapus_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bHapus_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bHapus_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bHapus_KMActionPerformed(evt);
            }
        });
        karyawanMagang.add(bHapus_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(433, 342, 172, 54));

        bBatal_KM.setBackground(new java.awt.Color(204, 204, 204));
        bBatal_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bBatal_KM.setForeground(new java.awt.Color(96, 96, 96));
        bBatal_KM.setText("Batal");
        bBatal_KM.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 96, 96), 3, true));
        bBatal_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bBatal_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatal_KMActionPerformed(evt);
            }
        });
        karyawanMagang.add(bBatal_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 345, 418, 49));

        bSearch_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSearch_KM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bSearch_KMMouseClicked(evt);
            }
        });
        karyawanMagang.add(bSearch_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 430, 45, 50));

        bordersearch_KM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        karyawanMagang.add(bordersearch_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 430, -1, -1));

        txtSearch_KM.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch_KM.setBorder(null);
        txtSearch_KM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearch_KMKeyTyped(evt);
            }
        });
        karyawanMagang.add(txtSearch_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 432, 270, 47));

        tbKM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tbKM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKMMouseClicked(evt);
            }
        });
        jScrollPane1_KM.setViewportView(tbKM);

        karyawanMagang.add(jScrollPane1_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 494, 1057, 125));

        bCetak_KM.setBackground(new java.awt.Color(203, 115, 12));
        bCetak_KM.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bCetak_KM.setForeground(new java.awt.Color(255, 255, 255));
        bCetak_KM.setText("Cetak Laporan");
        bCetak_KM.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bCetak_KM.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bCetak_KM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bCetak_KMActionPerformed(evt);
            }
        });
        karyawanMagang.add(bCetak_KM, new org.netbeans.lib.awtextra.AbsoluteConstraints(814, 632, 259, 54));

        conKaryawan.add(karyawanMagang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        panelContent.add(conKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        conAdmin.setBackground(new java.awt.Color(255, 255, 255));
        conAdmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lForm_Admin.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        lForm_Admin.setForeground(new java.awt.Color(135, 33, 36));
        lForm_Admin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lForm_Admin.setText("Form Admin");
        conAdmin.add(lForm_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 20, 250, -1));

        borderForm_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/border-contentkaryawan.png"))); // NOI18N
        conAdmin.add(borderForm_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 80, -1, -1));

        lTabel_Admin.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        lTabel_Admin.setForeground(new java.awt.Color(135, 33, 36));
        lTabel_Admin.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTabel_Admin.setText("Tabel Admin");
        conAdmin.add(lTabel_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 431, -1, -1));

        lusername_Admin.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lusername_Admin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lusername_Admin.setText("Username");
        conAdmin.add(lusername_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 105, 119, 35));

        borderusername_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-331x35.png"))); // NOI18N
        borderusername_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(borderusername_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 105, -1, -1));

        txtUsername_Admin.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtUsername_Admin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtUsername_Admin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtUsername_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(txtUsername_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 106, 315, 33));

        lnama_Admin.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnama_Admin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnama_Admin.setText("Nama");
        conAdmin.add(lnama_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 159, 119, 35));

        bordernama_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-331x35.png"))); // NOI18N
        bordernama_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(bordernama_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 159, -1, -1));

        txtNama_Admin.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNama_Admin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNama_Admin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNama_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(txtNama_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 315, 33));

        lemail_Admin.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lemail_Admin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lemail_Admin.setText("Email");
        conAdmin.add(lemail_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 213, 119, 35));

        borderemail_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-331x35.png"))); // NOI18N
        borderemail_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(borderemail_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 213, -1, -1));

        txtEmail_Admin.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtEmail_Admin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtEmail_Admin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtEmail_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(txtEmail_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 214, 315, 33));

        lnotelp_Admin.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lnotelp_Admin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lnotelp_Admin.setText("No. Telp");
        conAdmin.add(lnotelp_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 267, 119, 35));

        bordernotelp_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-331x35.png"))); // NOI18N
        bordernotelp_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(bordernotelp_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(182, 267, -1, -1));

        txtNotelp_Admin.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNotelp_Admin.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNotelp_Admin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNotelp_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(txtNotelp_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 268, 315, 33));

        lalamat_Admin.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lalamat_Admin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lalamat_Admin.setText("Alamat");
        conAdmin.add(lalamat_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(576, 105, 117, 35));

        borderalamat_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-331x89.png"))); // NOI18N
        borderalamat_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(borderalamat_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(717, 105, -1, -1));

        jScrollPane_Alamat.setBorder(null);
        jScrollPane_Alamat.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane_Alamat.setToolTipText("");
        jScrollPane_Alamat.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane_Alamat.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N

        txtAlamat_Admin.setColumns(20);
        txtAlamat_Admin.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtAlamat_Admin.setRows(5);
        jScrollPane_Alamat.setViewportView(txtAlamat_Admin);

        conAdmin.add(jScrollPane_Alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(722, 108, 321, 83));

        lpassword_Admin.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 23)); // NOI18N
        lpassword_Admin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lpassword_Admin.setText("Password");
        conAdmin.add(lpassword_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(576, 213, 117, 35));

        borderpassword_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/outline-331x35.png"))); // NOI18N
        borderpassword_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(borderpassword_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(717, 213, -1, -1));

        txtPassword_Admin.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtPassword_Admin.setToolTipText("");
        txtPassword_Admin.setBorder(null);
        txtPassword_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        conAdmin.add(txtPassword_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(725, 214, 315, 33));

        garis_Admin.setBackground(new java.awt.Color(205, 205, 205));

        javax.swing.GroupLayout garis_AdminLayout = new javax.swing.GroupLayout(garis_Admin);
        garis_Admin.setLayout(garis_AdminLayout);
        garis_AdminLayout.setHorizontalGroup(
            garis_AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1009, Short.MAX_VALUE)
        );
        garis_AdminLayout.setVerticalGroup(
            garis_AdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        conAdmin.add(garis_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 324, 1009, 3));

        bTambah_Admin.setBackground(new java.awt.Color(29, 160, 42));
        bTambah_Admin.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bTambah_Admin.setForeground(new java.awt.Color(255, 255, 255));
        bTambah_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-tambah.png"))); // NOI18N
        bTambah_Admin.setText("   Tambah");
        bTambah_Admin.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        bTambah_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bTambah_Admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bTambah_AdminActionPerformed(evt);
            }
        });
        conAdmin.add(bTambah_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(39, 342, 492, 54));

        bBatal_Admin.setBackground(new java.awt.Color(204, 204, 204));
        bBatal_Admin.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 20)); // NOI18N
        bBatal_Admin.setForeground(new java.awt.Color(96, 96, 96));
        bBatal_Admin.setText("Batal");
        bBatal_Admin.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(96, 96, 96), 3, true));
        bBatal_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bBatal_Admin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBatal_AdminActionPerformed(evt);
            }
        });
        conAdmin.add(bBatal_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(555, 345, 492, 49));

        bSearch_Admin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bSearch_Admin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bSearch_AdminMouseClicked(evt);
            }
        });
        conAdmin.add(bSearch_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(1026, 430, 45, 50));

        bordersearch_Admin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        conAdmin.add(bordersearch_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(742, 430, -1, -1));

        txtSearch_Admin.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch_Admin.setBorder(null);
        txtSearch_Admin.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSearch_AdminKeyTyped(evt);
            }
        });
        conAdmin.add(txtSearch_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 432, 270, 47));

        tbAdmin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9"
            }
        ));
        tbAdmin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbAdminMouseClicked(evt);
            }
        });
        jScrollPane_Admin.setViewportView(tbAdmin);

        conAdmin.add(jScrollPane_Admin, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 494, 1057, 193));

        panelContent.add(conAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        conDashboard.setBackground(new java.awt.Color(255, 255, 255));
        conDashboard.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lDashboard.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        lDashboard.setForeground(new java.awt.Color(135, 33, 36));
        lDashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lDashboard.setText("Dashboard");
        conDashboard.add(lDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(38, 20, -1, -1));

        iconKT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 39)); // NOI18N
        iconKT.setForeground(new java.awt.Color(255, 255, 255));
        iconKT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        iconKT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/karyawan tetap.png"))); // NOI18N
        conDashboard.add(iconKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 131, -1, -1));

        lJumlahKaryawanTetap.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        lJumlahKaryawanTetap.setForeground(new java.awt.Color(255, 255, 255));
        lJumlahKaryawanTetap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lJumlahKaryawanTetap.setText("Jumlah Karyawan Tetap");
        conDashboard.add(lJumlahKaryawanTetap, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 125, -1, 35));

        lvalueKT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        lvalueKT.setForeground(new java.awt.Color(255, 255, 255));
        lvalueKT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lvalueKT.setText("3000");
        conDashboard.add(lvalueKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 182, 150, 35));

        satu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/content-dashboard.png"))); // NOI18N
        conDashboard.add(satu, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 97, -1, -1));

        iconGKT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 39)); // NOI18N
        iconGKT.setForeground(new java.awt.Color(255, 255, 255));
        iconGKT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        iconGKT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/gaji karyawan tetap.png"))); // NOI18N
        conDashboard.add(iconGKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(615, 131, -1, -1));

        lJumlahGajiKaryawanTetap.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 20)); // NOI18N
        lJumlahGajiKaryawanTetap.setForeground(new java.awt.Color(255, 255, 255));
        lJumlahGajiKaryawanTetap.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lJumlahGajiKaryawanTetap.setText("Jumlah Gaji Karyawan Tetap");
        conDashboard.add(lJumlahGajiKaryawanTetap, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 114, -1, 35));

        lTelahDibayar.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 20)); // NOI18N
        lTelahDibayar.setForeground(new java.awt.Color(255, 255, 255));
        lTelahDibayar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTelahDibayar.setText("Telah Dibayar");
        conDashboard.add(lTelahDibayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 139, -1, 35));

        lvalueGKT.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        lvalueGKT.setForeground(new java.awt.Color(255, 255, 255));
        lvalueGKT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lvalueGKT.setText("Rp. 1.200.000.000");
        conDashboard.add(lvalueGKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 191, 320, 35));

        dua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/content-dashboard.png"))); // NOI18N
        conDashboard.add(dua, new org.netbeans.lib.awtextra.AbsoluteConstraints(578, 97, -1, -1));

        iconKK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 39)); // NOI18N
        iconKK.setForeground(new java.awt.Color(255, 255, 255));
        iconKK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        iconKK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/karyawan kontrak.png"))); // NOI18N
        conDashboard.add(iconKK, new org.netbeans.lib.awtextra.AbsoluteConstraints(55, 322, -1, -1));

        lJumlahKaryawanKontrak.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        lJumlahKaryawanKontrak.setForeground(new java.awt.Color(255, 255, 255));
        lJumlahKaryawanKontrak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lJumlahKaryawanKontrak.setText("Jumlah Karyawan Kontrak");
        conDashboard.add(lJumlahKaryawanKontrak, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 316, -1, 35));

        lvalueKK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        lvalueKK.setForeground(new java.awt.Color(255, 255, 255));
        lvalueKK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lvalueKK.setText("3000");
        conDashboard.add(lvalueKK, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 374, 150, 35));

        tiga.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/content-dashboard.png"))); // NOI18N
        conDashboard.add(tiga, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 288, -1, -1));

        iconGKK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 39)); // NOI18N
        iconGKK.setForeground(new java.awt.Color(255, 255, 255));
        iconGKK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        iconGKK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/gaji karyawan kontrak.png"))); // NOI18N
        conDashboard.add(iconGKK, new org.netbeans.lib.awtextra.AbsoluteConstraints(596, 321, -1, -1));

        lJumlahGajiKaryawanKontrak.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 20)); // NOI18N
        lJumlahGajiKaryawanKontrak.setForeground(new java.awt.Color(255, 255, 255));
        lJumlahGajiKaryawanKontrak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lJumlahGajiKaryawanKontrak.setText("Jumlah Gaji Karyawan Kontrak");
        conDashboard.add(lJumlahGajiKaryawanKontrak, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 304, -1, 35));

        lTelahDibayarKontrak.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 20)); // NOI18N
        lTelahDibayarKontrak.setForeground(new java.awt.Color(255, 255, 255));
        lTelahDibayarKontrak.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTelahDibayarKontrak.setText("Telah Dibayar");
        conDashboard.add(lTelahDibayarKontrak, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 329, -1, 35));

        lvalueGKK.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        lvalueGKK.setForeground(new java.awt.Color(255, 255, 255));
        lvalueGKK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lvalueGKK.setText("Rp. 1.200.000.000");
        conDashboard.add(lvalueGKK, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 382, 320, 35));
        lvalueGKK.getAccessibleContext().setAccessibleDescription("");

        empat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/content-dashboard.png"))); // NOI18N
        conDashboard.add(empat, new org.netbeans.lib.awtextra.AbsoluteConstraints(578, 288, -1, -1));

        iconKM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 39)); // NOI18N
        iconKM.setForeground(new java.awt.Color(255, 255, 255));
        iconKM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        iconKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/karyawan magang.png"))); // NOI18N
        conDashboard.add(iconKM, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 513, -1, -1));

        lJumlahKaryawanMagang.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 24)); // NOI18N
        lJumlahKaryawanMagang.setForeground(new java.awt.Color(255, 255, 255));
        lJumlahKaryawanMagang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lJumlahKaryawanMagang.setText("Jumlah Karyawan Magang");
        conDashboard.add(lJumlahKaryawanMagang, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 507, -1, 35));

        lvalueKM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        lvalueKM.setForeground(new java.awt.Color(255, 255, 255));
        lvalueKM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lvalueKM.setText("3000");
        conDashboard.add(lvalueKM, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 565, 150, 35));

        lima.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/content-dashboard.png"))); // NOI18N
        conDashboard.add(lima, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 479, -1, -1));

        iconGKM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 39)); // NOI18N
        iconGKM.setForeground(new java.awt.Color(255, 255, 255));
        iconGKM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        iconGKM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/gaji karyawan magang.png"))); // NOI18N
        conDashboard.add(iconGKM, new org.netbeans.lib.awtextra.AbsoluteConstraints(601, 512, -1, -1));

        lJumlahGajiKaryawanMagang.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 20)); // NOI18N
        lJumlahGajiKaryawanMagang.setForeground(new java.awt.Color(255, 255, 255));
        lJumlahGajiKaryawanMagang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lJumlahGajiKaryawanMagang.setText("Jumlah Gaji Karyawan Magang");
        conDashboard.add(lJumlahGajiKaryawanMagang, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 495, -1, 35));

        lTelahDibayarMagang.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 20)); // NOI18N
        lTelahDibayarMagang.setForeground(new java.awt.Color(255, 255, 255));
        lTelahDibayarMagang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lTelahDibayarMagang.setText("Telah Dibayar");
        conDashboard.add(lTelahDibayarMagang, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 520, -1, 35));

        lvalueGKM.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 36)); // NOI18N
        lvalueGKM.setForeground(new java.awt.Color(255, 255, 255));
        lvalueGKM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lvalueGKM.setText("Rp. 1.200.000.000");
        conDashboard.add(lvalueGKM, new org.netbeans.lib.awtextra.AbsoluteConstraints(734, 573, 320, 35));

        enam.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/content-dashboard.png"))); // NOI18N
        conDashboard.add(enam, new org.netbeans.lib.awtextra.AbsoluteConstraints(578, 479, -1, -1));

        panelContent.add(conDashboard, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1092, 697));

        getContentPane().add(panelContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(274, 71, 1092, 697));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void colorActive(JPanel p){
        p.setBackground(new Color(135,33,36));
    }
    
    public void colorNoActive(JPanel p){
        p.setBackground(new Color(207,36,42));
    }
    
    private void bdashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bdashboardMouseClicked
        menuDashboard();
    }//GEN-LAST:event_bdashboardMouseClicked

    private void badminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_badminMouseClicked
        menuAdmin();
    }//GEN-LAST:event_badminMouseClicked

    private void bjabatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bjabatanMouseClicked
        menuJabatan();
    }//GEN-LAST:event_bjabatanMouseClicked

    private void bpenggajianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bpenggajianMouseClicked
        menuPenggajian();
    }//GEN-LAST:event_bpenggajianMouseClicked

    private void blogoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_blogoutMouseClicked
        System.exit(0);
    }//GEN-LAST:event_blogoutMouseClicked

    private void cbKaryawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKaryawanActionPerformed
        karyawan();
    }//GEN-LAST:event_cbKaryawanActionPerformed

    private void cbPenggajianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPenggajianActionPerformed
        penggajian();
    }//GEN-LAST:event_cbPenggajianActionPerformed

    private void bkaryawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bkaryawanMouseClicked
        menuKaryawan();
    }//GEN-LAST:event_bkaryawanMouseClicked

    private void bTHRMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bTHRMouseClicked
        menuTHR();
    }//GEN-LAST:event_bTHRMouseClicked

    private void bTambah_AdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambah_AdminActionPerformed
        Data_User.CreateData(txtUsername_Admin.getText(), txtNama_Admin.getText(), txtEmail_Admin.getText(), txtNotelp_Admin.getText(), txtAlamat_Admin.getText(), encrypt(txtPassword_Admin.getText()));
        DataAdmin();
        Kosongin_Admin();
    }//GEN-LAST:event_bTambah_AdminActionPerformed

    private void tbAdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbAdminMouseClicked
        int brs = tbAdmin.getSelectedRow();
        String ini = tbAdmin.getValueAt(brs, 0).toString();
        String itu = tbAdmin.getValueAt(brs, 1).toString();
        String uhuy = tbAdmin.getValueAt(brs, 2).toString();
        String uwu = tbAdmin.getValueAt(brs, 3).toString();
        String ono = tbAdmin.getValueAt(brs, 4).toString();
        txtUsername_Admin.setText(ini);
        txtNama_Admin.setText(itu);
        txtEmail_Admin.setText(uhuy);
        txtNotelp_Admin.setText(uwu);
        txtAlamat_Admin.setText(ono);
    }//GEN-LAST:event_tbAdminMouseClicked

    private void bTambah_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambah_KTActionPerformed
        String FormDate = Date.format(cbTglmsk_KT.getDate());
        Data_Karyawan_Tetap.CreateData(txtNik_KT.getText(),txtNama_KT.getText(),txtEmail_KT.getText(), txtNotelp_KT.getText(), txtAlamat_KT.getText(), (String)cbJabatan_KT.getSelectedItem(), FormDate);
        DataKT();
        Kosongin_KT();
    }//GEN-LAST:event_bTambah_KTActionPerformed

    private void bUbah_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbah_KTActionPerformed
        String FormDate = Date.format(cbTglmsk_KT.getDate());
        Data_Karyawan_Tetap.UpdateData(txtNik_KT.getText(),txtNama_KT.getText(),txtEmail_KT.getText(), txtNotelp_KT.getText(), txtAlamat_KT.getText(), (String)cbJabatan_KT.getSelectedItem(), FormDate);
        DataKT();
        Kosongin_KT();
        txtNik_KT.enable();
    }//GEN-LAST:event_bUbah_KTActionPerformed

    private void bHapus_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapus_KTActionPerformed
        Data_Karyawan_Tetap.DeleteData(txtNik_KT.getText());
        DataKT();
        txtNik_KT.enable();
        Kosongin_KT();
    }//GEN-LAST:event_bHapus_KTActionPerformed

    private void bTambah_JabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambah_JabatanActionPerformed
        Data_Jabatan.CreateData((String)cbKode_Jabatan_Jabatan.getSelectedItem(), txtJabatan.getText(), Integer.parseInt(txtGajipokok.getText()));
        DataJabatan();
        Kosongin_Jabatan();
    }//GEN-LAST:event_bTambah_JabatanActionPerformed

    private void tbKTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKTMouseClicked
        int brs = tbKT.getSelectedRow();
        String ini = tbKT.getValueAt(brs, 0).toString();
        String itu = tbKT.getValueAt(brs, 1).toString();
        String uhuy = tbKT.getValueAt(brs, 2).toString();
        String uwu = tbKT.getValueAt(brs, 3).toString();
        String ono = tbKT.getValueAt(brs, 4).toString();
        String sini = tbKT.getValueAt(brs, 5).toString();
        situ = java.sql.Date.valueOf(tbKT.getValueAt(brs, 6).toString());
        txtNik_KT.setText(ini);
        txtNik_KT.disable();
        txtNama_KT.setText(itu);
        txtEmail_KT.setText(uhuy);
        txtNotelp_KT.setText(uwu);
        txtAlamat_KT.setText(ono);
        cbJabatan_KT.setSelectedItem(sini);
        cbTglmsk_KT.setDate(situ);
    }//GEN-LAST:event_tbKTMouseClicked

    private void bTambah_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambah_KKActionPerformed
        String Masuk = Date.format(cbTglmsk_KK.getDate());
        String Keluar = Date.format(cbTglklr_KK.getDate());
        Data_Karyawan_Kontrak.CreateData(txtNik_KK.getText(),txtNama_KK.getText(),txtEmail_KK.getText(), txtNotelp_KK.getText(), txtAlamat_KK.getText(), (String)cbJabatan_KK.getSelectedItem(), Masuk, Keluar);
        DataKK();
        Kosongin_KK();
    }//GEN-LAST:event_bTambah_KKActionPerformed

    private void bUbah_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbah_KKActionPerformed
        String Masuk = Date.format(cbTglmsk_KK.getDate());
        String Keluar = Date.format(cbTglklr_KK.getDate());
        Data_Karyawan_Kontrak.UpdateData(txtNik_KK.getText(),txtNama_KK.getText(),txtEmail_KK.getText(), txtNotelp_KK.getText(), txtAlamat_KK.getText(), (String)cbJabatan_KK.getSelectedItem(), Masuk, Keluar);
        DataKK();
        txtNik_KK.enable();
        Kosongin_KK();
    }//GEN-LAST:event_bUbah_KKActionPerformed

    private void bHapus_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapus_KKActionPerformed
        Data_Karyawan_Kontrak.DeleteData(txtNik_KK.getText());
        DataKK();
        txtNik_KK.enable();
        Kosongin_KK();
    }//GEN-LAST:event_bHapus_KKActionPerformed

    private void bTambah_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambah_KMActionPerformed
        String Masuk = Date.format(cbTglmsk_KM.getDate());
        String Keluar = Date.format(cbTglklr_KM.getDate());
        Data_Karyawan_Magang.CreateData(txtNik_KM.getText(),txtNama_KM.getText(),txtEmail_KM.getText(), txtNotelp_KM.getText(), txtAlamat_KM.getText(), (String)cbJabatan_KM.getSelectedItem(), Masuk, Keluar);
        DataKM();
        Kosongin_KM();
    }//GEN-LAST:event_bTambah_KMActionPerformed

    private void bUbah_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbah_KMActionPerformed
        String Masuk = Date.format(cbTglmsk_KM.getDate());
        String Keluar = Date.format(cbTglklr_KM.getDate());
        Data_Karyawan_Magang.UpdateData(txtNik_KM.getText(),txtNama_KM.getText(),txtEmail_KM.getText(), txtNotelp_KM.getText(), txtAlamat_KM.getText(), (String)cbJabatan_KM.getSelectedItem(), Masuk, Keluar);
        DataKM();
        txtNik_KM.enable();
        Kosongin_KM();
    }//GEN-LAST:event_bUbah_KMActionPerformed

    private void bHapus_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapus_KMActionPerformed
        Data_Karyawan_Magang.DeleteData(txtNik_KM.getText());
        DataKM();
        txtNik_KM.enable();
        Kosongin_KM();
    }//GEN-LAST:event_bHapus_KMActionPerformed

    private void tbKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKMMouseClicked
        int brs = tbKM.getSelectedRow();
        String ini = tbKM.getValueAt(brs, 0).toString();
        String itu = tbKM.getValueAt(brs, 1).toString();
        String uhuy = tbKM.getValueAt(brs, 2).toString();
        String uwu = tbKM.getValueAt(brs, 3).toString();
        String ono = tbKM.getValueAt(brs, 4).toString();
        String sini = tbKM.getValueAt(brs, 5).toString();
        situ = java.sql.Date.valueOf(tbKM.getValueAt(brs, 6).toString());
        Sini = java.sql.Date.valueOf(tbKM.getValueAt(brs, 7).toString());
        txtNik_KM.setText(ini);
        txtNik_KM.disable();
        txtNama_KM.setText(itu);
        txtEmail_KM.setText(uhuy);
        txtNotelp_KM.setText(uwu);
        txtAlamat_KM.setText(ono);
        cbJabatan_KM.setSelectedItem(sini);
        cbTglmsk_KM.setDate(situ);
        cbTglklr_KM.setDate(Sini);
    }//GEN-LAST:event_tbKMMouseClicked

    private void tbKKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKKMouseClicked
        int brs = tbKK.getSelectedRow();
        String ini = tbKK.getValueAt(brs, 0).toString();
        String itu = tbKK.getValueAt(brs, 1).toString();
        String uhuy = tbKK.getValueAt(brs, 2).toString();
        String uwu = tbKK.getValueAt(brs, 3).toString();
        String ono = tbKK.getValueAt(brs, 4).toString();
        String sini = tbKK.getValueAt(brs, 5).toString();
        situ = java.sql.Date.valueOf(tbKK.getValueAt(brs, 6).toString());
        Sini = java.sql.Date.valueOf(tbKK.getValueAt(brs, 7).toString());
        txtNik_KK.setText(ini);
        txtNik_KK.disable();
        txtNama_KK.setText(itu);
        txtEmail_KK.setText(uhuy);
        txtNotelp_KK.setText(uwu);
        txtAlamat_KK.setText(ono);
        cbJabatan_KK.setSelectedItem(sini);
        cbTglmsk_KK.setDate(situ);
        cbTglklr_KK.setDate(Sini);
    }//GEN-LAST:event_tbKKMouseClicked

    private void tbJabatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJabatanMouseClicked
        cbKode_Jabatan_Jabatan.disable();
        int brs = tbJabatan.getSelectedRow();
        String ini = tbJabatan.getValueAt(brs, 0).toString();
        String itu = tbJabatan.getValueAt(brs, 1).toString();
        String uhuy = tbJabatan.getValueAt(brs, 2).toString();
        cbKode_Jabatan_Jabatan.setSelectedItem(ini);
        txtJabatan.setText(itu);
        txtGajipokok.setText(uhuy);
    }//GEN-LAST:event_tbJabatanMouseClicked

    private void bTambah_penggajian_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambah_penggajian_KTActionPerformed
        String TglSlip = Date.format(cbTglslip_penggajian_KT.getDate());
        Data_Karyawan_Tetap.CreateDataSlipGaji(txtKodeslip_penggajian_KT.getText(), nmprofile.getText(), TglSlip, txtNik_penggajian_KT.getText(), txtNama_penggajian_KT.getText(), (String)cbKodejabatan_penggajian_KT.getSelectedItem(), Integer.parseInt(txtGajipokok_penggajian_KT.getText()), Integer.parseInt(txtJmlabsensi_penggajian_KT.getText()), Integer.parseInt(txtTotalgaji_penggajian_KT.getText()));
        try{
            String File1 = "src/Laporan/SlipKTByKode.jasper";
            HashMap par = new HashMap();
            par.put("Kode", txtKodeslip_penggajian_KT.getText());
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
        SlipGajiKT();
        DataGajiKT();
        KosonginGajiKT();
    }//GEN-LAST:event_bTambah_penggajian_KTActionPerformed

    private void tbPenggajianKTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPenggajianKTMouseClicked
        int brs = tbPenggajianKT.getSelectedRow();
        String ini = tbPenggajianKT.getValueAt(brs, 0).toString();
        String itu = tbPenggajianKT.getValueAt(brs, 1).toString();
        situ = java.sql.Date.valueOf(tbPenggajianKT.getValueAt(brs, 2).toString());
        String uhuy = tbPenggajianKT.getValueAt(brs, 3).toString();
        String uwu = tbPenggajianKT.getValueAt(brs, 4).toString();
        String ono = tbPenggajianKT.getValueAt(brs, 5).toString();
        String sini = tbPenggajianKT.getValueAt(brs, 6).toString();
        String sono = tbPenggajianKT.getValueAt(brs, 7).toString();
        String inu = tbPenggajianKT.getValueAt(brs, 8).toString();
        txtKodeslip_penggajian_KT.setText(ini); 
        nmprofile.setText(itu); 
        cbTglslip_penggajian_KT.setDate(situ); 
        txtNik_penggajian_KT.setText(uhuy); 
        txtNama_penggajian_KT.setText(uwu); 
        cbKodejabatan_penggajian_KT.setSelectedItem(ono); 
        txtGajipokok_penggajian_KT.setText(sini); 
        txtJmlabsensi_penggajian_KT.setText(sono); 
        txtTotalgaji_penggajian_KT.setText(inu);
    }//GEN-LAST:event_tbPenggajianKTMouseClicked

    private void bUbah_penggajian_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbah_penggajian_KTActionPerformed
        String TglSlip = Date.format(cbTglslip_penggajian_KT.getDate());
        Data_Karyawan_Tetap.UpdateDataSlipGaji(txtKodeslip_penggajian_KT.getText(), nmprofile.getText(), TglSlip, txtNik_penggajian_KT.getText(), txtNama_penggajian_KT.getText(), (String)cbKodejabatan_penggajian_KT.getSelectedItem(), Integer.parseInt(txtGajipokok_penggajian_KT.getText()), Integer.parseInt(txtJmlabsensi_penggajian_KT.getText()), Integer.parseInt(txtTotalgaji_penggajian_KT.getText()));
        SlipGajiKT();
        DataGajiKT();
        KosonginGajiKT();
    }//GEN-LAST:event_bUbah_penggajian_KTActionPerformed

    private void bCari_penggajian_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCari_penggajian_KTActionPerformed
        popupKaryawanTetap obj = new popupKaryawanTetap();
            obj.KT = this;
            obj.setVisible(true);
            obj.setResizable(false);
    }//GEN-LAST:event_bCari_penggajian_KTActionPerformed

    private void bHitunggaji_penggajian_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHitunggaji_penggajian_KTActionPerformed
        HitungGajiKT();
    }//GEN-LAST:event_bHitunggaji_penggajian_KTActionPerformed

    private void bHapus_penggajian_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapus_penggajian_KTActionPerformed
        Data_Karyawan_Tetap.DeleteDataSlipGaji(txtKodeslip_penggajian_KT.getText());
        SlipGajiKT();
        DataGajiKT();
        KosonginGajiKT();
    }//GEN-LAST:event_bHapus_penggajian_KTActionPerformed

    private void txtSearch_JabatanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_JabatanKeyTyped
            DataJabatan();
    }//GEN-LAST:event_txtSearch_JabatanKeyTyped

    private void bSearch_JabatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSearch_JabatanMouseClicked
        DataJabatan();
    }//GEN-LAST:event_bSearch_JabatanMouseClicked

    private void bCari_penggajian_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCari_penggajian_KMActionPerformed
        popupKaryawanMagang obj = new popupKaryawanMagang();
        obj.KM = this;
        obj.setVisible(true);
        obj.setResizable(false);
    }//GEN-LAST:event_bCari_penggajian_KMActionPerformed

    private void bHitunggaji_penggajian_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHitunggaji_penggajian_KMActionPerformed
        HitungGajiKM();
    }//GEN-LAST:event_bHitunggaji_penggajian_KMActionPerformed

    private void bTambah_penggajian_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambah_penggajian_KMActionPerformed
        String TglSlip = Date.format(cbTglslip_penggajian_KM.getDate());
        Data_Karyawan_Magang.CreateDataSlipGaji(txtKodeslip_penggajian_KM.getText(), nmprofile.getText(), TglSlip, txtNik_penggajian_KM.getText(), txtNama_penggajian_KM.getText(), (String)cbKodejabatan_penggajian_KM.getSelectedItem(), Integer.parseInt(txtGajipokok_penggajian_KM.getText()), Integer.parseInt(txtJmlabsensi_penggajian_KM.getText()), Integer.parseInt(txtTotalgaji_penggajian_KM.getText()));
        try{
            String File1 = "src/Laporan/SlipKMByKode.jasper";
            HashMap par = new HashMap();
            par.put("Kode", txtKodeslip_penggajian_KM.getText());
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
        SlipGajiKM();
        DataGajiKM();
        KosonginGajiKM();
    }//GEN-LAST:event_bTambah_penggajian_KMActionPerformed

    private void bUbah_penggajian_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbah_penggajian_KMActionPerformed
        String TglSlip = Date.format(cbTglslip_penggajian_KM.getDate());
        Data_Karyawan_Magang.UpdateDataSlipGaji(txtKodeslip_penggajian_KM.getText(), nmprofile.getText(), TglSlip, txtNik_penggajian_KM.getText(), txtNama_penggajian_KM.getText(), (String)cbKodejabatan_penggajian_KM.getSelectedItem(), Integer.parseInt(txtGajipokok_penggajian_KM.getText()), Integer.parseInt(txtJmlabsensi_penggajian_KM.getText()), Integer.parseInt(txtTotalgaji_penggajian_KM.getText()));
        SlipGajiKM();
        DataGajiKM();
        KosonginGajiKM();
    }//GEN-LAST:event_bUbah_penggajian_KMActionPerformed

    private void bHapus_penggajian_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapus_penggajian_KMActionPerformed
        Data_Karyawan_Magang.DeleteDataSlipGaji(txtKodeslip_penggajian_KM.getText());
        SlipGajiKM();
        DataGajiKM();
        KosonginGajiKM();
    }//GEN-LAST:event_bHapus_penggajian_KMActionPerformed

    private void bBatal_penggajian_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatal_penggajian_KTActionPerformed
        SlipGajiKT();
        DataGajiKT();
        KosonginGajiKT();
    }//GEN-LAST:event_bBatal_penggajian_KTActionPerformed

    private void bBatal_penggajian_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatal_penggajian_KMActionPerformed
        SlipGajiKM();
        DataGajiKM();
        KosonginGajiKM();
    }//GEN-LAST:event_bBatal_penggajian_KMActionPerformed

    private void txtSearch_penggajian_KMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_penggajian_KMKeyTyped
        DataGajiKM();
    }//GEN-LAST:event_txtSearch_penggajian_KMKeyTyped

    private void bCari_penggajian_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCari_penggajian_KKActionPerformed
        popupKaryawanKontrak obji = new popupKaryawanKontrak();
        obji.KK = this;
        obji.setVisible(true);
        obji.setResizable(false);
    }//GEN-LAST:event_bCari_penggajian_KKActionPerformed

    private void bTambah_penggajian_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambah_penggajian_KKActionPerformed
        String TglSlip = Date.format(cbTglslip_penggajian_KK.getDate());
        Data_Karyawan_Kontrak.CreateDataSlipGaji(txtKodeslip_penggajian_KK.getText(), nmprofile.getText(), TglSlip, txtNik_penggajian_KK.getText(), txtNama_penggajian_KK.getText(), (String)cbKodejabatan_penggajian_KK.getSelectedItem(), Integer.parseInt(txtGajipokok_penggajian_KK.getText()), Integer.parseInt(txtJmlabsensi_penggajian_KK.getText()), Integer.parseInt(txtTotalgaji_penggajian_KK.getText()));
        try{
            String File1 = "src/Laporan/SlipKKByKode.jasper";
            HashMap par = new HashMap();
            par.put("Kode", txtKodeslip_penggajian_KK.getText());
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
        SlipGajiKK();
        DataGajiKK();
        KosonginGajiKK();
    }//GEN-LAST:event_bTambah_penggajian_KKActionPerformed

    private void bUbah_penggajian_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbah_penggajian_KKActionPerformed
        String TglSlip = Date.format(cbTglslip_penggajian_KK.getDate());
        Data_Karyawan_Kontrak.UpdateDataSlipGaji(txtKodeslip_penggajian_KK.getText(), nmprofile.getText(), TglSlip, txtNik_penggajian_KK.getText(), txtNama_penggajian_KK.getText(), (String)cbKodejabatan_penggajian_KK.getSelectedItem(), Integer.parseInt(txtGajipokok_penggajian_KK.getText()), Integer.parseInt(txtJmlabsensi_penggajian_KK.getText()), Integer.parseInt(txtTotalgaji_penggajian_KK.getText()));
        SlipGajiKK();
        DataGajiKK();
        KosonginGajiKK();
    }//GEN-LAST:event_bUbah_penggajian_KKActionPerformed

    private void bHapus_penggajian_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapus_penggajian_KKActionPerformed
        Data_Karyawan_Kontrak.DeleteDataSlipGaji(txtKodeslip_penggajian_KK.getText());
        SlipGajiKK();
        DataGajiKK();
        KosonginGajiKK();
    }//GEN-LAST:event_bHapus_penggajian_KKActionPerformed

    private void bBatal_penggajian_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatal_penggajian_KKActionPerformed
        SlipGajiKK();
        DataGajiKK();
        KosonginGajiKK();
    }//GEN-LAST:event_bBatal_penggajian_KKActionPerformed

    private void bHitunggaji_penggajian_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHitunggaji_penggajian_KKActionPerformed
        HitungGajiKK();
    }//GEN-LAST:event_bHitunggaji_penggajian_KKActionPerformed

    private void txtSearch_penggajian_KKKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_penggajian_KKKeyTyped
        DataGajiKK();
    }//GEN-LAST:event_txtSearch_penggajian_KKKeyTyped

    private void tbPenggajianKKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPenggajianKKMouseClicked
        int brs = tbPenggajianKK.getSelectedRow();
        String ini = tbPenggajianKK.getValueAt(brs, 0).toString();
        String itu = tbPenggajianKK.getValueAt(brs, 1).toString();
        situ = java.sql.Date.valueOf(tbPenggajianKK.getValueAt(brs, 2).toString());
        String uhuy = tbPenggajianKK.getValueAt(brs, 3).toString();
        String uwu = tbPenggajianKK.getValueAt(brs, 4).toString();
        String ono = tbPenggajianKK.getValueAt(brs, 5).toString();
        String sini = tbPenggajianKK.getValueAt(brs, 6).toString();
        String sono = tbPenggajianKK.getValueAt(brs, 7).toString();
        String inu = tbPenggajianKK.getValueAt(brs, 8).toString();
        txtKodeslip_penggajian_KK.setText(ini); 
        nmprofile.setText(itu); 
        cbTglslip_penggajian_KK.setDate(situ); 
        txtNik_penggajian_KK.setText(uhuy); 
        txtNama_penggajian_KK.setText(uwu); 
        cbKodejabatan_penggajian_KK.setSelectedItem(ono); 
        txtGajipokok_penggajian_KK.setText(sini); 
        txtJmlabsensi_penggajian_KK.setText(sono); 
        txtTotalgaji_penggajian_KK.setText(inu);
    }//GEN-LAST:event_tbPenggajianKKMouseClicked

    private void tbPenggajianKMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPenggajianKMMouseClicked
        int brs = tbPenggajianKM.getSelectedRow();
        String ini = tbPenggajianKM.getValueAt(brs, 0).toString();
        String itu = tbPenggajianKM.getValueAt(brs, 1).toString();
        situ = java.sql.Date.valueOf(tbPenggajianKM.getValueAt(brs, 2).toString());
        String uhuy = tbPenggajianKM.getValueAt(brs, 3).toString();
        String uwu = tbPenggajianKM.getValueAt(brs, 4).toString();
        String ono = tbPenggajianKM.getValueAt(brs, 5).toString();
        String sini = tbPenggajianKM.getValueAt(brs, 6).toString();
        String sono = tbPenggajianKM.getValueAt(brs, 7).toString();
        String inu = tbPenggajianKM.getValueAt(brs, 8).toString();
        txtKodeslip_penggajian_KM.setText(ini); 
        nmprofile.setText(itu); 
        cbTglslip_penggajian_KM.setDate(situ); 
        txtNik_penggajian_KM.setText(uhuy); 
        txtNama_penggajian_KM.setText(uwu); 
        cbKodejabatan_penggajian_KM.setSelectedItem(ono); 
        txtGajipokok_penggajian_KM.setText(sini); 
        txtJmlabsensi_penggajian_KM.setText(sono); 
        txtTotalgaji_penggajian_KM.setText(inu);
    }//GEN-LAST:event_tbPenggajianKMMouseClicked

    private void bCari_THR_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCari_THR_KKActionPerformed
        popupTHRKaryawanKontrak objekS = new popupTHRKaryawanKontrak();
        objekS.THRKK = this;
        objekS.setVisible(true);
        objekS.setResizable(false);
    }//GEN-LAST:event_bCari_THR_KKActionPerformed

    private void bHitungTHR_THR_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHitungTHR_THR_KKActionPerformed
        HitungTHRKK();
    }//GEN-LAST:event_bHitungTHR_THR_KKActionPerformed

    private void bCari_THR_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCari_THR_KTActionPerformed
        popupKaryawanTetap objekSi = new popupKaryawanTetap();
        objekSi.KT = this;
        objekSi.setVisible(true);
        objekSi.setResizable(false);
    }//GEN-LAST:event_bCari_THR_KTActionPerformed

    private void bHitungTHR_THR_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHitungTHR_THR_KTActionPerformed
        HitungTHRKT();
    }//GEN-LAST:event_bHitungTHR_THR_KTActionPerformed

    private void bTambah_THR_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambah_THR_KKActionPerformed
        String TglSlip = Date.format(cbTglslip_THR_KK.getDate());
        Data_Karyawan_Kontrak.CreateDataSlipTHR(txtKodeslip_THR_KK.getText(), nmprofile.getText(), TglSlip, txtNik_THR_KK.getText(), txtNama_THR_KK.getText(), (String)cbKodejabatan_THR_KK.getSelectedItem(), Integer.parseInt(txtGajipokok_THR_KK.getText()), Integer.parseInt(txtTotalthr_THR_KK.getText()));  
        try{
            String File1 = "src/Laporan/THRKKByKode.jasper";
            HashMap par = new HashMap();
            par.put("Kode", txtKodeslip_THR_KK.getText());
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
        DataTHRKK();
        KosonginTHRKK();
        SlipTHRKK();
    }//GEN-LAST:event_bTambah_THR_KKActionPerformed

    private void bTambah_THR_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bTambah_THR_KTActionPerformed
        String TglSlip = Date.format(cbTglslip_THR_KT.getDate());
        Data_Karyawan_Tetap.CreateDataSlipTHR(txtKodeslip_THR_KT.getText(), nmprofile.getText(), TglSlip, txtNik_THR_KT.getText(), txtNama_THR_KT.getText(), (String)cbKodejabatan_THR_KT.getSelectedItem(), Integer.parseInt(txtGajipokok_THR_KT.getText()), Integer.parseInt(txtTotalthr_THR_KT.getText()));  
        try{
            String File1 = "src/Laporan/THRKTByKode.jasper";
            HashMap par = new HashMap();
            par.put("Kode", txtKodeslip_THR_KT.getText());
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
        DataTHRKT();
        KosonginTHRKT();
        SlipTHRKT();
    }//GEN-LAST:event_bTambah_THR_KTActionPerformed

    private void cbTHRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTHRActionPerformed
        THR();
    }//GEN-LAST:event_cbTHRActionPerformed

    private void bUbah_THR_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbah_THR_KTActionPerformed
        String TglSlip = Date.format(cbTglslip_THR_KT.getDate());
        Data_Karyawan_Tetap.UpdateDataSlipTHR(txtKodeslip_THR_KT.getText(), nmprofile.getText(), TglSlip, txtNik_THR_KT.getText(), txtNama_THR_KT.getText(), (String)cbKodejabatan_THR_KT.getSelectedItem(), Integer.parseInt(txtGajipokok_THR_KT.getText()), Integer.parseInt(txtTotalthr_THR_KT.getText()));  
        DataTHRKT();
        KosonginTHRKT();
        SlipTHRKT();
    }//GEN-LAST:event_bUbah_THR_KTActionPerformed

    private void bHapus_THR_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapus_THR_KTActionPerformed
        Data_Karyawan_Tetap.DeleteDataSlipTHR(txtKodeslip_THR_KT.getText());
        DataTHRKT();
        KosonginTHRKT();
        SlipTHRKT();
    }//GEN-LAST:event_bHapus_THR_KTActionPerformed

    private void bUbah_THR_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbah_THR_KKActionPerformed
        String TglSlip = Date.format(cbTglslip_THR_KK.getDate());
        Data_Karyawan_Kontrak.UpdateDataSlipTHR(txtKodeslip_THR_KK.getText(), nmprofile.getText(), TglSlip, txtNik_THR_KK.getText(), txtNama_THR_KK.getText(), (String)cbKodejabatan_THR_KK.getSelectedItem(), Integer.parseInt(txtGajipokok_THR_KK.getText()), Integer.parseInt(txtTotalthr_THR_KK.getText()));  
        DataTHRKK();
        KosonginTHRKK();
        SlipTHRKK();
    }//GEN-LAST:event_bUbah_THR_KKActionPerformed

    private void bHapus_THR_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapus_THR_KKActionPerformed
        Data_Karyawan_Kontrak.DeleteDataSlipTHR(txtKodeslip_THR_KK.getText());
        DataTHRKK();
        KosonginTHRKK();
        SlipTHRKK();
    }//GEN-LAST:event_bHapus_THR_KKActionPerformed

    private void bBatal_THR_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatal_THR_KKActionPerformed
        DataTHRKK();
        KosonginTHRKK();
        SlipTHRKK();
    }//GEN-LAST:event_bBatal_THR_KKActionPerformed

    private void bSearch_THR_KKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSearch_THR_KKMouseClicked
        DataTHRKK();
    }//GEN-LAST:event_bSearch_THR_KKMouseClicked

    private void bBatal_THR_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatal_THR_KTActionPerformed
        KosonginTHRKT();
        SlipTHRKT();
    }//GEN-LAST:event_bBatal_THR_KTActionPerformed

    private void txtSearch_THR_KKKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_THR_KKKeyTyped
        DataTHRKK();
    }//GEN-LAST:event_txtSearch_THR_KKKeyTyped

    private void txtSearch_THR_KTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_THR_KTKeyTyped
        DataTHRKT();
    }//GEN-LAST:event_txtSearch_THR_KTKeyTyped

    private void txtSearch_penggajian_KTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_penggajian_KTKeyTyped
        DataGajiKT();
    }//GEN-LAST:event_txtSearch_penggajian_KTKeyTyped

    private void penggajianKontrakKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_penggajianKontrakKeyTyped
        DataGajiKK();
    }//GEN-LAST:event_penggajianKontrakKeyTyped

    private void bSearch_penggajian_KMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSearch_penggajian_KMMouseClicked
        DataGajiKM();
    }//GEN-LAST:event_bSearch_penggajian_KMMouseClicked

    private void bSearch_penggajian_KKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSearch_penggajian_KKMouseClicked
        DataGajiKK();
    }//GEN-LAST:event_bSearch_penggajian_KKMouseClicked

    private void bSearch_penggajian_KTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSearch_penggajian_KTMouseClicked
        DataGajiKT();
    }//GEN-LAST:event_bSearch_penggajian_KTMouseClicked

    private void bUbah_JabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bUbah_JabatanActionPerformed
        cbKode_Jabatan_Jabatan.enable();
        Data_Jabatan.UpdateData((String)cbKode_Jabatan_Jabatan.getSelectedItem(), txtJabatan.getText(), Integer.parseInt(txtGajipokok.getText()));
        DataJabatan();
        Kosongin_Jabatan();
    }//GEN-LAST:event_bUbah_JabatanActionPerformed

    private void bHapus_JabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bHapus_JabatanActionPerformed
        cbKode_Jabatan_Jabatan.enable();
        Data_Jabatan.DeleteData((String)cbKode_Jabatan_Jabatan.getSelectedItem());
        DataJabatan();
        Kosongin_Jabatan();
    }//GEN-LAST:event_bHapus_JabatanActionPerformed

    private void bBatal_JabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatal_JabatanActionPerformed
        cbKode_Jabatan_Jabatan.enable();
        Kosongin_Jabatan();
    }//GEN-LAST:event_bBatal_JabatanActionPerformed

    private void bBatal_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatal_KTActionPerformed
        Kosongin_KT();
        txtNik_KT.enable();
    }//GEN-LAST:event_bBatal_KTActionPerformed

    private void txtSearch_KTKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_KTKeyTyped
        DataKT();
    }//GEN-LAST:event_txtSearch_KTKeyTyped

    private void bSearch_KTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSearch_KTMouseClicked
        DataKT();
    }//GEN-LAST:event_bSearch_KTMouseClicked

    private void bBatal_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatal_KKActionPerformed
        txtNik_KK.enable();
        Kosongin_KK();
    }//GEN-LAST:event_bBatal_KKActionPerformed

    private void txtSearch_KKKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_KKKeyTyped
        DataKK();
    }//GEN-LAST:event_txtSearch_KKKeyTyped

    private void bSearch_KKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSearch_KKMouseClicked
        DataKK();
    }//GEN-LAST:event_bSearch_KKMouseClicked

    private void bBatal_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatal_KMActionPerformed
        txtNik_KM.enable();
        Kosongin_KM();
    }//GEN-LAST:event_bBatal_KMActionPerformed

    private void txtSearch_KMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_KMKeyTyped
        DataKM();
    }//GEN-LAST:event_txtSearch_KMKeyTyped

    private void bSearch_KMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSearch_KMMouseClicked
        DataKM();
    }//GEN-LAST:event_bSearch_KMMouseClicked

    private void bBatal_AdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBatal_AdminActionPerformed
        Kosongin_Admin();
    }//GEN-LAST:event_bBatal_AdminActionPerformed

    private void txtSearch_AdminKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch_AdminKeyTyped
        DataAdmin();
    }//GEN-LAST:event_txtSearch_AdminKeyTyped

    private void bSearch_AdminMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bSearch_AdminMouseClicked
        DataAdmin();
    }//GEN-LAST:event_bSearch_AdminMouseClicked

    private void tbTHRKTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTHRKTMouseClicked
        int brs = tbTHRKT.getSelectedRow();
        String ini = tbTHRKT.getValueAt(brs, 0).toString();
        String itu = tbTHRKT.getValueAt(brs, 1).toString();
        situ = java.sql.Date.valueOf(tbTHRKT.getValueAt(brs, 2).toString());
        String uhuy = tbTHRKT.getValueAt(brs, 3).toString();
        String uwu = tbTHRKT.getValueAt(brs, 4).toString();
        String ono = tbTHRKT.getValueAt(brs, 5).toString();
        String sini = tbTHRKT.getValueAt(brs, 6).toString();
        String inu = tbTHRKT.getValueAt(brs, 7).toString();
        txtKodeslip_THR_KT.setText(ini); 
        nmprofile.setText(itu); 
        cbTglslip_THR_KT.setDate(situ); 
        txtNik_THR_KT.setText(uhuy); 
        txtNama_THR_KT.setText(uwu); 
        cbKodejabatan_THR_KT.setSelectedItem(ono); 
        txtGajipokok_THR_KT.setText(sini); 
        txtTotalthr_THR_KT.setText(inu);
    }//GEN-LAST:event_tbTHRKTMouseClicked

    private void tbTHRKKMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTHRKKMouseClicked
        int brs = tbTHRKK.getSelectedRow();
        String ini = tbTHRKK.getValueAt(brs, 0).toString();
        String itu = tbTHRKK.getValueAt(brs, 1).toString();
        situ = java.sql.Date.valueOf(tbTHRKK.getValueAt(brs, 2).toString());
        String uhuy = tbTHRKK.getValueAt(brs, 3).toString();
        String uwu = tbTHRKK.getValueAt(brs, 4).toString();
        String ono = tbTHRKK.getValueAt(brs, 5).toString();
        String sini = tbTHRKK.getValueAt(brs, 6).toString();
        String inu = tbTHRKK.getValueAt(brs, 7).toString();
        txtKodeslip_THR_KK.setText(ini); 
        nmprofile.setText(itu); 
        cbTglslip_THR_KK.setDate(situ); 
        txtNik_THR_KK.setText(uhuy); 
        txtNama_THR_KK.setText(uwu); 
        cbKodejabatan_THR_KK.setSelectedItem(ono); 
        txtGajipokok_THR_KK.setText(sini); 
        txtTotalthr_THR_KK.setText(inu);
    }//GEN-LAST:event_tbTHRKKMouseClicked

    private void bCetak_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetak_KTActionPerformed
        try{
            String File1 = "src/Laporan/Karyawan_Tetap.jasper";
            HashMap par = new HashMap();
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
    }//GEN-LAST:event_bCetak_KTActionPerformed

    private void bCetak_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetak_KKActionPerformed
        try{
            String File1 = "src/Laporan/Karyawan_Kontrak.jasper";
            HashMap par = new HashMap();
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
    }//GEN-LAST:event_bCetak_KKActionPerformed

    private void bCetak_JabatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetak_JabatanActionPerformed
        try{
            String File1 = "src/Laporan/Jabatan.jasper";
            HashMap par = new HashMap();
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
    }//GEN-LAST:event_bCetak_JabatanActionPerformed

    private void bCetak_PKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetak_PKTActionPerformed
        try{
            String File1 = "src/Laporan/SlipKT.jasper";
            HashMap par = new HashMap();
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
    }//GEN-LAST:event_bCetak_PKTActionPerformed

    private void bCetak_THR_KKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetak_THR_KKActionPerformed
        try{
            String File1 = "src/Laporan/THRKK.jasper";
            HashMap par = new HashMap();
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
    }//GEN-LAST:event_bCetak_THR_KKActionPerformed

    private void bCetak_THR_KTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetak_THR_KTActionPerformed
        try{
            String File1 = "src/Laporan/THRKT.jasper";
            HashMap par = new HashMap();
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
    }//GEN-LAST:event_bCetak_THR_KTActionPerformed

    private void bCetak_PKKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetak_PKKActionPerformed
        try{
            String File1 = "src/Laporan/SlipKK.jasper";
            HashMap par = new HashMap();
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
    }//GEN-LAST:event_bCetak_PKKActionPerformed

    private void bCetak_PKMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetak_PKMActionPerformed
        try{
            String File1 = "src/Laporan/SlipKM.jasper";
            HashMap par = new HashMap();
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
    }//GEN-LAST:event_bCetak_PKMActionPerformed

    private void bCetak_KMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bCetak_KMActionPerformed
        try{
            String File1 = "src/Laporan/Karyawan_Magang.jasper";
            HashMap par = new HashMap();
            JasperPrint Print = JasperFillManager.fillReport (File1, par, conn);
            JasperViewer.viewReport(Print, false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
        }catch(Exception yusuf){
            JOptionPane.showMessageDialog(this, yusuf.getMessage());
        }
    }//GEN-LAST:event_bCetak_KMActionPerformed
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Admin().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel THRKontrak;
    private javax.swing.JPanel THRTetap;
    private javax.swing.JLabel TabelPenggajian;
    private javax.swing.JLabel TabelTHR;
    private javax.swing.JLabel avatar;
    private javax.swing.JButton bBatal_Admin;
    private javax.swing.JButton bBatal_Jabatan;
    private javax.swing.JButton bBatal_KK;
    private javax.swing.JButton bBatal_KM;
    private javax.swing.JButton bBatal_KT;
    private javax.swing.JButton bBatal_THR_KK;
    private javax.swing.JButton bBatal_THR_KT;
    private javax.swing.JButton bBatal_penggajian_KK;
    private javax.swing.JButton bBatal_penggajian_KM;
    private javax.swing.JButton bBatal_penggajian_KT;
    private javax.swing.JButton bCari_THR_KK;
    private javax.swing.JButton bCari_THR_KT;
    private javax.swing.JButton bCari_penggajian_KK;
    private javax.swing.JButton bCari_penggajian_KM;
    private javax.swing.JButton bCari_penggajian_KT;
    private javax.swing.JButton bCetak_Jabatan;
    private javax.swing.JButton bCetak_KK;
    private javax.swing.JButton bCetak_KM;
    private javax.swing.JButton bCetak_KT;
    private javax.swing.JButton bCetak_PKK;
    private javax.swing.JButton bCetak_PKM;
    private javax.swing.JButton bCetak_PKT;
    private javax.swing.JButton bCetak_THR_KK;
    private javax.swing.JButton bCetak_THR_KT;
    private javax.swing.JButton bHapus_Jabatan;
    private javax.swing.JButton bHapus_KK;
    private javax.swing.JButton bHapus_KM;
    private javax.swing.JButton bHapus_KT;
    private javax.swing.JButton bHapus_THR_KK;
    private javax.swing.JButton bHapus_THR_KT;
    private javax.swing.JButton bHapus_penggajian_KK;
    private javax.swing.JButton bHapus_penggajian_KM;
    private javax.swing.JButton bHapus_penggajian_KT;
    private javax.swing.JButton bHitungTHR_THR_KK;
    private javax.swing.JButton bHitungTHR_THR_KT;
    private javax.swing.JButton bHitunggaji_penggajian_KK;
    private javax.swing.JButton bHitunggaji_penggajian_KM;
    private javax.swing.JButton bHitunggaji_penggajian_KT;
    private javax.swing.JLabel bSearch_Admin;
    private javax.swing.JLabel bSearch_Jabatan;
    private javax.swing.JLabel bSearch_KK;
    private javax.swing.JLabel bSearch_KM;
    private javax.swing.JLabel bSearch_KT;
    private javax.swing.JLabel bSearch_THR_KK;
    private javax.swing.JLabel bSearch_THR_KT;
    private javax.swing.JLabel bSearch_penggajian_KK;
    private javax.swing.JLabel bSearch_penggajian_KM;
    private javax.swing.JLabel bSearch_penggajian_KT;
    private javax.swing.JPanel bTHR;
    private javax.swing.JButton bTambah_Admin;
    private javax.swing.JButton bTambah_Jabatan;
    private javax.swing.JButton bTambah_KK;
    private javax.swing.JButton bTambah_KM;
    private javax.swing.JButton bTambah_KT;
    private javax.swing.JButton bTambah_THR_KK;
    private javax.swing.JButton bTambah_THR_KT;
    private javax.swing.JButton bTambah_penggajian_KK;
    private javax.swing.JButton bTambah_penggajian_KM;
    private javax.swing.JButton bTambah_penggajian_KT;
    private javax.swing.JButton bUbah_Jabatan;
    private javax.swing.JButton bUbah_KK;
    private javax.swing.JButton bUbah_KM;
    private javax.swing.JButton bUbah_KT;
    private javax.swing.JButton bUbah_THR_KK;
    private javax.swing.JButton bUbah_THR_KT;
    private javax.swing.JButton bUbah_penggajian_KK;
    private javax.swing.JButton bUbah_penggajian_KM;
    private javax.swing.JButton bUbah_penggajian_KT;
    private javax.swing.JPanel badmin;
    private javax.swing.JPanel bdashboard;
    private javax.swing.JPanel bjabatan;
    private javax.swing.JPanel bkaryawan;
    private javax.swing.JButton blogout;
    private javax.swing.JLabel borderFormKaryawan;
    private javax.swing.JLabel borderFormPenggajian;
    private javax.swing.JLabel borderFormTHR;
    private javax.swing.JLabel borderForm_Admin;
    private javax.swing.JLabel borderForm_User1;
    private javax.swing.JLabel borderJabatan_Jabatan;
    private javax.swing.JLabel borderKode_Jabatan_Jabatan;
    private javax.swing.JLabel borderalamat_Admin;
    private javax.swing.JLabel borderalamat_KK;
    private javax.swing.JLabel borderalamat_KM;
    private javax.swing.JLabel borderalamat_KT;
    private javax.swing.JLabel borderemail_Admin;
    private javax.swing.JLabel borderemail_KK;
    private javax.swing.JLabel borderemail_KM;
    private javax.swing.JLabel borderemail_KT;
    private javax.swing.JLabel bordergajipokok_Jabatan;
    private javax.swing.JLabel bordergajipokok_THR_KK;
    private javax.swing.JLabel bordergajipokok_THR_KT;
    private javax.swing.JLabel bordergajipokok_penggajian_KK;
    private javax.swing.JLabel bordergajipokok_penggajian_KM;
    private javax.swing.JLabel bordergajipokok_penggajian_KT1;
    private javax.swing.JLabel borderjabatan_KK;
    private javax.swing.JLabel borderjabatan_KM;
    private javax.swing.JLabel borderjabatan_KT;
    private javax.swing.JLabel borderjmlabsensi_penggajian_KK;
    private javax.swing.JLabel borderjmlabsensi_penggajian_KM;
    private javax.swing.JLabel borderjmlabsensi_penggajian_KT;
    private javax.swing.JLabel borderkodejabatan_THR_KK;
    private javax.swing.JLabel borderkodejabatan_THR_KT;
    private javax.swing.JLabel borderkodejabatan_penggajian_KK;
    private javax.swing.JLabel borderkodejabatan_penggajian_KM;
    private javax.swing.JLabel borderkodejabatan_penggajian_KT;
    private javax.swing.JLabel borderkodeslip_THR_KK;
    private javax.swing.JLabel borderkodeslip_THR_KT;
    private javax.swing.JLabel borderkodeslip_penggajian_KK;
    private javax.swing.JLabel borderkodeslip_penggajian_KM;
    private javax.swing.JLabel borderkodeslip_penggajian_KT;
    private javax.swing.JLabel bordernama_Admin;
    private javax.swing.JLabel bordernama_KK;
    private javax.swing.JLabel bordernama_KM;
    private javax.swing.JLabel bordernama_KT;
    private javax.swing.JLabel bordernama_THR_KK;
    private javax.swing.JLabel bordernama_THR_KT;
    private javax.swing.JLabel bordernama_penggajian_KK;
    private javax.swing.JLabel bordernama_penggajian_KM;
    private javax.swing.JLabel bordernama_penggajian_KT;
    private javax.swing.JLabel bordernik_KK;
    private javax.swing.JLabel bordernik_KM;
    private javax.swing.JLabel bordernik_KT;
    private javax.swing.JLabel bordernik_THR_KK;
    private javax.swing.JLabel bordernik_THR_KT;
    private javax.swing.JLabel bordernik_penggajian_KK;
    private javax.swing.JLabel bordernik_penggajian_KM;
    private javax.swing.JLabel bordernik_penggajian_KT;
    private javax.swing.JLabel bordernotelp_Admin;
    private javax.swing.JLabel bordernotelp_KK;
    private javax.swing.JLabel bordernotelp_KM;
    private javax.swing.JLabel bordernotelp_KT;
    private javax.swing.JLabel borderpassword_Admin;
    private javax.swing.JLabel bordersearch_Admin;
    private javax.swing.JLabel bordersearch_Jabatan;
    private javax.swing.JLabel bordersearch_KK;
    private javax.swing.JLabel bordersearch_KM;
    private javax.swing.JLabel bordersearch_KT;
    private javax.swing.JLabel bordersearch_THR_KK;
    private javax.swing.JLabel bordersearch_THR_KT;
    private javax.swing.JLabel bordersearch_penggajian_KK;
    private javax.swing.JLabel bordersearch_penggajian_KM;
    private javax.swing.JLabel bordersearch_penggajian_KT;
    private javax.swing.JLabel bordertglklr_KK;
    private javax.swing.JLabel bordertglklr_KM;
    private javax.swing.JLabel bordertglmsk_KK;
    private javax.swing.JLabel bordertglmsk_KM;
    private javax.swing.JLabel bordertglmsk_KT;
    private javax.swing.JLabel bordertglslip_THR_KK;
    private javax.swing.JLabel bordertglslip_THR_KT;
    private javax.swing.JLabel bordertglslip_penggajian_KK;
    private javax.swing.JLabel bordertglslip_penggajian_KM;
    private javax.swing.JLabel bordertglslip_penggajian_KT;
    private javax.swing.JLabel bordertotalgaji_penggajian_KK;
    private javax.swing.JLabel bordertotalgaji_penggajian_KM;
    private javax.swing.JLabel bordertotalgaji_penggajian_KT;
    private javax.swing.JLabel bordertotalthr_THR_KK;
    private javax.swing.JLabel bordertotalthr_THR_KT;
    private javax.swing.JLabel borderusername_Admin;
    private javax.swing.JPanel bpenggajian;
    private javax.swing.JComboBox<String> cbJabatan_KK;
    private javax.swing.JComboBox<String> cbJabatan_KM;
    private javax.swing.JComboBox<String> cbJabatan_KT;
    private javax.swing.JComboBox<String> cbKaryawan;
    private javax.swing.JComboBox<String> cbKode_Jabatan_Jabatan;
    private javax.swing.JComboBox<String> cbKodejabatan_THR_KK;
    private javax.swing.JComboBox<String> cbKodejabatan_THR_KT;
    private javax.swing.JComboBox<String> cbKodejabatan_penggajian_KK;
    private javax.swing.JComboBox<String> cbKodejabatan_penggajian_KM;
    private javax.swing.JComboBox<String> cbKodejabatan_penggajian_KT;
    private javax.swing.JComboBox<String> cbPenggajian;
    private javax.swing.JComboBox<String> cbTHR;
    private com.toedter.calendar.JDateChooser cbTglklr_KK;
    private com.toedter.calendar.JDateChooser cbTglklr_KM;
    private com.toedter.calendar.JDateChooser cbTglmsk_KK;
    private com.toedter.calendar.JDateChooser cbTglmsk_KM;
    private com.toedter.calendar.JDateChooser cbTglmsk_KT;
    private com.toedter.calendar.JDateChooser cbTglslip_THR_KK;
    private com.toedter.calendar.JDateChooser cbTglslip_THR_KT;
    private com.toedter.calendar.JDateChooser cbTglslip_penggajian_KK;
    private com.toedter.calendar.JDateChooser cbTglslip_penggajian_KM;
    private com.toedter.calendar.JDateChooser cbTglslip_penggajian_KT;
    private javax.swing.JPanel conAdmin;
    private javax.swing.JPanel conDashboard;
    private javax.swing.JPanel conJabatan;
    private javax.swing.JPanel conKaryawan;
    private javax.swing.JPanel conPenggajian;
    private javax.swing.JPanel conTHR;
    private javax.swing.JLabel dua;
    private javax.swing.JLabel empat;
    private javax.swing.JLabel enam;
    private javax.swing.JPanel garisMenu;
    private javax.swing.JPanel garis_Admin;
    private javax.swing.JPanel garis_Jabatan;
    private javax.swing.JPanel garis_KK;
    private javax.swing.JPanel garis_KM;
    private javax.swing.JPanel garis_KT;
    private javax.swing.JPanel garis_THR_KK;
    private javax.swing.JPanel garis_THR_KT;
    private javax.swing.JPanel garis_penggajian_KK;
    private javax.swing.JPanel garis_penggajian_KM;
    private javax.swing.JPanel garis_penggajian_KT;
    private javax.swing.JLabel iconAdmin;
    private javax.swing.JLabel iconDashboard;
    private javax.swing.JLabel iconGKK;
    private javax.swing.JLabel iconGKM;
    private javax.swing.JLabel iconGKT;
    private javax.swing.JLabel iconJabatan;
    private javax.swing.JLabel iconKK;
    private javax.swing.JLabel iconKM;
    private javax.swing.JLabel iconKT;
    private javax.swing.JLabel iconKaryawan;
    private javax.swing.JLabel iconPenggajian;
    private javax.swing.JLabel iconTHR;
    private javax.swing.JScrollPane jScrollPane1_KK;
    private javax.swing.JScrollPane jScrollPane1_KM;
    private javax.swing.JScrollPane jScrollPane1_KT;
    private javax.swing.JScrollPane jScrollPane1_THR_KK;
    private javax.swing.JScrollPane jScrollPane1_THR_KT;
    private javax.swing.JScrollPane jScrollPane1_penggajian_KK;
    private javax.swing.JScrollPane jScrollPane1_penggajian_KM;
    private javax.swing.JScrollPane jScrollPane1_penggajian_KT;
    private javax.swing.JScrollPane jScrollPane_Admin;
    private javax.swing.JScrollPane jScrollPane_Alamat;
    private javax.swing.JScrollPane jScrollPane_Jabatan;
    private javax.swing.JScrollPane jScrollPane_KK;
    private javax.swing.JScrollPane jScrollPane_KM;
    private javax.swing.JScrollPane jScrollPane_KT;
    private javax.swing.JPanel karyawanKontrak;
    private javax.swing.JPanel karyawanMagang;
    private javax.swing.JPanel karyawanTetap;
    private javax.swing.JLabel lAdministrator;
    private javax.swing.JLabel lDashboard;
    private javax.swing.JLabel lFormKaryawan;
    private javax.swing.JLabel lFormPenggajian;
    private javax.swing.JLabel lFormTHR;
    private javax.swing.JLabel lForm_Admin;
    private javax.swing.JLabel lForm_User1;
    private javax.swing.JLabel lJumlahGajiKaryawanKontrak;
    private javax.swing.JLabel lJumlahGajiKaryawanMagang;
    private javax.swing.JLabel lJumlahGajiKaryawanTetap;
    private javax.swing.JLabel lJumlahKaryawanKontrak;
    private javax.swing.JLabel lJumlahKaryawanMagang;
    private javax.swing.JLabel lJumlahKaryawanTetap;
    private javax.swing.JLabel lMenuAdmin;
    private javax.swing.JLabel lMenuDashboard;
    private javax.swing.JLabel lMenuJabatan;
    private javax.swing.JLabel lMenuKaryawan;
    private javax.swing.JLabel lMenuPenggajian;
    private javax.swing.JLabel lMenuTHR;
    private javax.swing.JLabel lTabelKaryawan;
    private javax.swing.JLabel lTabel_Admin;
    private javax.swing.JLabel lTabel_Jabatan;
    private javax.swing.JLabel lTelahDibayar;
    private javax.swing.JLabel lTelahDibayarKontrak;
    private javax.swing.JLabel lTelahDibayarMagang;
    private javax.swing.JLabel lalamat_Admin;
    private javax.swing.JLabel lalamat_KK;
    private javax.swing.JLabel lalamat_KM;
    private javax.swing.JLabel lalamat_KT;
    private javax.swing.JLabel lemail_Admin;
    private javax.swing.JLabel lemail_KK;
    private javax.swing.JLabel lemail_KM;
    private javax.swing.JLabel lemail_KT;
    private javax.swing.JLabel lgajipokok_Jabatan;
    private javax.swing.JLabel lgajipokok_THR_KK;
    private javax.swing.JLabel lgajipokok_THR_KT;
    private javax.swing.JLabel lgajipokok_penggajian_KK;
    private javax.swing.JLabel lgajipokok_penggajian_KM;
    private javax.swing.JLabel lgajipokok_penggajian_KT1;
    private javax.swing.JLabel lima;
    private javax.swing.JLabel ljabatan_Jabatan;
    private javax.swing.JLabel ljabatan_KK;
    private javax.swing.JLabel ljabatan_KM;
    private javax.swing.JLabel ljabatan_KT;
    private javax.swing.JLabel ljmlabsensi_penggajian_KK;
    private javax.swing.JLabel ljmlabsensi_penggajian_KM;
    private javax.swing.JLabel ljmlabsensi_penggajian_KT;
    private javax.swing.JLabel lkodejabatan_Jabatan;
    private javax.swing.JLabel lkodejabatan_THR_KK;
    private javax.swing.JLabel lkodejabatan_THR_KT;
    private javax.swing.JLabel lkodejabatan_penggajian_KK;
    private javax.swing.JLabel lkodejabatan_penggajian_KM;
    private javax.swing.JLabel lkodejabatan_penggajian_KT;
    private javax.swing.JLabel lkodeslip_THR_KK;
    private javax.swing.JLabel lkodeslip_THR_KT;
    private javax.swing.JLabel lkodeslip_penggajian_KM;
    private javax.swing.JLabel lkodeslip_penggajian_KT;
    private javax.swing.JLabel lkodeslip_penggajian_KT1;
    private javax.swing.JLabel lnama_Admin;
    private javax.swing.JLabel lnama_KK;
    private javax.swing.JLabel lnama_KM;
    private javax.swing.JLabel lnama_KT;
    private javax.swing.JLabel lnama_THR_KK;
    private javax.swing.JLabel lnama_THR_KT;
    private javax.swing.JLabel lnama_penggajian_KK;
    private javax.swing.JLabel lnama_penggajian_KM;
    private javax.swing.JLabel lnama_penggajian_KT;
    private javax.swing.JLabel lnik_KK;
    private javax.swing.JLabel lnik_KK1;
    private javax.swing.JLabel lnik_KT;
    private javax.swing.JLabel lnik_THR_KK;
    private javax.swing.JLabel lnik_THR_KT;
    private javax.swing.JLabel lnik_penggajian_KK;
    private javax.swing.JLabel lnik_penggajian_KM;
    private javax.swing.JLabel lnik_penggajian_KT;
    private javax.swing.JLabel lnotelp_Admin;
    private javax.swing.JLabel lnotelp_KK;
    private javax.swing.JLabel lnotelp_KM;
    private javax.swing.JLabel lnotelp_KT;
    private javax.swing.JLabel lpassword_Admin;
    private javax.swing.JLabel ltglklr_KK;
    private javax.swing.JLabel ltglklr_KM;
    private javax.swing.JLabel ltglmsk_KK;
    private javax.swing.JLabel ltglmsk_KM;
    private javax.swing.JLabel ltglmsk_KT;
    private javax.swing.JLabel ltglslip_THR_KK;
    private javax.swing.JLabel ltglslip_THR_KT;
    private javax.swing.JLabel ltglslip_penggajian_KK;
    private javax.swing.JLabel ltglslip_penggajian_KM;
    private javax.swing.JLabel ltglslip_penggajian_KT;
    private javax.swing.JLabel ltotalgaji_penggajian_KK;
    private javax.swing.JLabel ltotalgaji_penggajian_KM;
    private javax.swing.JLabel ltotalgaji_penggajian_KT;
    private javax.swing.JLabel ltotalthr_THR_KK;
    private javax.swing.JLabel ltotalthr_THR_KT;
    private javax.swing.JLabel lusername_Admin;
    private javax.swing.JLabel lvalueGKK;
    private javax.swing.JLabel lvalueGKM;
    private javax.swing.JLabel lvalueGKT;
    private javax.swing.JLabel lvalueKK;
    private javax.swing.JLabel lvalueKM;
    private javax.swing.JLabel lvalueKT;
    private javax.swing.JLabel nmprofile;
    private javax.swing.JPanel panelContent;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelProfile;
    private javax.swing.JPanel penggajianKontrak;
    private javax.swing.JPanel penggajianMagang;
    private javax.swing.JPanel penggajianTetap;
    private javax.swing.JLabel satu;
    private javax.swing.JTable tbAdmin;
    private javax.swing.JTable tbJabatan;
    private javax.swing.JTable tbKK;
    private javax.swing.JTable tbKM;
    private javax.swing.JTable tbKT;
    private javax.swing.JTable tbPenggajianKK;
    private javax.swing.JTable tbPenggajianKM;
    private javax.swing.JTable tbPenggajianKT;
    private javax.swing.JTable tbTHRKK;
    private javax.swing.JTable tbTHRKT;
    private javax.swing.JLabel tiga;
    private javax.swing.JTextArea txtAlamat_Admin;
    private javax.swing.JTextArea txtAlamat_KK;
    private javax.swing.JTextArea txtAlamat_KM;
    private javax.swing.JTextArea txtAlamat_KT;
    private javax.swing.JTextField txtEmail_Admin;
    private javax.swing.JTextField txtEmail_KK;
    private javax.swing.JTextField txtEmail_KM;
    private javax.swing.JTextField txtEmail_KT;
    private javax.swing.JTextField txtGajipokok;
    private javax.swing.JTextField txtGajipokok_THR_KK;
    private javax.swing.JTextField txtGajipokok_THR_KT;
    private javax.swing.JTextField txtGajipokok_penggajian_KK;
    private javax.swing.JTextField txtGajipokok_penggajian_KM;
    private javax.swing.JTextField txtGajipokok_penggajian_KT;
    private javax.swing.JTextField txtJabatan;
    private javax.swing.JTextField txtJmlabsensi_penggajian_KK;
    private javax.swing.JTextField txtJmlabsensi_penggajian_KM;
    private javax.swing.JTextField txtJmlabsensi_penggajian_KT;
    private javax.swing.JTextField txtKodeslip_THR_KK;
    private javax.swing.JTextField txtKodeslip_THR_KT;
    private javax.swing.JTextField txtKodeslip_penggajian_KK;
    private javax.swing.JTextField txtKodeslip_penggajian_KM;
    private javax.swing.JTextField txtKodeslip_penggajian_KT;
    private javax.swing.JTextField txtNama_Admin;
    private javax.swing.JTextField txtNama_KK;
    private javax.swing.JTextField txtNama_KM;
    private javax.swing.JTextField txtNama_KT;
    private javax.swing.JTextField txtNama_THR_KK;
    private javax.swing.JTextField txtNama_THR_KT;
    private javax.swing.JTextField txtNama_penggajian_KK;
    private javax.swing.JTextField txtNama_penggajian_KM;
    private javax.swing.JTextField txtNama_penggajian_KT;
    private javax.swing.JTextField txtNik_KK;
    private javax.swing.JTextField txtNik_KM;
    private javax.swing.JTextField txtNik_KT;
    private javax.swing.JTextField txtNik_THR_KK;
    private javax.swing.JTextField txtNik_THR_KT;
    private javax.swing.JTextField txtNik_penggajian_KK;
    private javax.swing.JTextField txtNik_penggajian_KM;
    private javax.swing.JTextField txtNik_penggajian_KT;
    private javax.swing.JTextField txtNotelp_Admin;
    private javax.swing.JTextField txtNotelp_KK;
    private javax.swing.JTextField txtNotelp_KM;
    private javax.swing.JTextField txtNotelp_KT;
    private javax.swing.JPasswordField txtPassword_Admin;
    private javax.swing.JTextField txtSearch_Admin;
    private javax.swing.JTextField txtSearch_Jabatan;
    private javax.swing.JTextField txtSearch_KK;
    private javax.swing.JTextField txtSearch_KM;
    private javax.swing.JTextField txtSearch_KT;
    private javax.swing.JTextField txtSearch_THR_KK;
    private javax.swing.JTextField txtSearch_THR_KT;
    private javax.swing.JTextField txtSearch_penggajian_KK;
    private javax.swing.JTextField txtSearch_penggajian_KM;
    private javax.swing.JTextField txtSearch_penggajian_KT;
    private javax.swing.JTextField txtTotalgaji_penggajian_KK;
    private javax.swing.JTextField txtTotalgaji_penggajian_KM;
    private javax.swing.JTextField txtTotalgaji_penggajian_KT;
    private javax.swing.JTextField txtTotalthr_THR_KK;
    private javax.swing.JTextField txtTotalthr_THR_KT;
    private javax.swing.JTextField txtUsername_Admin;
    // End of variables declaration//GEN-END:variables
}