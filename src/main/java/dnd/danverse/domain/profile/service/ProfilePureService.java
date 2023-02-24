package dnd.danverse.domain.profile.service;

import static dnd.danverse.global.exception.ErrorCode.PROFILE_NOT_FOUND;

import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.exception.NoProfileException;
import dnd.danverse.domain.profile.repository.ProfileRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Profile Entity 에 대한 순수 Service.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProfilePureService {

  private final ProfileRepository profileRepository;

  /**
   * 프로필 조회
   * 프로필이 존재하지 않으면 예외를 발생시킨다.
   *
   * @param memberId 사용자 (member 고유 DB ID)
   * @return 프로필
   */
  @Transactional(readOnly = true)
  public Profile retrieveProfile(Long memberId) {
    log.info("memberId: {} 를 통해서 프로필을 찾습니다.", memberId);
    return profileRepository.findByMember(memberId)
        .orElseThrow(() -> new NoProfileException(PROFILE_NOT_FOUND));
  }

  /**
   * 모든 프로필 조회.
   *
   * @return 모든 프로필
   */
  @Transactional(readOnly = true)
  public List<Profile> searchProfileForHome() {
    return profileRepository.findAllProfiles();
  }

  /**
   * 프로필을 상세 조회합니다.
   * 프로필과 프로필 장르를 fetch join 하여 profile 을 반환합니다.
   * 요청한 profile Id 에 해당하는 프로필이 없다면 예외로 처리합니다.
   *
   * @param profileId 조회하려고 하는 profile 고유 Id.
   * @return Profile.
   */
  @Transactional(readOnly = true)
  public Profile getProfileDetail(Long profileId) {
    log.info("profileId: {}를 통해서 profile 과 profileGenre 를 fetch join 하여 찾습니다.", profileId);
    return profileRepository.findProfileWithGenreById(profileId)
        .orElseThrow(() -> new NoProfileException(PROFILE_NOT_FOUND));
  }
}
