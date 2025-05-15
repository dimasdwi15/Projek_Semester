/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.form;

import com.app.JDialog.FormInputMember;
import com.app.JDialog.FormInputSupplier;
import com.app.config.koneksi;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.lang.reflect.Member;
import java.sql.*;
import java.util.function.Supplier;



/**
 *
 * @author LENOVO
 */
public class FormLaporanRugi extends javax.swing.JPanel {
     private static final String DB_URL = "jdbc:mysql://localhost:3306/rcollection";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    

    
    
    public FormLaporanRugi() {
        initComponents();
        
    }
    
    
    /**
     * Connect to database
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
    
    /**
     * Display data in table
     */
    
    /**
     * Search for members based on keyword
     */
    private void cariData(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tblRugi.getModel();
        model.setRowCount(0); // Clear the table first
        
        String sql = "SELECT * FROM tb_supplier WHERE kode_supplier LIKE ? OR nama_supplier LIKE ? OR tlp LIKE ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchKeyword = "%" + keyword + "%";
            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);
            pstmt.setString(3, searchKeyword);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                 String id = rs.getString("kode_supplier");
                String nama = rs.getString("nama_supplier");
                String nomor = rs.getString("tlp");
                String alamat = rs.getString("alamat");
                java.sql.Date tanggal = rs.getDate("tanggal");
                
                // Add data to table
                model.addRow(new Object[]{id, nama, nomor, alamat, tanggal});
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

        private void tampilkanDataRugiRentangTanggal(java.util.Date fromDate, java.util.Date toDate) {
            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("ID Retur");
            model.addColumn("No Transaksi");
            model.addColumn("Kode Barang");
            model.addColumn("Tanggal");
            model.addColumn("Kuantitas");
            model.addColumn("Alasan");
            model.addColumn("Total Rugi");

            try {
                Connection conn = koneksi.getKoneksi();

                String sql = "SELECT retur_penjualan.id_retur, retur_penjualan.no_transaksi, retur_penjualan.kode_barang, " +
                             "retur_penjualan.tanggal, retur_penjualan.kuantitas, retur_penjualan.alasan, " +
                             "data_barang.harga_jual, " +
                             "(retur_penjualan.kuantitas * data_barang.harga_jual) AS total_rugi " +
                             "FROM retur_penjualan " +
                             "JOIN data_barang ON retur_penjualan.kode_barang = data_barang.kode_barang " +
                             "WHERE retur_penjualan.tanggal BETWEEN ? AND ?";

                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setDate(1, new java.sql.Date(fromDate.getTime()));
                pst.setDate(2, new java.sql.Date(toDate.getTime()));
                ResultSet rs = pst.executeQuery();

                int totalRugiKeseluruhan = 0;

                while (rs.next()) {
                    int totalRugi = rs.getInt("total_rugi");

                    model.addRow(new Object[]{
                        rs.getString("id_retur"),
                        rs.getString("no_transaksi"),
                        rs.getString("kode_barang"),
                        rs.getDate("tanggal"),
                        rs.getInt("kuantitas"),
                        rs.getString("alasan"),
                        totalRugi
                    });

                    totalRugiKeseluruhan += totalRugi;
                }

                tblRugi.setModel(model);
                lblTotalRugi.setText("Rp " + totalRugiKeseluruhan);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Gagal tampilkan data rugi: " + e.getMessage());
            }
        }
    

        
     public static class Supplier {
        private String id;
        private String nama;
        private String nomor;
        private String alamat;
        private java.util.Date tanggal;
        
        public Supplier(String id, String nama, String nomor ,String alamat, java.util.Date tanggal) {
            this.id = id;
            this.nama = nama;
            this.nomor = nomor;
            this.alamat = alamat;
            this.tanggal = tanggal;
        }
        
        public String getId() {
            return id;
        }
        
        public String getNama() {
            return nama;
        }
        
        public String getNomor() {
            return nomor;
        }
        public String getAlamat() {
            return alamat;
        }
        
        public java.util.Date getTanggal() {
            return tanggal;
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
        jSeparator1 = new javax.swing.JSeparator();
        btnTampilkan = new javax.swing.JButton();
        txt_cari = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fromDate = new com.toedter.calendar.JDateChooser();
        toDate = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        lblTotalRugi = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRugi = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        btnTampilkan.setBackground(new java.awt.Color(177, 131, 40));
        btnTampilkan.setForeground(new java.awt.Color(255, 255, 255));
        btnTampilkan.setText("TAMPILKAN");
        btnTampilkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTampilkanActionPerformed(evt);
            }
        });

        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(125, 99, 41));
        jLabel3.setText("Rugi");

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(125, 99, 41));
        jLabel2.setText("LAPORAN");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(15, 15, 15)
                    .addComponent(jLabel2)
                    .addContainerGap(973, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 41, Short.MAX_VALUE)
                .addComponent(jLabel3))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(35, Short.MAX_VALUE)))
        );

        jLabel4.setText("TOTAL RUGI");

        tblRugi.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblRugi);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(164, 164, 164)
                        .addComponent(btnTampilkan))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(toDate, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(lblTotalRugi, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(100, 100, 100)
                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1018, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(lblTotalRugi, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fromDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(toDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnTampilkan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnTampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTampilkanActionPerformed
        // TODO add your handling code here:
    java.util.Date selectedFromDate = fromDate.getDate();
    java.util.Date selectedToDate = toDate.getDate();

    if (selectedFromDate != null && selectedToDate != null) {
        tampilkanDataRugiRentangTanggal(selectedFromDate, selectedToDate);
    } else {
        JOptionPane.showMessageDialog(null, "Silakan pilih rentang tanggal terlebih dahulu.");
    }
    }//GEN-LAST:event_btnTampilkanActionPerformed

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
        // TODO add your handling code here:
                                            
    java.util.Date selectedFromDate = fromDate.getDate();
    java.util.Date selectedToDate = toDate.getDate();

    if (selectedFromDate != null && selectedToDate != null) {
        tampilkanDataRugiRentangTanggal(selectedFromDate, selectedToDate);
    } else {
        JOptionPane.showMessageDialog(null, "Silakan pilih rentang tanggal terlebih dahulu.");
    }

    }//GEN-LAST:event_txt_cariKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnTampilkan;
    private com.toedter.calendar.JDateChooser fromDate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTotalRugi;
    private javax.swing.JTable tblRugi;
    private com.toedter.calendar.JDateChooser toDate;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables

 
  


}
