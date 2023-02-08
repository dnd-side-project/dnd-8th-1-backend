package dnd.danverse.domain.performance.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.common.Image;
import dnd.danverse.domain.common.Location;
import dnd.danverse.domain.performgenre.entity.PerformGenre;
import dnd.danverse.domain.profile.entity.Profile;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공연 Entity
 */
@Entity
@NoArgsConstructor
@Getter
@SequenceGenerator(name = "PERFORMANCE_SEQ_GENERATOR", sequenceName = "PERFORMANCE_SEQ",
    initialValue = 1, allocationSize = 1)
public class Performance extends BaseTimeEntity {

  /**
   * 공연 고유 ID, 시퀀스 전략 사용
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERFORMANCE_SEQ_GENERATOR")
  private Long id;

  /**
   * 공연 주최자
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_host_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PERFORMANCE_PROFILE_HOST"))
  private Profile profileHost;

  /**
   * 공연 장르 목록
   * PerformGenre 의 삭제는 Performance 가 삭제될 때 함께 삭제된다.
   * CascadeType.PERSIST 를 하지 않고, 추후 saveAll()를 통해서 한번의 네트워크 통신으로 처리한다.
   */
  @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JoinColumn(name = "performance_id", foreignKey = @ForeignKey(name = "FK_PERFORMGENRE_PERFORMANCE"))
  private List<PerformGenre> performGenres = new ArrayList<>();

  /**
   * 공연 제목
   */
  @Column(nullable = false)
  private String title;

  /**
   * 공연 위치(지역)
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Location location;

  /**
   * 공연 구체적인 장소
   */
  @Column(nullable = false)
  private String address;

  /**
   * 공연 시작 날짜
   * (년, 월, 일)
   */
  @Column(nullable = false)
  private LocalDate startDate;

  /**
   * 공연 시작 시간
   * (년, 월, 일, 시, 분, 초)
   */
  @Column(nullable = false)
  private LocalDateTime startTime;

  /**
   * 공연 포스터 이미지
   */
  @Embedded
  private Image performanceImg;

  /**
   * 공연 설명
   */
  @Column(nullable = false)
  private String description;





}
