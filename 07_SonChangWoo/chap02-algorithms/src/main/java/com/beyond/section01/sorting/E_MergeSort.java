package com.beyond.section01.sorting;

/* 병합 정렬
 * - 분할 정복(Divide and Conquer) 방식으로
 *   배열을 반씩 나누어(1-2개 요소가 남을 때 까지) 정렬한 후 병합하는 방식
 * - 정렬된 두 배열을 합치는 과정에서 안정 정렬(Stable Sort) 특성을 유지할 수 있다.
 * - 시간 복잡도 : O(n log n) (평균, 최악 모두 일정)
 * - 추가 메모리 공간이 필요하기 때문에 메모리 제한적 환경에서는 비효율적일 수 있다.
 * */

import java.util.Arrays;

public class E_MergeSort {

  /* 문제 : n개의 정수가 주어졌을 때
   *        병합 정렬 알고리즘을 사용하여 오름차순 정렬하는 프로그램 작성하기
   * */

  public static void solution(int[] arr) {

    System.out.println("원본 배열 : " + Arrays.toString(arr));

    // 병합 정렬에 필요한 임시 배열 생성(추가 메모리 공간)
    int[] temp = new int[arr.length];

    // 병합 정렬 실행 -> 배열을 나눌 범위를 전달(0 - arr.length - 1)
    mergeSort(arr, temp, 0, arr.length - 1);


    System.out.println("정렬 결과 : " + Arrays.toString(arr));


  }

  /**
   * 병합 정렬의 핵심 메서드(재귀 함수)
   * 분할 정복 방식으로 배열을 반으로 나누어 정렬
   *
   * @param arr   정렬할 배열
   * @param temp  임시 저장 공간
   * @param left  정렬 시작 인덱스
   * @param right 정렬 끝 인덱스
   */
  private static void mergeSort(int[] arr, int[] temp, int left, int right) {

    // 분할된 배열의 크기가 2 이상인 경우만 수행
    // <-> 분할된 배열의 크기가 1 이하인 경우에는 수행 X
    //     --> if문 수행 종료 == 분할된 배열이 정렬 완료됨
    if (left < right) {

      // 1. 분할 - 중간 지점 계산
      int mid = (left + right) / 2;

      // 2. 왼쪽 정복 - 왼쪽 부분 재귀 호출 -> 정렬수행
      mergeSort(arr, temp, left, mid);

      // 3. 오른쪽 정복 - 오른쪽 부분 재귀 호출 -> 정렬수행
      mergeSort(arr, temp, mid + 1, right);

      // 4. 병합 - 정복된 왼쪽, 오른쪽 부분 배열을 합치면서 정렬
      merge(arr, temp, left, mid, right);

    }

  }

  /**
   * 정복된 두 배열을 하나로 병합하면서 정렬하는 메서드
   *
   * @param arr   원본 배열(병합 결과가 저장됨)
   * @param temp  임시 배열
   * @param left  왼쪽 부분 배열의 시작 인덱스
   * @param mid   왼쪽 부분 배열의 끝 인덱스, mid+1 : 오른쪽 부분 배열의 시작
   * @param right 오른쪽 부분 배열의 끝 인덱스
   */
  private static void merge(int[] arr, int[] temp, int left, int mid, int right) {

    System.out.println("병합 전 : " + Arrays.toString(arr));
    System.out.printf("left: %d, mid: %d, right: %d \n", left, mid, right);
    // 1단계 : 병합할 구간의 배열 요소를 임시 배열(temp)에 복사
    // -> 원본 수정 전 값을 보존하기 위해서
    for (int i = left; i <= right; i++) {
      temp[i] = arr[i];
    }

    // 2단계 : 병합을 위한 인덱스 초기화
    int leftIndex = left;
    int rightIndex = mid + 1;
    int current = left;

    // 3단계 : 두 부분 배열을 비교하면서 작은 값부터 원본 배열에 저장
    // -> 이 과정에서 정렬이 됨
    // 두 부분 배열에 모두 남은 요소가 있을 때만 반복
    while (leftIndex <= mid && rightIndex <= right) {

      // 임시 배열의 leftIndex가 가리키는 값이
      // rightIndex가 가리키는 값 보다 같거나 작은 경우
      if (temp[leftIndex] <= temp[rightIndex]) {
        arr[current++] = temp[leftIndex++];
      }
      // 임시 배열의 rightIndex가 가리키는 값이
      // leftIndex가 가리키는 값 보다 작은 경우
      else {
        arr[current++] = temp[rightIndex++];
      }
    }

    // 4단계 : 왼쪽 배열에 남은 요소를 복사
    // 오른쪽 배열에 요소가 남아있을 경우에는 별도 처리 필요 X
    // -> 이미 정렬된 상태로 볼 수 있음
    while (leftIndex <= mid) {
      arr[current++] = temp[leftIndex++];
    }

    System.out.println("병합 후 : " + Arrays.toString(arr));
    System.out.println("------------------------------------");

  }

}