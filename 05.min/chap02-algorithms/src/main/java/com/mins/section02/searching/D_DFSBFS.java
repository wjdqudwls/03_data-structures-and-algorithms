package com.mins.section02.searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/* DFS와 BFS 탐색 순서 비교 문제
 * 동일한 그래프에서 DFS와 BFS를 각각 수행하여 탐색 순서의 차이를 확인한다.
 *
 * === DFS vs BFS 탐색 순서 비교 예시 ===
 * 다음과 같은 그래프가 있을 때:
 *
 *       1
 *      /|\
 *     2 3 4
 *    /     \
 *   5       6
 *
 * 연결 관계:
 * 1-2, 1-3, 1-4
 * 2-5
 * 4-6
 *
 * 시작 노드: 1
 *
 * === DFS 탐색 (깊이 우선) ===
 * 1 → 2 → 5 → (백트래킹) → 3 → (백트래킹) → 4 → 6
 * 결과: "1 2 5 3 4 6"
 *
 * 동작 과정:
 * 1. 1 방문 → 출력 "1"
 * 2. 1의 이웃 중 2 방문 → 출력 "2"
 * 3. 2의 이웃 중 5 방문 → 출력 "5"
 * 4. 5는 더 이상 이웃 없음 → 2로 백트래킹
 * 5. 2는 더 이상 이웃 없음 → 1로 백트래킹
 * 6. 1의 다음 이웃 3 방문 → 출력 "3"
 * 7. 3은 더 이상 이웃 없음 → 1로 백트래킹
 * 8. 1의 다음 이웃 4 방문 → 출력 "4"
 * 9. 4의 이웃 6 방문 → 출력 "6"
 *
 * === BFS 탐색 (너비 우선) ===
 * 1 → 2 → 3 → 4 → 5 → 6
 * 결과: "1 2 3 4 5 6"
 *
 * 동작 과정 (Queue 상태 표시):
 * 1. Queue = [1], 1 꺼내서 출력 "1", 1의 이웃 2,3,4 추가
 * 2. Queue = [2,3,4], 2 꺼내서 출력 "2", 2의 이웃 5 추가
 * 3. Queue = [3,4,5], 3 꺼내서 출력 "3"
 * 4. Queue = [4,5], 4 꺼내서 출력 "4", 4의 이웃 6 추가
 * 5. Queue = [5,6], 5 꺼내서 출력 "5"
 * 6. Queue = [6], 6 꺼내서 출력 "6"
 *
 * === 핵심 차이점 ===
 * DFS: 한 방향으로 끝까지 깊게 탐색 → 백트래킹 → 다른 방향 탐색
 * BFS: 현재 레벨의 모든 노드 탐색 → 다음 레벨로 이동
 *
 * DFS 레벨별 방문: [1] → [2] → [5] → [3] → [4] → [6]
 * BFS 레벨별 방문: [1] → [2,3,4] → [5,6]
 * */
public class D_DFSBFS {
  static int node;           // 정점(노드)의 개수
  static int edge;           // 간선(엣지)의 개수
  static int start;          // 탐색을 시작할 노드 번호
  static int[][] map;        // 그래프의 인접 행렬
  static boolean[] visit;    // 방문 여부 배열
  static StringBuilder sb;   // 탐색 결과를 저장할 문자열
  static Queue<Integer> q = new LinkedList<>();  // BFS용 Queue

  /**
   * DFS와 BFS 탐색을 수행하는 메서드
   * @param input 입력 데이터 (노드 수, 간선 수, 시작 노드, 간선 정보)
   * @return DFS 탐색 결과 + "\n" + BFS 탐색 결과
   */
  public static String solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st = new StringTokenizer(br.readLine());

    // 첫 번째 줄: 정점의 개수(N), 간선의 개수(M), 탐색 시작 노드(V)
    node = Integer.parseInt(st.nextToken());   // 정점 개수
    edge = Integer.parseInt(st.nextToken());   // 간선 개수
    start = Integer.parseInt(st.nextToken());  // 시작 노드

    // 그래프와 방문 배열 초기화
    map = new int[node + 1][node + 1];
    visit = new boolean[node + 1];

    /* 그래프의 간선 정보 입력 */
    for (int i = 1; i <= edge; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());

      // 무방향 그래프이므로 양방향 연결
      map[a][b] = map[b][a] = 1;
    }

    // 결과를 저장할 StringBuilder 초기화
    sb = new StringBuilder();

    /* 1단계: DFS 탐색 수행 */
    dfs(start);
    sb.append("\n");  // DFS 결과와 BFS 결과 구분을 위한 줄바꿈

    /* 2단계: 방문 배열 초기화 (DFS에서 사용한 방문 기록 제거) */
    // BFS는 새로운 탐색이므로 방문 배열을 초기화해야 함
    visit = new boolean[node + 1];

    /* 3단계: BFS 탐색 수행 */
    bfs(start);

    // DFS 결과 + "\n" + BFS 결과 반환
    return sb.toString();
  }

  /**
   * DFS (깊이 우선 탐색) - 재귀 방식
   * 한 방향으로 끝까지 깊게 탐색한 후, 백트래킹하여 다른 경로 탐색
   * @param start 현재 방문하는 노드
   */
  static void dfs(int start) {
    /* 1단계: 현재 노드 방문 처리 및 결과에 추가 */
    visit[start] = true;           // 방문 표시
    sb.append(start).append(" ");  // 탐색 순서 기록

    /* 2단계: 현재 노드와 연결된 모든 노드 탐색 */
    // 노드 번호가 작은 것부터 방문 (1번부터 순서대로 확인)
    for (int i = 1; i <= node; i++) {
      /* 연결되어 있고 아직 방문하지 않은 노드를 발견하면 */
      if(map[start][i] == 1 && !visit[i]) {
        /* 3단계: 즉시 그 노드로 깊게 들어감 (재귀 호출) */
        // 재귀가 끝나면 자동으로 현재 노드로 돌아옴 (백트래킹)
        dfs(i);
      }
    }
    // 더 이상 방문할 이웃이 없으면 자동으로 이전 노드로 복귀 (백트래킹)
  }

  /**
   * BFS (너비 우선 탐색) - Queue 사용
   * 시작 노드에서 가까운 노드부터 레벨 순서로 탐색
   * @param start 탐색 시작 노드
   */
  static void bfs(int start) {
    /* 1단계: 시작 노드를 Queue에 추가하고 방문 처리 */
    q.offer(start);           // Queue에 시작 노드 추가
    visit[start] = true;      // 시작 노드 방문 표시

    /* 2단계: Queue가 빌 때까지 반복 (FIFO: 선입선출) */
    while(!q.isEmpty()) {
      /* Queue에서 노드를 꺼내서 방문 */
      start = q.poll();              // 가장 먼저 들어간 노드를 꺼냄
      sb.append(start).append(" ");  // 탐색 순서 기록

      /* 3단계: 현재 노드와 연결된 모든 미방문 노드를 Queue에 추가 */
      // 노드 번호가 작은 것부터 Queue에 추가 (1번부터 순서대로 확인)
      for (int i = 1; i <= node; i++) {
        /* 연결되어 있고 아직 방문하지 않은 노드를 발견하면 */
        if(map[start][i] == 1 && !visit[i]) {
          q.offer(i);          // Queue에 추가 (나중에 방문하기 위해)
          visit[i] = true;     // 방문 표시 (중복 추가 방지)
          // 주의: Queue에 넣을 때 바로 방문 처리를 해야 중복 추가 방지
        }
      }
    }
    // Queue가 비면 모든 연결된 노드 탐색 완료
  }
}