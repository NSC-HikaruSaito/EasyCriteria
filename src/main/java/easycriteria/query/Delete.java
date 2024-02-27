package easycriteria.query;

import easycriteria.path.Root;
import jakarta.persistence.Entity;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.Attribute;

import java.util.function.Function;
import java.util.stream.Stream;

public class Delete <D> extends QueryCondition {

    private CriteriaDelete<D> deleteQuery;

    private jakarta.persistence.criteria.Root<D> deleteRoot;

    public Delete(CriteriaBuilder criteriaBuilder, Class<D> entity) {
        if (entity.getAnnotation(Entity.class) == null) {
            throw new RuntimeException("This class is not entity.");
        }
        this.criteriaBuilder = criteriaBuilder;
        this.deleteQuery = criteriaBuilder.createCriteriaDelete(entity);
        this.deleteRoot = this.deleteQuery.from(entity);
    }

    public Delete<D> where(Function<Delete<D>, Predicate>...functions) {
        Predicate[] predicates = Stream.of(functions)
                .map(func -> func.apply(this))
                .toArray(Predicate[]::new);

        this.deleteQuery.where(predicates);

        return this;
    }

    public <T> SubSelect<T> subQuery(Root<T> path) {
        return new SubSelect(this.criteriaBuilder, this.deleteQuery.subquery(path.getJavaType()), path);
    }

    public CriteriaDelete<D> getQuery() {
        return this.deleteQuery;
    }

    @Override
    protected Path getExpression(Attribute... attribute) {
        From baseRoot = this.deleteRoot;

        Attribute first = attribute[0];

        Path result = baseRoot.get(first.getName());

        for (int i = 1; i < attribute.length; i++) {
            result = result.get(attribute[i].getName());
        }

        return result;
    }

}
