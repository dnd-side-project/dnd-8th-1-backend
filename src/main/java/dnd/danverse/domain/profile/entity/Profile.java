package dnd.danverse.domain.profile.entity;

import static dnd.danverse.global.exception.ErrorCode.EVENT_TYPE_NOT_MATCH;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.common.Image;
import dnd.danverse.domain.common.TeamType;
import dnd.danverse.domain.event.exception.EventNotAvailableException;
import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.profile.dto.request.ProfileUpdateRequestDto;
import dnd.danverse.domain.profilegenre.entity.ProfileGenre;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Parameter;

/**
 * 서비스 사용자의 팀(개인) 프로필 정보.
 */
@Slf4j
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "PROFILE_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "PROFILE_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class Profile extends BaseTimeEntity {

  /**
   * 사용자 프로필의 고유 ID. Sequence 전략을 사용한다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROFILE_SEQ_GENERATOR")
  private Long id;

  /**
   * 사용자 프로필의 소유자. OneToOne 관계이며, Member 의 프로필 정보를 조회할 때 사용한다.
   */
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false, unique = true, foreignKey = @ForeignKey(name = "FK_PROFILE_MEMBER"))
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Member member;

  /**
   * 사용자 프로필의 장르 정보. OneToMany 관계이며, ProfileGenre 의 프로필 정보를 조회할 때 사용한다.
   * ProfileGenre 의 삭제는 Profile 이 삭제될 때 함께 삭제된다.
   * CascadeType.PERSIST 를 하지 않고, 추후 saveAll()를 통해서 한번의 네트워크 통신으로 처리한다.
   */
  @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<ProfileGenre> profileGenres = new HashSet<>();

  /**
   * 사용자 프로필의 종류. 팀인지 개인인지 구분한다.
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TeamType profileType;

  /**
   * 사용자 프로필의 팀(개인)명.
   * 팀이름은 중복 가능하기 때문에, 유니크 제약조건을 걸지 않는다.
   */
  @Column(nullable = false)
  private String profileName;

  /**
   * Embeddable 을 사용하여 프로필 이미지를 관리한다.
   */
  @Embedded
  private Image profileImg;

  /**
   * 사용자 프로필 주 활동지역.
   */
  @Column(nullable = false)
  private String location;

  /**
   * 사용자 커리어 시작일 (년, 월, 일).
   */
  @Column(nullable = false)
  private LocalDate careerStartDay;

  /**
   * 사용자 프로필의 소개글.
   */
  @Column(nullable = false)
  private String description;

  /**
   * 사용자에게 연락할 수 있는 오픈 카카오톡 채팅 URL.
   */
  @Embedded
  private OpenChat openChatUrl;

  /**
   * 사용자 작업물을 확인할 수 있는 포트폴리오 URL.
   */
  @Embedded
  private Portfolio portfolioUrl;


  @Builder
  public Profile(Member member, TeamType profileType, String profileName,
      Image profileImg, String location, LocalDate careerStartDay, String description,
      OpenChat openChatUrl, Portfolio portfolioUrl) {
    this.member = member;
    this.profileType = profileType;
    this.profileName = profileName;
    this.profileImg = profileImg;
    this.location = location;
    this.careerStartDay = careerStartDay;
    this.description = description;
    this.openChatUrl = openChatUrl;
    this.portfolioUrl = portfolioUrl;
  }


  /**
   * 프로필의 팀 타입과 모집 타입이 일치하는지 확인한다.
   *
   * @param recruitType 모집 타입
   */
  public void checkMatchType(TeamType recruitType) {
    if (this.profileType != recruitType) {
      throw new EventNotAvailableException(EVENT_TYPE_NOT_MATCH);
    }
  }

  /**
   * 프로필 장르를 String 타입으로 변환한다.
   *
   * @return String 타입의 Set 반환
   */
  public Set<String> toStringProfileGenre() {
    return this.profileGenres.stream()
        .map(ProfileGenre::getGenre)
        .collect(Collectors.toSet());
  }

  /**
   * Profile 객체의 ID 와 파라미터로 넘어온 Profile 객체의 ID 가 같은지 확인한다.
   *
   * @param stranger 비교할 Profile 객체
   * @return 같지 않으면 true, 같으면 false 반환
   */
  public boolean isNotSame(Profile stranger) {
    return !Objects.equals(this.id, stranger.getId());
  }

  /**
   * Profile 객체를 Dirty Checking 을 통해 업데이트한다.
   *
   * @param request 프로필 수정 요청 dto
   * @return 수정된 Profile 객체
   */
  public Profile update(ProfileUpdateRequestDto request) {
    this.profileType = TeamType.of(request.getType());
    this.profileImg = new Image(request.getImgUrl());
    this.profileName = request.getName();
    this.location = request.getLocation();
    this.careerStartDay = request.getCareerStartDate();
    this.description = request.getDescription();
    this.openChatUrl = new OpenChat(request.getOpenChatUrl());
    this.portfolioUrl = new Portfolio(request.getPortfolio().getYoutube(), request.getPortfolio().getInstagram(), request.getPortfolio().getTiktok());

    return this;
  }

  /**
   * 수정하려는 장르의 Set 과 기존 장르의 Set 을 containsAll 메서드로 비교합니다.
   *
   * @param newGenres 새로운 장르 Set<String>
   * @return 모두 포함한다면 true, 하나라도 중복되지 않는 값이 있다면 false.
   */
  public boolean containGenres(Set<String> newGenres) {
    return this.toStringProfileGenre().containsAll(newGenres);
  }

  /**
   * 기존 장르 데이터 set 의 사이즈와 새로운 장르 데이터 set 의 사이즈를 비교합니다.
   *
   * @param newGenres 새로운 장르 set.
   * @return 사이즈가 같다면 true, 다르면 false.
   */
  public boolean compareSize(Set<String> newGenres) {
    return this.profileGenres.size() == newGenres.size();
  }
}
