/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.common.runtime;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.Properties;

/**
 * Instances of this class represent an object that is shared amongst classes
 * in the same JVM.
 *
 * @author Blair Butterworth
 */
public class JvmSingleton
{
    private String name;

    public JvmSingleton(String name) {
        this.name = name;
    }

    public JvmSingleton(String name, Observer observer) {
        this.name = name;
        addObserver(observer);
    }

    public void addObserver(Observer observer) {
        List<WeakReference<Observer>> observers = getObservers();
        observers.add(new WeakReference<>(observer));
        setObservers(observers);
    }

    public void notifyObservers() {
        for (WeakReference<Observer> reference: getObservers()){
            Observer observer = reference.get();
            notifyObserver(observer);
        }
    }

    private void notifyObserver(Observer observer){
        try {
            if (observer != null) {
                observer.update(null, null);
            }
        }
        catch (Throwable error) {
            error.printStackTrace(); //TODO: log error
        }
    }

    @SuppressWarnings("unchecked")
    private List<WeakReference<Observer>> getObservers() {
        synchronized (ClassLoader.getSystemClassLoader()) {
            Properties systemProperties = System.getProperties();
            List<WeakReference<Observer>> observers = (List)systemProperties.get(name);
            return observers != null ? observers : new ArrayList<>();
        }
    }

    private void setObservers(List<WeakReference<Observer>> observers) {
        synchronized (ClassLoader.getSystemClassLoader()) {
            observers = removeEmpty(observers);
            Properties systemProperties = System.getProperties();
            systemProperties.put(name, observers);
        }
    }

    private List<WeakReference<Observer>> removeEmpty(List<WeakReference<Observer>> observers) {
        List<WeakReference<Observer>> result = new ArrayList<>(observers.size());
        for (WeakReference<Observer> reference: observers){
            if (reference.get() != null) {
                result.add(reference);
            }
        }
        return result;
    }
}