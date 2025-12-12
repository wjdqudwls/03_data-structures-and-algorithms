package org.ho.section02.searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/* 너비 우선 탐색 (Breadth-First Search, BFS)
 * - Queue(FIFO, 선입선출)를 활용
 * - 시작 노드에서 출발해서
 *   시작 노드 기준으로 가까운 노드를 먼저 방문하면서 탐색하는 알고리즘이다.
 * - 최단 경로를 찾을 때 적합 */
public class B_BFS {

  // 배추밭의 크기를 저장할 변수
  static int M; // 가로 길이
  static int N; // 세로 길이
  static int K; // 배추(노드)의 개수

  // 배추밭을 나타낼 인접행렬(2차원 배열)
  static int[][] map;

  // 각 위치의 방문 여부를 저장할 배열
  static boolean[][] visit;

  // 필요한 지렁이 마릿수
  static int count;

  // 상하좌우 이동을 위한 값을 배열로 초기화
  static int[] dx = {1, 0, -1, 0};
  static int[] dy = {0, 1, 0, -1};

  /**
   * 노드의 x,y 좌표를 저장하는 클래스
   * -> Queue에 담겨질 객체
   */
  static class Node {
    int x;  // x 좌표(가로)
    int y;  // y 좌표(세로)

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
   * @return
   * @throws IOException
   */
  public static int solution(String input) throws IOException {

    BufferedReader br = new BufferedReader(new StringReader(input));

    // 첫 번째 줄(가로 세로 개수)을 읽어와 토큰화
    StringTokenizer st = new StringTokenizer(br.readLine());

    M = Integer.parseInt(st.nextToken());   //가로
    N = Integer.parseInt(st.nextToken());   //세로
    K = Integer.parseInt(st.nextToken());   //개수

    // 배추밭 map, visit 초기화
    map = new int[M][N];
    visit = new boolean[M][N];

    // 배추밭에 배추 심기
    for (int i = 0; i < K; i++) {
      st = new StringTokenizer(br.readLine());
      int x = Integer.parseInt(st.nextToken()); // x좌표
      int y = Integer.parseInt(st.nextToken()); // y좌표
      map[x][y] = 1; // 1은 배추가 심어진 곳
    }

    // 배추밭 출력하기
    for (int i = 0; i < M; i++) {//x좌표
      for (int j = 0; j < N; j++) {//y좌표
        System.out.print(map[i][j] + " ");
      }
      System.out.println();
    }

    // test시에 여러 테스트에서 static 변수를 사용하면 값이 쌓이므로, count를 초기화해줘야됨.
    count = 0;

    /* 배추밭 전체를 탐색하여 연결된 배추 묶음 찾기 */
    for (int i = 0; i < M; i++) {
      for (int j = 0; j < N; j++) {

        /* 방문하지 않은 위치에 배추가 있다면
         * == 새로운 배추 묶음을 발견
         * */
        if (map[i][j] == 1 && !visit[i][j]) { // if문이 시작 = 새로운 배추묶음을 찾음
          count++;  //지렁이 한마리 추가
          /* BFS 알고리즘을 통해서
           * 현재 배추와 상,하,좌,우로 연결된 모든 배추를 찾아서
           * 방문 처리 (visit[x][y] = true)*/
          bfs(i, j);  // => 묶음에 대해서 visit처리
        }
      }
    }


    return count;
  }

  private static void bfs(int x, int y) {
    q.offer(new Node(x, y));
    visit[x][y] = true;
    while (!q.isEmpty()) {
      Node now = q.poll();
      for (int i = 0; i < 4; i++) {
        int nx = now.x + dx[i];
        int ny = now.y + dy[i];
        if (nx >= 0 && M > nx && ny >= 0 && N > ny
            && !visit[nx][ny]
            && map[nx][ny] == 1) {
          q.offer(new Node(nx, ny));
          visit[nx][ny] = true;
        }
      }
    }
  }
}
