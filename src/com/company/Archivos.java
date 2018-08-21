package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Archivos {
    public String LeerTxt(String direccion) throws FileNotFoundException {
        String texto = "";
        try {
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String temp="";
            String bfRead;
            while ((bfRead = bf.readLine())!=null){
                temp =temp +bfRead;

            }
            texto=temp;
        }catch (Exception e){
            System.out.println("No se encontro archivo");

        }

        return texto;
    }
}
