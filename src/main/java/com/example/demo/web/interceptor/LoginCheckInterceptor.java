package com.example.demo.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;

@Slf4j
@Component
public class LoginCheckInterceptor implements HandlerInterceptor {
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String redirectUrl = null;

    String requestURI = request.getRequestURI();

    if(request.getQueryString() == null){
      redirectUrl = requestURI;
    }else{

      String queryString = URLEncoder.encode(request.getQueryString(), "UTF-8");
      StringBuffer str = new StringBuffer();
      redirectUrl = str.append(requestURI).append("?").append(queryString).toString();
    }

    HttpSession session = request.getSession(false);

    //게시글 id만

    if(session == null || session.getAttribute("loginOkMember") == null ){
      response.sendRedirect("/login?redirectUrl=" + redirectUrl);
    }
    return true;
  }
}
