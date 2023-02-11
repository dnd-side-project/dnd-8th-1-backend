package dnd.danverse.global.s3;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class S3Controller {

  private final S3Uploader s3Uploader;

  @PutMapping ("/upload")
  @ResponseBody
  public String upload(@RequestParam("image") MultipartFile multipartFile) throws IOException {
    s3Uploader.upload(multipartFile, "static");
    return "ok";
  }


}
