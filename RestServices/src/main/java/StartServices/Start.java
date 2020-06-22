package StartServices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("Services")
@SpringBootApplication
public class Start {
    public static void main(String[] args){
        SpringApplication.run(Start.class,args);
    }
}
