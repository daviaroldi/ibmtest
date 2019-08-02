/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ibmtest.validator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author davi.aroldi
 */
public class DataValidator {
    private File file;
    private File[] files;
    private int errorLine;
    private String errorFilename;
    

    public DataValidator(File file) {
        this.file = file;
    }
    
    public DataValidator(File[] files) {
        this.files = files;
    }
    
    public boolean isValid() throws DataValidatorException, FileNotFoundException, IOException {
        if (file != null) {
            FileReader freader = new FileReader(file);
            BufferedReader breader = new BufferedReader(freader);
            String line = breader.readLine();
            int lineCount = 1;
            
            while (line != null) {
                if (!isValidLine(line)) {
                    this.errorLine = lineCount;
                    this.errorFilename = file.getName();
                    
                    return false;
                }
                lineCount++;
                line = breader.readLine();
            }
        } else if (files != null && files.length > 0) {
            for (File f :files) {
                FileReader freader = new FileReader(f);
                BufferedReader breader = new BufferedReader(freader);
                String line = breader.readLine();
                int lineCount = 1;
                
                while (line != null) {
                    if (!isValidLine(line)) {
                        this.errorLine = lineCount;
                        this.errorFilename = f.getName();
                        
                        return false;
                    }
                    lineCount++;
                    line = breader.readLine();
                }
            }
        } else {
            throw new DataValidatorException("Nenhum arquivo foi expecificado!");
        }
        
        return true;
    }
    
    public String getErrorFilename() {
        return errorFilename;
    }
    
    public int getErrorLine() {
        return errorLine;
    }

    private boolean isValidLine(String line) {
        String[] lineSplitted = line.split("ç");
        boolean result = false;

        if (lineSplitted.length == 4) {
            switch (lineSplitted[0]) {
                case "001":
                case "002":
                    for (String item: lineSplitted) {
                        if (!item.trim().equals("")) {
                            result = true;
                        }
                    }
                    break;
                case "003":
                    String[] sold = lineSplitted[2].split(",");
                    if (sold.length > 0) {
                        for(String i: sold) {
                            if (i.equals(sold[0])) {
                                i = i.substring(1);
                            } else if (i.equals(sold[sold.length-1])) {
                                i = i.substring(0,i.length()-1);
                            }
                            
                            String[] itemInformation = i.split("-");
                            for (String item: itemInformation) {
                                if (!item.trim().equals("")) {
                                    result = true;
                                }
                            }
                        }
                    }
                    break;
            }
        }
        
        return result;
    }
}
