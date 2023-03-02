package dnd.danverse.domain.profilegenre.service;

import dnd.danverse.domain.profilegenre.repository.ProfileGenreRepository;
import dnd.danverse.domain.profilegenre.entity.ProfileGenre;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileGenrePureService {

  private final ProfileGenreRepository profileGenreRepository;

  /**
   * 기존의 장르 데이터와 새로운 장르 데이터가 동일하지 않다면,
   * 전체 삭제 및 전체 저장이 한 트랜잭션 내에서 동작합니다.
   *
   * @param profileId 수정을 원하는 프로필 Id.
   * @param profileGenres 새로 저장하는 장르 데이터 set.
   */
  @Transactional
  public void deleteAndSaveAll(Long profileId, Set<ProfileGenre> profileGenres) {
    // 이전 ProfileGenre 데이터를 모두 삭제합니다.
    log.info("이전 프로필의 genre 데이터를 모두 삭제합니다. 프로필 Id : {}", profileId);
    profileGenreRepository.deleteByProfileId(profileId);

    log.info("새로운 genre 데이터를 저장합니다.");
    profileGenreRepository.saveAll(profileGenres);
  }

}
