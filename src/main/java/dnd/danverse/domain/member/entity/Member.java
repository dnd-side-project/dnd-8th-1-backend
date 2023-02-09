package dnd.danverse.domain.member.entity;

import dnd.danverse.domain.common.BaseTimeEntity;
import dnd.danverse.domain.oauth.info.OAuth2Provider;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 사용자 정보를 담는 Entity.
 */
@Entity
@NoArgsConstructor
@Getter
@GenericGenerator(
    name = "MEMBER_SEQ_GENERATOR",
    strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "MEMBER_SEQ"),
        @Parameter(name = "initial_value", value = "1"),
        @Parameter(name = "increment_size", value = "1")
    }
)
public class Member extends BaseTimeEntity {

  /**
   * 사용자의 고유 ID. Sequence 전략을 사용한다.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
  private Long id;

  /**
   * 사용자의 이름.
   */
  @Column(nullable = false)
  private String name;

  /**
   * 사용자의 이메일.
   */
  @Column(nullable = false, unique = true)
  private String email;

  /**
   * 사용자의 OAuth2 Sub.
   */
  @Column(nullable = false, unique = true)
  private String username;

  /**
   * 사용자의 비밀번호.
   */
  @Column(nullable = false)
  private String password;

  /**
   * 사용자의 소셜 프로필 이미지.
   */
  @Column(length = 512, nullable = false)
  private String socialImg;

  /**
   * 사용자의 권한.
   */
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Role role;

  /**
   * 사용자의 소셜 로그인 제공자.
   */
  @Column(length = 20, nullable = false)
  @Enumerated(value = EnumType.STRING)
  private OAuth2Provider oauth2Provider;


  @Builder
  public Member(String name, String email, String username, String password, String socialImg,
      Role role, OAuth2Provider oauth2Provider) {
    this.name = name;
    this.email = email;
    this.username = username;
    this.password = password;
    this.socialImg = socialImg;
    this.role = role;
    this.oauth2Provider = oauth2Provider;
  }

  /**
   * 사용자의 정보를 업데이트 한다.
   *
   * @param email        사용자의 이메일
   * @param name         사용자의 이름
   * @param socialImg 사용자의 프로필 이미지
   */
  public void updateInfo(String email, String name, String socialImg) {
    this.email = email;
    this.name = name;
    this.socialImg = socialImg;
  }







}

