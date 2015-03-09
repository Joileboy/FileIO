package com.example.jong.fileio;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Jong on 2015-03-09.
 */
public class Tab1Activity extends Activity {
    EditText edit;
    String sdCardPath;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///////////////////////////TAB 1 Button and var////////////////////////////////////
        edit = (EditText) findViewById(R.id.editText);
        findViewById(R.id.Save).setOnClickListener(clickListener);
        findViewById(R.id.load).setOnClickListener(clickListener);
        findViewById(R.id.Loadr).setOnClickListener(clickListener);
        findViewById(R.id.Delete).setOnClickListener(clickListener);

        ////////////////////////////sd Card//////////////////////////////////////////////////
        String ext= Environment.getExternalStorageState();
        if(ext.equals(Environment.MEDIA_MOUNTED)){
            sdCardPath=Environment.getExternalStorageDirectory().getAbsolutePath();
        }else{
            sdCardPath=Environment.MEDIA_UNMOUNTED;
        }


    }

    Button.OnClickListener clickListener = new View.OnClickListener(){
        public void onClick(View v){
            switch (v.getId()){
                case R.id.Save://Save button
                    try{
                        FileOutputStream fos = openFileOutput("test.txt", Context.MODE_WORLD_READABLE);
                        String str = "Android File IO Test";
                        fos.write(str.getBytes());
                        fos.close();
                        edit.setText("Sucess write file \n 파일 쓰기 성공");
                    } catch (FileNotFoundException e) {
                        Log.i("SAVE", "Fail write file \n 파일 쓰기 실패");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.load://파일 읽기
                    try{
                        FileInputStream fis = openFileInput("test.txt");
                        byte[] data = new byte[fis.available()];
                        while (fis.read(data) != -1){}
                        fis.close();
                        edit.setText(new String(data));
                    }catch (FileNotFoundException fileNotFountError) {
                        edit.setText("File Not Found");
                    }catch(IOException ioe){
                        ioe.printStackTrace();
                    }
                    break;
                case R.id.Loadr://리소스 파일 읽기
                    try{
                        InputStream fres = getResources().openRawResource(R.raw.myfile);
                        InputStreamReader reader = new InputStreamReader(fres,"UTF-8");
                        char[] data = new char[fres.available()];
                        while(reader.read(data) !=-1){}
                        fres.close();
                        edit.setText(new String(data));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    break;
                case R.id.Delete://삭제
                    if(deleteFile("test.txt")){
                        edit.setText("성공적으로 삭제되었습니다. \n file Delete sucess");
                    }else{
                        edit.setText("파일 삭제 실패 하였습니다. \n Fail to Delete file");
                    }
                    break;
                default:
                    break;
            }
        }
    };
}