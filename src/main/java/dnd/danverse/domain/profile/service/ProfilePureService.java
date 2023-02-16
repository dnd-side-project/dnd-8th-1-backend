package dnd.danverse.domain.profile.service;

import static dnd.danverse.global.exception.ErrorCode.PROFILE_NOT_FOUND;

import dnd.danverse.domain.profile.exception.NoProfileException;
import dnd.danverse.domain.profile.ProfileRepository;
import dnd.danverse.domain.profile.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Profile Entity 에 대한 순수 Service
 */
@Service
@RequiredArgsConstructor
public class ProfilePureService {

  private final ProfileRepository profileRepository;

  /**
   * 프로필 조회
   * @param memberId 사용자 (member 고유 DB ID)
   * @return 프로필
   */
  @Transactional(readOnly = true)
  public Profile retrieveProfile(Long memberId) {
    return profileRepository.findByMember(memberId)
        .orElseThrow(() -> new NoProfileException(PROFILE_NOT_FOUND));
  }
}
