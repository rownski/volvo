package org.rownski.controller;

import lombok.Builder;

@Builder
public record TaxResponse(
        int tax
) {
}
