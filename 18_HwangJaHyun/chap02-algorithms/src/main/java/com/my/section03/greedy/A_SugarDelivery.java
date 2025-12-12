package com.my.section03.greedy;

/* 탐욕(greedy) 알고리즘
* 각 단계에서 현재 시점의 최적해를 선택하는 방식
* 현재 선택이 전체 문제에서도 최적해가 되는 경우에만 사용 가능
* 한 번 선택한 것은 번복하지 않음(백트래킹 X)
* 시간 복잡도가 낮아 효율적이다.
* -> 대부분 O(n)
* */

/* 설탕 배달 문제
* -N킬로그램의 설탕을 배달해야한다
* - 설탕 봉지는 3kg, 5kg 존재
* - 최대한 적은 봉지를 들고 가려고 한다.
* - 정확하게 N킬로그램을 만들 수 없다면 -1 반환
* ex) N = 18 => 4 (3kg 1개, 5kg 3개)
*     N = 7 => -1
* */

public class A_SugarDelivery {

  /**
   * 3kg, 5kg 봉지로 가져갈 수 있는 최소 봉지 수
   * @param n 배달해야 할 설탕의 무게
   * @return 최소 봉지 수 (불가능한 경우 -1)
   */
  public static int solution1(int n) {
    int count = 0; // 봉지 수를 세기 위한 변수

    if(n % 5 == 3){
      count += n / 5;
      n = n - count * 5;
      count += n / 3;
    }else if(n % 3 == 0){
      count += n / 3;
    }else if(n % 5 == 0){
      count += n / 5;
    }else{
      count = -1;
    }
    return count;
  }
  // 나머지가 3이 아닌경우에 처리 불가
  // 14

  /**
   * 3kg, 5kg 봉지로 가져갈 수 있는 최소 봉지 수
   * @param n 배달해야 할 설탕의 무게
   * @return 최소 봉지 수 (불가능한 경우 -1)
   */
  public static int solution(int n) {
    int count = 0; // 봉지 수를 세기 위한 변수
    while(true){

      if(n % 5 == 0) return count += n / 5;
      else if(n < 0) return -1; // 불가능한 경우
      else if(n == 0) return count;

      n -= 3; // n = n - 3;
      count++;
    }
  }
}
