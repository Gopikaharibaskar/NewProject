package com.mph.records;

import com.mph.annotations.FieldLength;

public record FraudTransaction(
    @FieldLength(20) String transactionId,
    @FieldLength(14) String transactionDateTime,
    @FieldLength(10) String transactionType,
    @FieldLength(10) String transactionAmount,
    @FieldLength(3) String currency,
    @FieldLength(20) String merchantId,
    @FieldLength(30) String merchantName,
    @FieldLength(15) String category,
    @FieldLength(10) String fraudStatus,
    @FieldLength(5) String riskScore,
    @FieldLength(10) String transactionMode,
    @FieldLength(3) String chargeback
) {}
