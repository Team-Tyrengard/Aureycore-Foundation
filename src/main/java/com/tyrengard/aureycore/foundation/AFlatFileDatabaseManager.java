package com.tyrengard.aureycore.foundation;

public abstract class AFlatFileDatabaseManager<T extends AManagedPlugin> extends ADatabaseManager<T> {
	protected AFlatFileDatabaseManager(T plugin) {
		super(plugin);
	}
//
//	private final GsonBuilder b;
//    private Gson gson;
//
//	protected AFlatFileDatabaseManager(T plugin) {
//		super(plugin);
//    	b = new GsonBuilder();
//    	b.registerTypeAdapter(UUID.class, new UUIDJsonHandler());
//    	registerTypes(b);
//    	gson = b.setPrettyPrinting().create();
//    }
//
//    protected void registerTypes(GsonBuilder b) {}
//
////    final <S extends ASerializable> void registerSerializableType(Class<S> type) {
////    	b.registerTypeAdapter(type, new ASerializableHandler<S>(true));
////    }
//
//    protected final <S extends ASerializable> List<S> loadAllObjects(Class<S> objectClass) {
//    	List<S> objects = new ArrayList<>();
//    	String name = objectClass.getSimpleName();
//		File objectFolder = new File(dataFolderPath.toString(), name);
//		if (!objectFolder.exists())
//			objectFolder.mkdirs();
//		else for (File objectFile : objectFolder.listFiles(new FilenameFilter() {
//			@Override
//		    public boolean accept(File dir, String name) {
//		        return name.endsWith(".json");
//		    }
//		}))
//			try (BufferedReader br = new BufferedReader(new FileReader(objectFile))) {
//				S object = gson.fromJson(br, objectClass);
//				object.id = UUID.fromString(objectFile.getName().replace(".json", ""));
//				object.shouldSerialize = true;
//				objects.add(object);
//			} catch (JsonSyntaxException e) {
//				e.printStackTrace();
//			} catch (JsonIOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (FileNotFoundException ignored) {
//			} catch (IOException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//		return objects;
//    }
//
//	protected final <S extends ASerializable> S loadObject(Class<S> objectClass, UUID id) {
//		String name = objectClass.getSimpleName();
//		File objectFolder = new File(dataFolderPath.toString(), name);
//		if (!objectFolder.exists()) {
//			objectFolder.mkdirs();
//		}
//		else try (BufferedReader br = new BufferedReader(new FileReader(objectFolder + File.separator + id.toString() + ".json"))) {
//			S object = gson.fromJson(br, objectClass);
//			object.id = id;
//			object.shouldSerialize = true;
//			return object;
//		} catch (JsonSyntaxException e) {
//			e.printStackTrace();
//		} catch (JsonIOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (FileNotFoundException ignored) {
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (NullPointerException e2) {
//			// TODO Auto-generated catch block
//			e2.printStackTrace();
//		}
//
//		return null;
//	}
//
//	protected final <S extends ASerializable> void saveObject(S object) {
//		if (!object.shouldSerialize) return;
//
//		String name = object.getClass().getSimpleName();
//		File objectFolder = new File(dataFolderPath.toString(), name);
//		if (!objectFolder.exists()) {
//			objectFolder.mkdirs();
//		}
//		else try (FileWriter fr = new FileWriter(new File(objectFolder, object.id.toString() + ".json"))) {
//			fr.write(gson.toJson(object));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//	protected final <S extends ASerializable> void removeObject(S object) {
//		File objectFolder = new File(dataFolderPath.toString(), object.getClass().getSimpleName());
//		if (objectFolder.exists()) {
//			File objectFile = new File(objectFolder, object.id.toString() + ".json");
//			if (objectFile.exists())
//				objectFile.delete();
//		}
//	}
//
//	private static class UUIDJsonHandler implements JsonSerializer<UUID>, JsonDeserializer<UUID> {
//		@Override
//		public JsonElement serialize(UUID arg0, Type arg1, JsonSerializationContext arg2) {
//			// TODO Auto-generated method stub
//			return new JsonPrimitive(arg0.toString());
//		}
//
//		@Override
//		public UUID deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
//				throws JsonParseException {
//			return UUID.fromString(arg0.getAsString());
//		}
//	}

}
