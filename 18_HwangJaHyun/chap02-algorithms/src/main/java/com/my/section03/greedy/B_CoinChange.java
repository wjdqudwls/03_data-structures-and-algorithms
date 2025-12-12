package com.my.section03.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.StringTokenizer;

/* 동전 교환 문제
* 주어진 N개의 동전을 이용하여
* 금액 K를 만들기 위한 동전의 최소 개수 구하기
* 
* ex) N = 3 (10, 50, 100), K = 670
* -> 9개 (10*2 + 50*1 + 100*6)
* */
public class B_CoinChange {

  public static int solution(String input) throws IOException {

    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st = new StringTokenizer(br.readLine());

    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());
    int[] coinList = new int[N];

    for (int i = 0; i < N; i++) {
      // 줄바꿈이 있으면 새로운 줄을 읽어와야함.
      coinList[i] = Integer.parseInt(br.readLine());
    }

    int count = 0;

    /* 그리디 알고리즘 : 큰 동전부터 사용해서 최적해 구하기 */
    // coinList[] 배열에 저장된 동전의 종류는 0 ~ 끝 오름차순
    // 끝 인덱스로 갈수록 동전 금액이 크다
    for(int i = N - 1; i >= 0; i--){
      if(K < coinList[i]) continue; // 50원 남았는데 100원단위면 건너뛰기
      count += K / coinList[i];
      K %= coinList[i];
      //K -= coinList[i] * (K / coinList[i]);
      if(K == 0) break; // 더이상 나누지 않아도 되니까 break;
    }
    return count;
  }
}
