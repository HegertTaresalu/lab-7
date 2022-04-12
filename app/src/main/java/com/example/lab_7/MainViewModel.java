package com.example.lab_7;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.io.InputStream;

public class MainViewModel extends AndroidViewModel {

    public static final String TAG = MainViewModel.class.getName();
    private final MutableLiveData<Bitmap> selectedImage;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.selectedImage = new MutableLiveData<>();
    }


    public MutableLiveData<Bitmap> getSelectedImage() {
        return selectedImage;
    }

    //kustuta commentid ära

    //todo remove comments
    /*
    public Bitmap loadImage(Image image){
        Bitmap bitmap = null;
        InputStream stream;
        try{

            stream = getApplication().getContentResolver().openInputStream();
            bitmap = BitmapFactory.decodeStream(stream);
            stream.close();


        }
        catch (IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }
*/

    //TODO fix stuff and steal some code
    /*
    public final void ImageChosenToThread(Image image){
        new Thread(()-> {
            Bitmap bitmap = loadImage(uri);
            double ratio = (double) bitmap.getWidth()/ (double) bitmap.getHeight();
            Bitmap scaledImage = Bitmap.createScaledBitmap(bitmap,(int)((double)800+ratio), 800, false);


        }).start();

     */
}
