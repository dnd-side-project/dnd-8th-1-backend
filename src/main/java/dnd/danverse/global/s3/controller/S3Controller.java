package dnd.danverse.global.s3.controller;

import dnd.danverse.global.response.DataResponse;
import dnd.danverse.global.s3.dto.response.S3Dto;
import dnd.danverse.global.s3.service.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * S3에 이미지를 업로드하는 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
public class S3Controller {

  private final S3Uploader s3Uploader;

  @PostMapping ("/events/image")
  public ResponseEntity<DataResponse<S3Dto>> uploadEventImg(@RequestParam("img") MultipartFile multipartFile) {
    S3Dto savedEventUrl = s3Uploader.uploadFile(multipartFile, "event");
    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED, "이벤트 이미지 업로드 성공", savedEventUrl), HttpStatus.CREATED);
  }


  @PostMapping ("/performances/image")
  public ResponseEntity<DataResponse<S3Dto>> uploadPerformImg(@RequestParam("img") MultipartFile multipartFile) {
    S3Dto savedPerformUrl = s3Uploader.uploadFile(multipartFile, "performance");
    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED, "공연 이미지 업로드 성공", savedPerformUrl), HttpStatus.CREATED);
  }

  @PostMapping ("/profiles/image")
  public ResponseEntity<DataResponse<S3Dto>> uploadProfileImg(@RequestParam("img") MultipartFile multipartFile) {
    S3Dto savedProfileUrl = s3Uploader.uploadFile(multipartFile, "profile");
    return new ResponseEntity<>(DataResponse.of(HttpStatus.CREATED, "프로필 이미지 업로드 성공", savedProfileUrl), HttpStatus.CREATED);
  }


}
