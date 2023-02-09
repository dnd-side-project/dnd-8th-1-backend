package dnd.danverse.domain.profile.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.common.Image;
import dnd.danverse.domain.common.Location;
import dnd.danverse.domain.common.TeamType;
import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.profilegenre.entity.ProfileGenre;
import java.time.LocalDate;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 서비스 사용자의 팀(개인) 프로필 정보
 */
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
  private Member member;

  /**
   * 사용자 프로필의 장르 정보. OneToMany 관계이며, ProfileGenre 의 프로필 정보를 조회할 때 사용한다.
   * ProfileGenre 의 삭제는 Profile 이 삭제될 때 함께 삭제된다.
   * CascadeType.PERSIST 를 하지 않고, 추후 saveAll()를 통해서 한번의 네트워크 통신으로 처리한다.
   */
  @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
  @JoinColumn(name = "profile_id")
  private List<ProfileGenre> profileGenres = new ArrayList<>();

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
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Location location;

  /**
   * 사용자 커리어 시작일 (년, 월, 일)
   */
  @Column(nullable = false)
  private LocalDate careerStartDay;

  /**
   * 사용자 프로필의 소개글.
   */
  @Column(nullable = false)
  private String description;

  /**
   * 사용자에게 연락할 수 있는 오픈 카카오톡 채팅 URL
   */
  @Embedded
  private OpenChat openChatUrl;

  /**
   * 사용자 작업물을 확인할 수 있는 포트폴리오 URL
   */
  @Embedded
  private Portfolio portfolioUrl;




}
