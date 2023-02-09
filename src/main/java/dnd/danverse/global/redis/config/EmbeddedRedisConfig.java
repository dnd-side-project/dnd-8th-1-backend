package dnd.danverse.global.redis.config;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

/**
 * 여러 스프링 테스트 컨텍스트가 실행되면 EmbeddedRedis가 포트충돌이 납니다.
 * 서로 다른 property를 가진 테스트 코드들이 작성되있는 경우 통합테스트 실행시
 * (ex: ./gradlew test) 새로운 컨텍스트를 생성해서 EmbeddedRedis를 하나더 실행하려고 한다.
 * 이미 지정된 포트 (여기선 6379)가 사용중이라서 다른
 * 테스트들이 수행되지 못한다. 해당 포트가 미사용중이라면 사용하고, 사용중이라면 그외 다른 포트를 사용하도록 한다.
 * -> 이동욱님
 * 저 같은 경우 테스트 코드가 1000개 이상 작성된 이후로는 병렬로 전체 테스트를 수행할때가 많았습니다.
 * (동시에 테스트를 3~4개씩 수행하도록 parellel 옵션을 gradle에 추가해서 실행한 경우를 얘기합니다)
 * 그래서 6379 포트를 재사용 하는식으로 하는 경우 맨처음 6379 포트로 레디스를 실행했던 테스트 컨텍스트가 종료되면
 * 그걸 쓰는 다른 테스트는 이미 6379 포트로 레디스에 접속해서 테스트 수행중에
 * 갑자기 커넥션 Fail이 발생하게 되는 케이스가 있었습니다 :)
 * 그래서 저렇게 개별 컨텍스트마다 레디스를 사용하게 구성하였다.
 * -> ywroh 님
 * ozimov/embedded-redis 0.7.3 버전을 사용하면 slf4j 가 중복 에러가 발생하기 때문에 0.7.2 버전을 사용하던지,
 * gradle 에서 ozimov/embedded-redis 를 implement 할 때 slf4j 를 exclude 해야합니다
 */
@Configuration
@Profile("test")
public class EmbeddedRedisConfig {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Value("${spring.data.redis.port}")
  private int redisPort;

  private RedisServer redisServer;

  @PostConstruct
  public void redisServer() throws IOException {
    int port = isRedisRunning() ? findAvailablePort() : redisPort;
    redisServer = new RedisServer(port);
    redisServer.start();
  }

  @PreDestroy
  public void stopRedis() {
    if (redisServer != null) {
      redisServer.stop();
    }
  }

  /**
   * Embedded Redis가 현재 실행중인지 확인
   */
  private boolean isRedisRunning() throws IOException {
    return isRunning(executeGrepProcessCommand(redisPort));
  }

  /**
   * 현재 PC/서버에서 사용가능한 포트 조회
   */
  public int findAvailablePort() throws IOException {

    for (int port = 10000; port <= 65535; port++) {
      Process process = executeGrepProcessCommand(port);
      if (!isRunning(process)) {
        return port;
      }
    }

    throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
  }

  /**
   * 해당 port를 사용중인 프로세스 확인하는 sh 실행
   */
  private Process executeGrepProcessCommand(int port) throws IOException {
    String command = String.format("netstat -nao | find \"LISTEN\" | find \"%d\"", port);
    String[] shell = {"cmd.exe", "/y", "/c", command};
    return Runtime.getRuntime().exec(shell);
  }

  /**
   * 해당 Process가 현재 실행중인지 확인
   */
  private boolean isRunning(Process process) {
    String line;
    StringBuilder pidInfo = new StringBuilder();

    try (BufferedReader input = new BufferedReader(
        new InputStreamReader(process.getInputStream()))) {

      while ((line = input.readLine()) != null) {
        pidInfo.append(line);
      }

    } catch (Exception e) {
      log.error("Error Message : {}", e.getMessage());
    }

    return !StringUtils.hasText(pidInfo.toString());
  }

}