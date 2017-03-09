/*
 *
 *  *
 *  *  * <!--
 *  *  *   ~
 *  *  *   ~ The MIT License (MIT)
 *  *  *   ~
 *  *  *   ~ Copyright (c) 2010-2017 QAMatic
 *  *  *   ~
 *  *  *   ~ Permission is hereby granted, free of charge, to any person obtaining a copy
 *  *  *   ~ of this software and associated documentation files (the "Software"), to deal
 *  *  *   ~ in the Software without restriction, including without limitation the rights
 *  *  *   ~ to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  *  *   ~ copies of the Software, and to permit persons to whom the Software is
 *  *  *   ~ furnished to do so, subject to the following conditions:
 *  *  *   ~
 *  *  *   ~ The above copyright notice and this permission notice shall be included in all
 *  *  *   ~ copies or substantial portions of the Software.
 *  *  *   ~
 *  *  *   ~ THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  *  *   ~ IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  *  *   ~ FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  *  *   ~ AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  *  *   ~ LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  *  *   ~ OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  *  *   ~ SOFTWARE.
 *  *  *   ~
 *  *  *   ~
 *  *  *   -->
 *  *
 *  *
 *
 */

package org.qamatic.mintleaf.dbs;

import org.junit.Test;
import org.qamatic.mintleaf.DbSettings;
import org.qamatic.mintleaf.core.JdbcDriverSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;


public class ConnectionParameterTest {


    @Test
    public void testDefaultDevMode() {
        DbSettings settings = new JdbcDriverSource();
        assertFalse(settings.isDebugEnabled());
    }

    @Test
    public void testJdbcUrl() {
        DbSettings settings = new JdbcDriverSource();
        settings.setUrl("jdbc:");
        assertEquals("jdbc:", settings.getUrl());
    }

    @Test
    public void testUsername() {
        DbSettings settings = new JdbcDriverSource();
        settings.setUsername("sys");
        assertEquals("sys", settings.getUsername());
    }

    @Test
    public void testPassword() {
        DbSettings settings = new JdbcDriverSource();
        settings.setPassword("1234");
        assertEquals("1234", settings.getPassword());
    }

    @Test
    public void testDebug() {
        DbSettings settings = new JdbcDriverSource();
        settings.setPassword("debug");
        assertEquals("debug", settings.getPassword());
    }

    @Test
    public void testDriverClassName() {
        DbSettings settings = new JdbcDriverSource();
        settings.setDriverClassName("org.h2.Driver");
        assertEquals("org.h2.Driver", settings.getDriverClassName());
    }

}
