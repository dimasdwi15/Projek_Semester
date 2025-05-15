/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.form;

import com.app.main.FormMenuUtama;
import com.formdev.flatlaf.FlatClientProperties;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.awt.Color;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author LENOVO
 */
public class FormRegister extends javax.swing.JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/rcollection";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    /**
     * Creates new form FormRegister
     */
    public FormRegister() {
      initComponents();
        setLayoutForm();
        initializeComboBoxes();
        generateNewId();
        txt_id.setEditable(false);
    }
    
     private void setLayoutForm () {
       txt_nama.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
       txt_nama.putClientProperty("JTextField.placeholderText", "Masukkan nama ");
       txt_alamat.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
       txt_alamat.putClientProperty("JTextField.placeholderText", "Masukkan alamat ");
       txt_id.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
       txt_id.putClientProperty("JTextField.placeholderText", "Masukkan id ");
       txt_username.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
       txt_username.putClientProperty("JTextField.placeholderText", "Masukkan username Anda");
       // untuk password
        txt_pass.putClientProperty(FlatClientProperties.STYLE, ""
        + "arc:20;"
        + "showRevealButton:true;"
        + "showCapsLock:true;");
        txt_pass.putClientProperty("JTextField.placeholderText", "Masukkan password Anda");
         cmb_level.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
          cmb_sex.putClientProperty(FlatClientProperties.STYLE, "arc:20;");
         
    }
      private java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
     

    
    private void generateNewId() {
        try (Connection conn = (Connection) getConnection();
             Statement st = (Statement) conn.createStatement()) {
            
            String sql = "SELECT COALESCE(MAX(CAST(RIGHT(id, 3) AS UNSIGNED)), 0) + 1 FROM tb_user";
            ResultSet rs = st.executeQuery(sql);
            
            if (rs.next()) {
                int angka = rs.getInt(1);
                String no = String.format("%03d", angka);
                txt_id.setText("A" + no);
            } else {
                txt_id.setText("A001");
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Terjadi Kesalahan: " + e.getMessage());
        }
    }
    
    /**
     * Menyimpan data pengguna baru
     */
   private boolean simpanData() {
    String id = txt_id.getText();
    String nama = txt_nama.getText();
    String username = txt_username.getText();
    String password = new String(txt_pass.getPassword());
    String cjenis = cmb_sex.getSelectedItem().toString();
    String alamat = txt_alamat.getText();
    String level = cmb_level.getSelectedItem().toString();
    
    // Validasi kolom
    if (id.isEmpty() || nama.isEmpty() || username.isEmpty() || password.isEmpty() || alamat.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!", 
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    
    // Validasi combo box
    if (cjenis.equals("Pilih Jenis Kelamin")) {
        JOptionPane.showMessageDialog(this, "Silakan pilih jenis kelamin!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return false;
    }
    
    if (level.equals("Pilih Level")) {
        JOptionPane.showMessageDialog(this, "Silakan pilih level pengguna!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return false;
    }

    try (Connection conn = (Connection) getConnection()) {
        // Cek username duplikat
        String checkSql = "SELECT COUNT(*) FROM tb_user WHERE username = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Username sudah digunakan. Silakan pilih username lain.",
                        "Peringatan", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        
        // Tambahkan pengguna baru
        String sql = "INSERT INTO tb_user (id, nama, username, password, cjenis, alamat, level) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, nama);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            pstmt.setString(5, cjenis);
            pstmt.setString(6, alamat);
            pstmt.setString(7, level);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Registrasi berhasil! Pengguna baru telah ditambahkan.");
                resetForm();
                generateNewId();
                return true;
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
    return false;
}

    private void initializeComboBoxes() {
        // Jenis kelamin
        cmb_sex.removeAllItems();
        cmb_sex.addItem("Pilih Jenis Kelamin");
        cmb_sex.addItem("L");
        cmb_sex.addItem("P");

        // Level pengguna
        cmb_level.removeAllItems();
        cmb_level.addItem("Pilih Level");
        cmb_level.addItem("admin");
        cmb_level.addItem("kasir");
    }
    
    // Reset form setelah simpan atau batal
    private void resetForm() {
        txt_nama.setText("");
        txt_username.setText("");
        txt_pass.setText("");
        txt_alamat.setText("");
        cmb_sex.setSelectedIndex(0);
        cmb_level.setSelectedIndex(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_alamat = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_username = new javax.swing.JTextField();
        txt_id = new javax.swing.JTextField();
        cmb_sex = new javax.swing.JComboBox<>();
        cmb_level = new javax.swing.JComboBox<>();
        txt_pass = new javax.swing.JPasswordField();
        btn_daftar = new javax.swing.JButton();
        lbl_login = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, 170, 30));
        jPanel1.add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 140, 30));
        jPanel1.add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 140, 30));

        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });
        jPanel1.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 230, 140, 30));

        cmb_sex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmb_sex, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 140, 30));

        cmb_level.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel1.add(cmb_level, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 280, 140, 30));

        txt_pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_passActionPerformed(evt);
            }
        });
        jPanel1.add(txt_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 330, 140, 30));

        btn_daftar.setBackground(new java.awt.Color(177, 131, 40));
        btn_daftar.setForeground(new java.awt.Color(255, 255, 255));
        btn_daftar.setText("DAFTAR");
        btn_daftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_daftarActionPerformed(evt);
            }
        });
        jPanel1.add(btn_daftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 420, 120, -1));

        lbl_login.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lbl_login.setForeground(new java.awt.Color(0, 0, 0));
        lbl_login.setText("Login");
        lbl_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_loginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_loginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_loginMouseExited(evt);
            }
        });
        jPanel1.add(lbl_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 430, 60, 50));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/app/icon/DAFTAR_750x512.png"))); // NOI18N
        jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_passActionPerformed

    private void btn_daftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_daftarActionPerformed
        boolean berhasil = simpanData();
    if (berhasil) {
        resetForm();
        FormMenuUtama.logout();
    }
    }//GEN-LAST:event_btn_daftarActionPerformed

    private void lbl_loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_loginMouseClicked
        // TODO add your handling code here:
        FormMenuUtama.logout();
  
    }//GEN-LAST:event_lbl_loginMouseClicked

    private void lbl_loginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_loginMouseEntered
        // TODO add your handling code here:
        lbl_login.setForeground(Color.blue);
     
    }//GEN-LAST:event_lbl_loginMouseEntered

    private void lbl_loginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_loginMouseExited
        // TODO add your handling code here:
        lbl_login.setForeground(Color.black);
    }//GEN-LAST:event_lbl_loginMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btn_daftar;
    private javax.swing.JComboBox<String> cmb_level;
    private javax.swing.JComboBox<String> cmb_sex;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_login;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JPasswordField txt_pass;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
