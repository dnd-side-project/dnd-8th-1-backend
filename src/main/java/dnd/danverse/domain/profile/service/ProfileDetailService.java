package dnd.danverse.domain.profile.service;

import dnd.danverse.domain.profile.dto.response.ProfileWithGenreDto;
import dnd.danverse.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 프로필 상세 조회 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class ProfileDetailService {

  private final ProfilePureService profilePureService;

  /**
   * 프로필 순수 서비스에서 프로필 아이디를 통해서 프로필의 상세 정보를 가져옵니다.
   *
   * @param profileId 조회하려고 하는 profileId.
   * @return profile 과 genre 를 함께 반환하는 응답 Dto.
   */
  public ProfileWithGenreDto getProfile(Long profileId) {
    Profile profile = profilePureService.getProfileDetail(profileId);
    return new ProfileWithGenreDto(profile);
  }

}
