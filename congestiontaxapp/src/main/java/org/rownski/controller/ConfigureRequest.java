package org.rownski.controller;

import org.rownski.domain.TimeRangeToll;

import java.util.List;

record ConfigureRequest(

        List<TimeRangeToll> timeRangeTolls
){
}
