package br.com.freitag.petz.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "resttemplate")
@Getter
@Setter
public class RestTemplateConfig {

	@Value("${timeout.connect:30}")
	private int connectTimeout;

	@Value("${timeout.read:30}")
	private int readTimeout;

	public Duration getDurationTimeoutConnect() {
		return Duration.ofSeconds(connectTimeout);
	}

	public Duration getDurationTimeoutRead() {
		return Duration.ofSeconds(readTimeout);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}
}
