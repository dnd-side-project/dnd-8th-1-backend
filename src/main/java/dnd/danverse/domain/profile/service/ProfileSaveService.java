package dnd.danverse.domain.profile.service;

import dnd.danverse.domain.member.entity.Member;
import dnd.danverse.domain.member.service.MemberPureService;
import dnd.danverse.domain.profile.dto.request.ProfileSaveRequestDto;
import dnd.danverse.domain.profile.dto.response.ProfileDetailResponseDto;
import dnd.danverse.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 프로필 등록 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class ProfileSaveService {

  private final ProfilePureService profilePureService;
  private final MemberPureService memberPureService;

  /**
   * 로그인이 되어있고, 프로필이 없는 사용자에 한해서 프로필 등록이 가능합니다.
   * 프로필이 있는 사용자가 등록 요청을 할 경우, ProfileAlreadyException 처리가 됩니다.
   *
   * @param request 프로필 요청 Dto.
   * @param memberId 프로필 등록 요청하는 멤버 Id.
   * @return 프로필 응답 Dto.
   */
  public ProfileDetailResponseDto saveProfile(ProfileSaveRequestDto request, Long memberId) {
    Member member = memberPureService.findMemberWithProfile(memberId);
    profilePureService.checkIfHasProfile(memberId);
    Profile profile = profilePureService.saveProfile(request.toEntity(member));
    return new ProfileDetailResponseDto(profile);
  }

}
