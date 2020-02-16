package uk.co.bjdavies.app.modules;

public interface ModuleCommandDefinition {
    String getName();
    Object[] getArgs();
    Class<?>[] getParameterTypes();
    Class<? extends Module> getModuleClass();
}
