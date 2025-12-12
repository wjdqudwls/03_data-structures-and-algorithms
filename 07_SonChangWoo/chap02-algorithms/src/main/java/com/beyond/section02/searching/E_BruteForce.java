package com.beyond.section02.searching;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/* 완전 탐색(BruteForce) + 백트래킹 (Backtracking)
* 주어진 정수들의 모든 순열(순서 있는 조합)을 구하는 문제
* 가능한 모든 경우의 수를 탐색하는 '완전 탐색' 방식을 사용하지만,
* 백트래킹을 활용하여 이미 사용한 정수를 다시 선택하지 않음으로써 가지치기를 수행하고
* 불필요한 탐색을 줄여 효율적으로 모든 순열의 개수를 구한다.
*
* === 완전 탐색(Brute Force)이란? ===
* 모든 가능한 경우의 수를 전부 탐색하는 방법
* - 장점: 항상 정확한 답을 찾을 수 있음
* - 단점: 경우의 수가 많으면 시간이 오래 걸림
*
* === 백트래킹(Backtracking)이란? ===
* 완전 탐색 중 불필요한 경로를 조기에 차단(가지치기)하는 기법
* - DFS 기반으로 동작
* - 조건을 만족하지 않으면 더 이상 탐색하지 않고 이전 단계로 돌아감
* - "선택 → 탐색 → 복구" 과정을 반복
*
* === 순열(Permutation) 예시 ===
* 입력: [1, 2, 3]
* 순열 개수: 3! = 6개
*
* 모든 순열:
* [1, 2, 3]
* [1, 3, 2]
* [2, 1, 3]
* [2, 3, 1]
* [3, 1, 2]
* [3, 2, 1]
*
* === 백트래킹 탐색 트리 시각화 ===
* 입력: [1, 2, 3]
*
*                    []
*                  / | \
*                 1  2  3
*                /|  |\  |\
*               2 3  1 3  1 2
*               |  |  | |  | |
*               3  2  3 1  2 1
*
* 탐색 과정 (depth 순서):
* depth=0: [] (시작)
* depth=1: [1] 선택 (visited[0]=true)
*   depth=2: [1,2] 선택 (visited[1]=true)
*     depth=3: [1,2,3] 선택 (visited[2]=true) → 완성! count=1
*     depth=2로 복구: [1,2] (visited[2]=false, perm에서 3 제거)
*   depth=2: [1,3] 선택 (visited[2]=true)
*     depth=3: [1,3,2] 선택 (visited[1]=true) → 완성! count=2
*     depth=2로 복구: [1,3] (visited[1]=false, perm에서 2 제거)
*   depth=1로 복구: [1] (visited[2]=false, perm에서 3 제거)
* depth=1로 복구: [] (visited[0]=false, perm에서 1 제거)
* depth=1: [2] 선택... (이하 동일한 방식으로 진행)
*
* === 백트래킹의 핵심 3단계 ===
* 1. 선택(Choose): visited[i] = true, perm.add(nums[i])
* 2. 탐색(Explore): backtrack(depth + 1, n, perm)
* 3. 복구(Unchoose): perm.remove(), visited[i] = false
*
* === 가지치기(Pruning) 효과 ===
* 백트래킹 없이: 3^3 = 27번 탐색 (중복 허용)
* 백트래킹 사용: 3! = 6번만 탐색 (visited 체크로 중복 방지)
* 효율성: 불필요한 21번의 탐색을 제거!
* */
public class E_BruteForce {

    // 순열의 총 개수를 저장할 변수
    static int count = 0;

    // 입력 받은 정수 배열 (예: [1, 2, 3])
    static int[] nums;

    // 각 정수의 사용 여부를 체크하는 배열
    // visited[i] = true: nums[i]가 현재 순열에서 사용 중
    // visited[i] = false: nums[i]를 아직 사용하지 않음 (선택 가능)
    static boolean[] visited;

    /**
     * 모든 순열의 개수를 구하는 메서드
     * @param input 공백으로 구분된 정수들 (예: "1 2 3")
     * @return 생성 가능한 순열의 총 개수 (n!)
     */
    public static Integer solution(String input) {
        // 입력 문자열을 토큰으로 분리
        StringTokenizer st = new StringTokenizer(input.trim());

        // 정수의 개수 계산
        int n = st.countTokens();

        // 정수 배열 생성 및 값 저장
        nums = new int[n];
        for(int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        // 방문 배열 초기화 (모두 false로 시작)
        visited = new boolean[n];

        // 순열의 개수 초기화
        count = 0;

        /* 백트래킹 시작 */
        // depth=0 (아직 아무것도 선택하지 않은 상태)에서 시작
        // 빈 리스트로 순열 생성 시작
        backtrack(0, n, new ArrayList<>());

        // 찾은 순열의 총 개수 반환
        return count;
    }
    /**
     * 백트래킹을 이용한 순열 생성 메서드 (재귀)
     * @param depth 현재까지 선택한 정수의 개수 (재귀 깊이)
     * @param n 전체 정수 개수 (목표 깊이)
     * @param perm 현재까지 만든 순열 (부분 순열)
     */
    private static void backtrack(int depth, int n, List<Integer> perm) {
        /* 기저 조건 (Base Case): 순열 완성 */
        if(depth == n) {
            /* 모든 정수를 한 번씩 사용했다 = 유효한 순열 1개 완성 */
            System.out.println(perm);   // 디버깅용 출력 (순열 확인)
            count++;                    // 순열 개수 증가
            return;                     // 재귀 종료
        }

        /* 재귀 단계: 아직 순열이 완성되지 않음 */
        // 사용하지 않은 정수를 하나씩 선택하여 재귀 탐색

        for(int i = 0; i < n; i++) {
            /* 가지치기(Pruning): 이미 사용한 정수는 건너뛰기 */
            if(!visited[i]) {   // nums[i]를 아직 사용하지 않았다면

                /* 1단계: 선택 (Choose) */
                visited[i] = true;        // nums[i]를 사용 중으로 표시
                perm.add(nums[i]);        // 순열에 nums[i] 추가

                /* 2단계: 탐색 (Explore) */
                // 다음 깊이로 재귀 호출 (depth + 1)
                // depth가 증가하면서 순열이 하나씩 완성됨
                backtrack(depth + 1, n, perm);

                /* 3단계: 복구 (Unchoose) - 백트래킹의 핵심! */
                // 재귀에서 돌아온 후, 선택을 취소하고 다른 경로 탐색 준비
                perm.remove(perm.size() - 1);  // 순열에서 마지막 원소 제거
                visited[i] = false;            // nums[i]를 미사용 상태로 복구

                // 이제 다음 i에 대해 다른 정수를 선택하여 탐색
            }
        }
        // 모든 i에 대해 탐색 완료 후, 이전 depth로 복귀 (자동 백트래킹)
    }
}
