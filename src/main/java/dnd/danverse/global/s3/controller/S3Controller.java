package dnd.danverse.global.s3.controller;

import dnd.danverse.global.response.DataResponse;
import dnd.danverse.global.s3.dto.response.S3Dto;
import dnd.danverse.global.s3.service.S3Uploader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * S3에 이미지를 업로드하는 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "S3Controller : 이미지 등록 API")
public class S3Controller {

  private final S3Uploader s3Uploader;

  @PostMapping(value = "/api/v1/events/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "이벤트 이미지 등록", notes = "이벤트 이미지를 S3에 등록, multipart/form-data 형식으로 전송, key: img")
  public ResponseEntity<DataResponse<S3Dto>> uploadEventImg(
      @RequestPart("img") MultipartFile multipartFile) {
    S3Dto savedEventUrl = s3Uploader.uploadFile(multipartFile, "event");
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.CREATED, "이벤트 이미지 업로드 성공", savedEventUrl), HttpStatus.CREATED);
  }


  @PostMapping(value = "/api/v1/performances/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "공연 이미지 등록", notes = "공연 이미지를 S3에 등록, multipart/form-data 형식으로 전송, key: img")
  public ResponseEntity<DataResponse<S3Dto>> uploadPerformImg(
      @RequestPart("img") MultipartFile multipartFile) {
    S3Dto savedPerformUrl = s3Uploader.uploadFile(multipartFile, "performance");
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.CREATED, "공연 이미지 업로드 성공", savedPerformUrl), HttpStatus.CREATED);
  }

  @PostMapping(value = "/api/v1/profiles/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ApiOperation(value = "프로필 이미지 등록", notes = "프로필 이미지를 S3에 등록, multipart/form-data 형식으로 전송, key: img")
  public ResponseEntity<DataResponse<S3Dto>> uploadProfileImg(
      @RequestPart("img") MultipartFile multipartFile) {
    S3Dto savedProfileUrl = s3Uploader.uploadFile(multipartFile, "profile");
    return new ResponseEntity<>(
        DataResponse.of(HttpStatus.CREATED, "프로필 이미지 업로드 성공", savedProfileUrl), HttpStatus.CREATED);
  }


}
