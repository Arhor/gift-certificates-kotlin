package com.epam.esm.gift.model

import java.time.temporal.TemporalAccessor

interface Auditable<T : TemporalAccessor> {
    var dateTimeCreated: T?
    var dateTimeUpdated: T?
}