package com.wjdqudwls.section01.sorting;

import java.util.Arrays;

/* 퀵 정렬
 *  - 기준 값(pivot)을 선정하여 해당 값을 기준으로 작은 값과 큰 값을 "분할"하는 정렬
 *    작은 값과 큰 값을 "분할"하는 정렬
 *  - 분할 후 각각의 부분 배열을 재귀적으로 정렬하여
 *    최종적으로 모두 정렬된 형태를 완성한다.
 *  - 시간 복잡도
 *   -- 평균 : O(n log n)
 *   -- 최악 : O(n^2)
 *
 *  - 메모리 사용이 적고, 대부분 데이터 정렬에서 우수한 효과를 보여줌
 * */
public class D_QuickSort {
  /* 문제 : n개의 정수가 주어졌을 때
   *        퀵 정렬 알고리즘을 사용하여 오름차순 정렬하는 프로그램 작성하기
   * */

  public static void solution(int[] arr) {
    System.out.println("원본 배열 : " + Arrays.toString(arr));

    quickSort(arr, 0, arr.length - 1);

    System.out.println("정렬 결과 : " + Arrays.toString(arr));
  }

  /**
   * 재귀 호출용 퀵 정렬 메서드
   *
   * @param arr
   * @param low
   * @param high
   */
  private static void quickSort(int[] arr, int low, int high) {

    // 정렬을 멈추는 경우
    if (low >= high) return;

    // 최종 pivot 위치 == 원본 배열을 나눌 지점
    int partitionIndex = partition(arr, low, high);

    // pivot 기준으로 배열을 나눠서 다시 퀵 정렬 수행(재귀)
    quickSort(arr, low, partitionIndex - 1);
    quickSort(arr, partitionIndex, high);
  }

  /**
   * 전달 받는 arr에 피벗을 지정하고,
   * low, high를 이동 시키면서 알맞게 값을 교환하는 작업
   *
   * @param arr
   * @param low
   * @param high
   * @return 최종 pivot의 인덱스 -> pivot을 기준으로 부분 배열을 만드는데 사용
   */
  private static int partition(int[] arr, int low, int high) {
    // 중간 위치를 pivot으로 지정 => pivot 초기 위치는 바뀔 수 있다
    // 이때 pivot에 index가 아닌 값을 지정한다
    //  => 값을 기준으로 변경할 것이기 때문
    int pivot = arr[(low + high) / 2];

    System.out.println("pivot : " + pivot + " 기준, 인덱스 범위 : " + low + " ~ " + high);
    System.out.println("분할 전 배열 : " + Arrays.toString(arr));

    // low와 high가 겹치거나 교차될 때 까지 반복
    while (low <= high) {

      // 왼쪽 low부터 우측으로 이동하면서 pivot보다 큰 값이 있으면 low를 멈춘다
      while (arr[low] < pivot) low++;

      // 오른쪽 high부터 좌측으로 이동하면서 pivot보다 작은 값이 있으면 high를 멈춘다
      while (arr[high] > pivot) high--;

      // low, high가 이동했는데, low랑 high가 교차(low>high)되지 않은 경우 low값과 high값을 swap해줌
      if (low <= high) {
        swap(arr, low, high);
        // low와 high 위치의 값들은 변경되었으므로, 불필요하게 한번 더 연산을 막겠다
        low++;
        high--;
      }
    }

    System.out.println("분할 후 배열 : " + Arrays.toString(arr));
    System.out.println("반환할 pivot의 인덱스 : " + low);
    System.out.println("----------------------------------------------------------------");
    return low;
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}

