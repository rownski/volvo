package org.rownski.domain;

import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CongestionTaxCalculator {

  private static Map<Vehicle, Integer> tollFreeVehicles = new HashMap<>();

  static {
    tollFreeVehicles.put(Vehicle.MOTORBIKE, 0);
    tollFreeVehicles.put(Vehicle.TRACTOR, 1);
    tollFreeVehicles.put(Vehicle.EMERGENCY, 2);
    tollFreeVehicles.put(Vehicle.DIPLOMAT, 3);
    tollFreeVehicles.put(Vehicle.FOREIGN, 4);
    tollFreeVehicles.put(Vehicle.MILITARY, 5);
  }

  public int getTax(Vehicle vehicle, LocalDateTime[] dates) {

    LocalDateTime intervalStart = dates[0];
    int totalFee = 0;
    int currentFee;
    int biggestIntervalFee = getTollFee(intervalStart, vehicle);
    totalFee += biggestIntervalFee;
    for (int i = 0; i < dates.length; i++) {
      long differenceInMinutes = ChronoUnit.MINUTES.between(intervalStart, dates[i]);
      currentFee = getTollFee(dates[i], vehicle);
      if (differenceInMinutes <= 60) {
        totalFee -= biggestIntervalFee;
        if (currentFee > biggestIntervalFee) {
          biggestIntervalFee = currentFee;
        }
      } else {
        biggestIntervalFee = currentFee;
        intervalStart = dates[i];
      }
      totalFee += biggestIntervalFee;
    }

    if (totalFee > 60) totalFee = 60;
    return totalFee;
  }

  private boolean IsTollFreeVehicle(Vehicle vehicle) {
    if (vehicle == null) return false;
    return tollFreeVehicles.containsKey(vehicle);
  }

  public int getTollFee(LocalDateTime date, Vehicle vehicle) {

    if (IsTollFreeDate(date) || IsTollFreeVehicle(vehicle)) return 0;
    var localTime = date.toLocalTime();
    int tollFee = 0;
    if (localTime.isBefore(LocalTime.of(6, 0))
        || localTime.isAfter(LocalTime.of(18, 29, 59))) { // do 6, po 18:30
      tollFee = 0;
    } else if (localTime.isAfter(LocalTime.of(6, 0))
        && localTime.isBefore((LocalTime.of(6, 30)))) { // 06:00–06:29	SEK 8
      tollFee = 8;
    } else if (localTime.isAfter(LocalTime.of(6, 29, 59))
        && localTime.isBefore((LocalTime.of(7, 0)))) { // 06:30–06:59	SEK 13
      tollFee = 13;
    } else if (localTime.isAfter(LocalTime.of(6, 59, 59))
        && localTime.isBefore((LocalTime.of(8, 0)))) { // 07:00–07:59	SEK 18
      tollFee = 18;
    } else if (localTime.isAfter(LocalTime.of(7, 59, 59))
        && localTime.isBefore((LocalTime.of(8, 30)))) { // 08:00–08:29	SEK 13
      tollFee = 13;
    } else if (localTime.isAfter(LocalTime.of(8, 29, 59))
        && localTime.isBefore((LocalTime.of(15, 0)))) { // 08:30–14:59	SEK 8
      tollFee = 8;
    } else if (localTime.isAfter(LocalTime.of(14, 59, 59))
        && localTime.isBefore((LocalTime.of(15, 30)))) { // 15:00–15:29	SEK 13
      tollFee = 13;
    } else if (localTime.isAfter(LocalTime.of(15, 29, 59))
        && localTime.isBefore((LocalTime.of(17, 0)))) { // 15:30–16:59	SEK 18
      tollFee = 18;
    } else if (localTime.isAfter(LocalTime.of(16, 59, 59))
        && localTime.isBefore((LocalTime.of(18, 0)))) { // 17:00–17:59	SEK 13
      tollFee = 13;
    } else if (localTime.isAfter(LocalTime.of(17, 59, 59))) { // 18:00–18:29	SEK 8
      tollFee = 8;
    }
    return tollFee;
  }

  public int getTollFromList(List<TimeRangeToll> timeRangeTolls, LocalTime time) {
    return timeRangeTolls.stream()
        .filter(
            timeRangeToll -> time.isAfter(timeRangeToll.from) && time.isBefore(timeRangeToll.to))
        .findAny()
        .get()
        .fee;
  }

  private Boolean IsTollFreeDate(LocalDateTime date) {

    if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY)
        || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) return true;

    if (date.getMonth().equals(Month.JULY)) return true;
    LocalDate localDate = date.toLocalDate();

    var freeOfChargeDaysOfTheYear =
        List.of(
            LocalDate.of(2013, 1, 1),
            LocalDate.of(2013, 3, 28),
            LocalDate.of(2013, 3, 29),
            LocalDate.of(2013, 4, 1),
            LocalDate.of(2013, 4, 30),
            LocalDate.of(2013, 5, 1),
            LocalDate.of(2013, 5, 8),
            LocalDate.of(2013, 5, 9),
            LocalDate.of(2013, 11, 1),
            LocalDate.of(2013, 12, 24),
            LocalDate.of(2013, 12, 25),
            LocalDate.of(2013, 12, 26),
            LocalDate.of(2013, 12, 31));

    if (freeOfChargeDaysOfTheYear.contains(localDate)) return true;
    return false;
  }
}
