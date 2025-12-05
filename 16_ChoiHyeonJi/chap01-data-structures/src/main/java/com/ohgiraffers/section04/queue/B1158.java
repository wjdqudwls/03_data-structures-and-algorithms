package com.ohgiraffers.section04.queue;

import java.util.LinkedList;
import java.util.Queue;

// 문제를 분석해서 어떤 자료구조 쓰면 좋을 지 먼저 보기. 그림 그려서 해보기
public class B1158 {
  public static void main(String[] args) {

  }

  public static String solution(int n, int k){ // 두개의 숫자가 전달되어 옴.

    Queue<Integer> queue = new LinkedList<Integer>(); // 큐 구현할 때 LinkedList가 더 효율이 좋다
    // 1부터 n 만큼의 사람을 추가
    for(int i = 1; i<=n; i++){
      queue.offer(i);
    }

    // 결과 문자열 만들기용 가변 문자열 객체
    StringBuilder sb = new StringBuilder();

    sb.append("<"); //append 뒤에 덧붙이다.

    // 큐에 사람이 남아있으면 반복 == 모든 사람이 나가면 반복 종료
    while (!queue.isEmpty()){

      // k-1번 반복하여 큐 앞에 사람을 뒤로 옮기기
      for (int i = 0; i < k-1 ; i++) {
        queue.offer(queue.poll()); //꺼낸걸(queue.poll()) 뒤(queue.offer)로 보냄
      }

      // k번쨰 사람을 꺼내고 StringBuilder에 추가
      sb.append(queue.poll()); //사람 꺼내기 이걸 넣음
      //<3627514> 모양이 됨.

      // 아직 큐에 남은 사람이 있을 경우
      if(!queue.isEmpty()){
        sb.append(", "); // 쉼표 + 공백 추가
      }
    }

    sb.append(">");
    //return null; // 에시 출력 형태 "<3, 6, 2, 7, 5, 1, 4>"
    return sb.toString(); //"<3, 6, 2, 7, 5, 1, 4>"
  }
}
