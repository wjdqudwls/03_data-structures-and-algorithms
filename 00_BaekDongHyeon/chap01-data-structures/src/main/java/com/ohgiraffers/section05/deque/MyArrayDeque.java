package com.ohgiraffers.section05.deque;

/**
 * 배열 기반 원형 덱(Circular Deque) 직접 구현 클래스.
 * 양쪽 끝에서 삽입과 삭제 가능한 자료구조.
 *
 * @param <T> 덱에 저장할 요소 타입
 */
public class MyArrayDeque<T> {
  // 덱의 기본 초기 용량.
  private static final int INITIAL_CAPACITY = 10;
  // 데이터 저장 내부 배열.
  private T[] deque;
  // 덱 맨 앞 요소 인덱스.
  private int front;
  // 덱 맨 뒤 요소 인덱스.
  private int rear;
  // 덱에 저장된 요소 개수.
  private int size;

  /**
   * 기본 생성자.
   * 기본 용량으로 덱 초기화.
   */
  @SuppressWarnings("unchecked")
  public MyArrayDeque() {
    deque = (T[]) new Object[INITIAL_CAPACITY];
    front = 0;   // 초기 front는 배열 시작 가리킴.
    rear = -1;   // 초기 rear는 유효한 요소 가리키지 않음 (비어있음).
    size = 0;    // 초기 크기 0.
  }

  /**
   * 덱에 저장된 요소 개수 반환.
   * 시간 복잡도: O(1)
   *
   * @return 덱의 크기
   */
  public int size() {
    return size;
  }

  /**
   * 덱 비어있는지 확인.
   * 시간 복잡도: O(1)
   *
   * @return 덱 비어있으면 true, 아니면 false
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * 덱 맨 앞에 요소 추가 (addFirst).
   * 시간 복잡도: O(1) (평균), O(n) (배열 크기 재조정 시)
   *
   * @param data 추가할 요소
   */
  public void addFirst(T data) {
    // 1. 덱 가득 찼는지 확인.
    if (size == deque.length) {
      resize(); // 가득 찼으면 배열 크기 확장
    }
    // 2. 덱 비어있었다면 front와 rear 0으로 초기화.
    if (isEmpty()) {
      front = 0;
      rear = 0;
    } else {
      // 3. front 인덱스 왼쪽으로 한 칸 이동.
      //    원형 배열이므로 (front - 1 + length) % length 사용, 음수 방지 및 순환 처리.
      front = (front - 1 + deque.length) % deque.length;
    }
    // 4. 새 데이터를 front 위치에 저장.
    deque[front] = data;
    // 5. 덱 크기 1 증가.
    size++;
  }

  /**
   * 덱 맨 뒤에 요소 추가 (addLast).
   * 시간 복잡도: O(1) (평균), O(n) (배열 크기 재조정 시)
   *
   * @param data 추가할 요소
   */
  public void addLast(T data) {
    // 1. 덱 가득 찼는지 확인.
    if (size == deque.length) {
      resize(); // 가득 찼으면 배열 크기 확장
    }
    // 2. 덱 비어있었다면 front와 rear 0으로 초기화.
    if (isEmpty()) {
      front = 0;
      rear = 0;
    } else {
      // 3. rear 인덱스 오른쪽으로 한 칸 이동.
      //    원형 배열이므로 (rear + 1) % length 사용, 순환 처리.
      rear = (rear + 1) % deque.length;
    }
    // 4. 새 데이터를 rear 위치에 저장.
    deque[rear] = data;
    // 5. 덱 크기 1 증가.
    size++;
  }

  /**
   * 덱 맨 앞에서 요소 제거 및 반환 (removeFirst).
   * 시간 복잡도: O(1)
   *
   * @return 제거된 맨 앞 요소
   * @throws IllegalStateException 덱 비어있는 경우 예외 발생
   */
  public T removeFirst() {
    // 1. 덱 비어있는지 확인.
    if (isEmpty()) {
      throw new IllegalStateException("덱 비어있음.");
    }
    // 2. front 위치 데이터 임시 변수에 저장.
    T data = deque[front];
    // 3. 해당 위치 데이터 null로 만들어 메모리 해제.
    deque[front] = null;
    // 4. front 인덱스 오른쪽으로 한 칸 이동 (원형 구조 유지).
    front = (front + 1) % deque.length;
    // 5. 덱 크기 1 감소.
    size--;
    // 6. 덱 비게 되면 front와 rear 초기 상태로 재설정.
    if (size == 0) {
      front = 0;
      rear = -1;
    }
    // 7. 제거된 데이터 반환.
    return data;
  }

  /**
   * 덱 맨 뒤에서 요소 제거 및 반환 (removeLast).
   * 시간 복잡도: O(1)
   *
   * @return 제거된 맨 뒤 요소
   * @throws IllegalStateException 덱 비어있는 경우 예외 발생
   */
  public T removeLast() {
    // 1. 덱 비어있는지 확인.
    if (isEmpty()) {
      throw new IllegalStateException("덱 비어있음.");
    }
    // 2. rear 위치 데이터 임시 변수에 저장.
    T data = deque[rear];
    // 3. 해당 위치 데이터 null로 만들어 메모리 해제.
    deque[rear] = null;
    // 4. rear 인덱스 왼쪽으로 한 칸 이동 (원형 구조 유지).
    rear = (rear - 1 + deque.length) % deque.length;
    // 5. 덱 크기 1 감소.
    size--;
    // 6. 덱 비게 되면 front와 rear 초기 상태로 재설정.
    if (size == 0) {
      front = 0;
      rear = -1;
    }
    // 7. 제거된 데이터 반환.
    return data;
  }

  /**
   * 덱 맨 앞 요소 제거 없이 반환 (getFirst).
   * 시간 복잡도: O(1)
   *
   * @return 맨 앞 요소
   * @throws IllegalStateException 덱 비어있는 경우 예외 발생
   */
  public T getFirst() {
    if (isEmpty()) {
      throw new IllegalStateException("덱 비어있음.");
    }
    return deque[front];
  }

  /**
   * 덱 맨 뒤 요소 제거 없이 반환 (getLast).
   * 시간 복잡도: O(1)
   *
   * @return 맨 뒤 요소
   * @throws IllegalStateException 덱 비어있는 경우 예외 발생
   */
  public T getLast() {
    if (isEmpty()) {
      throw new IllegalStateException("덱 비어있음.");
    }
    return deque[rear];
  }

  /**
   * 덱 크기 부족 시 배열 크기 두 배로 확장하는 private 헬퍼 메소드.
   * 원형 배열 순서 유지하며 요소 새 배열에 복사.
   */
  private void resize() {
    // 1. 새 용량(기존 용량의 2배) 계산.
    int newCapacity = deque.length * 2;
    @SuppressWarnings("unchecked")
    // 2. 새 용량으로 새 배열 생성.
    T[] newDeque = (T[]) new Object[newCapacity];
    // 3. 기존 덱 front부터 시작하여 size만큼 요소 새 배열 0번 인덱스부터 순서대로 복사.
    for (int i = 0; i < size; i++) {
      newDeque[i] = deque[(front + i) % deque.length];
    }
    // 4. 내부 배열 참조를 새 배열로 교체.
    deque = newDeque;
    // 5. front는 0번, rear는 size - 1 위치로 재설정 (선형적 배열처럼).
    front = 0;
    rear = size - 1;
  }

  /**
   * 덱 내용 문자열로 반환 (디버깅용).
   * 시간 복잡도: O(n) - 모든 요소 순회 필요.
   *
   * @return 덱의 문자열 표현
   */
  @Override
  public String toString() {
    if(isEmpty()) {
      return "MyArrayDeque[]";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("MyArrayDeque[");
    for (int i = 0; i < size; i++) {
      // (front + i) % deque.length를 통해 원형 구조 실제 인덱스 계산.
      sb.append(deque[(front + i) % deque.length]);
      if (i < size - 1) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}