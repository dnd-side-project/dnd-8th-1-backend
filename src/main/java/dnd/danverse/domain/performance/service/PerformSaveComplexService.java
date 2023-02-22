package dnd.danverse.domain.performance.service;

import dnd.danverse.domain.performance.dto.request.PerformSavedRequestDto;
import dnd.danverse.domain.performance.dto.response.PerformDetailResponse;
import dnd.danverse.domain.performance.entity.Performance;
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

  /**
   * 공연 등록자의 프로필 정보를 조회한 후, 공연을 등록한다.
   * Request Dto 가지는 데이터를 통해서 공연을 생성한다.
   * 공연 생성시, 공연 장르에 대해서도 함께 연관 관계 설정을 한다.
   * 공연 장르에 대해서는 따로 save 를 호출하지 않는다. cascade 옵션을 통해서 공연을 저장할 때, 같이 저장된다.
   * 혹은, Batch Insert 를 통해서 한 번에 저장할 수도 있다.
   * @param performSavedRequest 공연 등록 요청 Dto
   * @param memberId            공연 등록자 Id
   * @return 공연 등록 성공 시, 공연 상세 정보
   */
  public PerformDetailResponse postPerform(PerformSavedRequestDto performSavedRequest, Long memberId) {
    Profile hostProfile = profilePureService.retrieveProfile(memberId);
    Performance performance = performancePureService.createPerform(performSavedRequest.toPerform(hostProfile));

    return new PerformDetailResponse(performance);
  }
}
