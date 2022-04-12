package com.example.lab_7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    private static final int PERMISSION_REQUEST_CODE = 200;
    private Spinner spinner;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize variables //TODO GAMING
        spinner = findViewById(R.id.spinner);

        imageView = findViewById(R.id.imageView);
        Button mainThread = findViewById(R.id.MainThreadBtn);
        Button workerThread = findViewById(R.id.workerThreadBtn);
        mainThread.setOnClickListener(view -> workOnMainThread());
        workerThread.setOnClickListener(view -> workOnWorkerThread());
        //method for loading imageList to SPinner
        setUpFileSpinner();
    }

    private void workOnMainThread() {
        Log.i(TAG, "Loading image on main thread");
        Image selectedImage = (Image) spinner.getSelectedItem();
        Uri uri = selectedImage.getUri();
        Bitmap bitmap = loadImage(uri);
        imageView.setImageBitmap(bitmap);
    }

    private void workOnWorkerThread(){
        Image selectedImage = (Image) spinner.getSelectedItem();
        Uri uri = selectedImage.getUri();
        new Thread(() ->{
            Bitmap bitmap = loadImage(uri);
            double ratio = (double) bitmap.getWidth()/ (double) bitmap.getHeight();
            Bitmap scaledImage = Bitmap.createScaledBitmap(bitmap,(int)((double)800+ratio), 800, false);
            runOnUiThread(()->{
                imageView.setImageBitmap(scaledImage);
            });

        }).start();
    }

    private Bitmap loadImage(Uri uri) {
        Bitmap bitmap = null;
        InputStream stream;
        try{
            stream = getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(stream);
            stream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }

    private void setUpFileSpinner(){
        if (checkStoragePermission()){
            Toast.makeText(this,"External permission granted",Toast.LENGTH_SHORT).show();
            List<Image> imageList = MediaStorageUtils.INSTANCE.loadImagesFromMediaStore(this);
            ArrayAdapter<Image> adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,imageList);
            spinner.setAdapter(adapter);


        }
        else{
            requestStoragePermission();
        }

    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }

    private boolean checkStoragePermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Externan Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.w(TAG,"app cant runt without external storage permission");
            }
        }
    }

    /*
    private void setUpObserver() {
        Observer chosenImageObserver = new Observer() {
            @Override
            public void onChanged(Object obj) {
                this.onChanged((Bitmap)obj);
            }
            public final void onChanged(Bitmap newImage){imageView.setImageBitmap(newImage);}
        };
        MainViewModel.().observe(this, chosenImageObserver);
    }



}

*/

}