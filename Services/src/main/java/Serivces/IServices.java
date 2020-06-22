package Serivces;

import DTO.Clasament;
import DTO.CuvintePropuse;
import DTO.JucatorDTO;
import Model.Jucator;
import Model.Raspuns;
import Repos.RepoException;

import java.util.List;

public interface IServices {
    void login(JucatorDTO jucator) throws RepoException;
    void logout(JucatorDTO jucator) throws RepoException;
    void submitRaspuns(Raspuns infoSubmite) throws RepoException;
    int getPunctaj(String Jucator)throws RepoException;
    List<Clasament> getClasament() throws RepoException;
    void SubmitPropunere(String username,String propunere) throws RepoException;
    List<CuvintePropuse> getPropuneri()throws RepoException;
}
