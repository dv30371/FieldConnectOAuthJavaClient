package com.deere.api.pagination;

import org.codehaus.jackson.map.BeanProperty;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializerFactory;
import org.codehaus.jackson.map.DeserializerProvider;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.deser.BeanDeserializerFactory;
import org.codehaus.jackson.map.deser.ValueInstantiator;
import org.codehaus.jackson.map.introspect.BasicBeanDescription;
import org.codehaus.jackson.map.type.ArrayType;
import org.codehaus.jackson.map.type.CollectionLikeType;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.map.type.MapLikeType;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.type.JavaType;

public class CollectionPageDeserializerFactory extends DeserializerFactory {
    private DeserializerFactory nonCollectionPageStrategy;

    public CollectionPageDeserializerFactory(final Config config) {
        this.nonCollectionPageStrategy = new BeanDeserializerFactory(config);
    }

    protected CollectionPageDeserializerFactory(final DeserializerFactory nonCollectionPageStrategy) {
        this.nonCollectionPageStrategy = nonCollectionPageStrategy;
    }

    @Override
    public JsonDeserializer<?> createCollectionDeserializer(final DeserializationConfig config,
                                                            final DeserializerProvider p,
                                                            final CollectionType type,
                                                            final BeanProperty property) throws JsonMappingException {

        if (CollectionPage.class.equals(type.getRawClass())) {
            final JsonDeserializer<Object> contentDeserializer = type.getContentType().getValueHandler();
            return new CollectionPageDeserializer(type, contentDeserializer);
        }

        return nonCollectionPageStrategy.createCollectionDeserializer(config, p, type, property);
    }

    @Override public Config getConfig() {
        return nonCollectionPageStrategy.getConfig();
    }

    @Override public DeserializerFactory withConfig(final Config config) {
        return nonCollectionPageStrategy.withConfig(config);
    }

    @Override
    public JavaType mapAbstractType(final DeserializationConfig config, final JavaType type) throws
            JsonMappingException {
        return nonCollectionPageStrategy.mapAbstractType(config, type);
    }

    @Override
    public ValueInstantiator findValueInstantiator(final DeserializationConfig config,
                                                   final BasicBeanDescription beanDesc)
            throws JsonMappingException {
        return nonCollectionPageStrategy.findValueInstantiator(config, beanDesc);
    }

    @Override
    public JsonDeserializer<Object> createBeanDeserializer(final DeserializationConfig config,
                                                           final DeserializerProvider p,
                                                           final JavaType type,
                                                           final BeanProperty property) throws JsonMappingException {
        return nonCollectionPageStrategy.createBeanDeserializer(config, p, type, property);
    }

    @Override
    public JsonDeserializer<?> createArrayDeserializer(final DeserializationConfig config,
                                                       final DeserializerProvider p,
                                                       final ArrayType type,
                                                       final BeanProperty property) throws JsonMappingException {
        return nonCollectionPageStrategy.createArrayDeserializer(config, p, type, property);
    }

    @Override
    public JsonDeserializer<?> createCollectionLikeDeserializer(final DeserializationConfig config,
                                                                final DeserializerProvider p,
                                                                final CollectionLikeType type,
                                                                final BeanProperty property)
            throws JsonMappingException {
        return nonCollectionPageStrategy.createCollectionLikeDeserializer(config, p, type, property);
    }

    @Override
    public JsonDeserializer<?> createEnumDeserializer(final DeserializationConfig config,
                                                      final DeserializerProvider p,
                                                      final JavaType type,
                                                      final BeanProperty property)
            throws JsonMappingException {
        return nonCollectionPageStrategy.createEnumDeserializer(config, p, type, property);
    }

    @Override
    public JsonDeserializer<?> createMapDeserializer(final DeserializationConfig config,
                                                     final DeserializerProvider p,
                                                     final MapType type,
                                                     final BeanProperty property)
            throws JsonMappingException {
        return nonCollectionPageStrategy.createMapDeserializer(config, p, type, property);
    }

    @Override
    public JsonDeserializer<?> createMapLikeDeserializer(final DeserializationConfig config,
                                                         final DeserializerProvider p,
                                                         final MapLikeType type,
                                                         final BeanProperty property)
            throws JsonMappingException {
        return nonCollectionPageStrategy.createMapLikeDeserializer(config, p, type, property);
    }

    @Override
    public JsonDeserializer<?> createTreeDeserializer(final DeserializationConfig config,
                                                      final DeserializerProvider p,
                                                      final JavaType type,
                                                      final BeanProperty property)
            throws JsonMappingException {
        return nonCollectionPageStrategy.createTreeDeserializer(config, p, type, property);
    }
}
