package com.google.section01.sorting;

import java.util.Arrays;

/* 최적화된 버블 정렬 구현하기
 *  - 버블 정렬은 인접한 두 요소를 비교하여 정렬하기 때문에
 *    일반적으로 O(n^2)의 시간 복잡도를 갖는다.
 *
 *  - 다만, 정렬이 어느정도 되어있는 경우
 *    불필요한 비교를 줄여서 O(n)까지 최적화(성능 개선)을 할 수 있다.
 * */
public class Practice1 {
  public static void solution(int[] arr) {

    System.out.println("초기 값 : " + Arrays.toString(arr));
    boolean swapped = false; // 스왑 여부 확인


    for (int i = 0; i < arr.length - 1; i++) {
      swapped = false;

      // 버블 이동 루프
      for (int j = 0; j < arr.length - i - 1; j++) {

        if (arr[j] > arr[j + 1]) {
          swap(arr, j, j + 1);
          swapped = true;// 스왑한적있음
        }
      }

      System.out.println(i + " 번째 : " + Arrays.toString(arr));
    }
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}

