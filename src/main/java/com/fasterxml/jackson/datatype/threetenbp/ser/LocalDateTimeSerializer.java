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

package com.fasterxml.jackson.datatype.threetenbp.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.ChronoField;

import java.io.IOException;

/**
 * Serializer for ThreeTen temporal {@link LocalDateTime}s.
 *
 * @author Nick Williams
 * @since 2.4.1
 */
public class LocalDateTimeSerializer extends ThreeTenFormattedSerializerBase<LocalDateTime>
{
    private static final long serialVersionUID = 1L;

    public static final LocalDateTimeSerializer INSTANCE = new LocalDateTimeSerializer();

    private LocalDateTimeSerializer() {
        this(null, null);
    }

    private LocalDateTimeSerializer(Boolean useTimestamp, DateTimeFormatter dtf) {
        super(LocalDateTime.class, useTimestamp, dtf);
    }

    @Override
    protected ThreeTenFormattedSerializerBase<LocalDateTime> withFormat(Boolean useTimestamp, DateTimeFormatter dtf) {
        return new LocalDateTimeSerializer(useTimestamp, dtf);
    }

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator generator, SerializerProvider provider)
            throws IOException
    {
        if(useTimestamp(provider))
        {
            generator.writeStartArray();
            generator.writeNumber(dateTime.getYear());
            generator.writeNumber(dateTime.getMonthValue());
            generator.writeNumber(dateTime.getDayOfMonth());
            generator.writeNumber(dateTime.getHour());
            generator.writeNumber(dateTime.getMinute());
            if(dateTime.getSecond() > 0 || dateTime.getNano() > 0)
            {
                generator.writeNumber(dateTime.getSecond());
                if(dateTime.getNano() > 0)
                {
                    if(provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS))
                        generator.writeNumber(dateTime.getNano());
                    else
                        generator.writeNumber(dateTime.get(ChronoField.MILLI_OF_SECOND));
                }
            }
            generator.writeEndArray();
        }
        else
        {
            String str = (_formatter == null) ? dateTime.toString() : dateTime.format(_formatter);
            generator.writeString(str);
        }
    }
}
