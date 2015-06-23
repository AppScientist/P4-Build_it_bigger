package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.util.Log;

import com.udacity.gradle.builtitbigger.EndPointsAysncTask;

/**
 * Created by Abhilash on 23/06/15.
 */
public class TestAsync extends AndroidTestCase{

    public void testClick() {

        String msg=null;
        EndPointsAysncTask endpointsAsyncTask=new EndPointsAysncTask(mContext,null);
        endpointsAsyncTask.execute();
        try {
            msg = endpointsAsyncTask.get();
            Log.d("Async Message",msg);
        }catch (Exception e){

        }
        assertNotNull(msg);
    }
}
