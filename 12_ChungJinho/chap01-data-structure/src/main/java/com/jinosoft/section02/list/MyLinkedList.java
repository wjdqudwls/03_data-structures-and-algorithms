package com.jinosoft.section02.list;

/**
 * 단일 연결 리스트(LinkedList) 구현 클래스
 * - head: 첫 번째 노드
 * - tail: 마지막 노드
 * - size: 전체 노드 개수
 */
public class MyLinkedList<T> {

  private Node<T> head;
  private Node<T> tail;
  private int size;

  /* 생성자 */
  public MyLinkedList() {
    this.head = null;
    this.tail = null;
    this.size = 0;
  }

  /* 내부 정적 클래스: 노드 구조 정의 */
  private static class Node<T> {
    T data;        // 실제 데이터
    Node<T> next;  // 다음 노드를 가리키는 포인터

    public Node(T data) {
      this.data = data;
      this.next = null;
    }
  }

  /**
   * 연결 리스트의 끝에 요소를 추가
   * 시간 복잡도: O(1)
   *
   * @param element 추가할 값
   */
  public void add(T element) {
    Node<T> newNode = new Node<>(element);

    // 리스트가 비어 있는 경우
    if (head == null) {
      head = newNode;
      tail = newNode;
    } else {
      // tail 다음에 추가하고 tail 갱신
      tail.next = newNode;
      tail = newNode;
    }

    size++;
  }

  /**
   * 특정 위치에 요소 삽입
   * 시간 복잡도: O(n)
   *
   * @param index   삽입할 위치
   * @param element 삽입할 데이터
   */
  public void add(int index, T element) {
    checkAddIndex(index);   // index 검증

    Node<T> newNode = new Node<>(element);

    // 맨 앞에 삽입하는 경우
    if (index == 0) {
      newNode.next = head;
      head = newNode;

      // size 0일 때 head=tail 둘 다 newNode
      if (size == 0) {
        tail = newNode;
      }
    }
    // 맨 뒤에 삽입: add(element)와 동일
    else if (index == size) {
      tail.next = newNode;
      tail = newNode;
    }
    // 중간 삽입
    else {
      Node<T> prev = getNode(index - 1); // 이전 노드 탐색
      newNode.next = prev.next;
      prev.next = newNode;
    }

    size++;
  }

  /**
   * 특정 인덱스의 요소 반환
   * 시간 복잡도: O(n)
   *
   * @param index 조회할 인덱스
   * @return 해당 위치의 데이터
   */
  public T get(int index) {
    checkIndex(index);
    return getNode(index).data;
  }

  /**
   * 특정 위치의 요소 삭제 후 반환
   * 시간 복잡도: O(n)
   *
   * @param index 삭제할 위치
   * @return 삭제된 요소의 값
   */
  public T remove(int index) {
    checkIndex(index);

    T removedValue;

    // 첫 번째 노드 삭제
    if (index == 0) {
      removedValue = head.data;
      head = head.next;

      // 삭제 후 리스트가 비게 되는 경우
      if (head == null) {
        tail = null;
      }
    }
    // 중간 또는 끝 삭제
    else {
      Node<T> prev = getNode(index - 1);
      Node<T> target = prev.next;
      removedValue = target.data;

      prev.next = target.next;

      // 마지막 노드를 삭제하는 경우 tail 갱신
      if (target == tail) {
        tail = prev;
      }
    }

    size--;
    return removedValue;
  }

  /**
   * index 위치의 노드 반환 (내부용)
   * 시간 복잡도 O(n)
   */
  private Node<T> getNode(int index) {
    Node<T> current = head;

    for (int i = 0; i < index; i++) {
      current = current.next;
    }

    return current;
  }

  /**
   * add(index)용 index 체크
   * size 위치는 허용됨 (맨 뒤 삽입)
   */
  private void checkAddIndex(int index) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException("인덱스 범위 초과: " + index);
    }
  }

  /**
   * get/remove용 index 체크
   */
  private void checkIndex(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("인덱스 범위 초과: " + index);
    }
  }

  /**
   * 리스트의 현재 크기 반환
   */
  public int size() {
    return size;
  }

  /**
   * 디버깅이나 출력용: 리스트 전체를 문자열로 변환
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("[");
    Node<T> current = head;

    while (current != null) {
      sb.append(current.data);
      if (current.next != null) sb.append(", ");
      current = current.next;
    }

    sb.append("]");
    return sb.toString();
  }
}
