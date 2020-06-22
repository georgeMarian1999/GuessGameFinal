package Implementations;

import DTO.Clasament;
import DTO.CuvintePropuse;
import DTO.JucatorDTO;
import Model.Jucator;
import Model.Raspuns;
import Model.Runda;
import Repos.RepoException;
import Serivces.IServices;
import Service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ServicesIMPL implements IServices {
    private Service service;
    private Map<String,JucatorDTO> loggedPlayers;
    private NotificationService notificationService;
    private int CrtRound=1;
    private int NrResponses=0;
    private List<Raspuns> crtRaspunsuri;
    private String crtLitera;
    private int PropuneriInitiale=0;
    private int crtGame;
    public ServicesIMPL(Service service1,NotificationService notificationService1){
        this.service=service1;
        this.notificationService=notificationService1;
        this.loggedPlayers=new ConcurrentHashMap<>();
        this.crtRaspunsuri=new ArrayList<>();
        this.crtGame=this.service.GetGameID()+1;
    }

    @Override
    public synchronized void login(JucatorDTO jucator) throws RepoException {
        boolean isJucator=this.service.LocalLogin(jucator.getUsername(),jucator.getPassword());
        if(isJucator){
            if(loggedPlayers.get(jucator.getUsername())!=null){
                throw new RepoException("User is already logged in");
            }
            loggedPlayers.put(jucator.getUsername(),jucator);
        }else{
            System.out.println("Authentification failed");
            throw new RepoException("Wrong username or password");
        }
        if(loggedPlayers.size()==3){
            notificationService.startJoc(this.crtLitera);
        }
    }



    @Override
    public void logout(JucatorDTO jucator) throws RepoException {
        JucatorDTO localClient=loggedPlayers.remove(jucator.getUsername());
        if(localClient==null){
            throw new RepoException("User "+jucator.getUsername()+" is not logged in");
        }
        else System.out.println("Logout succesful for "+jucator.getUsername());
        //if(this.CrtRound<3){
          //  this.notificationService.leaver();
        //}
    }

    @Override
    public void submitRaspuns(Raspuns infoSubmit) throws RepoException {
        this.NrResponses++;
        this.crtRaspunsuri.add(infoSubmit);

        if(this.NrResponses==3){
            CalculeazaPunctaje();
            notificationService.punctaj();
            if(this.CrtRound==4){
                this.NrResponses=0;
                this.service.saveJoc(this.crtGame);
                notificationService.stopJoc();
            }
        }

    }

    private void CalculeazaPunctaje() throws RepoException{
        for (Raspuns ras:this.crtRaspunsuri
             ) {
            int punctajJuc=this.service.getNrofApp(ras.getIdPropus(),ras.getCuvGhicit(),this.crtGame);
            Runda runda=new Runda(this.service.getIdFromUser(ras.getUsernameJuc()),this.crtGame,ras.getIdPropus(),ras.getCuvGhicit(),punctajJuc,this.CrtRound);
            this.service.saveRundaInfo(runda);
        }
        this.crtRaspunsuri.clear();
        this.NrResponses=0;
        this.CrtRound++;
    }

    @Override
    public int getPunctaj(String username) throws RepoException {
            return this.service.getPunctajForPlayer(username,this.CrtRound-1,this.crtGame);
    }

    @Override
    public List<Clasament> getClasament() throws RepoException {
        List<Clasament> result=new ArrayList<>();
        return this.service.getClasament(this.crtGame);
    }

    @Override
    public void SubmitPropunere(String username, String propunere) throws RepoException {
        this.PropuneriInitiale++;
        this.service.addPropunere(new CuvintePropuse(this.service.getIdFromUser(username),propunere),this.crtGame);
        if(this.PropuneriInitiale==3){
            notificationService.getPropuneri();
        }
    }
    public String convert(String propunere){
        StringBuilder newWord=new StringBuilder(propunere);

        for(int i=0;i<propunere.length();i++){
            newWord.setCharAt(i,'_');
        }
        return newWord.toString();
    }

    @Override
    public List<CuvintePropuse> getPropuneri() throws RepoException {
        List<CuvintePropuse> result=this.service.getPropuneriPerGame(this.crtGame);
        for (CuvintePropuse cuv:result
             ) {
            cuv.setCuvantul(convert(cuv.getCuvantul()));
        }
        return result;
    }
}
