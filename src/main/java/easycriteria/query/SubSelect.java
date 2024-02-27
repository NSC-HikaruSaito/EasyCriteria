package easycriteria.query;

import easycriteria.path.Root;
import jakarta.persistence.Entity;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.Attribute;

import java.util.function.Function;
import java.util.stream.Stream;

public class SubSelect <T> extends QueryCondition {

    //クエリ
    private final Subquery<T> query;

    private final Root<T> path;

    //ルート
    public jakarta.persistence.criteria.Root<?> root;

    public SubSelect(CriteriaBuilder criteriaBuilder, Subquery<T> query, Root<T> path) {
        this.criteriaBuilder = criteriaBuilder;
        this.query = query;
        this.path = path;
    }

    //-----FROM句-------
    public SubSelect<T> from(Class<?> fromEntity) {
        if (fromEntity.getAnnotation(Entity.class) == null) {
            throw new RuntimeException("This class is not entity.");
        }
        this.root = query.from(fromEntity);
        return this;
    }

    //-----WEHRE句------
    public SubSelect<T> where(Function<SubSelect<T>, Predicate>... functions) {
        Predicate[] predicates = Stream.of(functions)
                .map(func -> func.apply(this))
                .toArray(Predicate[]::new);

        this.query.where(predicates);

        return this;
    }

    //-----クエリの取得-------
    public Subquery<T> getQuery() {
        return this.query.select(this.getExpression(path.getArrayAttribute()));

    }

    @Override
    protected Path getExpression(Attribute... attribute) {
        if (attribute.length == 0) {
            return null;
        }
        From baseRoot = null;

        Attribute first = attribute[0];

        Class<?> firstClass = first.getDeclaringType().getJavaType();

        if (this.root.getJavaType() == firstClass) {
            baseRoot = this.root;
        }

        if (baseRoot == null) {
            throw new RuntimeException("Not Found Root");
        }

        Path result = baseRoot.get(first.getName());

        for (int i = 1; i < attribute.length; i++) {
            result = result.get(attribute[i].getName());
        }

        return result;
    }

}
