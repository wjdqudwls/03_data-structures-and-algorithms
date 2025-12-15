package com.ohgiraffers.section04.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import java.util.StringTokenizer;

/* =====================================================
 * LIS (Longest Increasing Subsequence)
 * 최장 증가 부분 수열 - DP
 * =====================================================
 *
 * === 문제 설명 ===
 * - 주어진 수열에서 일부 원소를 선택
 * - 선택한 원소들이 증가하는 순서를 만족
 * - 원래 수열의 순서는 유지 (연속일 필요 없음)
 * - 가장 긴 증가 부분 수열의 길이 구하기
 *
 * === 예시 ===
 *
 * 수열: {10, 20, 10, 30, 20, 50}
 *
 * 가능한 증가 부분 수열:
 * 1) {10, 20, 30, 50} - 길이 4 ✓ 최대!
 * 2) {10, 20, 30} - 길이 3
 * 3) {10, 20, 50} - 길이 3
 * 4) {10, 30, 50} - 길이 3
 * 5) {10, 20} - 길이 2
 *
 * 답: 4
 *
 * === 부분 수열(Subsequence)이란? ===
 * - 원래 순서를 유지하면서 일부 원소 선택
 * - 연속일 필요 없음 (Subarray와의 차이점!)
 *
 * 예: {10, 20, 10, 30}
 * - {10, 20, 30}: 부분 수열 ✓ (순서 유지)
 * - {10, 30, 20}: 부분 수열 X (순서 바뀜)
 * - {20, 30}: 부분 수열 ✓
 *
 * === DP 접근 방법 ===
 * dp[i] = i번째 원소를 마지막으로 하는 최장 증가 부분 수열의 길이
 *
 * 점화식:
 * dp[i] = max(dp[j] + 1) for all j < i where arr[j] < arr[i]
 *
 * === 점화식 유도 ===
 *
 * i번째 원소를 마지막으로 하는 LIS 길이 = ?
 *
 * 1. i 이전의 모든 원소 j (j < i) 확인
 * 2. arr[j] < arr[i]인 경우만 고려 (증가 조건)
 * 3. dp[j] + 1 중 최대값이 dp[i]
 *
 * 초기값: dp[i] = 1 (자기 자신만으로도 길이 1)
 *
 * === 동작 과정 시각화 ===
 *
 * 입력: {10, 20, 10, 30, 20, 50}
 * 인덱스: 1   2   3   4   5   6
 *
 * 초기화:
 * dp = [1, 1, 1, 1, 1, 1]
 *
 * i=1 (arr[1]=10):
 * - 앞에 비교할 원소 없음
 * - dp[1] = 1
 *
 * i=2 (arr[2]=20):
 * - j=1: arr[1]=10 < 20 ✓
 *   dp[2] = max(dp[2], dp[1]+1) = max(1, 2) = 2
 * - dp[2] = 2
 * - LIS: {10, 20}
 *
 * i=3 (arr[3]=10):
 * - j=1: arr[1]=10 = 10 ✗ (증가 아님)
 * - j=2: arr[2]=20 > 10 ✗
 * - dp[3] = 1
 * - LIS: {10}
 *
 * i=4 (arr[4]=30):
 * - j=1: arr[1]=10 < 30 ✓
 *   dp[4] = max(dp[4], dp[1]+1) = max(1, 2) = 2
 * - j=2: arr[2]=20 < 30 ✓
 *   dp[4] = max(dp[4], dp[2]+1) = max(2, 3) = 3
 * - j=3: arr[3]=10 < 30 ✓
 *   dp[4] = max(dp[4], dp[3]+1) = max(3, 2) = 3
 * - dp[4] = 3
 * - LIS: {10, 20, 30}
 *
 * i=5 (arr[5]=20):
 * - j=1: arr[1]=10 < 20 ✓
 *   dp[5] = max(1, 2) = 2
 * - j=2: arr[2]=20 = 20 ✗
 * - j=3: arr[3]=10 < 20 ✓
 *   dp[5] = max(2, 2) = 2
 * - j=4: arr[4]=30 > 20 ✗
 * - dp[5] = 2
 * - LIS: {10, 20}
 *
 * i=6 (arr[6]=50):
 * - j=1: arr[1]=10 < 50 ✓
 *   dp[6] = max(1, 2) = 2
 * - j=2: arr[2]=20 < 50 ✓
 *   dp[6] = max(2, 3) = 3
 * - j=3: arr[3]=10 < 50 ✓
 *   dp[6] = max(3, 2) = 3
 * - j=4: arr[4]=30 < 50 ✓
 *   dp[6] = max(3, 4) = 4
 * - j=5: arr[5]=20 < 50 ✓
 *   dp[6] = max(4, 3) = 4
 * - dp[6] = 4
 * - LIS: {10, 20, 30, 50}
 *
 * 최종:
 * arr = [10, 20, 10, 30, 20, 50]
 * dp  = [ 1,  2,  1,  3,  2,  4]
 *
 * 답: max(dp) = 4
 *
 * === 테이블 작성 과정 ===
 *
 * i\j    1(10)  2(20)  3(10)  4(30)  5(20)  6(50)
 * 1(10):   1
 * 2(20):   ↑      2
 * 3(10):   -      -      1
 * 4(30):   ↑      ↑      ↑      3
 * 5(20):   ↑      -      ↑      -      2
 * 6(50):   ↑      ↑      ↑      ↑      ↑      4
 *
 * ↑ = arr[j] < arr[i] (dp 갱신 가능)
 *
 * === LIS 역추적 (실제 수열 구하기) ===
 *
 * 최대값 dp[6] = 4부터 역추적:
 *
 * dp[6] = 4, arr[6] = 50
 * → dp[4] = 3인 30을 찾음 (dp[4] + 1 = 4)
 *
 * dp[4] = 3, arr[4] = 30
 * → dp[2] = 2인 20을 찾음 (dp[2] + 1 = 3)
 *
 * dp[2] = 2, arr[2] = 20
 * → dp[1] = 1인 10을 찾음 (dp[1] + 1 = 2)
 *
 * LIS: {10, 20, 30, 50}
 *
 * === 시간 복잡도 ===
 * - 이중 반복문: i = 1~n, j = 1~i
 * - O(n²)
 *
 * === 공간 복잡도 ===
 * - arr 배열: O(n)
 * - dp 배열: O(n)
 * - 전체: O(n)
 *
 * === 최적화: O(n log n) 풀이 ===
 * 이진 탐색을 사용하여 O(n log n)으로 개선 가능:
 *
 * 1. lis 배열 유지 (현재까지의 최장 증가 부분 수열)
 * 2. 각 원소마다:
 *    - lis의 마지막보다 크면 추가
 *    - 아니면 이진 탐색으로 적절한 위치에 교체
 * 3. lis의 길이가 답
 *
 * === LIS vs LCS ===
 *
 * LIS (Longest Increasing Subsequence):
 * - 1개 수열에서 증가하는 부분 수열
 * - 시간: O(n²) 또는 O(n log n)
 *
 * LCS (Longest Common Subsequence):
 * - 2개 수열에서 공통 부분 수열
 * - 시간: O(n × m)
 *
 * === 변형 문제 ===
 * 1. Longest Decreasing Subsequence (감소 수열)
 * 2. Longest Non-decreasing Subsequence (비내림 수열)
 * 3. Number of LIS (LIS 개수)
 * 4. Russian Doll Envelopes (2D LIS)
 *
 * === 실생활 응용 ===
 * - 버전 관리 시스템 (업그레이드 경로)
 * - 주가 분석 (최장 상승 구간)
 * - 작업 스케줄링 (의존성 순서)
 * - 게임 레벨 진행 순서
 *
 * =====================================================
 */
public class H_LIS {
    public static int solution(String input) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(input));
        int n = Integer.parseInt(br.readLine());

        /* 배열 초기화 */
        int[] arr = new int[n + 1];  // 입력 수열
        int[] dp = new int[n + 1];   // dp[i] = i를 마지막으로 하는 LIS 길이

        /* 입력 읽기 및 dp 초기화 */
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            dp[i] = 1;  // 초기값: 자기 자신만으로 길이 1
        }

        /* 최장 증가 부분 수열 길이 추적 */
        int max = 1;

        /* DP로 LIS 길이 계산
         * i번째 원소를 마지막으로 하는 LIS 길이 계산
         */
        for(int i = 1; i <= n; i++) {
            /* i 이전의 모든 원소 j 확인 */
            for(int j = 1; j < i; j++) {
                /* 증가 조건 확인: arr[j] < arr[i]
                 * j번째 원소 다음에 i번째 원소를 붙일 수 있는가?
                 */
                if(arr[i] > arr[j]) {
                    /* 증가 조건 만족!
                     * 현재 값(dp[i])과 j를 거쳐 오는 경로(dp[j]+1) 중 큰 값 선택
                     *
                     * 예: arr = [10, 20, 10, 30]
                     * i=4 (30)일 때:
                     * - j=1: dp[4] = max(1, dp[1]+1) = max(1, 2) = 2
                     * - j=2: dp[4] = max(2, dp[2]+1) = max(2, 3) = 3
                     * - j=3: dp[4] = max(3, dp[3]+1) = max(3, 2) = 3
                     *
                     * 계속 업데이트하며 최대값 찾기
                     */
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            /* 전체 최대값 갱신 */
            max = Math.max(max, dp[i]);
        }

        /* 최장 증가 부분 수열의 길이 반환 */
        return max;
    }
}
