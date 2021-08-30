package com.mobile.bookingpitch_user.model.CityAndDistrict;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityHunter {
    private List<HDDataCity> lsCity = new ArrayList<>();
    private List<HDDataDistrict> lsDistrict = new ArrayList<>();
    private Map<String, String> mapCity = new HashMap<>();
    private Map<String, String> mapDistrict = new HashMap<>();

    public CityHunter(Context context) {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences("DistrictAndCity", context.MODE_PRIVATE);
        String cityGson = sharedPreferences.getString("listCity", null);
        String districtGson = sharedPreferences.getString("listDistrict", null);
        if (cityGson != null) {
            Type type = new TypeToken<List<HDDataCity>>() {
            }.getType();
            lsCity = gson.fromJson(cityGson, type);
            mapCity.putAll(createMapCity());
        }
        if (districtGson != null) {
            Type type = new TypeToken<List<HDDataDistrict>>() {
            }.getType();
            lsDistrict = gson.fromJson(districtGson, type);
            mapDistrict.putAll(createMapDistrict());
            Log.d("TAG", "CityHunter: size district: " + lsDistrict.size());
        }
    }

    public Map<String, String> getListCity() {
        return mapCity;
    }

    public Map<String, String> getListDistrict() {
        return mapDistrict;
    }

    private Map<String, String> createMapDistrict() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < lsDistrict.size(); i++) {
            map.put(lsDistrict.get(i).getIdCity(), lsDistrict.get(i).getNameCity());
        }
        return map;
    }

    private Map<String, String> createMapCity() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < lsCity.size(); i++) {
            map.put(lsCity.get(i).getIdCity(), lsCity.get(i).getNameCity());
        }
        return map;
    }

    public List<HDDataCity> getListDataCity(){
        return lsCity;
    }

    public List<HDDataDistrict> getListDistrictFromParentID(String idOfParent){
        List<HDDataDistrict> list = new ArrayList<>();
        for (int i = 0; i < lsDistrict.size(); i++){
            if(lsDistrict.get(i).getCitParent().equals(idOfParent)){
                list.add(lsDistrict.get(i));
            }
        }
        return list;
    }
}