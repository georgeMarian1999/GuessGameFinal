package Services;

import DTO.Clasament;
import DTO.CuvintePropuse;
import DTO.RaspunsRunda;
import Model.Raspuns;
import Repos.CuvintePropuseRepository;
import Repos.JocRepository;
import Repos.RepoException;
import Repos.RundaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/games")
public class JocController {

    ApplicationContext context=new ClassPathXmlApplicationContext("Rest.xml");
    private RundaRepository rundaRepository=(RundaRepository)context.getBean("RundaRepository");
    private CuvintePropuseRepository cuvintePropuseRepository=(CuvintePropuseRepository)context.getBean("CuvintePropuseRepo");

    @RequestMapping(value = "/propuneri/{id}",method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable int id){
        try{
            List<CuvintePropuse> clasamentList=this.cuvintePropuseRepository.getPropuneriPerGame(id);
            return new ResponseEntity<>(clasamentList,HttpStatus.OK);
        }catch (RepoException ex){
            return new ResponseEntity<String>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{idJoc}/jucator/{idJucator}")
    public ResponseEntity<?> getForEach(@PathVariable int idJoc,@PathVariable int idJucator){
        try{
            List<RaspunsRunda> raspunsuri=this.rundaRepository.getRaspunsForPlayerPerGame(idJucator,idJoc);
            return new ResponseEntity<>(raspunsuri,HttpStatus.OK);
        }catch (RepoException ex){
            return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


}
