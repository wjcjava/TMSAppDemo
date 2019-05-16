package e.library;

import android.text.TextUtils;
import android.util.Base64;

/**
 * base64编码和解密的封装
 */
public class BaseUtil {
    static String TAG = "";

    /**
     * base64 编码
     * @param uid
     * @return
     */
    public static String makeUidToBase64(long uid) {
        L.d(TAG, "makeUidToBase64 uid = " + uid);
        String strUid = String.valueOf(uid);
        String enUid = new String(Base64.encode(strUid.getBytes(), Base64.DEFAULT));
        L.d(TAG, "makeUidToBase64 enUid = " + enUid);
        return enUid;
    }

    /**
     * base64 解密
     * @param base64Id
     * @return
     */
    public static long getUidFromBase64(String base64Id) {
        long uid = -1l;
        if (!TextUtils.isEmpty(base64Id)) {
            L.d(TAG, "getUidFromBase64 enUID = " + base64Id);
            String result = "";
            if (!TextUtils.isEmpty(base64Id)) {
                result = new String(Base64.decode(base64Id.getBytes(), Base64.DEFAULT));
                uid = Long.parseLong(result);
            }
        }
        L.d(TAG, "getUidFromBase64 uid = " + uid);
        return uid;
    }


}

