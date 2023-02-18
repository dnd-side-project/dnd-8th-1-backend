package dnd.danverse.domain.matching.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프로필 id를 전달하기 위한 DTO.
 */
@Getter
@NoArgsConstructor
public class ProfileIdRequestDto {

  @ApiModelProperty(value = "지원자 프로필 고유 ID", example = "1")

  private Long profileId;

}
