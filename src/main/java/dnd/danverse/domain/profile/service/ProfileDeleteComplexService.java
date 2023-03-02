package dnd.danverse.domain.profile.service;

import dnd.danverse.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 프로필 삭제 복합 Service
 */
@Service
@RequiredArgsConstructor
public class ProfileDeleteComplexService {

  private final ProfilePureService profilePureService;


  /**
   * 사용자는 자신의 프로필을 삭제한다.
   *
   * @param memberId 삭제를 요청하는 사용자 Id.
   */
  public void deleteProfile(Long memberId) {
    Profile profile = profilePureService.retrieveProfile(memberId);
    profilePureService.deleteProfile(profile);
  }

}
