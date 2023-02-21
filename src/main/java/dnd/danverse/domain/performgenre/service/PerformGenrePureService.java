package dnd.danverse.domain.performgenre.service;

import dnd.danverse.domain.performgenre.exception.LimitExceededException;
import dnd.danverse.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 공연 장르 순수 서비스.
 */
@Service
@RequiredArgsConstructor
public class PerformGenrePureService {

  /**
   * 장르 리스트의 사이즈가 3보다 크면 LimitExceededException 이 발생한다.
   *
   * @param genre 장르의 List.
   */
  public void smallerThanThree(List<String> genre) {
    if (genre.size() > 3) {
      throw new LimitExceededException(ErrorCode.PERFORM_GENRE_EXCEEDED);
    }
  }
}
