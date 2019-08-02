/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ibmtest.controller;

import br.com.ibmtest.validator.DataValidator;
import br.com.ibmtest.validator.DataValidatorException;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aroldi
 */
public class ReaderController {
    private final String path = System.getProperty("user.home");
    private final String inPath = "data" + File.separator + "in";
//    public function
    
    public ReaderController() {
    }
    
    public String getCompletePath() {
        return path + File.separator + inPath + File.separator;
    }
    
    public File[] readFiles() {
        File folder = new File(getCompletePath());
        
        File[] files = folder.listFiles((File pathname) -> {
            if (pathname.getName().endsWith(".dat")) {
                return true;
            }
            return false;
        });
        
        try {
            DataValidator dataValidator = new DataValidator(files);
            if (!dataValidator.isValid()) {
                System.out.println("Formato não compatível. Verfique o arquivo " + dataValidator.getErrorFilename() + " na linha "+dataValidator.getErrorLine());
            }
        } catch (DataValidatorException dve) {
            System.out.println(dve.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(ReaderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return files;
    }
}