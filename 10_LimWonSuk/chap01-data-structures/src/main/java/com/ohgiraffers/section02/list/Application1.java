package com.ohgiraffers.section02.list;

public class Application1 {
    public static void main(String[] args) {
        /* MyArrayList 테스트 */
        MyArrayList<Integer> MyArrayList = new MyArrayList<>(3);

        /* isEmpty() 확인*/
        System.out.println(" 비어잇음?" + MyArrayList.isEmpty());

        MyArrayList.add(10);
        MyArrayList.add(20);
        MyArrayList.add(30);
        MyArrayList.add(40);
       MyArrayList.add(50); // 용량 증가 확인

        /* 중간 삽인 확인 */
        //MyArrayList.add(2, 999);
        MyArrayList.add(2, 999);

        /* isEmpty() 확인*/
        System.out.println(" 비어잇음?" + MyArrayList.isEmpty());

        /* remove() 확인 */
        System.out.println("1번 index 요소 제거 : " + MyArrayList.remove(1) + " 제거됨 ");

        /* get(), size() 확인*/
        for (int i = 0; i < MyArrayList.size(); i++) {
            System.out.println("get("+i+") = " + MyArrayList.get(i));

        }
        System.out.println(MyArrayList);
        System.out.println("========== 종료 ==========");
    }
}
