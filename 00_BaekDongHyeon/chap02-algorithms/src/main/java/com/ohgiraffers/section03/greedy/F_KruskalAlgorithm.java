package com.ohgiraffers.section03.greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*
 * 최소 신장 트리(Minimum Spanning Tree, MST)
 * - 신장 트리(Spanning Tree): 그래프의 모든 정점을 포함하는 트리
 * - 최소 신장 트리: 신장 트리 중 간선 가중치의 합이 최소인 트리
 *
 * 조건:
 * 1. 모든 정점을 포함해야 함
 * 2. 사이클이 없어야 함 (트리의 조건)
 * 3. 간선의 가중치 합이 최소여야 함
 * 4. N개의 정점 → N-1개의 간선 사용
 *
 *
 * 크루스칼 알고리즘 (Kruskal's Algorithm - Greedy Algorithm)
 * 최소 신장 트리(Minimum Spanning Tree, MST)를 구하는 대표적인 알고리즘
 * 주어진 그래프의 모든 정점을 연결하는 부분 그래프 중 가중치의 합이 최소인 트리
 *
 * - 간선을 가중치 오름차순으로 정렬
 * - 가중치가 작은 간선부터 선택 (Greedy!)
 * - 사이클을 형성하지 않으면 간선 추가
 * - Union-Find로 사이클 검출
 *
 * === 시간 복잡도 ===
 * 1. 간선 정렬: O(E log E)
 * 2. Union-Find 연산: O(E × α(V))
 *    - α(V): 아커만 함수의 역함수 (거의 상수)
 * 3. 전체: O(E log E)
 *    - 정렬이 병목 구간
 *
 * === 공간 복잡도 ===
 * O(V + E) - parent 배열 + 간선 배열
 *
 *
 * === 주의사항 ===
 * 1. 간선 정렬이 필수 (가중치 오름차순)
 * 2. Union-Find의 경로 압축 필수 (성능 최적화)
 * 3. 사이클 검출을 빠뜨리면 안 됨
 * 4. 최종적으로 N-1개의 간선이 선택되어야 함
 * 5. 그래프가 연결되어 있지 않으면 MST 불가능
 * */
public class F_KruskalAlgorithm {
    static int[] parent;  // Union-Find를 위한 parent 배열

    /**
     * 간선 정보를 저장하는 클래스
     * Comparable을 구현하여 가중치 기준으로 정렬 가능
     */
    static class Edge implements Comparable<Edge> {
        int u, v, weight;

        Edge(int u, int v, int weight) {
            this.u = u;              // 간선의 시작 정점
            this.v = v;              // 간선의 끝 정점
            this.weight = weight;    // 간선의 가중치
        }

        /**
         * 간선을 가중치 오름차순으로 정렬하기 위한 비교 메서드
         * @param o 비교할 다른 간선
         * @return 음수: this가 앞, 양수: o가 앞, 0: 동일
         */
        @Override
        public int compareTo(Edge o) {
            // 가중치 오름차순 정렬 (작은 가중치가 앞으로)
            return this.weight - o.weight;
        }
    }

    /**
     * Find 연산: 특정 원소가 속한 집합의 루트를 찾는 연산
     * 경로 압축(Path Compression)을 통해 최적화
     * @param x 찾고자 하는 원소
     * @return x가 속한 집합의 루트
     */
    static int find(int x) {
        /* 집합의 대표 원소(루트 노드)를 찾는다 */
        // 대표 원소를 알면 두 원소가 같은 집합에 속하는지 확인 가능

        /* 경로 압축 (Path Compression) */
        if(parent[x] != x) {
            // 재귀적으로 루트를 찾아서 parent[x]에 직접 연결
            parent[x] = find(parent[x]);
        }

        return parent[x];  // 루트 반환
    }

    /**
     * Union 연산: 두 개의 집합을 하나로 합치는 연산
     * 두 집합의 대표 원소를 비교하여 연결
     * @param x 첫 번째 원소
     * @param y 두 번째 원소
     */
    static void union(int x, int y) {
        /* 각 원소의 루트 찾기 */
        int rootX = find(x);  // x의 루트
        int rootY = find(y);  // y의 루트

        /* 두 루트가 다르면 합치기 */
        // rootX == rootY: 이미 같은 집합 (사이클 발생)
        // rootX != rootY: 다른 집합 → 합치기
        if(rootX != rootY) {
            // 앞쪽 원소(rootX)를 전체 루트로 설정
            parent[rootY] = rootX;
        }
    }

    /**
     * 크루스칼 알고리즘을 사용하여 최소 신장 트리(MST)의 가중치 합을 계산
     * @param input 입력 데이터 (정점 개수 V, 간선 개수 E, 각 간선 정보)
     * @return 최소 신장 트리의 가중치 합
     */
    public static Long solution(String input) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(input));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());  // 정점의 개수
        int E = Integer.parseInt(st.nextToken());  // 간선의 개수

        /* 간선 정보 입력 */
        Edge[] edges = new Edge[E];
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());      // 시작 정점
            int v = Integer.parseInt(st.nextToken());      // 끝 정점
            int weight = Integer.parseInt(st.nextToken()); // 가중치

            edges[i] = new Edge(u, v, weight);
        }

        /* Union-Find를 위한 parent 배열 초기화 */
        parent = new int[V + 1];  // 0번 인덱스는 사용 안 함
        for(int i = 1; i <= V; i++) {
            parent[i] = i;  // 초기: 각 정점이 독립된 집합
        }

        /* [크루스칼 1단계] 간선을 가중치 오름차순으로 정렬 */
        // Edge 클래스의 compareTo() 메서드에 의해 자동 정렬
        // 가중치가 작은 간선부터 선택하기 위함 (Greedy!)
        Arrays.sort(edges);

        long totalWeight = 0L;  // 최소 신장 트리의 총 가중치

        /* [크루스칼 2단계] 가중치가 작은 간선부터 선택 */
        for(Edge edge : edges) {
            /* 사이클 검출: 두 정점이 이미 연결되어 있는지 확인 */
            // find(edge.u) != find(edge.v): 다른 집합 → 연결 안 됨
            // find(edge.u) == find(edge.v): 같은 집합 → 이미 연결됨 (사이클!)

            if(find(edge.u) != find(edge.v)) {
                /* 사이클이 없으면 간선 선택 */

                /* 1. 두 정점을 하나의 집합으로 합치기 (연결) */
                union(edge.u, edge.v);

                /* 2. 선택한 간선의 가중치를 총합에 추가 */
                totalWeight += edge.weight;

                /* 디버깅용 출력 (선택 사항) */
                System.out.println("선택한 간선: " + edge.u + "-" + edge.v + " (가중치: " + edge.weight + ")");

                /* 최적화: N-1개의 간선을 선택하면 조기 종료 가능 */
                // if(선택된 간선 개수 == V - 1) break;
            }
            // 사이클이 발생하면 이 간선은 건너뛰기 (선택 안 함)
        }

        /* 최소 신장 트리의 가중치 합 반환 */
        return totalWeight;

        /* 예시:
        * 입력: V=4, E=5
        * 간선: (1-2,1), (2-3,2), (1-3,3), (3-4,4), (2-4,5)
        *
        * 정렬 후: (1-2,1), (2-3,2), (1-3,3), (3-4,4), (2-4,5)
        *
        * 처리:
        * 1. (1-2,1): find(1)≠find(2) → 선택, weight=1
        * 2. (2-3,2): find(2)≠find(3) → 선택, weight=3
        * 3. (1-3,3): find(1)==find(3) → 사이클, 건너뜀
        * 4. (3-4,4): find(3)≠find(4) → 선택, weight=7
        * 5. (2-4,5): find(2)==find(4) → 사이클, 건너뜀
        *
        * 결과: 7
        */
    }
}
