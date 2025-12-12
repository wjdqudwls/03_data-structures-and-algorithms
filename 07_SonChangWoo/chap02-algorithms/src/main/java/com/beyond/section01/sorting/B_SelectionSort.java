package com.beyond.section01.sorting;

import java.util.Arrays;

/* 선택 정렬
* - 배열에서 최소값을 찾아 맨 앞과 교환하는 방식의 정렬
* - 배열을 여러 번 반복하면서, 선택한 요소와 나머지 요소들을 비교하며 정렬한다.
* - 시간 복잡도 : O(n^2)
* - 하지만 버블 정렬보다 값 교환 횟수가 적은 편이다.
* - 단, 정렬이 어느 정도 진행되어 있어도 시간 복잡도가 변하지 않아 비효율적
* */
public class B_SelectionSort {

  /* 문제 : n개의 정수가 주어졌을 때
   *        선택 정렬 알고리즘을 사용하여 오름차순 정렬하는 프로그램 작성하기
   * */
  public static void solution(int[] arr) {

    System.out.println("초기값 : " + Arrays.toString(arr));

    for (int i = 0; i < arr.length-1; i++) {
      int minIndex = i; // 각 사이클 시작 시 첫 인덱스가 최솟값으로 지정됨
      for (int j = i + 1; j < arr.length; j++) {
        if(arr[minIndex] > arr[j]) {
          minIndex = j;
        }
      }
      swap(arr, i, minIndex);


      System.out.println((i + 1) + "번째 : " + Arrays.toString(arr));
    }

  }

  private static void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }
}
