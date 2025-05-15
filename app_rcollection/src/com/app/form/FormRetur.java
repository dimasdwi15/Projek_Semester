/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.app.form;

import com.app.JDialog.FormInputMember;
import com.app.JDialog.FormInputRetur;
import com.app.JDialog.FormInputSupplier;
import com.app.config.koneksi;
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
public class FormRetur extends javax.swing.JPanel {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/rcollection";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
String idReturTerpilih = null; // deklarasi variabel global di class
private final TableModelBarang tblModel = new TableModelBarang();
private final ServiceBarang servis = new BarangDAO();
    
    
    public FormRetur() {
        initComponents();
        tampilkanData();

    }
    
    private void tampilkanData() {
    DefaultTableModel model = new DefaultTableModel();
    model.addColumn("Id Retur");
    model.addColumn("No Transaksi");
    model.addColumn("Nama Barang");
    model.addColumn("Tanggal");
    model.addColumn("Kuantitas");
    model.addColumn("Alasan");
    model.addColumn("Total Rugi");
    model.setRowCount(0); // Kosongkan tabel

    tblStokRetur.setModel(model); // Set model ke JTable jika belum

    String sql = """
        SELECT r.id_retur, r.no_transaksi, b.nama_barang, r.tanggal, r.kuantitas, r.alasan, r.total_rugi
        FROM retur_penjualan r
        JOIN data_barang b ON r.kode_barang = b.kode_barang
        ORDER BY r.id_retur
    """;

    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            String id_retur = rs.getString("id_retur");
            String no_transaksi = rs.getString("no_transaksi");
            String nama_barang = rs.getString("nama_barang");
            String tanggal = rs.getString("tanggal");
            int kuantitas = rs.getInt("kuantitas");
            String alasan = rs.getString("alasan");
            int total_rugi = rs.getInt("total_rugi");

            model.addRow(new Object[]{
                id_retur, no_transaksi, nama_barang, tanggal, kuantitas, alasan, total_rugi
            });
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Gagal menampilkan data: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}

        
  
        private void tambahRetur() {
        FormInputRetur formInput = new FormInputRetur(null);
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

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);    
    }
    
     public static class Retur {
        private String id;
        private String nama;
        private String nomor;
        private java.util.Date tanggal;
        
        public Retur(String id, String nama, String nomor, java.util.Date tanggal) {
            this.id = id;
            this.nama = nama;
            this.nomor = nomor;
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
        
        public java.util.Date getTanggal() {
            return tanggal;
        }
    }
    
    
    private void hapusRetur(String idRetur) {
    try (Connection conn = getConnection()) {
        String sql = "DELETE FROM retur_penjualan WHERE id_retur = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, idRetur);
        ps.executeUpdate();
        ps.close();

        JOptionPane.showMessageDialog(null, "Data berhasil dihapus.");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal menghapus data: " + e.getMessage());
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
        tblStokRetur = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(125, 99, 41));
        jLabel2.setText("Penjualan");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(125, 99, 41));
        jLabel3.setText("Retur");

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

        tblStokRetur.setModel(new javax.swing.table.DefaultTableModel(
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
        tblStokRetur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStokReturMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblStokRetur);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
        if (idReturTerpilih == null) {
            JOptionPane.showMessageDialog(null, "Pilih data yang ingin dihapus terlebih dahulu.");
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(null,
                "Yakin ingin menghapus data dengan ID Retur: " + idReturTerpilih + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {
            hapusRetur(idReturTerpilih);
            tampilkanData(); // Refresh tabel
            idReturTerpilih = null; // Reset setelah hapus
        }
    
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
         tambahRetur();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
        // TODO add your handling code here:
        pencarianData();
    }//GEN-LAST:event_txt_cariKeyReleased

    private void btn_perbaruiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_perbaruiActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_btn_perbaruiActionPerformed

    private void tblStokReturMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStokReturMouseClicked
        // TODO add your handling code here:
        int baris = tblStokRetur.getSelectedRow();
        if (baris >= 0) {
            idReturTerpilih = tblStokRetur.getValueAt(baris, 0).toString(); // kolom ke-0 = id_retur
        }
    }//GEN-LAST:event_tblStokReturMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_perbarui;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable tblStokRetur;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables

    private void loadData() {
        List<ModelBarang> list = servis.tampilData();
        tblModel.setData(list);
    }

 
    private void pencarianData () {
        List<ModelBarang> list = servis.pencarianData(txt_cari.getText());
        tblModel.setData(list);
    }



  



}
