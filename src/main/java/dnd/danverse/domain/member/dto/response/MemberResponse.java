package dnd.danverse.domain.member.dto.response;

import dnd.danverse.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 후, 사용자 정보를 반환하기 위한 Dto
 */
@Getter
@AllArgsConstructor
public class MemberResponse {

  /**
   * 사용자의 고유 ID.
   */
  private Long id;

  /**
   * 사용자의 이름.
   */
  private String name;

  /**
   * 사용자의 이메일.
   */
  private String email;

  /**
   * 사용자의 소셜 프로필 이미지.
   */
  private String picture;

  /**
   * 사용자의 권한.
   */
  private String role;

  /**
   * 사용자의 회원가입 여부.
   * isSignUp = true : 회원가입을 했다.
   * isSignUp = false : 기존 회원이며, 로그인 했다.
   */
  private boolean isSignUp;

  /**
   * Member Entity 를 MemberResponse 로 변환하는 메소드
   * @param member Member Entity
   */
  public MemberResponse(Member member, boolean isSignUp) {
    this.id = member.getId();
    this.name = member.getName();
    this.email = member.getEmail();
    this.picture = member.getProfileImage();
    this.role = member.getRole().getAuthority();
    this.isSignUp = isSignUp;
  }



}
