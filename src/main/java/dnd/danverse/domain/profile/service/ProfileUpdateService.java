package dnd.danverse.domain.profile.service;

import dnd.danverse.domain.profile.dto.request.ProfileUpdateRequestDto;
import dnd.danverse.domain.profile.dto.response.ProfileDetailResponseDto;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profilegenre.entity.ProfileGenre;
import dnd.danverse.domain.profilegenre.service.ProfileGenrePureService;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 프로필 수정 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class ProfileUpdateService {

  private final ProfilePureService profilePureService;
  private final ProfileGenrePureService profileGenrePureService;

  /**
   * memberId 를 가진 사용자의 프로필이 null 이라면, 예외가 발생한다.
   * null 이 아니라면 프로필 정보를 수정한다.
   * 새로운 장르가 기존의 장르와 같다면 장르 데이터는 update 를 하지 않는다.
   * 다르다면, deleteAll 과 saveAll 이 일어난다.
   *
   * @param request 정보 수정 요청 dto.
   * @param memberId 수정을 요청하는 멤버 Id.
   * @return 수정된 프로필 응답 dto.
   */
  public ProfileDetailResponseDto updateProfile(ProfileUpdateRequestDto request, Long memberId) {
    Profile profile = profilePureService.retrieveProfile(memberId);
    Profile updatedProfile = profile.update(request);

    if (updatedProfile.containGenres(request.getGenres()) && updatedProfile.compareSize(request.getGenres())) {
      return new ProfileDetailResponseDto(updatedProfile);
    }
    Set<ProfileGenre> newProfileGenres = request.getSetOfGenres(updatedProfile);
    profileGenrePureService.deleteAndSaveAll(profile.getId(), newProfileGenres);
    return new ProfileDetailResponseDto(updatedProfile, newProfileGenres);
  }


}
