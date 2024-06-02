package be.kdg.team9.integration4.config;

import be.kdg.team9.integration4.converters.QuestionDtoConverter;
import be.kdg.team9.integration4.converters.SurveyDtoConverter;
import be.kdg.team9.integration4.service.OptionService;
import be.kdg.team9.integration4.utils.LogRunner;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        // Apply configuration to the modelMapper instance
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT); // Example: Set strict matching strategy
        return modelMapper;
    }

    @Bean
    public QuestionDtoConverter questionDtoConverter() {
        return new QuestionDtoConverter();
    }

    @Bean
    public Logger getLogger() {
        return LoggerFactory.getLogger(LogRunner.class);
    }
}
