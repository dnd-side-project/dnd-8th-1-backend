package dnd.danverse.domain.member.service;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.entity.Role;
import dnd.danverse.domain.member.repository.MemberRepository;
import dnd.danverse.domain.oauth.info.OAuth2UserInfo;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 최초 사용자 회원가입 시 사용자 정보를 저장하는 Service.
 */
@Service
@Getter
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberSignUpService {

  private final MemberRepository memberRepository;

  /**
   * 계정이 이미 존재 한다면 프로필 정보를 업데이트 한다. 존재하지 않는다면 회원가입을 진행한다.
   *
   * @param userInfo 소셜 로그인 정보
   * @return 회원가입이 완료된 사용자 정보
   */
  @Transactional
  public SignUpResult signUpOrUpdate(OAuth2UserInfo userInfo) {
    Optional<Member> optionalMember = memberRepository.findByUsername(userInfo.getId());

    // 존재한다면 프로필도 업데이트
    if (optionalMember.isPresent()) {
      Member member = optionalMember.get();
      updateMemberInfo(userInfo, member);
      return new SignUpResult(member, false);
    }
    // 존재하지 않는다면 회원가입
    Member member = signUp(userInfo);
    return new SignUpResult(member, true);
  }

  /**
   * 회원 정보를 업데이트 한다.
   * @param userInfo 소셜 로그인 정보
   * @param member 회원 정보
   */
  private void updateMemberInfo(OAuth2UserInfo userInfo, Member member) {
    member.updateInfo(userInfo.getEmail(), userInfo.getName(), userInfo.getImageUrl());
  }

  /**
   * 구글 로그인 정보를 받아서 회원가입을 진행한다.
   *
   * @param userInfo 구글에서 제공 받은 사용자 정보
   * @return 회원가입이 완료된 사용자 정보
   */
  private Member signUp(OAuth2UserInfo userInfo) {

    Member member = userInfo.toEntity();

    return memberRepository.save(member);
  }


}
