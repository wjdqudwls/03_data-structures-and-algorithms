package com.beyond.section03.greedy;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class B_CoinChange {

  /* 동전 교환 문제
   * 주어진 N개의 동전을 이용하여
   *  금액 K를 만들기 위한
   *  동전의 최소 개수 구하기
   *
   * ex) N=3 (10, 50, 100), K= 670
   *     결과 -> 9 (10*2, 50*1, 100*6)
   * */
  static int N, K;
  static int[] money;
  static int count;

  public static int solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());

    money = new int[N];
    count = 0;

    // 읽어온 동전의 종류를 배열에 저장
    for (int i = 0; i < N; i++) {
      money[i] = Integer.parseInt(br.readLine());
    }

    for (int i = N - 1; i >= 0; i--) {
      count += K / money[i];
      K %= money[i];
      if (K == 0) {
        break;
      }
    }

    return count;
  }
}
