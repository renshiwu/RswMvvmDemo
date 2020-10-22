package com.rsw.mvvmdemo.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.rsw.mvvmdemo.bean.User;

import java.lang.reflect.Field;


public class MyApplication extends Application {

    private static MyApplication context;
    private static User loginUser;
    //自定义字体路径
    private String fontPath = "fonts/Lobster-1.4.otf";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //捕获崩溃日志，位置在外部存储的LianSou
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
        context = this;
//        TaskDispatcher.init(this);
//        TaskDispatcher dispatcher = TaskDispatcher.createInstance();
//        dispatcher.addTask(new SmartRefreshLayoutTask())
//                .addTask(new X5WebTask())
//                .start();

        //初始化字体替换库
        replaceSystemDefaultFont(context, fontPath);
    }

    public void replaceSystemDefaultFont(Context context, String fontPath) {
        //這里我们修改的是MoNOSPACE,是因为我们在主题里给app设置的默认字体就是monospace，设置其他的也可以
        replaceTypefaceField("MONOSPACE", createTypeface(context, fontPath));
    }
    //通过字体地址创建自定义字体

    private Typeface createTypeface(Context context, String fontPath) {
        return Typeface.createFromAsset(context.getAssets(), fontPath);
    }
    //关键--》通过修改MONOSPACE字体为自定义的字体达到修改app默认字体的目的

    private void replaceTypefaceField(String fieldName, Object value) {
        try {
            Field defaultField = Typeface.class.getDeclaredField(fieldName);
            defaultField.setAccessible(true);
            defaultField.set(null, value);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return context;
    }
}
