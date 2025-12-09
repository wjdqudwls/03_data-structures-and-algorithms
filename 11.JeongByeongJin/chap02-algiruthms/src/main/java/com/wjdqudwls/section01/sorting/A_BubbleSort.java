package com.wjdqudwls.section01.sorting;

import java.util.Arrays;

/* 버블 정렬
 * - 인접한 두 요소를 비교하여 크기가 순서대로 정렬되지 않았다면
 *   두 요소의 자리를 교환하는 방식의 정렬이다.
 *
 * - 배열 전체를 여러 번 반복하면서 반복마다 가장 큰 요소를 끝으로 이동시킨다.
 * - 시간 복잡도
 * -- 평균 : O(n^2)
 * -- 최선 : O(n) (이미 정렬 되어있거나, 1~2개만 정렬하면 되는 상태)
 *
 * - 하지만 교환 연산이 많기 때문에 다른 정렬보다 실제로는 더 느림
 * - 장점 : 정렬 알고리즘 중 가장 쉽다
 * */
public class A_BubbleSort {
  public static void solution(int[] arr) {
    /* 문제 : n개의 정수가 주어졌을 때
     *        버블 정렬 알고리즘을 사용하여 오름차순 정렬하는 프로그램 작성하기
     * */
    System.out.println("초기 값 : " + Arrays.toString(arr));

    // 버블이 처음 -> 끝 이동을 반복하는 루프
    for (int i = 0; i < arr.length - 1; i++) {

      // 버블 이동 루프
      for (int j = 0; j < arr.length - i - 1; j++) {

        if(arr[j]>arr[j+1]){
          int tmp=arr[j];
          arr[j]=arr[j+1];
          arr[j+1]=tmp;
        }

      }
      System.out.println((i + 1) + "번째 : " + Arrays.toString(arr));
    }
  }

}
