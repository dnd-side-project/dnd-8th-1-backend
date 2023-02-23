package dnd.danverse.domain.performgenre.service;

import dnd.danverse.domain.performgenre.entity.PerformGenre;
import dnd.danverse.domain.performgenre.repository.PerformGenreRepository;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 공연 장르 순수 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PerformGenrePureService {

  private final PerformGenreRepository performGenreRepository;

  /**
   * 이전의 공연 genre 데이터를 모두 삭제한 후, 다시 새로운 genre 데이터를 saveAll 합니다.
   *
   * @param performanceId 수정하려는 공연 Id.
   * @param newGenres 새로운 genre 데이터.
   */
  @Transactional
  public void deleteAndSaveAll(Long performanceId, Set<PerformGenre> newGenres) {
    // 이전 PerformGenre 데이터를 모두 삭제합니다.
    log.info("이전 공연의 genre 데이터를 모두 삭제합니다. 공연 Id : {}", performanceId);
    performGenreRepository.deleteByPerformId(performanceId);

    log.info("새로운 genre 데이터를 저장합니다.");
    performGenreRepository.saveAll(newGenres);
  }

}
