package com.mph.records;

import com.mph.annotations.FieldLength;

public record BankingCustomer(
    @FieldLength(6) String customerId,
    @FieldLength(20) String name,
    @FieldLength(10) String gender,
    @FieldLength(10) String dob,
    @FieldLength(12) String phone,
    @FieldLength(30) String email,
    @FieldLength(40) String address,
    @FieldLength(15) String nationality,
    @FieldLength(20) String occupation,
    @FieldLength(10) String maritalStatus,
    @FieldLength(19) String registrationDate,
    @FieldLength(15) String customerCategory,
    @FieldLength(12) String incomeLevel,
    @FieldLength(5) String creditScore,
    @FieldLength(15) String preferredBankingService
) {}
