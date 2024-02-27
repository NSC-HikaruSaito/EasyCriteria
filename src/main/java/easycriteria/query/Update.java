package easycriteria.query;

import easycriteria.path.Root;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.Attribute;

import java.util.function.Function;
import java.util.stream.Stream;

public class Update <U> extends QueryCondition {

    private CriteriaUpdate<U> updateQuery;

    private jakarta.persistence.criteria.Root<U> updateRoot;

    public Update(CriteriaBuilder criteriaBuilder, Class<U> entity) {
        if (entity.getAnnotation(jakarta.persistence.Entity.class) == null) {
            throw new RuntimeException("This class is not entity.");
        }
        this.criteriaBuilder = criteriaBuilder;
        this.updateQuery = criteriaBuilder.createCriteriaUpdate(entity);
        this.updateRoot = this.updateQuery.from(entity);
    }

    public Update<U> set(Attribute<U, ?>[] attribute, Object value) {

        updateQuery.set(this.getExpression(attribute), value);

        return this;
    }

    public Update<U> set(Attribute<U, ?> attribute, Object value) {

        updateQuery.set(this.getExpression(attribute), value);

        return this;
    }

    public Update<U> where(Function<Update<U>, Predicate>... functions) {
        Predicate[] predicates = Stream.of(functions)
                .map(func -> func.apply(this))
                .toArray(Predicate[]::new);

        this.updateQuery.where(predicates);

        return this;
    }

    public <T> SubSelect<T> subQuery(Root<T> path) {
        return new SubSelect(this.criteriaBuilder, this.updateQuery.subquery(path.getJavaType()), path);
    }

    public CriteriaUpdate<U> getQuery() {
        return this.updateQuery;
    }

    @Override
    protected Path getExpression(Attribute... attribute) {
        From baseRoot = this.updateRoot;

        Attribute first = attribute[0];

        Path result = baseRoot.get(first.getName());

        for (int i = 1; i < attribute.length; i++) {
            result = result.get(attribute[i].getName());
        }

        return result;
    }

}
