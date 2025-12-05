package com.ohgiraffers.section05.deque;

/**
 * 이중 연결 리스트(Doubly Linked List) 기반 덱(Deque) 직접 구현 클래스.
 * 각 노드는 이전 노드와 다음 노드를 모두 가리키는 포인터 가짐.
 *
 * @param <T> 덱에 저장할 요소 타입
 */
public class LinkedListDeque<T> {
  // 덱 맨 앞 노드를 가리키는 포인터
  private Node<T> front;
  // 덱 맨 뒤 노드를 가리키는 포인터
  private Node<T> rear;
  // 덱에 저장된 요소 개수
  private int size;

  /**
   * 노드 클래스.
   * 덱 각 요소 나타내는 노드. 데이터 저장 및 이전/다음 노드 가리킴.
   */
  private static class Node<T> {
    T data;       // 노드가 저장할 실제 데이터
    Node<T> next; // 다음 노드를 가리키는 포인터
    Node<T> prev; // 이전 노드를 가리키는 포인터

    Node(T data, Node<T> next, Node<T> prev) {
      this.data = data;
      this.next = next;
      this.prev = prev;
    }
  }

  /**
   * LinkedListDeque 기본 생성자.
   * 새 이중 연결 리스트 기반 덱 생성, front, rear, size 초기화.
   */
  public LinkedListDeque() {
    front = null;
    rear = null;
    size = 0;
  }

  /**
   * 덱 맨 앞에 요소 추가 (addFirst)
   * 시간 복잡도: O(1) - front 포인터만 변경.
   *
   * @param data 추가할 데이터
   */
  public void addFirst(T data) {
    // 1. 새 노드 생성. 새 노드 next는 현재 front 가리키고, prev는 null.
    Node<T> newNode = new Node<>(data, front, null);
    // 2. 덱 비어있는지 확인.
    if (isEmpty()) {
      // 2-1. 비어있다면, 새 노드가 front이자 rear.
      front = rear = newNode;
    } else {
      // 2-2. 비어있지 않다면, 현재 front 노드 이전 포인터가 새 노드 가리키도록 설정,
      //      front 포인터를 새 노드로 갱신.
      front.prev = newNode;
      front = newNode;
    }
    // 3. 덱 크기 1 증가.
    size++;
  }

  /**
   * 덱 맨 뒤에 요소 추가 (addLast)
   * 시간 복잡도: O(1) - rear 포인터만 변경.
   *
   * @param data 추가할 데이터
   */
  public void addLast(T data) {
    // 1. 새 노드 생성. 새 노드 prev는 현재 rear 가리키고, next는 null.
    Node<T> newNode = new Node<>(data, null, rear);
    // 2. 덱 비어있는지 확인.
    if (isEmpty()) {
      // 2-1. 비어있다면, 새 노드가 front이자 rear.
      front = rear = newNode;
    } else {
      // 2-2. 비어있지 않다면, 현재 rear 노드 다음 포인터가 새 노드 가리키도록 설정,
      //      rear 포인터를 새 노드로 갱신.
      rear.next = newNode;
      rear = newNode;
    }
    // 3. 덱 크기 1 증가.
    size++;
  }

  /**
   * 덱 맨 앞에서 요소 제거 및 반환 (removeFirst)
   * 시간 복잡도: O(1) - front 포인터만 변경.
   *
   * @return 제거된 맨 앞 요소
   * @throws RuntimeException 덱 비어있는 경우 예외 발생
   */
  public T removeFirst() {
    // 1. 덱 비어있는지 확인.
    if (isEmpty()) {
      throw new RuntimeException("덱 비어있음.");
    }
    // 2. front 노드 데이터 임시 변수에 저장.
    T data = front.data;
    // 3. front 포인터를 현재 front 다음 노드로 이동.
    front = front.next;
    // 4. front가 null이 되면 (덱이 비면), rear도 null로 설정.
    if (front == null) {
      rear = null;
    } else {
      // 덱에 요소 남아있다면, 새 front 이전 포인터 null로 설정하여 연결 끊음.
      front.prev = null;
    }
    // 5. 덱 크기 1 감소.
    size--;
    // 6. 제거된 데이터 반환.
    return data;
  }

  /**
   * 덱 맨 뒤에서 요소 제거 및 반환 (removeLast)
   * 시간 복잡도: O(1) - rear 포인터만 변경.
   *
   * @return 제거된 맨 뒤 요소
   * @throws RuntimeException 덱 비어있는 경우 예외 발생
   */
  public T removeLast() {
    // 1. 덱 비어있는지 확인.
    if (isEmpty()) {
      throw new RuntimeException("덱 비어있음.");
    }
    // 2. rear 노드 데이터 임시 변수에 저장.
    T data = rear.data;
    // 3. rear 포인터를 현재 rear 이전 노드로 이동.
    rear = rear.prev;
    // 4. rear가 null이 되면 (덱이 비면), front도 null로 설정.
    if (rear == null) {
      front = null;
    } else {
      // 덱에 요소 남아있다면, 새 rear 다음 포인터 null로 설정하여 연결 끊음.
      rear.next = null;
    }
    // 5. 덱 크기 1 감소.
    size--;
    // 6. 제거된 데이터 반환.
    return data;
  }

  /**
   * 덱 맨 앞 요소 제거 없이 반환 (peekFirst)
   * 시간 복잡도: O(1)
   *
   * @return 맨 앞 요소
   * @throws RuntimeException 덱 비어있는 경우 예외 발생
   */
  public T peekFirst() {
    if (isEmpty()) {
      throw new RuntimeException("덱 비어있음.");
    }
    return front.data;
  }

  /**
   * 덱 맨 뒤 요소 제거 없이 반환 (peekLast)
   * 시간 복잡도: O(1)
   *
   * @return 맨 뒤 요소
   * @throws RuntimeException 덱 비어있는 경우 예외 발생
   */
  public T peekLast() {
    if (isEmpty()) {
      throw new RuntimeException("덱 비어있음.");
    }
    return rear.data;
  }

  /**
   * 덱 비어있는지 확인.
   *
   * @return 덱 비어있으면 true, 아니면 false
   */
  public boolean isEmpty() {
    return front == null; // front가 null이면 덱 비어있음 (rear도 null 됨)
  }

  /**
   * 덱에 저장된 요소 개수 반환.
   *
   * @return 덱의 크기
   */
  public int size() {
    return size;
  }

  /**
   * 덱 모든 요소 제거, 초기 상태로 되돌림.
   * 각 노드 참조 끊어 가비지 컬렉션 발생 돕기.
   */
  public void clear() {
    // front부터 시작하여 모든 노드 순회하며 참조 끊음.
    Node<T> current = front;
    while (current != null) {
      Node<T> next = current.next; // 다음 노드 미리 저장
      current.data = null;         // 데이터 참조 해제
      current.next = null;         // 다음 노드 참조 해제
      current.prev = null;         // 이전 노드 참조 해제
      current = next;              // 다음 노드로 이동
    }
    front = rear = null; // front와 rear를 null로 설정
    size = 0;            // 크기 0으로 초기화
  }

  /**
   * 덱 내용 문자열로 반환 (디버깅용)
   * 시간 복잡도: O(n) - 모든 노드 순회 필요.
   *
   * @return 덱의 문자열 표현
   */
  @Override
  public String toString() {
    if(isEmpty()) {
      return "LinkedListDeque[]";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("LinkedListDeque[");
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