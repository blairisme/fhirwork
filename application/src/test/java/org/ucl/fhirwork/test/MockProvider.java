/*
 * FHIRWork (c) 2018 - Blair Butterworth, Abdul-Qadir Ali, Xialong Chen,
 * Chenghui Fan, Alperen Karaoglu, Jiaming Zhou
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 */

package org.ucl.fhirwork.test;

import javax.inject.Provider;

public class MockProvider<T> implements Provider<T>
{
    private T instance;

    public MockProvider(T instance)
    {
        this.instance = instance;
    }

    @Override
    public T get()
    {
        return instance;
    }
}
