/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.ibmtest;

import br.com.ibmtest.controller.ProcessController;
import br.com.ibmtest.controller.ReaderController;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author aroldi
 */
public class IBMTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        ReaderController readerController = new ReaderController();
        File[] files = readerController.readFiles();

        if (files != null) {
            ProcessController processController = new ProcessController();
            processController.processFile(files);
            
            try {
                processController.processInfo();
            } catch (IOException e) {
                System.out.println("Nao foi possivel processar os dados!");
            }
        }
    }
    
}
