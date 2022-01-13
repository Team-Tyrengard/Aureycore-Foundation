module com.tyrengard.aureycore.foundation {
    requires org.bukkit;
    requires org.mongodb.driver.core;
    requires morphia.core;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires java.logging;

    exports com.tyrengard.aureycore.foundation;
    exports com.tyrengard.aureycore.foundation.db;
    exports com.tyrengard.aureycore.foundation.common.anvilevents;
    exports com.tyrengard.aureycore.foundation.common.armorevents;
    exports com.tyrengard.aureycore.foundation.common.stringformat;
    exports com.tyrengard.aureycore.foundation.common.interfaces;
    exports com.tyrengard.aureycore.foundation.common.struct;
    exports com.tyrengard.aureycore.foundation.common.utils;
}