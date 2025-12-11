package com.kth.section03.greedy;

/* 탐욕(greedy) 알고리즘
* - 각 단계에서 현재 시점의 최적해를 선택하는 방식
* - 현재 선택이 전체 문제에서도 최적해가 되는 경우에만 사용 가능
* - 한 번 선택한 것은 번복하지 않음(백트래킹 X)
* - 시간 복잡도가 낮아 효율적 (대부분 O(n))
* */

/* 설탕 배달 문제
* - N킬로그램의 설탕을 배달해야 한다.
* - 설탕 봉지는 3kg, 5kg 존재
* - 최대한 적은 봉지를 들고 가려고 한다.
* - 정확하게 N 킬로그램을 만들 수 없다면 -1 반환
*
* 예) N = 18, 결과 = 4
*     N = 7,  결과 = -1
* */


public class A_SugarDelivery {


  /**
   * 3kg, 5kg 봉지로 가져갈 수 있는 최소 봉지 수
   * @param n 배달해야 할 설탕의 무게
   * @return 최소 봉지 수 (불가능한 경우 -1)
   */
  public static int solution(int n){
    int count = 0; // 봉지 수를 세기 위한 변수

    while(n >= 0 /*true도 가능*/){
      if (n % 5 == 0){
        count += n / 5;
        return count;
      }
      n -= 3;
      count++;
    }

    return -1;
  }
}


//while(true){
//  if(n % 5 == 0) return count + n/5; // 5kg으로 해결 가능한 경우
//  else if(n < 0) return -1; // 불가능
//  else if(n == 0) return count; // 3kg으로만 처리 가능한 경우
//
//  n -= 3;
//  count++;
//}


//        /* n >= 10 /
//        if (n >= 10) {
//            count += (n / 10)2;
//            n %= 10;
//            if(n % 3 == 0){
//                count += n / 3;
//            }else if(n == 5  n == 8){
//                count += 2;
//            }else {
//                count = -1;
//            }
//        }
//
//        /* 0 < n < 10 */
//        else if (n >= 0 && n < 10) {
//            if(n % 3 == 0){
//                count += n / 3;
//            }else if(n == 5  n == 8){
//                count += 2;
//            }else {
//                count = -1;
//            }
//        }


