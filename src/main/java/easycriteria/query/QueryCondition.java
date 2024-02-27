package easycriteria.query;

import easycriteria.path.Root;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.Attribute;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class QueryCondition {
    protected CriteriaBuilder criteriaBuilder;

    //-----条件文------
    //-----EQUAL------
    public <Y> Predicate equal(Root<Y> leftRoot, Y rightObject) {
        return criteriaBuilder.equal(getExpression(leftRoot.getArrayAttribute()), rightObject);
    }

    public <Y> Predicate equal(Root<Y> leftRoot, Expression<Y> rightExpression) {
        return criteriaBuilder.equal(getExpression(leftRoot.getArrayAttribute()), rightExpression);
    }

    public <Y> Predicate equal(Root<Y> leftRoot, Attribute<?, Y> rightAttribute) {
        return criteriaBuilder.equal(getExpression(leftRoot.getArrayAttribute()), getExpression(rightAttribute));
    }

    public <Y> Predicate equal(Attribute<?, Y> leftAttribute, Expression<Y> rightExpression) {
        return criteriaBuilder.equal(getExpression(leftAttribute), rightExpression);
    }

    public <Y> Predicate equal(Attribute<?, Y> leftAttribute, Attribute<?, Y> rightAttribute) {
        return criteriaBuilder.equal(getExpression(leftAttribute), getExpression(rightAttribute));
    }

    public <Y> Predicate equal(Attribute<?, Y> leftAttribute, Y rightObject) {
        return criteriaBuilder.equal(getExpression(leftAttribute), rightObject);
    }

    public <Y> Predicate equal(Root<Y> leftRoot, Root<Y> rightRoot) {
        return criteriaBuilder.equal(getExpression(leftRoot.getArrayAttribute()), getExpression(rightRoot.getArrayAttribute()));
    }

    public <Y> Predicate equal(Attribute<?, Y> leftAttribute, Root<Y> rightRoot) {
        return criteriaBuilder.equal(getExpression(leftAttribute), getExpression(rightRoot.getArrayAttribute()));
    }

    //-----LIKE------
    public Predicate like(Root<String> leftRoot, String stringValue) {
        return criteriaBuilder.like(getExpression(leftRoot.getArrayAttribute()), stringValue);
    }

    public Predicate like(Attribute<?, String> leftAttribute, String stringValue) {
        return criteriaBuilder.like(getExpression(leftAttribute), stringValue);
    }

    public Predicate like(Root<String> leftRoot, List<String> stringValueList) {
        List<Predicate> predicates = new ArrayList<>();
        Expression expression = getExpression(leftRoot.getArrayAttribute());
        if (CollectionUtils.isNotEmpty(stringValueList)) {
            stringValueList.forEach(value -> predicates.add(criteriaBuilder.like(expression, value)));
        }
        return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
    }

    public Predicate like(Attribute<?, String> leftAttribute, List<String> stringValueList) {
        List<Predicate> predicates = new ArrayList<>();
        Expression expression = getExpression(leftAttribute);
        if (CollectionUtils.isNotEmpty(stringValueList)) {
            stringValueList.forEach(value -> predicates.add(criteriaBuilder.like(expression, value)));
        }
        return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
    }

    //-----NOT EQUAL------
    public <Y> Predicate notEqual(Root<Y> leftRoot, Y rightObject) {
        return criteriaBuilder.notEqual(getExpression(leftRoot.getArrayAttribute()), rightObject);
    }

    public <Y> Predicate notEqual(Root<Y> leftRoot, Expression<Y> rightExpression) {
        return criteriaBuilder.notEqual(getExpression(leftRoot.getArrayAttribute()), rightExpression);
    }

    public <Y> Predicate notEqual(Root<Y> leftRoot, Attribute<?, Y> rightAttribute) {
        return criteriaBuilder.notEqual(getExpression(leftRoot.getArrayAttribute()), getExpression(rightAttribute));
    }

    public <Y> Predicate notEqual(Attribute<?, Y> leftAttribute, Expression<Y> rightExpression) {
        return criteriaBuilder.notEqual(getExpression(leftAttribute), rightExpression);
    }

    public <Y> Predicate notEqual(Attribute<?, Y> leftAttribute, Attribute<?, Y> rightAttribute) {
        return criteriaBuilder.notEqual(getExpression(leftAttribute), getExpression(rightAttribute));
    }

    public <Y> Predicate notEqual(Attribute<?, Y> leftAttribute, Y rightObject) {
        return criteriaBuilder.notEqual(getExpression(leftAttribute), rightObject);
    }

    public <Y> Predicate notEqual(Root<Y> leftRoot, Root<Y> rightRoot) {
        return criteriaBuilder.notEqual(getExpression(leftRoot.getArrayAttribute()), getExpression(rightRoot.getArrayAttribute()));
    }

    //-----NOT LIKE------
    public Predicate notLike(Root<String> leftRoot, String stringValue) {
        return criteriaBuilder.notLike(getExpression(leftRoot.getArrayAttribute()), stringValue);
    }

    public Predicate notLike(Attribute<?, String> leftAttribute, String stringValue) {
        return criteriaBuilder.notLike(getExpression(leftAttribute), stringValue);
    }


    public Predicate notLike(Root<String> leftRoot, List<String> stringValueList) {
        List<Predicate> predicates = new ArrayList<>();
        Expression expression = getExpression(leftRoot.getArrayAttribute());
        if (CollectionUtils.isNotEmpty(stringValueList)) {
            stringValueList.forEach(value -> predicates.add(criteriaBuilder.notLike(expression, value)));
        }
        return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
    }

    public Predicate notLike(Attribute<?, String> leftAttribute, List<String> stringValueList) {
        List<Predicate> predicates = new ArrayList<>();
        Expression expression = getExpression(leftAttribute);
        if (CollectionUtils.isNotEmpty(stringValueList)) {
            stringValueList.forEach(value -> predicates.add(criteriaBuilder.notLike(expression, value)));
        }
        return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
    }

    //-----以上------
    public <Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Root<Y> leftRoot, Y y) {
        return criteriaBuilder.greaterThanOrEqualTo(this.getExpression(leftRoot.getArrayAttribute()), y);
    }

    public <Y extends Comparable<? super Y>> Predicate greaterThanOrEqualTo(Attribute<?, Y> leftAttribute, Y y) {
        return criteriaBuilder.greaterThanOrEqualTo(this.getExpression(leftAttribute), y);
    }

    public <Y> Predicate greaterThanOrEqualTo(Attribute<?, Y> leftAttribute, Attribute<?, Y> rightAttribute) {
        return criteriaBuilder.greaterThanOrEqualTo(this.getExpression(leftAttribute), getExpression(rightAttribute));
    }

    public <Y> Predicate greaterThanOrEqualTo(Root<Y> leftRoot, Root<Y> rightRoot) {
        return criteriaBuilder.greaterThanOrEqualTo(this.getExpression(leftRoot.getArrayAttribute()), getExpression(rightRoot.getArrayAttribute()));
    }

    //-----より上------
    public <Y extends Comparable<? super Y>> Predicate greaterThan(Root<Y> leftRoot, Y y) {
        return criteriaBuilder.greaterThan(this.getExpression(leftRoot.getArrayAttribute()), y);
    }

    public <Y extends Comparable<? super Y>> Predicate greaterThan(Attribute<?, Y> leftAttribute, Y y) {
        return criteriaBuilder.greaterThan(this.getExpression(leftAttribute), y);
    }

    public <Y> Predicate greaterThan(Attribute<?, Y> leftAttribute, Attribute<?, Y> rightAttribute) {
        return criteriaBuilder.greaterThan(this.getExpression(leftAttribute), getExpression(rightAttribute));
    }

    public <Y> Predicate greaterThan(Root<Y> leftRoot, Root<Y> rightRoot) {
        return criteriaBuilder.greaterThan(this.getExpression(leftRoot.getArrayAttribute()), getExpression(rightRoot.getArrayAttribute()));
    }

    //-----以下------
    public <Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Root<Y> leftRoot, Y y) {
        return criteriaBuilder.lessThanOrEqualTo(this.getExpression(leftRoot.getArrayAttribute()), y);
    }

    public <Y extends Comparable<? super Y>> Predicate lessThanOrEqualTo(Attribute<?, Y> leftAttribute, Y y) {
        return criteriaBuilder.lessThanOrEqualTo(this.getExpression(leftAttribute), y);
    }

    public <Y> Predicate lessThanOrEqualTo(Attribute<?, Y> leftAttribute, Attribute<?, Y> rightAttribute) {
        return criteriaBuilder.lessThanOrEqualTo(this.getExpression(leftAttribute), getExpression(rightAttribute));
    }

    public <Y> Predicate lessThanOrEqualTo(Root<Y> leftRoot, Root<Y> rightRoot) {
        return criteriaBuilder.lessThanOrEqualTo(this.getExpression(leftRoot.getArrayAttribute()), getExpression(rightRoot.getArrayAttribute()));
    }

    //-----より下------
    public <Y extends Comparable<? super Y>> Predicate lessThan(Root<Y> leftRoot, Y y) {
        return criteriaBuilder.lessThan(this.getExpression(leftRoot.getArrayAttribute()), y);
    }

    public <Y extends Comparable<? super Y>> Predicate lessThan(Attribute<?, Y> leftAttribute, Y y) {
        return criteriaBuilder.lessThan(this.getExpression(leftAttribute), y);
    }

    public <Y> Predicate lessThan(Attribute<?, Y> leftAttribute, Attribute<?, Y> rightAttribute) {
        return criteriaBuilder.lessThan(this.getExpression(leftAttribute), getExpression(rightAttribute));
    }

    public <Y> Predicate lessThan(Root<Y> leftRoot, Root<Y> rightRoot) {
        return criteriaBuilder.lessThan(this.getExpression(leftRoot.getArrayAttribute()), getExpression(rightRoot.getArrayAttribute()));
    }

    //-----IN句------
    public <Y> Predicate in(Root<Y> leftRoot, Collection<Y> values) {
        return getExpression(leftRoot.getArrayAttribute()).in(values);
    }

    public <Y> Predicate in(Attribute<?, Y> leftAttribute, Collection<Y> values) {
        return getExpression(leftAttribute).in(values);
    }

    public <Y> Predicate in(Root<Y> leftRoot, Expression<Y> expression) {
        return getExpression(leftRoot.getArrayAttribute()).in(expression);
    }

    public <Y> Predicate in(Attribute<?, Y> leftAttribute, Expression<Y> expression) {
        return getExpression(leftAttribute).in(expression);
    }

    //-----IN句------
    public <Y> Predicate notIn(Root<Y> leftRoot, Collection<?> values) {
        return this.criteriaBuilder.not(getExpression(leftRoot.getArrayAttribute()).in(values));
    }

    public <Y> Predicate notIn(Attribute<?, Y> leftAttribute, Collection<Y> values) {
        return this.criteriaBuilder.not(getExpression(leftAttribute).in(values));
    }

    public <Y> Predicate notIn(Root<Y> leftRoot, Expression<Y> expression) {
        return this.criteriaBuilder.not(getExpression(leftRoot.getArrayAttribute()).in(expression));
    }

    public <Y> Predicate notIn(Attribute<?, Y> leftAttribute, Expression<Y> expression) {
        return this.criteriaBuilder.not(getExpression(leftAttribute).in(expression));
    }

    //Max,Min
    public <Y extends Number> Expression<Y> max(Root<Y> leftRoot) {
        return this.criteriaBuilder.max(this.getExpression(leftRoot.getArrayAttribute()));
    }

    public <Y extends Number> Expression<Y> min(Root<Y> leftRoot) {
        return this.criteriaBuilder.min(this.getExpression(leftRoot.getArrayAttribute()));
    }

    //-----AND------
    public Predicate and(Function<QueryCondition, Predicate>... functions) {
        return criteriaBuilder.and(this.toArray(functions));
    }

    //-----OR------
    public Predicate or(Function<QueryCondition, Predicate>... functions) {
        return criteriaBuilder.or(this.toArray(functions));
    }

    protected Predicate[] toArray(Function<QueryCondition, Predicate>... functions) {
        return Stream.of(functions).map(func -> func.apply(this)).toArray(Predicate[]::new);
    }

    protected abstract Path getExpression(Attribute... attribute);

}
