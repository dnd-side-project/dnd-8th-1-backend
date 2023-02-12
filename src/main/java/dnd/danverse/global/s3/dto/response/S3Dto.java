package dnd.danverse.global.s3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 업로드된 이미지의 url을 전달하기 위한 DTO.
 * - S3에 저장된 이미지 url
 */
@AllArgsConstructor
@Data
public class S3Dto {
  private String savedUrl;

}
