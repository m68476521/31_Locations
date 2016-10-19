package com.bignerdrach.android.locatr;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class LocatrActivity extends SingleFragmentActivity {
    private static final int REQUEST_ERROR = 0;

    @Override
    protected Fragment createFragment() {
        return LocatrFragment.newInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//
//        if (errorCode != ConnectionResult.SUCCESS) {
//            Dialog errorDialog = GooglePlayServicesUtil
//                    .getErrorDialog(errorCode, this, REQUEST_ERROR,
//                            new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialog) {
//                                    // leave if service are unavailable
//                                    finish();
//                                }
//                            });
//
//            errorDialog.show();
//        }
        int errorCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this, REQUEST_ERROR, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    // Leave if services aren't available
                    finish();
                }
            });
            errorDialog.show();
        }
        // Next, check on "dangerous" permissions - to wit, location data availability.  As of Android 6.0, we need to check
        // at runtime - not  during installation.
        int permissionCheck = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            Log.i("", "No permission");
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        }
    }

}
