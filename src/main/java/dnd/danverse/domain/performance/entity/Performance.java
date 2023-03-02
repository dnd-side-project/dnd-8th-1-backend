package dnd.danverse.domain.performance.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.common.Image;
import dnd.danverse.domain.performance.dto.request.PerformUpdateRequestDto;
import dnd.danverse.domain.performgenre.entity.PerformGenre;
import dnd.danverse.domain.profile.entity.Profile;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parameter;

/**
 * 공연 Entity.
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "PERFORMANCE_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "PERFORMANCE_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class Performance extends BaseTimeEntity {

  /**
   * 공연 고유 ID, 시퀀스 전략 사용.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERFORMANCE_SEQ_GENERATOR")
  private Long id;

  /**
   * 공연 주최자.
   */
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "profile_host_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PERFORMANCE_PROFILE_HOST"))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Profile profileHost;

  /**
   * 공연 장르 목록 PerformGenre 의 삭제는 Performance 가 삭제될 때 함께 삭제된다. CascadeType.PERSIST 를 하지 않고, 추후
   * saveAll()를 통해서 한번의 네트워크 통신으로 처리한다.
   */
  @OneToMany(mappedBy = "performance", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private Set<PerformGenre> performGenres = new HashSet<>();

  /**
   * 공연 제목.
   */
  @Column(nullable = false)
  private String title;

  /**
   * 공연 위치(지역).
   */
  @Column(nullable = false)
  private String location;

  /**
   * 공연 구체적인 장소.
   */
  @Column(nullable = false)
  private String address;

  /**
   * 공연 시작 날짜 (년, 월, 일).
   */
  @Column(nullable = false)
  private LocalDate startDate;

  /**
   * 공연 시작 시간 (년, 월, 일, 시, 분, 초).
   */
  @Column(nullable = false)
  private LocalDateTime startTime;

  /**
   * 공연 포스터 이미지.
   */
  @Embedded
  private Image performanceImg;

  /**
   * 공연 설명.
   */
  @Column(nullable = false)
  private String description;


  @Builder
  public Performance(Profile profileHost, String title,
      String location, String address, LocalDate startDate, LocalDateTime startTime,
      Image performanceImg, String description) {
    this.profileHost = profileHost;
    this.title = title;
    this.location = location;
    this.address = address;
    this.startDate = startDate;
    this.startTime = startTime;
    this.performanceImg = performanceImg;
    this.description = description;
  }

  /**
   * 공연 장르 목록을 문자열로 반환한다.
   *
   * @return 장르 List.
   */
  public List<String> getStringOfPerformGenres() {
    List<String> genres = new ArrayList<>();
    for (PerformGenre genre : performGenres) {
      genres.add(genre.getGenre());
    }
    return genres;
  }

  /**
   * performGenre 를 제외한 performance 정보를 수정합니다.
   *
   * @param updateRequest 수정 요청 Dto.
   * @return 수정된 Performance.
   */
  public Performance updateInfo(PerformUpdateRequestDto updateRequest) {
    this.title = updateRequest.getTitle();
    this.location = updateRequest.getLocation();
    this.address = updateRequest.getAddress();
    this.startDate = updateRequest.getStartDate();
    this.startTime = updateRequest.getStartTime();
    this.performanceImg = new Image(updateRequest.getImgUrl());
    this.description = updateRequest.getDescription();
    return this;
  }

  /**
   * PerformGenre -> String 으로 변환.
   */
  private Set<String> toStringGenre() {
    return this.performGenres.stream()
        .map(PerformGenre::getGenre)
        .collect(Collectors.toSet());
  }

  /**
   * 수정하려는 장르의 Set 과 기존 장르의 Set 을 containsAll 메서드로 비교합니다.
   *
   * @param newGenres 새로운 장르 Set<String>
   * @return 모두 포함한다면 true, 하나라도 중복되지 않는 값이 있다면 false.
   */
  public boolean containGenres(Set<String> newGenres) {
    return this.toStringGenre().containsAll(newGenres);
  }

  /**
   * 기존 장르 데이터 set 의 사이즈와 새로운 장르 데이터 set 의 사이즈를 비교합니다.
   *
   * @param newGenres 새로운 장르 set.
   * @return 사이즈가 같다면 true, 다르면 false.
   */
  public boolean compareSize(Set<String> newGenres) {
    return this.performGenres.size() == newGenres.size();
  }

}
