package com.wjdqudwls.section02.list;

/*
 * 배열 기반 동적 배열(ArrayList) 직접 구현한 클래스.
 * 제네릭을 이용해서 모든 타입의 데이터를 저장할 수 있다.
 * */
public class MyArrayList<T> {

  // 내부 데이터 저장 공간 (실제로 데이터를 저장하는 공간)
  private T[] data;

  // 현재 저장된 요소의 개수
  private  int size;

  // 사용자가 리스트의 용량을 지정하지 않을 경우 사용할 기본 값
  private static final int DEFAULT_CAPACITY = 10;

  /*
   * 기본 생성자 -- 초기 용량 10으로 생성
   *
   * @SuppressWarnings("unchecked")
   *  - 해당 부분에서 발생하는 제네릭 관련 안정성 경고를 무시하라고
   *    컴파일러에게 알려주는 어노테이션
   * */

  @SuppressWarnings("unchecked")
  public MyArrayList() {
    // Object[] 생성 후 T[]로 강제 형변환
    // -> Java 에서는 제네릭 배열을 직접 생성할 수 없기 때문이다.
    data = (T[]) new Object[DEFAULT_CAPACITY];
    size = 0; // 저장된 요소 개수 0개
  }

  /*
   * 사용자가 초기 용량을 지정하는 생성자
   * */
  public MyArrayList(int initialCapacity){

    if(initialCapacity < 0 ) {
      throw new IllegalArgumentException("잘못된 용량 : " + initialCapacity);
    }

    data = (T[]) new Object[DEFAULT_CAPACITY];
    size = 0; // 저장된 요소 개수 0개
  }

  /**
   *  리스트 끝에 요소를 추가.
   *  시간 복잡도 :
   *  - 평균 : O(1)
   *  - 배열 크기 재 조정시 : O(n)
   * @param element
   */
  public void add(T element) {

    // 1. data 배열이 가득 찼는지 확인
    if(data.length == size){
      // 2. 배열의 크기를 2배 증가 시키기
      resize();
    }

    // 끝에 요소를 추가 후 size 1증가
    data[size++] = element;
  }

  /**
   *  지정된 인덱스에 요소 추가
   *  시간 복잡도 : O(n) - 삽입 위치 뒤의 모든 요소를 뒤로 한 칸씩 밀어야 됨
   * @param index
   * @param element
   */
  public void add(int index, T element){

    // 1. index가 data 배열의 유효한 범위인지 확인
    if(index < 0 || index > size) {
      throw new IndexOutOfBoundsException("인덱스 범위를 벗어났습니다.");
    }

    // 2. 배열이 가득 찼다면 사이즈 재조정
    if(size == data.length) resize();

    // 3. 전달 받은 element를 지정된 index에 추가

    // 3-1. 지정된 index 위치부터 뒤로 한 칸씩 미루기
    for(int i = size; i > index; i--){
      data[i] = data[i - 1];
    }

    // 3-2. 지정된 index에 element 대입하기
    data[index] = element;

    // 4. size 1 증가
    size++;
  }

  /**
   * 지정된 인덱스 요소를 반환
   * 시간 복잡도 : O(1)
   * @param index
   * @return
   * @throws IndexOutOfBoundsException 인덱스 범위를 벗어났습니다
   */
  public T get(int index) {

    // 1. index가 data 배열의 유효한 범위인지 확인
    if(index < 0 || index > size) {
      throw new IndexOutOfBoundsException("인덱스 범위를 벗어났습니다.");
    }

    // 2. 해당 인덱스 요소 반환
    return data[index];
  }

  /**
   * 저장된 요소의 개수 반환
   * @return size
   */
  public int size() {
    return size;
  }

  /**
   * 리스트가 비어 있는지 확인
   * @return 비어있으면 true, 아니면 false
   */

  public boolean isEmpty() {
    return size == 0;
  }


  /**
   * 지정된 인덱스의 요소를 리스트에서 제거하고,
   * 제거된 요소를 반환.
   * 시간 복잡도 : O(n) - 제거된 요소 뒤 모든 요소를 한 칸씩 당겨야 됨
   * @param index
   * @return 제거된 요소
   */
  public T remove(int index){
    // 1. index가 data 배열의 유효한 범위인지 확인
    if(index < 0 || index >= size){
      throw new IndexOutOfBoundsException("인덱스 범위를 벗어났습니다.");
    }

    // 2. 제거될 요소를 임시 변수에 저장
    T removedElement = data[index];

    // 3. 제거된 위치 뒤의 요소를 한 칸씩 당김
    for(int i = index; i < size - 1; i++){
      data[i] = data[i+1];
    }

    // 4. 마지막 요소를 null로 변경하고 size 1 감소
    data[--size] = null;

    // 5. 제거된 요소를 반환
    return removedElement;
  }

  /*
   *  - 배열의 용량을 재조정 하기 위한 헬퍼 메서드
   *  - 현재 배열의 크기를 2배로 늘리고 요소를 복사함.
   * */
  private void resize() {

    // 1. 기존 용량의 2배 계산
    int newCapacity = data.length * 2;

    // 2. 새로운 용량의 새 배열을 생성
    T[] newData = (T[]) new Object[newCapacity];

    // 3. 기존 배열의 모든 요소를 새 배열로 복사 ( O(n) )
    System.arraycopy(data,0, newData,0, data.length);

    // 4. data 참조 변수가 새 배열을 참조하도록 함.
    data = newData;
  }

  /**
   * 리스트의 저장된 모든 요소를 하나의 문자열 형태로 반환(디버깅용)
   * ex) [e1, e2, e3]
   * @return
   */
  public String toString(){
    if (size == 0) return "[]";

    StringBuilder sb = new StringBuilder();
    sb.append("[");

    for (int i = 0; i < size; i++) {
      sb.append(data[i]);

      if (i < size - 1 ){
        sb.append(", ");
      }
    }

    sb.append("]");

    return sb.toString();
  }

}