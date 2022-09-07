package bryja.com.WorkDayApp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class WorkDayControllerTests {

    @Autowired
    WorkDayController controller;

    @Test
    public void contextLoads() {
        Assertions.assertThat(controller).isNotNull();
    }

}

