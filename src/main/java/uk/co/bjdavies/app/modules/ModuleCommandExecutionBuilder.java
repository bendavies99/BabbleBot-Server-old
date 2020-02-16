package uk.co.bjdavies.app.modules;

public class ModuleCommandExecutionBuilder {
    private String name;

    private Class<? extends Module> moduleClass;

    private Object[] args = new Object[0];

    private Class<?>[] parameterTypes = new Class[0];

    public ModuleCommandExecutionBuilder(String name, Class<? extends Module> moduleClass) {
        this.name = name;
        this.moduleClass = moduleClass;
    }

    public ModuleCommandExecutionBuilder setModuleClass(Class<? extends Module> moduleClass) {
        this.moduleClass = moduleClass;
        return this;
    }

    public ModuleCommandExecutionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ModuleCommandExecutionBuilder setArgs(Object... args) {
        this.args = args;
        return this;
    }

    public ModuleCommandExecutionBuilder setParameterTypes(Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes;
        return this;
    }

    public ModuleCommandDefinition build() {
        return new ModuleCommandDefinition() {
            public String getName() {
                return ModuleCommandExecutionBuilder.this.name;
            }

            public Object[] getArgs() {
                return ModuleCommandExecutionBuilder.this.args;
            }

            public Class<?>[] getParameterTypes() {
                return ModuleCommandExecutionBuilder.this.parameterTypes;
            }

            public Class<? extends Module> getModuleClass() {
                return ModuleCommandExecutionBuilder.this.moduleClass;
            }
        };
    }
}
