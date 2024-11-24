package com.example.demo.domain.bbs.replybbs;

import com.example.demo.domain.entity.ReplyBbs;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ApiResponseData {
  private List<ReplyBbs> comments;
  private int totalCnt;
}
