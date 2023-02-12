package dnd.danverse.global.s3.service;

import static dnd.danverse.global.exception.ErrorCode.HANDLE_ACCESS_DENIED;
import static dnd.danverse.global.exception.ErrorCode.IMAGE_WRONG_FILE_FORMAT;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import dnd.danverse.global.s3.dto.response.S3Dto;
import dnd.danverse.global.s3.exception.ImageUploadException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * S3에 이미지를 업로드 하는 서비스.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {

  private final AmazonS3Client amazonS3Client;


  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  /**
   * @param multipartFile S3에 올릴 multipartFile
   * @param dir 이미지가 저장될 Directory 이름
   * @return S3에 저장된 이미지의 url 반환
   */
  public S3Dto uploadFile(MultipartFile multipartFile, String dir) {
    String fileName = createFileName(multipartFile.getOriginalFilename(), dir);
    uploadToS3(multipartFile, fileName, getObjectMetadata(multipartFile));
    return new S3Dto(amazonS3Client.getUrl(bucket, fileName).toString());
  }



  /**
   * @param fileName multipartFile의 파일 이름
   * @param dirName 저장될 directory 이름
   * @return 'direcotry 이름 + 랜덤 UUID + 확장자' 를 반환
   */
  private String createFileName(String fileName, String dirName) {
    return dirName + "/" + UUID.randomUUID().toString().concat(getFileExtension(fileName));
  }



  /**
   * @param fileName createFileName 메서드를 통해서 변경된 파일 이름
   */
  private String getFileExtension(String fileName) {
    List<String> possibleExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
    String extension = fileName.substring(fileName.lastIndexOf("."));
    if (!possibleExtensions.contains(extension)) {
      throw new ImageUploadException(IMAGE_WRONG_FILE_FORMAT);
    }
    return extension;
  }



  /**
   * @param file MultipartFile
   * @param fileName createFileName 메서드를 통해서 변경된 파일 이름
   * @param objectMetadata MultipartFile의 length와 contentType을 가진 객체
   */
  private void uploadToS3(MultipartFile file, String fileName, ObjectMetadata objectMetadata) {
    try (InputStream inputStream = file.getInputStream()) {
      amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
          .withCannedAcl(CannedAccessControlList.PublicRead));
    } catch (IOException e) {
      throw new ImageUploadException(HANDLE_ACCESS_DENIED);
    }
  }



  /**
   * @param file MultipartFile
   * @return 메타데이터를 가진 객체를 반환
   */
  private static ObjectMetadata getObjectMetadata(MultipartFile file) {
    ObjectMetadata objectMetadata = new ObjectMetadata();
    objectMetadata.setContentLength(file.getSize());
    objectMetadata.setContentType(file.getContentType());
    return objectMetadata;
  }



}
