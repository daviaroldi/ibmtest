/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.testeibm.controller;

import java.io.File;
import br.com.testeibm.model.*;

/**
 *
 * @author aroldi
 */
public class ReaderController {
    private final String path = System.getProperty("user.home");
    private final String inPath = "data" + File.separator + "in";
//    public function
    
    public ReaderController() { }
    
    public String getCompletePath() {
        return path + File.separator + inPath + File.separator;
    }
    
    public void read() {
        
    }
}
