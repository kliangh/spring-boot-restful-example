package code.kliangh.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static void copyPropertiesWithValue(Object source, Object target) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.copyProperties(source, target, getPropertyNamesWithoutValue(source));
    }

    public static String[] getPropertyNamesWithoutValue(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();

        Set<String> propertyNamesWithoutValue = new HashSet<>();
        Arrays.stream(propertyDescriptors).forEach(propertyDescriptor -> {
            Object sourceValue = src.getPropertyValue(propertyDescriptor.getName());
            if (sourceValue == null) propertyNamesWithoutValue.add(propertyDescriptor.getName());
        });

        String[] result = new String[propertyNamesWithoutValue.size()];
        return propertyNamesWithoutValue.toArray(result);
    }
}
