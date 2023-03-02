package dnd.danverse.domain.member.service;

import static dnd.danverse.global.exception.ErrorCode.MEMBER_NOT_FOUND;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.exception.MemberNotFoundException;
import dnd.danverse.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Member 순수 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberPureService {

  private final MemberRepository memberRepository;

  /**
   * Member 와 Profile 을 찾는다.
   * @param memberId Member 의 id
   * @return Profile 을 알고 있는 Member
   */
  @Transactional(readOnly = true)
  public Member findMemberWithProfile(Long memberId) {
    log.info("member 와 profile 을 찾기 위해 fetch join 으로 memberId 인 {} 를 찾는다.", memberId);
    return memberRepository.findMemberWithProfile(memberId).orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND));
  }

  /**
   * Member 를 탈퇴시킨다.
   *
   * @param member 탈퇴시키고자 하는 Member
   */
  @Transactional
  public void withDrawMember(Member member) {
    log.info("memberId 인 {} 를 탈퇴시킨다.", member.getId());
    memberRepository.delete(member);
  }
}
