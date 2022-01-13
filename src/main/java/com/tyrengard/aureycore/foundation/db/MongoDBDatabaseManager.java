package com.tyrengard.aureycore.foundation.db;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import com.tyrengard.aureycore.foundation.ADatabaseManager;
import com.tyrengard.aureycore.foundation.AManagedPlugin;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.codec.PrimitiveCodecRegistry;
import dev.morphia.query.experimental.filters.Filters;
import org.bson.UuidRepresentation;

import java.util.ArrayList;
import java.util.List;

public class MongoDBDatabaseManager<T extends AManagedPlugin> extends ADatabaseManager<T> {
    @SuppressWarnings("rawtypes")
    private final List<Class> entityClassCache;
    private Datastore datastore;

    public MongoDBDatabaseManager(T plugin) {
        super(plugin);
        entityClassCache = new ArrayList<>();
    }

    @Override
    protected <O> void mapClass(Class<O> objectClass) {
        entityClassCache.add(objectClass);
    }

    @Override
    protected <O> List<O> loadAllObjects(Class<O> objectClass) {
        List<O> output = new ArrayList<>();
        try {
            output = datastore.find(objectClass).stream().toList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    @Override
    protected <O, I> O loadObject(Class<O> objectClass, I id) {
        O output = null;
        try {
            output = datastore.find(objectClass).filter(Filters.eq("_id", id)).first();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    @Override
    protected <O> void saveObject(Class<O> objectClass, O object) {
        datastore.save(object);
    }

    @Override
    protected <O> void removeObject(Class<O> objectClass, O object) {
        datastore.delete(object);
    }

    @Override
    protected void startup() {
        MongoClientSettings settings = MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD).build();
        datastore = Morphia.createDatastore(MongoClients.create(settings), plugin.getName());
        datastore.getMapper().map(entityClassCache);
        datastore.ensureIndexes();
    }

    @Override
    protected void cleanup() {

    }
}
