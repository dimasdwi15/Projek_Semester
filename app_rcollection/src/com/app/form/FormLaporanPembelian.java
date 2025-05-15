/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.form;

import com.app.JDialog.FormInputBarang;

import com.app.dao.BarangDAO;
import com.app.model.ModelBarang;
import com.app.service.ServiceBarang;
import com.app.tablemodel.TableModelBarang;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;



/**
 *
 * @author LENOVO
 */
public class FormLaporanPembelian extends javax.swing.JPanel {
   Connection conn;
    Statement st;
    ResultSet rs;
    
    
    public FormLaporanPembelian() {
        initComponents();
        kosongkanTabel();
    }
    
    
      private void kosongkanTabel() {
        DefaultTableModel model = (DefaultTableModel) tbl_data.getModel();
        model.setRowCount(0);
    }
    
    private void setKoneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/rcollection", "root", "");
            st = conn.createStatement();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Koneksi Gagal: " + e);
        }
    }
    
   private void tampilkanData() {
    // Format tanggal
    String tampil = "yyyy-MM-dd";
    SimpleDateFormat fm = new SimpleDateFormat(tampil);
    String tanggalAwal = String.valueOf(fm.format(jDateChooser1.getDate()));
    String tanggalAkhir = String.valueOf(fm.format(jDateChooser2.getDate()));
    
    // Membuat model tabel
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("No Faktur");
    model.addColumn("Kode Barang");
    model.addColumn("Nama Barang");
    model.addColumn("QTY");
    model.addColumn("Sub Total");
    model.addColumn("Tanggal");
    model.addColumn("Kode Supplier");
    
    try {
        setKoneksi();
        String sql = "SELECT brg_masuk.no_faktur, data_barang.kode_barang, data_barang.nama_barang, " +
                     "detail_brgmasuk.kuantitas, detail_brgmasuk.sub_total, brg_masuk.tanggal, brg_masuk.kode_supplier " +
                     "FROM brg_masuk JOIN detail_brgmasuk ON brg_masuk.no_faktur = detail_brgmasuk.no_faktur " +
                     "JOIN data_barang ON data_barang.kode_barang = detail_brgmasuk.kode_barang " +
                     "WHERE brg_masuk.tanggal BETWEEN '" + tanggalAwal + "' AND '" + tanggalAkhir + "' " +
                     "ORDER BY brg_masuk.no_faktur ASC";
        
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7)
            });
        }
        tbl_data.setModel(model);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}
    
     private void hapusData() {
    int selectedRow = tbl_data.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Silakan pilih data yang akan dihapus!");
        return;
    }
    
    String noFaktur = tbl_data.getValueAt(selectedRow, 0).toString();
    
    int konfirmasi = JOptionPane.showConfirmDialog(null, 
            "Anda yakin ingin menghapus faktur " + noFaktur + "?", 
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
    
    if (konfirmasi == JOptionPane.YES_OPTION) {
        try {
            setKoneksi();
            // Hapus detail transaksi terlebih dahulu
            String sqlDetail = "DELETE FROM detail_brgmasuk WHERE no_faktur = '" + noFaktur + "'";
            st.executeUpdate(sqlDetail);
            
            // Kemudian hapus transaksi utama
            String sqlTransaksi = "DELETE FROM brg_masuk WHERE no_faktur = '" + noFaktur + "'";
            st.executeUpdate(sqlTransaksi);
            
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            tampilkanData(); // Refresh tabel
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}

private void cariData() {
    String kataCari = txt_cari.getText();
    
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("No Faktur");
    model.addColumn("Kode Barang");
    model.addColumn("Nama Barang");
    model.addColumn("QTY");
    model.addColumn("Sub Total");
    model.addColumn("Tanggal");
    model.addColumn("Kode Supplier");
    
    try {
        setKoneksi();
        String sql = "SELECT brg_masuk.no_faktur, data_barang.kode_barang, data_barang.nama_barang, " +
                     "detail_brgmasuk.kuantitas, detail_brgmasuk.sub_total, brg_masuk.tanggal, brg_masuk.kode_supplier " +
                     "FROM brg_masuk JOIN detail_brgmasuk ON brg_masuk.no_faktur = detail_brgmasuk.no_faktur " +
                     "JOIN data_barang ON data_barang.kode_barang = detail_brgmasuk.kode_barang " +
                     "WHERE brg_masuk.no_faktur LIKE '%" + kataCari + "%' " +
                     "OR data_barang.kode_barang LIKE '%" + kataCari + "%' " +
                     "OR data_barang.nama_barang LIKE '%" + kataCari + "%' " +
                     "ORDER BY brg_masuk.no_faktur ASC";
        
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            model.addRow(new Object[]{
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7)
            });
        }
        tbl_data.setModel(model);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        btn_hapus = new javax.swing.JButton();
        txt_cari = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();
        btn_tampilkan = new javax.swing.JButton();
        btn_reset = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(125, 99, 41));
        jLabel2.setText("Laporan");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(125, 99, 41));
        jLabel3.setText("Pembelian");

        btn_hapus.setBackground(new java.awt.Color(177, 131, 40));
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });

        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });
        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
        });

        tbl_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tbl_data);

        btn_tampilkan.setBackground(new java.awt.Color(177, 131, 40));
        btn_tampilkan.setForeground(new java.awt.Color(255, 255, 255));
        btn_tampilkan.setText("Tampilkan");
        btn_tampilkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tampilkanActionPerformed(evt);
            }
        });

        btn_reset.setBackground(new java.awt.Color(177, 131, 40));
        btn_reset.setForeground(new java.awt.Color(255, 255, 255));
        btn_reset.setText("Reset");
        btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetActionPerformed(evt);
            }
        });

        jDateChooser1.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooser1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)
                        .addComponent(btn_reset)
                        .addGap(18, 18, 18)
                        .addComponent(btn_hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 278, Short.MAX_VALUE)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(116, 116, 116)
                .addComponent(btn_tampilkan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_reset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_tampilkan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        hapusData();
      
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
       cariData();
    }//GEN-LAST:event_txt_cariKeyReleased

    private void btn_tampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tampilkanActionPerformed
          if (jDateChooser1.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Silakan pilih tanggal terlebih dahulu!");
            return;
        }
        tampilkanData();
    }//GEN-LAST:event_btn_tampilkanActionPerformed

    private void btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetActionPerformed
       kosongkanTabel();
    txt_cari.setText("");
    
    // Cara yang benar untuk mengosongkan JDateChooser
   // jDateChooser1.setCalendar(null);
    //jDateChooser2.setCalendar(null);
    
      jDateChooser1.setDate(new Date());
     jDateChooser2.setDate(new Date());
    }//GEN-LAST:event_btn_resetActionPerformed

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
        cariData();
    }//GEN-LAST:event_txt_cariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_reset;
    private javax.swing.JButton btn_tampilkan;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tbl_data;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables
    
}
