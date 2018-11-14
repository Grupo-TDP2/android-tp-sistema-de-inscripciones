package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

public class AnyNullCheck
{
    public static boolean any(Object... objects)
    {
        for( Object object : objects )
        {
            if( object == null )
                return true;
        }
        return false;
    }
}
