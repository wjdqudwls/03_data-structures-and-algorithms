package com.jinosoft.section03.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * 다익스트라 알고리즘 (Dijkstra)
 * - 한 정점에서 다른 모든 정점까지의 최단 거리를 구하는 알고리즘
 * - "가장 가까운 정점부터" 처리하는 방식
 * - 모든 간선 가중치는 양수여야 함
 */
public class D_DijkstraAlgorithm {
  static int n, m, start;     // 정점 수, 간선 수, 시작 정점 번호
  static int[] dis;           // 시작 정점에서 각 정점까지의 최단 거리 저장

  /**
   * 다익스트라 실행 메서드
   * @param input 입력 문자열(정점 수, 간선 수, 시작 정점, 간선 목록)
   * @return 2번~N번 정점까지의 최단 거리 결과
   */
  public static String solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st = new StringTokenizer(br.readLine());

    n = Integer.parseInt(st.nextToken());
    m = Integer.parseInt(st.nextToken());
    start = Integer.parseInt(st.nextToken());

    /* 그래프 만들기 (인접 리스트)
     * graph.get(a) → a 정점에서 갈 수 있는 간선 목록
     */
    ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
    for (int i = 0; i <= n; i++) {
      graph.add(new ArrayList<>());
    }

    /* 거리 배열 초기화
     * - 처음에는 모두 '무한대'로 두고
     * - 시작 정점만 0으로 설정할 예정
     */
    dis = new int[n + 1];
    Arrays.fill(dis, Integer.MAX_VALUE);

    /* 간선 입력 (a → b, 비용 c) */
    for (int i = 0; i < m; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      int c = Integer.parseInt(st.nextToken());
      graph.get(a).add(new Edge(b, c));
    }

    /* 우선순위 큐(최소 힙)
     * - 항상 "가장 비용이 낮은" 정점을 먼저 꺼냄
     */
    PriorityQueue<Edge> pq = new PriorityQueue<>();

    /* 시작 정점 설정
     * - 시작 정점 거리 = 0
     * - 큐에 시작 정점 넣기
     */
    pq.offer(new Edge(start, 0));
    dis[start] = 0;

    /* 다익스트라 본격 시작 */
    while (!pq.isEmpty()) {
      Edge tmp = pq.poll();   // 가장 비용이 작은 정점 꺼내기
      int now = tmp.ver;      // 현재 정점
      int nowCost = tmp.cost; // 현재 정점까지의 거리

      /* 이 정보가 최신이 아니면 건너뛰기
       * - 더 짧은 거리로 이미 갱신된 적이 있으면 skip
       */
      if (nowCost > dis[now]) continue;

      /* 현재 정점에서 갈 수 있는 정점들 확인 */
      for (Edge next : graph.get(now)) {

        /* 현재 정점을 거쳐 가는 게 더 짧다면 거리 갱신 */
        if (dis[next.ver] > nowCost + next.cost) {
          dis[next.ver] = nowCost + next.cost;

          /* 갱신된 정점을 큐에 다시 넣기
           * - 나중에 이 정점을 기준으로 또 다른 정점 거리 갱신 가능
           */
          pq.offer(new Edge(next.ver, dis[next.ver]));
        }
      }
    }

    /* 최종 거리 출력 형식 만들기 */
    StringBuilder sb = new StringBuilder();
    for (int i = 2; i < dis.length; i++) {
      if (dis[i] == Integer.MAX_VALUE) sb.append("impossible");
      else sb.append(dis[i]);
      sb.append(" ");
    }

    return sb.toString().trim();
  }

  /**
   * 간선(도착 정점, 비용) 저장용 클래스
   * - PriorityQueue에서 비용이 작은 순서대로 꺼내기 위해 Comparable 구현
   */
  static class Edge implements Comparable<Edge> {
    int ver;    // 도착 정점
    int cost;   // 비용

    Edge(int ver, int cost) {
      this.ver = ver;
      this.cost = cost;
    }

    /* 비용이 작은 게 우선 */
    @Override
    public int compareTo(Edge o) {
      return this.cost - o.cost;
    }

    @Override
    public String toString() {
      return "(" + "ver=" + ver + ", cost=" + cost + ')';
    }
  }
}
