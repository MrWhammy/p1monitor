package io.p1jmonitor.telegram.converter;

import io.p1jmonitor.telegram.COSEMTelegram;
import io.p1jmonitor.telegram.Line;
import io.p1jmonitor.telegram.ObisCode;
import io.p1jmonitor.telegram.Telegram;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class BeanTelegramConverter<T extends Telegram> {
    private final Class<T> clazz;
    private final List<ObisMethodReference> methodReferences;

    BeanTelegramConverter(Class<T> clazz, List<ObisMethodReference> references) {
        this.clazz = clazz;
        this.methodReferences = references;
    }

    public T convert(COSEMTelegram COSEMTelegram) {
        try {
            T instance = clazz.getConstructor().newInstance();

            for (ObisMethodReference obisMethodReference : methodReferences) {
                Line.DataLine dataLine = COSEMTelegram.getDataLine(obisMethodReference.getObisCode());
                String value = dataLine.getValues().get(obisMethodReference.getValueIndex());
                Object convertedValue = obisMethodReference.getValueConverter().convert(value);
                obisMethodReference.getWriteMethod().invoke(instance, convertedValue);
            }

            return instance;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new IllegalArgumentException(e);
        }
    }

    static class ObisMethodReference {
        private final ObisCode obisCode;
        private final int valueIndex;
        private final Method writeMethod;
        private final ValueConverter<?> valueConverter;

        public ObisMethodReference(ObisCode obisCode, int valueIndex, Method writeMethod, ValueConverter<?> valueConverter) {
            this.obisCode = obisCode;
            this.valueIndex = valueIndex;
            this.writeMethod = writeMethod;
            this.valueConverter = valueConverter;
        }

        public ObisCode getObisCode() {
            return obisCode;
        }

        public int getValueIndex() {
            return valueIndex;
        }

        public Method getWriteMethod() {
            return writeMethod;
        }

        public ValueConverter<?> getValueConverter() {
            return valueConverter;
        }
    }
}
