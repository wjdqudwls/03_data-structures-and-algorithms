package com.ohgiraffers.section03.stack;

import java.util.EmptyStackException;

/**
 * 연결 리스트 기반 스택(Stack) 직접 구현 클래스
 * 제네릭 사용하여 모든 타입 데이터 저장 가능
 *
 * @param <T> 스택에 저장할 요소 타입
 */
public class LinkedListStack<T> {
  // 스택 최상단 노드를 가리키는 포인터. 스택의 'top'
  private Node<T> top;
  // 스택에 저장된 요소 개수
  private int size;

  /**
   * 노드 클래스.
   * 스택 각 요소 나타내는 노드. 데이터 저장 및 다음 노드 가리킴.
   */
  private static class Node<T> {
    T data;         // 노드가 저장할 실제 데이터
    Node<T> next;   // 다음 노드를 가리키는 포인터

    /**
     * Node 클래스 생성자.
     *
     * @param data 이 노드에 저장할 데이터
     * @param next 다음 노드를 가리킬 포인터
     */
    Node(T data, Node<T> next) {
      this.data = data;
      this.next = next;
    }
  }

  /**
   * LinkedListStack 기본 생성자.
   * 새 연결 리스트 기반 스택 생성, top과 size 초기화.
   */
  public LinkedListStack() {
    top = null; // 스택 비어있으므로 top은 null
    size = 0;   // 초기 크기 0
  }

  /**
   * 스택에 요소 추가 (push)
   * 새 요소는 항상 현재 top 노드 위에 추가, 새로운 top 됨.
   * 시간 복잡도: O(1) - top 노드 참조만 변경.
   *
   * @param data 추가할 요소
   */
  public void push(T data) {
    // 1. 새 노드 생성. 새 노드 next는 현재 top 노드 가리킴.
    //    새 노드가 기존 스택 맨 위에 위치.
    top = new Node<>(data, top);
    // 2. 스택 크기 1 증가.
    size++;
  }

  /**
   * 스택에서 요소 제거 및 반환 (pop)
   * 스택 맨 위 요소(top) 제거.
   * 시간 복잡도: O(1) - top 노드 참조만 변경.
   *
   * @return 제거된 최상단 요소
   * @throws EmptyStackException 스택 비어있는 경우 예외 발생
   */
  public T pop() {
    // 1. 스택 비어있는지 확인.
    if (isEmpty()) {
      throw new EmptyStackException();
    }
    // 2. 현재 top 노드 데이터를 임시 변수에 저장.
    T data = top.data;
    // 3. top 포인터를 현재 top 노드 다음 노드로 이동. (현재 top 노드는 GC 대상)
    top = top.next;
    // 4. 스택 크기 1 감소.
    size--;
    // 5. 제거된 데이터 반환.
    return data;
  }

  /**
   * 스택 상단 요소 제거 없이 반환 (peek)
   * 시간 복잡도: O(1)
   *
   * @return 스택 최상단 요소
   * @throws EmptyStackException 스택 비어있는 경우 예외 발생
   */
  public T peek() {
    // 1. 스택 비어있는지 확인.
    if (isEmpty()) {
      throw new EmptyStackException();
    }
    // 2. top 노드 데이터 반환.
    return top.data;
  }

  /**
   * 스택 비어있는지 확인
   *
   * @return 스택 비어있으면 true, 아니면 false
   */
  public boolean isEmpty() {
    // top 포인터 null이면 스택 비어있음.
    return top == null;
  }

  /**
   * 스택에 저장된 요소 개수 반환
   *
   * @return 스택의 크기
   */
  public int size() {
    return size;
  }

  /**
   * 스택 모든 요소 제거, 초기 상태로 되돌림.
   */
  public void clear() {
    top = null; // top null로 설정하여 모든 노드 연결 끊음 (GC 대상)
    size = 0;   // 크기 0으로 초기화.
  }

  /**
   * 스택 내용 문자열로 반환 (디버깅용)
   * 스택 top부터 순서대로 출력.
   * 예: LinkedListStack[topElement, ..., bottomElement]
   * 시간 복잡도: O(n) - 모든 노드 순회 필요.
   *
   * @return 스택의 문자열 표현
   */
  @Override
  public String toString() {
    if(isEmpty()) {
      return "LinkedListStack[]";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("LinkedListStack[");
    Node<T> current = top; // top부터 시작하여 모든 노드 순회
    while (current != null) {
      sb.append(current.data); // 현재 노드 데이터 추가
      if (current.next != null) { // 다음 노드 있다면 쉼표와 공백 추가
        sb.append(", ");
      }
      current = current.next; // 다음 노드로 이동
    }
    sb.append("]");
    return sb.toString();
  }
}