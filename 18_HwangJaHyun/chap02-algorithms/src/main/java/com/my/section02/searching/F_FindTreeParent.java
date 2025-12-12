package com.my.section02.searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

public class F_FindTreeParent {
  /*
  * 무방향 그래프로 주어진 트리의 간선 정보를 이용하여
  * 각 노드의 부모 노드 찾기
  * DFS, BFS를 이용하여 탐색
  * 인접 리스트(각 노드에 연결된 노드(==간선)정보 저장)
  * */

  static int N;                // 노드의 개수
  static List<Integer>[] list; // 인접 리스트   //List<Integer>의 배열
  static int[] parent;         // 각 노드의 부모 노드 번호를 저장할 배열
  static boolean[] visit;      // 방문 여부 기록 배열

  @SuppressWarnings("unchecked")
  public static String solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));

    // 입력 첫 번째 줄 == 노드 개수
    N = Integer.parseInt(br.readLine());

    // 배열 할당
    // index 번호 == 노드 번호
    parent = new int[N + 1];  // 1번부터 시작
    visit = new boolean[N + 1];
    list = new ArrayList[N + 1];

    // 각 노드의 인접 리스트 초기화
    for(int i = 1; i <= N; i++){
        list[i] = new ArrayList<>();
        // list[1] : 2,3 -> 1번 노드가 2,3 노드와 연결되어있음
        // list[2] : 1,4 -> 2번 노드가 1,4 노드와 연결되어있음
    }
    /* 간선 정보 입력 (N-1개의 간선) */
    // 트리는 N개의 노드 N-1개의 간선을 갖는다

    StringTokenizer st; // 공백기준으로 나눌때 성능이 좋음
    for(int i = 1; i < N; i++) {
      st = new StringTokenizer(br.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());

      // 인접 리스트에 추가
      // -> 무방향 그래프 == 양방향 연결
      list[a].add(b);
      list[b].add(a);
    }

    /* 트리 탐색 : 루트(1번 노드) 부터 시작하여 부모-자식 관계 파악*/
    //dfs(1);
    bfs(1);

    /* 결과 문자열 생성 */
    // 1번 노드는 부모가 없으므로 제외
    StringBuilder sb = new StringBuilder();
    for (int i = 2; i < parent.length; i++) {
      sb.append(parent[i]).append(" ");
    }
    return sb.toString();
  }

  /**
   * DFS(깊이 우선 탐색) 뻗어나간후 백트래킹, 반복
   * 메모리스택을 이용했음...!
   * @param parentNode 현재 방문한 노드(이 노드의 자식 노드를 찾기)
   */
  private static void dfs(int parentNode){

    /* 1단계 : 현재 노드 방문 처리 */
    visit[parentNode] = true;

    /* 2단계 : 현재 노드와 연결된 모든 노드 탐색 */
    // list[parentNode]
    // -> parentNode와 연결된 모든 노드 정보가 입력되어있음 ** 하단 참고
    for(int childNode : list[parentNode]){

      /* 3단계 : 아직 방문하지 않은 노드일때만 추가 탐색 수행
      *  == 이미 방문한 노드는 건너뜀
      * */
      if(visit[childNode]) continue;

      /* 4단계 : 부모-자식 관계를 저장 */
      parent[childNode] = parentNode;

      /* 5단계 : 자식 노드를 기준으로 하여 재귀 탐색 */
      dfs(childNode);
    }
  }

  /*
  * list[1] = 2,3,4
  * list[2] = 1,5
  * list[3] = 1,6,7
  * list[4] = 1
  * list[5] = 2
  * list[6] = 3
  * list[7] = 3
  *
  *           1
  *      /   |    \
  *     2    3     4
  *    /   /  \
  *   5   6    7
  *  -> 모든 정보를 알 수 있다.
  * 현재 for문은 list[1]의 2 방문여부 확인 false
  * dfs(2) 실행 -> list[2]의 1,5 순회 후
  * dfs(5) -> list[5] = 2 << 방문여부 true
  * 재귀끝
  * -> list[1]의 3 방문여부 확인....
  * */

  /**
   * BFS(너비 우선 탐색)를 이용하여 트리의 부모-자식 관계 파악
   * - 현재 노드를 기준으로 주변에 있는 노드 전부 탐색
   * - 루트부터 레벨 순서로 탐색하며 부모 - 자식 관계를 저장
   * @param root (시작 노드, 1번)
   */
  private static void bfs(int root){
    /* 1단계: Queue 초기화 및 루트 노드 추가 */
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(root);

    visit[root] = true;

    /* 2단계: Queue가 빌 때까지 반복 */
    while(!queue.isEmpty()){
     /* 현재 처리할 노드를 Queue에서 꺼내기 (FIFO) */
     int parentNode = queue.poll();

      /* 3단계: praentNode와 연결된 모든 노드를 탐색 */
      for(int childNode : list[parentNode]){

        /* 4단계: 방문하지 않은 노드만 처리
        * == 이미 방문한 노드는 넘어감
        * */
        if(visit[childNode]) continue;

        // if문 통과 == 방문한 적 없음
        // -> 방문 처리
        visit[childNode] = true;

        /* 5단계: 부모 자식 관계 저장 */
        parent[childNode] = parentNode;

        /* 6단계: 자식 노드를 Queue에 추가
        * -> 다음 레벨 탐색을 위해 추가
        * */
        queue.offer(childNode);
      }
      // list[1] = 2,3,4
      // 2 방문여부: false
      // 방문처리, 부모노드 저장 후 3으로

    }
  }
}
