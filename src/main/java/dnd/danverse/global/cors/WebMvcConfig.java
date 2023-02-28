package dnd.danverse.global.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS 설정 CORS(Cross-Origin Resource Sharing)는 한 출처(origin)에서 실행 중인 웹 애플리케이션이 다른 출처의 선택한 자원에 접근할 수
 * 있는 권한을 부여하도록 브라우저에 알려주는 체제이다.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("http://localhost:3000", "http://localhost:6006",
            "https://danverse.vercel.app", "https://danverse-.*-allsilver.vercel.app")
        .allowedMethods(
            HttpMethod.GET.name(),
            HttpMethod.POST.name(),
            HttpMethod.PUT.name(),
            HttpMethod.DELETE.name(),
            HttpMethod.PATCH.name(),
            HttpMethod.HEAD.name(),
            HttpMethod.OPTIONS.name()
        )
        //allow to use Authorization header
        .allowedHeaders("*")
        // allow to use cookies
        .allowCredentials(true);

  }

}
