package dnd.danverse.domain.matching.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이벤트 매칭 Entity
 * 이벤트에 지원하면 해당 테이블로 내역이 저장된다.
 */
@Entity
@NoArgsConstructor
@Getter
@SequenceGenerator(name = "EVENT_MATCH_SEQ_GENERATOR", sequenceName = "EVENT_MATCH_SEQ",
    initialValue = 1, allocationSize = 1)
public class EventMatch extends BaseTimeEntity {

  /**
   * 이벤트 매칭 ID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EVENT_MATCH_SEQ_GENERATOR")
  private Long id;

  /**
   * 이벤트 매칭 주최자
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_host_id", nullable = false, foreignKey = @ForeignKey(name = "FK_EVENT_MATCH_PROFILE_HOST"))
  private Profile profileHost;

  /**
   * 이벤트 매칭 지원자
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_guest_id", nullable = false, foreignKey = @ForeignKey(name = "FK_EVENT_MATCH_PROFILE_GUEST"))
  private Profile profileGuest;

  /**
   * 이벤트 매칭 상태, default 로는 false 로 저장 된다.
   * true : 매칭 성공
   * false : 매칭 실패
   */
  @Column(nullable = false)
  private boolean isMatched;

  /**
   * 이벤트 매칭 생성 시, 매칭 상태를 false 로 저장한다.
   */
  @PrePersist
  public void defaultMatching() {
    this.isMatched = false;
  }



}