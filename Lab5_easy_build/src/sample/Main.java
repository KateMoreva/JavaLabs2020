package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.dataBase.DbHelper;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ui/mainWindow.fxml"));
        primaryStage.setTitle("База данных магазина");
        primaryStage.setScene(new Scene(root, 730, 430));
        primaryStage.show();
        DbHelper.addTestProduct();

    }


    public static void main(String[] args){
        launch(args);
    }
}
