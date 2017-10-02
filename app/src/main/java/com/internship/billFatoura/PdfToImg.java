package com.internship.billFatoura;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.R.attr.path;



public class PdfToImg extends AppCompatActivity {

Button btn;
    Bitmap bmp;
    private TessBaseAPI mTess;
    String path = "";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.pdftoimg);
            String language = "eng";
            path = getFilesDir()+ "/tesseract/";
            mTess = new TessBaseAPI();
            fileExist(new File(path + "tessdata/"));
            mTess.init(path, language);
            Bundle extras = getIntent().getExtras();
            byte[] byteArray = extras.getByteArray("picture");

             bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ImageView image = (ImageView) findViewById(R.id.imageView1);

            image.setImageBitmap(bmp);

        }

    public void tessImage(View view){

        mTess.setImage(bmp);
        String OCRresult = mTess.getUTF8Text();
        setContentView(R.layout.show_result);
        TextView OCRTextView = (TextView) findViewById(R.id.OCRTextView);
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

}