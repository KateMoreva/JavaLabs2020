//package curs.BD.cursBd;
//
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationListener;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.net.URL;
//import java.sql.SQLException;
//
//@Component
//public class StageListener implements ApplicationListener<StageReadyEvent> {
//    private final String appTitle;
//    private final Resource fxml;
//    private final ApplicationContext applicationContext;
//
//    public StageListener(@Value("${spring.ui.title}") String appTitle,
//                         @Value("classpath:/ui/MainWindow.fxml") Resource resource, ApplicationContext applicationContext) {
//        this.appTitle = appTitle;
//        this.fxml = resource;
//        this.applicationContext = applicationContext;
//    }
//
//    @Override
//    public void onApplicationEvent(StageReadyEvent stageReadyEvent) {
//        Stage stage = stageReadyEvent.getStage();
//        try {
//            URL url = this.fxml.getURL();
//            FXMLLoader fxmlLoader = new FXMLLoader(url);
//            fxmlLoader.setControllerFactory(applicationContext::getBean);
//            Parent root = fxmlLoader.load();
//            stage.setScene(new Scene(root, 800, 500));
//            stage.setTitle(this.appTitle);
//            stage.show();
////            DbHelper.addTestProduct();
//        }
//        catch (IOException e){
//           throw new RuntimeException(e);
//        }
//    }
//}
