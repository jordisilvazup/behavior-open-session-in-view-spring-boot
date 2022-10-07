package br.com.zup.edu.library.shared.errorhandling;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;
import org.zalando.problem.jackson.ProblemModule;

@Component
public class ProblemDetailsCustomizer implements Jackson2ObjectMapperBuilderCustomizer {

    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        ProblemModule problemModule = new ProblemModule().withStackTraces();
        jacksonObjectMapperBuilder.modules(problemModule);
    }
}
