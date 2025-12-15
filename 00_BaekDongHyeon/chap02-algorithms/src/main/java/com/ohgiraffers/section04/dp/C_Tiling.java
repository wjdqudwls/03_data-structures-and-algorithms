package com.ohgiraffers.section04.dp;

/* 2 X N 직사각형을 
 * 2x1, 1x2 짜리 타일로 채우는 방법의 수 구하기
 * == 피보나치 수열
 */
public class C_Tiling {
  
  public static int solution(int n){

    /* DP : Bottom-up 방식 활용 */
    int[] dp = new int[n+1];

    dp[0] = 1; // 2x0을 채우는 방법 -> 1 가지(아무 것도 안 넣는 것도 방법)
    dp[1] = 1; // 2x1을 채우는 방법 -> 1 가지

    for(int i = 2 ; i <= n ; i++){
      dp[i] = dp[i-1] + dp[i-2];
    }

    return dp[n];
  }
}

