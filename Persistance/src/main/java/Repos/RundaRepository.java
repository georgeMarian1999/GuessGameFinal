package Repos;

import DTO.Clasament;
import DTO.RaspunsRunda;
import Model.Jucator;
import Model.Raspuns;
import Model.Runda;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RundaRepository {
    private JDBC utils;
    public RundaRepository(Properties props){
        utils=new JDBC(props);
    }
    private Properties getProps(){
        Properties props = new Properties();
        try {
            props.load(new FileReader("bd.config"));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public int getPunctajFinal(int idJucator,int idJoc) throws RepoException{

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select sum(Punctaj) as total from Runda where JucatorId=? AND JocId=?")){
            preStmt.setInt(1,idJucator);
            preStmt.setInt(2,idJoc);
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    int total=result.getInt("total");
                    return total;
                }
                else return -1;
            }
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
    }
    public List<RaspunsRunda> getRaspunsForPlayerPerGame(int idJuc,int idJoc)throws RepoException{
        List<RaspunsRunda> result=new ArrayList<>();
        Connection con=utils.getConnection();
        try(PreparedStatement preparedStatement=con.prepareStatement("SELECT R.JucatorId,R.Punctaj,R.cuvGhicit,R.NrRunda FROM Jucator Juc INNER JOIN Runda R on Juc.id = R.JucatorId INNER JOIN Joc J on R.JocId = J.id where J.id=? AND Juc.id=? GROUP BY R.NrRunda")){
            preparedStatement.setInt(1,idJoc);
            preparedStatement.setInt(2,idJuc);
            try(ResultSet resultSet=preparedStatement.executeQuery()){
                while(resultSet.next()){
                    RaspunsRunda runda=new RaspunsRunda(resultSet.getString("cuvGhicit"),resultSet.getInt("JucatorId"),resultSet.getInt("Punctaj"),resultSet.getInt("NrRunda"));
                    result.add(runda);
                }
            }
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
        return result;
    }
    public List<Clasament> getClasamentPerGame(int idJoc) throws RepoException{
        List<Clasament> result=new ArrayList<>();
        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("SELECT Juc.username,SUM(R.Punctaj) as suma FROM Runda R inner join Jucator Juc on Juc.id=R.JucatorId inner join Joc J on J.id=R.JocId where J.id=? GROUP BY Juc.username")){
            preStmt.setInt(1,idJoc);
            try(ResultSet resultSet=preStmt.executeQuery()){
                while(resultSet.next()){
                    String username=resultSet.getString("username");
                    int punctaj=resultSet.getInt("suma");
                    Clasament clas=new Clasament(username,punctaj);
                    result.add(clas);
                }
            }
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
        return result;
    }
    public int size() throws RepoException{

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Runda ")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            throw new RepoException(ex.getMessage());
        }
        return 0;
    }

    public void save(Runda entity)throws RepoException {

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Runda values (?,?,?,?,?,?)")){
            preStmt.setInt(1,entity.getJucatorId());
            preStmt.setInt(2,entity.getJocId());
            preStmt.setInt(3,entity.getPunctaj());
            preStmt.setInt(4,entity.getNrRunda());
            preStmt.setInt(5,entity.getIdJucGhicit());
            preStmt.setString(6,entity.getCuvGhicit());
            int result=preStmt.executeUpdate();
        }catch(SQLException ex){
            throw new RepoException(ex.getMessage());
        }

    }


    public int getPunctajForPlayer(int idJucator,int nrRunda,int idJoc) throws RepoException{

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select Punctaj from Runda where JucatorId=? AND NrRunda=? AND JocId=?")){
            preStmt.setInt(1,idJucator);
            preStmt.setInt(2,nrRunda);
            preStmt.setInt(3,idJoc);
            try(ResultSet resultSet=preStmt.executeQuery()){
                if(resultSet.next()){
                    int punctaj=resultSet.getInt("Punctaj");
                    return punctaj;
                }
                else return -2;
            }
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
    }

}
