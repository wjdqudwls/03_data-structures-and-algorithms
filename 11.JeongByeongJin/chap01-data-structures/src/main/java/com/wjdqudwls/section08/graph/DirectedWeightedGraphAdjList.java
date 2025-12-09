package com.wjdqudwls.section08.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 인접 리스트(Adjacency List)를 기반으로 한 유방향 가중치 그래프(Directed Weighted Graph)
 * - 각 노드(정점)에 연결된 인접 노드와 그 간선(edge)의 가중치를 리스트 형태로 관리
 * - 희소 그래프(Sparse Graph, 간선이 적은 그래프)에 효율적
 */
public class DirectedWeightedGraphAdjList {
  // 그래프의 총 정점(노드) 개수
  private int vertices;
  // 각 정점의 인접 간선 목록을 저장하는 리스트의 리스트
  // adjList.get(i)는 i번 정점에서 나가는 간선들의 리스트
  private List<List<Edge>> adjList;
  // 각 노드의 활성 상태. true이면 해당 노드가 존재 (동적 노드 추가/삭제 지원)
  private List<Boolean> active;

  /**
   * 간선(Edge) 정보를 저장하는 정적 내부 클래스
   * 목적지 정점과 가중치 보유
   */
  public static class Edge {
    int target; // 간선이 가리키는 목적지 정점
    int weight; // 간선의 가중치

    public Edge(int target, int weight) {
      this.target = target;
      this.weight = weight;
    }

    @Override
    public String toString() {
      return "(" + target + ", w:" + weight + ")";
    }
  }

  /**
   * 지정된 수의 정점으로 그래프 초기화
   * @param vertices 초기 정점의 수
   */
  public DirectedWeightedGraphAdjList(int vertices) {
    this.vertices = vertices;
    adjList = new ArrayList<>();
    active = new ArrayList<>();
    for (int i = 0; i < vertices; i++) {
      adjList.add(new ArrayList<>()); // 각 정점에 대한 빈 인접 리스트 생성
      active.add(true);               // 모든 노드를 활성 상태로 초기화
    }
  }

  /**
   * 그래프에 새로운 노드 동적 추가
   * @return 새로 추가된 노드의 인덱스
   */
  public int addNode() {
    adjList.add(new ArrayList<>()); // 새 노드를 위한 인접 리스트 추가
    active.add(true);               // 새 노드를 활성 상태로 설정
    vertices++;                       // 총 정점 수 증가
    return vertices - 1;              // 새 노드의 인덱스 반환
  }

  /**
   * 유방향 간선 추가: u에서 v로 가는 간선에 가중치 추가
   * u와 v가 모두 활성 상태여야 함
   * @param u 시작 정점
   * @param v 도착 정점
   * @param weight 가중치
   */
  public void addEdge(int u, int v, int weight) {
    if (u < vertices && v < vertices && active.get(u) && active.get(v)) {
      // u의 인접 리스트에 (v, weight) 정보를 담은 Edge 객체 추가
      adjList.get(u).add(new Edge(v, weight));
    }
  }

  /**
   * 유방향 간선 제거: u에서 v로 가는 간선 제거
   * @param u 시작 정점
   * @param v 도착 정점
   */
  public void removeEdge(int u, int v) {
    if (u < vertices && v < vertices) {
      // u의 인접 리스트에서 목적지가 v인 간선 제거
      // removeIf() : 컬렉션 내 요소 중 조건에 맞는 요소를 모두 제거한다
      adjList.get(u).removeIf(edge -> edge.target == v);
    }
  }

  /**
   * 노드 x를 그래프에서 제거
   * 제거된 노드는 비활성화(active=false) 처리
   * 다른 노드의 인접 리스트에서 해당 노드와 연결된 모든 간선 제거
   * @param x 제거할 노드의 인덱스
   */
  public void removeNode(int x) {
    if (x < vertices && active.get(x)) {
      // 1. 노드 x를 비활성 상태로 변경
      active.set(x, false);

      // 2. 다른 모든 노드의 인접 리스트를 순회하며 x로 향하는 간선 제거
      for (int i = 0; i < vertices; i++) {
        if (active.get(i)) {
          adjList.get(i).removeIf(edge -> edge.target == x);
        }
      }
      // 3. 노드 x에서 나가는 모든 간선 제거 (자신의 인접 리스트를 비움)
      adjList.get(x).clear();
    }
  }

  /**
   * 그래프의 현재 상태 출력
   * 각 활성 노드의 인접 간선 목록 출력, 비활성 노드는 'x'로 표시
   */
  public void printGraph() {
    System.out.println("유방향 가중치 그래프 (인접 리스트):");
    for (int i = 0; i < vertices; i++) {
      if (active.get(i)) {
        System.out.print(i + ": ");
        for (Edge edge : adjList.get(i)) {
          System.out.print(edge + " ");
        }
        System.out.println();
      } else {
        System.out.println(i + ": x (비활성)");
      }
    }
  }

  public static void main(String[] args) {
    // 1. 4개의 정점을 가진 초기 그래프 생성
    DirectedWeightedGraphAdjList graph = new DirectedWeightedGraphAdjList(4);

    /*
     * 초기 그래프 구조 (0-based index):
     * 0 -> 1 (w:5), 0 -> 2 (w:3), 0 -> 3 (w:1)
     * 1 -> 3 (w:2)
     * 2 -> 3 (w:2)
     */
    graph.addEdge(0, 1, 5);
    graph.addEdge(0, 2, 3);
    graph.addEdge(0, 3, 1);
    graph.addEdge(1, 3, 2);
    graph.addEdge(2, 3, 2);
    System.out.println("초기 그래프:");
    graph.printGraph();
    System.out.println();

    // 2. 노드 추가 (addNode)
    int newNode = graph.addNode(); // 노드 4 추가
    System.out.println("새로운 노드 추가: " + newNode);
    graph.addEdge(1, newNode, 4);   // 1 -> 4 (w:4)
    graph.addEdge(3, newNode, 4);   // 3 -> 4 (w:4)
    System.out.println("노드 4 추가 후 그래프:");
    graph.printGraph();
    System.out.println();

    // 3. 간선 제거 (removeEdge)
    System.out.println("정점 0에서 정점 3으로 가는 간선 제거:");
    graph.removeEdge(0, 3);
    graph.printGraph();
    System.out.println();

    // 4. 노드 제거 (removeNode)
    System.out.println("정점 2 제거:");
    graph.removeNode(2); // 정점 2와 관련된 모든 간선이 제거됨
    graph.printGraph();
    System.out.println();
  }
}
