package com.ohgiraffers.section04.dp;

import java.util.Arrays;

/* ===== 문제 =====
 * N kg의 설탕을 배달해야 함.
 * 봉지는 3kg, 5kg만 있음.
 * 최소한의 봉지 개수로 정확히 N kg을 배달해야함.
 * 정확히 만들 수 없으면 -1 반환
 */
public class B_SugarDelivery {

  // 불가능한 경우를 나타내는 큰 값
  static final int INF = 9999;

  /**
   * 배달할 설탕 봉지 수 계산
   * @param n 요구 설탕 kg
   * @return 최소 봉지 수(불가능 -1)
   */
  public static int solution(int n){

    // dp 배열의 인덱스 == 해당 kg의 봉지 개수
    // dp[i] == i kg을 만드는데 필요한 최소 봉지 수
    int[] dp = new int[n + 1];

    // 배열 모든 요소를 INF로 초기화
    // INF == 아직 계산 X 또는 불가능
    Arrays.fill(dp, INF);

    /* Base Case 설정
    * n으로 전달되는 숫자가 작으면
    * 인덱스 범위를 벗어날 수 있기 때문에
    * 초기 값 설정 시 확인하고 이후 반복문 수행
    * */
    if(n >= 3) dp[3] = 1; // 3kg 요청 -> 3kg 설탕 1봉지
    if(n >= 5) dp[5] = 1; // 5kg 요청 -> 5kg 설탕 1봉지

    /* Bottom-Up DP */
    for(int i = 6 ; i <= n ; i++){
      dp[i] = Math.min(dp[i-3], dp[i-5]) + 1;
    }

    // 결과 반환
    return dp[n] >= INF ? -1 : dp[n];
  }
}
