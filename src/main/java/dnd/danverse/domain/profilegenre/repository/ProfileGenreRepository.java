package dnd.danverse.domain.profilegenre.repository;

import dnd.danverse.domain.profilegenre.entity.ProfileGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileGenreRepository extends JpaRepository<ProfileGenre, Long> {

  @Modifying
  @Query("delete from ProfileGenre pg where pg.profile.id = :profileId")
  void deleteByProfileId(@Param("profileId") Long profileId);
}
