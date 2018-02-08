/*
 * FHIRWork (c)
 *
 * This work is licensed under the Creative Commons Attribution 4.0
 * International License. To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by/4.0/
 */

package org.ucl.fhirwork.integration.common.http;

import com.google.common.base.Stopwatch;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HttpStatus
{
    public static boolean isSuccessful(int code)
    {
        return (code >= 200 && code <= 299);
    }

    public static boolean isOnline(String url)
    {
        try
        {
            URL siteURL = new URL(url);
            URLConnection urlConnection = siteURL.openConnection();
            HttpURLConnection connection = (HttpURLConnection)urlConnection;
            connection.setRequestMethod("GET");
            connection.connect();
            return true;
        }
        catch (Exception e) {
           return false;
        }
    }

    public static void waitForOnline(String url, long timeout, TimeUnit timeUnit) throws TimeoutException
    {
        Stopwatch stopwatch = Stopwatch.createStarted();

        while (stopwatch.elapsed(timeUnit) < timeout && !isOnline(url)){
            sleep(5 * 1000);
        }
        if (stopwatch.elapsed(timeUnit) > timeout){
            throw new TimeoutException("Server offline: " + url);
        }
    }

    private static void sleep(long time)
    {
        try {
            Thread.sleep(time);
        }
        catch (Exception e) {
        }
    }
}
