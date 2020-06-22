import Repos.RepoException;
import Serivces.IServices;
import Service.Service;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartServer {
    public static void main(String[] args){
        ApplicationContext factory = new ClassPathXmlApplicationContext("spring-server.xml");
    }
}
