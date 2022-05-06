package org.sfcmn.basics;

import java.util.Arrays;
import java.util.Random;

enum Season {
  SPRING(0),
  SUMMER(1),
  AUTUMN(2),
  WINTER(3);

  private final int index;

  Season(int index) {
    this.index = index;
  }

  public static Season at(int index) {
    return Arrays
        .stream(Season.values())
        .filter(season -> season.index == index)
        .findFirst()
        .orElse(Season.SPRING);
  }
}

public class SwitchExpression {
  public static void main(String[] args) {
    Season season = Season.at(new Random().nextInt(4));
    switch (season) {
      case SPRING, AUTUMN -> System.out.println("天气很温暖");
      case SUMMER         -> System.out.println("天气很炎热");
      case WINTER         -> System.out.println("天气很寒冷");
    }
    String feeling = switch (season) {
      case SPRING, AUTUMN -> "舒服";
      case SUMMER         -> "热死了";
      case WINTER         -> "冻死了";
    };
    System.out.println(feeling);
  }
}
