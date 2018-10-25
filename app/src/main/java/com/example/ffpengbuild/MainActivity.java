package com.example.ffpengbuild;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class MainActivity extends AppCompatActivity {
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
        System.loadLibrary("avutil");
        System.loadLibrary("avcodec");
        System.loadLibrary("swresample");
        System.loadLibrary("avformat");
        System.loadLibrary("swscale");
        System.loadLibrary("avfilter");
        System.loadLibrary("avdevice");

    }

    private SurfaceView surfaceView;
    private SurfaceHolder surfaceViewHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean isSign = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                isSign = false;
            }
        }
        if (isSign) {
            play();
        } else {
            ActivityCompat.requestPermissions(this, permissions, 321);
        }
    }

    private void play() {
        System.out.println("start +++++++++");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 321) {
            boolean isSign = true;
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    isSign = false;
                }
            }
            if (isSign) {
                play();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView = (SurfaceView) findViewById(R.id.surface);
        surfaceViewHolder = surfaceView.getHolder();

        surfaceViewHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //获取文件路径，这里将文件放置在手机根目录下
//                String folderurl = Environment.getExternalStorageDirectory().getPath();

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        File file = new File(inputurl);
//                        InputStream in = null;
//                        try {
//                            System.out.println("以字节为单位读取文件内容，一次读一个字节：");
//                            // 一次读一个字节
//                            in = new FileInputStream(file);
//                            int tempbyte;
//                            while ((tempbyte = in.read()) != -1) {
//                                System.out.write(tempbyte);
//                            }
//                            in.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            return;
//                        }
//                    }
//                }).start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Thread thread = new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        final String inputurl =  "/mnt/sdcard/a.mp4";
                        play(inputurl, surfaceViewHolder.getSurface());

                    }
                };
                thread.start();

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native int play(String url, Surface surface);
}
