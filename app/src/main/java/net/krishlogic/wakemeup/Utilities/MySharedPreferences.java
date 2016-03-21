package net.krishlogic.wakemeup.Utilities;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by kvenkat on 2/13/15.
 */

public class MySharedPreferences{

    private static SharedPreferences sharedPreferences;
    private static Editor editor;
    private static String FILENAME="wakemeup";

    public static void init_SP_Instance(Context c) {
        sharedPreferences = c.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
    }

    public static void init_SP_Instance(Context c, String SPFileName){
        sharedPreferences = c.getSharedPreferences(SPFileName, Context.MODE_PRIVATE);
    }

    public static void delete_SP(Context c, String SPFileName){
        sharedPreferences = c.getSharedPreferences(SPFileName, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }

    public static String get_String(String Key, String defaultValue){
        String value = sharedPreferences.getString(Key, defaultValue);
        return value;
    }

    public static int get_Int(String Key, int defaultValue){
        //getString()第二个参数为默认值，如果preference中不存在该key，将返回默认值
        int value = sharedPreferences.getInt(Key, defaultValue);
        return value;
    }

    public static boolean get_Boolean(String Key, boolean defaultValue){
        //getString()第二个参数为默认值，如果preference中不存在该key，将返回默认值
        boolean value = sharedPreferences.getBoolean(Key, defaultValue);
        return value;
    }

    public static float get_Float(String Key, float defaultValue){
        //getString()第二个参数为默认值，如果preference中不存在该key，将返回默认值
        float value = sharedPreferences.getFloat(Key, defaultValue);
        return value;
    }

    public static long get_Long(String Key, long defaultValue){
        //getString()第二个参数为默认值，如果preference中不存在该key，将返回默认值
        long value = sharedPreferences.getLong(Key, defaultValue);
        return value;
    }

    public static Map<String, ?> get_All(){
        //getString()第二个参数为默认值，如果preference中不存在该key，将返回默认值
        Map<String, ?> value = sharedPreferences.getAll();
        return value;
    }

    public static ArrayList<String> get_List(String key) {
        ArrayList<String> list = new ArrayList<String>();
        int size = sharedPreferences.getInt(key+"_size", 0);
        for(int i=0;i<size;i++) {
            list.add(sharedPreferences.getString(key+"_" + i, null));
        }
        return list;
    }

    public static void put_String(String Key, String Value){
        editor = sharedPreferences.edit();//获取编辑器
        editor.putString(Key, Value);  //存储要存储的值
        editor.commit();//提交修改
    }

    public static void put_Boolean(String Key, boolean Value){
        editor = sharedPreferences.edit();//获取编辑器
        editor.putBoolean(Key, Value);  //存储要存储的值
        editor.commit();//提交修改
    }

    public static void put_Float(String Key, float Value){
        editor = sharedPreferences.edit();//获取编辑器
        editor.putFloat(Key, Value);  //存储要存储的值
        editor.commit();//提交修改
    }

    public static void put_Int(String Key, int Value){
        editor = sharedPreferences.edit();//获取编辑器
        editor.putInt(Key, Value);  //存储要存储的值
        editor.commit();//提交修改
    }

    public static void put_Long(String Key, long Value){
        editor = sharedPreferences.edit();//获取编辑器
        editor.putLong(Key, Value);  //存储要存储的值
        editor.commit();//提交修改
    }

    public static void put_List(String key, ArrayList<String> list){
        editor = sharedPreferences.edit();
        editor.putInt(key+"_size",list.size());
        for(int i=0;i<list.size();i++) {
            editor.remove(key+"_" + i);
            editor.putString(key+"_" + i, list.get(i));
        }
        editor.commit();
    }

    public static void clear(Context c, String SPFileName){
        SharedPreferences sharedPreferences = c.getSharedPreferences(SPFileName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();//提交修改
    }
}