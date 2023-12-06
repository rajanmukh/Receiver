/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Istrac
 */
public class Writer {

    PrintWriter pw = null;

    /**
     *
     */

    public Writer() {

    }

    public void write(String result) {
        
            if (pw == null) {
                makenewfile();
            } 
            if(pw!=null){
                pw.println(result);
                pw.flush();
            }

    }

    public void makenewfile() {
        try {            
            FileWriter fw = null;
            File file = new File("Log\\");
            
            if (file.mkdir()) {
            } else {
            }
            
            SimpleDateFormat date3 = new SimpleDateFormat("YYYY_MM_dd");
            
            Date date = new Date();
            File file4 = new File("Log\\sit_"+date3.format(date) + ".txt");
            if (file4.exists() == false) {
                try {
                    file4.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            fw = new FileWriter(file4, true);
            BufferedWriter bw = new BufferedWriter(fw);
            if(pw!=null){
                pw.close();
            }
            pw = new PrintWriter(bw);
        } catch (IOException ex) {
            Logger.getLogger(Writer.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
    

    public static void main(String[] args) {
        // TODO code application logic here
        new Writer().write("hello");
    }

}
