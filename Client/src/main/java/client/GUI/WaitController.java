package client.GUI;

import DTO.JucatorDTO;
import Notification.*;
import Repos.RepoException;
import Serivces.IServices;
import Serivces.NotificationReceiver;
import Serivces.NotificationSubscriber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WaitController implements NotificationSubscriber {
    private MainController mainCtrl;
    private JucatorDTO crtJucator;
    private String crtLit;
    private NotificationReceiver receiver;
    private IServices server;
    @FXML
    Button StartButton;
    @FXML
    TextField PropunereField;
    Parent mainParent;
    @Override
    public void notificationReceived(Notification notification) {
        if(notification.getType()== NotificationType.STARTGAME){
            StartButton.setDisable(false);
            //this.crtLit=notification.getLit();
        }

    }

    public void setServer(IServices server) {
        this.server = server;
    }

    public void init(){
        StartButton.setDisable(true);
    }

    public void setReceiver(NotificationReceiver receiver) {
        this.receiver = receiver;
    }

    public void StartReceiver(){
        this.receiver.start(this);
    }
    public void setCrtJucator(JucatorDTO crtJucator) {
        this.crtJucator = crtJucator;
    }

    public void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void setMainParent(Parent mainParent) {
        this.mainParent = mainParent;
    }

    public void sendPropunere(String username,String propunere){
        try{
            this.server.SubmitPropunere(username,propunere);
        }catch (RepoException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void StartJoc(ActionEvent actionEvent) {
        sendPropunere(crtJucator.getUsername(),PropunereField.getText());
        this.receiver.setSubscriber(mainCtrl);
        //this.mainCtrl.setLitCrt(this.crtLit);
        this.mainCtrl.setReceiver(this.receiver);
        this.mainCtrl.initialiazeTabel();
        this.mainCtrl.setCrtJucator(this.crtJucator);
        Stage stage=new Stage();
        stage.setTitle("Main Window for "+crtJucator.getUsername());
        stage.setScene(new Scene(mainParent));
        stage.show();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }
}
