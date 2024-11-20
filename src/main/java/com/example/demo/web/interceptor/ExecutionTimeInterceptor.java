package com.example.demo.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class ExecutionTimeInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    long startTime = System.currentTimeMillis();
    request.setAttribute("startTime",startTime);
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    long startTime = (Long) request.getAttribute("startTime");
    long endTime = System.currentTimeMillis();
    long executionTime = endTime - startTime;

    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      String className = handlerMethod.getBeanType().getSimpleName();
      String methodName = handlerMethod.getMethod().getName();

      log.info("{}.{}={}ms", className, methodName, executionTime);
    }
  }
}
