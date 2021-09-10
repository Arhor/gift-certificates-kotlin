package com.epam.esm.gift;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@FunctionalInterface
public interface TimeService {

    @Nonnull
    Instant now();

    @Nonnull
    default LocalDateTime now(@Nullable TimeZone timeZone) {
        var instant = now();
        var zoneId = (
                (timeZone != null)
                        ? timeZone
                        : TimeZone.getDefault()
        ).toZoneId();
        return LocalDateTime.ofInstant(instant, zoneId);
    }
}