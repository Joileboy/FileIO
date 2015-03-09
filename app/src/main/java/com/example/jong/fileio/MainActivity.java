package com.example.jong.fileio;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {

    EditText edit;
    String sdCardPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabHost tabs = (TabHost) findViewById(R.id.tabHost);
        tabs.setup();

        TabHost.TabSpec spec = tabs.newTabSpec("tag1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("File I/O");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Sensor");
        tabs.addTab(spec);

        spec = tabs.newTabSpec("tag3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("BlueTooth");
        tabs.addTab(spec);
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
                        ////////////파일 저장////////////////////////
                        File dir = new File(sdCardPath + "/dir");
                        dir.mkdir();
                        File file = new File(sdCardPath + "/dir/file.txt");
                        /////////////////////////////////////////////
                        FileOutputStream fos = new FileOutputStream(file);
                        //FileOutputStream fos = openFileOutput("test.txt", Context.MODE_WORLD_READABLE);
                        String str = "Android File IO Test";
                        fos.write(str.getBytes());
                        fos.close();
                        edit.setText("Sucess write file \n 파일 쓰기 성공");
                    } catch (FileNotFoundException e) {
                        Log.i("SAVE","Fail write file \n 파일 쓰기 실패");
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
