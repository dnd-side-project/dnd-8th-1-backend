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

  /**
   * 공연 장르 고유 ID, 시퀀스 전략 사용.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERFORM_GENRE_SEQ_GENERATOR")
  private Long id;

  /**
   * 공연 장르.
   */
  @Column(nullable = false)
  private String genre;

  /**
   * 공연.
   * 공연은 많은 장르를 가질 수 있으므로, ManyToOne 관계.
   * OnDelete 옵션을 사용하여, 부모 Performance 가 삭제되면 자식 PerformGenre 도 삭제되도록 설정.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "performance_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PERFORM_GENRE_PERFORM"))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Performance performance;

  /**
   * 생성자.
   * @param genre 장르
   */
  public PerformGenre(String genre) {
    this.genre = genre;
  }

  /**
   * 양방향 연관관계 편의 메소드.
   * @param performance 외래키로 들어갈 공연 객체
   */
  public void addPerform(Performance performance) {
    this.performance = performance;
    performance.getPerformGenres().add(this);
  }
}
