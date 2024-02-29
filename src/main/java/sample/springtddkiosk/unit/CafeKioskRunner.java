package sample.springtddkiosk.unit;

import sample.springtddkiosk.unit.beverage.Americano;
import sample.springtddkiosk.unit.beverage.Latte;

public class CafeKioskRunner {

  public static void main(String[] args) {
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano());
    System.out.println("아메리카노 추가");

    cafeKiosk.add(new Latte());
    System.out.println("라떼 추가");

    final int totalPrice = cafeKiosk.calculateTotalPrice();
    System.out.println("총주문가격=" + totalPrice);
  }

}
