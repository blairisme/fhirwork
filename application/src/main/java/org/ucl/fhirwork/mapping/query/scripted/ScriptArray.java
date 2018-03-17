/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.mapping.query.scripted;

import jdk.nashorn.api.scripting.ScriptObjectMirror;

import java.util.Iterator;

/**
 * Instances of this class represent an Javascript Array, returned from a user
 * provided mapping script.
 *
 * @param <T> the type of elements in this array.
 *
 * @author Blair Butterworth
 */
public class ScriptArray<T> implements Iterable<T>
{
    private ScriptObjectMirror objectMirror;

    public ScriptArray(ScriptObjectMirror objectMirror) {
        this.objectMirror = objectMirror;
    }

    public int length() {
        return (int)objectMirror.getMember("0");
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        return (T)objectMirror.getMember(Integer.toString(index + 1));
    }

    @Override
    public Iterator<T> iterator() {
        return new ScriptArrayIterator<>(this);
    }
}
