package dnd.danverse.domain.profilegenre.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 프로필 - 장르, 중간 테이블 Entity.
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "PROFILE_GENRE_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "PROFILE_GENRE_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class ProfileGenre extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILE_GENRE_SEQ_GENERATOR")
  private Long id;

  @Column(nullable = false)
  private String genre;

  public ProfileGenre(String genre) {
    this.genre = genre;
  }


}
