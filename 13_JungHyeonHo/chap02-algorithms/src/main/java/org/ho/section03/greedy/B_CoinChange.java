package org.ho.section03.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class B_CoinChange {
  public static int solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st = new StringTokenizer(br.readLine());
    int n = Integer.parseInt(st.nextToken()); /* 동전 종류 : N */
    int k = Integer.parseInt(st.nextToken()); /* 가치의 합 : K */
    int[] coins = new int[n];
    for (int i = 0; i < n; i++) {
      coins[i] = Integer.parseInt(br.readLine());
    }
    int ans = 0;
    int[] answer = greedy(coins, n - 1, k, new int[n]);

    return Arrays.stream(answer).sum();
  }

  /**
   * @param coins     동전 종류가 오름차순으로 저장되어 있음
   * @param coinIndex 현재 어떤 동전을 대상으로 조사하는지 알려줌
   * @param money     현재 소지금
   * @param answer    각 동전종류별로 몇개가 들어가는지
   * @return
   */

  private static int[] greedy(int[] coins, int coinIndex, int money, int[] answer) {
    if (coinIndex < 0)return answer;
    answer[coinIndex] = money / coins[coinIndex];
    money %= coins[coinIndex];
    return greedy(coins, coinIndex - 1, money, answer);
  }

  /*
  n= 48
  7 : 6 / 6 / 6
  3 : 1 / 2
  2 : 1 / 0
      1 / 0
  */
}