package com.niranjanbajgai.criminalintent;


import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

/**
 * Created by niranjanbajgai on 14-12-09.
 */
public class CrimeCameraFragment extends Fragment {
    private static final String TAG = "CrimeCameraFragment";

    private android.hardware.Camera  mCamera;
    private SurfaceView mSurfaceView;


    @Override
    @SuppressWarnings("deprecation")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_camera, container,false);

        Button takePictureButton = (Button)v.findViewById(R.id.crime_camera_takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        mSurfaceView = (SurfaceView)v.findViewById(R.id.crime_camera_surfaceView);
        final SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (mCamera != null) {
                        mCamera.setPreviewDisplay(holder);

                    }
                }
                catch (IOException exception){
                    Log.e(TAG, "Error setting up preview display", exception);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int i, int w, int h) {
                if(mCamera == null) return;
                Camera.Parameters parameters = mCamera.getParameters();
                Camera.Size s = getBestSupportedSize(parameters.getSupportedPictureSizes(), w, h);


                parameters.setPreviewSize(s.width,s.height);
                mCamera.setParameters(parameters);


                try{
                    mCamera.startPreview();
                }
                catch (Exception e){
                    Log.e(TAG, "Could not start preview", e);
                    mCamera.release();
                    mCamera = null;
                }

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if(mCamera != null){
                    mCamera.stopPreview();
                }

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
            mCamera = android.hardware.Camera.open(0);
        }
        else{
            mCamera = android.hardware.Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mCamera != null){
            mCamera.release();
            mCamera = null;
        }

    }

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, int width, int height){
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width*bestSize.height;
        for(Camera.Size s : sizes){
            int area = s.width * s.height;
            if(area > largestArea){
                bestSize = s;
                largestArea = area;
                Log.d("size: ", bestSize.toString());
            }
        }
        return bestSize;
    }
}
