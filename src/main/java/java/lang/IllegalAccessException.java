/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.lang;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * An IllegalAccessException is thrown when an application tries
 * to reflectively create an instance (other than an array),
 * set or get a field, or invoke a method, but the currently
 * executing method does not have access to the definition of
 * the specified class, field, method or constructor.
 *
 * @author  unascribed
 * @see     Class#newInstance()
 * @see     Field#set(Object, Object)
 * @see     Field#setBoolean(Object, boolean)
 * @see     Field#setByte(Object, byte)
 * @see     Field#setShort(Object, short)
 * @see     Field#setChar(Object, char)
 * @see     Field#setInt(Object, int)
 * @see     Field#setLong(Object, long)
 * @see     Field#setFloat(Object, float)
 * @see     Field#setDouble(Object, double)
 * @see     Field#get(Object)
 * @see     Field#getBoolean(Object)
 * @see     Field#getByte(Object)
 * @see     Field#getShort(Object)
 * @see     Field#getChar(Object)
 * @see     Field#getInt(Object)
 * @see     Field#getLong(Object)
 * @see     Field#getFloat(Object)
 * @see     Field#getDouble(Object)
 * @see     Method#invoke(Object, Object[])
 * @see     Constructor#newInstance(Object[])
 * @since   JDK1.0
 */
public class IllegalAccessException extends ReflectiveOperationException {
    private static final long serialVersionUID = 6616958222490762034L;

    /**
     * Constructs an <code>IllegalAccessException</code> without a
     * detail message.
     */
    public IllegalAccessException() {
        super();
    }

    /**
     * Constructs an <code>IllegalAccessException</code> with a detail message.
     *
     * @param   s   the detail message.
     */
    public IllegalAccessException(String s) {
        super(s);
    }
}
