package org.rownski.domain;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class TimeRangeToll {

  LocalTime from;
  LocalTime to;
  int fee;
}
