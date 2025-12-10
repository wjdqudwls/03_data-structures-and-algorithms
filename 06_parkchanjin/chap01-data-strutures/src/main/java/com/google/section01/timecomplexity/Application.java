package com.google.section01.timecomplexity;


import java.util.Arrays;

/* ** 시간 복잡도를 이해할 수 있다. **
 *  시간 복잡도란?
 *  - 알고리즘(로직) 해결하는데 걸리는 시간의 양을 나타내는 척도
 *  - 입력 크기(n)에 대한 함수로 표현
 *  - Big-O 표기법을 사용하여 최악의 경우를 나타냄
 *  - 특정 알고리즘이 대규모 데이터 셋에서 얼마나 잘 동작할지 예측하는데 중요함
 * */
public class Application {
  public static void main(String[] args) {

    // 테스트용 저수 배열 선언 및 초기화
    int[] arr = {3, 1, 2, 5, 6, 9, 4, 12};

    System.out.println("상수 시간 O(1) 결과 : " + getFirst(arr));

    System.out.println("상수 시간 O(n) 결과 : " + Arrays.toString(reverse(arr)));

    System.out.println("로그 시간 O(log n) 결과 인덱스 : " + binarySearch(arr, 8));

    System.out.println("지수 시간 O(2^n) 결과 : " + fibonacci(6));

//    arr = reverse(arr);
//    for (int i = 0; i < arr.length; i++) {
//      System.out.print(arr[i] + " ");
//    }
  }



  /* 상수 시간 O(1)
   *  - 입력된 크기와 관련 없이 코드 실행 시간이 일정한 경우를 의미
   * */
  private static int getFirst ( int[] arr){

    // 입력된 배열의 크기가 아무리 커지더라도
    // 연산은 딱 한 번(덧셈)만 수행됨
    // == 입력 크기가 실행 시간에 영향을 주지 않는다
    return arr[0] + arr[1];
  }

  /* 선형 시간 O(n)
   *  - 입력 크기(n)에 비례하여 실행 시간이 증가하는 경우를 의미
   *  - 흔한 예시 : 배열/리스트 순회
   *  - 이점 : 입력 크기에 따라 예측 가능한 성능을 제공한다.
   * */
  private static int[] reverse ( int[] arr){
    int[] result = new int[arr.length];

    // reverse 로직
    for (int i = 0; i < arr.length; i++) {
      result[arr.length - i - 1] = arr[i];
    }

    return result;
  }


  /* 로그 시간 O(log n)
   *  - 입력 크기(n)가 증가할 때, 실행 시간이 로그 함수적으로 증가하는 경우를 의미
   *  - 주로 탐색 공간을 절반씩 줄여 나가며 탐색하는 알고리즘에서 나타남
   *  - 이점 : 입력이 커져도 효율적인 성능을 유지함
   * */
  private static int binarySearch ( int[] arr, int target){

    /* 초기 세팅
     *  - 배열 내 요소가 오름차순 정렬이 되어있어야 한다
     *  */
    // Arrays.sort(arr);
    // 배열 원본 정렬
    // Arrays.sort() 는 일반적으로 O(n log n)의 시간 복잡도를 가짐.
    // -> 정렬 시간은 제외하고 생각하자!!
    Arrays.sort(arr);

    // 탐색 시작(left), 끝(right) 인덱스를 가리키는 포인터
    int left = 0;
    int right = arr.length - 1;

    //
    while (left <= right) {
      // 중간 찾기
      // int mid = left +  (right - left) / 2;
      int mid = (left + right)  / 2;

      // 1. 찾으려는 target이 mid와 같다면
      if(target == arr[mid]) return mid; // 가운데를 나타내는 인덱스 반환

      // 2. 찾으려는 target이 중간보다 크다면 (mid보다 오른쪽에 있을 경우)
      if(arr[mid] < target){
        left = mid + 1;
      }
      /* 3. 찾으려는 target이 중간보다 작다면(mid보다 왼쪽에 있을 경우) */
      else {
        right = mid - 1;
      }
    }
    // 찾으려는 값이 배열 내에 없거나 또는 정렬 안됨
    return -1;
  }

  /* 지수 시간 O(2^n)
   *  - 입력 크기(n)이 증가할 때 마다 2의 n제곱에 비례하여
   *    시간이 기하급수적으로 증가하는 경우를 의미 */


  // 피보나치 수열에서 n번째 숫자 반환하기
  private static int fibonacci(int n) {
    // 0 1 1 2 3 5 8 13 21 34 55 ...........

    if(n <= 1) return n;

    // 재귀 함수
    // - 함수가 다시 자신을 호출하는 함수
    //
    return fibonacci(n - 1) + fibonacci(n - 2);
  }





}