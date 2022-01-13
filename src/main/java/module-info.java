module com.tyrengard.aureycore.foundation {
    requires org.bukkit;
    requires org.mongodb.driver.core;
    requires morphia.core;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires java.logging;
    exports com.tyrengard.aureycore.foundation.db;
    exports com.tyrengard.aureycore.foundation;
}