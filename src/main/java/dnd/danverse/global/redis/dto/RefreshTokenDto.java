package dnd.danverse.global.redis.dto;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Redis 에 저장되는 객체.
 * RefreshToken 은 1주일의 유효시간을 가진다.
 * key = refreshToken , value = email
 * value 에 Indexed 를 생성 함으로써, value 를 통해 조회가 가능하다.
 */
@Getter
@AllArgsConstructor
@Builder
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 7) // 1주일의 유효시간
public class RefreshTokenDto {

  @Id
  private String refreshToken;

  private String email;

}

