package timbo.spring.lambda;

import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import timbo.spring.lambda.controller.TestController;

@EnableWebMvc
@Import({TestController.class})
public class AppConfig {


}
