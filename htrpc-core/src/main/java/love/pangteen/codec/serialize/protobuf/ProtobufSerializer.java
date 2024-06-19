package love.pangteen.codec.serialize.protobuf;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import love.pangteen.codec.serialize.Serializer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/18 16:48
 **/
public class ProtobufSerializer implements Serializer {

    /**
     * Avoid re applying buffer space every time serialization.
     */
    private static final ThreadLocal<LinkedBuffer> BUFFER = ThreadLocal.withInitial(() -> {
        return LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
    });
    private static final Map<Class<?>, Schema<?>> CACHE_SCHEMA = new ConcurrentHashMap<>();

    @Override
    public byte[] serialize(Object obj) {
        Class<?> clazz = obj.getClass();
        Schema schema = CACHE_SCHEMA.computeIfAbsent(clazz, k -> RuntimeSchema.getSchema(clazz));
        byte[] bytes;
        try {
            bytes = ProtostuffIOUtil.toByteArray(obj, schema, BUFFER.get());
        } finally {
            BUFFER.get().clear();
        }
        return bytes;

    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }
}
