package dnd.danverse.domain.performgenre.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.performance.entity.Performance;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parameter;

/**
 * 공연 - 장르, 중간 테이블 Entity
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "PERFORM_GENRE_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "PERFORM_GENRE_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class PerformGenre extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERFORM_GENRE_SEQ_GENERATOR")
  private Long id;

  @Column(nullable = false)
  private String genre;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "performance_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PERFORM_GENRE_PERFORM"))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Performance performance;

  public PerformGenre(String genre) {
    this.genre = genre;
  }
}
