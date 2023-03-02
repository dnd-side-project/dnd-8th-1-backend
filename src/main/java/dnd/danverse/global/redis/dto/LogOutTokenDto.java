package dnd.danverse.global.redis.dto;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@RedisHash(value = "LogOutToken")
public class LogOutTokenDto {

  @Id
  private final String accessToken;

  @TimeToLive
  private final long expiration;

  public LogOutTokenDto(String accessToken, long expiration) {
    this.accessToken = accessToken;
    this.expiration = expiration;
  }

}
