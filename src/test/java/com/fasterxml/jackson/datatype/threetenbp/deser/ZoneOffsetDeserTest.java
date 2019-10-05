/*
 * Copyright 2013 FasterXML.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package com.fasterxml.jackson.datatype.threetenbp.deser;

import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.threetenbp.MockObjectConfiguration;
import com.fasterxml.jackson.datatype.threetenbp.ModuleTestBase;

import org.junit.Test;

public class ZoneOffsetDeserTest extends ModuleTestBase
{
    private final ObjectMapper MAPPER = newMapper();

    @Test
    public void testDeserialization01() throws Exception
    {
        ZoneOffset value = MAPPER.readValue("\"Z\"", ZoneOffset.class);
        assertEquals("The value is not correct.", ZoneOffset.of("Z"), value);
    }

    @Test
    public void testDeserialization02() throws Exception
    {
        ZoneOffset value = MAPPER.readValue("\"+0300\"", ZoneOffset.class);
        assertEquals("The value is not correct.", ZoneOffset.of("+0300"), value);
    }

    @Test
    public void testDeserialization03() throws Exception
    {
        ZoneOffset value = MAPPER.readValue("\"-06:30\"", ZoneOffset.class);
        assertEquals("The value is not correct.", ZoneOffset.of("-0630"), value);
    }

    @Test
    public void testDeserializationWithTypeInfo03() throws Exception
    {
        ObjectMapper mapper = newMapper()
            .addMixIn(ZoneId.class, MockObjectConfiguration.class);
        ZoneId value = mapper.readValue("[\"" + ZoneOffset.class.getName() + "\",\"+0415\"]", ZoneId.class);
        assertTrue("The value should be a ZoneOffset.", value instanceof ZoneOffset);
        assertEquals("The value is not correct.", ZoneOffset.of("+0415"), value);
    }
}
