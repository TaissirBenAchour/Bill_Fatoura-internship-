package com.internship.billFatoura;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Activity2Images extends AppCompatActivity implements View.OnClickListener {
    Bitmap bitmapImage;
    final int OK_GO = 1234;
    private TessBaseAPI mTess;
    String path = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton btn_choose_option = (ImageButton) findViewById(R.id.choose_bill);
        btn_choose_option.setOnClickListener(btnChoosebtnPressed);
        String language = "eng";
        path = getFilesDir()+ "/tesseract/";
        mTess = new TessBaseAPI();
        fileExist(new File(path + "tessdata/"));
        mTess.init(path, language);
    }
    public View.OnClickListener btnChoosebtnPressed = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(i, OK_GO);

        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

            Uri chosenImage = intent.getData();
            try {
                 bitmapImage = ToBmp(chosenImage );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ImageView imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmapImage);
    }
    public  Bitmap ToBmp(Uri chosenImage) throws FileNotFoundException {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        BitmapFactory.decodeStream(getContentResolver().openInputStream(chosenImage), null, opt);

        final int REQUIRED_SIZE = 400;

        int width_tmp = opt.outWidth, height_tmp = opt.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
        }
        BitmapFactory.Options opt2 = new BitmapFactory.Options();
        opt2.inSampleSize = scale;
        return BitmapFactory.decodeStream(getContentResolver().openInputStream(chosenImage), null, opt2);
    }
    public void tessImage(View view){

        mTess.setImage(bitmapImage);
        String OCRresult = mTess.getUTF8Text();
        setContentView(R.layout.show_result);
        EditText OCRTextView = (EditText) findViewById(R.id.OCRTextView);
        OCRTextView.setText(OCRresult);
    }

    private void fileExist(File dir) {
        if (!dir.exists()&& dir.mkdirs()){
            cpyFile();
        }
        if(dir.exists()) {
            String datafilepath = path+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                cpyFile();
            }
        }
    }

    private void cpyFile() {
        try {
            String filepath = path + "/tessdata/eng.traineddata";
            AssetManager assetManager = getAssets();

            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

    }
}
