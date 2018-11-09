package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ActionPeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonGetter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class JsonFinalsPeriod extends JsonTransformer<ActionPeriod> {
    @Override
    public ActionPeriod transform(JsonElement object)
    {
        if( object.isJsonArray() )
        {
            List<Date> dates = getDates(object.getAsJsonArray());
            if( dates != null && dates.size() != 0 )
            {
                Collections.sort(dates, new Comparator<Date>() {
                    @Override
                    public int compare(Date o1, Date o2) {
                        return o1.compareTo(o2);
                    }
                });
                return new ActionPeriod(onWeekBefore(dates.get(0)),dates.get(dates.size() - 1));
            }
        }
        return null;
    }

    private Date onWeekBefore(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -7);
        return cal.getTime();
    }

    private List<Date> getDates(JsonArray asJsonArray)
    {
        List<Date> list = new ArrayList<>();
        for( int i = 0; i < asJsonArray.size(); i++)
        {
            JsonElement element = asJsonArray.get(i);
            Date date = getDate(element);
            if( date == null ) return null;
            Log.d("DATE","Read date " + date.toString());
            list.add(date);
        }
        return list;
    }

    private Date getDate(JsonElement element)
    {
        if( element.isJsonObject() )
        {
            JsonGetter getter = new JsonGetter(element.getAsJsonObject());
            String dateString = getter.asString(JsonKeys.DATE_START_WEEK);
            if( dateString != null )
            {
                try {
                    return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(dateString);
                } catch (ParseException ignore) {}
            }
        }
        return null;
    }
}
