package com.kth.section03.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/* 동전 교환 문제
* 주어진 N개의 동전을 이용하여 금액 K를 만들기 위한 동전의 최소 개수 구하기
*
* ex) N=3 (10, 50, 100), K = 670
*     결과 -> 9 (10*2, 50*1, 100*6)
* */
public class B_CoinChange {

  public static int solution(String input) throws IOException {
    int count = 0;

//    BufferedReader br = new BufferedReader(new StringReader(input));
//
//    StringTokenizer st = new StringTokenizer(br.readLine());
//    int N = Integer.parseInt(st.nextToken());
//    int K = Integer.parseInt(st.nextToken());
//
//    int[] coins = new int[N];
//
//    // 읽어온 동전의 종류를 배열에 저장
//    for (int i = 0; i < N; i++) {
//      coins[i] = Integer.parseInt(st.nextToken());
//    }
//
//    int count = 0;
//
//    /* 그리디 알고리즘 : 큰 동전부터 사용해서 최적해 구하기 */
//    // coins[] 배열에 저장된 동전의 종류는 0 ~ 끝 오름차순
//    // == 끝 인덱스로 갈수록 동전 금액이 가장 크다
//    for (int i = N - 1; i >= 0; i--) {
//
//      if(K >= coins[i]) {
//        count += K / coins[i];
//        K = K % coins[i];
//      }
//      if(K == 0) break;
//    return count;
//  }
//}

    Scanner sc = new Scanner(input);
    int N = sc.nextInt();
    int K = sc.nextInt();
    int index = N - 1;
    int[] coins = new int[N];

    for(int i = 0; i < N; i++){
      coins[i] = sc.nextInt();
    }

    for(int i = N - 1; i >= 0; i--){
      if(K >= coins[i]) {
        count += K / coins[i];
        K = K % coins[i];
      }

      if(K == 0){
        break;
      }
    }


    return count;


  }

}
