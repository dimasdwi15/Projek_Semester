/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.main;

import com.app.form.FormAbsensi;
import com.app.form.FormDashBoard;
import com.app.form.FormBarang;
import com.app.form.FormKaryawan;
import com.app.form.FormLaporanLaba;
import com.app.form.FormLaporanPembelian;
import com.app.form.FormLaporanPenjualan;
import com.app.form.FormLaporanRugi;
import com.app.form.FormLogin;
import com.app.form.FormMember;
import com.app.form.FormPenjualan;
import com.app.form.FormRFID;
import com.app.form.FormRetur;
import com.app.form.FormSupplier;
import com.app.form.UserData;
import com.app.main.FormMenuUtama;
import com.app.menu.Menu;
import com.app.menu.MenuAction;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import com.formdev.flatlaf.util.UIScale;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author LENOVO
 */
public class Main extends JLayeredPane {
    
    private Menu menu;
    private JPanel panelBody;
    private JButton menuButton;
     
    
    public Main () {
          // Set global style untuk tombol
         UIManager.put("Button.select", Color.decode("#7D6329"));        // Saat diklik
         UIManager.put("Button.focus", Color.decode("#7D6329"));         // Saat fokus
         UIManager.put("Button.foreground", Color.decode("#FFFFFF"));   // Warna teks normal
         UIManager.put("Button.disabledText", Color.decode("#CCCCCC")); // Warna teks saat disabled
        
        init();
    }


    private void init() {
        setBorder(null);
        setLayout(new MainFormLayout());
        
         String userLevel = FormLogin.userLevel;
    
    // Jika level tidak ada di FormLogin, cek di FormRFID
    if (userLevel == null || userLevel.isEmpty()) {
        userLevel = FormRFID.userLevel;
    }
    
    // Jika masih tidak ada, gunakan default
    if (userLevel == null || userLevel.isEmpty()) {
        userLevel = "admin"; // Default fallback
    }
    
    // Buat menu berdasarkan level pengguna
   // menu = new Menu(userLevel.toLowerCase());
    
        
        // Buat menu berdasarkan level pengguna
        menu = new Menu(userLevel.toLowerCase());
        
        panelBody = new JPanel(new BorderLayout());
        initMenuArrowIcon();
        menuButton.putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Menu.button.background;"
                + "arc:99;"
                + "focusWidth:0;"
                + "borderWidth:0");
        menuButton.addActionListener((e) -> {
            setMenuFull(!menu.isMenuFull()); 
        });
        
        initMenuEvent();
        add(menuButton);
        add(menu);
        add(panelBody);
    }
    
   private void initMenuEvent() {
        menu.addMenuEvent((int index, int subIndex, MenuAction action) -> {
        // Sesuaikan aksi menu berdasarkan level pengguna
        String userLevel = FormLogin.userLevel;
        
        // Jika level tidak ada di FormLogin, cek di FormRFID
        if (userLevel == null || userLevel.isEmpty()) {
            userLevel = FormRFID.userLevel;
        }
          
            
            if (index == 0) {
                // Dashboard - semua level bisa akses
                FormMenuUtama.showForm(new FormDashBoard());
            } else if (index == 1) { // Menu Data
                if (userLevel.equalsIgnoreCase("admin")) {
                    // Admin memiliki akses penuh ke menu Data
                    handleAdminDataMenu(subIndex, action);
                } else if (userLevel.equalsIgnoreCase("kasir")) {
                    // Kasir hanya bisa akses Absensi dan Barang
                    if (subIndex == 1) {
                        FormMenuUtama.showForm(new FormBarang());
                    } else if (subIndex ==2) {
                         FormMenuUtama.showForm(new FormAbsensi());
                    } else {
                        action.cancel();
                    }
                } else {
                    action.cancel();
                }
            } else if (index == 2) { // Menu Transaksi
                if (userLevel.equalsIgnoreCase("admin")) {
                    // Admin memiliki akses penuh ke menu Transaksi
                    handleAdminTransactionMenu(subIndex, action);
                } else if (userLevel.equalsIgnoreCase("kasir")) {
                    // Kasir hanya bisa akses Penjualan dan retur
                    if (subIndex == 1) {
                        FormMenuUtama.showForm(new FormPenjualan());
                    }else if (subIndex == 2){
                        FormMenuUtama.showForm(new FormRetur());//disini untuk retur
                    }else {
                        action.cancel();
                    }
                } else {
                    action.cancel();
                }
            } else if (index == 3) { // Menu Laporan
                if (userLevel.equalsIgnoreCase("admin") || userLevel.equalsIgnoreCase("owner")) {
                    // Admin dan Owner memiliki akses penuh ke menu Laporan
                    handleReportMenu(subIndex, action);
                } else if (userLevel.equalsIgnoreCase("kasir")) {
                    // Kasir hanya bisa akses Laporan Penjualan dan rugi
                    if (subIndex == 1) {
                        FormMenuUtama.showForm(new FormLaporanPenjualan());
                    } else if (subIndex == 2){
                     //   FormMenuUtama.showForm(new FormLaporanRugi());//disini untuk rugi
                    } else {
                        action.cancel();
                    }
                } else {
                    action.cancel();
                }
            } else if (index == 4) { // Opsi Logout - semua level bisa akses
                handleLogout(action);
            } else {
                action.cancel();
            }
        });
    }
   
    private void handleAdminDataMenu(int subIndex, MenuAction action) {
        switch (subIndex) {
            case 1:
                FormMenuUtama.showForm(new FormKaryawan());
                break;
            case 2:
                FormMenuUtama.showForm(new FormBarang());
                break;
            case 3:
                FormMenuUtama.showForm(new FormSupplier());
                break;
            case 4:
                FormMenuUtama.showForm(new FormMember());
                break;
            case 5:
                FormMenuUtama.showForm(new FormAbsensi());
                break;
            default:
                action.cancel();
                break;
        }
    }
    
    // Method pembantu untuk menangani menu Transaksi untuk Admin
    private void handleAdminTransactionMenu(int subIndex, MenuAction action) {
        switch (subIndex) {
            case 1:
                FormMenuUtama.showForm(new FormPenjualan());
                break;
            case 2:
            //    FormMenuUtama.showForm(new FormPembelian());
                break;
            case 3:
                FormMenuUtama.showForm(new FormRetur());
                break;
            default:
                action.cancel();
                break;
        }
    }
    
    // Method pembantu untuk menangani menu Laporan
    private void handleReportMenu(int subIndex, MenuAction action) {
        switch (subIndex) {
            case 1:
                FormMenuUtama.showForm(new FormLaporanPenjualan());
                break;
            case 2:
                FormMenuUtama.showForm(new FormLaporanPembelian());
                break;
            case 3:
                FormMenuUtama.showForm(new FormLaporanLaba());
                break;
            case 4:
                FormMenuUtama.showForm(new FormLaporanRugi());
                break;
            default:
                action.cancel();
                break;
        }
    }
   
    private void handleLogout(MenuAction action) {
        int pilihan = JOptionPane.showConfirmDialog(
            null,
            "Apakah Anda yakin ingin keluar?",
            "Konfirmasi Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (pilihan == JOptionPane.YES_OPTION) {
            // Reset informasi user saat logout
            FormLogin.userID = null;
            FormLogin.userName = null;
            FormLogin.userLevel = null;
            
            FormMenuUtama.logout();
        } else {
            action.cancel();
        }
    }

    
    private void initMenuArrowIcon () {
        if (menuButton == null) {
           menuButton = new JButton(new ImageIcon(getClass().getResource("/com/app/icon/menu_left_20x20.png")));
           menuButton = new JButton(new ImageIcon(getClass().getResource("/com/app/icon/menu_right_20x20.png")));
        }
      
    }
    
 private void setMenuFull(boolean full) {
    String iconPath;
    if (getComponentOrientation().isLeftToRight()) {
        iconPath = full 
            ? "/com/app/icon/menu_left_20x20.png"    // saat terbuka, tampilkan panah ke kiri
            : "/com/app/icon/menu_right_20x20.png";  // saat tertutup, tampilkan panah ke kanan
    } else {
        iconPath = full 
            ? "/com/app/icon/menu_right_20x20.png"
            : "/com/app/icon/menu_left_20x20.png";
    }

    // Mengambil ikon dan melakukan scaling gambar
    ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
    Image image = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
    
    // Set icon pada button
    menuButton.setIcon(new ImageIcon(image));

    // Panggil metode untuk mengatur tampilan menu
    menu.setMenuFull(full);

    // Revalidate untuk memperbarui tampilan
    revalidate();
}

    
    public void hideMenu () {
        menu.hideMenuItem();
    }
    
    public void showForm (Component component) {
        panelBody.removeAll();
        panelBody.add(component);
        panelBody.repaint();
        panelBody.revalidate();
    }
    
    public void setSelectedMenu (int index,int subIndex) {
        menu.setSelectedMenu(index,subIndex);
    }
    
    private class MainFormLayout implements LayoutManager {

    @Override
    public void addLayoutComponent(String name, Component comp) {}

    @Override
    public void removeLayoutComponent(Component comp) {}

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(5, 5);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(0, 0);
    }

    @Override
    public void layoutContainer(Container parent) {
        boolean ltr = parent.getComponentOrientation().isLeftToRight();
        int x = 0;
        int y = 0;
        int width = parent.getWidth();
        int height = parent.getHeight();

        int menuWidth = UIScale.scale(menu.isMenuFull() ? menu.getMenuMaxWidth() : menu.getMenuMinWidth());
        menu.setBounds(x, y, menuWidth, height);

        int menuButtonWidth = menuButton.getPreferredSize().width;
        int menuButtonHeight = menuButton.getPreferredSize().height;
        int menuX = (ltr)
                ? (int) (x + menuWidth - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.3f)))
                : (int) (x - (menuButtonWidth * (menu.isMenuFull() ? 0.5f : 0.7f)));

        menuButton.setBounds(menuX, UIScale.scale(30), menuButtonWidth, menuButtonHeight);

        int gap = UIScale.scale(5);
        int bodyX = ltr ? (x + menuWidth + gap) : x;
        int bodyY = y;
        int bodyWidth = width - menuWidth - gap;
        int bodyHeight = height;

        panelBody.setBounds(bodyX, bodyY, bodyWidth, bodyHeight);
    }
}

}
