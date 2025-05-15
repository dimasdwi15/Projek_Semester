/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.app.tablemodel;

import com.app.model.ModelBarang;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author LENOVO
 */
public class TableModelBarang extends AbstractTableModel{
    private List<ModelBarang> list = new ArrayList<>();
    public ModelBarang getData (int index) {
        return list.get(index);
    }
    
    public void clear() {
        list.clear();
        fireTableDataChanged();
    }
    
    public void setData (List<ModelBarang> list) {
        clear();
        this.list.addAll(list);
        fireTableDataChanged();
    }
    
    private final String [] columnNames ={"Kode Barang","Nama Barang","Stok","Harga Beli","Harga Jual","Kode Supplier"};

    @Override
    public int getRowCount() {
        return  list.size();
    }

    @Override
    public int getColumnCount() {
      return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
       ModelBarang barang = list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return barang.getKodeBarang();
            case 1:
                return barang.getNamaBarang();
            case 2:
                return barang.getStok();   
            case 3:
                return barang.getHargaBeli();  
            case 4:
                return barang.getHargaJual(); 
            case 5:
                return barang.getKodeSupplier();    
            default:
               return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
    
    
}
