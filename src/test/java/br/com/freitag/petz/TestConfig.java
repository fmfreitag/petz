package br.com.freitag.petz;

import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.SimpleThreadScope;
import org.springframework.stereotype.Component;

@Component
public class TestConfig {

	@Bean
    public CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.addScope("session", new SimpleThreadScope());
        return configurer;
    }
	
}
