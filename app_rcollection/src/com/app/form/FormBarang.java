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
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;



/**
 *
 * @author LENOVO
 */
public class FormBarang extends javax.swing.JPanel {

private static final String DB_URL = "jdbc:mysql://localhost:3306/rcollection";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    

    
    
    public FormBarang() {
        initComponents();
        initTable();
        tampilkanData();
    }
    
     private void initTable() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Barcode");
        model.addColumn("Stok");
        model.addColumn("Stok Retur");
        model.addColumn("Harga Beli");
        model.addColumn("Harga Jual");
        model.addColumn("Kode Supplier");
        
        tbl_data.setModel(model);
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
   private void tampilkanData() {
    DefaultTableModel model = (DefaultTableModel) tbl_data.getModel();
    model.setRowCount(0); // Membersihkan tabel
    
    String sql = "SELECT * FROM data_barang ORDER BY kode_barang";
    
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {
        
        while (rs.next()) {
            String kode = rs.getString("kode_barang");
            String nama = rs.getString("nama_barang");
            int barcode = rs.getInt("barcode");
            int stok = rs.getInt("stok");
            int stokRetur = rs.getInt("stok_retur");
            int hargaBeli = rs.getInt("harga_beli");
            int hargaJual = rs.getInt("harga_jual");
            String kodeSupplier = rs.getString("kode_supplier");
            
            // Menambahkan data ke tabel
            model.addRow(new Object[]{kode, nama, barcode, stok, stokRetur, hargaBeli, hargaJual, kodeSupplier});
        }
        
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal menampilkan data: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    

    private void cariData(String keyword) {
        DefaultTableModel model = (DefaultTableModel) tbl_data.getModel();
        model.setRowCount(0); // Clear the table first
        
        String sql = "SELECT * FROM data_barang WHERE kode_barang LIKE ? OR nama_barang LIKE ? OR kode_supplier LIKE ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchKeyword = "%" + keyword + "%";
            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);
            pstmt.setString(3, searchKeyword);
            
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
            String kode = rs.getString("kode_barang");
            String nama = rs.getString("nama_barang");
            int barcode = rs.getInt("barcode");
            int stok = rs.getInt("stok");
            int stokRetur = rs.getInt("stok_retur");
            int hargaBeli = rs.getInt("harga_beli");
            int hargaJual = rs.getInt("harga_jual");
            String kodeSupplier = rs.getString("kode_supplier");
            
            // Menambahkan data ke tabel
            model.addRow(new Object[]{kode, nama, barcode, stok, stokRetur, hargaBeli, hargaJual, kodeSupplier});
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mencari data: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Delete selected member
     */
    private void hapusData() {
        int selectedRow = tbl_data.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String id = tbl_data.getValueAt(selectedRow, 0).toString();
        
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Apakah Anda yakin ingin menghapus data barang dengan ID: " + id + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (konfirmasi == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM data_barang WHERE kode_barang=?";
            
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                pstmt.setString(1, id);
                
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                    tampilkanData(); // Refresh table
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * Open input form to add new member
     */
    private void tambahBarang() {
        FormInputBarang formInput = new FormInputBarang(null);
        formInput.setLocationRelativeTo(this);
        formInput.setVisible(true);
        
        // Refresh table after form is closed
        formInput.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                tampilkanData();
            }
        });
    }
    
    /**
     * Open input form to edit selected member
     */
   private void perbaruiBarang() {
    int selectedRow = tbl_data.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Pilih data barang yang akan diperbarui terlebih dahulu!",
                "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Mengambil data dari baris yang dipilih
    String kodeBarang = tbl_data.getValueAt(selectedRow, 0).toString();
    String namaBarang = tbl_data.getValueAt(selectedRow, 1).toString();
    int barcode = Integer.parseInt(tbl_data.getValueAt(selectedRow, 2).toString());
    int stok = Integer.parseInt(tbl_data.getValueAt(selectedRow, 3).toString());
    int stokRetur = Integer.parseInt(tbl_data.getValueAt(selectedRow, 4).toString());
    int hargaBeli = Integer.parseInt(tbl_data.getValueAt(selectedRow, 5).toString());
    int hargaJual = Integer.parseInt(tbl_data.getValueAt(selectedRow, 6).toString());
    
    // Kode supplier bisa null, jadi perlu penanganan khusus
    String kodeSupplier = null;
    if (tbl_data.getValueAt(selectedRow, 7) != null) {
        kodeSupplier = tbl_data.getValueAt(selectedRow, 7).toString();
    }
    
    // Membuat objek Barang untuk dikirim ke form input
    Barang selectedBarang = new Barang(kodeBarang, namaBarang, barcode, stok, stokRetur, 
                                      hargaBeli, hargaJual, kodeSupplier);
    
    // Membuka form input dengan data barang yang dipilih
    FormInputBarang formInput = new FormInputBarang(selectedBarang);
    formInput.setLocationRelativeTo(this);
    formInput.setVisible(true);
    
    
    
    // Menyegarkan tabel setelah form ditutup
    formInput.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
            tampilkanData();
        }
    });
}

public static class Barang {
    private String kodeBarang;
    private String namaBarang;
    private int barcode;
    private int stok;
    private int stokRetur;
    private int hargaBeli;
    private int hargaJual;
    private String kodeSupplier;
    
    public Barang(String kodeBarang, String namaBarang, int barcode, int stok, 
                 int stokRetur, int hargaBeli, int hargaJual, String kodeSupplier) {
        this.kodeBarang = kodeBarang;
        this.namaBarang = namaBarang;
        this.barcode = barcode;
        this.stok = stok;
        this.stokRetur = stokRetur;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.kodeSupplier = kodeSupplier;
    }
    
    // Getter untuk setiap properti
    public String getKodeBarang() {
        return kodeBarang;
    }
    
    public String getNamaBarang() {
        return namaBarang;
    }
    
    public int getBarcode() {
        return barcode;
    }
    
    public int getStok() {
        return stok;
    }
    
    public int getStokRetur() {
        return stokRetur;
    }
    
    public int getHargaBeli() {
        return hargaBeli;
    }
    
    public int getHargaJual() {
        return hargaJual;
    }
    
    public String getKodeSupplier() {
        return kodeSupplier;
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
        btn_tambah = new javax.swing.JButton();
        btn_perbarui = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        txt_cari = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_data = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(125, 99, 41));
        jLabel2.setText("DATA ");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(125, 99, 41));
        jLabel3.setText(" Barang");

        btn_tambah.setBackground(new java.awt.Color(177, 131, 40));
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_perbarui.setBackground(new java.awt.Color(177, 131, 40));
        btn_perbarui.setForeground(new java.awt.Color(255, 255, 255));
        btn_perbarui.setText("Perbarui");
        btn_perbarui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_perbaruiActionPerformed(evt);
            }
        });

        btn_hapus.setBackground(new java.awt.Color(177, 131, 40));
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
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
                        .addGap(12, 12, 12)
                        .addComponent(btn_tambah)
                        .addGap(18, 18, 18)
                        .addComponent(btn_perbarui)
                        .addGap(19, 19, 19)
                        .addComponent(btn_hapus)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 490, Short.MAX_VALUE)
                        .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(24, 24, 24))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_perbarui, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
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

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        tambahBarang();
         
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
        // TODO add your handling code here:
           // TODO add your handling code here:
         String keyword = txt_cari.getText();
                if (!keyword.isEmpty()) {
                    cariData(keyword);
                } else {
                    tampilkanData();
                }
    ;
    }//GEN-LAST:event_txt_cariKeyReleased

    private void btn_perbaruiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_perbaruiActionPerformed
        // TODO add your handling code here:
         perbaruiBarang();
        
    }//GEN-LAST:event_btn_perbaruiActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_perbarui;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tbl_data;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables
    
}
