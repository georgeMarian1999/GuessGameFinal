package client;

import Serivces.INotificationService;
import Serivces.IServices;
import Serivces.NotificationReceiver;
import client.GUI.LoginController;
import client.GUI.MainController;
import client.GUI.WaitController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartClient extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext factory = new ClassPathXmlApplicationContext("spring-client.xml");
        IServices server=(IServices) factory.getBean("implementation");
        NotificationReceiver notificationReceiver=factory.getBean("notificationReceiver",NotificationReceiver.class);

        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("LoginView.fxml"));
        Parent root=loader.load();


        LoginController ctrl =
                loader.<LoginController>getController();
        ctrl.setServer(server);

        FXMLLoader cloader=new FXMLLoader(getClass().getClassLoader().getResource("WaitView.fxml"));
        Parent waitParent=cloader.load();

        ctrl.setWaitParent(waitParent);

        WaitController waitController=cloader.<WaitController>getController();
        notificationReceiver.start(waitController);
        waitController.setReceiver(notificationReceiver);

        ctrl.setWaitCtrl(waitController);
        waitController.setServer(server);

        FXMLLoader mainLoader=new FXMLLoader(getClass().getClassLoader().getResource("MainView.fxml"));
        Parent mainParent=mainLoader.load();
        waitController.setMainParent(mainParent);
        MainController mainController=mainLoader.<MainController>getController();
        waitController.setMainCtrl(mainController);
        mainController.setServer(server);


        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
