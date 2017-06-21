package com.naicop.naicopsecurityclient.Helpers;

import android.text.TextUtils;

/**
 * Created by Martin on 21/06/2017.
 */

public class HelperFunctions {

    public static boolean validEmail(String email){
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
