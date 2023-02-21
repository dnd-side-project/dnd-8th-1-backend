package dnd.danverse.domain.performance.service;

import dnd.danverse.domain.performance.dto.request.PerformSavedRequestDto;
import dnd.danverse.domain.performance.dto.response.PerformDetailResponse;
import dnd.danverse.domain.performance.entity.Performance;
import dnd.danverse.domain.performgenre.service.PerformGenrePureService;
import dnd.danverse.domain.profile.entity.Profile;
import dnd.danverse.domain.profile.service.ProfilePureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 공연 등록 복합 서비스.
 */
@Service
@RequiredArgsConstructor
public class PerformSaveComplexService {
  private final PerformancePureService performancePureService;
  private final ProfilePureService profilePureService;
  private final PerformGenrePureService performGenrePureService;


  public PerformDetailResponse postPerform(PerformSavedRequestDto performSavedRequest, Long memberId) {
    Profile writer = profilePureService.retrieveProfile(memberId);
    Performance performance = performancePureService.createPerform(performSavedRequest.toEntity(writer));
    performGenrePureService.smallerThanThree(performance.getPerformGenres());

    return new PerformDetailResponse(performance);
  }
}
