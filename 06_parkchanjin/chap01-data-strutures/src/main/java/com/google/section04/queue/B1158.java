package com.google.section04.queue;

import java.util.LinkedList;
import java.util.Queue;

  public class B1158 {

    public static void main(String[] args){




    }


    public static String solution(int n, int k) {

      Queue<Integer> queue = new LinkedList<>();

      // 결과 문자열 만들기용 가변 문자열 객체
      StringBuilder sb = new StringBuilder();

      for (int i = 1; i <= n ; i++) {
        queue.offer(i);
      }

      sb.append("<");

      // 큐에 사람이 남아 있으면 반복 == 모든 사람이 나가면 반복 종료
      while(!queue.isEmpty()){

        // K-1번 반복하여 큐 앞에 사람을 뒤로 옮기기
        for(int i =  0; i < k-1; i++){
          queue.offer( queue.poll() );
        }

        // k번째 사람을 꺼내고 StringBuilder에 추가
        sb.append( queue.poll() );

        // <3627514>
        if(!queue.isEmpty()){
          sb.append(", "); // 쉼표 + 공백 추가
        }
      }
      sb.append(">");
      return sb.toString(); //  "<3, 6, 2, 7, 5, 1, 4> "
    }

  }

