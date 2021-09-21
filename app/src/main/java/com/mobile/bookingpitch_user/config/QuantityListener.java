package com.mobile.bookingpitch_user.config;

import com.mobile.bookingpitch_user.model.DateMonth;

import java.util.ArrayList;
import java.util.List;

public interface QuantityListener {
    void onQuantityChange(List<DateMonth> arrayList);
}
