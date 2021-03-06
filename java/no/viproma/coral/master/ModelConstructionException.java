/* Copyright 2014-2017, SINTEF Ocean.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package no.viproma.coral.master;


/** Exception type thrown on model construction errors. */
public class ModelConstructionException extends Exception
{
    /** Constructs an exception with the given message. */
    public ModelConstructionException(String message)
    {
        super(message);
    }

    // Strongly recommended for Serializable classes, see:
    // https://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
    private static final long serialVersionUID = 0L;
}
