package dnd.danverse.global.redis.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


import redis.embedded.RedisServer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
@Profile("test")
public class EmbeddedRedisConfig {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Value("${spring.data.redis.port}")
  private int redisPort;

  private RedisServer redisServer;

  @PostConstruct
  public void redisServer() throws IOException {
    redisServer = new RedisServer(redisPort);
    redisServer.start();
  }

  @PreDestroy
  public void stopRedis() {
    if (redisServer != null) {
      redisServer.stop();
    }
  }

}