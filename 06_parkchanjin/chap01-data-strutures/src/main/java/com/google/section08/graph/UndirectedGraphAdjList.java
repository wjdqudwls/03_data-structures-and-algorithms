package com.google.section08.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 인접 리스트(Adjacency List)를 기반으로 한 무방향 비가중치 그래프(Undirected Unweighted Graph)
 * - 각 노드(정점)에 연결된 인접 노드들의 목록을 리스트 형태로 관리
 * - 희소 그래프(Sparse Graph, 간선이 적은 그래프)에 효율적
 */
public class UndirectedGraphAdjList {
  // 그래프의 총 정점(노드) 개수
  private int vertices;
  // 각 정점의 인접 노드 목록을 저장하는 리스트의 리스트
  // adjList.get(i)는 i번 정점과 연결된 노드들의 리스트
  private List<List<Integer>> adjList;
  // 각 노드의 활성 상태. true이면 해당 노드가 존재 (동적 노드 추가/삭제 지원)
  private List<Boolean> active;

  /**
   * 지정된 수의 정점으로 그래프 초기화
   * @param vertices 초기 정점의 수
   */
  public UndirectedGraphAdjList(int vertices) {
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
   * 무방향 간선 추가. 노드 u와 v가 모두 활성 상태여야 함
   * 무방향이므로 양쪽 모두에 간선 정보 추가 (u -> v, v -> u)
   * @param u 연결할 정점 1
   * @param v 연결할 정점 2
   */
  public void addEdge(int u, int v) {
    if (u < vertices && v < vertices && active.get(u) && active.get(v)) {
      // u의 인접 리스트에 v를 추가
      adjList.get(u).add(v);
      // v의 인접 리스트에 u를 추가
      adjList.get(v).add(u);
    }
  }

  /**
   * 무방향 간선 제거
   * 양쪽 모두에서 간선 정보 제거
   * @param u 제거할 간선의 정점 1
   * @param v 제거할 간선의 정점 2
   */
  public void removeEdge(int u, int v) {
    if (u < vertices && v < vertices) {
      // u의 인접 리스트에서 v를 제거
      // remove() 메소드는 인덱스로 삭제하는 것과 객체로 삭제하는 것을 구분하기 위해 Integer 객체로 캐스팅하여 전달
      if (active.get(u)) {
        adjList.get(u).remove((Integer) v);
      }
      // v의 인접 리스트에서 u를 제거
      if (active.get(v)) {
        adjList.get(v).remove((Integer) u);
      }
    }
  }

  /**
   * 노드 x를 그래프에서 제거
   * 제거된 노드는 비활성화(active=false) 처리, 다른 노드의 인접 목록에서도 삭제
   * @param x 제거할 노드의 인덱스
   */
  public void removeNode(int x) {
    if (x < vertices && active.get(x)) {
      // 1. 노드 x를 비활성 상태로 변경
      active.set(x, false);
      // 2. 다른 모든 활성 노드의 인접 리스트를 순회하며 x를 제거
      for (int i = 0; i < vertices; i++) {
        if (active.get(i)) {
          adjList.get(i).remove((Integer) x);
        }
      }
      // 3. 노드 x 자신의 인접 리스트를 비움
      adjList.get(x).clear();
    }
  }

  /**
   * 그래프의 현재 상태 출력
   * 각 활성 노드의 인접 목록 출력
   */
  public void printGraph() {
    System.out.println("무방향 그래프 (인접 리스트):");
    for (int i = 0; i < vertices; i++) {
      if (active.get(i)) {
        System.out.print(i + ": ");
        for (int neighbor : adjList.get(i)) {
          System.out.print(neighbor + " ");
        }
        System.out.println();
      } else {
        System.out.println(i + ": x (비활성)");
      }
    }
  }

  public static void main(String[] args) {
    // 1. 4개의 정점을 가진 초기 그래프 생성 (0-based index)
    UndirectedGraphAdjList graphList = new UndirectedGraphAdjList(4);

    /*
     * 초기 그래프 구조:
     * 0 -- 1
     * | \  |
     * 2 -- 3
     */
    graphList.addEdge(0, 1);
    graphList.addEdge(0, 2);
    graphList.addEdge(0, 3);
    graphList.addEdge(1, 3);
    graphList.addEdge(2, 3);

    System.out.println("초기 그래프:");
    graphList.printGraph();
    System.out.println();

    // 2. 노드 추가 (addNode)
    int newNode = graphList.addNode(); // 노드 4 추가
    System.out.println("새로운 노드 추가: " + newNode);
    // 노드 4를 1번, 3번 노드와 연결
    /*
     * 변경된 그래프 구조:
     * 0 -- 1 -- 4
     * | \  |  /
     * 2 -- 3 /
     */
    graphList.addEdge(newNode, 1);
    graphList.addEdge(newNode, 3);
    System.out.println("노드 4 추가 후 그래프:");
    graphList.printGraph();
    System.out.println();

    // 3. 간선 제거 (removeEdge)
    System.out.println("정점 0과 3 사이의 간선 제거:");
    graphList.removeEdge(0, 3);
    graphList.printGraph();
    System.out.println();

    // 4. 노드 제거 (removeNode)
    System.out.println("정점 2 제거:");
    graphList.removeNode(2); // 정점 2와 연결된 모든 간선이 제거됨 (0-2, 2-3)
    graphList.printGraph();
    System.out.println();
  }
}
