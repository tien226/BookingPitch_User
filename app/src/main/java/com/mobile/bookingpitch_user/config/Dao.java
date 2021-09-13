package com.mobile.bookingpitch_user.config;

import android.content.Context;

import com.mobile.bookingpitch_user.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dao {

    private Map<String, String> getSpanPitch(Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("1", context.getString(R.string.ca) + "1: 7h00 - 9h00");
        map.put("2", context.getString(R.string.ca) + "2: 9h30 - 11h30");
        map.put("3", context.getString(R.string.ca) + "3: 13h00 - 15h30");
        map.put("4", context.getString(R.string.ca) + "4: 16h00 - 18h00");
        map.put("5", context.getString(R.string.ca) + "5: 19h00 - 21h00");

        return map;
    }

    public String getSpanFromListID(Context context, List<String> ls) {
        Map<String, String> mapSpan = getSpanPitch(context);
        String s = "";
        for (int i = 0; i < ls.size(); i++) {
            if (ls.get(i) != null && mapSpan.get(ls.get(i)) != null) {
                s += mapSpan.get(ls.get(i));
            }
        }

        return s;
    }

    public String getSpanPitchID(Context context, String id) {
        switch (id) {
            case "1": {
                return context.getString(R.string.ca) + "1: 7h00 - 9h00";
            }
            case "2": {
                return context.getString(R.string.ca) + "2: 9h30 - 11h30";
            }
            case "3": {
                return context.getString(R.string.ca) + "3: 13h00 - 15h30";
            }
            case "4": {
                return context.getString(R.string.ca) + "4: 16h00 - 18h00";
            }
            case "5": {
                return context.getString(R.string.ca) + "5: 19h00 - 21h00";
            }
        }
        return null;
    }

}
