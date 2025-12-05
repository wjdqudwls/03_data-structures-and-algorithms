package com.ohgiraffers.section04.queue;

/**
 * 연결 리스트 기반 큐(Queue) 직접 구현 클래스
 *
 * @param <T> 큐에 저장할 요소 타입
 */
public class LinkedListQueue<T> {
  // 큐 맨 앞 노드를 가리키는 포인터 (데이터 나올 위치, head와 동일)
  private Node<T> front;
  // 큐 맨 뒤 노드를 가리키는 포인터 (데이터 들어갈 위치, tail과 동일)
  private Node<T> rear;
  // 큐에 저장된 요소 개수
  private int size;

  /**
   * 노드 클래스.
   * 큐 각 요소 나타내는 노드. 데이터 저장 및 다음 노드 가리킴.
   */
  private static class Node<T> {
    T data;       // 노드가 저장할 실제 데이터
    Node<T> next; // 다음 노드를 가리키는 포인터

    Node(T data, Node<T> next) {
      this.data = data;
      this.next = next;
    }
  }

  /**
   * LinkedListQueue 기본 생성자.
   * 새 연결 리스트 기반 큐 생성, front, rear, size 초기화.
   */
  public LinkedListQueue() {
    front = null;
    rear = null;
    size = 0;
  }

  /**
   * 큐 맨 뒤에 요소 추가 (enqueue/offer)
   * 시간 복잡도: O(1) - rear 포인터를 통해 맨 뒤에 바로 추가 가능.
   *
   * @param data 추가할 데이터
   */
  public void enqueue(T data) {
    // 1. 새 노드 생성. 새 노드 next는 항상 null.
    Node<T> newNode = new Node<>(data, null);
    // 2. 큐 비어있는지 확인.
    if (isEmpty()) {
      // 2-1. 비어있다면, 새 노드가 front이자 rear.
      front = rear = newNode;
    } else {
      // 2-2. 비어있지 않다면, 현재 rear 다음 노드로 새 노드 연결,
      //      rear 포인터를 새 노드로 갱신.
      rear.next = newNode;
      rear = newNode;
    }
    // 3. 큐 크기 1 증가.
    size++;
  }

  /**
   * 큐 맨 앞에서 요소 제거 및 반환 (dequeue/poll)
   * 시간 복잡도: O(1) - front 포인터를 통해 맨 앞에서 바로 제거 가능.
   *
   * @return 제거된 맨 앞 요소
   * @throws RuntimeException 큐 비어있는 경우 예외 발생
   */
  public T dequeue() {
    // 1. 큐 비어있는지 확인.
    if (isEmpty()) {
      throw new RuntimeException("큐 비어있음.");
    }
    // 2. front 노드 데이터를 임시 변수에 저장.
    T data = front.data;
    // 3. front 포인터를 현재 front 다음 노드로 이동.
    front = front.next;
    // 4. front가 null이 되면 (큐가 비면), rear도 null로 설정.
    if (front == null) {
      rear = null;
    }
    // 5. 큐 크기 1 감소.
    size--;
    // 6. 제거된 데이터 반환.
    return data;
  }

  /**
   * 큐 맨 앞 요소 제거 없이 반환 (peek)
   * 시간 복잡도: O(1)
   *
   * @return 맨 앞 요소
   * @throws RuntimeException 큐 비어있는 경우 예외 발생
   */
  public T peek() {
    if (isEmpty()) {
      throw new RuntimeException("큐 비어있음.");
    }
    return front.data;
  }

  /**
   * 큐 비어있는지 확인.
   *
   * @return 큐 비어있으면 true, 아니면 false
   */
  public boolean isEmpty() {
    return front == null;
  }

  /**
   * 큐에 저장된 요소 개수 반환.
   *
   * @return 큐의 크기
   */
  public int size() {
    return size;
  }

  /**
   * 큐 모든 요소 제거, 초기화.
   */
  public void clear() {
    front = null;
    rear = null;
    size = 0;
  }

  /**
   * 큐 내용 문자열로 반환 (디버깅용)
   * 시간 복잡도: O(n) - 모든 노드 순회 필요.
   *
   * @return 큐의 문자열 표현
   */
  @Override
  public String toString() {
    if(isEmpty()) {
      return "LinkedListQueue[]";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("LinkedListQueue[");
    Node<T> current = front; // front부터 시작하여 모든 노드 순회
    while (current != null) {
      sb.append(current.data);
      if (current.next != null) {
        sb.append(", ");
      }
      current = current.next; // 다음 노드로 이동
    }
    sb.append("]");
    return sb.toString();
  }
}