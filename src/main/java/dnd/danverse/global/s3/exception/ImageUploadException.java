package dnd.danverse.global.s3.exception;

import dnd.danverse.global.exception.BusinessException;
import dnd.danverse.global.exception.ErrorCode;

public class ImageUploadException extends BusinessException{
  public ImageUploadException(ErrorCode errorCode) {super(errorCode);}
}
