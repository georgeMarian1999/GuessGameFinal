package Service;

import DTO.RaspunsRunda;
import Model.Joc;
import Model.Runda;
import Repos.JocRepository;
import Repos.RepoException;
import Repos.RundaRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestHibernate {
    public static void main(String[] args){
        ApplicationContext factory=new ClassPathXmlApplicationContext("spring-server.xml");
        Service service=(Service) factory.getBean("serviceLocal");
        RundaRepository rundaRepository=(RundaRepository)factory.getBean("RundaRepository");
        //Runda runda=new Runda(13,41,"","","","",0,2);
        try{
            for (RaspunsRunda r:rundaRepository.getRaspunsForPlayerPerGame(1,3)
                 ) {

            }
        }catch (RepoException e){
            System.out.println(e.getMessage());
        }

    }
}
