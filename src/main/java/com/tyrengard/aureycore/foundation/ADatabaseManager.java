package com.tyrengard.aureycore.foundation;

import java.util.List;

public abstract class ADatabaseManager<T extends AManagedPlugin> extends AManager<T> {
//    protected Path dataFolderPath;

    protected ADatabaseManager(T plugin) {
        super(plugin);
//        dataFolderPath = Paths.get(plugin.getDataFolder().toString(), "data");
//        if (Files.notExists(dataFolderPath))
//            dataFolderPath.toFile().mkdirs();
    }

    protected abstract <O> void mapClass(Class<O> objectClass);

    protected abstract <O> List<O> loadAllObjects(Class<O> objectClass);

    protected abstract <O, I> O loadObject(Class<O> objectClass, I id);
	
	protected abstract <O> void saveObject(Class<O> objectClass, O object);

	protected abstract <O> void removeObject(Class<O> objectClass, O object);
}
