//package net.havengarde.aureycore.foundation.db;
//
//import com.hayachikin.conduit.Conduit;
//import com.hayachikin.conduit.Database;
//import com.hayachikin.conduit.DatabaseOptions;
//import com.hayachikin.conduit.DatabaseType;
//import com.hayachikin.conduit.exceptions.*;
//import AManagedPlugin;
//import ADatabaseManager;
//import Configured;
//import org.bukkit.Bukkit;
//import org.bukkit.configuration.ConfigurationSection;
//import org.bukkit.configuration.InvalidConfigurationException;
//import org.bukkit.configuration.file.FileConfiguration;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.logging.Level;
//
//public final class ConduitDatabaseManager<T extends AManagedPlugin> extends ADatabaseManager<T> implements Configured {
//    private Database db;
//    private DatabaseType type;
//    private String databaseName, host, username, password;
//    private int port;
//    private boolean devMode;
//
//    public ConduitDatabaseManager(T plugin) {
//        super(plugin);
//    }
//
//    @Override
//    protected void startup() {
//        DatabaseOptions dbOptions = new DatabaseOptions(host, port, username, password, devMode);
//
//        Database db;
//        try {
//            db = Conduit.createDatabase(type, databaseName, dbOptions);
//        } catch (Exception e) {
//            Bukkit.getLogger().log(Level.SEVERE, "ConduitDatabaseManager failed to create database for plugin " + plugin.getName());
//            db = null;
//        }
//
//        this.db = db;
//    }
//
//    @Override
//    protected void cleanup() {
//
//    }
//
//    @Override
//    public void setConfigDefaults(FileConfiguration config) {
//
//    }
//
//    @Override
//    public void loadSettingsFromConfig(FileConfiguration config) throws InvalidConfigurationException {
//        ConfigurationSection conduitSection = config.getConfigurationSection("conduit-db");
//        if (conduitSection == null)
//            throw new InvalidConfigurationException("No \"conduit-db\" section found in configuration");
//
//        String dbTypeString = conduitSection.getString("type");
//        if (dbTypeString == null)
//            throw new InvalidConfigurationException("No value found for \"conduit-db.type\" in configuration");
//
//        type = parseDatabaseTypeFromConfig(dbTypeString);
//
//        databaseName = conduitSection.getString("name");
//        if (databaseName == null)
//            throw new InvalidConfigurationException("No value found for \"conduit-db.name\" in configuration");
//
//        host = conduitSection.getString("url");
//        if (host == null)
//            throw new InvalidConfigurationException("No value found for \"conduit-db.url\" in configuration");
//
//        port = conduitSection.getInt("port");
//        if (port == 0)
//            throw new InvalidConfigurationException("No value found for \"conduit-db.port\" in configuration");
//
//        username = conduitSection.getString("username");
//        if (username == null)
//            throw new InvalidConfigurationException("No value found for \"conduit-db.username\" in configuration");
//
//        password = conduitSection.getString("password");
//        if (password == null)
//            throw new InvalidConfigurationException("No value found for \"conduit-db.password\" in configuration");
//
//        devMode = conduitSection.getBoolean("dev-mode", true);
//    }
//
//    @Override
//    public void saveSettingsToConfig(FileConfiguration config) {
//
//    }
//
//    @Override
//    protected <O> void mapClass(Class<O> objectClass) {
//        try {
//            if (db != null)
//                db.mapClass(objectClass);
//        } catch (ConduitOperationException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected <O> List<O> loadAllObjects(Class<O> objectClass) {
//        List<O> objects = Collections.emptyList();
//
//        try {
//            if (db != null)
//                objects = db.selectAll(objectClass);
//        } catch (ConduitQueryAgentException | ConduitOperationException | ConduitMappingException e) {
//            e.printStackTrace();
//        }
//
//        return objects;
//    }
//
//    @Override
//    protected <O, I> O loadObject(Class<O> objectClass, I id) {
//        try {
//            if (db != null)
//                return db.select(objectClass, id);
//        } catch (ConduitQueryAgentException | ConduitOperationException | ConduitMappingException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    protected <O> void saveObject(Class<O> objectClass, O object) {
//        try {
//            if (db != null)
//                db.upsert(objectClass, object);
//        } catch (ConduitQueryAgentException | ConduitOperationException | ConduitMappingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected <O> void removeObject(Class<O> objectClass, O object) {
//        try {
//            if (db != null)
//                db.delete(objectClass, object);
//        } catch (ConduitQueryAgentException | ConduitOperationException | ConduitMappingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static DatabaseType parseDatabaseTypeFromConfig(String type) throws InvalidConfigurationException {
//        return switch (type) {
//            case "mysql", "mysql8" -> DatabaseType.MySQL8;
//            default -> throw new InvalidConfigurationException("Invalid database type for Conduit: " + type);
//        };
//    }
//}
