package com.fresh.coding.sooatelapi.exceptions;

import java.time.LocalDate;

public record AppError<T>(
        T message,
        LocalDate date,
        int status
) {
}
