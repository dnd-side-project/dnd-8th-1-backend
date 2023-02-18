package dnd.danverse.domain.matching.dto.response;

import dnd.danverse.domain.matching.entity.EventMatch;
import dnd.danverse.domain.profile.dto.response.MatchProfileDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 신청자 프로필의 dto와 매칭 여부를 담은 Dto.
 */
@Getter
@NoArgsConstructor
public class ApplicantsResponseDto {

  @ApiModelProperty(value = "신청자의 프로필 정보를 담은 Dto")
  private MatchProfileDto profile;

  @ApiModelProperty(value = "신청자의 매칭 여부")
  private boolean isMatched;


  public ApplicantsResponseDto(EventMatch eventMatch) {
    this.profile = new MatchProfileDto(eventMatch.getProfileGuest());
    this.isMatched = eventMatch.isMatched();
  }


}
