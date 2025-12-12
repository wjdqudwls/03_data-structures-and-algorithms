package com.mycompany.section03.greedy;

/* 회의실 배정 문제
* - 하나의 회의실에서 최대한 많은(greedy) 회의가 진행될 수 있도록
*   스케줄을 배정하는 문제
* - 각 회의는 시작, 종료 시간이 주어짐.
* - 결과는 배정된 회의의 개수를 반환
* 
* - 회의실 개수 1개
* - N : 회의 개수 
* - 각 회의는 [시작 시간], [종료 시간] 정보를 갖는다.
*
* === 그리디 전략 ===
* - 종료 시간이 가장 빠른 회의부터 선택
* [왜?]
* - 회의가 빨리 끝나면 이후에 회의를 선택할 수 있는 남은 시간이 많아짐
* */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class C_MeetingRoomScheduler {


  public static int solution(String input) throws IOException {

    BufferedReader br = new BufferedReader(new StringReader(input));
    int N = Integer.parseInt(br.readLine());

    
    // 회의 정보(시작, 종료) 기록
    int[][] times = new int[N][2];

    for (int i = 0; i < N; i++) {

      StringTokenizer st = new StringTokenizer(br.readLine());

      times[i][0] = Integer.parseInt(st.nextToken());
      times[i][1] = Integer.parseInt(st.nextToken());

    }

    /* <<핵심>> 종료 시간 오름차순 정렬 */
    // 끝 시간 기준 오름차순 배열
    Arrays.sort(times,(t1, t2) ->
    {
      if (t1[1] == t2[1]) { // 종료 시간이 같을 경우, 시작시간 빠른 순서로 정렬하는 이유
        // 1. 코드 일관성(예측 가능성 향상)
        // 2. 정렬 안정성
        return t1[0] - t2[0]; // 시작 시간 빠른 순서
      }
      return t1[1] - t2[1]; // 종료 시간 빠른 순서
    });
//    int startTime; // 등록하려는 미팅의 시작 시간
    int endTime = 0; // 직전 회의의 종료 시간을 저장
//    Arrays.stream(times).sorted((a,b)-> a[1] - b[1]);

    int count = 0;
    
    // 이전 등록된 미팅의 끝나는 시간보다 다음 등록하려는 미팅의 시작시간이 빠르면 넘어가고 느리거나 같으면 등록 -cyh
    for(int i = 0; i < N; i++){

      /* 현재 회의의 시작 시간이
      * 이전 회의의 종료 시간 이후인지 확인
      * - 맞을 경우 == 회의 배정 가능!
      *  -> 배정된 회의 개수 증가
      *  + 이전 회의 종료 시간을 현재 종료 시간으로 갱신
      * */

      if (times[i][0] >= endTime) {
        count++;
        endTime = times[i][1];
        System.out.printf("선택된 회의의 시간 [ %d, %d ] \n", times[i][0],times[i][1]);
      }

    }
    System.out.println("예약된 회의 수 : " + count);
    System.out.println("===============================================");
    return count;
  }

}
