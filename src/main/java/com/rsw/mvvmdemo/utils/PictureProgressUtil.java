package com.rsw.mvvmdemo.utils;


public class PictureProgressUtil {
    public static int currentProgress = 0;
    public static int totalProgress = 0;
    public static int oldProgress = 0;

    public static void initData(int pictureSize) {
        currentProgress = 0;
        oldProgress = 0;
        totalProgress = 100 * pictureSize;
    }

    public static int setCurrentProgress(int progress) {
        if (progress <= oldProgress) {
            progress += 100;
            int trueCount = progress - oldProgress;
            currentProgress += trueCount;
            oldProgress = progress - 100;
        } else {
            int trueCount = progress - oldProgress;
            currentProgress += trueCount;
            oldProgress = progress;
        }

        if (currentProgress >= totalProgress) {
            currentProgress = totalProgress;
        }
        return currentProgress * 100 / totalProgress;
    }
}
