package tarea_productor_consumidor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLOutput;

public class MainApp  {
    public static void main(String[] args) {
        Monitor m = new Monitor();
        Productor productor = new Productor(m);
        Consumidor consumidor = new Consumidor(m);
        productor.start();
        consumidor.start();

    }
}
