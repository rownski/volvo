package org.rownski.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.rownski.domain.Vehicle;

import java.time.LocalDateTime;
record TaxRequest (

        Vehicle vehicle,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime[] entrances
){
}
