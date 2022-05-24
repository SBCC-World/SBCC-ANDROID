package com.world.sbcc.activity;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.world.sbcc.R;
import com.world.sbcc.common.CommonUtils;
import com.world.sbcc.listener.HandlerMessageListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import com.bumptech.glide.Glide;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SbccIntroActivity extends AppCompatActivity implements HandlerMessageListener {
    private static final int MESSAGE_INTRO_TIME_TICK = 0x0001;
    private int mCount;
    private Handler mHandler;
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = false;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;


    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private static final int MULTI_PERMISSION = 1000;
    private String[] permissionArray = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE
    };


    private final Handler mHideHandler = new Handler();
    //private ImageView mIntroImageView;
    private VideoView mIntroVideoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_sbcc_intro);
        //mIntroImageView = findViewById(R.id.intro_imageview);
        mIntroVideoView = findViewById(R.id.intro_videoview);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        //delayedHide(100);
    }




    private static class StatusHandler extends Handler {
        private final WeakReference<HandlerMessageListener> mInHandler;

        public StatusHandler(HandlerMessageListener activity) {
            mInHandler = new WeakReference<HandlerMessageListener>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            HandlerMessageListener handler = (HandlerMessageListener)mInHandler.get();
            if (handler == null) return;
            handler.handleMessage(msg);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        initialise();

        getPermissionCheck();
        //Glide.with(this).load(R.drawable.sbcc_intro).into(mIntroImageView);
    }

    private void IntroVideoPlayer() {
        try {
            Resources resources = getResources();
            int id_video = resources.getIdentifier("ssbc_intro_video", "raw", getPackageName());
            Uri videofile = Uri.parse("android.resource://" + getPackageName() + "/" + id_video);
            mIntroVideoView.setVideoURI(videofile);
            mIntroVideoView.start();
            mIntroVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Intent intent = new Intent(SbccIntroActivity.this, SbccMainActivity.class);
                    CommonUtils.safeStartActivity(SbccIntroActivity.this, intent);

                    if (!isFinishing())
                        finish();
                }
            });
            mIntroVideoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                    Intent intent = new Intent(SbccIntroActivity.this, SbccMainActivity.class);
                    CommonUtils.safeStartActivity(SbccIntroActivity.this, intent);

                    if (!isFinishing())
                        finish();
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MULTI_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "승인이 허가되어 있습니다.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "아직 승인받지 않았습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        mHandler.removeMessages(MESSAGE_INTRO_TIME_TICK);
        mCount = 0;
    }

    private void initialise() {
        mHandler = new StatusHandler(this);
        mCount = 0;
    }

    @Override
    public void handleMessage(Message msg) {
        if (msg.what == MESSAGE_INTRO_TIME_TICK) {
            mCount++;

            if (mCount > 3) {
                Intent intent = new Intent(SbccIntroActivity.this, SbccMainActivity.class);
                CommonUtils.safeStartActivity(this, intent);

                if (!isFinishing())
                    finish();
            } else {
                mHandler.sendEmptyMessageDelayed(MESSAGE_INTRO_TIME_TICK, 1000);
            }
        }
    }

    /*
        permission check
     */

    public void getPermissionCheck(){
        try {
            ArrayList <String> permissionOK = new ArrayList();
            ArrayList<String> permissionNO = new ArrayList();
            if(permissionArray.length > 0){
                int check = 0;
                for (String data : permissionArray) {
                    check = ContextCompat.checkSelfPermission(SbccIntroActivity.this, data);
                    if (check != PackageManager.PERMISSION_GRANTED) {
                        permissionNO.add(data);
                    }
                    else {
                        permissionOK.add(data);
                    }
                }
                Log.d("---","---");
                Log.w("//===========//","============");
                Log.d("","\n"+"[퍼미션 허용된 리스트 : "+permissionOK.toString()+"]");
                Log.d("","\n"+"[퍼미션 거부된 리스트 : "+permissionNO.toString()+"]");
                Log.w("//===========//","============");
                Log.d("---","---");
                if(permissionNO.size() > 0){
                    ActivityCompat.requestPermissions(SbccIntroActivity.this, permissionNO.toArray(new String[permissionNO.size()]), MULTI_PERMISSION);
                }
                else {
                    //Toast.makeText(getApplication(),"모든 퍼미션이 허용되었습니다 !!! ",Toast.LENGTH_SHORT).show();
                    //mHandler.sendEmptyMessageDelayed(MESSAGE_INTRO_TIME_TICK, 1000);
                    IntroVideoPlayer();
                }
            }
            else {
                //Toast.makeText(getApplication(),"퍼미션 허용을 확인할 데이터가 없습니다 ... ",Toast.LENGTH_SHORT).show();
                //mHandler.sendEmptyMessageDelayed(MESSAGE_INTRO_TIME_TICK, 1000);
                IntroVideoPlayer();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}