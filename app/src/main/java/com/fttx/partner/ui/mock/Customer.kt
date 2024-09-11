package com.fttx.partner.ui.mock

import com.fttx.partner.domain.model.Customer

fun getCustomer() = Customer(
    id = 1L,
    name = "name",
    address = "address",
    phone = "phone",
    lat = 0.0,
    long = 0.0,
)