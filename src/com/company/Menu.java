package com.company;

import java.util.Scanner;

public class Menu {
    public int menu() {
        System.out.println("");
        System.out.println("");
        System.out.println("========================MENU==========================");
        System.out.println("1. COMPRIMIR CON ARCHIVOS ");
        System.out.println("2. COMPRIMIR CON TEXTO ");
        System.out.println("3. DESCOMPRIMIR ARCHIVOS ");
        System.out.println("4. VER LOS ARCHIVOS DISPONIBLES ");
        System.out.println("5. Salir");
        int opcion = 0;
        while ((opcion < 1) || (opcion > 6)) {
            System.out.println("Favor Ingresar Una Opcion : ");
            Scanner Sc = new Scanner(System.in);
            opcion = Sc.nextInt();
            if ((opcion < 1) || (opcion > 6)) {
                System.out.println("Opcion No Invalida.");
            }
        }
        return opcion;

    }

}
