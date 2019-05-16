package e.library;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密，方便后期注册登录（密码进行加密操作）
 */
public class MD5Util {
    /**
     * 加密
     * @param sourceStr
     * @return
     */
    public static String getMD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }

}
