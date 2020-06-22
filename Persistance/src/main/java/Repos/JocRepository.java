package Repos;

import DTO.Clasament;
import Model.Joc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class JocRepository implements CRUDRepository<Integer, Joc>{

    private static SessionFactory sessionFactory;
    static void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.out.println("Exceptie "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    public void close() {
        if ( sessionFactory != null ) {
            sessionFactory.close();
        }
    }

    public JocRepository(){

    }
    @Override
    public int size() {
        if(sessionFactory==null){
            initialize();;
        }
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            String hql="SELECT new Joc(J.id) FROM Joc J";
            Query query=session.createQuery(hql);
            List result=query.getResultList();
            return result.size();
        }
    }

    @Override
    public void save(Joc entity) {
        if(sessionFactory==null){
            initialize();
        }
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Integer integer) {
        if(sessionFactory==null){
            initialize();
        }
        try(Session session=sessionFactory.openSession()){
            String hql="DELETE FROM Joc J where J.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",integer);
            int result=query.executeUpdate();
            if(result>0){
                System.out.println("Joc cu id "+integer+" sters cu succes");
            }
            session.close();
        }
    }

    @Override
    public void update(Integer integer, Joc entity) {

    }

    @Override
    public Joc findOne(Integer integer) {
        if(sessionFactory==null){
            initialize();
        }
        try(Session session=sessionFactory.openSession()){
            String hql="SELECT NEW Joc(J.id) FROM Joc J WHERE J.id=:id";
            Query query=session.createQuery(hql);
            query.setParameter("id",integer);
            Joc result=(Joc)query.getSingleResult();
            return result;
        }
    }
    public int getMaxId(){
        if(sessionFactory==null){
            initialize();
        }
        try(Session session=sessionFactory.openSession()){
            String hql="SELECT MAX(J.id) FROM Joc J";
            Query query=session.createQuery(hql);
            int id=(int)query.getSingleResult();
            return id;
        }
    }
    @Override
    public Iterable<Joc> findAll() {
        if(sessionFactory==null){
            initialize();
        }
        try(Session session=sessionFactory.openSession()){
            String hql="SELECT NEW Joc(J.id) FROM Joc J ";
            Query query=session.createQuery(hql);

            List result=query.getResultList();
            List<Joc> jocuri=new ArrayList<>();
            for (Object res:result
                 ) {
                jocuri.add((Joc)res);
            }
            return jocuri;
        }
    }
}
