package dnd.danverse.domain.review.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.member.entity.Member;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parameter;

/**
 * 공연 관람 후 리뷰 Entity
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "REVIEW_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "REVIEW_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class Review extends BaseTimeEntity {

  /**
   * 리뷰 ID, sequence 전략 사용
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_SEQ_GENERATOR")
  private Long id;

  /**
   * 공연 ID
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "performance_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REVIEW_PERFORMANCE"))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Performance performance;

  /**
   * 리뷰 작성자 ID (멤버 ID)
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REVIEW_MEMBER"))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Member member;

  /**
   * 리뷰 내용
   */
  @Column(nullable = false)
  private String content;

  @Builder
  public Review(Performance performance, Member member, String content) {
    this.performance = performance;
    this.member = member;
    this.content = content;
  }

  /**
   * 리뷰 수정
   * @param reviewContent 수정할 리뷰 내용
   * @return 수정된 리뷰
   */
  public Review updateReview(String reviewContent) {
    this.content = reviewContent;
    return this;
  }

}
