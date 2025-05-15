/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.dao;

import com.app.config.koneksi;
import com.app.model.ModelBarang;
import com.app.service.ServiceBarang;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;


/**
 *
 * @author LENOVO
 */
public class BarangDAO implements ServiceBarang{

    private Connection conn;
    
    public BarangDAO() {
        conn = koneksi.getKoneksi();
    }
            
    @Override
    public void tambahData(ModelBarang model) {
        PreparedStatement st = null;
        try {
            String sql = "INSERT INTO data_barang (kode_barang, nama_barang, stok, harga_beli, harga_jual, kode_supplier) VALUES (?, ?, ?, ?, ?, ?)";
            st = (PreparedStatement) conn.prepareStatement(sql);
            st.setString    (1, model.getKodeBarang());
            st.setString    (2, model.getNamaBarang());
            st.setInt       (3, model.getStok());
            st.setDouble    (4, model.getHargaBeli());
            st.setDouble    (5, model.getHargaJual());
            st.setString    (6, model.getKodeSupplier());
            
            st.executeUpdate();
            st.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void perbaruiData(ModelBarang model) {
              PreparedStatement st = null;
        try {
            String sql = "UPDATE data_barang SET nama_barang = ?, stok = ?, harga_beli = ?, harga_jual = ?, kode_supplier = ? WHERE kode_barang = ?";
            st = (PreparedStatement) conn.prepareStatement(sql);
            st.setString(1, model.getNamaBarang());    // First ? is nama_barang
            st.setInt(2, model.getStok());             // Second ? is stok
            st.setInt(3, model.getHargaBeli());        // Third ? is harga_beli
            st.setInt(4, model.getHargaJual());        // Fourth ? is harga_jual
            st.setString(5, model.getKodeSupplier());  // Fifth ? is kode_supplier
            st.setString(6, model.getKodeBarang());    
            
            st.executeUpdate();
        
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void hapusData(ModelBarang model) {
             PreparedStatement st = null;
             String sql = "DELETE FROM data_barang WHERE kode_barang = ?";
        try {
             st = (PreparedStatement) conn.prepareStatement(sql);
             st.setString(1, model.getKodeBarang());
             st.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ModelBarang> tampilData() {
           PreparedStatement st = null;
           ResultSet rs = null;
           List list = new ArrayList();
           String sql = "SELECT * FROM data_barang";

           try {
               st = (PreparedStatement) conn.prepareStatement(sql);
               rs = st.executeQuery();
               while(rs.next()){
                 ModelBarang barang = new ModelBarang();
                barang.setKodeBarang(rs.getString("kode_barang"));      // This line is correct
                barang.setNamaBarang(rs.getString("nama_barang"));      // Fixed: "nama_produk" -> "nama_barang"
                barang.setStok(rs.getInt("stok"));                      // Fixed: getInt instead of getString, and "id_kategori" -> "stok"
                barang.setHargaBeli(rs.getInt("harga_beli"));           // Fixed: getInt instead of getLong, and "harga" -> "harga_beli"
                barang.setHargaJual(rs.getInt("harga_jual"));           // Fixed: "stok" -> "harga_jual"
                barang.setKodeSupplier(rs.getString("kode_supplier"));  // Fixed: "id_supplier" -> "kode_supplier"
                   

                   list.add(barang);
               }
           } catch (SQLException e) {
               e.printStackTrace();
           }

    return list;
    }

    @Override
    public List<ModelBarang> pencarianData(String id) {
        PreparedStatement st = null;
           ResultSet rs = null;
           List list = new ArrayList();
           String sql = "SELECT * FROM data_barang WHERE kode_barang LIKE '%"+id+"%'"
                   + " OR nama_barang LIKE '%"+id+"%' ";

           try {
               st = (PreparedStatement) conn.prepareStatement(sql);
               rs = st.executeQuery();
               while(rs.next()){
                 ModelBarang barang = new ModelBarang();
                barang.setKodeBarang(rs.getString("kode_barang"));      // This line is correct
                barang.setNamaBarang(rs.getString("nama_barang"));      // Fixed: "nama_produk" -> "nama_barang"
                barang.setStok(rs.getInt("stok"));                      // Fixed: getInt instead of getString, and "id_kategori" -> "stok"
                barang.setHargaBeli(rs.getInt("harga_beli"));           // Fixed: getInt instead of getLong, and "harga" -> "harga_beli"
                barang.setHargaJual(rs.getInt("harga_jual"));           // Fixed: "stok" -> "harga_jual"
                barang.setKodeSupplier(rs.getString("kode_supplier"));  // Fixed: "id_supplier" -> "kode_supplier"
                   

                   list.add(barang);
               }
               rs.close();
               st.close();
               
           } catch (SQLException e) {
               e.printStackTrace();
           }

    return list;
    }
    
  

    
}
