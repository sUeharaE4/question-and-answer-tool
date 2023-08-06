package com.qa.common_libs.exception;

import lombok.Getter;
import java.util.List;

public class ValidateException extends AbstractUserErrorExceptionBase {

  @Getter
  private List<String> errMessages;

  public ValidateException(List<String> errMessages) {
    super(String.join(", ", errMessages));
    this.errMessages = errMessages;
  }

}
