
package admin;

import Koneksi.connect;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/*
    @author Llawl, Wahyu Hadi
 */
public class popupKaryawanMagang extends javax.swing.JFrame {
    private DefaultTableModel tabmode;
    private static Connection conn = new connect().configDB();
    public Admin KM = null;
    public void DataKM(){
        Object [] Baris ={"NIK", "Nama", "Email", "No Telepon", "Alamat", "Kode Jabatan", "Tanggal Masuk", "Tanggal Selesai Magang"};
        tabmode = new DefaultTableModel(null, Baris);
        String Cari = txtSearch.getText();
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
                    Result.getDate(8),});     
                }tbKaryawanMagang.setModel(tabmode);
            }
        catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
    }
    public popupKaryawanMagang() {
        initComponents();
        DataKM();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        conKaryawan = new javax.swing.JPanel();
        ltabelkaryawanmagang = new javax.swing.JLabel();
        jScrollPane = new javax.swing.JScrollPane();
        tbKaryawanMagang = new javax.swing.JTable();
        bSearch = new javax.swing.JLabel();
        bordersearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        conKaryawan.setBackground(new java.awt.Color(255, 255, 255));
        conKaryawan.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ltabelkaryawanmagang.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 38)); // NOI18N
        ltabelkaryawanmagang.setForeground(new java.awt.Color(135, 33, 36));
        ltabelkaryawanmagang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ltabelkaryawanmagang.setText("Tabel Karyawan Magang");
        conKaryawan.add(ltabelkaryawanmagang, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 26, -1, -1));

        tbKaryawanMagang.setModel(new javax.swing.table.DefaultTableModel(
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
        tbKaryawanMagang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbKaryawanMagangMouseClicked(evt);
            }
        });
        jScrollPane.setViewportView(tbKaryawanMagang);

        conKaryawan.add(jScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 102, 1076, 436));

        bSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        conKaryawan.add(bSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(1051, 26, 45, 50));

        bordersearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-search.png"))); // NOI18N
        conKaryawan.add(bordersearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(767, 26, -1, -1));

        txtSearch.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        txtSearch.setBorder(null);
        conKaryawan.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(775, 28, 270, 47));

        getContentPane().add(conKaryawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1121, 556));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tbKaryawanMagangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKaryawanMagangMouseClicked
        int row = tbKaryawanMagang.getSelectedRow();
        KM.nik = tbKaryawanMagang.getValueAt(row, 0).toString();
        KM.nama = tbKaryawanMagang.getValueAt(row, 1).toString();
        KM.jabatan = tbKaryawanMagang.getValueAt(row, 5).toString();
        try{
            String SQL = "SELECT * FROM jabatan where Kode_Jabatan='"+KM.jabatan+"'";
            Statement State = conn.createStatement();
            ResultSet Result = State.executeQuery(SQL);
            while (Result.next()) {
                KM.gapok = Result.getInt("Gaji_Pokok");
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(this, "data gagal dipanggil"+yusuf);
        }
        KM.DataKMTerpilih();
        
        this.dispose();
    }//GEN-LAST:event_tbKaryawanMagangMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(popupKaryawanMagang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(popupKaryawanMagang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(popupKaryawanMagang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(popupKaryawanMagang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new popupKaryawanMagang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bSearch;
    private javax.swing.JLabel bordersearch;
    private javax.swing.JPanel conKaryawan;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JLabel ltabelkaryawanmagang;
    private javax.swing.JTable tbKaryawanMagang;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
