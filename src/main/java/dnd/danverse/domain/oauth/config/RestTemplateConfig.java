package dnd.danverse.domain.oauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Google 서비스한테 액세스 토큰으로 사용자 정보를 받아올때 사용하는 RestTemplate
 * Http 통신을 위한 RestTemplate Bean 등록
 */
@Configuration
public class RestTemplateConfig {

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
