/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.common.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils
{
    public static boolean isSuccessful(int code)
    {
        return (code >= 200 && code <= 299);
    }

    public static boolean pingUrl(String url) {
        return pingUrl(url, 5 * 1000);
    }

    public static boolean pingUrl(String url, int timeout) {
        try {
            HttpURLConnection connection = (HttpURLConnection)new URL(url).openConnection();
            connection.setConnectTimeout(timeout);
            connection.setReadTimeout(timeout);
            connection.getResponseCode();
            return true;
        } catch (IOException exception) {
            return false;
        }
    }

    public static String combineUrl(String root, String path) {

        StringBuilder result = new StringBuilder();
        result.append(root);

        if (!root.endsWith("/") && !path.startsWith("/")){
            result.append("/");
        }
        result.append(path);
        return result.toString();
    }
}
