package Repos;

import Model.Jucator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class JucatorRepository implements CRUDRepository<Integer, Jucator>{
    private JDBC utils;


    public JucatorRepository(Properties props){
        utils=new JDBC(props);
    }


    @Override
    public int size() throws RepoException{

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Jucator ")) {
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

    @Override
    public void save(Jucator entity)throws RepoException {

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Jucator values (?,?,?)")){
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getPassword());
            int result=preStmt.executeUpdate();
        }catch(SQLException ex){
           throw new RepoException(ex.getMessage());
        }

    }

    @Override
    public void delete(Integer integer)throws RepoException {

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Jucator where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }
        catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }

    }

    @Override
    public void update(Integer integer, Jucator entity)throws RepoException {

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Jucator set id=?,username=?,password=?  where id=?")){
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getPassword());
            preStmt.setInt(4,integer);
        }catch (SQLException ex){
           throw new RepoException(ex.getMessage());
        }
    }

    @Override
    public Jucator findOne(Integer integer)throws RepoException {

        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Jucator where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()){
                if (result.next()){
                    Integer id=result.getInt("id");
                    String username,password;
                    username=result.getString("username");
                    password=result.getString("password");
                    Jucator P=new Jucator(id,username,password);
                    return P;
                }
            }
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
        return null;
    }
    public int findIdByUsername(String username) throws RepoException{
        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select id from Jucator where username=?")){
            preStmt.setString(1,username);
            try(ResultSet result=preStmt.executeQuery()){
                if (result.next()){
                    Integer id=result.getInt("id");
                    return id;
                }
            }
        }catch (SQLException ex){
            throw new RepoException(ex.getMessage());
        }
        return 0;
    }

    @Override
    public Iterable<Jucator> findAll() throws RepoException{
        Connection con=utils.getConnection();
        List<Jucator> Jucatori=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Jucator")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String username,password;
                    username=result.getString("username");
                    password=result.getString("password");
                    Jucator P=new Jucator(id,username,password);
                    Jucatori.add(P);
                }
            }
        } catch (SQLException e) {
           throw new RepoException(e.getMessage());
        }
        return Jucatori;
    }



    public boolean LocalLogin(String username, String Password)throws RepoException {
        Connection con=utils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select id,username,password from Jucator where username=? AND password=?")){
            preStmt.setString(1,username);
            preStmt.setString(2,Password);
            try(ResultSet result=preStmt.executeQuery()){
                if(result.next()){
                    return true;
                }
            }
        }catch(SQLException ex){
            throw new RepoException(ex.getMessage());
        }
        return false;
    }

}
