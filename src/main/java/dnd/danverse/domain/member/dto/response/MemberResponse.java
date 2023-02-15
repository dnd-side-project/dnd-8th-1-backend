package dnd.danverse.domain.member.dto.response;

import dnd.danverse.domain.member.service.SignUpResult;
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
   * @param signUpResult 회원가입 결과
   */
  public MemberResponse(SignUpResult signUpResult) {
    this.id = signUpResult.getMember().getId();
    this.name = signUpResult.getMember().getName();
    this.email = signUpResult.getMember().getEmail();
    this.picture = signUpResult.getMember().getSocialImg();
    this.role = signUpResult.getMember().getRole().getAuthority();
    this.isSignUp = signUpResult.isSignUp();
  }



}
