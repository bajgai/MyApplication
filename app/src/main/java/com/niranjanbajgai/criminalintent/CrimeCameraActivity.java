package com.niranjanbajgai.criminalintent;

import android.support.v4.app.Fragment;

/**
 * Created by niranjanbajgai on 14-12-09.
 */
public class CrimeCameraActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeCameraFragment();
    }
}
