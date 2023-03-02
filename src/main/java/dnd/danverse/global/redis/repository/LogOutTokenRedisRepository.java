package dnd.danverse.global.redis.repository;

import dnd.danverse.global.redis.dto.LogOutTokenDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogOutTokenRedisRepository extends CrudRepository<LogOutTokenDto, String> {

}
