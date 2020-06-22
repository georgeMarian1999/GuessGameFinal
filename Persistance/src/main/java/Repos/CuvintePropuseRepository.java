package Repos;

import DTO.CuvintePropuse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CuvintePropuseRepository {
    private JDBC utils;

    public CuvintePropuseRepository(Properties props){
        this.utils=new JDBC(props);
    }
    public List<CuvintePropuse> getPropuneri() throws RepoException{
        List<CuvintePropuse> result=new ArrayList<>();
        Connection con=utils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("Select idJuc,CuvPropus FROM CuvintePropuse")){
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                while(resultSet.next()){
                    CuvintePropuse cuv=new CuvintePropuse(resultSet.getInt("idJuc"),resultSet.getString("CuvPropus"));
                    result.add(cuv);
                }
            }
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
        return result;
    }
    public List<CuvintePropuse> getPropuneriPerGame(int idJoc) throws RepoException{
        List<CuvintePropuse> result=new ArrayList<>();
        Connection con=utils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("Select idJuc,CuvPropus FROM CuvintePropuse where idJoc=?")){
            preparedStatement.setInt(1,idJoc);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                while(resultSet.next()){
                    CuvintePropuse cuv=new CuvintePropuse(resultSet.getInt("idJuc"),resultSet.getString("CuvPropus"));
                    result.add(cuv);
                }
            }
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
        return result;
    }
    public int searchPropunere(String propunere,int idJucPropunere,int idJoc) throws RepoException{
        Connection con=utils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("Select CuvPropus FROM CuvintePropuse where idJuc=? AND idJoc=?")){
            preparedStatement.setInt(1,idJucPropunere);
            preparedStatement.setInt(2,idJoc);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next()){
                    String cuvProp=resultSet.getString("CuvPropus");
                    if(cuvProp.equals(propunere)){
                        return 5;
                    }
                    else{
                    char prop=propunere.charAt(0);
                    int count = 0;

                    for (int i = 0; i < cuvProp.length(); i++) {
                        if (cuvProp.charAt(i) == prop) {
                            count++;
                        }
                    }
                        return count;
                    }

                }
                else return 0;
            }
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
    }
    public int getMaxId()throws RepoException{
        Connection con=utils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("SELECT MAX(idPropunere) as max from CuvintePropuse")){
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt("max");
                }
                else return 0;
            }
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
    }
    public void addPropunere(CuvintePropuse propunere,int idJoc) throws RepoException{
        Connection con=utils.getConnection();
        int idNew=getMaxId()+1;
        try(PreparedStatement preparedStatement=con.prepareStatement("INSERT INTO CuvintePropuse values(?,?,?,?)")){
            preparedStatement.setInt(1,propunere.getIdJuc());
            preparedStatement.setString(2,propunere.getCuvantul());
            preparedStatement.setInt(3,idNew);
            preparedStatement.setInt(4,idJoc);
            int result=preparedStatement.executeUpdate();
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
    }
}
