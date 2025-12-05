package com.ohgiraffers.section02.list;

/*
* 배열기반 동적 뱅려 (ArrayList) 직접 구현한 클래스.
* 제네릭을 이용해서 모든 타입의 데이터를 저장할 수 있다.
* */
public class MyArrayList<T> {
    // 현재 저장소의 용량
    private T[] data;
    // 현재 저장된 요소의 개수 그안에 들은 수
    private int size;
    // 사용자가 리스트의 용량을 지정하지 않을 경우 사용할 기본 값
    private static final int DEFAULT_CAPACICY = 10;

    /*
    * 기본 생성자 - 초기 용량 10으로 생성
    * @SuppressWarnings ("unchecked") = 해당 부분에서 발생하는 제네릭 관련 안정성 경고를 무시하라고
    *       컴파일러에게 알려주는 이노테이션 (제네릭 관련 안전성 경고 전달)
    * */
    @SuppressWarnings("unchecked")
    public MyArrayList(){
        //data = new Object[DEFAULT_CAPACICY]; // 좌측과 형이달라서 "강제 형변환"이 필요함
        // Object[] 생성후 T[]로 강제 형변환
        // -> Java에서는 제네릭 배열을 직접 생성할 수 없기 떄문이다.
        data = (T[]) new Object[DEFAULT_CAPACICY];
        size = 0; // 저장된 요소 개수 0개
    }

        /*
        * 사용자가 초기 용량을 지정하느 생성자
        * */

    public MyArrayList(int initialCapacity){
        if (initialCapacity < 0){
            throw new IllegalArgumentException("잘못된 용량 : ");
        }
        data = (T[]) new Object[initialCapacity];
        size = 0; // 저장된 요소 개수 0개
    }

    /**
     * 리스트 끝에 요소를 추가
     * 시간 복잡도 : O(1) ~ O(n)
     * - 평균 = 0(1)
     * - 배열 크기 재 조정 시 = O(n)
     * n = 칸의 개수만큼 늘어나니 옮기는 시간이 n의 크기에따라 늘어난다
     * @param element
     */
    public void add(T element){
        // data 배열이 가득 찼는지 확인
        if(data.length == size){
            // 2. 배열의 크기를 2배 증가 시키기
            resize();
        }


   /*     후의 연산 (모든 연산이 끝나고진행)
   data[size] = element;
        size++; */
        // 끝에 요소를 추가 후 size 1증가
        data[size++] = element;
    }

    /**
     * 지정된 인덱스에 요소 추가
     * 시간 복잡도 : O(n) - 삽입 위치 뒤의 모든 요소를 뒤로 한 칸씩 밀어야됨.
     * @param index
     * @param element
     */
    public void add(int index, T element){
        // 1. index가 data 배열의 유효한 범위인지 확인
        // 0 ~ size(length) - 1 -> 배열의 최소값 ~ 최대값(길이)
        if (index < 0 || index > size){
            throw new IndexOutOfBoundsException("인덱스 범위를 벗어났습니다");
        }
        // 2. 배열이 가득 찼다면 사이즈 재조정
        if(size == data.length) resize();

        // 3. 전당 받은 element를 지정된 index에 추가

        // 3-1. 지정된 index위치 부터 뒤로 한 칸씩 미루기
    /*    for(int i = size ; 1 > index ; i-- ){
            data[i] = data[i-1];
        } */
        for(int i = size ; i > index ; i-- ){
            data[i] = data[i-1];
        }

        // 3-2. 지정된 index에 element 대입하기
        data[index] = element;

        // 4. size 1 증가
        size++;
    }

    /**
     * 지정된 인덱스 요소를 반환
     * 시간 복잡도 = O(1)
     * @param index
     * @return 해당 IndexOutOfBoundsException 인덱스 범위를 벗어났습니다
     */
    public T get(int index){
        if (index < 0 || index > size){
            // 1. index가 data 배열의 유효한 범위인지 확인
            // 0 ~ size(length) - 1 -> 배열의 최소값 ~ 최대값(길이)
            throw new IndexOutOfBoundsException("인덱스 범위를 벗어났습니다");
        }
        // 2. 해당 인덱스 요소 반환
        return data[index];
    }

    /**
     *  저장된 요소의 개수 반환
     * @return
     */
    public int size(){
    return size;
    }

    /**
     * 리스트가 비어잇는지 확인
     * @return size가 0 이면 비어있는것
     */
    public boolean isEmpty(){
        return size == 0;
}

    /**
     * 지정된 인덱스의 요소를 리스트에서 제거하고
     * 제거된 요소를 반환
     * 시간 복잡도 = O(n) - 제거된 요소 뒤 모든 요소를 한 칸씩 당겨야됨
     * @param index
     * @return
     */
    public T remove(int index){
        if (index < 0 || index > size){
            // 1. index가 data 배열의 유효한 범위인지 확인
            // 0 ~ size(length) - 1 -> 배열의 최소값 ~ 최대값(길이)
            throw new IndexOutOfBoundsException("인덱스 범위를 벗어났습니다");
        }

        //2. 제거될 요소를 임시 변수에 저장
        T removedElement = data[index];

        // 3. 제거된 위치 뒤의 요소를 한 칸씩 당김
        for(int i = index ; i < size - 1 ; i ++){
            data[i] = data[i + 1];
        }


        // 4. 마지막 요소를 null로 변경하고, size 1 감소
       // data[size - 1] = null;
        data[--size] = null;

        // 5.  제거된 요소를 반환
        return removedElement;

}


    /**
     * - 배열의 용량을 재조정하기 위한 헬퍼(Helper) 메서드
     * - 현재 배열의 크기를 2배로 늘리고 요소를 복사함
     */
    private void resize(){

        // 1. 기존 용량의 2배 계산
        int newCapacity = data.length * 2;

        // 2. 새로운 용량의 새 배열 생성
        T[] newData = (T[]) new Object[newCapacity];

        // 3. 기존 배열의 모든 요소를 새 배열로 복사 (O(n))
        System.arraycopy(data, 0, newData, 0, data.length);

        // 4.  data 참조 변수가 새 배열을 참조하도록 함.
        data = newData;

    }

    /**
     * 리스트의 저장된 모든 요소를 하나의 문자열 형태로 반환 (디버깅용)
     * ex) [10,999,30,40,50]
     * @return
     */
    @Override
    public String toString(){
        if (size == 0) return "[]";

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1){ // 평소에는 (i < size)쉼표를찍고 마지막(size - 1)에는 쉼표를 안찍음
                sb.append(",");
            }
        }

        sb.append("]");
        return sb.toString();
    }
}
