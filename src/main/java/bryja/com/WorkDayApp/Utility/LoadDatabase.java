package bryja.com.WorkDayApp.Utility;
import bryja.com.WorkDayApp.Classes.*;
import bryja.com.WorkDayApp.Repository.WorkDayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    public static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(WorkDayRepository repository) {

        return args -> {
           // log.info("Preloading " + repository.save(new WorkDay("1111")));
            log.info("Preloading " + repository.save(new WorkDay("07.09.2022")));
        };
    }
}