/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.app.service;

import com.app.model.ModelBarang;
import java.util.List;


public interface ServiceBarang {
    void tambahData (ModelBarang model);
    void perbaruiData (ModelBarang model);
    void hapusData (ModelBarang model);
    
    List<ModelBarang> tampilData();
    List<ModelBarang> pencarianData(String id);
}
