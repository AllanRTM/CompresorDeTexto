
package principal;
import java.io.FileNotFoundException;
import excepciones.FueraDeRango;
import excepciones.ListaVacia;
import excepciones.NombreNulo;
import lista_arbol.ListaArbol;
import lista_char.Lista;
import tabla.ListaTabla;
import tabla.Nodo;

import java.io.IOException;
import java.io.*;
import java.util.Scanner;

public class CompresorDeTexto {
Scanner SC=new Scanner(System.in);
    public String[] Comprimir(String mensaje) throws FueraDeRango, ListaVacia, IOException, NombreNulo, ClassNotFoundException {
        //creamos una lista para almacenar el mensaje
        Lista cadena=new Lista();
        cadena.toLista(mensaje);//La funcion toLista se encuentra dentro de la clase lista
        cadena.recorrer();
        //Se crea una lista de arboles para almacenar el caracter y su frecuencia
        ListaArbol lista=new ListaArbol();
        lista.toListaArbol(cadena);//Creamos la listaArbol, el caracter y su frecuencia
        lista.imprimir();
        //De la lista anterior se crea el arbol
        lista.obtenerArbol();
        //Creamos una lista para almacenar el caracter y su direccion en el arbol(la tabla)
        ListaTabla tabla=new ListaTabla();
        tabla=lista.obtenerHojas("", tabla, lista.getInicio().getArbol().getRaiz());
        //Unimos todas las direcciones en un string
        String direcciones=tabla.unirTodo(mensaje);
        //Preguntamos al usuario el nombre que debe llevar el archivo
        System.out.println("escribir el nombre del archivo a generar: ");
        String archivo = SC.next();
        if(archivo.length()==0){
            System.out.println("DEBES ESCRIBIR UN NOMBRE VALIDO!! ");
            throw new NombreNulo();
        }
        //Escibimos en el nuevo archivo la cadena de caracteres
        String codificado=escribir(direcciones,tabla,archivo+".txt");
        //Creamos un array de string para retornar las direcciones y los caracteres al usuario
        String[] array={direcciones,codificado};
        return array;
    }
    public String Descomprimir() throws NombreNulo, IOException {
        //prguntamos al usuario el nombre del archivo a leer
        Scanner s = new Scanner(System.in);
        System.out.println("nombre del archivo a descomprimir");
        String a = s.next();

            File doc = new File(a + ".txt");
            FileReader fr = new FileReader(doc);
            BufferedReader lector = new BufferedReader(fr);
            //Cargamos en apoyo2 el mensaje de caracteres
            String apoyo2 = lector.readLine();
            Integer falta = Integer.parseInt(lector.readLine());
            String apoyo = "";
            String mensaje = "";
            byte bit2;
            //los convertimos a binario y rellenamos con ceros en caso de que no sea de longitud 8
            for (int i = 0; i < apoyo2.length(); i++) {
                bit2 = (byte) apoyo2.charAt(i);
                apoyo = Integer.toBinaryString(bit2 & 0xFF);
                if (apoyo.length() < 8) {
                    apoyo = rellenar(apoyo);
                    if (i == (apoyo2.length() - 1))
                        apoyo = quitar(apoyo, falta);
                }
                mensaje += apoyo;
            }
            //Creamos una listaTabla para cargar los caracteres y sus direcciones
            ListaTabla tabla = new ListaTabla();
            apoyo = lector.readLine();
            while (apoyo != null) {
                tabla.agregar(apoyo.charAt(0) + "", quitar(apoyo, 1));
                apoyo = lector.readLine();
            }
            System.out.println("archivo cargado con exito");
            apoyo = "";
            String original = "";
            apoyo2 = "";
            //Decodificamos el string de direcciones para obtener el mensaje original
            for (int i = 0; i < mensaje.length(); i++) {
                apoyo = apoyo + mensaje.charAt(i);
                apoyo2 = tabla.LetraDe(apoyo);
                if (apoyo2 != null) {
                    original = original + apoyo2;
                    apoyo = "";
                    apoyo2 = "";
                }
            }
            return original;
        }



    private String escribir(String mensaje,ListaTabla tabla,String nombre) throws ClassNotFoundException, IOException{
        //Creamos los objetos necesarios para escribir
        File f=new File(nombre);
        FileWriter NuevaFila=new FileWriter(f);
        BufferedWriter ProcteccionEscritura=new BufferedWriter(NuevaFila);
        PrintWriter escritor=new PrintWriter(ProcteccionEscritura);
        String bits="";
        String codificado="";
        int falta=0;
        byte bin;
        char car;
        //Convertimos los 1 y 0 a bytes y despues a caracter, y lo vamos concatenando
        for(int i=0;i<mensaje.length();i++){
            if(bits.length()==8){
                bin = (byte)Short.parseShort(bits, 2);
                car=(char)(bin & 0xFF);
                codificado+=car;
                bits="";
                i--;
            }else
                bits+=mensaje.charAt(i);
        }
        if(bits.length()!=0){
            bin = (byte)Short.parseShort(bits, 2);
            car=(char)(bin & 0xFF);
            codificado+=car;
            falta=8-bits.length();
        }
        //Escribimos en el archivo la cadena de caracteres generada
        escritor.write(codificado+"\n");
        escritor.write(falta+"\n");
        Nodo aux=tabla.getInicio();
        //A partir de nuestra ListaTabla escribimos el caracter seguido de la direccion
        while(aux!=null){
            escritor.write(aux.getDato()+aux.getUbicacion()+"\n");
            aux=aux.getSiguiente();
        }
        escritor.close();
        ProcteccionEscritura.close();
        System.out.println( "Archivo generado exitosamente!!!\n nombre: "+nombre);
        //Retornamos la cadena decodificada
        return codificado;
    }
    public static void muestraContenido(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            System.out.println(cadena);
        }
        b.close();
    }

	 private String rellenar(String cadena){
		 //Rellenamos la cadena con ceros a la izquierda
		 String nueva="";
		 for(int i=0;i<8-cadena.length();i++){
			 nueva+="0";
		 }
		 nueva+=cadena;
		 return nueva;
	 }

	 private String quitar(String cad,Integer n){
		 //Quitamos a la cadena n caracteres
		 String nueva="";
		 for(int i=n;i<cad.length();i++)
			 nueva+=cad.charAt(i);
		 return nueva;
	 }
}
