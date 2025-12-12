package com.jinosoft.section02.list;

import java.util.Arrays;

/* 배열 기반의 동적 배열(ArrayList) 직접 구현 클래스
 * 제네릭을 사용하여 모든 타입의 요소를 저장할 수 있다.
 */
public class MyArrayList<T> {

  // 내부 배열(요소 저장 공간)
  private T[] data;

  // 현재 저장된 요소 수
  private int size;

  private static final int DEFAULT_CAPACITY = 10;

  // 기본 생성자
  // 제네릭 배열 생성 안정성 경고 무시
  @SuppressWarnings("unchecked")
  public MyArrayList() {
    data = (T[]) new Object[DEFAULT_CAPACITY];
  }

  // 사용자가 초기 용량을 지정하는 생성자
  // Java는 제네릭 배열 직접 생성 불가 → Object 배열 후 캐스팅
  @SuppressWarnings("unchecked")
  public MyArrayList(int initialCapacity) {
    if (initialCapacity < 0) {
      throw new IllegalArgumentException("잘못된 용량 : " + initialCapacity);
    }
    data = (T[]) new Object[initialCapacity];
  }

  /* 리스트 끝에 요소 추가
   * 평균 시간 복잡도 : O(1)
   * 배열이 가득 찼을 경우 resize()가 실행되면 O(n)
   * @param element 추가할 요소
   */
  public void add(T element) {
    if (size == data.length) {
      resize();
    }
    data[size++] = element;
  }

  // 내부 배열의 용량을 두 배로 증가시키는 헬퍼 메서드
  @SuppressWarnings("unchecked")
  private void resize() {
    int newCapacity = data.length * 2;

    // 혹시 overflow 발생할 경우 대비
    if (newCapacity < data.length) {
      newCapacity = DEFAULT_CAPACITY;
    }

    T[] newData = (T[]) new Object[newCapacity];

    // 기존 배열의 요소 복사 (O(n))
    System.arraycopy(data, 0, newData, 0, size);

    data = newData;
  }

  /* 지정된 위치에 요소 삽입
   * 시간 복잡도 : O(n)
   * 삽입 지점 뒤의 모든 요소를 한 칸씩 뒤로 이동해야 한다.
   *
   * @param index 삽입할 위치
   * @param element 삽입할 요소
   */
  public void add(int index, T element) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException("범위 초과");
    }

    if (size == data.length) {
      resize();
    }

    // 뒤에서부터 한 칸씩 이동
    for (int i = size; i > index; i--) {
      data[i] = data[i - 1];
    }

    data[index] = element;
    size++;
  }

  /* 특정 인덱스의 요소 반환
   * 시간 복잡도 : O(1)
   *
   * @param index 가져올 위치
   * @return 요소 값
   */
  public T get(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("인덱스가 범위를 벗어났습니다.");
    }
    return data[index];
  }

  /* 현재 저장된 요소 개수 반환
   * @return size
   */
  public int size() {
    return size;
  }

  /* 리스트가 비어 있는지 여부 확인
   * @return true → 요소 없음
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /* 특정 인덱스의 요소 삭제
   * 시간 복잡도 : O(n)
   * 삭제 위치 이후의 요소들을 앞으로 당겨야 한다.
   *
   * @param index 삭제할 위치
   * @return 삭제된 요소 값
   */
  public T remove(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("인덱스가 범위를 벗어났습니다: " + index);
    }

    // 삭제할 요소 임시 저장
    T removed = data[index];

    // 뒤 요소들을 한 칸씩 앞으로 이동
    for (int i = index; i < size - 1; i++) {
      data[i] = data[i + 1];
    }

    // 마지막 요소 null 처리 (메모리 누수 방지)
    data[size - 1] = null;

    size--;

    return removed;
  }

  public String toString() {
    if(size == 0) return "[]";

    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < size; i++) {
      sb.append(data[i]);
      if (i != size - 1) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }


}
