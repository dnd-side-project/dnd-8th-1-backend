package dnd.danverse.domain.member.service;

import dnd.danverse.domain.member.entity.Member;
import java.util.Optional;
import lombok.Getter;

@Getter
public class SignUpResult {

  private final Member member;
  private final boolean isSignUp;

  public SignUpResult(Member member, boolean isSignUp) {
    this.member = member;
    this.isSignUp = isSignUp;
  }

}
