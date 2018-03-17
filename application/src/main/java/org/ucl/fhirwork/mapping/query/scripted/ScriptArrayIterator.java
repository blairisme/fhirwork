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

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Instances of this class iterate over the contents of a Javascript Array,
 * returned from a user provided mapping script.
 *
 * @param <T> the type of elements returned by the iterator
 *
 * @author Blair Butterworth
 */
public class ScriptArrayIterator<T> implements Iterator<T>
{
    private int index;
    private int length;
    private ScriptArray<T> array;

    public ScriptArrayIterator(ScriptArray<T> array) {
        this.index = 0;
        this.length = array.length();
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return index < length;
    }

    @Override
    public T next() {
        if (index < length) {
            return array.get(index++);
        }
        throw new NoSuchElementException();
    }
}