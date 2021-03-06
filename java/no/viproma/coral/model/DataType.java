/* Copyright 2014-2017, SINTEF Ocean.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package no.viproma.coral.model;


/**
 * The data type of a variable.
 */
public enum DataType
{
    /** Real-valued number (double-precision floating point) */
    REAL,

    /** Signed integer */
    INTEGER,

    /** Boolean value */
    BOOLEAN,

    /** Character/byte string */
    STRING
}
