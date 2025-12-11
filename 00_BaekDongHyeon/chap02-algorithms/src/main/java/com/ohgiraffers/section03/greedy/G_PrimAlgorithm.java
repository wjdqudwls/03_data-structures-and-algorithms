package com.ohgiraffers.section03.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

/* 프림 알고리즘 (Prim's Algorithm - Greedy Algorithm)
 * 최소 신장 트리(Minimum Spanning Tree, MST)를 구하는 대표적인 알고리즘
 * 시작 정점에서 출발하여, 최소 가중치 간선을 추가하면서 트리를 확장하는 방법
 * 우선순위 큐를 사용하여 가장 작은 가중치의 간선을 효율적으로 선택
 *
 * - 한 정점에서 시작하여 트리를 점진적으로 확장
 * - 현재 트리에 연결된 간선 중 가중치가 가장 작은 간선 선택 (Greedy!)
 * - 우선순위 큐로 효율적으로 최소 가중치 간선 선택
 * - visited 배열로 사이클 방지
 *
 * === 핵심 원리 ===
 * 1. 우선순위 큐 사용: 항상 "가장 작은 가중치의 간선"을 먼저 선택
 * 2. visited 배열: 이미 MST에 포함된 정점은 다시 선택 안 함
 * 3. 정점 중심 탐색: 크루스칼과 달리 정점을 하나씩 추가하며 확장
 * 4. 사이클 방지: visited 체크로 자동으로 사이클 방지
 *
 * === 시간 복잡도 ===
 * - 우선순위 큐 사용: O(E log V)
 *   - 각 간선을 한 번씩 PQ에 추가: O(E)
 *   - 각 추가/삭제: O(log V)
 * - 인접 행렬 + 선형 탐색: O(V²)
 *   - 정점 수가 많고 간선이 적을 때 비효율적
 *
 * === 공간 복잡도 ===
 * O(V + E) - 인접 리스트 + visited 배열 + 우선순위 큐
 *
 * === 주의사항 ===
 * 1. 시작 정점을 (정점, 0)으로 PQ에 추가 (거리 0)
 * 2. visited 체크를 poll 직후에 수행 (중복 처리 방지)
 * 3. 이미 방문한 정점의 간선은 PQ에 추가 안 함
 * 4. 무방향 그래프는 양방향으로 간선 추가
 * 5. 그래프가 연결되어 있지 않으면 MST 불가능
 * */
public class G_PrimAlgorithm {

  /**
   * 간선 정보를 저장하는 클래스
   * 우선순위 큐에서 가중치 기준으로 정렬하기 위해 사용
   */
  static class Edge {
    int vertex;   // 도착 정점
    int weight;   // 간선의 가중치

    Edge(int vertex, int weight) {
      this.vertex = vertex;
      this.weight = weight;
    }
  }

  /**
   * 프림 알고리즘을 사용하여 최소 신장 트리(MST)의 가중치 합을 계산
   *
   * @param input 입력 데이터 (정점 개수 V, 간선 개수 E, 각 간선 정보)
   * @return 최소 신장 트리의 가중치 합
   */
  public static Long solution(String input) throws IOException {

    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st = new StringTokenizer(br.readLine());

    int V = Integer.parseInt(st.nextToken());  // 정점의 개수
    int E = Integer.parseInt(st.nextToken());  // 간선의 개수

    /* 인접 리스트로 그래프 표현 */
    // graph[i]: i번 정점과 연결된 간선들의 리스트
    List<Edge>[] graph = new ArrayList[V + 1];
    for (int i = 1; i <= V; i++) {
      graph[i] = new ArrayList<>();
    }

    /* 간선 정보 입력 (무방향 그래프) */
    for (int i = 0; i < E; i++) {
      st = new StringTokenizer(br.readLine());
      int u = Integer.parseInt(st.nextToken());      // 시작 정점
      int v = Integer.parseInt(st.nextToken());      // 끝 정점
      int weight = Integer.parseInt(st.nextToken()); // 가중치

      // 무방향 그래프이므로 양방향 간선 추가
      graph[u].add(new Edge(v, weight));  // u → v
      graph[v].add(new Edge(u, weight));  // v → u
    }

    /* 방문 여부를 체크할 배열 */
    // visited[i] = true: i번 정점이 MST에 포함됨
    boolean[] visited = new boolean[V + 1];

    /* 우선순위 큐: 간선의 가중치를 기준으로 오름차순 정렬 */
    // 가중치가 작은 간선이 먼저 나옴 (Min-Heap)
    PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

    /* 프림 알고리즘 시작 */
    // 정점 1에서 시작 (임의의 시작점, 어느 정점에서 시작해도 결과는 같음)
    pq.add(new Edge(1, 0));  // (정점 1, 가중치 0)
    long totalWeight = 0;     // 최소 신장 트리의 총 가중치

    /* 프림 메인 루프 */
    while (!pq.isEmpty()) {
      /* 1단계: 우선순위 큐에서 가중치가 가장 작은 간선 꺼내기 */
      Edge edge = pq.poll();
      int currentVertex = edge.vertex;   // 현재 정점
      int currentWeight = edge.weight;   // 해당 정점으로 가는 간선의 가중치

      /* 2단계: 이미 방문한 정점인지 확인 */
      // visited[currentVertex] = true: 이미 MST에 포함됨 → 건너뛰기
      // 같은 정점이 여러 번 PQ에 들어갈 수 있으므로 중복 체크 필수
      if (visited[currentVertex]) continue;

      /* 3단계: 정점을 MST에 추가 */
      visited[currentVertex] = true;       // 방문 처리
      totalWeight += currentWeight;        // 가중치 누적

      /* 디버깅용 출력 (선택 사항) */
      System.out.println("정점 " + currentVertex + " 선택, 가중치: " + currentWeight);

      /* 4단계: 현재 정점과 연결된 모든 간선을 확인 */
      // 미방문 정점으로 가는 간선을 우선순위 큐에 추가
      for (Edge neighbor : graph[currentVertex]) {
        /* 아직 방문하지 않은 정점만 처리 */
        if (!visited[neighbor.vertex]) {
          // 이 간선을 우선순위 큐에 추가
          // 다음 단계에서 가장 작은 가중치의 간선이 선택됨
          pq.offer(neighbor);

          /* 예시: 정점 2에서 정점 3으로 가는 가중치 5 간선
           * pq.offer(new Edge(3, 5))
           * → 다음 단계에서 가중치가 가장 작은 간선이 먼저 선택됨
           */
        }
        // 이미 방문한 정점으로 가는 간선은 추가하지 않음 (사이클 방지)
      }
    }
    // 큐가 비면 모든 정점을 MST에 추가 완료!

    /* 최소 신장 트리의 총 가중치 반환 */
    return totalWeight;

    /* 예시:
     * 입력: V=4, E=5
     * 간선: (1-2,1), (1-3,3), (2-3,2), (2-4,5), (3-4,4)
     *
     * 처리:
     * 1. 정점 1 선택 (시작), weight = 0
     *    → PQ에 (2,1), (3,3) 추가
     * 2. 정점 2 선택 (가중치 1), weight = 0+1 = 1
     *    → PQ에 (3,2), (4,5) 추가
     * 3. 정점 3 선택 (가중치 2), weight = 1+2 = 3
     *    → PQ에 (4,4) 추가
     * 4. (3,3) 꺼냄 → 이미 방문 → 건너뜀
     * 5. 정점 4 선택 (가중치 4), weight = 3+4 = 7
     *    → PQ에 추가할 미방문 정점 없음
     * 6. (4,5) 꺼냄 → 이미 방문 → 건너뜀
     *
     * 최종 MST: 1-2(1), 2-3(2), 3-4(4)
     * 결과: 7
     */
  }
}
