package ge.rodichev.eventgenerator;

import ge.rodichev.eventgenerator.kafka.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;

@SpringBootApplication
public class EventGeneratorApplication {
    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KafkaConsumerConfig.class);


    public static void main(String[] args) {
        SpringApplication.run(EventGeneratorApplication.class, args);
    }

}
