package net.gsantner.markor.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    @SuppressLint("StaticFieldLeak")
    private ImageView _imageview;

    public DownloadImageTask(ImageView imageview) {
        _imageview = imageview;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap dlbitmap = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            dlbitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return dlbitmap;
    }

    protected void onPostExecute(Bitmap result) {
        _imageview.setImageBitmap(result);
        _imageview = null;
    }
}