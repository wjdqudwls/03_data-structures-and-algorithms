package com.beyond.section02.searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/* 너비 우선 탐색 (Breadth-First Search, BFS)
 * - Queue(FIFO, 선입선출) 를 활용
 * - 시작 노드에서 출발해서
 *   시작 노드 기준으로 가까운 노드를 먼저 방문하면서 탐색하는 알고리즘이다.
 * - 최단 경로를 찾을 때 적합
 * */

public class B_BFS {

  // 배추밭의 크기를 저장할 변수
  static int M; // 가로 길이
  static int N; // 세로 길이
  static int K; // 배추(노드)의 개수

  // 배추밭을 나타낼 인접 행렬(2차원 배열)
  static int[][] map;

  // 각 위치의 방문 여부를 저장할 배열
  static boolean[][] visit;

  static int count;

  // 현재 노드 기준으로 상,하,좌,우 이동을 위한 값을 배열로 초기화
  static int[] dirX = {0, 0, -1, 1};
  static int[] dirY = {-1, 1, 0, 0};

  /*
   * 노드의 x,y 좌표를 저장하는 클래스
   * -> Queue에 담겨질 객체
   * */
  static class Node {
    int x; // x 좌표(가로)
    int y; // y 좌표(세로)

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  // 현재 탐색 중인 좌표를 저장할 변수(상하좌우 이동한 좌표가 저장됨)
  static int cx, cy;

  // BFS 방식으로 해결하기 위한 Queue 생성
  static Queue<Node> q = new LinkedList<>();

  /**
   * 배추밭에 필요한 지렁이의 수를 구하는 메서드
   *
   * @param input 입력 데이터(배추밭 크기, 배추 개수, 배추 위치)
   * @return 필요한 지렁이의 수
   * @throws IOException
   */
  public static int solution(String input) throws IOException {

    BufferedReader br = new BufferedReader(new StringReader(input));

    // 첫 번째 줄 (가로 세로 개수)을 읽어와 토큰화
    StringTokenizer st = new StringTokenizer(br.readLine());

    M = Integer.parseInt(st.nextToken()); // 가로
    N = Integer.parseInt(st.nextToken()); // 세로
    K = Integer.parseInt(st.nextToken()); // 개수

    // 배추밭 map, visit 초기화
    map = new int[M][N];
    visit = new boolean[M][N];

    // 배추밭에 배추 심기
    for (int i = 0; i < K; i++) {
      st = new StringTokenizer(br.readLine());
      int x = Integer.parseInt(st.nextToken()); // x 좌표
      int y = Integer.parseInt(st.nextToken()); // y 좌표
      map[x][y] = 1; // 배추 심기
    }

    // 배추밭 출력하기
    System.out.println("[[배추밭]]");
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {
        System.out.print(map[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();

    // count 0으로 초기화 (static의 공유 특징 때문에 테스트 마다 값이 누적되는걸 방지)
    count = 0;

    /* 배추밭 전체를 탐색하여 연결된 배추 묶음 찾기 */
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {

        /* 방문하지 않은 위치에 배추가 있다면
        * == 새로운 배추 묶음을 발견
        *  */
        if (map[i][j] == 1 && !visit[i][j]) {
          count++; // 지렁이 한 마리 추가

          /* BFS 알고리즘을 통해서
          * 현재 배추와 상,하,좌,우로 연결된 모든 배추를 찾아서
          * 방문 처리 (visit[x][y] = true)
          * */
          bfs(i,j); // 묶음 찾기
        }

      }
    }
    return count;
  }

  /**
   * BFS(너비 우선 탐색) 알고리즘을 이용한 배추 묶음 탐색 메서드
   * @param x   x 시작 좌표
   * @param y   y 시작 좌표
   */
  private static void bfs(int x, int y) {

    // 1단계 : 시작 위치를 방문 처리하고, Queue에 추가
    visit[x][y] = true;
    q.offer(new Node(x, y));

    // 2단계 : Queue가 빌 때까지 반복(FIFO)
    while (!q.isEmpty()) {

      // Queue에서 상하좌우 탐색을 수행할 배추 위치를 꺼냄.
      Node node = q.poll();

      // 3단계 : 현재 위치 기준 4방향 탐색
      for (int i = 0; i < 4; i++) {

        // 다음 탐색할 위치를 계산
        cx = node.x + dirX[i];
        cy = node.y + dirY[i];

        // 4단계 : 계산한 위치가 유효한지 검사
        // - 배추밭 범위를 넘지 않았는지 확인
        // - 방문하지 않은 곳인지 확인'
        // - 배추가 있는지 확인
        boolean condition1 = cx >= 0 && cy >= 0 && cx < M && cy < N;


        if (condition1 && !visit[cx][cy] && map[cx][cy] == 1) { // 유효한 경우
          q.offer(new Node(cx, cy)); // 큐에 추가 -> 탐색 후보로 등록
          visit[cx][cy] = true; // 방문 처리
        }

      }
    }


  }

}
