package tarea_productor_consumidor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Productor extends Thread{
    Monitor monitor;
    static String fechaSistema = "";

    public Productor(Monitor m){
        this.monitor = m;
    }

    @Override
    public void run() {
        int i = 1;
        while(Thread.currentThread().isAlive()) {
            int nivel = (int) Math.round(Math.random()*3);
            String rutaArchivo = "C://td//Persistencia//patron_productor_consumidor/"+"archivo"+i+".txt";
            File archivo = new File(rutaArchivo);
            try {
                archivo.createNewFile();
                guardarRegistroLog("registro", nivel, "accion",rutaArchivo);
                monitor.lanzar(archivo);
                System.out.println("se ha creado el archivo "+i);
                sleep(2000);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            i++;
        }
    }

    private static void cargarFechaSistema() {
        String diaN = "";
        String mesN = "";
        String añoN = "";

        Calendar cal1 = Calendar.getInstance();

        int  dia = cal1.get(Calendar.DATE);
        int mes = cal1.get(Calendar.MONTH)+1;
        int año = cal1.get(Calendar.YEAR);
        if(dia < 10){
            diaN+="0"+dia;
        }
        else{
            diaN+=""+dia;
        }
        if(mes < 10){
            mesN+="0"+mes;
        }
        else{
            mesN+=""+mes;
        }
        fechaSistema = año+"-"+mesN+"-"+diaN;
    }
    public static void guardarRegistroLog(String mensajeLog, int nivel, String accion, String rutaArchivo) {
        String log = "";
        Logger LOGGER = Logger.getLogger(accion);
        FileHandler fileHandler =  null;
        cargarFechaSistema();
        try {
            fileHandler = new FileHandler(rutaArchivo,false);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            switch (nivel) {
                case 1:
                    LOGGER.log(Level.INFO,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                case 2:
                    LOGGER.log(Level.WARNING,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                case 3:
                    LOGGER.log(Level.SEVERE,accion+","+mensajeLog+","+fechaSistema) ;
                    break;

                default:
                    break;
            }

        } catch (SecurityException e) {

            //LOGGER.log(Level.SEVERE,e.getMessage());
            //e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //LOGGER.log(Level.SEVERE,e.getMessage());
            //e.printStackTrace();
        }
        finally {

            fileHandler.close();
        }
    }
}
