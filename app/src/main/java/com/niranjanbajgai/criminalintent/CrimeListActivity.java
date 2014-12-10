package com.niranjanbajgai.criminalintent;

import android.support.v4.app.Fragment;;

/**
 * Created by niranjanbajgai on 14-10-23.
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment(){

        return new CrimeListFragment();
    }

}
