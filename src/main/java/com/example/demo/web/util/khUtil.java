package com.example.demo.web.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class khUtil {
  /**
   * Controller의 BindingResult를 ApiResponse Map로 변환
   * @param bindingResult 요청 데이터 검증 오류
   * @return map
   */
  public static Map<String, String> getValidChkMap(BindingResult bindingResult) {
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    Map<String,String> map = new HashMap<>();   //map 한번 초기화 해줘야함
    for (FieldError fieldError : fieldErrors) {
      map.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    return map;
  }
}
