package com.boenci.hotbul.boencistream;


import com.dooo.stream.DoooStream;

public class BoenciStream {
    private android.content.Context context;
    private java.lang.String code;

    public BoenciStream(@androidx.annotation.NonNull android.content.Context context, java.lang.String code, OnInitialize onInitialize) { /* compiled code */ }

    public void find(java.lang.String source, java.lang.String url, boolean download, OnTaskCompleted onComplete) { /* compiled code */ }

    public static interface OnTaskCompleted {
        void onSuccess(java.util.List<StreamList> list);

        void onError();
    }

    public static interface OnInitialize {
        void onSuccess(BoenciStream boenciStream);

        void onError(java.lang.RuntimeException e);
    }
}