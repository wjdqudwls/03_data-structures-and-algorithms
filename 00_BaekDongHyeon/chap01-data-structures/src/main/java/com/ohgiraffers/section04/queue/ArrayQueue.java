package com.ohgiraffers.section04.queue;

/**
 * 배열 기반 원형 큐(Circular Queue) 구현 클래스.
 * 배열의 처음과 끝이 연결되어 공간 효율성 향상.
 *
 * @param <T> 큐에 저장할 요소 타입
 */
public class ArrayQueue<T> {
  // 데이터를 저장할 내부 배열
  private T[] queue;
  // 큐의 맨 앞 요소 인덱스 (데이터가 나올 위치)
  private int front;
  // 큐의 맨 뒤 요소의 다음 인덱스 (데이터가 들어갈 위치)
  private int rear;
  // 큐에 저장된 요소 개수
  private int size;
  // 기본 초기 용량
  private static final int INITIAL_CAPACITY = 10;

  /**
   * 기본 생성자: 큐를 기본 용량으로 초기화.
   */
  @SuppressWarnings("unchecked")
  public ArrayQueue() {
    queue = (T[]) new Object[INITIAL_CAPACITY];
    front = 0; // 맨 앞 인덱스 초기화
    rear = 0;  // 맨 뒤 인덱스 초기화
    size = 0;  // 크기 초기화
  }

  /**
   * 큐의 맨 뒤에 요소 추가 (offer/enqueue).
   * 시간 복잡도: O(1) 평균, O(n) 배열 크기 재조정 시.
   *
   * @param data 추가 데이터
   */
  public void offer(T data) {
    // 1. 큐가 가득 찼는지 확인합니다.
    if (size == queue.length) {
      resize(); // 가득 찼으면 배열 크기 확장
    }
    // 2. rear 위치에 데이터를 삽입합니다.
    queue[rear] = data;
    // 3. rear 인덱스를 다음 위치로 이동시킵니다.
    //    모듈러(%) 연산을 사용하여 배열의 끝에 도달하면 다시 맨 앞으로 돌아오게 합니다. (원형 구조)
    //    예: rear가 9이고 배열 길이가 10이면, (9 + 1) % 10 = 0 이 되어 rear는 0이 됩니다.
    rear = (rear + 1) % queue.length;
    // 4. 큐의 크기를 1 증가시킵니다.
    size++;
  }

  /**
   * 큐의 맨 앞 데이터를 제거하고 반환합니다. (poll/dequeue)
   * 시간 복잡도: O(1)
   *
   * @return 제거된 맨 앞 데이터, 큐가 비어있으면 null
   */
  public T poll() {
    // 1. 큐가 비어있는지 확인합니다.
    if (isEmpty()) {
      return null;
    }
    // 2. front 위치의 데이터를 임시 변수에 저장합니다.
    T data = queue[front];
    // 3. 해당 위치의 데이터를 null로 만들어 메모리를 해제합니다.
    queue[front] = null;
    // 4. front 인덱스를 다음 위치로 이동시킵니다.
    //    모듈러(%) 연산을 사용하여 원형 구조를 유지합니다.
    front = (front + 1) % queue.length;
    // 5. 큐의 크기를 1 감소시킵니다.
    size--;
    // 6. 제거된 데이터를 반환합니다.
    return data;
  }

  /**
   * 큐의 맨 앞 데이터를 제거하지 않고 반환합니다. (peek)
   * 시간 복잡도: O(1)
   *
   * @return 맨 앞 데이터, 큐가 비어있으면 null
   */
  public T peek() {
    return isEmpty() ? null : queue[front];
  }

  /**
   * 큐가 비어있는지 확인합니다.
   *
   * @return 큐가 비어있으면 true, 아니면 false
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * 큐에 저장된 요소의 개수를 반환합니다.
   *
   * @return 큐의 크기
   */
  public int size() {
    return size;
  }

  /**
   * 큐의 모든 요소를 제거하여 초기화합니다.
   */
  public void clear() {
    // 배열의 모든 요소를 순회하며 null로 설정합니다.
    for (int i = 0; i < queue.length; i++) {
      queue[i] = null;
    }
    size = 0;
    front = 0;
    rear = 0;
  }

  /**
   * 큐의 내용을 문자열로 반환합니다. (디버깅용)
   *
   * @return 큐의 문자열 표현
   */
  @Override
  public String toString() {
    if(isEmpty()) {
      return "ArrayQueue[]";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("ArrayQueue[");
    // front부터 시작하여 size만큼 순회하며 요소를 출력합니다.
    // (front + i) % queue.length를 통해 원형 구조의 실제 인덱스를 계산합니다.
    for (int i = 0; i < size; i++) {
      sb.append(queue[(front + i) % queue.length]);
      if (i < size - 1) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }

  /**
   * 큐의 크기가 부족하면 배열의 크기를 두 배로 확장하는 private 헬퍼 메소드입니다.
   * 이 과정에서 원형 구조를 일반적인 선형 구조로 재배치합니다.
   */
  private void resize() {
    // 1. 새로운 용량(기존 용량의 2배)을 계산합니다.
    int newCapacity = queue.length * 2;
    @SuppressWarnings("unchecked")
    // 2. 새로운 용량으로 새 배열을 생성합니다.
    T[] newQueue = (T[]) new Object[newCapacity];
    // 3. 기존 큐의 front부터 순서대로 요소를 새 배열에 복사합니다.
    //    이 과정에서 원형 구조가 풀리고 0번 인덱스부터 채워집니다.
    for (int i = 0; i < size; i++) {
      newQueue[i] = queue[(front + i) % queue.length];
    }
    // 4. 내부 배열의 참조를 새 배열로 교체합니다.
    queue = newQueue;
    // 5. front는 0번, rear는 size 위치로 재설정합니다.
    front = 0;
    rear = size;
  }
}