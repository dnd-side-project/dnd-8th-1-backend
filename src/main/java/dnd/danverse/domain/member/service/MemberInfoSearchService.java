package dnd.danverse.domain.member.service;

import dnd.danverse.domain.member.dto.response.MemberResponse;
import dnd.danverse.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 본인 정보 조회 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class MemberInfoSearchService {

  private final MemberPureService memberPureService;

  /**
   * 사용자의 Id(memberId)를 통하여, 프로필과 멤버 정보를 응답 dto 에 담아 반환합니다.
   *
   * @param memberId memberId.
   * @return 프로필과 멤버 정보 응답 Dto.
   */
  public MemberResponse searchMyInfo(Long memberId) {
    Member member = memberPureService.findMemberWithProfile(memberId);
    return new MemberResponse(member, member.getProfile());
  }
}
