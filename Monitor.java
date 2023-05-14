package tarea_productor_consumidor;

import javafx.scene.media.SubtitleTrack;

import java.io.File;
import java.io.FileWriter;
import java.sql.SQLOutput;

public class Monitor {
    private File buffer[] = new File[10];
    private int siguiente = 0;
    //flags para saber el estado del buffer
    private boolean estaLlena  = false;
    private boolean estaVacia  = true;

    public synchronized File recoger(){
        while (estaVacia == true){
            try {
                System.out.println("NO HAY ARCHIVOS POR CONSUMIR");
                wait();
            }catch (InterruptedException e){

            }
        }

        siguiente--;

        if(siguiente == 0)
            estaVacia = true;

        estaLlena = false;
        notify();

        return buffer[siguiente];
    }

    public synchronized void lanzar(File archivo){
        while (estaLlena == true){
            try{
                System.out.println("EL BUFFER ESTA LLENO!!!!!!!");
                wait();
            }catch (InterruptedException e){

            }
        }
        buffer[siguiente] = archivo;
        siguiente++;

        if(siguiente == 9){
            estaLlena = true;
        }

        estaVacia = false;
        notify();
    }
}
