package com.mph.records;

import com.mph.annotations.FieldLength;

public record BankRecord<T>(
    @FieldLength(20) String recordType,
    T data
) {}
