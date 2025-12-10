package com.google.section06.tree;

import java.util.ArrayList;
import java.util.List;

/*
# 이진 트리(Binary Tree)
- 각 노드가  최대 두 개의 자식(L, R)을 가지는 트리 구조.
## 이진 탐색 트리(Binary Search Tree)
- 이진 트리 구조를 가지면서, 정렬 속성을 만족하는 트리.
- 모든 노드의 왼쪽 서브트리에 있는 모든 노드의 값은 현재 노드의 값보다 작다.
- 모든 노드의 오른쪽 서브트리에 있는 모든 노드의 값은 현재 노드의 값보다 크다.
- 모든 서브트리 또한 이진 탐색 트리다.

- 이러한 정렬 속성 덕분에 탐색, 삽입, 삭제 연산을 효율적으로 수행 가능함.
  (평균 O(log n))
- 하지만 한 쪽으로 편향된 트리는 최악의 경우 O(n)이 될 수 있다.
*/
public class BinarySearchTree<T extends Comparable<T>> {

  // 최상위 노드
  private Node<T> root;

  static class Node<T> {
    T data; //노드에 저장될 데이터
    Node<T> left; //왼쪽 자식 노드
    Node<T> right;  //오른쪽 자식 노드


    public Node(T data) {
      this.data = data;
      this.left = null;
      this.right = null;
    }
  }

  // 기본 생성자
  public BinarySearchTree() {
    this.root = null;
  }

  /**
   * 트리에 새로운 데이터를 삽입하는 메서드
   *
   * @param data
   */
  public void insert(T data) {
    root = insertRec(root, data);
  }

  /**
   * 노드 삽입을 위한 재귀 헬퍼 메서드
   *
   * @param node 현재 탐색 중인 노드
   * @param data 삽입할 데이터
   * @return 삽입 후 서브 트리의 루트 노드, 같은 데이터가 있을 시 해당 노드 반환
   */
  private Node<T> insertRec(Node<T> node, T data) {
    // node가 null인 경우는, root node인 경우이므로, 만들어준다. (즉, 경우의 수는 NIL노드 혹은 초기루트노드)
    if (node == null) {
      return new Node<>(data);
    }

    // 현재 data가 node.data보다 작을 경우 -> 왼쪽 삽입
    if (data.compareTo(node.data) < 0) {
      node.left = insertRec(node.left, data); // 왼쪽을 조회한다
    }
    // 현재 data가 node.data보다 큰 경우 -> 오른쪽 삽입
    else if (data.compareTo(node.data) > 0) {
      node.right = insertRec(node.right, data); // 왼쪽을 조회한다
    }
    // 현재 data가 node.data와 같을 경우 -> 중복이라서 삽입 X

    return node;
  }
  /* ===== 트리 순회 (Traversal) ===== */

  /**
   * 전위 순회 : Root -> L -> R
   * @return
   */
  public List<T> preOrder(){
    List<T> result = new ArrayList<>();
    preOrderRec(root,result);
    return result;
  }

  /**
   * 재귀용 전위 순회 헬퍼 메서드
   * @param node
   * @param result
   */
  private void preOrderRec(Node<T> node, List<T> result){
    if(node != null){
      result.add(node.data); // 현재 노드 값 기록 -> Root Node 방문
      preOrderRec(node.left,result);  // 왼쪽 순회
      preOrderRec(node.right,result); // 오른쪾 순회
    }
  }

  /**
   * 중위 순회 : L -> Root -> R
   * @return
   */
  public List<T> inOrderOrder(){
    List<T> result = new ArrayList<>();
    inOrderRec(root,result);
    return result;
  }

  /**
   * 재귀용 중위 순회 헬퍼 메서드
   * @param node
   * @param result
   */
  private void inOrderRec(Node<T> node, List<T> result){
    if(node != null){
      inOrderRec(node.left,result);  // 왼쪽 순회
      result.add(node.data); // 현재 노드 값 기록 -> Root Node 방문
      inOrderRec(node.right,result); // 오른쪾 순회
    }
  }

  /**
   * 후위 순회 : L  -> R -> Root
   * @return
   */
  public List<T> postOrderOrder(){
    List<T> result = new ArrayList<>();
    postOrderRec(root,result);
    return result;
  }

  /**
   * 재귀용 후위 순회 헬퍼 메서드
   * @param node
   * @param result
   */
  private void postOrderRec(Node<T> node, List<T> result){
    if(node != null){
      postOrderRec(node.left,result);  // 왼쪽 순회
      postOrderRec(node.right,result); // 오른쪾 순회
      result.add(node.data); // 현재 노드 값 기록 -> Root Node 방문
    }
  }

  /**
   * 특정 데이터가 트리 내에 있는지 검색
   * 시간 복잡도
   * - 평균 : O(log n)
   * - 최악 : O(n)
   *
   * @param data
   * @return 존재하면 true, 없으면 false
   *  */
  public boolean search(T data) {
    return searchRec(root,data);
  }

  /**
   * 노드 탐색 헬퍼 메서드
   * @param node 현재 노드
   * @param data 찾을 값
   * @return 찾으면 true, 없으면 false
   * */
  public boolean searchRec(Node<T> node, T data) {
    if (node == null) {
      return false;
    }
    if (data.compareTo(node.data) == 0) {
      return true; // 찾았다
    }

    return data.compareTo(node.data) < 0 ? searchRec(node.left, data) : searchRec(node.right, data);
  }

  public void delete(T data){
    root = deleteRec(root, data);
  }

  /**
   * BST 삭제 재귀 헬퍼 메서드
   * 1) leaf 삭제
   * 2) 자식 하나 있을 때 삭제
   * 3) 자식 둘 있을 때 → 오른쪽 서브트리 최소값을 가져와 대체
   */
  private Node<T> deleteRec(Node<T> node, T data) {
    if (node == null) {
      return null;  // 삭제할 값이 없음
    }

    int cmp = data.compareTo(node.data);

    if (cmp < 0) {
      // 왼쪽으로 탐색
      node.left = deleteRec(node.left, data);
    }
    else if (cmp > 0) {
      // 오른쪽으로 탐색
      node.right = deleteRec(node.right, data);
    }
    else {
      // === 삭제할 노드를 찾은 경우 ===

      // 1) 자식이 없는 경우 (leaf)
      if (node.left == null && node.right == null) {
        return null;
      }

      // 2) 자식이 하나만 있는 경우
      if (node.left == null) {
        return node.right;
      }
      else if (node.right == null) {
        return node.left;
      }

      // 3) 자식이 두 개인 경우
      // 오른쪽 서브트리에서 가장 작은 값 가져오기
      T minVal = minValue(node.right);
      node.data = minVal;
      // 해당 최소값 노드를 삭제해야 함
      node.right = deleteRec(node.right, minVal);
    }
    return node;
  }

  /**
   * 오른쪽 서브트리에서 가장 작은 값을 반환하는 메서드
   */
  private T minValue(Node<T> node) {
    T min = node.data;
    while (node.left != null) {
      node = node.left;
      min = node.data;
    }
    return min;
  }


  public static void main(String[] args) {

    // BinarySearchTree 생성 (Integer 타입)
    BinarySearchTree<Integer> bst = new BinarySearchTree<>();

    // 데이터 삽입
    bst.insert(50);
    bst.insert(30);
    bst.insert(70);
    bst.insert(20);
    bst.insert(40);
    bst.insert(60);
    bst.insert(80);

    // ===== 삽입 후 순회 출력 =====
    System.out.println("=== 전위 순회 (Pre-Order) ===");
    System.out.println(bst.preOrder());

    System.out.println("=== 중위 순회 (In-Order) ===");
    System.out.println(bst.inOrderOrder());

    System.out.println("=== 후위 순회 (Post-Order) ===");
    System.out.println(bst.postOrderOrder());

    System.out.println("=== 데이터 검색 ===");
    System.out.println("40 검색 : " + bst.search(40));
    System.out.println("90 검색 : " + bst.search(90));

    // ===== 삭제 테스트 =====
    System.out.println("\n======== 삭제 테스트 ========");

    System.out.println("[1] Leaf 노드 삭제: 20");
    bst.delete(20);
    System.out.println("In-Order: " + bst.inOrderOrder());

    System.out.println("\n[2] 자식 1개인 노드 삭제: 30");
    bst.delete(30);
    System.out.println("In-Order: " + bst.inOrderOrder());

    System.out.println("\n[3] 자식 2개인 노드 삭제: 50 (루트)");
    bst.delete(50);
    System.out.println("In-Order: " + bst.inOrderOrder());
  }

}