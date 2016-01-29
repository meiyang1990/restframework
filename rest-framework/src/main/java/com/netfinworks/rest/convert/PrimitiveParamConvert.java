/**
 * 
 */
package com.netfinworks.rest.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.netfinworks.rest.util.Magic;

/**
 * @author bigknife
 *
 */
public class PrimitiveParamConvert implements IParamConvert {
    private Map<Class<?>, IParamConvert> convertMaps       = new HashMap<Class<?>, IParamConvert>();
    private String                       defaultDateFormat = "yyyy-MM-dd HH:mm:ss";

    public PrimitiveParamConvert() {
        IntegerConvert ic = new IntegerConvert();
        convertMaps.put(Integer.class, ic);
        convertMaps.put(int.class, ic);

        FloatConvert fc = new FloatConvert();
        convertMaps.put(Float.class, fc);
        convertMaps.put(float.class, fc);

        DoubleConvert dc = new DoubleConvert();
        convertMaps.put(Double.class, dc);
        convertMaps.put(double.class, dc);

        LongConvert lc = new LongConvert();
        convertMaps.put(Long.class, lc);
        convertMaps.put(long.class, lc);

        ShortConvert sc = new ShortConvert();
        convertMaps.put(Short.class, sc);
        convertMaps.put(short.class, sc);

        CharConvert cc = new CharConvert();
        convertMaps.put(Character.class, cc);
        convertMaps.put(char.class, cc);

        ByteConvert bc = new ByteConvert();
        convertMaps.put(Byte.class, bc);
        convertMaps.put(byte.class, bc);

        BooleanConvert boc = new BooleanConvert();
        convertMaps.put(Boolean.class, boc);
        convertMaps.put(boolean.class, boc);

        DateConvert dateConvert = new DateConvert();
        dateConvert.setDateFormat(defaultDateFormat);
        convertMaps.put(Date.class, dateConvert);

        EnumConvert enumConvert = new EnumConvert();
        convertMaps.put(Enum.class, enumConvert);
    }

    public void setDateFormat(String dateFormat) {
        ((DateConvert) convertMaps.get(Date.class)).setDateFormat(dateFormat);
    }

    public void register(Class<?> type, IParamConvert convert) {
        if (type != null && convert != null) {
            convertMaps.put(type, convert);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T convert(String raw, Class<T> distClass) {
        if (distClass.equals(String.class)) {
            return (T) raw;
        }
        if (raw != null) {
            IParamConvert convert = lookup(distClass);
            if (convert != null) {
                return convert.convert(raw, distClass);
            } else {
                convert = lookup(Enum.class);
                T distValue = convert.convert(raw, distClass);
                if (distValue != null) {
                    return distValue;
                }
                throw new RuntimeException("no convert set to " + distClass);
            }
        }
        return null;
    }

    private IParamConvert lookup(Class<?> clazz) {
        IParamConvert convert = convertMaps.get(clazz);
        return convert;
    }

    static class IntegerConvert implements IParamConvert {

        @SuppressWarnings("unchecked")
        @Override
        public <T> T convert(String raw, Class<T> distClass) {
            if (Magic.EmtpyString.equals(raw)) {
                return (T) (Integer) 0;
            }
            return (T) (Integer) Integer.parseInt(raw);
        }
    }

    static class FloatConvert implements IParamConvert {
        @SuppressWarnings("unchecked")
        @Override
        public <T> T convert(String raw, Class<T> distClass) {
            if (Magic.EmtpyString.equals(raw)) {
                return (T) (Float) 0f;
            }
            return (T) (Float) Float.parseFloat(raw);
        }
    }

    static class DoubleConvert implements IParamConvert {
        @SuppressWarnings("unchecked")
        @Override
        public <T> T convert(String raw, Class<T> distClass) {
            if (Magic.EmtpyString.equals(raw)) {
                return (T) (Double) 0d;
            }
            return (T) (Double) Double.parseDouble(raw);
        }
    }

    static class LongConvert implements IParamConvert {
        @SuppressWarnings("unchecked")
        @Override
        public <T> T convert(String raw, Class<T> distClass) {
            if (Magic.EmtpyString.equals(raw)) {
                return (T) (Long) 0l;
            }
            return (T) (Long) Long.parseLong(raw);
        }
    }

    static class ShortConvert implements IParamConvert {
        @SuppressWarnings("unchecked")
        @Override
        public <T> T convert(String raw, Class<T> distClass) {
            if (Magic.EmtpyString.equals(raw)) {
                return (T) (Short) (short) 0;
            }
            return (T) (Short) (Short.parseShort(raw));
        }
    }

    static class CharConvert implements IParamConvert {
        @SuppressWarnings("unchecked")
        @Override
        public <T> T convert(String raw, Class<T> distClass) {
            return (T) (Character) (Character.valueOf(raw.charAt(0)));
        }
    }

    static class ByteConvert implements IParamConvert {
        @SuppressWarnings("unchecked")
        @Override
        public <T> T convert(String raw, Class<T> distClass) {
            return (T) (Byte) (Byte.parseByte(raw));
        }
    }

    static class BooleanConvert implements IParamConvert {
        @SuppressWarnings("unchecked")
        @Override
        public <T> T convert(String raw, Class<T> distClass) {
            return (T) (Boolean.valueOf(raw));
        }
    }

    static class StringConvert implements IParamConvert {

        @Override
        public <T> T convert(String raw, Class<T> distClass) {
            return distClass.cast(raw);
        }

    }

    static final class DateConvert implements IParamConvert {
        private String dateFormat;

        @Override
        public <T> T convert(String paramString, Class<T> paramClass) {
            try {
                if (paramString.trim().length() == 0) {
                    return null;
                }
                Date date = new SimpleDateFormat(dateFormat).parse(paramString);
                return paramClass.cast(date);
            } catch (ParseException e) {
                throw new IllegalArgumentException("date convert args illegal", e);
            }
        }

        public void setDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
        }
    }

    static final class EnumConvert implements IParamConvert {
        @Override
        public <T> T convert(String paramString, Class<T> paramClass) {
            try {
                @SuppressWarnings({ "rawtypes", "unchecked" })
                T distInst = (T) Enum.valueOf((Class<Enum>) paramClass, paramString);
                return distInst;
            } catch (Exception e) {
                //just ignore
                //throw new IllegalArgumentException("enum convert args illegal",e);
            }
            return null;
        }
    }
}
