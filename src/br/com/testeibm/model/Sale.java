/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testeibm.model;

import java.util.List;

/**
 *
 * @author aroldi
 */
public class Sale {
    private final int id;
    private Salesman salesman;
    private List<ItemSold> items;

    public Sale(int id, Salesman salesman, List<ItemSold> items) {
        this.id = id;
        this.salesman = salesman;
        this.items = items;
    }
    
    public int getId() {
        return id;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public List<ItemSold> getItems() {
        return items;
    }

    public void setItems(List<ItemSold> items) {
        this.items = items;
    }
    
    
}
