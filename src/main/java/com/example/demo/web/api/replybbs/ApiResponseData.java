package com.example.demo.web.api.replybbs;

import com.example.demo.domain.entity.ReplyBbs;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

//댓글과, 총 댓글 수를 가져오기 위해 필요한 영역
public class ApiResponseData {
  private List<ReplyBbs> comments;    //댓글 내용
  private int totalCnt;               //총 댓글수
}
