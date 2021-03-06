package com.avixy.qrtoken.gui.components.hacks;

/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Skin;

/**
 *
 */
class IntegerField extends InputField {
    /**
     * The value of the IntegerField. If null, the value will be treated as "0", but
     * will still actually be null.
     */
    private IntegerProperty value = new SimpleIntegerProperty(this, "value");
    public final int getValue() { return value.get(); }
    public final void setValue(int value) { this.value.set(value); }
    public final IntegerProperty valueProperty() { return value; }

    private IntegerProperty maxValue = new SimpleIntegerProperty(this, "maxValue", -1);
    public final int getMaxValue() { return maxValue.get(); }
    public final void setMaxValue(int maxVal) {this.maxValue.set(maxVal); }
    public final IntegerProperty maxValueProperty() { return maxValue; }
    /**
     * Creates a new IntegerField. The style class is set to "money-field".
     */
    public IntegerField() {
        this(-1); // setting to default
    }
    public IntegerField(int maxVal) {
        getStyleClass().setAll("integer-field");
        setMaxValue(maxVal);
    }

    /***************************************************************************
     *                                                                         *
     * Methods                                                                 *
     *                                                                         *
     **************************************************************************/

    /** {@inheritDoc} */
   @Override protected Skin<?> createDefaultSkin() {
        return new IntegerFieldSkin(this);
    }
}
