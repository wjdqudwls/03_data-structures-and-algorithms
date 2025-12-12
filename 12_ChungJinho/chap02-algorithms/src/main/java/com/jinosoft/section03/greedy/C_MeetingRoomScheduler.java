package com.jinosoft.section03.greedy;

import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

/* 회의실 배정 문제
 *  - 그리디 전략: 종료시간이 가장 빠른 회의부터 선택한다.
 *  - 종료시간이 같다면 시작시간이 빠른 순으로 정렬한다.
 */
public class C_MeetingRoomScheduler {

  public static int solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));

    // 1. 회의 개수 N 입력
    int N = Integer.parseInt(br.readLine());

    int[][] times = new int[N][2];

    // 2. 회의 시간 정보 입력 (테스트 코드의 입력 형식이 줄바꿈으로 되어 있으므로 반복문 안에서 readLine 수행)
    for (int i = 0; i < N; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      times[i][0] = Integer.parseInt(st.nextToken()); // 시작 시간
      times[i][1] = Integer.parseInt(st.nextToken()); // 종료 시간
    }

    // 3. 정렬
    // 종료 시간을 기준으로 오름차순 정렬
    // 종료 시간이 같다면 시작 시간을 기준으로 오름차순 정렬
    Arrays.sort(times, (t1, t2) -> {
      if (t1[1] == t2[1]) {
        return t1[0] - t2[0];
      } else {
        return t1[1] - t2[1];
      }
    });

    int count = 0;
    int endTime = 0;


    for (int i = 0; i < N; i++) {

      if (times[i][0] >= endTime) {
        endTime = times[i][1];
        count++;
      }

    }

    return count;
  }
}