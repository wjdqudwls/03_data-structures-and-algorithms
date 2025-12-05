package com.ohgiraffers.section03.stack;

import java.util.EmptyStackException;

/**
 * 배열 기반 스택(Stack) 직접 구현
 * 제네릭을 사용하여 모든 타입 데이터 저장 가능
 *
 * @param <T> 스택에 저장할 요소의 타입
 */
public class ArrayStack<T> {
  // 스택의 기본 초기 용량
  private static final int INITIAL_CAPACITY = 10;

  // 데이터를 저장할 내부 배열
  private T[] stack;

  // 스택의 최상단 요소 인덱스를 가리키는 변수.
  // top은 0부터 시작, -1은 스택이 비어있음을 의미
  private int top;

  /**
   * 기본 생성자 - 기본 용량으로 스택 초기화
   */
  @SuppressWarnings("unchecked")
  public ArrayStack() {
    // 제네릭 배열 직접 생성 불가, Object 배열 생성 후 형변환
    stack = (T[]) new Object[INITIAL_CAPACITY];
    top = -1; // 스택이 비어있음을 나타내는 초기값
  }

  /**
   * 스택 맨 위에 요소 추가 (push)
   * 시간 복잡도: O(1) (평균), O(n) (배열 크기 재조정 시)
   *
   * @param data 추가할 요소
   */
  public void push(T data) {
    // 1. 스택 가득 찼는지 확인 (top이 배열의 마지막 인덱스와 같으면 가득 찬 상태)
    if (top == stack.length - 1) {
      // 2. 가득 찬 경우 배열 크기 늘림
      resize();
    }
    // 3. top 1 증가 후, 해당 위치에 새 데이터 저장
    stack[++top] = data;
  }

  /**
   * 스택 맨 위 요소 제거 및 반환 (pop)
   * 시간 복잡도: O(1)
   *
   * @return 제거된 최상단 요소
   * @throws EmptyStackException 스택 비어있는 경우 예외 발생
   */
  public T pop() {
    // 1. 스택 비어있는지 확인
    if (isEmpty()) {
      throw new EmptyStackException();
    }
    // 2. 최상단(top) 데이터 임시 변수에 저장
    T data = stack[top];
    // 3. 현재 top 요소 null로 만들어 GC 메모리 회수 돕고, top 1 감소
    stack[top--] = null;
    // 4. 제거된 데이터 반환
    return data;
  }

  /**
   * 스택 맨 위 요소 제거 없이 반환 (peek)
   * 시간 복잡도: O(1)
   *
   * @return 스택의 최상단 요소
   * @throws EmptyStackException 스택 비어있는 경우 예외 발생
   */
  public T peek() {
    // 1. 스택 비어있는지 확인
    if (isEmpty()) {
      throw new EmptyStackException();
    }
    // 2. 최상단(top) 데이터 반환
    return stack[top];
  }

  /**
   * 스택 비어있는지 확인
   *
   * @return 스택 비어있으면 true, 아니면 false
   */
  public boolean isEmpty() {
    // top이 -1이면 스택 비어있는 상태
    return top == -1;
  }

  /**
   * 스택에 저장된 요소 개수 반환
   *
   * @return 스택의 크기
   */
  public int size() {
    // top은 0부터 시작 인덱스, 요소 개수는 top + 1
    return top + 1;
  }

  /**
   * 스택 모든 요소 제거
   */
  public void clear() {
    // 스택 모든 요소 순회하며 null로 설정, 메모리 해제
    for (int i = 0; i <= top; i++) {
      stack[i] = null;
    }
    top = -1; // 스택 비어있는 상태로 초기화
  }

  /**
   * 데이터 스택의 몇 번째 위치에 있는지 반환 (맨 위 1), 없으면 -1 반환
   *
   * @param data 찾을 데이터
   * @return 데이터의 위치 (1-based) 또는 -1
   */
  public int search(T data) {
    // 위치는 1부터 시작
    int position = 1;
    // 스택 top부터 0까지 순차적 검사 (맨 위 1번째)
    for (int i = top; i >= 0; i--) {
      // null 값 비교
      if (stack[i] == null && data == null) {
        return position;
      }
      // 객체 값 비교
      else if (stack[i] != null && stack[i].equals(data)) {
        return position;
      }
      position++; // 위치 1 증가
    }
    // 데이터 찾지 못하면 -1 반환
    return -1;
  }

  /**
   * 스택 크기 부족 시 배열 크기 두 배로 확장하는 private 헬퍼 메소드
   */
  private void resize() {
    // 1. 새 용량(기존 용량의 2배) 계산
    int newCapacity = stack.length * 2;
    @SuppressWarnings("unchecked")
    // 2. 새 용량으로 새 배열 생성
    T[] newStack = (T[]) new Object[newCapacity];
    // 3. 기존 배열(stack)의 모든 요소 새 배열(newStack)로 복사
    System.arraycopy(stack, 0, newStack, 0, stack.length);
    // 4. 내부 배열(stack) 참조를 새 배열(newStack)로 교체
    stack = newStack;
  }

  /**
   * 스택 내용 문자열로 반환 (디버깅용)
   * 스택 최상단(top) 요소부터 출력
   * 예: ArrayStack[topElement, ..., bottomElement]
   *
   * @return 스택의 문자열 표현
   */
  @Override
  public String toString() {
    if(isEmpty()) {
      return "ArrayStack[]";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("ArrayStack[");
    // 스택의 top부터 순서대로 출력 (LIFO 순서)
    for (int i = top; i >= 0; i--) {
      sb.append(stack[i]);
      if (i > 0) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}