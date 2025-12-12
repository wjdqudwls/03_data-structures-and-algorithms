package com.my.section02.searching;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/* 너비 우선 탐색 (Breadth-First Search, BFS)
 * - Queue(FIFO, 선입선출)을 활용
 * - 시작 노드에서 출발해서
 * 시작 노드 기준으로 가까운 노드를 먼저 방문하면서 탐색하는 알고리즘
 * - 최단 경로를 찾을 때 적합
 * */

public class B_BFS {

  // 배추밭의 크기를 저장할 변수
  static int M; // 가로 길이
  static int N; // 세로 길이
  static int K; // 배추(노드)의 개수

  // 배추 밭을 나타낼 인접 행렬(2차원배열)
  static int[][] map;

  // 각 위치의 방문 여부를 저장할 배열
  static boolean[][] visit;

  // 필요한 배추흰지렁이 마리 수
  static int count;

  static int[] dirX = {0, 0, -1, 1};
  static int[] dirY = {-1 , 1, 0, 0};

  /*
  * 노드의 x, y 좌표를 저장하는 클래스
  * -> Queue에 담겨질 객체
  * */
  static class Node{
    int x; // x좌표(가로)
    int y; // Y좌표(세로)

    public Node(int x, int y ){
      this.x = x;
      this.y = y;
    }
  }

  // 현재 탐색 중인 좌표를 저장할 변수
  // 상하좌우 이동한 좌표가 저장됨
  static int cx, cy;

  // BFS 방식으로 해결하기 위한 Queue 생성
  static Queue<Node> q = new LinkedList<>();

  /**
   * 배추밭에 필요한 지렁이의 수를 구하는 메서드
   * @param input 배추밭의 가로 크기, 세로 크기, 배추 개수
   * @return 지렁이 마릿수
   * @throws IOException
   */
  public static Integer solution(String input) throws IOException {
    BufferedReader br = new BufferedReader(new StringReader(input));

    // 첫 번째 줄(가로 세로 개수)을 읽어와 토큰화
    StringTokenizer st = new StringTokenizer(br.readLine());

    M = Integer.parseInt(st.nextToken()); // 가로
    N = Integer.parseInt(st.nextToken()); // 세로
    K = Integer.parseInt(st.nextToken()); // 배추개수

    // 배추밭 map, visit 초기화
    map = new int[M][N];
    visit = new boolean[M][N];

    // 배추밭에 배추 심기
    for(int i = 0; i < K; i++){
      st = new StringTokenizer(br.readLine());
      int x = Integer.parseInt(st.nextToken()); // x좌표
      int y = Integer.parseInt(st.nextToken()); // y좌표
      map[x][y] = 1; // 배추 심기
    }

    // 배추밭 출력하기
    System.out.println("[[배추밭]]");
    for(int i = 0; i < M; i++){
      for(int j = 0; j < N; j++){
        System.out.print(map[i][j] + " ");
      }
      System.out.println();
    }
    // count 0으로 초기화
    // static의 공유 특징 때문에 테스트마다 값이 누적되는 걸 방지
    count = 0;


    /* 배추밭 전체를 탐색하여 연결된 배추 묶음 찾기 */
    for(int i = 0; i < M; i++){
      for(int j = 0; j < N; j++){

        /* 방문하지 않은 위치에 배추가 있다면
        * == 새로운 배추 묶음을 발견, 지렁이 한마리 추가
        * */
        if(map[i][j] == 1 && !visit[i][j]){
          count++; // 지렁이 한 마리 추가

          /* BFS 알고리즘을 통해서
          * 현재 배추와 상, 하, 좌, 우로 연결된 모든 배추를 찾아서 방문처리
          * -> visit[x][y] == true */
          bfs(i, j); // 묶음 찾기
        }
      }
    }
    return count;
  }

  /**
   *
   * @param x x 시작 좌표
   * @param y y 시작 좌표
   */
  private static void bfs(int x, int y) {
    // 1단계 : 시작 위치를 방문 처리하고, Queue에 추가
    visit[x][y] = true;
    q.offer(new Node(x, y));

    // 2단계 : Queue가 빌 때까지 반복(FIFO)
    while(!q.isEmpty()){

      // Queue에서 상하좌우 탐색을 수행할 배추 위치를 꺼냄.
      Node node = q.poll();

      // 3단계 : 현재 위치 기준 상하좌우 좌표 탐색
      for (int i = 0; i < 4; i++) {
        // 다음에 탐색할 위치를 계산
        cx = node.x + dirX[i]; // 0 0 -1 1
        cy = node.y + dirY[i]; // -1 1 0 0

        // 4단계: 계산한 위치가 유효한지 검사
        // -> 배추밭 범위를 넘지 않는지 확인
        // -> 방문하지 않은 곳인지 확인
        // -> 배추가 있는지 확인
        boolean condition1 = cx >= 0 && cy >= 0 && cx < M && cy < N;

        if(condition1 && !visit[cx][cy] && map[cx][cy] == 1){
        // 유효한 경우
        q.offer(new Node(cx, cy)); // 큐에 추가 -> 탐색후보로 등록
        visit[cx][cy] = true; // 방문 처리
        }
      }
    }
  }
}
/* 문제
* 차세대 영농인 한나는 강원도 고랭지에서 유기농 배추를 재배하기로 하였다.
* 농약을 쓰지 않고 배추를 재배하려면 배추를 해충으로부터 보호하는 것이 중요하기 때문에,
* 한나는 해충 방지에 효과적인 배추흰지렁이를 구입하기로 결심한다.
* 이 지렁이는 배추근처에 서식하며 해충을 잡아 먹음으로써 배추를 보호한다.
* 특히, 어떤 배추에배추흰지렁이가 한 마리라도 살고 있으면 이 지렁이는 인접한 다른 배추로 이동할 수 있어,
* 그 배추들 역시 해충으로부터 보호받을 수 있다.
* 한 배추의 상하좌우 네 방향에 다른배추가 위치한 경우에 서로 인접해있는 것이다.
* 한나가 배추를 재배하는 땅은 고르지 못해서 배추를 군데군데 심어 놓았다.
* 배추들이 모여있는 곳에는 배추흰지렁이가 한 마리만 있으면 되므로 서로 인접해있는배추들이
* 몇 군데에 퍼져있는지 조사하면 총 몇 마리의 지렁이가 필요한지 알 수 있다.
* 예를 들어 배추밭이 아래와 같이 구성되어 있으면 최소 5마리의 배추흰지렁이가 필요하다.
* 0은배추가 심어져 있지 않은 땅이고, 1은 배추가 심어져 있는 땅을 나타낸다.
* -------------------------------> X축
* |  1  1  0  0  0  0  0  0  0  0
* |  0  1  0  0  0  0  0  0  0  0
* |  0  0  0  0  1  0  0  0  0  0
* |  0  0  0  0  1  0  0  0  0  0
* |  0  0  1  1  0  0  0  1  1  1
* |  0  0  0  0  1  0  0  1  1  1
* y축
* 입력
* 첫째 줄에는 배추를 심은 배추밭의 가로길이 M(1 ≤ M ≤ 50)과 세로길이 N(1 ≤ N ≤ 50),
* 그리고 배추가 심어져 있는 위치의 개수 K(1 ≤ K ≤ 2500)이 주어진다.
* 그 다음 K줄에는 배추의 위치 X(0 ≤ X ≤ M-1), Y(0 ≤ Y ≤ N-1)가 주어진다.
* 두 배추의 위치가 같은 경우는 없다.
*
* 출력
* 각 테스트 케이스에 대해 필요한 최소의 배추흰지렁이 마리 수를 출력한다
* */
