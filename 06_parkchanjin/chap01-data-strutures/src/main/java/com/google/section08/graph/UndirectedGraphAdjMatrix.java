package com.google.section08.graph;

/* 인접 행렬(Adjacency Matrix)을 기반으로 한
*  무방향 비가중치 그래프(Undirect Unweighted Graph) 구현
*
*  - 2차원 배열(행렬)을 사용하여 모든 정점 쌍의 연결 여부를 표현합니다.
*  - matrix[i][j] = true -> 정점 i와 j가 연결되어 있음을 의미
*  - 밀집 그래프(Dense Graph, 간선이 많은 그래프)에서 효율적입니다.
* */

import java.util.ArrayList;
import java.util.List;

  public class UndirectedGraphAdjMatrix {
    // 그래프의 총 정점(노드) 개수
    private int vertices;
    // 인접 행렬. 각 정점 쌍의 연결 여부를 boolean 값으로 저장
    private boolean[][] matrix;
    // 각 노드의 활성 상태. true이면 해당 노드가 존재 (동적 노드 추가/삭제 지원)
    private List<Boolean> active;

    /**
     * 지정된 수의 정점으로 그래프 초기화
     * @param vertices 초기 정점의 수
     */
    public UndirectedGraphAdjMatrix(int vertices) {
      this.vertices = vertices;
      matrix = new boolean[vertices][vertices]; // 정점 수에 맞는 2차원 배열 생성
      active = new ArrayList<>();
      for (int i = 0; i < vertices; i++) {
        active.add(true); // 모든 노드를 활성 상태로 초기화
      }
    }

    public static void main(String[] args) {
      // 1. 4개의 정점을 가진 초기 그래프 생성 (0-based index)
      UndirectedGraphAdjMatrix graphMatrix = new UndirectedGraphAdjMatrix(4);

      /*
       * 초기 그래프 구조:
       * 0 -- 1
       * | \  |
       * 2 -- 3
       */
      graphMatrix.addEdge(0, 1);
      graphMatrix.addEdge(0, 2);
      graphMatrix.addEdge(0, 3);
      graphMatrix.addEdge(1, 3);
      graphMatrix.addEdge(2, 3);

      System.out.println("초기 그래프:");
      graphMatrix.printGraph();
      System.out.println();

      // 2. 노드 추가 (addNode)
      int newNode = graphMatrix.addNode(); // 노드 4 추가
      System.out.println("새로운 노드 추가: " + newNode);
      // 노드 4를 1번, 3번 노드와 연결
      /*
       * 변경된 그래프 구조:
       * 0 -- 1 -- 4
       * | \  |  /
       * 2 -- 3 /
       */
      graphMatrix.addEdge(newNode, 1);
      graphMatrix.addEdge(newNode, 3);
      System.out.println("노드 4 추가 후 그래프:");
      graphMatrix.printGraph();
      System.out.println();


      // 3. 간선 제거 (removeEdge)

      // 노드 1과 4 사이의 간선 제거
      // 그래프 구조:
      // 0 - 1 -- 4
      // |   |  /
      // 2 - 3 /
      System.out.println("정점 0과 3 사이의 간선 제거:");
      graphMatrix.removeEdge(0, 3);
      graphMatrix.printGraph();
      System.out.println();

      // 4. 노드 제거 (removeNode)

      // 노드 2 제거
      // 그래프 구조:
      // 0 - 1 -- 4
      //     |  /
      // x   3 /
      System.out.println("정점 2 제거:");
      graphMatrix.removeNode(2); // 정점 2와 연결된 모든 간선이 제거됨
      graphMatrix.printGraph();
      System.out.println();
    }

    /**
     * 그래프에 새로운 노드 동적 추가
     * 기존 행렬보다 크기가 1 더 큰 새로운 행렬을 만들어 기존 데이터 복사
     * @return 새로 추가된 노드의 인덱스
     */
    public int addNode() {
      int newVertexIndex = vertices; // 새 노드의 인덱스는 현재 정점 수와 같습니다.
      vertices++; // 총 정점 수 증가

      // 1. 새로운 크기(vertices x vertices)의 행렬을 생성합니다.
      boolean[][] newMatrix = new boolean[vertices][vertices];

      // 2. 기존 행렬의 값을 새 행렬에 복사합니다.
      for (int i = 0; i < newVertexIndex; i++) {
        for (int j = 0; j < newVertexIndex; j++) {
          newMatrix[i][j] = matrix[i][j];
        }
      }
      matrix = newMatrix; // 내부 행렬의 참조를 새 행렬로 교체
      active.add(true);   // 새 노드를 활성 상태로 설정
      return newVertexIndex;
    }

    /**
     * 무방향 간선 추가. 노드 u와 v가 모두 활성 상태여야 함
     * 무방향이므로 행렬에서 양방향으로 연결 표시 (matrix[u][v] = true, matrix[v][u] = true)
     * @param u 연결할 정점 1
     * @param v 연결할 정점 2
     */
    public void addEdge(int u, int v) {
      if (u < vertices && v < vertices && active.get(u) && active.get(v)) {
        matrix[u][v] = true;
        matrix[v][u] = true;
      }
    }

    /**
     * 무방향 간선 제거
     * @param u 제거할 간선의 정점 1
     * @param v 제거할 간선의 정점 2
     */
    public void removeEdge(int u, int v) {
      if (u < vertices && v < vertices) {
        matrix[u][v] = false;
        matrix[v][u] = false;
      }
    }

    /**
     * 노드 x를 그래프에서 제거
     * 제거된 노드는 비활성화(active = false) 처리
     * 인접 행렬에서 해당 노드와 관련된 모든 간선 제거
     * @param x 제거할 노드의 인덱스
     */
    public void removeNode(int x) {
      if (x < vertices && active.get(x)) {
        // 1. 노드 x를 비활성 상태로 변경합니다.
        active.set(x, false);
        // 2. 노드 x와 관련된 모든 간선을 제거합니다.
        //    (해당 노드의 행과 열을 모두 false로 설정)
        for (int i = 0; i < vertices; i++) {
          matrix[x][i] = false; // x번 행 전체를 false로 (x에서 나가는 간선)
          matrix[i][x] = false; // x번 열 전체를 false로 (x로 들어오는 간선)
        }
      }
    }

    /**
     * 그래프의 현재 상태(인접 행렬) 출력
     * 각 활성 노드의 인접 행렬 값을 출력, 비활성 노드는 'x'로 표시
     */
    public void printGraph() {
      System.out.println("무방향 그래프 (인접 행렬):");
      System.out.print("  ");
      for (int i = 0; i < vertices; i++) {
        if (active.get(i)) {
          System.out.print(i + " ");
        } else {
          System.out.print("x ");
        }
      }
      System.out.println();

      for (int i = 0; i < vertices; i++) {
        if (active.get(i)) {
          System.out.print(i + " ");
        } else {
          System.out.print("x ");
        }

        for (int j = 0; j < vertices; j++) {
          // 행(i) 또는 열(j)이 비활성이면 x 출력
          if (!active.get(i) || !active.get(j)) {
            System.out.print("x ");
          } else {
            System.out.print((matrix[i][j] ? 1 : 0) + " ");
          }
        }
        System.out.println();
      }
    }
  }
