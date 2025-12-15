package com.ohgiraffers.section04.dp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

/* =====================================================
 * LCS (Longest Common Subsequence)
 * 최장 공통 부분 수열 - DP
 * =====================================================
 *
 * === 문제 설명 ===
 * - 두 개의 문자열(또는 수열)이 주어짐
 * - 각 문자열의 원래 순서를 유지하면서
 * - 공통으로 나타나는 부분 수열 중 가장 긴 것을 찾기
 *
 * === 예시 ===
 *
 * 문자열 1: "ABCBDAB"
 * 문자열 2: "BDCABA"
 *
 * 가능한 공통 부분 수열:
 * - "B", "A", "BA", "AB", "BCA", "BCBA", "BDAB" 등
 *
 * 최장 공통 부분 수열:
 * - "BCBA" (길이 4) ✓
 * - "BDAB" (길이 4) ✓
 *
 * 답: 4
 *
 * === 부분 수열(Subsequence) 이해 ===
 *
 * "ABCBDAB"의 부분 수열:
 * - "AB": A___B__ ✓ (순서 유지)
 * - "BCA": _BC__A_ ✓
 * - "CAB": 순서가 맞지 않음 X
 *
 * === LCS vs LIS ===
 *
 * LCS (Longest Common Subsequence):
 * - 2개 문자열의 공통 부분 수열
 * - dp[i][j]: 문자열1[0..i]와 문자열2[0..j]의 LCS 길이
 * - 시간: O(n × m)
 *
 * LIS (Longest Increasing Subsequence):
 * - 1개 수열의 증가하는 부분 수열
 * - dp[i]: i를 마지막으로 하는 LIS 길이
 * - 시간: O(n²) 또는 O(n log n)
 *
 * === DP 접근 방법 (Top-Down) ===
 * dp[x][y] = arr1[0..x]와 arr2[0..y]의 LCS 길이
 *
 * 점화식:
 * if (arr1[x] == arr2[y]):
 *     dp[x][y] = dp[x-1][y-1] + 1
 * else:
 *     dp[x][y] = max(dp[x-1][y], dp[x][y-1])
 *
 * === 점화식 유도 ===
 *
 * 경우 1: arr1[x] == arr2[y] (문자가 같음)
 * - 이 문자를 LCS에 포함
 * - 이전까지의 LCS에 1을 더함
 * - dp[x][y] = dp[x-1][y-1] + 1
 *
 * 경우 2: arr1[x] != arr2[y] (문자가 다름)
 * - x를 한 칸 뒤로 or y를 한 칸 뒤로
 * - 둘 중 큰 값 선택
 * - dp[x][y] = max(dp[x-1][y], dp[x][y-1])
 *
 * === 동작 과정 시각화 (Top-Down) ===
 *
 * arr1 = "ABCB"
 * arr2 = "BDCB"
 *
 * lcs(3, 3) 호출 (마지막 인덱스)
 * arr1[3]='B', arr2[3]='B' → 같음!
 * → lcs(2, 2) + 1
 *
 * lcs(2, 2) 호출
 * arr1[2]='C', arr2[2]='C' → 같음!
 * → lcs(1, 1) + 1
 *
 * lcs(1, 1) 호출
 * arr1[1]='B', arr2[1]='D' → 다름
 * → max(lcs(0, 1), lcs(1, 0))
 *
 * lcs(0, 1) 호출
 * arr1[0]='A', arr2[1]='D' → 다름
 * → max(lcs(-1, 1), lcs(0, 0))
 *
 * lcs(-1, 1) → 0 (기저 조건)
 *
 * lcs(0, 0) 호출
 * arr1[0]='A', arr2[0]='B' → 다름
 * → max(lcs(-1, 0), lcs(0, -1))
 * → max(0, 0) = 0
 *
 * lcs(1, 0) 호출
 * arr1[1]='B', arr2[0]='B' → 같음!
 * → lcs(0, -1) + 1 = 0 + 1 = 1
 *
 * 역순으로 결과 계산:
 * lcs(1, 1) = max(0, 1) = 1
 * lcs(2, 2) = 1 + 1 = 2
 * lcs(3, 3) = 2 + 1 = 3
 *
 * 최종 답: 3 ("BCB")
 *
 * === Bottom-Up 방식 테이블 ===
 *
 * arr1 = "ABCBDAB"
 * arr2 = "BDCABA"
 *
 * DP 테이블:
 *
 *       ''  B  D  C  A  B  A
 *    '' 0   0  0  0  0  0  0
 *    A  0   0  0  0  1  1  1
 *    B  0   1  1  1  1  2  2
 *    C  0   1  1  2  2  2  2
 *    B  0   1  1  2  2  3  3
 *    D  0   1  2  2  2  3  3
 *    A  0   1  2  2  3  3  4
 *    B  0   1  2  2  3  4  4
 *
 * dp[7][6] = 4 → LCS 길이
 *
 * === 테이블 작성 과정 상세 ===
 *
 * i=1, j=1: A vs B (다름)
 * dp[1][1] = max(dp[0][1], dp[1][0]) = max(0, 0) = 0
 *
 * i=1, j=4: A vs A (같음!)
 * dp[1][4] = dp[0][3] + 1 = 0 + 1 = 1
 *
 * i=2, j=1: B vs B (같음!)
 * dp[2][1] = dp[1][0] + 1 = 0 + 1 = 1
 *
 * i=7, j=6: B vs A (다름)
 * dp[7][6] = max(dp[6][6], dp[7][5]) = max(4, 4) = 4
 *
 * === LCS 역추적 (실제 문자열 구하기) ===
 *
 * dp[7][6] = 4에서 시작:
 *
 * 1. arr1[7]='B', arr2[6]='A' → 다름
 *    dp[6][6] = 4 → 위로 이동
 *
 * 2. arr1[6]='A', arr2[6]='A' → 같음! 'A' 선택
 *    dp[5][5] = 3 → 대각선 위로
 *
 * 3. arr1[5]='D', arr2[5]='B' → 다름
 *    dp[5][4] = 3 → 왼쪽으로
 *
 * 4. arr1[5]='D', arr2[4]='B' → 다름
 *    dp[4][4] = 3 → 위로
 *
 * 5. arr1[4]='B', arr2[4]='B' → 같음! 'B' 선택
 *    dp[3][3] = 2 → 대각선 위로
 *
 * 6. arr1[3]='B', arr2[3]='A' → 다름
 *    dp[2][3] = 2 → 위로
 *
 * 7. arr1[2]='C', arr2[3]='A' → 다름
 *    dp[2][2] = 2 → 왼쪽으로
 *
 * 8. arr1[2]='C', arr2[2]='C' → 같음! 'C' 선택
 *    dp[1][1] = 1 → 대각선 위로
 *
 * 9. arr1[1]='B', arr2[1]='D' → 다름
 *    dp[1][0] = 1 → 왼쪽으로
 *
 * 10. arr1[1]='B', arr2[0]='B' → 같음! 'B' 선택
 *     dp[0][-1] → 종료
 *
 * LCS: "BCBA" (역순으로 읽음)
 *
 * === Top-Down (메모이제이션) 장점 ===
 * - 필요한 부분만 계산
 * - 재귀로 직관적
 * - 코드가 간결
 *
 * === 시간 복잡도 ===
 * - 모든 셀 계산: O(n × m)
 * - n: arr1의 길이
 * - m: arr2의 길이
 *
 * === 공간 복잡도 ===
 * - dp 배열: O(n × m)
 * - Top-Down 재귀 스택: O(n + m)
 * - 전체: O(n × m)
 *
 * === 공간 최적화 ===
 * 이전 행만 참조하므로 1차원 배열로 최적화 가능:
 * → 공간 복잡도: O(min(n, m))
 *
 * === 변형 문제 ===
 * 1. Longest Common Substring (연속 부분 문자열)
 * 2. Edit Distance (편집 거리)
 * 3. Shortest Common Supersequence
 * 4. Diff 알고리즘 (버전 비교)
 *
 * === 실생활 응용 ===
 * - DNA 서열 비교 (생물정보학)
 * - 파일 비교 도구 (diff, git)
 * - 표절 검사
 * - 자동 완성 시스템
 * - 음성/영상 동기화
 *
 * === LCS와 Edit Distance의 관계 ===
 * Edit Distance (편집 거리):
 * - 삽입, 삭제, 교체 연산의 최소 횟수
 * - LCS를 이용해 계산 가능
 * - Edit Distance = (n - LCS) + (m - LCS)
 *
 * =====================================================
 */
public class I_LCS {

    static char[] arr1;      // 첫 번째 문자열
    static char[] arr2;      // 두 번째 문자열
    static Integer[][] dp;   // 메모이제이션 테이블

    public static int solution(String input) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(input));

        /* 입력 문자열을 char 배열로 변환 */
        arr1 = br.readLine().toCharArray();
        arr2 = br.readLine().toCharArray();

        /* DP 테이블 초기화
         * dp[x][y] = arr1[0..x]와 arr2[0..y]의 LCS 길이
         * Integer 타입으로 null 체크 가능
         */
        dp = new Integer[arr1.length][arr2.length];

        /* Top-Down 방식 (재귀 호출 + 메모이제이션)
         * 마지막 인덱스부터 시작하여 LCS 계산
         */
        return lcs(arr1.length-1, arr2.length-1);
    }

    /**
     * Top-Down 방식의 LCS 재귀 함수
     * @param x arr1의 현재 인덱스
     * @param y arr2의 현재 인덱스
     * @return arr1[0..x]와 arr2[0..y]의 LCS 길이
     */
    static int lcs(int x, int y) {

        /* Base Case: 재귀 호출 종료 조건
         * 인덱스가 음수가 되면 (범위 벗어남) 0 반환
         * 빈 문자열과의 LCS는 0
         */
        if(x < 0 || y < 0) return 0;

        /* 메모이제이션: 이미 계산된 값인지 확인
         * dp[x][y] == null: 아직 계산 안 됨 → 계산 필요
         * dp[x][y] != null: 이미 계산됨 → 저장된 값 반환
         */
        if(dp[x][y] == null) {
            /* 점화식 적용 */
            if(arr1[x] == arr2[y]) {
                /* 경우 1: 현재 문자가 같은 경우
                 * 이 문자를 LCS에 포함
                 * 이전까지의 LCS 길이에 1을 더함
                 *
                 * 예: "AB"와 "CB"의 마지막 'B'가 같으면
                 * → "A"와 "C"의 LCS + 1
                 */
                dp[x][y] = lcs(x-1, y-1) + 1;
            } else {
                /* 경우 2: 현재 문자가 다른 경우
                 * 두 가지 선택지 중 큰 값 선택:
                 *
                 * 선택 A: arr1에서 현재 문자 제외
                 *         → lcs(x-1, y)
                 * 선택 B: arr2에서 현재 문자 제외
                 *         → lcs(x, y-1)
                 *
                 * 예: "AB"와 "CD"의 마지막이 다르면
                 * → max("A"와 "CD", "AB"와 "C")
                 */
                dp[x][y] = Math.max(lcs(x-1, y), lcs(x, y-1));
            }
        }

        /* 계산된 값 반환 (메모이제이션된 값 재사용) */
        return dp[x][y];

    }

}
