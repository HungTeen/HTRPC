package love.pangteen.utils.extension;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import love.pangteen.annotations.SPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Refer to Dubbo SPI: <a href="https://cn.dubbo.apache.org/zh-cn/overview/mannual/java-sdk/reference-manual/spi/overview/">...</a>.
 *
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/3 15:54
 **/
@Slf4j
public final class ExtensionLoader<T> {

    private static final String SERVICE_DIRECTORY = "META-INF/extensions/";
    private static final Map<Class<?>, ExtensionLoader<?>> EXTENSION_LOADERS = new ConcurrentHashMap<>();
    private static final Map<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>();

    private final Class<T> type;
    private final Map<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
    private final Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();

    public ExtensionLoader(Class<T> type) {
        this.type = type;
    }

    /**
     * Get the extension loader of the specified interface.
     *
     * @param type the specified interface.
     * @param <S>  the type of the specified interface.
     * @return the extension loader of the specified interface.
     */
    public static <S> ExtensionLoader<S> getExtensionLoader(Class<S> type) {
        if (type == null) {
            throw new IllegalArgumentException("Extension type should not be null.");
        }
        if (!type.isInterface()) {
            throw new IllegalArgumentException("Extension type must be an interface.");
        }
        if (!type.isAnnotationPresent(SPI.class)) {
            throw new IllegalArgumentException("Extension type must be annotated by @SPI");
        }
        return (ExtensionLoader<S>) EXTENSION_LOADERS.computeIfAbsent(type, k -> new ExtensionLoader<>(type));
    }

    public T getExtension(String name) {
        if (StrUtil.isBlank(name)) {
            throw new IllegalArgumentException("Extension name should not be blank.");
        }
        Holder<Object> holder = this.cachedInstances.computeIfAbsent(name, k -> new Holder<>());
        Object instance = Objects.requireNonNull(holder).get();
        // 双重校验锁防止并发冲突。
        if (instance == null) {
            synchronized (holder) {
                instance = holder.get();
                if (instance == null) {
                    instance = createExtension(name);
                    holder.set(instance);
                }
            }
        }
        return (T) instance;
    }

    private T createExtension(String name) {
        Map<String, Class<?>> extensionClasses = this.getExtensionClasses();
        Class<?> clazz = extensionClasses.get(name);
        if (clazz == null) {
            throw new RuntimeException("No such extension of name " + name);
        }
        return (T) EXTENSION_INSTANCES.computeIfAbsent(clazz, k -> {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });

    }

    private Map<String, Class<?>> getExtensionClasses() {
        Map<String, Class<?>> classes = this.cachedClasses.get();
        if (classes == null) {
            synchronized (this.cachedClasses) {
                classes = this.cachedClasses.get();
                if (classes == null) {
                    classes = new HashMap<>();
                    loadDirectory(classes);
                    this.cachedClasses.set(classes);
                }
            }
        }
        return classes;
    }

    private void loadDirectory(Map<String, Class<?>> classMap) {
        String fileName = SERVICE_DIRECTORY + type.getName();
        try {
            ClassLoader classLoader = ExtensionLoader.class.getClassLoader();
            Enumeration<URL> urls = classLoader.getResources(fileName);
            if (urls != null) {
                while (urls.hasMoreElements()) {
                    URL url = urls.nextElement();
                    loadResource(classMap, classLoader, url);
                }
            }
        } catch (IOException e) {
            log.error("Exception occurred when loading extension classes from file: " + fileName, e);
        }
    }

    private void loadResource(Map<String, Class<?>> classMap, ClassLoader classLoader, URL url) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // get index of comment
                final int ci = line.indexOf('#');
                if (ci >= 0) {
                    line = line.substring(0, ci); // string after # is comment so we ignore it
                }
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        final int ei = line.indexOf('=');
                        String name = line.substring(0, ei).trim();
                        String clazzName = line.substring(ei + 1).trim();
                        // our SPI use key-value pair so both of them must not be empty
                        if (!name.isEmpty() && !clazzName.isEmpty()) {
                            Class<?> clazz = classLoader.loadClass(clazzName);
                            classMap.put(name, clazz);
                        }
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public int getInstanceSize() {
        return cachedInstances.size();
    }

    public int getClassSize(){
        return getExtensionClasses().size();
    }
}
