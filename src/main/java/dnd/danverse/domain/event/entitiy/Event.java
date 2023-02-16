package dnd.danverse.domain.event.entitiy;

import static dnd.danverse.global.exception.ErrorCode.EVENT_OVER_DEADLINE;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.common.Image;
import dnd.danverse.domain.common.TeamType;
import dnd.danverse.domain.event.exception.EventNotAvailableException;
import dnd.danverse.domain.profile.entity.Profile;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 이벤트 정보를 담는 Entity.
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "EVENT_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "EVENT_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class Event extends BaseTimeEntity {

  /**
   * 이벤트의 고유 ID. Sequence 전략을 사용한다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_SEQ_GENERATOR")
  private Long id;

  /**
   * 이벤트의 주최자.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_id", nullable = false, foreignKey = @ForeignKey(name = "FK_EVENT_PROFILE"))
  private Profile profile;

  /**
   * 이벤트의 종류. (콜라보, 쉐어)
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private EventType eventType;

  /**
   * 이벤트의 제목.
   */
  @Column(nullable = false)
  private String title;

  /**
   * 이벤트의 모집 종류. (팀, 개인)
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TeamType recruitType;

  /**
   * 이벤트의 모집 인원.
   */
  @Column(nullable = false)
  private int recruitCount;

  /**
   * 이벤트의 모집 마감 기한 (년, 월, 일, 시, 분, 초)
   */
  @Column(nullable = false)
  private LocalDateTime deadline;

  /**
   * 이벤트 지역
   */
  @Column(nullable = false)
  private String location;

  /**
   * 이벤트의 상세 설명.
   */
  @Column(nullable = false)
  private String description;

  /**
   * 이벤트의 이미지.
   */
  @Embedded
  private Image eventImg;

  @Builder
  public Event(Profile profile, EventType eventType, String title, TeamType recruitType,
      int recruitCount, LocalDateTime deadline, String location, String description,
      Image eventImg) {
    this.profile = profile;
    this.eventType = eventType;
    this.title = title;
    this.recruitType = recruitType;
    this.recruitCount = recruitCount;
    this.deadline = deadline;
    this.location = location;
    this.description = description;
    this.eventImg = eventImg;
  }


  /**
   * 요청한 시간이 마감 기한보다 늦은지 확인한다.
   * @return 마감 기한이 지났으면 true, 아니면 false
   */
  public boolean overDeadline() {
    return LocalDateTime.now().isAfter(this.deadline);
  }

  /**
   * 이벤트 모집 기간이 지났는지 확인한다.
   */
  public void checkIfOverDeadline() {
    if (overDeadline()) {
      throw new EventNotAvailableException(EVENT_OVER_DEADLINE);
    }
  }
}
