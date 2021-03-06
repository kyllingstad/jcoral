/* Copyright 2014-2017, SINTEF Ocean.
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package no.viproma.coral.model;


/**
 * A property of variables that defines how they may be changed or connected.
 */
public enum Causality
{
    /**
     * Independent parameter.
     * <p>
     * A data value which is constant during simulation and is provided by the
     * simulation environment/user.  Cannot be used in connections.  Only occurs
     * with {@link Variability#FIXED} or {@link Variability#TUNABLE}.
     */
    PARAMETER,

    /**
     * Calculated parameter.
     * <p>
     * A data value which is constant during simulation and is computed during
     * initialisation or when tunable parameters change.  Only occurs with
     * {@link Variability#FIXED} or {@link Variability#TUNABLE}.
     */
    CALCULATED_PARAMETER,

    /**
     * Input variable.
     * <p>
     * Variable value which may be received from another subsimulator, by
     * connecting it to a compatible output variable.
     */
    INPUT,

    /**
     * Output variable.
     * <p>
     * Variable value which may be passed to another subsimulator, by connecting
     * it to a compatible input variable.
     */
    OUTPUT,

    /**
     * Local variable.
     * <p>
     * Typically an internal variable or state which is calculated from other
     * variables.  May not be connected.
     */
    LOCAL
}
