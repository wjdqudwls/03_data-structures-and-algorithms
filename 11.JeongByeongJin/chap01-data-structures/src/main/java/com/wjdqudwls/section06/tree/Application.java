package com.wjdqudwls.section06.tree;

/*
* 자바 컬렉션의 TreeSet, TreeMap이 트리 구조를 활용하고 있음
* 내부적으로 레드-블랙 트리로 구현되어 있음
* -> 데이터가 정렬된 상태를 유지하면서도 검색, 삽입, 삭제 시 O(log n) 성능 보장
*
* */

import java.util.TreeMap;

public class Application {
  public static void main(String[] args) {

    /* TreeMap :
    *  - Key를 기준으로 자동 정력되는 맵
    * */

    // 키(String) 오름차순(기본 정렬)으로 정렬되는 TreeMap 생성
    TreeMap<String, String> map = new TreeMap<>();

    map.put("apple", "사과");
    map.put("orange", "오렌지");
    map.put("banana", "바나나");
    map.put("grape", "포도");

    System.out.println("현재 TreeMap : " + map);

    System.out.println("Key == banana : " + map.get("banana"));
    System.out.println("Key == melon : " + map.get("melon"));

    map.remove("orange");
    System.out.println("orange 제거 후 TreeMap : " + map);

    System.out.println("첫번째 Key : " + map.firstKey());
    System.out.println("마지막 Key : " + map.lastKey());



  }
}
