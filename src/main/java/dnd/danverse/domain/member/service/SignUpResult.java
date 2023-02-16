package dnd.danverse.domain.member.service;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.profile.entity.Profile;
import lombok.Getter;

/**
 * Member 데이터와 회원가입 여부, Profile 데이터를 반환하기 위한 객체
 */
@Getter
public class SignUpResult {

  private final Member member;
  private final boolean isSignUp;
  private final Profile profile;

  /**
   * 프로필이 있는 사용자의 경우, 로그인 후 프로필 정보를 반환한다.
   * @param member 회원 정보
   * @param isSignUp 회원가입 여부
   */
  public SignUpResult(Member member, boolean isSignUp) {
    this.member = member;
    this.isSignUp = isSignUp;
    this.profile = member.getProfile();
  }

  /**
   * 회원가입 여부를 반환한다.
   * @return 회원가입 여부
   */
  public boolean isSignUp() {
    return isSignUp;
  }

}
