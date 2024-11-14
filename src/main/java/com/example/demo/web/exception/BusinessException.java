package com.example.demo.web.exception;

import com.example.demo.web.api.ApiResponse;
import com.example.demo.web.api.ApiResponseCode;
import lombok.Getter;

import java.util.Map;

// 비즈니스 예외 클래스도 이에 맞춰 수정
@Getter
public class BusinessException extends RuntimeException {   //BusinessException 사용자 예외 정의 : RuntimeException 상속
  private final ApiResponseCode responseCode;
  private final Map<String, String> details;

  // 기본 생성자 - details 없음
  public BusinessException(ApiResponseCode responseCode) {
    super(responseCode.getRtmsg());
    this.responseCode = responseCode;
    this.details = null;
  }

  // details가 필요한 경우의 생성자
  public BusinessException(ApiResponseCode responseCode, Map<String, String> details) {
    super(responseCode.getRtmsg());
    this.responseCode = responseCode;
    this.details = details;
  }

  // ApiResponse 생성 헬퍼 메소드
  public ApiResponse<Object> toResponse() {
    return details != null
        ? ApiResponse.withDetails(responseCode, details, null)
        : ApiResponse.of(responseCode, null);
  }
}