package be.keycu.k3android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class PrefUtils {

    private static String KEY_USER_CODE = "key_user_code";

    static String getUserCodePref(Context context) {
        String value = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null) {
            value = preferences.getString(KEY_USER_CODE, null);
        }
        return value;
    }

    static void setUseCodePref(Context context, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences != null && !TextUtils.isEmpty(value)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(KEY_USER_CODE, value);
            editor.apply();
        }
    }
}
