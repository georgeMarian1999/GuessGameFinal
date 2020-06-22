package Service;

import DTO.Clasament;
import DTO.CuvintePropuse;
import Model.Joc;
import Model.Runda;
import Repos.*;

import java.util.List;

public class Service {
    private JocRepository jocRepository;
    private JucatorRepository jucatorRepository;
    private RundaRepository rundaRepository;
    private CuvintePropuseRepository cuvintePropuseRepository;
    public Service(){

    }
    public Service(JocRepository jocRepository1,JucatorRepository jucatorRepository1,RundaRepository rundaRepository1,CuvintePropuseRepository cuvintePropuseRepository1){
        this.jocRepository=jocRepository1;
        this.jucatorRepository=jucatorRepository1;
        this.rundaRepository=rundaRepository1;
        this.cuvintePropuseRepository=cuvintePropuseRepository1;
    }
    public void saveRundaInfo(Runda runda)throws RepoException {
        this.rundaRepository.save(runda);
    }
    public void saveJoc(int idJoc){
        Joc joc=new Joc(idJoc);
        this.jocRepository.save(joc);
    }
    public boolean LocalLogin(String username,String password)throws RepoException{
        return this.jucatorRepository.LocalLogin(username, password);
    }
    public int getPunctajForPlayer(String username,int nrRunda,int JocId)throws RepoException{
        return this.rundaRepository.getPunctajForPlayer(this.jucatorRepository.findIdByUsername(username),nrRunda,JocId);
    }
    public int getIdFromUser(String username) throws RepoException{
        return this.jucatorRepository.findIdByUsername(username);
    }
    public int getPunctajFinal(int idJucator,int idJoc) throws RepoException{
        return this.rundaRepository.getPunctajFinal(idJucator,idJoc);
    }
    public int GetGameID(){
        return this.jocRepository.getMaxId();
    }

    public List<Clasament> getClasament(int idJoc) throws RepoException{
        return this.rundaRepository.getClasamentPerGame(idJoc);
    }
    public List<CuvintePropuse> getPropuneri() throws RepoException{
        return this.cuvintePropuseRepository.getPropuneri();
    }
    public int getNrofApp(int idJuc,String propunere,int idJoc) throws RepoException{
        return this.cuvintePropuseRepository.searchPropunere(propunere,idJuc,idJoc);
    }
    public void addPropunere(CuvintePropuse propunere,int idJoc) throws RepoException{
        this.cuvintePropuseRepository.addPropunere(propunere,idJoc);
    }
    public List<CuvintePropuse> getPropuneriPerGame(int idGame) throws RepoException{
        return this.cuvintePropuseRepository.getPropuneriPerGame(idGame);
    }
    public int getPunctaj(String prop,int idJucProp,int idJoc) throws RepoException{
        return this.cuvintePropuseRepository.searchPropunere(prop,idJucProp,idJoc);
    }
}
