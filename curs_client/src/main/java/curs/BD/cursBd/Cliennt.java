package curs.BD.cursBd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Cliennt extends Application{

        @Override
        public void start(Stage primaryStage) throws Exception{
            Parent root = FXMLLoader.load(getClass().getResource("/mainWindow.fxml"));
            primaryStage.setTitle("База данных магазина");
            primaryStage.setScene(new Scene(root, 730, 430));
            primaryStage.show();
        }


        public static void main(String[] args){
            Application.launch(args);
        }
}
