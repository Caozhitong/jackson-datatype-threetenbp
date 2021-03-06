package com.fasterxml.jackson.datatype.threetenbp.key;

import static org.junit.Assert.assertEquals;

import org.threeten.bp.LocalDate;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.datatype.threetenbp.ModuleTestBase;

import org.junit.Test;

public class LocalDateAsKeyTest extends ModuleTestBase
{
    private static final LocalDate DATE = LocalDate.of(2015, 3, 14);
    private static final String DATE_STRING = "2015-03-14";

    private final ObjectMapper MAPPER = newMapper();
    private final ObjectReader READER = MAPPER.readerFor(new TypeReference<Map<LocalDate, String>>() { });

    @Test
    public void testSerialization() throws Exception {
        assertEquals("Incorrect value", mapAsString(DATE_STRING, "test"),
                MAPPER.writeValueAsString(asMap(DATE, "test")));
    }

    @Test
    public void testDeserialization() throws Exception {
        assertEquals("Incorrect value", asMap(DATE, "test"),
                READER.readValue(mapAsString(DATE_STRING, "test")));
    }
}
