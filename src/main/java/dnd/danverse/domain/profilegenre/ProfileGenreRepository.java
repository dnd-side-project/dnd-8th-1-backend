package dnd.danverse.domain.profilegenre;

import dnd.danverse.domain.profilegenre.entity.ProfileGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileGenreRepository extends JpaRepository<ProfileGenre, Long> {

}
