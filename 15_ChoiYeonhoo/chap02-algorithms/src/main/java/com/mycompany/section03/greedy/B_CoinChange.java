package com.mycompany.section03.greedy;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/* 동전 교환 문제
* 주어진 N개의 동전을 이용하여
* 금액 K를 만들기 위한
* 동전의 최소 개수 구하기
*
* ex) N=3 ( 10, 50, 100 ), K = 670
*   결과 -> 9 (10*2, 50*1, 100*6) */
public class B_CoinChange {

  static int N; // 동전의 종류
  static int[] values; // 동전 가치
  static int K; // 총 가치
  static int count; // 동전 총 갯수
  static int[] coinCount;

  public static int solution(String input) throws IOException {
    count = 0;

    BufferedReader br = new BufferedReader(new StringReader(input));

    StringTokenizer st;
    st = new StringTokenizer(br.readLine());
    N = Integer.parseInt(st.nextToken());
    K = Integer.parseInt(st.nextToken());

    // A1부터 시작, 첫칸 제외
    values = new int[N+1];
    coinCount = new int[N+1];
    coinCount[0]=0;

    for(int i = 1; i<N+1;i++){
      // 동전의 종류 입력
//      st = new StringTokenizer(br.readLine());
//      values[i] = Integer.parseInt(st.nextToken());
      values[i] = Integer.parseInt(br.readLine());
    }

    // 계산 구간
    // values[i]는 values[i-1]의 배수 이므로, values[i]로 먼저 빼고, values[i-1]로 나중에 하면 될듯
    // 큰거부터 역순으로, 가장 작은 것은 무조건 1이므로 무조건 나누어 떨어짐
    for(int i = N; i > 0; i--){

      if (K < values[i]) continue;

      int tempCount = 0;
      tempCount = K/values[i];
      coinCount[i] = tempCount;

//      K -= tempCount * values[i];
      K = K % values[i];
      count += tempCount;

      if(K == 0) break;
    }
    System.out.println(Arrays.toString(values));
    System.out.println(Arrays.toString(coinCount));
    return count;
  }
}
