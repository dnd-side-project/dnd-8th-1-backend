package dnd.danverse.domain.profile.service;

import dnd.danverse.domain.profile.dto.response.ProfileHomeDto;
import dnd.danverse.domain.profile.entity.Profile;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 프로필 검색 복합 서비스
 */
@Service
@RequiredArgsConstructor
public class ProfileSearchService {

  private final ProfilePureService profilePureService;


  /**
   * 프로필 6개 랜덤 조회
   * @return 프로필 6개
   */
  public List<ProfileHomeDto> searchProfileForHome() {
    List<Profile> profiles = profilePureService.searchProfileForHome();

    Collections.shuffle(profiles); // 리스트를 랜덤하게 섞음

    return ProfileHomeDto.toDtoList(profiles.subList(0, Math.min(6, profiles.size())));
  }
}
