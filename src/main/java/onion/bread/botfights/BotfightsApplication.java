package onion.bread.botfights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;


@SpringBootApplication
public class BotfightsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BotfightsApplication.class, args);
    }


}
