package dnd.danverse.global.redis.repository;


import dnd.danverse.global.redis.dto.RefreshTokenDto;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Refresh Token 을 관리하고 있는 redis 에 접근하기 위한 Repository
 */
@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshTokenDto, String> {

  Optional<RefreshTokenDto> findByRefreshToken(String refreshToken);
}
