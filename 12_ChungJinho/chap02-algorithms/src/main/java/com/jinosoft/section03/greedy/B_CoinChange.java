package com.jinosoft.section03.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.StringTokenizer;

public class B_CoinChange {

  public static int solution(String input) throws IOException {

    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st = new StringTokenizer(br.readLine());

    int N = Integer.parseInt(st.nextToken());
    int K = Integer.parseInt(st.nextToken());

    int[] coins = new int[N];
    for (int i = 0; i < N; i++) {
      coins[i] = Integer.parseInt(br.readLine());
    }

    br.close();

    int count = 0;

    for (int i = N - 1; i >= 0; i--) {

      if (coins[i] > K) {
        continue;
      }

      // 현재 동전으로 만들 수 있는 최대 개수를 더함
      count += (K / coins[i]);

      // 해당 동전을 사용하고 남은 금액
      K %= coins[i];

      if (K == 0) {
        break;
      }
    }

    return count;
  }
}