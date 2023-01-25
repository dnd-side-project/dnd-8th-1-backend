package dnd.danverse.global.healthcheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * health 체크를 위한 컨트롤러입니다.
 */
@RestController
public class HealthController {

  @GetMapping("/health")
  public String checkHealth(){
    return "healthy";
  }

}
