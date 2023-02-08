package dnd.danverse.domain.review.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.profile.entity.Profile;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공연 관람 후 리뷰 Entity
 */
@Entity
@NoArgsConstructor
@Getter
@SequenceGenerator(name = "REVIEW_SEQ_GENERATOR", sequenceName = "REVIEW_SEQ",
    initialValue = 1, allocationSize = 1)
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
  private Performance performance;

  /**
   * 리뷰 작성자 ID (프로필 ID)
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false, foreignKey = @ForeignKey(name = "FK_REVIEW_PROFILE"))
  private Profile profile;

  /**
   * 리뷰 내용
   */
  @Column(nullable = false)
  private String content;

}
