package com.mph.records;

import com.mph.annotations.FieldLength;

public record BankTransaction(
    @FieldLength(20) String transactionId,
    @FieldLength(14) String transactionDateTime,
    @FieldLength(10) String transactionType,
    @FieldLength(10) String transactionAmount,
    @FieldLength(3) String currency,
    @FieldLength(10) String transactionStatus,
    @FieldLength(30) String merchantName,
    @FieldLength(15) String category,
    @FieldLength(10) String transactionMethod,
    @FieldLength(5) String transactionIdCode,
    @FieldLength(5) String transactionMode
) {}
