package com.mobile.bookingpitch_user.config;

import android.content.Context;
import android.util.Log;

import com.mobile.bookingpitch_user.R;

import java.util.ArrayList;
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
                s += mapSpan.get(ls.get(i)) + ", ";
            }
        }

        return s.equals("") ? "" : s.substring(0, s.length() - 2);
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

//    public String getCityFromList(Context context, String s) {
//        List<String> z = new ArrayList<>();
//        int j = 0;
//        for (int i = 0 ; i < s.length(); i++){
//            if (s.charAt(i) == '/'){
//                z.add(s.substring(j,i));
//                j = i++;
//            }
//        }
//        String r = "";
//        if(z.size() >0) {
//            Log.e(Dao.class.getName(), z.get(z.size() - 1));
//            Map<String, String> m = getSpanPitch(context);
//
//            for (int i = 0; i < z.size(); i++) {
//                r += m.get(z.get(i));
//                r += "/ ";
//            }
//        }
//        return r.length() >2 ? r.substring(0, r.length() -2) : "";
//    }

}
