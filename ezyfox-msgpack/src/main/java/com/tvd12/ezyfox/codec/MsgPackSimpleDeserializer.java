package com.tvd12.ezyfox.codec;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.exception.EzyCodecException;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.function.EzyParser;
import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.ezyfox.io.EzyLongs;
import com.tvd12.ezyfox.io.EzyStrings;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("AbbreviationAsWordInName")
public class MsgPackSimpleDeserializer implements EzyMessageDeserializer {

    protected final MsgPackTypeParser typeParser;
    protected final MapSizeDeserializer mapSizeDeserializer;
    protected final ArraySizeDeserializer arraySizeDeserializer;
    protected final StringSizeDeserializer stringSizeDeserializer;
    protected final Map<MsgPackType, EzyParser<ByteBuffer, Object>> parsers;

    public MsgPackSimpleDeserializer() {
        this.typeParser = new MsgPackTypeParser();
        this.mapSizeDeserializer = new MapSizeDeserializer();
        this.arraySizeDeserializer = new ArraySizeDeserializer();
        this.stringSizeDeserializer = new StringSizeDeserializer();
        this.parsers = defaultParsers();
    }

    @SuppressWarnings("unchecked")
    public Object deserialize(ByteBuffer buffer) {
        return read(buffer, null);
    }

    protected Object deserialize(ByteBuffer buffer, int type) {
        buffer.position(buffer.position() - 1);
        return deserialize(buffer, getDataType(type));
    }

    protected Object deserialize(ByteBuffer buffer, MsgPackType type) {
        EzyParser<ByteBuffer, Object> parser = parsers.get(type);
        if (parser == null) {
            throw new EzyCodecException("unsupported type: " + type);
        }
        return parser.parse(buffer);
    }

    private Object read(ByteBuffer buffer, Frame frame) {
        while (true) {
            if (!buffer.hasRemaining()) {
                throw new EzyCodecException("unexpected end of data");
            }
            MsgPackType type = getDataType(buffer.get(buffer.position()) & 0xff);
            Object value;
            switch (type) {
                case FIXMAP:
                case MAP16:
                case MAP32:
                    EzyObject object = EzyEntityFactory.newObject();
                    int mapSize = getMapSize(buffer, sizeBytes(type));
                    if (mapSize > 0) {
                        frame = new Frame(frame, object, null, mapSize);
                        continue;
                    }
                    value = object;
                    break;
                case FIXARRAY:
                case ARRAY16:
                case ARRAY32:
                    EzyArray array = EzyEntityFactory.newArray();
                    int arraySize = parseArraySize(buffer, sizeBytes(type));
                    if (arraySize > 0) {
                        frame = new Frame(frame, null, array, arraySize);
                        continue;
                    }
                    value = array;
                    break;
                default:
                    value = deserialize(buffer, type);
                    break;
            }
            while (true) {
                if (frame == null) {
                    return value;
                }
                if (!frame.add(value)) {
                    break;
                }
                value = frame.value();
                frame = frame.parent;
            }
        }
    }

    private static int sizeBytes(MsgPackType type) {
        switch (type) {
            case FIXMAP:
            case FIXARRAY:
                return 1;
            case MAP16:
            case ARRAY16:
                return 3;
            default:
                return 5;
        }
    }

    protected Map<MsgPackType, EzyParser<ByteBuffer, Object>> defaultParsers() {
        Map<MsgPackType, EzyParser<ByteBuffer, Object>> parsers = new ConcurrentHashMap<>();
        addParsers(parsers);
        return parsers;
    }

    protected void addParsers(Map<MsgPackType, EzyParser<ByteBuffer, Object>> parsers) {
        parsers.put(MsgPackType.POSITIVE_FIXINT, this::parsePositiveFixInt); //0K
        parsers.put(MsgPackType.NEGATIVE_FIXINT, this::parseNegativeFixInt); //OK
        parsers.put(MsgPackType.UINT8, this::parseUInt8); //OK
        parsers.put(MsgPackType.UINT16, this::parseUInt16); //OK
        parsers.put(MsgPackType.UINT32, this::parseUInt32); //OK
        parsers.put(MsgPackType.UINT64, this::parseUInt64); //OK
        parsers.put(MsgPackType.INT8, this::parseInt8); //OK
        parsers.put(MsgPackType.INT16, this::parseInt16); //OK
        parsers.put(MsgPackType.INT32, this::parseInt32); //OK
        parsers.put(MsgPackType.INT64, this::parseInt64); //OK
        parsers.put(MsgPackType.FIXMAP, this::parseFixMap); //OK
        parsers.put(MsgPackType.MAP16, this::parseMap16); //OK
        parsers.put(MsgPackType.MAP32, this::parseMap32); //OK
        parsers.put(MsgPackType.FIXARRAY, this::parseFixArray); //OK
        parsers.put(MsgPackType.ARRAY16, this::parseArray16); //OK
        parsers.put(MsgPackType.ARRAY32, this::parseArray32); //OK
        parsers.put(MsgPackType.FIXSTR, this::parseFixStr);
        parsers.put(MsgPackType.STR8, this::parseStr8);
        parsers.put(MsgPackType.STR16, this::parseStr16);
        parsers.put(MsgPackType.STR32, this::parseStr32);
        parsers.put(MsgPackType.NIL, this::parseNil);
        parsers.put(MsgPackType.FALSE, this::parseFalse);
        parsers.put(MsgPackType.TRUE, this::parseTrue);
        parsers.put(MsgPackType.BIN8, this::parseBin8);
        parsers.put(MsgPackType.BIN16, this::parseBin16);
        parsers.put(MsgPackType.BIN32, this::parseBin32);
        parsers.put(MsgPackType.FLOAT32, this::parseFloat32);
        parsers.put(MsgPackType.FLOAT64, this::parseFloat64);
    }

    protected Object parseFloat32(ByteBuffer buffer) {
        buffer.get();
        return buffer.getFloat();
    }

    protected Object parseFloat64(ByteBuffer buffer) {
        buffer.get();
        return buffer.getDouble();
    }

    protected Object parseBin32(ByteBuffer buffer) {
        return parseBin(buffer, getBinLength(buffer, 4));
    }

    protected Object parseBin16(ByteBuffer buffer) {
        return parseBin(buffer, getBinLength(buffer, 2));
    }

    protected Object parseBin8(ByteBuffer buffer) {
        return parseBin(buffer, getBinLength(buffer, 1));
    }

    protected int getBinLength(ByteBuffer buffer, int size) {
        buffer.get();
        return readLength(buffer, size);
    }

    protected Object parseBin(ByteBuffer buffer, int length) {
        return EzyBytes.copy(buffer, length);
    }

    protected Object parseTrue(ByteBuffer buffer) {
        return parseValue(buffer, Boolean.TRUE);
    }

    protected Object parseFalse(ByteBuffer buffer) {
        return parseValue(buffer, Boolean.FALSE);
    }

    protected Object parseNil(ByteBuffer buffer) {
        return parseValue(buffer, null);
    }

    protected Object parseValue(ByteBuffer buffer, Object value) {
        buffer.get();
        return value;
    }

    protected EzyObject parseFixMap(ByteBuffer buffer) {
        return parseMap(buffer, getMapSize(buffer, 1));
    }

    protected EzyObject parseMap16(ByteBuffer buffer) {
        return parseMap(buffer, getMapSize(buffer, 3));
    }

    protected EzyObject parseMap32(ByteBuffer buffer) {
        return parseMap(buffer, getMapSize(buffer, 5));
    }

    protected int getMapSize(ByteBuffer buffer, int nbytes) {
        return mapSizeDeserializer.deserialize(buffer, nbytes);
    }

    protected EzyObject parseMap(ByteBuffer buffer, int size) {
        EzyObject object = EzyEntityFactory.newObject();
        if (size > 0) {
            read(buffer, new Frame(null, object, null, size));
        }
        return object;
    }

    protected String parseStr32(ByteBuffer buffer) {
        return parseString(buffer, 5);
    }

    protected String parseStr16(ByteBuffer buffer) {
        return parseString(buffer, 3);
    }

    protected String parseStr8(ByteBuffer buffer) {
        return parseString(buffer, 2);
    }

    protected String parseFixStr(ByteBuffer buffer) {
        return parseString(buffer, 1);
    }

    protected String parseString(ByteBuffer buffer, int nbytes) {
        return EzyStrings.newUtf(buffer, parseStringSize(buffer, nbytes));
    }

    protected int parseStringSize(ByteBuffer buffer, int nbytes) {
        return stringSizeDeserializer.deserialize(buffer, nbytes);
    }

    protected int parsePositiveFixInt(ByteBuffer buffer) {
        return (buffer.get() & 0x7F);
    }

    protected int parseNegativeFixInt(ByteBuffer buffer) {
        return buffer.get();
    }

    protected int parseUInt8(ByteBuffer buffer) {
        return parseUInt(buffer, 1);
    }

    protected int parseUInt16(ByteBuffer buffer) {
        return parseUInt(buffer, 2);
    }

    protected Object parseUInt32(ByteBuffer buffer) {
        long value = parseULong(buffer, 4);
        return value <= Integer.MAX_VALUE
            ? (Object) (int) value
            : (Object) value;
    }

    protected long parseUInt64(ByteBuffer buffer) {
        return parseULong(buffer, 8);
    }

    protected int parseInt8(ByteBuffer buffer) {
        return parseInt(buffer, 1);
    }

    protected int parseInt16(ByteBuffer buffer) {
        return parseInt(buffer, 2);
    }

    protected int parseInt32(ByteBuffer buffer) {
        return parseInt(buffer, 4);
    }

    protected long parseInt64(ByteBuffer buffer) {
        return parseLong(buffer, 8);
    }

    protected int parseUInt(ByteBuffer buffer, int size) {
        return (int) parseULong(buffer, size);
    }

    protected long parseULong(ByteBuffer buffer, int size) {
        buffer.get();
        return EzyLongs.bin2ulong(buffer, size);
    }

    protected int parseInt(ByteBuffer buffer, int size) {
        return (int) parseLong(buffer, size);
    }

    protected long parseLong(ByteBuffer buffer, int size) {
        buffer.get();
        return EzyLongs.bin2long(buffer, size);
    }

    protected EzyArray parseFixArray(ByteBuffer buffer) {
        return parseArray(buffer, parseArraySize(buffer, 1));
    }

    protected EzyArray parseArray16(ByteBuffer buffer) {
        return parseArray(buffer, parseArraySize(buffer, 3));
    }

    protected EzyArray parseArray32(ByteBuffer buffer) {
        return parseArray(buffer, parseArraySize(buffer, 5));
    }

    protected int parseArraySize(ByteBuffer buffer, int nbytes) {
        return arraySizeDeserializer.deserialize(buffer, nbytes);
    }

    protected EzyArray parseArray(ByteBuffer buffer, int size) {
        EzyArray array = EzyEntityFactory.newArray();
        if (size > 0) {
            read(buffer, new Frame(null, null, array, size));
        }
        return array;
    }

    protected MsgPackType getDataType(int type) {
        MsgPackType answer = typeParser.parse(type);
        if (answer != null) {
            return answer;
        }
        throw new EzyCodecException("has no type with type = " + String.format("0x%x", type));
    }

    protected static int readLength(ByteBuffer buffer, int size) {
        long length = EzyLongs.bin2ulong(buffer, size);
        if (length > buffer.remaining()) {
            throw new EzyCodecException(
                "invalid length: " + length + ", remaining: " + buffer.remaining()
            );
        }
        return (int) length;
    }

    private static final class Frame {
        private final Frame parent;
        private final EzyObject object;
        private final EzyArray array;
        private int remaining;
        private Object key;
        private boolean hasKey;

        private Frame(Frame parent, EzyObject object, EzyArray array, int remaining) {
            this.parent = parent;
            this.object = object;
            this.array = array;
            this.remaining = remaining;
        }

        private boolean add(Object value) {
            if (object != null) {
                if (!hasKey) {
                    key = value;
                    hasKey = true;
                    return false;
                }
                object.put(key, value);
                key = null;
                hasKey = false;
            } else {
                array.add(value);
            }
            return --remaining == 0;
        }

        private Object value() {
            return object != null ? object : array;
        }
    }

    public abstract static class AbstractSizeDeserializer {

        public int deserialize(ByteBuffer buffer, int nbytes) {
            return nbytes == 1
                ? getFix(buffer)
                : getOther(buffer, nbytes);
        }

        protected abstract int getFix(ByteBuffer buffer);

        protected int getOther(ByteBuffer buffer, int nbytes) {
            buffer.get();
            return readLength(buffer, nbytes - 1);
        }
    }

    public static class StringSizeDeserializer extends AbstractSizeDeserializer {

        @Override
        protected int getFix(ByteBuffer buffer) {
            return buffer.get() & 0x1F;
        }
    }

    public static class MapSizeDeserializer extends AbstractSizeDeserializer {

        @Override
        protected int getFix(ByteBuffer buffer) {
            return buffer.get() & 0xF;
        }
    }

    public static class ArraySizeDeserializer extends AbstractSizeDeserializer {

        @Override
        protected int getFix(ByteBuffer buffer) {
            return buffer.get() & 0xF;
        }
    }
}
