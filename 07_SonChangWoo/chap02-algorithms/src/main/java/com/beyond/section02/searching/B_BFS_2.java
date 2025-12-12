package com.beyond.section02.searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/* 너비 우선 탐색 (Breadth-First Search, BFS) - 좌표 처리 버전 2
 * 선입 선출(FIFO) 구조의 queue를 활용한다.
 * 시작 노드에서 출발해서 시작 노드를 기준으로 가까운 노드를 먼저 방문하면서 탐색하는 알고리즘이다.
 *
 * === B_BFS vs B_BFS_2 차이점 ===
 * B_BFS.java: map[x][y] 형태로 x를 행 인덱스로 사용
 * B_BFS_2.java: map[y][x] 형태로 일반적인 2차원 배열 표기법 사용 (이 파일)
 *
 * 일반적인 좌표계:
 * - x: 가로 방향 (열, column)
 * - y: 세로 방향 (행, row)
 *
 * 2차원 배열 표기법:
 * - map[행][열] = map[y][x]
 * - 이 방식이 더 직관적이고 일반적임
 *
 * === 좌표와 배열 인덱스 관계 시각화 ===
 * 입력 좌표: (x=2, y=1) → 배열 인덱스: map[1][2]
 *
 *     x→  0  1  2  3  4
 *   y↓  ┌──┬──┬──┬──┬──┐
 *   0   │  │  │  │  │  │
 *       ├──┼──┼──┼──┼──┤
 *   1   │  │  │★│  │  │  ← map[1][2]
 *       ├──┼──┼──┼──┼──┤
 *   2   │  │  │  │  │  │
 *       └──┴──┴──┴──┴──┘
 *
 * === 배추 문제 예시 (좌표 중심) ===
 * 입력: M=5 (가로), N=3 (세로)
 * 배추 위치: (0,0), (1,0), (1,1), (4,1), (3,2), (4,2)
 *
 *     x→  0  1  2  3  4
 *   y↓  ┌──┬──┬──┬──┬──┐
 *   0   │1 │1 │0 │0 │0 │
 *       ├──┼──┼──┼──┼──┤
 *   1   │0 │1 │0 │0 │1 │
 *       ├──┼──┼──┼──┼──┤
 *   2   │0 │0 │0 │1 │1 │
 *       └──┴──┴──┴──┴──┘
 *
 * 배열 저장: map[y][x]
 * - 좌표 (0,0) → map[0][0] = 1
 * - 좌표 (1,0) → map[0][1] = 1
 * - 좌표 (4,1) → map[1][4] = 1
 *
 * 결과: 3개의 연결된 배추 묶음
 * 묶음1: (0,0)-(1,0)-(1,1)
 * 묶음2: (4,1)
 * 묶음3: (3,2)-(4,2)
 * */
public class B_BFS_2 {

  /* 배추 밭에 필요한 배추 지렁이 갯수 구하는 문제
   * x, y 좌표를 활용한 2차원 배열 탐색 문제
   * 좌표와 배열 인덱스의 관계를 정확히 이해하는 것이 중요!
   * */

  // 배추밭의 크기 (M: 가로/열, N: 세로/행)와 배추 개수 (K)
  static int M, N, K;

  // 배추밭을 나타내는 2차원 배열
  // 중요: map[N][M] → map[세로][가로] → map[행][열] → map[y][x]
  static int[][] map;

  // 각 위치의 방문 여부를 저장하는 배열 (중복 탐색 방지)
  static boolean[][] visit;

  // 필요한 지렁이의 개수 (연결된 배추 묶음의 개수)
  static int count;

  // 상하좌우 이동을 위한 방향 배열
  // dirX[i], dirY[i]를 더하면 상하좌우로 이동
  // i=0: 상 (x+0, y-1)
  // i=1: 하 (x+0, y+1)
  // i=2: 좌 (x-1, y+0)
  // i=3: 우 (x+1, y+0)
  static int[] dirX = {0, 0, -1, 1};
  static int[] dirY = {-1, 1, 0, 0};

  /**
   * x, y 좌표를 저장하는 노드 클래스
   * Queue에 저장할 좌표 정보를 담는 객체
   */
  static class Node {
    int x;  // x 좌표 (가로, 열)
    int y;  // y 좌표 (세로, 행)

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  // 현재 탐색 중인 좌표 (상하좌우 이동 시 사용)
  static int cx, cy;

  // BFS로 문제를 해결하기 위한 Queue (선입선출)
  static Queue<Node> q = new LinkedList<>();

  /**
   * 배추밭에 필요한 지렁이 개수를 구하는 메서드
   * 좌표(x, y)와 배열 인덱스[y][x]의 관계를 주의해야 함
   * @param input 입력 데이터 (배추밭 크기, 배추 위치 정보)
   * @return 필요한 지렁이의 개수 (연결된 배추 묶음의 개수)
   */
  public static int solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st;

    // 첫 번째 줄: 배추밭의 가로 길이(M), 세로 길이(N), 배추 개수(K)
    st = new StringTokenizer(br.readLine());
    M = Integer.parseInt(st.nextToken());  // 가로 (열의 개수)
    N = Integer.parseInt(st.nextToken());  // 세로 (행의 개수)
    K = Integer.parseInt(st.nextToken());  // 배추 개수

    // 배열 생성: map[N][M] → map[세로][가로] → map[행][열] → map[y][x]
    map = new int[N][M];
    visit = new boolean[N][M];

    /* 1단계: 배추밭에 배추 심기 */
    for (int i = 0; i < K; i++) {
      st = new StringTokenizer(br.readLine());
      int x = Integer.parseInt(st.nextToken());  // x 좌표 (가로, 열)
      int y = Integer.parseInt(st.nextToken());  // y 좌표 (세로, 행)

      // 중요: 좌표 (x, y)를 배열에 저장할 때는 map[y][x] 순서!
      // x는 가로(열), y는 세로(행)이므로 배열 인덱스는 [행][열] = [y][x]
      map[y][x] = 1;
    }

    // 배추밭 출력 (디버깅용)
    System.out.println("배추밭");
    for (int i = 0; i < N; i++) {  // i는 행(y 좌표)
      for (int j = 0; j < M; j++) {  // j는 열(x 좌표)
        System.out.print(map[i][j] + " ");
      }
      System.out.println();
    }
    System.out.println();

    // 지렁이 개수 초기화
    count = 0;

    /* 2단계: 배추밭 전체를 탐색하여 연결된 배추 묶음 찾기 */
    for (int i = 0; i < N; i++) {  // i는 행 인덱스 (y 좌표)
      for (int j = 0; j < M; j++) {  // j는 열 인덱스 (x 좌표)

        /* 방문하지 않은 위치에 배추가 있다면 새로운 배추 묶음 발견 */
        if (map[i][j] == 1 && !visit[i][j]) {
          count++;  // 지렁이 1마리 필요 (새로운 배추 묶음)

          /* BFS를 통해 연결된 배추 묶음을 모두 방문 처리 */
          // 중요: bfs 메서드는 (x, y) 순서로 받으므로
          // 배열 인덱스 (i, j) = (행, 열) = (y, x)를 (j, i) = (x, y) 순으로 전달
          bfs(j, i);  // bfs(x좌표, y좌표) = bfs(열, 행)
        }
      }
    }

    // 필요한 지렁이의 총 개수 반환
    return count;
  }

  /**
   * BFS를 이용하여 연결된 배추 묶음을 탐색하는 메서드
   * 시작 위치부터 상하좌우로 연결된 모든 배추를 방문 처리한다.
   * @param x 시작 x 좌표 (가로, 열)
   * @param y 시작 y 좌표 (세로, 행)
   */
  static void bfs(int x, int y) {
    /* 1단계: 시작 위치를 방문 처리하고 Queue에 추가 */
    // 배열 접근: visit[y][x] (행, 열 순서)
    visit[y][x] = true;
    q.offer(new Node(x, y));  // Node는 (x, y) 좌표로 저장

    /* 2단계: Queue가 빌 때까지 반복 (FIFO: 선입선출) */
    // Queue에 있는 모든 배추 위치를 차례대로 꺼내서 처리
    while (!q.isEmpty()) {
      // Queue에서 현재 탐색할 배추 위치를 꺼냄
      Node node = q.poll();

      /* 3단계: 현재 위치에서 상하좌우 4방향 탐색 */
      for (int i = 0; i < 4; i++) {
        // 다음 탐색할 좌표 계산
        // node.x는 현재 x 좌표, dirX[i]는 x 방향 이동량
        // node.y는 현재 y 좌표, dirY[i]는 y 방향 이동량
        cx = node.x + dirX[i];  // 새로운 x 좌표
        cy = node.y + dirY[i];  // 새로운 y 좌표

        /* 4단계: 유효한 위치인지, 방문하지 않았는지, 배추가 있는지 확인 */
        // rangeCheck(): 배추밭 범위 내에 있는지 확인
        // !visit[cy][cx]: 아직 방문하지 않은 위치인지 확인 (배열 인덱스는 [y][x])
        // map[cy][cx] == 1: 배추가 심어져 있는지 확인 (배열 인덱스는 [y][x])
        if (rangeCheck() && !visit[cy][cx] && map[cy][cx] == 1) {
          // 조건을 만족하면 해당 위치를 Queue에 추가하고 방문 처리
          q.offer(new Node(cx, cy));  // Node는 (x, y) 좌표로 저장
          visit[cy][cx] = true;       // 배열 접근은 [y][x] 순서
        }
      }
    }
    // Queue가 비면 현재 배추 묶음의 모든 배추를 방문 완료
  }

  /**
   * 현재 좌표(cx, cy)가 배추밭 범위 내에 있는지 확인하는 메서드
   * @return 범위 내에 있으면 true, 벗어나면 false
   */
  static boolean rangeCheck() {
    // cx >= 0: x 좌표가 0 이상 (왼쪽 경계)
    // cy >= 0: y 좌표가 0 이상 (위쪽 경계)
    // cx < M: x 좌표가 M 미만 (오른쪽 경계, M은 가로 길이)
    // cy < N: y 좌표가 N 미만 (아래쪽 경계, N은 세로 길이)
    return cx >= 0 && cy >= 0 && cx < M && cy < N;
  }
}