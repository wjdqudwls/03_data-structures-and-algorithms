package com.beyond.section01.sorting;

/* 퀵 정렬
* - 기준 값(pivot)을 선정하여 해당 값을 기준으로
*   작은 값과 큰 값을 "분할"하는 정렬
* - 분할 후 각각의 부분 배열을 재귀적으로 정렬하여
*   최종적으로 모두 정렬된 형태를 완성한다.
* - 시간 복잡도
* -- 평균 : O(nlog n)
* -- 최악 : O(n^2)
*
* -- 메모리 사용이 적고, 대부분 데이터 정렬에서 우수한 효과를 보여줌
* */

import java.util.Arrays;

public class D_QuickSort {
  /* 문제 : n개의 정수가 주어졌을 때
   *        삽입 정렬 알고리즘을 사용하여 오름차순 정렬하는 프로그램 작성하기
   * */
  public static void solution(int[] arr) {

    System.out.println("원본 배열 : " + Arrays.toString(arr));

    quickSort(arr, 0, arr.length - 1);

    System.out.println("정렬 결과 : " + Arrays.toString(arr));

  }

  /**
   * 재귀 호출용 퀵 정렬 매서드
   * @param arr
   * @param low
   * @param high
   */
  private static void quickSort(int[] arr, int low, int high) {

    // 정렬을 멈추는 경우
    if (low >= high) {
      return;
    }

    // 최종 pivot 위치 == 원본 배열을 나눌 지점
    int partitionIndex = partition(arr, low, high);

    // pivot 기준으로 배열을 나눠서 다시 퀵 정렬 수행
    quickSort(arr, low, partitionIndex - 1);
    quickSort(arr, partitionIndex, high);


  }

  /**
   * 전달 받은 arr에 피벗을 지정하고,
   * low, high를 이동 시키면서 알맞게 값을 교환
   * @param arr
   * @param low
   * @param high
   * @return 최종 pivot의 인덱스 -> pivot을 기준으로 부분 배열을 만드는데 사용
   */
  private static int partition(int[] arr, int low, int high) {

    // 중간 위치 값을 pivot으로 지정
    int pivot = arr[(low + high) / 2];

    System.out.println("pivot : " + pivot + " 기준. 인덱스 범위 : " + low + " - " + high);
    System.out.println("분할 전 배열 : " + Arrays.toString(arr));

    // low와 high가 교차될 때 까지 반복
    while(low <= high) {

      // 왼쪽부터 pivot까지 크거나 같은 값을 찾을 때까지 low 증가
      while(arr[low] < pivot) low++;

      // 오른쪽부터 pivot까지 작거나 같은 값을 찾을 때까지 high 감소
      while(arr[high] > pivot) high--;

      // low, high의 이동이 끝났을 때
      // 교차되지 않은 경우
      if(low <= high) {
        swap(arr, low, high);
        low++;
        high--;
      }

    }

    System.out.println("분할 후 배열 : " + Arrays.toString(arr));
    System.out.println("반환할 pivot의 인덱스 : " + low);
    System.out.println("------------------------------------------");
    return low;
  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

}