package com.beyond.section03.greedy;

/* 탐욕(greedy) 알고리즘
* - 각 단계에서 현재 시점의 최적해를 선택하는 방식
* - 현재 선택이 전체 문제에서도 최적해가 되는 경우에만 사용 가능
* - 한번 선택한 것은 번복하지 않음(백트래킹 X)
* - 시간 복잡도가 낮아 효율적 (대부분 O(n))
* */

/* 설탕 배달 문제
* - N kg의 설탕을 배달해야 한다.
* - 설탕 봉지는 3kg, 5kg 존재
* - 최대한 적은 봉지를 들고 가려고 한다.
* - 정확하게 N kg을 만들 수 없다면 -1 반환
*
* 예) N = 18, 결과 = 4
*     N = 7, 결과 = -1
* 3x + 5y = N
* x + y 는 최소값으로
* */

public class A_SugarDelivery {

  /**
   * 3kg, 5kg 봉지로 가져갈 수 있는 최소 봉지 수
   * @param n 배달해야 할 설탕의 무게
   * @return 최소 봉지 수(불가능할 경우 -1)
   */
  public static int solution(int n){
    int count = 0;
    boolean possible = false;
    for (int i = n/5; i >= 0; i--){ // 5*i의 값이 n이하인 경우 중 i가 큰값부터 반복문 순회
      for (int j = 0; j <= n/3; j++) {
        if (5*i+3*j == n){
          count = i + j;
          possible = true;
        }
      }
      if (possible){
        break;
      }
    }
    if (!possible){
      count = -1;
    }

    return count;
  }

}
