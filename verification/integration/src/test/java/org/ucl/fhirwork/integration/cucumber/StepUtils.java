package org.ucl.fhirwork.integration.cucumber;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

public class StepUtils
{
    public static void wait(long timeout, TimeUnit timeUnit, Supplier<Boolean> predicate, String message) throws TimeoutException
    {
        Stopwatch stopwatch = Stopwatch.createStarted();

        while (stopwatch.elapsed(timeUnit) < timeout && !predicate.get()){
            sleep(5 * 1000);
        }
        if (stopwatch.elapsed(timeUnit) > timeout){
            throw new TimeoutException(message);
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
