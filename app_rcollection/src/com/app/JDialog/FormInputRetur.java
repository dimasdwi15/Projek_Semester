/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package com.app.JDialog;
import com.app.config.koneksi;
import com.app.form.FormMember;
import com.app.form.FormSupplier;
import com.app.form.FormRetur;
import javax.swing.*;
import java.awt.*;
import java.sql.*;
import com.toedter.calendar.JDateChooser;
import java.util.Date;


import com.toedter.calendar.JDateChooser;
import java.util.HashMap;
/**
 *
 * @author LENOVO
 */
public class FormInputRetur extends javax.swing.JDialog {
    HashMap<String, String> mapKodeBarang = new HashMap<>();
    private static final String DB_URL = "jdbc:mysql://localhost:3306/rcollection";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
      private FormSupplier.Supplier supplierToEdit;
      private boolean isEditMode;
      
       public FormInputRetur (FormRetur.Retur retur) {
           
           initComponents();
           autoIdRetur();
           AlasanRetur();
           isiKodeBarang();
           txtNoTransaksi.setText("TR-");
    }
    
        private void autoIdRetur() {
    try {
        Connection con = koneksi.getKoneksi();
        Statement stat = con.createStatement();
        String sql = "SELECT MAX(RIGHT(id_retur, 6)) AS no FROM retur_penjualan";
        ResultSet res = stat.executeQuery(sql);

        if (res.next()) {
            if (res.getString("no") == null) {
                txtNoRetur.setText("RT-000001"); // ID pertama
            } else {
                int aut_id = Integer.parseInt(res.getString("no")) + 1;
                String no = String.format("%06d", aut_id); // tambahkan nol di depan
                txtNoRetur.setText("RT-" + no);
            }
        }

        res.close();
        stat.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Terjadi Kesalahan: " + e.getMessage());
    }
}
        
    private void AlasanRetur() {
        cbAlasanRetur.addItem("Pilih Alasan");
        cbAlasanRetur.addItem("Salah ukuran");
        cbAlasanRetur.addItem("Barang Rusak");
    }

        private void isiKodeBarang() {
        try {
        Connection conn = koneksi.getKoneksi();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT kode_barang, nama_barang FROM data_barang");

        cbBarang.removeAllItems(); // bersihkan dulu jika sebelumnya ada
        cbBarang.addItem("Pilih Barang");
        while (rs.next()) {
            String kode = rs.getString("kode_barang");
            String nama = rs.getString("nama_barang");
            cbBarang.addItem(nama); // tampilkan nama
            mapKodeBarang.put(nama, kode); // simpan nama->kode
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal memuat data barang: " + e.getMessage());
    }
}

   
   
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        btn_batal = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNoRetur = new javax.swing.JLabel();
        txtNoTransaksi = new javax.swing.JTextField();
        cbBarang = new javax.swing.JComboBox<>();
        txtKuantitas = new javax.swing.JTextField();
        cbAlasanRetur = new javax.swing.JComboBox<>();
        btnHitungRugi = new javax.swing.JButton();
        btnSimpanRetur = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        lblTotalRugi = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btn_batal.setBackground(new java.awt.Color(125, 99, 41));
        btn_batal.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("NO RETUR");

        jLabel4.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("NO TRANSAKSI");

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("KODE BARANG");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("ALASAN");

        jPanel2.setBackground(new java.awt.Color(125, 99, 41));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("RETUR");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Penjualan");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        txtNoRetur.setText("jLabel8");

        txtNoTransaksi.setText("TR-");
        txtNoTransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoTransaksiActionPerformed(evt);
            }
        });

        cbBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txtKuantitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKuantitasActionPerformed(evt);
            }
        });

        btnHitungRugi.setBackground(new java.awt.Color(125, 99, 41));
        btnHitungRugi.setForeground(new java.awt.Color(255, 255, 255));
        btnHitungRugi.setText("Hitung");
        btnHitungRugi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHitungRugiActionPerformed(evt);
            }
        });

        btnSimpanRetur.setBackground(new java.awt.Color(125, 99, 41));
        btnSimpanRetur.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanRetur.setText("Simpan");
        btnSimpanRetur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanReturActionPerformed(evt);
            }
        });

        jLabel8.setText("TOTAL RUGI");

        jLabel9.setText("JUMLAH");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addGap(164, 164, 164))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)))
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNoRetur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtKuantitas)
                                        .addComponent(txtNoTransaksi)
                                        .addComponent(cbAlasanRetur, 0, 152, Short.MAX_VALUE))
                                    .addComponent(cbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 6, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnHitungRugi)
                        .addGap(34, 34, 34)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSimpanRetur)
                        .addGap(30, 30, 30)
                        .addComponent(btn_batal))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel8)
                        .addComponent(lblTotalRugi, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotalRugi, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(288, 288, 288))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(txtNoRetur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                            .addComponent(txtNoTransaksi))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtKuantitas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbAlasanRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnHitungRugi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnSimpanRetur, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_batal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
      int konfirmasi = JOptionPane.showConfirmDialog(
            this, 
            "Apakah Anda yakin ingin membatalkan input?", 
            "Konfirmasi Pembatalan", 
            JOptionPane.YES_NO_OPTION
        );

        if (konfirmasi == JOptionPane.YES_OPTION) {
            // Lakukan pembatalan, misalnya tutup form
            dispose(); // Jika ini di JFrame atau JDialog
        }
    }//GEN-LAST:event_btn_batalActionPerformed

    private void txtNoTransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoTransaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoTransaksiActionPerformed

    private void txtKuantitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKuantitasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtKuantitasActionPerformed

    private void btnHitungRugiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHitungRugiActionPerformed
        // TODO add your handling code here:
        
        try {
            String namaBarang = cbBarang.getSelectedItem().toString();
            String kodeBarang = mapKodeBarang.get(namaBarang); // ambil kode berdasarkan nama

            int qty = Integer.parseInt(txtKuantitas.getText());

            Connection conn = koneksi.getKoneksi();
            String sql = "SELECT harga_jual FROM data_barang WHERE kode_barang = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, kodeBarang);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                int hargaJual = rs.getInt("harga_jual");
                int totalRugi = hargaJual * qty;
                lblTotalRugi.setText(String.valueOf(totalRugi));
            } else {
                JOptionPane.showMessageDialog(this, "Barang tidak ditemukan di database.");
            }

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Jumlah kuantitas tidak valid.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }//GEN-LAST:event_btnHitungRugiActionPerformed

    private void btnSimpanReturActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanReturActionPerformed
        // TODO add your handling code here:
        try {
            Connection conn = koneksi.getKoneksi();
            conn.setAutoCommit(false); // mulai transaksi

            // Ambil input
            String idRetur = txtNoRetur.getText();
            String noTransaksi = txtNoTransaksi.getText();
            String namaBarang = cbBarang.getSelectedItem().toString();
            String kodeBarang = mapKodeBarang.get(namaBarang);
            int kuantitas = Integer.parseInt(txtKuantitas.getText());
            String alasan = cbAlasanRetur.getSelectedItem().toString();
            int totalRugi = Integer.parseInt(lblTotalRugi.getText());

            // Logika update stok berdasarkan alasan
            String updateStokSql;
            if (alasan.equalsIgnoreCase("Barang Rusak")) {
                updateStokSql = "UPDATE data_barang SET stok_retur = stok_retur + ? WHERE kode_barang = ?";
            } else {
                updateStokSql = "UPDATE data_barang SET stok = stok + ? WHERE kode_barang = ?";
            }

            PreparedStatement pstUpdate = conn.prepareStatement(updateStokSql);
            pstUpdate.setInt(1, kuantitas);
            pstUpdate.setString(2, kodeBarang);
            int updateResult = pstUpdate.executeUpdate();

            if (updateResult == 0) {
                conn.rollback();
                JOptionPane.showMessageDialog(this, "Gagal update stok. Retur tidak disimpan.");
                return;
            }

            // Simpan retur jika update berhasil
            String insertRetur = "INSERT INTO retur_penjualan VALUES (?, ?, ?, NOW(), ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(insertRetur);
            pst.setString(1, idRetur);
            pst.setString(2, noTransaksi);
            pst.setString(3, kodeBarang);
            pst.setInt(4, kuantitas);
            pst.setString(5, alasan);
            pst.setInt(6, totalRugi);
            pst.executeUpdate();

            conn.commit(); // semua berhasil
            JOptionPane.showMessageDialog(this, "Data retur berhasil disimpan dan stok diperbarui!");

        } catch (Exception e) {
            try {
                koneksi.getKoneksi().rollback();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Rollback gagal: " + ex.getMessage());
            }
            JOptionPane.showMessageDialog(this, "Gagal simpan retur: " + e.getMessage());
        }
    }//GEN-LAST:event_btnSimpanReturActionPerformed

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
            java.util.logging.Logger.getLogger(FormInputRetur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormInputRetur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormInputRetur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormInputRetur.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormInputRetur dialog = new FormInputRetur(null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHitungRugi;
    private javax.swing.JButton btnSimpanRetur;
    private javax.swing.JButton btn_batal;
    private javax.swing.JComboBox<String> cbAlasanRetur;
    private javax.swing.JComboBox<String> cbBarang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotalRugi;
    private javax.swing.JTextField txtKuantitas;
    private javax.swing.JLabel txtNoRetur;
    private javax.swing.JTextField txtNoTransaksi;
    // End of variables declaration//GEN-END:variables
}
