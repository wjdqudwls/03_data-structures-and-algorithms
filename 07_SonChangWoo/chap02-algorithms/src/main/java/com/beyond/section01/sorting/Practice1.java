package com.beyond.section01.sorting;

/* 최적화된 버블 정렬 구현하기
* - 버블 정렬은 인접한 두 요소를 비교하여 정렬하기 때문에
*   일반적으로 O(n^2)의 시간 복잡도를 갖는다.
*
* - 다만, 정렬이 어느 정도 되어 있는 경우
*   불필요한 비교를 줄여서 O(n)까지 최적화(성능 개선)을 할 수 있다.
* */

import java.util.Arrays;

public class Practice1 {
  public static void solution(int[] arr) {

    /* 문제 : n개의 정수가 주어졌을 때
     *        버블 정렬 알고리즘을 사용하여 오름차순 정렬하는 프로그램 작성하기
     * */

    boolean swapped = false;

    System.out.println("초기값 : " + Arrays.toString(arr));

    // 버블이 처음 -> 끝 이동을 반복하는 루프
    for (int i = 0; i < arr.length - 1; i++) {

      swapped = false; // 스왑 여부 확인
      //버블 이동 루프
      for (int j = 0; j < arr.length - i - 1; j++) {

        if (arr[j] > arr[j + 1]) { // 왼쪽 요소가 큰 경우 swap
          swap(arr, j, j + 1);
          swapped = true;
        }
      }

      if (!swapped) {
        break;
      }


      System.out.println((i + 1) + "번째 : " + Arrays.toString(arr));
    }

  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
