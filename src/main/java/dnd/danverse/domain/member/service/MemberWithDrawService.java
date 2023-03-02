package dnd.danverse.domain.member.service;

import dnd.danverse.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 회원 탈퇴를 위한 복합 Service.
 */
@Service
@RequiredArgsConstructor
public class MemberWithDrawService {

  private final MemberPureService memberPureService;

  /**
   * 회원 탈퇴.
   *
   * @param memberId 탈퇴하고자 하는 회원의 ID
   */
  public void withdrawMember(Long memberId) {
    Member member = memberPureService.findMemberWithProfile(memberId);
    memberPureService.withDrawMember(member);
  }
}
