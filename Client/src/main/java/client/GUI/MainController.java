package client.GUI;

import DTO.Clasament;
import DTO.CuvintePropuse;
import DTO.JucatorDTO;
import Model.Raspuns;
import Notification.Notification;
import Repos.RepoException;
import Serivces.IServices;
import Serivces.NotificationReceiver;
import Serivces.NotificationSubscriber;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Notification.*;
import javafx.stage.Stage;

import java.util.List;

public class MainController implements NotificationSubscriber {
    private JucatorDTO crtJucator;
    private IServices server;
    private NotificationReceiver receiver;
    private String litCrt;

    ObservableList<Clasament> clasament= FXCollections.observableArrayList();
    ObservableList<CuvintePropuse> propuneri = FXCollections.observableArrayList();
    @FXML
    Button SubmitButton;
    @FXML
    Button LogoutButton;
    @FXML
    TextArea LiteraTextArea;
    @FXML
    TextArea PunctajRundaText;
    @FXML
    TextArea Clasament;
    @FXML
    TableView<Clasament> ClasamentTabel;
    @FXML
    TableColumn<Clasament,Integer> JucatorCol;
    @FXML
    TableColumn<Clasament,Integer> PunctajCol;
    @FXML
    TableView<CuvintePropuse> PropuneriTable;
    @FXML
    TableColumn<CuvintePropuse,Integer> ColId;
    @FXML
    TableColumn<CuvintePropuse,Integer> ColProp;
    @FXML
    TextField PropunereField;

    public void initialiazeTabel(){
        this.LiteraTextArea.setText("Se asteapta propunerile "+this.litCrt);
            JucatorCol.setCellValueFactory(new PropertyValueFactory<Clasament,Integer>("username"));
            PunctajCol.setCellValueFactory(new PropertyValueFactory<Clasament,Integer>("punctaj"));
            ColId.setCellValueFactory(new PropertyValueFactory<CuvintePropuse,Integer>("idJuc"));
            ColProp.setCellValueFactory(new PropertyValueFactory<CuvintePropuse,Integer>("cuvantul"));
    }

    public MainController(){

    }

    public void setCrtJucator(JucatorDTO crtJucator) {
        this.crtJucator = crtJucator;
    }

    public void setLitCrt(String litCrt) {
        this.litCrt = litCrt;
    }

    public void setReceiver(NotificationReceiver receiver) {
        this.receiver = receiver;
    }

    public void setServer(IServices server) {
        this.server = server;
    }

    @Override
    public void notificationReceived(Notification notification) {
        if(notification.getType()== NotificationType.LEAVER){
            leaver();
        }
        if(notification.getType()==NotificationType.PUNCTAJ){
            punctaj();
        }
        if(notification.getType()==NotificationType.STOPGAME){
            stopJoc();
        }
        if(notification.getType()==NotificationType.GETPROPTAB){
            getPropuneri();
        }
    }
    public void getPropuneri(){
        Platform.runLater(()->{
            try{
                List<CuvintePropuse> propuneri=this.server.getPropuneri();
                this.propuneri.addAll(propuneri);
                this.PropuneriTable.setItems(this.propuneri);
            }catch (RepoException ex){
                System.out.println(ex.getMessage());
            }

        });
    }
    private void punctaj(){
        Platform.runLater(()->{
                this.PunctajRundaText.setText("");
        this.PunctajRundaText.setText("Punctajul pe runda "+getPunctaj());
        this.SubmitButton.setDisable(false);
        });
    }
    private void stopJoc() {
        Platform.runLater(()->{
            getClasament();
            this.SubmitButton.setDisable(true);
            this.LiteraTextArea.setText("");
        });
    }

    public int getPunctaj() {
        try {
            return server.getPunctaj(crtJucator.getUsername());
        } catch (RepoException ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Problema cand se cauta punctajul pentru runda curenta");
            alert.setHeaderText("Error");
            alert.setContentText("Error" + ex.getMessage() + " " + ex.getCause());
            alert.showAndWait();
        }
    return -1;
    }
    private void leaver() {
        Platform.runLater(()->{
            this.SubmitButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Leaver :(");
            alert.setHeaderText("A player left!!");
            alert.setContentText("Please logout");
            alert.showAndWait();
        });

    }

    public void logout(ActionEvent actionEvent) throws RepoException{
        this.receiver.stop();
        server.logout(crtJucator);
        Stage stage = (Stage) LogoutButton.getScene().getWindow();
        stage.close();
    }

    public void submit(ActionEvent actionEvent) {
       try{
            this.LiteraTextArea.setText("Se asteapta sa ghiceasca toti");
            this.SubmitButton.setDisable(true);
            CuvintePropuse cuvintePropuse=new CuvintePropuse(this.PropuneriTable.getSelectionModel().getSelectedItem().getIdJuc(),this.PropuneriTable.getSelectionModel().getSelectedItem().getCuvantul());
            this.server.submitRaspuns(new Raspuns(crtJucator.getUsername(),cuvintePropuse.getIdJuc(),this.PropunereField.getText()));
        }catch (RepoException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Problema cand se trimite raspuns");
            alert.setHeaderText("Error");
            alert.setContentText("Error"+ex.getMessage()+" "+ex.getCause());
            alert.showAndWait();
        }

    }



    private void getClasament() {
        try{
            this.clasament.clear();
            List<Clasament> clasamentList=this.server.getClasament();
            this.clasament.addAll(clasamentList);
            ClasamentTabel.setItems(this.clasament);
        }catch (RepoException ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Problema cand se ia clasamentul");
            alert.setHeaderText("Error");
            alert.setContentText("Error"+ex.getMessage()+" "+ex.getCause());
            alert.showAndWait();
        }

    }
}
