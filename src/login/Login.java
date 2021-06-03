package login;

import Data.Data_Login;
import static Data.Data_Login.encrypt;
import Data.Data_Session;
import Koneksi.connect;
import admin.Admin;
import java.awt.Color;
import java.sql.*;
import javax.swing.JOptionPane;
/*
    @author Llawl, Wahyu Hadi
 */         
public class Login extends javax.swing.JFrame {
    private static Connection conn = new connect().configDB();
    public Login() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lJudul1 = new javax.swing.JLabel();
        lJudul2 = new javax.swing.JLabel();
        lJudul3 = new javax.swing.JLabel();
        user_icon = new javax.swing.JLabel();
        border_user = new javax.swing.JLabel();
        pass_icon = new javax.swing.JLabel();
        border_pass = new javax.swing.JLabel();
        llogin = new javax.swing.JLabel();
        txtNik = new javax.swing.JTextField();
        lpassword = new javax.swing.JLabel();
        txtpassword = new javax.swing.JPasswordField();
        bLogin = new javax.swing.JButton();
        bExit = new javax.swing.JLabel();
        pLogin = new javax.swing.JPanel();
        pCover = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lJudul1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 50)); // NOI18N
        lJudul1.setForeground(new java.awt.Color(255, 213, 3));
        lJudul1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lJudul1.setText("Sistem Penggajian");
        getContentPane().add(lJudul1, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 105, 796, 70));

        lJudul2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 50)); // NOI18N
        lJudul2.setForeground(new java.awt.Color(255, 213, 3));
        lJudul2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lJudul2.setText("Karyawan");
        getContentPane().add(lJudul2, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 168, 796, 70));

        lJudul3.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 42)); // NOI18N
        lJudul3.setForeground(new java.awt.Color(255, 255, 255));
        lJudul3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lJudul3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/logo-anabatic-lg 1.png"))); // NOI18N
        getContentPane().add(lJudul3, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 215, 796, 500));

        user_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-user.png"))); // NOI18N
        getContentPane().add(user_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 283, -1, -1));

        border_user.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Outline-login.png"))); // NOI18N
        getContentPane().add(border_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 259, -1, -1));

        pass_icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Icon-password.png"))); // NOI18N
        getContentPane().add(pass_icon, new org.netbeans.lib.awtextra.AbsoluteConstraints(71, 414, -1, -1));

        border_pass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Outline-login.png"))); // NOI18N
        getContentPane().add(border_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 388, -1, -1));

        llogin.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 50)); // NOI18N
        llogin.setForeground(new java.awt.Color(207, 36, 42));
        llogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        llogin.setText("Login");
        getContentPane().add(llogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 126, 567, -1));

        txtNik.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 26)); // NOI18N
        txtNik.setForeground(new java.awt.Color(153, 153, 153));
        txtNik.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNik.setText("NIK");
        txtNik.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtNik.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNikFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNikFocusLost(evt);
            }
        });
        getContentPane().add(txtNik, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 265, 395, 69));

        lpassword.setFont(new java.awt.Font("Arial Rounded MT Bold", 0, 26)); // NOI18N
        lpassword.setForeground(new java.awt.Color(153, 153, 153));
        lpassword.setText("Password");
        getContentPane().add(lpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 394, 395, 69));

        txtpassword.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 26)); // NOI18N
        txtpassword.setForeground(new java.awt.Color(56, 74, 108));
        txtpassword.setToolTipText("");
        txtpassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtpassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtpasswordFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtpasswordFocusLost(evt);
            }
        });
        getContentPane().add(txtpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 394, 395, 69));

        bLogin.setBackground(new java.awt.Color(92, 23, 25));
        bLogin.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 36)); // NOI18N
        bLogin.setForeground(new java.awt.Color(255, 255, 255));
        bLogin.setText("Login");
        bLogin.setBorder(null);
        bLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bLoginActionPerformed(evt);
            }
        });
        getContentPane().add(bLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 538, 468, 110));

        bExit.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        bExit.setForeground(new java.awt.Color(255, 255, 255));
        bExit.setText("X");
        bExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        bExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bExitMouseClicked(evt);
            }
        });
        getContentPane().add(bExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1330, 5, -1, -1));
        bExit.getAccessibleContext().setAccessibleDescription("");

        pLogin.setBackground(new java.awt.Color(255, 255, 255));
        pLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(pLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 567, 768));

        pCover.setBackground(new java.awt.Color(135, 33, 36));
        pCover.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(pCover, new org.netbeans.lib.awtextra.AbsoluteConstraints(569, 0, 796, 768));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_bExitMouseClicked

    private void txtNikFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNikFocusGained
        // TODO add your handling code here:
        if(txtNik.getText().equals("NIK")){
            txtNik.setText("");
            txtNik.setForeground(new Color(0,0,0));
        }
    }//GEN-LAST:event_txtNikFocusGained

    private void txtNikFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNikFocusLost
        if(txtNik.getText().equals("")){
            txtNik.setText("NIK");
            txtNik.setForeground(new Color(183,183,183));
        }
    }//GEN-LAST:event_txtNikFocusLost

    private void txtpasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtpasswordFocusGained
        lpassword.setVisible(false);
        if(txtpassword.getText().equals("")){
            txtpassword.setText("");
            txtpassword.setForeground(new Color(0,0,0));
        }
    }//GEN-LAST:event_txtpasswordFocusGained

    private void txtpasswordFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtpasswordFocusLost
        if(txtpassword.getText().equals("")){
            txtpassword.setText("");
            txtpassword.setForeground(new Color(183,183,183));
            lpassword.setVisible(true);
        }
    }//GEN-LAST:event_txtpasswordFocusLost

    private void bLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bLoginActionPerformed
        String SQL = "Select * from admin where Username='"+txtNik.getText()+"' or Password='"+String.valueOf(encrypt(encrypt(txtpassword.getText())))+"'";
        try{
            PreparedStatement state = conn.prepareStatement(SQL);
            ResultSet Result = state.executeQuery(SQL);
            if(Result.next()){
                if(txtNik.getText().equals(Result.getString("Username"))&&String.valueOf(encrypt(encrypt(txtpassword.getText()))).equals(Result.getString("Password"))){
                    Data_Session.setUsername(Result.getString("Username"));
                    JOptionPane.showMessageDialog(null, "\tLogin Sukses \nSelamat Datang "+Result.getString("Nama_Admin"));                    
                    new Admin().setVisible(true);
                    this.dispose();
                }else if(!txtNik.getText().equals(Result.getString("Username"))&&String.valueOf(encrypt(encrypt(txtpassword.getText()))).equals(Result.getString("Password"))){
                    JOptionPane.showMessageDialog(null, "Username Salah!", "Error!", JOptionPane.ERROR_MESSAGE);                    
                }else if(!encrypt(encrypt(txtpassword.getText())).equals(Result.getString("Password"))&&txtNik.getText().equals(Result.getString("Username"))){
                    JOptionPane.showMessageDialog(null, "Password Salah!", "Error!", JOptionPane.ERROR_MESSAGE);                   
                }
            }else{
                JOptionPane.showMessageDialog(null, "Username & Password Salah!", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }catch(SQLException yusuf){
            JOptionPane.showMessageDialog(null, "Error "+yusuf);
        }
    }//GEN-LAST:event_bLoginActionPerformed
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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bExit;
    private javax.swing.JButton bLogin;
    private javax.swing.JLabel border_pass;
    private javax.swing.JLabel border_user;
    private javax.swing.JLabel lJudul1;
    private javax.swing.JLabel lJudul2;
    private javax.swing.JLabel lJudul3;
    private javax.swing.JLabel llogin;
    private javax.swing.JLabel lpassword;
    private javax.swing.JPanel pCover;
    private javax.swing.JPanel pLogin;
    private javax.swing.JLabel pass_icon;
    private javax.swing.JTextField txtNik;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JLabel user_icon;
    // End of variables declaration//GEN-END:variables
}
