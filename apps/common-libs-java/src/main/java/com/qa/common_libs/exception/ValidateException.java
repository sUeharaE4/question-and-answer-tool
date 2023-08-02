package com.qa.common_libs.exception;

import java.util.List;
import lombok.Getter;

public class ValidateException extends RuntimeException {

  @Getter
  private List<String> errMessages;

  public ValidateException(List<String> errMessages) {
    this.errMessages = errMessages;
  }

}
