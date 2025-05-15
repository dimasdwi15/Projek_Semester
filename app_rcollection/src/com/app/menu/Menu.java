package com.app.menu;

import com.app.form.FormLogin;
import com.app.form.FormRFID;
import com.app.menu.LightDarkMode;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatUIUtils;
import com.formdev.flatlaf.util.UIScale;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;


/**
 *
 * @author Raven
 */
public class Menu extends JPanel {

    private final List<MenuEvent> events = new ArrayList<>();
    private boolean menuFull = true;
    private final String headerName = "";
    private JLabel header;
    private JScrollPane scroll;
    private JPanel panelMenu;

    // Konstanta UI
    protected final boolean hideMenuTitleOnMinimum = true;
    protected final int menuTitleLeftInset = 5;
    protected final int menuTitleVgap = 5;
    protected final int menuMaxWidth = 250;
    protected final int menuMinWidth = 60;
    protected final int headerFullHgap = 5;

    // Konstruktor dengan parameter role
    public Menu(String role) {
        init(role);
    }

    private void init(String role) {
        setLayout(new MenuLayout());
        setBackground(new Color(0x7D6329));
        setBorder(BorderFactory.createEmptyBorder());

        putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,2,2,2;"
                + "background:#7D6329;"
                + "arc:10");

        header = new JLabel(headerName);
        header.setIcon(new ImageIcon(getClass().getResource("/com/app/icon/Group 5.png")));
        header.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$Menu.header.font;"
                + "foreground:$Menu.foreground");

        // Panel Menu
        panelMenu = new JPanel(new MenuItemLayout(this));
        panelMenu.setBackground(new Color(0x7D6329));
        panelMenu.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:0,5,5,5;"
                + "background:#7D6329;");

        scroll = new JScrollPane(panelMenu);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBackground(new Color(0x7D6329));
        scroll.getVerticalScrollBar().setUnitIncrement(10);
        
       
         // Cek level dari FormLogin atau FormRFID
    String userLevel = FormLogin.userLevel;
    
    // Jika level tidak ada di FormLogin, cek di FormRFID
    if (userLevel == null || userLevel.isEmpty()) {
        userLevel = FormRFID.userLevel;
    }
    
    // Jika tidak ada level yang ditetapkan, gunakan parameter role
    if (userLevel == null || userLevel.isEmpty()) {
        createMenu(role);
    } else {
        createMenu(userLevel);
    }

        add(header);
        add(scroll);
    }

    private void createMenu(String role) {
        panelMenu.removeAll();
        
        // Jika role kosong, jangan tampilkan menu (default untuk login page)
        if (role == null || role.isEmpty()) {
            return;
        }
        
        // Set menu sesuai dengan level
        if (role.equalsIgnoreCase("admin")) {
            panelMenu.add(createTitle("ADMIN"));
            panelMenu.add(new MenuItem(this, new String[]{"Dashboard"}, 0, events));
            panelMenu.add(new MenuItem(this, new String[]{"Data", "Karyawan", "Barang", "Supplier", "Member", "Absensi"}, 1, events));
            panelMenu.add(new MenuItem(this, new String[]{"Transaksi", "Penjualan", "Pembelian","Retur"}, 2, events));
            panelMenu.add(new MenuItem(this, new String[]{"Laporan", "Penjualan", "Pembelian", "Laba", "Rugi"}, 3, events));
            panelMenu.add(new MenuItem(this, new String[]{"Logout"}, 4, events));
        } else if (role.equalsIgnoreCase("kasir")) {
            panelMenu.add(createTitle("KASIR"));
            panelMenu.add(new MenuItem(this, new String[]{"Dashboard"}, 0, events));
            panelMenu.add(new MenuItem(this, new String[]{"Data", "Barang" ,"Absensi"}, 1, events));
            panelMenu.add(new MenuItem(this, new String[]{"Transaksi", "Penjualan", "Retur"}, 2, events));
            panelMenu.add(new MenuItem(this, new String[]{"Laporan", "Penjualan"}, 3, events));
            panelMenu.add(new MenuItem(this, new String[]{"Logout"}, 4, events));
        } 
        
        // Refresh panel menu setelah menambahkan menu items
        panelMenu.revalidate();
        panelMenu.repaint();
    }

    private JLabel createTitle(String title) {
        JLabel lbTitle = new JLabel(title);
        lbTitle.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:$Menu.label.font;"
                + "foreground:#FFFFFF");
        return lbTitle;
    }

    public void setSelectedMenu(int index, int subIndex) {
        runEvent(index, subIndex);
    }

    protected void setSelected(int index, int subIndex) {
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem) {
                MenuItem item = (MenuItem) com;
                item.setSelectedIndex(item.getMenuIndex() == index ? subIndex : -1);
            }
        }
    }

    protected void runEvent(int index, int subIndex) {
        MenuAction menuAction = new MenuAction();
        for (MenuEvent event : events) {
            event.menuSelected(index, subIndex, menuAction);
        }
        if (!menuAction.isCancel()) {
            setSelected(index, subIndex);
        }
    }

    public void addMenuEvent(MenuEvent event) {
        events.add(event);
    }

    public boolean isMenuFull() {
        return menuFull;
    }

    public void setMenuFull(boolean menuFull) {
        this.menuFull = menuFull;
        header.setText(menuFull ? headerName : "");
        header.setHorizontalAlignment(menuFull ? JLabel.LEFT : JLabel.CENTER);
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem) {
                ((MenuItem) com).setFull(menuFull);
            }
        }
    }

    public void hideMenuItem() {
        for (Component com : panelMenu.getComponents()) {
            if (com instanceof MenuItem) {
                ((MenuItem) com).hideMenuItem();
            }
        }
        revalidate();
    }

    public boolean isHideMenuTitleOnMinimum() {
        return hideMenuTitleOnMinimum;
    }

    public int getMenuTitleLeftInset() {
        return menuTitleLeftInset;
    }

    public int getMenuTitleVgap() {
        return menuTitleVgap;
    }

    public int getMenuMaxWidth() {
        return menuMaxWidth;
    }

    public int getMenuMinWidth() {
        return menuMinWidth;
    }

    private class MenuLayout implements LayoutManager {
        @Override public void addLayoutComponent(String name, Component comp) {}
        @Override public void removeLayoutComponent(Component comp) {}
        @Override public Dimension preferredLayoutSize(Container parent) {
            return new Dimension(5, 5);
        }
        @Override public Dimension minimumLayoutSize(Container parent) {
            return new Dimension(0, 0);
        }
        @Override public void layoutContainer(Container parent) {
            Insets insets = parent.getInsets();
            int x = insets.left;
            int y = insets.top;
            int gap = UIScale.scale(5);
            int sheaderFullHgap = UIScale.scale(headerFullHgap);
            int width = parent.getWidth() - insets.left - insets.right;
            int height = parent.getHeight() - insets.top - insets.bottom;
            int iconHeight = header.getPreferredSize().height;
            int hgap = menuFull ? sheaderFullHgap : 0;

            header.setBounds(x + hgap, y, width - (hgap * 2), iconHeight);

            int menuy = y + iconHeight + gap;
            int menuHeight = height - iconHeight - gap;
            scroll.setBounds(x, menuy, width, menuHeight);
        }
    }
}
