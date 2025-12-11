package com.ohgiraffers.section03.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/* 유니온 파인드 (Union-Find / Disjoint Set Union, DSU)
 * 집합 간의 연산을 효율적으로 처리하기 위해 설계된 데이터 구조
 * 특히 집합의 합집합(union)과 특정 원소가 속한 집합 찾기(find) 연산을 빠르게 처리하는데 유용하다.
 * 집합은 서로 다른 원소들로 구성되며, 초기에는 각 원소가 독립적인 집합을 형성한다.
 *
 * - 서로소 집합(Disjoint Set)을 표현하는 자료구조
 * - 두 가지 핵심 연산:
 *   1. Find: 원소가 속한 집합의 대표(루트) 찾기
 *   2. Union: 두 집합을 하나로 합치기
 *
 * === 시간 복잡도 ===
 * - 경로 압축 없이:
 *   - Find: O(N) (최악의 경우 일직선 트리)
 *   - Union: O(N)
 *
 * - 경로 압축 사용:
 *   - Find: O(α(N)) (거의 상수 시간)
 *   - Union: O(α(N))
 *   - α(N): 아커만 함수의 역함수 (매우 느리게 증가, 실용적으로 4~5)
 *
 * === 공간 복잡도 ===
 * O(N) - parent 배열
 *
 * === Union-Find의 응용 ===
 * 1. 크루스칼 알고리즘 (최소 신장 트리)
 *    - 간선 추가 시 사이클 검출
 * 2. 네트워크 연결성
 *    - 두 컴퓨터가 연결되어 있는가?
 * 3. 이미지 세그멘테이션
 *    - 같은 영역에 속한 픽셀 그룹화
 * 4. 소셜 네트워크
 *    - 친구 그룹 형성
 * 5. 게임 개발
 *    - 지역 연결, 팀 구성
 *
 *
 * === 주의사항 ===
 * 1. parent 배열 초기화 필수 (parent[i] = i)
 * 2. find 연산 시 경로 압축을 꼭 수행
 * 3. union 전에 반드시 find로 루트 확인
 * 4. 인덱스 범위 주의 (1부터 시작 vs 0부터 시작)
 * */

/*
 * === 문제 설명 ===
 * N명의 학생이 있고, M개의 친구 관계가 주어진다.
 * "친구의 친구는 친구다" 규칙에 따라 같은 그룹으로 묶는다.
 * 마지막에 주어진 두 학생이 같은 그룹인지 확인한다.*/
public class E_UnionAndFind {
  static int[] parent;  // parent[i]: i번 원소의 부모 노드 (루트면 자기 자신)

  /**
   * Find 연산: 특정 원소가 속한 집합의 대표(루트)를 찾는 연산
   * 경로 압축(Path Compression) 최적화를 사용하여 트리 높이를 낮춤
   *
   * @param x 찾고자 하는 원소
   * @return x가 속한 집합의 루트 노드
   */
  static int find(int x) {
    /* 집합의 대표 원소(루트 노드)를 찾는다 */
    // 대표 원소를 알면 두 원소가 같은 집합에 속하는지 알 수 있다

    /* 경로 압축 (Path Compression) */
    // parent[x] != x: x가 루트가 아니면 (자기 자신이 부모가 아니면)
    if (parent[x] != x) {
      // 재귀적으로 루트를 찾아서 parent[x]에 직접 연결
      // 이렇게 하면 다음번 find 연산이 O(1)에 가까워짐
      parent[x] = find(parent[x]);

      /* 경로 압축 예시:
       *   1           1
       *   ↓    →    ↙ ↘
       *   2         2  3
       *   ↓
       *   3
       * find(3) 호출 시 3이 1에 직접 연결됨
       */
    }

    // 루트 노드 반환 (x == parent[x]인 경우)
    return parent[x];
  }

  /**
   * Union 연산: 두 개의 집합을 하나로 합치는 연산
   * 두 집합의 대표 원소를 비교하여 두 집합이 연결되도록 한다
   *
   * @param x 첫 번째 원소
   * @param y 두 번째 원소
   */
  static void union(int x, int y) {
    /* 1단계: 각 원소의 루트 찾기 */
    int rootX = find(x);  // x가 속한 집합의 루트
    int rootY = find(y);  // y가 속한 집합의 루트

    /* 2단계: 두 루트가 다르면 합치기 */
    // rootX == rootY: 이미 같은 집합에 속함 → 합칠 필요 없음
    // rootX != rootY: 다른 집합 → 하나로 합치기
    if (rootX != rootY) {
      // 앞쪽 원소(rootX)를 전체 루트로 만드는 기준
      // rootY의 부모를 rootX로 변경
      parent[rootY] = rootX;

      /* Union 과정 시각화:
       * 합치기 전:
       *   rootX    rootY
       *     ↓        ↓
       *     x        y
       *
       * 합친 후:
       *     rootX
       *    ↙    ↘
       *   x    rootY
       *          ↓
       *          y
       */
    }

    /* 참고: Union by Rank 최적화 (선택 사항)
     * 트리의 높이를 고려하여 작은 트리를 큰 트리 아래에 붙임
     * if(rank[rootX] > rank[rootY]) {
     *     parent[rootY] = rootX;
     * } else if(rank[rootX] < rank[rootY]) {
     *     parent[rootX] = rootY;
     * } else {
     *     parent[rootY] = rootX;
     *     rank[rootX]++;
     * }
     */
  }

  /**
   * Union-Find를 사용하여 친구 관계를 판별하는 메서드
   *
   * @param input 입력 데이터 (학생 수 N, 관계 수 M, M개의 친구 쌍, 확인할 쌍)
   * @return "YES" (같은 그룹) 또는 "NO" (다른 그룹)
   */
  public static String solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st;

    /* 입력: 학생 수와 관계 수 읽어오기 */
    st = new StringTokenizer(br.readLine());
    int N = Integer.parseInt(st.nextToken());   // 학생 수 (1 ~ N번)
    int M = Integer.parseInt(st.nextToken());   // 주어진 친구 관계 수

    /* parent 배열 초기화 */
    // 처음에는 각 학생이 독립적인 집합 (자기 자신이 루트)
    parent = new int[N + 1];  // 0번 인덱스는 사용 안 함
    for (int i = 1; i <= N; i++) {
      parent[i] = i;  // 초기: parent[i] = i (자기 자신이 부모)
    }
    // 예: N=6이면 parent = [0, 1, 2, 3, 4, 5, 6]

    /* M개의 친구 관계 처리 (Union 연산) */
    for (int i = 0; i < M; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());  // 첫 번째 학생
      int b = Integer.parseInt(st.nextToken());  // 두 번째 학생

      /* a와 b를 같은 그룹으로 합치기 */
      union(a, b);

      /* 디버깅용 출력 (선택 사항) */
      System.out.println("union(" + a + "," + b + ")");
      System.out.println("parent : " + Arrays.toString(parent));

      /* 예시 출력:
       * union(1,2)
       * parent : [0, 1, 1, 3, 4, 5, 6]
       * union(2,3)
       * parent : [0, 1, 1, 1, 4, 5, 6]
       */
    }

    /* 마지막 쌍의 친구 관계 확인 (Find 연산) */
    st = new StringTokenizer(br.readLine());
    int x = Integer.parseInt(st.nextToken());  // 확인할 첫 번째 학생
    int y = Integer.parseInt(st.nextToken());  // 확인할 두 번째 학생

    /* 두 학생이 같은 그룹에 속하는지 확인 */
    // find(x) == find(y): 같은 루트를 가짐 → 같은 그룹
    // find(x) != find(y): 다른 루트를 가짐 → 다른 그룹
    if (find(x) == find(y)) {
      return "YES";  // 같은 그룹 (친구의 친구 관계로 연결됨)
    } else {
      return "NO";   // 다른 그룹 (연결되지 않음)
    }

    /* 예시:
     * parent = [0, 1, 1, 1, 4, 4, 4] (1,2,3이 한 그룹, 4,5,6이 한 그룹)
     * 확인: x=2, y=3
     *   - find(2) = 1
     *   - find(3) = 1
     *   - 1 == 1 → "YES"
     *
     * 확인: x=1, y=4
     *   - find(1) = 1
     *   - find(4) = 4
     *   - 1 != 4 → "NO"
     */
  }
}
