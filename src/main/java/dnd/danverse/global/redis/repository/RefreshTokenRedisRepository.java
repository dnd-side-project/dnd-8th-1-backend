package dnd.danverse.global.redis.repository;


import dnd.danverse.global.redis.dto.RefreshTokenDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRedisRepository extends CrudRepository<RefreshTokenDto, String> {

}
