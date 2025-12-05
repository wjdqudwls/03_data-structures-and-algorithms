package com.wjdqudwls.section04.queue;

/**/

import java.util.LinkedList;
import java.util.Queue;

public class B1158 {
  public static void main(String[] args) {
  }
  public static String solution(int n, int k) {

    Queue<Integer> queue = new LinkedList<>();

    // 1부터 n만큼의 사람을 추가
    for (int i = 1; i <= n; i++) {
      queue.offer(i);
    }
    // 결과 문자열 만들기용 가변 문자열 객체
    StringBuilder sb = new StringBuilder();
    sb.append("<");

    // 큐에 사람이 남아있으면 반복 == 모든 사람이 나가면 반복 종료
    while (!queue.isEmpty()) {

      // k-1번 반복하여 큐 앞에 사람을 뒤로 옮기기
      for (int i = 0; i < k - 1; i++) {
        // k번째 사람을 꺼내고, StringBuilder에 추가
        queue.offer(queue.poll());
      }
      sb.append(queue.poll());

      // 아직 큐에 남은 사람이 있을 경우
      if(!queue.isEmpty() ){
        sb.append(", ");
      }

    }

    sb.append(">");
    return sb.toString(); // "<3, 6, 2, 7, 5, 1, 4>"
  }
}
