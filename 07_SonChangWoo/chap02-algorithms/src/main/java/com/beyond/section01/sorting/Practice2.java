package com.beyond.section01.sorting;

import java.util.Arrays;

/* TimSort 구현하기
 * 삽입 정렬과 병합 정렬의 장점을 결합한 하이브리드 정렬 알고리즘
 * 작은 배열(예: 길이 32 이하)은 삽입 정렬로 정렬한 후, 병합 정렬 방식으로 전체 배열을 병합한다.
 * */
public class Practice2 {
  // TimSort에서 사용할 RUN의 크기 (작은 배열은 삽입 정렬로 정렬)
  private static final int RUN = 32;

  public static void timSort(int[] arr) {
    int n = arr.length;

    // 각 RUN 단위로 삽입 정렬 수행
    for (int i = 0; i < n; i += RUN) {
      insertionSort(arr, i, Math.min((i + RUN - 1), n - 1));
    }

    System.out.println("삽입 정렬 수행 후의 arr : " + Arrays.toString(arr));

    /* 병합 과정에서 병합할 두 구간의 크기(size) 를 조정
     * 배열 전체를 다룰 때까지 병합할 구간의 크기를 확장
     * 한 단계마다 두 개의 정렬된 구간을 병합하여 구간 크기를 두 배로 늘림
     * 처음에는 두 개의 RUN 구간을 병합하고, 그 다음에는 두 개의 2×RUN 구간을 병합
     * */
    for (int size = RUN; size < n; size = 2 * size) {
      /* 배열 전체를 순회하면서, 현재 size에 해당하는 두 개의 정렬된 구간을 찾아서 병합
       * 한 번에 두 개의 구간(각각의 크기가 size)을 병합하기 때문에
       * 병합이 끝난 후 다음 두 구간으로 건너뛰기 위해 2×size 만큼 증가
       * */
      for (int left = 0; left < n; left += 2 * size) {
        int mid = left + size - 1;
        int right = Math.min(left + 2 * size - 1, n - 1);
        if (mid < right) {
          merge(arr, left, mid, right);
        }
        System.out.println("left : " + left + " mid : " + mid + " right : " + right);
        System.out.println("merge 실행 후의 arr : " + Arrays.toString(arr));
      }
    }
  }

  /* 주어진 구간에 대해 삽입 정렬을 수행하는 메소드 */
  private static void insertionSort(int[] arr, int left, int right) {
    for (int i = left + 1; i <= right; i++) {
      int key = arr[i];
      int j = i - 1;
      while (j >= left && arr[j] > key) {
        arr[j + 1] = arr[j];
        j--;
      }
      arr[j + 1] = key;
    }
  }

  /* 두 개의 정렬된 부분 배열을 병합하여 하나의 정렬된 배열로 만드는 메소드 */
  private static void merge(int[] arr, int left, int mid, int right) {
    int len1 = mid - left + 1;
    int len2 = right - mid;

    // 임시 배열 생성
    int[] leftArr = new int[len1];
    int[] rightArr = new int[len2];

    System.arraycopy(arr, left, leftArr, 0, len1);
    System.arraycopy(arr, mid + 1, rightArr, 0, len2);

    int i = 0, j = 0, k = left;

    // 두 임시 배열을 비교하여 정렬된 순서로 원본 배열에 복사
    while (i < len1 && j < len2) {
      if (leftArr[i] <= rightArr[j]) {
        arr[k++] = leftArr[i++];
      } else {
        arr[k++] = rightArr[j++];
      }
    }

    // 남은 요소들을 복사
    while (i < len1) {
      arr[k++] = leftArr[i++];
    }
  }

}