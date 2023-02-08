package dnd.danverse.domain.performgenre.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.common.Genre;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공연 - 장르, 중간 테이블 Entity
 */
@Entity
@NoArgsConstructor
@Getter
@SequenceGenerator(name = "PERFORM_GENRE_SEQ_GENERATOR", sequenceName = "PERFORM_GENRE_SEQ",
    initialValue = 1, allocationSize = 1)
public class PerformGenre extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERFORM_GENRE_SEQ_GENERATOR")
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Genre genre;
}
