package io.p1jmonitor.telegram.converter;

import io.p1jmonitor.telegram.ObisCode;
import io.p1jmonitor.telegram.ObisReference;
import io.p1jmonitor.telegram.Telegram;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public class BeanTelegramConverterFactory {
    private final ValueConverterFactory valueConverterFactory;

    public BeanTelegramConverterFactory(ValueConverterFactory valueConverterFactory) {
        this.valueConverterFactory = valueConverterFactory;
    }

    public <T extends Telegram> BeanTelegramConverter<T> createConverter(Class<T> clazz) {
        try {
            List<BeanTelegramConverter.ObisMethodReference> methodReferences = new ArrayList<>();
            BeanInfo info = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor pd : props) {
                ObisReference obisReference = pd.getReadMethod().getAnnotation(ObisReference.class);
                if (obisReference != null) {
                    methodReferences.add(new BeanTelegramConverter.ObisMethodReference(ObisCode.of(obisReference.value()), obisReference.valueIndex(), pd.getWriteMethod(), valueConverterFactory.forObisCodeAndType(obisReference, pd.getPropertyType())));
                }
            }
            return new BeanTelegramConverter<>(clazz, methodReferences);
        } catch (IntrospectionException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
