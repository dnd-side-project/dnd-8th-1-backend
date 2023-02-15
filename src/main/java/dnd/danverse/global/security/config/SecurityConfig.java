package dnd.danverse.global.security.config;

import dnd.danverse.domain.jwt.handler.JwtAuthenticationEntryPointHandler;
import dnd.danverse.domain.jwt.filter.JwtAuthenticationFilter;
import dnd.danverse.domain.jwt.exception.JwtExceptionFilter;
import dnd.danverse.domain.jwt.handler.JwtAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 설정
 * 권한이 필요한 요청에 대해서 정의할 수 있게 한다.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final JwtAuthenticationEntryPointHandler jwtAuthenticationEntryPointHandler;
  private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
  private final JwtExceptionFilter jwtExceptionFilter;

  /**
   * 인증이 필요한 부분에 대해서만 권한 설정을 하고 나머지는 모두 허용한다.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPointHandler) // 인증 되지 않는 사용자가 접근할 경우
        .accessDeniedHandler(jwtAccessDeniedHandler); // 인증은 했으나 권한이 없는 경우




    http.authorizeRequests()
        .antMatchers("/api/manager/resource").hasAuthority("ROLE_USER_PROFILE_YES")
        .antMatchers(HttpMethod.POST, "/api/v1/events").hasAuthority("ROLE_USER_PROFILE_YES")
        .anyRequest().permitAll();

    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class);


    return http.build();
  }
}

