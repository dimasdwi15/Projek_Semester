/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.form;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class Koneksi {
      private static Connection koneksi;
    

    public static Connection getConnection() {
        if (koneksi == null) {
            try {
                // Load driver JDBC MySQL
                Class.forName("com.mysql.jdbc.Driver");
                
                // Parameter koneksi
                String url = "jdbc:mysql://localhost:3306/rcollection"; // Ganti dengan nama database Anda
                String user = "root"; // Ganti dengan username MySQL Anda
                String password = ""; // Ganti dengan password MySQL Anda
                
                // Buat koneksi
                koneksi = DriverManager.getConnection(url, user, password);
                
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, 
                    "Driver MySQL tidak ditemukan: " + e.getMessage(), 
                    "Error Driver", 
                    JOptionPane.ERROR_MESSAGE);
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Koneksi ke database gagal: " + e.getMessage(), 
                    "Error Koneksi", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
        
        return koneksi;
    }
    
    /**
     * Method untuk menutup koneksi
     */
    public static void closeConnection() {
        if (koneksi != null) {
            try {
                koneksi.close();
                koneksi = null;
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, 
                    "Gagal menutup koneksi: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
