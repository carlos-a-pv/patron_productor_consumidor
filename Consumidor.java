package tarea_productor_consumidor;

import java.io.*;
import java.util.ArrayList;

public class Consumidor extends Thread{
    Monitor monitor;

    String rutaArchivoInfo = "C://td//Persistencia//patron_productor_consumidor/archivoLogInfo.txt";
    String rutaArchivoWarning = "C://td//Persistencia//patron_productor_consumidor/archivoLogWarning.txt";
    String rutaArchivoSevere = "C://td//Persistencia//patron_productor_consumidor/archivoLogSevere.txt";


    public Consumidor(Monitor m){
        this.monitor = m;
    }

    @Override
    public void run() {
        while(Thread.currentThread().isAlive()) {
            String aux = "";
            String aux2 = "";
            File archivoRecogido = monitor.recoger();
            String contenido = "";

            try {
                BufferedReader br = new BufferedReader(new FileReader(archivoRecogido));
                String linea;
                while ((linea = br.readLine()) != null) {
                    contenido +=  linea+";";
                }
                contenido+="\n";
                sleep(5000);
            } catch (IOException e) {
                System.out.println("Error al leer el archivo: " + e.getMessage());
            }catch (InterruptedException e){

            }
            aux = (contenido.split(";")[1]);
            aux2 = aux.split(":")[0];
        try{
            if(aux2.equalsIgnoreCase("info")){
                guardarArchivo(rutaArchivoInfo, contenido, true);
            }else if(aux2.equalsIgnoreCase("warning")){
                guardarArchivo(rutaArchivoWarning, contenido, true);
            }else{
                guardarArchivo(rutaArchivoSevere, contenido, true);
            }

            }catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Se ha consumido un archivo");
        }
    }
    public static void guardarArchivo(String ruta,String contenido, Boolean flagAnexarContenido) throws IOException {

        FileWriter fw = new FileWriter(ruta,flagAnexarContenido);
        BufferedWriter bfw = new BufferedWriter(fw);
        bfw.write(contenido);
        bfw.close();
        fw.close();
    }
}
