package com.beyond.section03.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/* 다익스트라 알고리즘 (Dijkstra's Algorithm - Greedy Algorithm)
 * 음의 가중치가 없는 그래프에서 한 정점에서 모든 정점까지의 최단 거리를 구하는 알고리즘
 * 간선에 가중치가 없으면 BFS로도 가능하지만, 가중치가 있다면 최단 경로 보장이 어렵다.
 *
 * === 문제 설명 ===
 * - 가중치가 있는 방향 그래프가 주어진다
 * - 시작 정점에서 다른 모든 정점까지의 최단 거리를 구하라
 * - 모든 간선의 가중치는 양수 (음의 가중치 없음)
 *
 * === 다익스트라 알고리즘이란? ===
 * - "가장 가까운 정점부터 탐색"하는 그리디 알고리즘
 * - 우선순위 큐를 사용하여 효율적으로 구현
 * - 한 번 확정된 정점의 최단 거리는 변하지 않음 (Greedy Choice)
 *
 * === 왜 그리디 알고리즘인가? ===
 * 1. 매 단계마다 "현재까지의 최단 거리 정점"을 선택 (국소 최적)
 * 2. 선택한 정점을 통해 인접 정점까지의 거리 갱신
 * 3. 이 과정을 반복하면 전역 최적해(모든 최단 거리) 도출
 * 4. 양의 가중치에서만 작동 (음수 가중치는 벨만-포드 알고리즘 사용)
 *
 * === 예시 그래프 ===
 * 정점: 6개 (1, 2, 3, 4, 5, 6)
 * 간선:
 * 1 → 2 (가중치 2)
 * 1 → 3 (가중치 5)
 * 1 → 4 (가중치 1)
 * 2 → 3 (가중치 3)
 * 2 → 4 (가중치 2)
 * 3 → 2 (가중치 3)
 * 3 → 6 (가중치 5)
 * 4 → 3 (가중치 3)
 * 4 → 5 (가중치 1)
 * 5 → 3 (가중치 1)
 * 5 → 6 (가중치 2)
 *
 * 시작 정점: 1
 *
 * 그래프 시각화:
 *       2 ←―――3―――→ 2
 *      ↗ ↘       ↗ ↘
 *    2/   \3   3/   \5
 *    /  1  ↘   /     ↘
 *   1 ―――→ 4     →   6
 *    \      ↓ 1   ↗ 2
 *   5 \     5 ―――
 *      ↘   ↗ ↖ 1
 *       ↘ / 3
 *        3
 *
 * === 알고리즘 동작 과정 (단계별) ===
 * 초기 상태:
 * dis = [INF, 0, INF, INF, INF, INF, INF]
 *        (1번 정점의 거리는 0, 나머지는 무한대)
 * PriorityQueue = [(1, 0)]  // (정점, 거리)
 *
 * [반복 1] 큐에서 (1, 0) 꺼내기
 * - 현재 정점: 1, 비용: 0
 * - 1번의 인접 정점 확인:
 *   → 2번: dis[2] = INF > 0+2 → 갱신! dis[2] = 2, 큐에 추가 (2, 2)
 *   → 3번: dis[3] = INF > 0+5 → 갱신! dis[3] = 5, 큐에 추가 (3, 5)
 *   → 4번: dis[4] = INF > 0+1 → 갱신! dis[4] = 1, 큐에 추가 (4, 1)
 * dis = [INF, 0, 2, 5, 1, INF, INF]
 * PriorityQueue = [(4,1), (2,2), (3,5)]  // 비용 오름차순
 *
 * [반복 2] 큐에서 (4, 1) 꺼내기
 * - 현재 정점: 4, 비용: 1
 * - 4번의 인접 정점 확인:
 *   → 3번: dis[3] = 5 > 1+3 → 갱신! dis[3] = 4, 큐에 추가 (3, 4)
 *   → 5번: dis[5] = INF > 1+1 → 갱신! dis[5] = 2, 큐에 추가 (5, 2)
 * dis = [INF, 0, 2, 4, 1, 2, INF]
 * PriorityQueue = [(2,2), (5,2), (3,4), (3,5)]
 *
 * [반복 3] 큐에서 (2, 2) 꺼내기
 * - 현재 정점: 2, 비용: 2
 * - 2번의 인접 정점 확인:
 *   → 3번: dis[3] = 4 > 2+3? NO (5 > 4) → 갱신 안 함
 *   → 4번: dis[4] = 1 > 2+2? NO (4 > 1) → 갱신 안 함
 * dis = [INF, 0, 2, 4, 1, 2, INF]
 * PriorityQueue = [(5,2), (3,4), (3,5)]
 *
 * [반복 4] 큐에서 (5, 2) 꺼내기
 * - 현재 정점: 5, 비용: 2
 * - 5번의 인접 정점 확인:
 *   → 3번: dis[3] = 4 > 2+1 → 갱신! dis[3] = 3, 큐에 추가 (3, 3)
 *   → 6번: dis[6] = INF > 2+2 → 갱신! dis[6] = 4, 큐에 추가 (6, 4)
 * dis = [INF, 0, 2, 3, 1, 2, 4]
 * PriorityQueue = [(3,3), (3,4), (6,4), (3,5)]
 *
 * [반복 5] 큐에서 (3, 3) 꺼내기
 * - 현재 정점: 3, 비용: 3
 * - 3번의 인접 정점 확인:
 *   → 2번: dis[2] = 2 > 3+3? NO → 갱신 안 함
 *   → 6번: dis[6] = 4 > 3+5? NO → 갱신 안 함
 * dis = [INF, 0, 2, 3, 1, 2, 4]
 *
 * [반복 6] 큐에서 (3, 4) 꺼내기
 * - nowCost(4) > dis[3](3) → 이미 더 좋은 경로 존재 → continue (중요!)
 *
 * [반복 7] 큐에서 (6, 4) 꺼내기
 * - 6번은 목적지이므로 인접 정점 없음
 *
 * [반복 8] 큐에서 (3, 5) 꺼내기
 * - nowCost(5) > dis[3](3) → continue
 *
 * 큐가 비어서 종료!
 *
 * 최종 결과:
 * dis = [INF, 0, 2, 3, 1, 2, 4]
 *        (1번 정점에서 각 정점까지의 최단 거리)
 *
 * 1 → 2: 2 (1→2)
 * 1 → 3: 3 (1→4→5→3)
 * 1 → 4: 1 (1→4)
 * 1 → 5: 2 (1→4→5)
 * 1 → 6: 4 (1→4→5→6)
 *
 * === 핵심 원리 ===
 * 1. 우선순위 큐 사용: 항상 "현재까지 비용이 가장 작은" 정점을 꺼냄
 * 2. 거리 갱신 조건: dis[다음정점] > dis[현재정점] + 간선비용
 * 3. 중복 방지: nowCost > dis[now]면 이미 처리된 것 → skip
 * 4. 음의 가중치 불가: 음수가 있으면 이미 확정된 거리가 바뀔 수 있음
 *
 * === 시간 복잡도 ===
 * - 우선순위 큐 사용: O((V + E) log V)
 *   - V: 정점 개수, E: 간선 개수
 *   - 각 정점을 한 번씩 꺼냄: O(V log V)
 *   - 각 간선을 한 번씩 확인: O(E log V)
 *
 * - 배열 사용 (선형 탐색): O(V²)
 *   - 정점 수가 적을 때 사용
 *
 * === 공간 복잡도 ===
 * O(V + E) - 인접 리스트 + 거리 배열 + 우선순위 큐
 *
 * === BFS vs 다익스트라 ===
 * BFS:
 * - 가중치 없는 그래프 (또는 모든 가중치 = 1)
 * - 레벨 순서 탐색 (거리 = 간선 개수)
 * - 시간: O(V + E)
 *
 * 다익스트라:
 * - 가중치 있는 그래프 (양수만)
 * - 비용 순서 탐색 (거리 = 가중치 합)
 * - 시간: O((V + E) log V)
 *
 * === 다익스트라 vs 벨만-포드 vs 플로이드-워셜 ===
 * 다익스트라:
 * - 한 정점 → 모든 정점
 * - 양의 가중치만
 * - O((V+E) log V)
 *
 * 벨만-포드:
 * - 한 정점 → 모든 정점
 * - 음의 가중치 가능 (음의 사이클 감지)
 * - O(VE)
 *
 * 플로이드-워셜:
 * - 모든 정점 → 모든 정점
 * - 음의 가중치 가능
 * - O(V³)
 *
 * === 실생활 응용 ===
 * - 네비게이션 최단 경로 찾기
 * - 네트워크 라우팅 프로토콜 (OSPF)
 * - 게임 AI의 길찾기
 * - 배달 최적 경로
 * - 항공편 최저 요금 찾기
 *
 * === 주의사항 ===
 * 1. 음의 가중치가 있으면 사용 불가 → 벨만-포드 사용
 * 2. 우선순위 큐에서 꺼낸 정점이 이미 처리되었는지 확인 필수
 * 3. 거리 배열 초기화는 INF(무한대)로
 * 4. 시작 정점의 거리는 0으로 초기화
 * */
public class D_DijkstraAlgorithm {
  static int n, m, start;     // 정점 개수, 간선 개수, 시작 정점
  static int[] dis;           // 시작 정점에서 다른 노드까지의 최단 거리를 저장할 배열

  /**
   * 다익스트라 알고리즘을 사용하여 시작 정점에서 모든 정점까지의 최단 거리를 계산
   * @param input 입력 데이터 (정점 개수, 간선 개수, 시작 정점, 각 간선 정보)
   * @return 시작 정점에서 2번~N번 정점까지의 최단 거리 (공백으로 구분)
   */
  public static String solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st = new StringTokenizer(br.readLine());

    n = Integer.parseInt(st.nextToken());       // 정점의 개수
    m = Integer.parseInt(st.nextToken());       // 간선의 개수
    start = Integer.parseInt(st.nextToken());   // 시작할 정점 번호

    /* 인접 리스트로 그래프 표현 */
    // ArrayList<ArrayList<Edge>>: 각 정점마다 연결된 간선 리스트를 저장
    // graph.get(i): i번 정점에서 출발하는 간선들의 리스트
    ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) {
      graph.add(new ArrayList<>());  // 0번~n번 정점 초기화
    }

    /* 거리 배열 초기화 */
    // dis[i]: 시작 정점에서 i번 정점까지의 최단 거리
    dis = new int[n + 1];
    // 아직 거리가 확정되지 않은 정점은 무한대(INF)로 초기화
    // Integer.MAX_VALUE를 무한대로 사용
    Arrays.fill(dis, Integer.MAX_VALUE);

    /* 간선 정보 입력 */
    for (int i = 0; i < m; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());   // 시작 정점
      int b = Integer.parseInt(st.nextToken());   // 도착 정점
      int c = Integer.parseInt(st.nextToken());   // 가중치 (비용)

      // a → b로 가는 가중치 c인 간선 추가 (방향 그래프)
      graph.get(a).add(new Edge(b, c));
    }

    for (int i = 0; i < n; i++) {
      System.out.println(i + " : " + graph.get(i));

    }



    /* 다익스트라 알고리즘 시작 */

    /* 우선순위 큐 초기화 */
    // PriorityQueue<Edge>: 가중치가 낮은 간선이 먼저 나오는 Min-Heap
    // Edge 클래스의 compareTo() 메서드에 의해 자동 정렬
    PriorityQueue<Edge> pq = new PriorityQueue<>();

    /* 시작 정점 초기화 */
    pq.offer(new Edge(start, 0));   // 시작 정점을 큐에 추가 (거리 0)
    dis[start] = 0;                 // 시작 정점까지의 거리는 0

    /* 다익스트라 메인 루프 */
    while (!pq.isEmpty()) {
      System.out.println("우선순위큐 : " + pq);

      /* 1단계: 우선순위 큐에서 비용이 가장 작은 정점 꺼내기 */
      // 가장 가까운 정점을 먼저 처리 (Greedy Choice!)
      Edge tmp = pq.poll();
      int now = tmp.ver;          // 현재 처리할 정점
      int nowCost = tmp.cost;     // 시작점에서 현재 정점까지의 비용

      /* 2단계: 이미 처리된 정점인지 확인 (중복 처리 방지) */
      // nowCost > dis[now]: 큐에 담긴 비용이 이미 갱신된 최단 거리보다 크면
      // 이미 더 좋은 경로로 처리되었다는 의미 → 건너뜀
      // 이유: 같은 정점이 여러 번 큐에 들어갈 수 있음
      // 예: 정점 3이 (3, 5), (3, 4), (3, 3)으로 여러 번 추가될 수 있음
      if (nowCost > dis[now]) continue;

      /* 3단계: 현재 정점과 연결된 이웃 정점들을 확인 */
      // graph.get(now): 현재 정점(now)에서 출발하는 모든 간선
      for (Edge edge : graph.get(now)) {
        /* 거리 갱신 조건 확인 */
        // dis[edge.ver]: 현재까지 알려진 edge.ver까지의 최단 거리
        // nowCost + edge.cost: 현재 정점을 거쳐서 가는 거리
        // 만약 현재 정점을 거쳐가는 것이 더 짧다면?
        if (dis[edge.ver] > nowCost + edge.cost) {
          /* 더 짧은 경로 발견! 거리 갱신 */
          dis[edge.ver] = nowCost + edge.cost;

          /* 갱신된 정점을 우선순위 큐에 추가 */
          // 이 정점을 통해 다른 정점으로 가는 더 짧은 경로가 있을 수 있음
          pq.offer(new Edge(edge.ver, nowCost + edge.cost));

          /* 예시: 1→4→5 경로 발견
           * dis[5] = INF > 1+1 → dis[5] = 2로 갱신
           * pq에 (5, 2) 추가
           */
        }
      }
    }
    // 큐가 비면 모든 정점까지의 최단 거리 계산 완료!

    System.out.println("dis : " + Arrays.toString(dis));

    /* 결과 문자열 생성 */
    StringBuilder sb = new StringBuilder();

    // 2번 정점부터 N번 정점까지의 최단 거리 출력
    // (1번은 시작 정점이므로 제외)
    for (int i = 2; i < dis.length; i++) {
      if (dis[i] != Integer.MAX_VALUE) {
        // 도달 가능: 최단 거리 출력
        sb.append(dis[i]);
      } else {
        // 도달 불가능: "impossible" 출력
        // 시작 정점에서 i번 정점으로 가는 경로가 없음
        sb.append("impossible");
      }
      sb.append(" ");  // 공백으로 구분
    }

    /* 최종 결과 반환 (마지막 공백 제거) */
    return sb.toString().trim();
  }

  /**
   * 간선 정보를 저장하는 클래스
   * 우선순위 큐에서 비용(cost) 기준으로 정렬하기 위해 Comparable 구현
   */
  static class Edge implements Comparable<Edge> {
    int ver;                // 해당 간선이 연결된 도착 정점
    int cost;               // 간선의 가중치 (비용)

    Edge(int ver, int cost) {
      this.ver = ver;
      this.cost = cost;
    }

    /**
     * 우선순위 큐에서 비용이 낮은 간선이 높은 우선순위를 갖도록 설정
     * @param o 비교할 다른 Edge 객체
     * @return 음수: this가 우선, 양수: o가 우선, 0: 동일
     */
    @Override
    public int compareTo(Edge o) {
      return this.cost - o.cost;  // 가중치 오름차순 (작은 비용이 우선)
    }

    @Override
    public String toString() {
      return "(" +
          "ver=" + ver +
          ", cost=" + cost +
          ')';
    }
  }
}

