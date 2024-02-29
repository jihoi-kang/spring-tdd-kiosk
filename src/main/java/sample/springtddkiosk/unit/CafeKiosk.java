package sample.springtddkiosk.unit;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import sample.springtddkiosk.unit.beverage.Beverage;
import sample.springtddkiosk.unit.order.Order;

@Getter
public class CafeKiosk {

    public static final LocalTime SHOP_OPEN_DATE = LocalTime.of(10, 0);
    public static final LocalTime SHOP_CLOSE_DATE = LocalTime.of(22, 0);

    private final List<Beverage> beverages = new ArrayList<>();

    public void add(Beverage beverage) {
        beverages.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다");
        }
        for (int i = 0; i < count; i++) {
            beverages.add(beverage);
        }
    }

    public void remove(Beverage beverage) {
        beverages.remove(beverage);
    }

    public void clear() {
        beverages.clear();
    }

    public int calculateTotalPrice() {
        int totalPrice = 0;
        for (Beverage beverage : beverages) {
            totalPrice += beverage.getPrice();
        }
        return totalPrice;
    }

    public Order createOrder() {
        LocalTime now = LocalTime.now();
        if (now.isBefore(SHOP_OPEN_DATE) || now.isAfter(SHOP_CLOSE_DATE)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의해주세요.");
        }
        return new Order(LocalDateTime.now(), beverages);
    }

    public Order createOrder(LocalTime now) {
        if (now.isBefore(SHOP_OPEN_DATE) || now.isAfter(SHOP_CLOSE_DATE)) {
            throw new IllegalArgumentException("주문 시간이 아닙니다. 관리자에게 문의해주세요.");
        }
        return new Order(LocalDateTime.now(), beverages);
    }
}
