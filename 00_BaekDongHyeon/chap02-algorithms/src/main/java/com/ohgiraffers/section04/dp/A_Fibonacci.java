package com.ohgiraffers.section04.dp;

/* 동적 계획법(Dynamic Programming)
 * - 복잡한 문제를 작은 부분 문제로 나누어 해결하는 알고리즘 설계 기법
 * - 한 번 계산한 결과를 메모리에 저장하여 같은 계산을 반복하지 않음
 *   (Memoization, Tabulation)
 * - "동적"이라는 단어는 프로그램 실행/문제 풀이 중 데이터를 저장한다는 의미
 * */

public class A_Fibonacci {

  /* 재귀 호출 방식의 피보나치 수열
   * [문제점]
   * - 호출이 너무 많다 O(2^n)
   * - 같은 값을 여러 번 계산(중복 계산)
   */
  public static int getFibonacci(int n) {
    if (n == 0) {
      return 0;
    } else if (n == 1) {
      return 1;
    } else {
      return getFibonacci(n - 1) + getFibonacci(n - 2);
    }
  }


  /* [DP : Top Down 방식]
   * - 큰 문제로 시작해서 작은 문제로 분할(재귀)하는 방식
   * - 필요한 부분만 계산
   * - 재귀 함수의 직관성 + DP 효율성
   *
   * - Memoization(메모이제이션)
   *   - 작은 문제(재귀) 결과를 메모리에 저장
   *   - 이전에 계산한 결과를 재활용하는 기법
   * */
  static int[] DP = new int[50];

  public static int getFibonacciNumberDP(int n) {
    if (n == 0) {
      return 0;
    } else if (n == 1) {
      return 1;

    } else {

      // 1. 확인(Check) : 함수 호출 전 해당 결과가 이미 저장되어 있는지 확인
      if (DP[n] == 0) {
        // 2. 저장(Store) : 계산되지 않은 값은 연산 후 특정 자료 구조에 저장(메모이제이션)
        DP[n] = getFibonacciNumberDP(n - 1) + getFibonacciNumberDP(n - 2);
      }
      // 3. 재활용(Reuse) : 저장된 결과를 반환(재사용)
      return DP[n];
    }
  }

  /* [DP : Bottom Up 방식]
  * - 작은 문제부터 시작해서 큰 문제로 확장(반복문 사용)
  * - 모든 부분을 순차적으로 계산
  * - 재귀 호출 X -> Stack Overflow 위험이 없음
  * - 일반적으로 Top-Down 방식보다 약간 더 빠름
  *
  * - Tabulation(타뷸레이션)
  *   - 작은 문제(부분 문제)로 나눈 후 연산 결과를 테이블(배열)에 저장하는 방식
  * */
  public static int getFibonacciNumberIter(int n) {
    int[] arr = new int[n + 1];
    arr[0] = 0;
    arr[1] = 1;

    if (n == 0) return arr[0];
    else if (n == 1) return arr[1];

    else {
      for (int i = 2; i < n + 1; i++) arr[i] = arr[i - 1] + arr[i - 2];
      return arr[n];
    }

  }

}
