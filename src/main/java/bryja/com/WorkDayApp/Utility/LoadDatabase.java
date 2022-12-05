package bryja.com.WorkDayApp.Utility;
import bryja.com.WorkDayApp.Classes.*;
import bryja.com.WorkDayApp.Repository.TimeEntryRepository;
import bryja.com.WorkDayApp.Repository.UserRepository;
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
    CommandLineRunner initDatabase(WorkDayRepository repository, TimeEntryRepository rep2, UserRepository rep3) {

        return args -> {
           // log.info("Preloading " + repository.save(new WorkDay("1111")));
            log.info("Preloading " + repository.save(new WorkDay("07.09.2022")));
            log.info("Preloading " + repository.save(new WorkDay("07.10.2020")));
            log.info("Preloading " + repository.save(new WorkDay("01.02.2017")));
            log.info("Preloading " + repository.save(new WorkDay("22.04.2013")));
            log.info("Preloading " + repository.save(new WorkDay("11.09.2014")));
            log.info("Preloading " + repository.save(new WorkDay("11.09.2014")));
            log.info("Preloading " + rep3.save(new User("artur","testmail@mail.com","password")));

        };
    }
}
