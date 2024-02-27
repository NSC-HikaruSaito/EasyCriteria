package easycriteria.path;

import lombok.Getter;

public class Entity  extends CriteriaPath {

    @Getter
    private Class<?> entityClass;

    private Entity(Class<?> entityClass) {

        this.type = PathType.ENTITY;

        if (entityClass.getAnnotation(jakarta.persistence.Entity.class) == null) {
            throw new RuntimeException("This class is not entity.");
        }

        this.entityClass = entityClass;
    }

    public static Entity path(Class<?> entityClass) {
        return new Entity(entityClass);
    }

}
