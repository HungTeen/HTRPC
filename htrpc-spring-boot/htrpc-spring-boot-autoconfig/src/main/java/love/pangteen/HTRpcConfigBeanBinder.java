package love.pangteen;

import com.alibaba.spring.context.config.ConfigurationBeanBinder;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.bind.PropertySourcesPlaceholdersResolver;
import org.springframework.boot.context.properties.bind.handler.IgnoreErrorsBindHandler;
import org.springframework.boot.context.properties.bind.handler.NoUnboundElementsBindHandler;
import org.springframework.boot.context.properties.source.ConfigurationPropertySource;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.context.properties.source.UnboundElementsSourceFilter;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Arrays;
import java.util.Map;

/**
 * @program: HTRPC
 * @author: PangTeen
 * @create: 2024/6/16 16:12
 **/
public class HTRpcConfigBeanBinder implements ConfigurationBeanBinder {

    @Override
    public void bind(
            Map<String, Object> configurationProperties,
            boolean ignoreUnknownFields,
            boolean ignoreInvalidFields,
            Object configurationBean) {
        Iterable<PropertySource<?>> propertySources =
                Arrays.asList(new MapPropertySource("internal", configurationProperties));

        // Converts ConfigurationPropertySources
        Iterable<ConfigurationPropertySource> configurationPropertySources = ConfigurationPropertySources.from(propertySources);

        // Wrap Bindable from DubboConfig instance
        Bindable<Object> bindable = Bindable.ofInstance(configurationBean);

        Binder binder =
                new Binder(configurationPropertySources, new PropertySourcesPlaceholdersResolver(propertySources));

        // Get BindHandler
        BindHandler bindHandler = getBindHandler(ignoreUnknownFields, ignoreInvalidFields);

        // Bind
        binder.bind("", bindable, bindHandler);
    }

    private BindHandler getBindHandler(boolean ignoreUnknownFields, boolean ignoreInvalidFields) {
        BindHandler handler = BindHandler.DEFAULT;
        if (ignoreInvalidFields) {
            handler = new IgnoreErrorsBindHandler(handler);
        }
        if (!ignoreUnknownFields) {
            UnboundElementsSourceFilter filter = new UnboundElementsSourceFilter();
            handler = new NoUnboundElementsBindHandler(handler, filter);
        }
        return handler;
    }

}
