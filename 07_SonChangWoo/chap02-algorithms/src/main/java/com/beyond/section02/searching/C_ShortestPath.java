package com.beyond.section02.searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/* BFS를 이용한 최단 경로 찾기 (미로 탐색 문제)
 * BFS는 먼저 방문한 노드를 기준으로 가장 가까운 노드부터 탐색한다.
 * 즉, 시작점에서 출발하여 거리 1짜리 노드를 모두 방문한 후,
 * 거리 2짜리 노드를 방문하는 방식으로 진행 된다.
 * 따라서 어떤 노드에 도달했을 때, 처음 도달한 순간이 곧 최단 거리가 된다.
 *
 * DFS는 한 방향으로 깊게 들어간 후, 막다른 길에 도달하면 다시 되돌아오므로
 * 최단 경로가 아닌 먼 길을 돌아 도착하는 경우도 생길 수 있다.
 *
 * === BFS로 최단 경로를 찾는 이유 ===
 * 1. 레벨별 탐색: 시작점에서 거리가 같은 노드들을 동시에 탐색
 * 2. 최초 도달 = 최단 거리: 처음 도달한 경로가 가장 짧은 경로
 * 3. 가중치 없는 그래프: 모든 간선의 가중치가 1일 때 최적
 *
 * === 미로 탐색 예시 ===
 * 입력 미로 (N=4, M=6):
 * 1 0 1 1 1 1
 * 1 0 1 0 1 0
 * 1 0 1 0 1 1
 * 1 1 1 0 1 1
 *
 * (0,0)에서 시작하여 (3,5)까지 최단 경로 찾기
 * 1: 이동 가능한 칸
 * 0: 벽 (이동 불가)
 *
 * BFS 탐색 과정 (거리 표시):
 * 단계 1 (거리 1):
 *   1 . . . . .
 *   2 . . . . .
 *   . . . . . .
 *   . . . . . .
 *
 * 단계 2 (거리 2):
 *   1 . . . . .
 *   2 . . . . .
 *   3 . . . . .
 *   . . . . . .
 *
 * 단계 3 (거리 3):
 *   1 . . . . .
 *   2 . . . . .
 *   3 . . . . .
 *   4 . . . . .
 *
 * 최종 결과 (각 칸까지의 최단 거리):
 *   1  0  3  4  5  6
 *   2  0  4  0  6  0
 *   3  0  5  0  7  8
 *   4  5  6  0  8  9
 *
 * 도착지 (3,5)까지 최단 거리: 9칸
 *
 * === DFS vs BFS 최단 경로 비교 ===
 * DFS:
 * - 한 방향으로 끝까지 탐색 → 백트래킹 → 다른 경로 탐색
 * - 먼저 도달한 경로가 최단 경로가 아닐 수 있음
 * - 모든 경로를 비교해야 최단 경로 확정 가능
 * - 시간 복잡도: O(V+E) ~ O(V^2) (최악의 경우)
 *
 * BFS:
 * - 레벨별로 가까운 노드부터 탐색
 * - 처음 도달한 순간이 곧 최단 경로
 * - 추가 비교 없이 바로 최단 경로 확정
 * - 시간 복잡도: O(V+E) (항상 효율적)
 * */
public class C_ShortestPath {

  static int N, M;              // 미로의 크기 (N: 세로, M: 가로)
  static int[][] map;           // 미로 정보 (1: 이동 가능, 0: 벽)
  static boolean[][] visit;     // 방문 여부 배열

  // 상하좌우 이동을 위한 방향 배열
  // dirX[0], dirY[0] : 상 (x+0, y-1)
  // dirX[1], dirY[1] : 하 (x+0, y+1)
  // dirX[2], dirY[2] : 좌 (x-1, y+0)
  // dirX[3], dirY[3] : 우 (x+1, y+0)
  static int[] dirX = {0, 0, -1, 1};
  static int[] dirY = {-1, 1, 0, 0};

  /**
   * 좌표를 저장하는 노드 클래스
   * Queue에 저장할 위치 정보
   */
  static class Node {
    int x;  // x 좌표 (행)
    int y;  // y 좌표 (열)

    public Node(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  static Queue<Node> q = new LinkedList<>();  // BFS용 Queue

  /**
   * BFS를 이용하여 미로의 최단 경로를 찾는 메서드
   * @param input 입력 데이터 (미로 크기, 미로 정보)
   * @return (0,0)에서 (N-1,M-1)까지의 최단 거리
   */
  public static int solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));
    StringTokenizer st = new StringTokenizer(br.readLine());

    // 첫 번째 줄: 미로의 크기 (N: 세로, M: 가로)
    N = Integer.parseInt(st.nextToken());  // 행(세로) 크기
    M = Integer.parseInt(st.nextToken());  // 열(가로) 크기

    // 미로 배열과 방문 배열 초기화
    map = new int[N][M];
    visit = new boolean[N][M];

    /* 미로 정보 입력 */
    for(int i = 0; i < N; i++) {
      // 한 줄을 문자 배열로 읽기 (예: "101010")
      char[] ch = br.readLine().toCharArray();
      for(int j = 0; j < ch.length; j++) {
        // 문자 '1'을 숫자 1로 변환하여 저장
        // '1': 이동 가능한 칸, '0': 벽
        map[i][j] = Character.getNumericValue(ch[j]);
      }
    }

    /* BFS 탐색 시작: (0, 0)에서 출발 */
    // BFS를 통해 각 칸까지의 최단 거리를 map 배열에 저장
    bfs(0, 0);

    /* 도착지 (N-1, M-1)까지의 최단 거리 반환 */
    // map[N-1][M-1]에는 시작점부터 도착점까지의 최단 거리가 저장되어 있음
    return map[N - 1][M -  1];
  }

  /**
   * BFS로 최단 경로를 찾는 메서드
   * 각 칸을 방문할 때마다 이전 칸의 거리 + 1을 저장
   * @param x 시작 x 좌표 (행)
   * @param y 시작 y 좌표 (열)
   */
  static void bfs(int x, int y) {
    /* 1단계: 시작 위치를 Queue에 추가하고 방문 처리 */
    q.add(new Node(x, y));
    visit[x][y] = true;

    /* 2단계: Queue가 빌 때까지 너비 우선 탐색 진행 (FIFO) */
    // Queue에 담긴 모든 노드를 처리할 때까지 반복
    while(!q.isEmpty()) {

      /* 현재 탐색을 진행할 노드를 Queue에서 꺼냄 */
      Node n = q.poll();

      /* 3단계: 현재 위치에서 상하좌우 4방향 탐색 */
      for(int i = 0; i < 4; i++) {
        // 다음 탐색할 위치 계산
        int cx = n.x + dirX[i];  // 새로운 x 좌표
        int cy = n.y + dirY[i];  // 새로운 y 좌표

        /* 4단계: 유효성 검사 */

        /* 검사 1: 미로 범위를 벗어나는지 확인 */
        // 범위를 벗어나면 다음 방향 탐색
        if(cx < 0 || cy < 0 || cx >= N || cy >= M) continue;

        /* 검사 2: 이미 방문했거나 벽인지 확인 */
        // visit[cx][cy]: 이미 방문한 곳
        // map[cx][cy] == 0: 벽 (이동 불가)
        if(visit[cx][cy] || map[cx][cy] == 0) continue;

        /* 5단계: 유효한 칸이면 Queue에 추가하고 거리 갱신 */
        q.offer(new Node(cx, cy));    // Queue에 추가
        visit[cx][cy] = true;         // 방문 처리

        /* 핵심: 현재 칸의 거리 = 이전 칸의 거리 + 1 */
        // map[n.x][n.y]: 현재 노드까지의 거리
        // map[cx][cy]: 다음 노드까지의 거리 = 현재 거리 + 1
        // 이렇게 하면 각 칸에 시작점으로부터의 최단 거리가 저장됨
        map[cx][cy] = map[n.x][n.y] + 1;

        /* 디버깅용 출력 */
        System.out.println("cx = " + cx + ", cy = " + cy);
        System.out.println("map[cx][cy] = " + map[cx][cy]);
      }
    }

    System.out.println("<< MAP >>");
    for(int i = 0; i < N; i++) {
      for (int j = 0; j < M; j++) {
        System.out.printf("%2d ", map[i][j]);
      }
      System.out.println();
    }
    // Queue가 비면 모든 도달 가능한 칸 탐색 완료
  }
}