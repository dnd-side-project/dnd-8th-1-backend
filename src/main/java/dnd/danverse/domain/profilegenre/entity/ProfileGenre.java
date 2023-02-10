package dnd.danverse.domain.profilegenre.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로필 - 장르, 중간 테이블 Entity.
 */
@Entity
@NoArgsConstructor
@Getter
@SequenceGenerator(name = "PROFILE_GENRE_SEQ_GENERATOR", sequenceName = "PROFILE_GENRE_SEQ",
    initialValue = 1, allocationSize = 1)
public class ProfileGenre extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILE_GENRE_SEQ_GENERATOR")
  private Long id;

  @Column(nullable = false)
  private String genre;


}
