package dnd.danverse.global.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * s3FileName은 파일 이름이 중복되지 않게 UUID를 이어 붙여서 파일 이름을 새롭게 생성합니다.
 * contentLength로 파일 사이즈를 S3에 알려줄 수 있습니다.
 * API 메소드인 putObject를 사용해서 S3에 파일을 업로드합니다.
 * S3에 업로드된 파일의 url을 가져옵니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {

  private final AmazonS3Client amazonS3Client;


  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  /**
   * @param multipartFile 타입의 파일.
   * @param dirName s3에 올렸을 때 저장되는 디렉토리
   * @return s3에 이미지 url을 올리는 upload 메서드 반환
   * @throws IOException
   */
  public String upload(MultipartFile multipartFile, String dirName) throws IOException {
    File uploadFile = convert(multipartFile)
        .orElseThrow(() -> new IllegalArgumentException("MultiPartFile -> File로의 전환이 실패했습니다."));
    return upload(uploadFile, dirName);
  }

  /**
   * @param file multipartFile에서 변환된 파일
   * @param dirName s3에 올렸을 때 저장되는 디렉토리
   * @return multipartFile에서 변환된 file의 고유한 url(string) 반환
   */
  private String upload(File file, String dirName){
    String fileName = dirName + "/" + UUID.randomUUID() + file.getName();
    String uploadImgUrl = putS3(file, fileName);
    removeNewFile(file);
    return uploadImgUrl;
  }

  /**
   * @param uploadFile s3에 업로드되는 파일
   * @param fileName 고유한 파일이름
   * @return s3에 url을 보내는 메서드
   */
  private String putS3(File uploadFile, String fileName){
    amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
    return amazonS3Client.getUrl(bucket, fileName).toString();
  }

  /**
   * @param targetFile s3에 이미지를 올리기 위해 생성한 파일
   */
  private void removeNewFile(File targetFile){
    if (targetFile.delete()) {
      log.info("파일이 삭제되었습니다.");
    } else {
      log.info("파일이 삭제되지 못했습니다.");
    }
  }

  /**
   * @param file multipartFile 타입의 파일
   * @return multipartFile을 file로 변환
   * @throws IOException
   */
  private Optional<File> convert(MultipartFile file) throws IOException {
    File convertedFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
    if(convertedFile.createNewFile()) {
      try(FileOutputStream fos = new FileOutputStream(convertedFile)){
        fos.write(file.getBytes());
      }
      return Optional.of(convertedFile);
    }
    return Optional.empty();
  }
}
