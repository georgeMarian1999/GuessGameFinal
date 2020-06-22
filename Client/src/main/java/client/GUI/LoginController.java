package client.GUI;

import DTO.JucatorDTO;
import Model.Jucator;
import Repos.RepoException;
import Serivces.IServices;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LoginController {
    private IServices server;
    private WaitController waitCtrl;
    private JucatorDTO crtJucator;

    @FXML
    TextField TFUser;
    @FXML
    PasswordField PFPass;
    @FXML
    Button LoginButton;

    Parent WaitParent;

    public void setWaitCtrl(WaitController waitCtrl) {
        this.waitCtrl = waitCtrl;
    }

    public void setWaitParent(Parent waitParent) {
        WaitParent = waitParent;
    }

    public void setServer(IServices server) {
        this.server = server;
    }

    public void SetParent(Parent parent1){
        WaitParent=parent1;
    }


    public void pressCancel(ActionEvent event){
        System.exit(0);
    }

    public void setCrtJucator(JucatorDTO crtJucator) {
        this.crtJucator = crtJucator;
    }

    @FXML
    public void pressLogin(ActionEvent actionEvent){
        String user=TFUser.getText();
        String pass=PFPass.getText();
        crtJucator=new JucatorDTO(user,pass);

        try{
            server.login(crtJucator);
            //waitCtrl.StartReceiver();
            waitCtrl.init();
            waitCtrl.setCrtJucator(crtJucator);
            Stage stage=new Stage();
            stage.setTitle("Wait Window for "+crtJucator.getUsername());
            stage.setScene(new Scene(WaitParent));
            stage.show();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }catch(RepoException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Authentication failure");
            alert.setContentText("An error occured :"+e.getMessage());
            alert.showAndWait();
        }
    }



}
