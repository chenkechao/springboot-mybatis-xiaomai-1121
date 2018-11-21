package com.niu.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by songben on 15/10/14.
 */
public class MD5 {

    private static final String CHARSET = "UTF-8";

    public static String getMD5(String sourceStr){
        String resultStr = "";
        try {
            byte[] temp = sourceStr.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(temp);
            // resultStr = new String(md5.digest());
            byte[] b = md5.digest();
            char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                    '9', 'A', 'B', 'C', 'D', 'E', 'F'};
            for (byte aB : b) {
                char[] ob = new char[2];
                ob[0] = digit[(aB >>> 4) & 0X0F];
                ob[1] = digit[aB & 0X0F];
                resultStr += new String(ob);
            }
            return resultStr;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5方法
     *
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text) {
        StringBuilder sb = new StringBuilder();
        try {
            byte[] bytes = text.getBytes(CHARSET);
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            bytes = messageDigest.digest();
            for (byte aByte : bytes) {
                if ((aByte & 0xff) < 0x10) {
                    sb.append("0");
                }
                sb.append(Long.toString(aByte & 0xff, 16));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString().toLowerCase();
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String md5) throws Exception {
        String md5Text = md5(text);
        return md5Text.equalsIgnoreCase(md5);
    }

    public static void main(String[] args){
        StringBuffer receipt = new StringBuffer();
//        receipt.append("{")
//            .append("\"signature\"=\"")
//            .append("Am7vrfmY+FJq9g8gJDdYMGWOBJiKUUz80nAHooQFwYEZAL9wdXU7uaMiSZn75JQUjO3XfydLs2bwm9VPoKYKTGcft0LrISl7YNlQAWeVfA62F2E1qgTAGVFoTF1k0o3hJR1D/bLoum3i5PrQiScV90s0V77WVon2+B6vqUtHLsZUAAADVzCCA1MwggI7oAMCAQICCGUUkU3ZWAS1MA0GCSqGSIb3DQEBBQUAMH8xCzAJBgNVBAYTAlVTMRMwEQYDVQQKDApBcHBsZSBJbmMuMSYwJAYDVQQLDB1BcHBsZSBDZXJ0aWZpY2F0aW9uIEF1dGhvcml0eTEzMDEGA1UEAwwqQXBwbGUgaVR1bmVzIFN0b3JlIENlcnRpZmljYXRpb24gQXV0aG9yaXR5MB4XDTA5MDYxNTIyMDU1NloXDTE0MDYxNDIyMDU1NlowZDEjMCEGA1UEAwwaUHVyY2hhc2VSZWNlaXB0Q2VydGlmaWNhdGUxGzAZBgNVBAsMEkFwcGxlIGlUdW5lcyBTdG9yZTETMBEGA1UECgwKQXBwbGUgSW5jLjELMAkGA1UEBhMCVVMwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMrRjF2ct4IrSdiTChaI0g8pwv/cmHs8p/RwV/rt/91XKVhNl4XIBimKjQQNfgHsDs6yju++DrKJE7uKsphMddKYfFE5rGXsAdBEjBwRIxexTevx3HLEFGAt1moKx509dhxtiIdDgJv2YaVs49B0uJvNdy6SMqNNLHsDLzDS9oZHAgMBAAGjcjBwMAwGA1UdEwEB/wQCMAAwHwYDVR0jBBgwFoAUNh3o4p2C0gEYtTJrDtdDC5FYQzowDgYDVR0PAQH/BAQDAgeAMB0GA1UdDgQWBBSpg4PyGUjFPhJXCBTMzaN+mV8k9TAQBgoqhkiG92NkBgUBBAIFADANBgkqhkiG9w0BAQUFAAOCAQEAEaSbPjtmN4C/IB3QEpK32RxacCDXdVXAeVReS5FaZxc+t88pQP93BiAxvdW/3eTSMGY5FbeAYL3etqP5gm8wrFojX0ikyVRStQ+/AQ0KEjtqB07kLs9QUe8czR8UGfdM1EumV/UgvDd4NwNYxLQMg4WTQfgkQQVy8GXZwVHgbE/UC6Y7053pGXBk51NPM3woxhd3gSRLvXj+loHsStcTEqe9pBDpmG5+sk4tw+GK3GMeEN5/+e1QT9np/Kl1nj+aBw7C0xsy0bFnaAd1cSS6xdory/CUvM6gtKsmnOOdqTesbp0bs8sn6Wqs0C9dgcxRHuOMZ2tm8npLUm7argOSzQ==")
//            .append("\";")
//            .append("\"purchase-info\"=\"").append("").append("")
//            .append("ewoJIm9yaWdpbmFsLXB1cmNoYXNlLWRhdGUtcHN0IiA9ICIyMDE0LTAyLTEyIDAwOjQ1OjUzIEFtZXJpY2EvTG9zX0FuZ2VsZXMiOwoJInVuaXF1ZS1pZGVudGlmaWVyIiA9ICJmNzFjODA0YmNkMDkwMDg1ZDE3Y2YwN2UyODA1YzFiMGRmYTA1M2VhIjsKCSJvcmlnaW5hbC10cmFuc2FjdGlvbi1pZCIgPSAiMTAwMDAwMDEwMTI2NTU1MSI7CgkiYnZycyIgPSAiMS4wIjsKCSJ0cmFuc2FjdGlvbi1pZCIgPSAiMTAwMDAwMDEwMTI2NTU1MSI7CgkicXVhbnRpdHkiID0gIjEiOwoJIm9yaWdpbmFsLXB1cmNoYXNlLWRhdGUtbXMiID0gIjEzOTIxOTQ3NTMzNjgiOwoJInVuaXF1ZS12ZW5kb3ItaWRlbnRpZmllciIgPSAiRjYzRTdBMzUtMDQwNi00NDVGLUE1QUEtQ0M5OTc0RDRDQTlCIjsKCSJwcm9kdWN0LWlkIiA9ICJjb20ueWNtLnBubS53aTEiOwoJIml0ZW0taWQiID0gIjgwMjc5MzM1MiI7CgkiYmlkIiA9ICJjb20ueWNtLnBubSI7CgkicHVyY2hhc2UtZGF0ZS1tcyIgPSAiMTM5MjE5NDc1MzM2OCI7CgkicHVyY2hhc2UtZGF0ZSIgPSAiMjAxNC0wMi0xMiAwODo0NTo1MyBFdGMvR01UIjsKCSJwdXJjaGFzZS1kYXRlLXBzdCIgPSAiMjAxNC0wMi0xMiAwMDo0NTo1MyBBbWVyaWNhL0xvc19BbmdlbGVzIjsKCSJvcmlnaW5hbC1wdXJjaGFzZS1kYXRlIiA9ICIyMDE0LTAyLTEyIDA4OjQ1OjUzIEV0Yy9HTVQiOwp9")
//            .append("\";")
//            .append("\"environment\"=\"Sandbox\"")
//            .append("\"pod\"=\"100\"")
//            .append("\"signing-status\"=\"0\"");
//        String result = MD5.md5(receipt.toString());
//        System.out.println(result.length());
//        System.out.println(result);

        System.out.println(MD5.md5("123456"));
    }
}
