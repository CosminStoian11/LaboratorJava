package ProiectLaborator.config;

import ProiectLaborator.mapper.CategoryMapper;
import ProiectLaborator.mapper.CategoryMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MapperConfiguration {

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapperImpl();
    }}