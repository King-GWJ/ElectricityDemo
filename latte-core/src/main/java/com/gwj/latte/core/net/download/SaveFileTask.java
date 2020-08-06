package com.gwj.latte.core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.FileUtils;

import com.gwj.latte.core.app.Latte;
import com.gwj.latte.core.net.callback.IRequest;
import com.gwj.latte.core.net.callback.ISuccess;
import com.gwj.latte.core.net.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * @Author: GWJ
 * @Date: 2020/8/6
 * @Explain:
 */
public class SaveFileTask extends AsyncTask<Object, Void, File> {

    private final IRequest iRequest;
    private final ISuccess iSuccess;

    public SaveFileTask(IRequest iRequest, ISuccess iSuccess) {
        this.iRequest = iRequest;
        this.iSuccess = iSuccess;
    }

    @Override
    protected File doInBackground(Object... objects) {
        String downloadDor = (String) objects[0];
        String extension = (String) objects[1];
        final ResponseBody body = (ResponseBody) objects[2];
        String name = (String) objects[3];
        final InputStream inputStream = body.byteStream();
        if (downloadDor == null || downloadDor.equals("")) {
            downloadDor = "down_loads";
        }
        if (extension == null || extension.equals("")) {
            extension = "";
        }
        if (name == null) {
            return FileUtil.writeToDisk(inputStream,downloadDor , extension.toUpperCase(),extension);
        }else{
            return FileUtil.writeToDisk(inputStream, downloadDor,name);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(iSuccess!=null){
            iSuccess.onSuccess(file.getPath());
        }
        if(iRequest!=null){
            iRequest.onRequestEnd();
        }
        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            Latte.getApplicationContext().startActivity(install);
        }
    }


}




















