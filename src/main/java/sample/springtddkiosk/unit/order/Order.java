package sample.springtddkiosk.unit.order;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sample.springtddkiosk.unit.beverage.Beverage;

@Getter
@RequiredArgsConstructor
public class Order {

  private final LocalDateTime orderDateTime;
  private final List<Beverage> beverages;

}
