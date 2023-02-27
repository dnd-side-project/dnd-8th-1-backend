package dnd.danverse.domain.profile.service;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.service.MemberPureService;
import dnd.danverse.domain.profile.dto.response.ProfileWithGenreDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 프로필 상세 조회 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class ProfileDetailService {

  private final MemberPureService memberPureService;

  /**
   * 멤버 ID 를 통해 프로필을 조회한다.
   *
   * @param memberId 멤버 Id.
   * @return profile 과 genre 를 함께 반환하는 응답 Dto.
   */
  public ProfileWithGenreDto getProfile(Long memberId) {
    Member member = memberPureService.findMemberWithProfile(memberId);
    return new ProfileWithGenreDto(member, member.getProfile());
  }

}
