package dnd.danverse.domain.member.dto.response;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.service.SignUpResult;
import dnd.danverse.domain.profile.dto.response.ProfileDetailResponseDto;
import dnd.danverse.domain.profile.entity.Profile;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 로그인 후, 사용자 정보를 반환하기 위한 Dto.
 */
@Getter
@AllArgsConstructor
public class MemberResponse {

  /**
   * 사용자의 고유 ID.
   */
  @ApiModelProperty(value = "사용자의 멤버 고유 ID")
  private Long id;

  /**
   * 사용자의 이름.
   */
  @ApiModelProperty(value = "사용자의 이름")
  private String name;

  /**
   * 사용자의 이메일.
   */
  @ApiModelProperty(value = "사용자의 이메일")
  private String email;

  /**
   * 사용자의 소셜 프로필 이미지.
   */
  @ApiModelProperty(value = "사용자의 소셜 프로필 이미지")
  private String imgUrl;

  /**
   * 사용자의 권한.
   */
  @ApiModelProperty(value = "사용자의 권한")
  private String role;

  /**
   * 사용자의 회원가입 여부.
   * isSignUp = true : 회원가입을 했다.
   * isSignUp = false : 기존 회원이며, 로그인 했다.
   */
  @ApiModelProperty(value = "사용자의 회원가입 여부")
  private boolean isSignUp;

  /**
   * 사용자의 프로필 정보를 담는 Dto.
   */
  @ApiModelProperty(value = "사용자의 프로필 정보를 담는 Dto")
  private ProfileDetailResponseDto profile;

  /**
   * SignUpResult 가 가지고 있는 member , profile 정보를 이용하여 MemberResponse 를 생성한다.
   * 프로필을 아직 등록하지 않은 사용자의 경우, profile 은 null 이다.
   *
   * @param signUpResult 회원가입 결과를 담은 객체
   */
  public MemberResponse(SignUpResult signUpResult) {
    final Member member = signUpResult.getMember();
    final Profile signedProfile = signUpResult.getProfile();

    this.id = member.getId();
    this.name = member.getName();
    this.email = member.getEmail();
    this.imgUrl = member.getSocialImg();
    this.role = member.getRole().getAuthority();
    this.isSignUp = signUpResult.isSignUp();
    this.profile = signedProfile != null ? new ProfileDetailResponseDto(signedProfile) : null;
  }


  /**
   * 이미 로그인 이후 시점에서 사용자의 정보를 반환하는 생성자이므로,
   * isSignup 의 값을 true 로 고정하였습니다.
   * 사용자의 프로필이 null 이라면, 프로필 dto 없이 null 을 반환합니다.
   * null 이 아니라면, dto 에 프로필을 담아 반환합니다.
   *
   * @param member 사용자.
   * @param profileResult 프로필 정보를 담은 Dto.
   */
  public MemberResponse(Member member, Profile profileResult) {
    this.id = member.getId();
    this.name = member.getName();
    this.email = member.getEmail();
    this.imgUrl = member.getSocialImg();
    this.role = member.getRole().getAuthority();
    this.isSignUp = false;
    this.profile = profileResult != null ? new ProfileDetailResponseDto(profileResult) : null;
  }
}
