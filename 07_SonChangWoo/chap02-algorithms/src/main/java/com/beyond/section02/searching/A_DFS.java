package com.beyond.section02.searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Stack;
import java.util.StringTokenizer;

/* 깊이 우선 탐색(Depth-First-Search, DFS)
* - 재귀 호출 또는 Stack(후입선출)을 이용해서 구현 가능
* - 시작 노드에서 출발하여 한 방향으로 갈 수 있는 만큼 깊이 탐색한 후
*   더 이상 진행할 수 없을 때 이전 분기점(노드)로 돌아가
*   다른 경로를 탐색하는 알고리즘이다.
* */
public class A_DFS {

  /* 그래프 시각화
  *   1
  *  /  \
  * 2 -  5    4 - 7
  * |    |
  * 3    6
  * */

  /* 웜 바이러스에 걸리게 되는 컴퓨터의 수 구하기 */
  static int node;        // 컴퓨터(노드)의 총 개수
  static int edge;        // 네트워크 연결(간선)의 총 개수
  static int[][] map;     // 그래프의 연결 정보를 저장할 인접 행렬
  static boolean[] visit; // 각 노드 방문 여부를 기록할 배열
  static int count;       // 감염된 컴퓨터의 수(1번 제외)

  public static Integer solution(String input) throws IOException {

    BufferedReader br = new BufferedReader(new StringReader(input));

    // 입력 첫 번째 줄 : 노드의 개수
    node = Integer.parseInt(br.readLine());

    // 입력 두 번째 줄 : 간선의 개수
    edge = Integer.parseInt(br.readLine());

    // 간선 연결 정보를 인접 행렬을 이용해서 표현
    // (0번 인덱스를 제외하고 1번부터 사용하기 위해서 node + 1 크기로 생성)
    map = new int[node+1][node+1];

    // 노드 방문 여부를 저장할 배열 생성
    // - 방문하면 true로 변환 ( visit[1] = true -> 1번 노드 방문한적 있음)
    // - 이미 방문한 노드를 다시 방문하지 않기 위하여 사용
    visit = new boolean[node+1]; // index 0 사용 X


    // 입력되는 간선 정보를 map(인접 행렬)에 기록
    StringTokenizer st;
    for (int i = 0; i < edge; i++) {
      st = new StringTokenizer(br.readLine()); // 공백 기준으로 토큰 나눔
      // "1 3" -> "1", "3"
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());

      // 무방향 그래프 == 양쪽 모두 1로 표기
      map[a][b] = map[b][a] = 1;

    }

    /* DFS 탐색 시작 */

    // 1. 재귀 호출 방법
    // dfsRecursive(1); // 1번부터 탐색 시작

    // 2. 스택 활용 방법
    dfsStack(1);

    return count; // 감염된 컴퓨터(노드) 수 반환
  }

  /**
   * 재귀 함수로 DFS 알고리즘을 구현한 메서드
   * 호출 스택을 이용하여 자동으로 백트래킹(역추적)
   * @param start 현재 방문하는 노드
   */
  private static void dfsRecursive(int start) {

    /* 1단계 : 해당 노드를 방문했으므로 방문 배열에 표기하기 */
    visit[start] = true;

    /* 2단계 : start 노드의 모든 이웃을 탐색하는 과정 */
    for (int i = 1; i <= node; i++) {

      /* start 노드와 연결되어 있고 (  map[start][i] == 1  )
      *  아직 방문한 적이 없는 이웃 노드 찾기 (  !visit[i]  )
      * */
      if (map[start][i] == 1 && !visit[i])
      {
        count++; // 바이러스를 전파할 이웃 컴퓨터를 찾았기 때문에 count 증가

        /* 3단계 : 찾은 이웃 노드를 방문해서
        *  그 노드와 연결된 이웃 또 찾는 과정을 반복(재귀 호출)
        *  메서드가 종료되면 호출한 곳(이전 노드)으로 돌아옴(== 백트래킹)
        * */
        dfsRecursive(i);
      }
    }

  }

  /**
   * Stack을 활용 DFS(반복문 방식)
   * -> 명시적으로 Stack 자료 구조 사용
   * @param start
   */
  private static void dfsStack(int start) {
    /* 1단계 : Stack 생성 및 시작 노드 추가 */
    Stack<Integer> stack = new Stack<>();
    stack.push(start);
    visit[start] = true;  // 시작 노드 방문 처리

    /* 2단계 : Stack이 빌 때까지 반복 */
    while(!stack.isEmpty()) {

      // 스택 제일 위에 있는 노드를 꺼냄(LIFO)
      int current = stack.pop();

      /* 3단계 : 현재 노드의 모든 이웃 탐색 */
      for (int i = 1; i <= node; i++) {

        // 현재 노드와 연결된 노드를 방문한 적 없을 경우
        if(map[current][i] == 1 && !visit[i]) {
          stack.push(i); // 스택에 추가(나중에 방문한 노드)
          visit[i] = true;
          count++;
        }
      }

    }


  }

}
