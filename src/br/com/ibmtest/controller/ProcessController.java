/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ibmtest.controller;

import br.com.ibmtest.model.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author davi.aroldi
 */
public class ProcessController {
    List<Client> clients;
    List<Sale> sales;
    List<Salesman> sellers;
    
    private final String path = System.getProperty("user.home");
    private final String inPath = "data" + File.separator + "out";
    private final String fileName = "out.done.dat";

    public ProcessController() {
        clients = new ArrayList<>();
        sales = new ArrayList<>();
        sellers = new ArrayList<>();
    }
    
    public String getCompletePath() {
        return path + File.separator + inPath + File.separator + fileName;
    }
    
    public void processFile(File[] files) throws FileNotFoundException, IOException {
        for (File f :files) {
            FileReader freader = new FileReader(f);
            BufferedReader breader = new BufferedReader(freader);
            String line = breader.readLine();

            while (line != null) {
                String[] lineSplitted = line.split("\\u00e7");
                switch (lineSplitted[0]) {
                    case "001":
                        sellers.add(new Salesman(lineSplitted[1], lineSplitted[2], Double.parseDouble(lineSplitted[3])));
                        break;
                    case "002":
                        clients.add(new Client(lineSplitted[1], lineSplitted[2], lineSplitted[3]));
                        break;
                    case "003":
                        String[] sold = lineSplitted[2].split(",");
                        List<ItemSold> items = new ArrayList<>();
                        for(String i: sold) {
                            if (i.equals(sold[0])) {
                                i = i.substring(1);
                            } else if (i.equals(sold[sold.length-1])) {
                                i = i.substring(0,i.length()-1);
                            }

                            String[] itemInformation = i.split("-");
                            ItemSold itemSold = new ItemSold(Integer.parseInt(itemInformation[0]), Integer.parseInt(itemInformation[1]), Double.parseDouble(itemInformation[2]));
                            items.add(itemSold);
                        }
                        sales.add(new Sale(Integer.parseInt(lineSplitted[1]), getSalemanByName(lineSplitted[3]), items));
                        break;
                }
                line = breader.readLine();
            }
        }
    }
    
    private Salesman getSalemanByName(String name) {
        for (Salesman s: sellers) {
            if (s.getName().equals(name)) {
                return s;
            }
        }
        return null;
    }

    public void processInfo() throws IOException {
        FileWriter arq = new FileWriter(getCompletePath());
        PrintWriter gravarArq = new PrintWriter(arq);

        gravarArq.printf("Quantidade de clientes nos arquivos: %d\n", clients.size());
        gravarArq.printf("Quantidade de vendedores nos arquivos: %d\n", sellers.size());
        
        int idMostExpensive = 0;
        Double valueMostExpensive = 0.0;
        
        //inicializa os valores de vendas dos vendedores para achar o que menos vendeu
        Hashtable<String, Double> salesBySaleman = new Hashtable<String, Double>(); 
        for(Salesman sa: sellers) {
            salesBySaleman.put(sa.getName(), 0.0);
        }
        
        
        for (Sale s: sales) {
            Double sumValue = 0.0;
            for(ItemSold i: s.getItems()){
                sumValue += i.getPrice();
            }

            if (sumValue > valueMostExpensive) {
                idMostExpensive = s.getId();
                valueMostExpensive = sumValue;
            }
            
            //aproveita o loop para incrementar o valor de vendas dos vendedoress
            salesBySaleman.put(s.getSalesman().getName(), salesBySaleman.get(s.getSalesman().getName()) + sumValue);
        }
        gravarArq.printf("ID da venda mais cara: %d\n", idMostExpensive);
        
        Enumeration e = salesBySaleman.keys();
        Double cheapestValueSales = 9999999999999999999.9;
        String cheapestSalemanSales = "";
        while (e.hasMoreElements()) {
            Object saleman = e.nextElement();
            Double value = salesBySaleman.get(saleman);
            if (value < cheapestValueSales) {
                cheapestSalemanSales = saleman+"";
            }
        }

        gravarArq.printf("O nome do pior vendedor(pelo valor das vendas): %s\n", cheapestSalemanSales);

        arq.close();
    }
}
