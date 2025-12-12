package org.ho.section03.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/* 회의실 배정 문제
 *  - 하나의 회의실에서 최대한 많은 회의가 진행될 수 있도록
 *     스케쥴을 배정하는 문제
 *  - 각 회의는 시작, 종료 시간이 주어진다.
 *  - 결과는 배정된 회의의 개수를 반환
 *
 *  - N = 회의 개수
 *        각 회의는 시작, 종료시간을 가지고 있음
 * */
public class C_MeetingRoomScheduler {
  public static class Meeting {
    int start;
    int end;

    Meeting(int start, int end) {
      this.start = start;
      this.end = end;
    }

  }

  public static int solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));
    int n = Integer.parseInt(br.readLine());
    Queue<Meeting> pq = new PriorityQueue<>(((o1, o2) ->
        o1.end - o2.end != 0 ?
            o1.end - o2.end
            : o1.start - o2.start));
    for (int i = 0; i < n; i++) {
      StringTokenizer st = new StringTokenizer(br.readLine());
      pq.add(new Meeting(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
    }
    int nowEnd = 0;
    int ans = 0;
    while (!pq.isEmpty()) {
      Meeting now = pq.poll();
      if (nowEnd <= now.start) {
        ans++;
        nowEnd = now.end;
      }
    }

    return ans;
  }
}
