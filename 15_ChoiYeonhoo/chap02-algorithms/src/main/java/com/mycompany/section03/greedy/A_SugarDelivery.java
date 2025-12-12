package com.mycompany.section03.greedy;


/* 탐욕(Greedy) 알고리즘
* - 각 단계에서 현재 시점의 최적해를 선택하는 방식
* - 현재 선택이 전체 문제에서도 최적해가 되는 경우에만 사용 가능
* - 한 번 선택한 것은 번복하지 않는다 (백 트래킹 X)
* - 시간 복잡도가 낮아 효율적 (대부분 O(n))
* */

/* 설탕 배달 문제
* - N kg 의 설탕을 배달해야 한다.
* - 설탕 봉지는 3kg, 5kg 존재
* - 최대한 적은 봉지를 들고 가려고 함
* - 정확하게 N kgㅇ을 만들 수 없다면 -1을 반환
*
* 예) N = 18, 결과 = 4 , (5kg : 3, 3kg : 1 )
*     N = 7, 결과 = -1
* */
public class A_SugarDelivery {

  public static int N; // 설탕 무게
  public static int baggage1 = 5;
  public static int baggage2 = 3;

  /**
   * 3kg, 5kg 봉지로 가져 갈 수 있는 최소 봉지 수
   * @param n 배달 해야하는 설탕의 무게
   * @return 최소 봉지 수 (불가능한 경우 -1)
   */
  public static int solution(int n){
    int count = 0; // 봉지 수를 세기 위한 변수

    if(n%5 == 0) count = n/5;
    else {
      while(n%5 != 0){
        n -= 3;
        count ++;
        if (n%5 == 0) {
          count += n/5;
          break;
        }
        if (n < 0) {
          count = -1;
          break;
        }
      }
    }

    return count;



  }
}
