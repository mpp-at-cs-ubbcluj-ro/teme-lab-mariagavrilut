package ro.mpp2024.laborator4;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ro.mpp2024.laborator4.ctrl.ComputerRepairShopController;
import ro.mpp2024.laborator4.services.ComputerRepairServices;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ro.mpp2024.laborator4.services.ServicesException;

public class MainFXSpring extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            //System.out.println(getClass().getResource("RepairShopWindow.fxml"));

            //FXMLLoader loader = new FXMLLoader(getClass().getResource("RepairShopWindow.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ro/mpp2024/laborator4/RepairShopWindow.fxml"));
            Parent root = loader.load();
            ComputerRepairShopController ctrl = loader.getController();
            ctrl.setService(getService());
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Computer Repairs Shop");
            primaryStage.show();
        }catch(Exception e){
            e.printStackTrace();
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error ");
            alert.setContentText("Error while starting app "+e);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    static ComputerRepairServices getService() throws ServicesException {
        // pentru configurare folosind XML
        //ApplicationContext context=new ClassPathXmlApplicationContext("RepairShopConfig.xml");

        //bun
        ApplicationContext context = new ClassPathXmlApplicationContext("ro/mpp2024/laborator4/RepairShopConfig.xml");

        //pentru configurare folosind JavaConfig
        //ApplicationContext context=new AnnotationConfigApplicationContext(RepairShopConfig.class);
        ComputerRepairServices services=context.getBean(ComputerRepairServices.class);
        return services;

    }
}
