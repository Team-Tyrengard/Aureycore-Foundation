package com.tyrengard.aureycore.foundation;

import java.util.List;

public abstract class ADataManager<T extends AManagedPlugin, S, I> extends AManager<T> {
	private final Class<S> objClass;
	protected ADataManager(T plugin, Class<S> objClass) {
		super(plugin);
		this.objClass = objClass;

		plugin.getDatabaseManager().mapClass(objClass);
	}

	@Deprecated
	protected final void addObject(S object) {
		this.saveObject(object);
	}

	protected final void saveObject(S object) {
		plugin.getDatabaseManager().saveObject(objClass, object);
	}

	protected final void removeObject(S object) {
		plugin.getDatabaseManager().removeObject(objClass, object);
	}

	@SuppressWarnings("unchecked")
	protected final S getObject(I id) {
		return (S) plugin.getDatabaseManager().loadObject(objClass, id);
	}

	@SuppressWarnings("unchecked")
	protected final List<S> getAllObjects() { return (List<S>) plugin.getDatabaseManager().loadAllObjects(objClass); }

	protected final boolean hasObject(I id) { return getObject(id) != null; }
}
