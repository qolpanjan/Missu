package com.missu.View;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by alimj on 2017/5/4.
 */

public class Type {

    public  static void SetDefaultFont(Context context,String staticTypeFAceFieldName,String FontAssetName){
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),FontAssetName);
        replaceFont(staticTypeFAceFieldName,regular);
    }

    private static void replaceFont(String staticTypeFAceFieldName, final Typeface newTyprface) {
        try {
            final Field staticField = Typeface.class.getDeclaredField(staticTypeFAceFieldName);
            staticField.setAccessible(true);
            staticField.set(null,newTyprface);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}
