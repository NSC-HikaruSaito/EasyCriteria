package easycriteria.query;

import easycriteria.path.CriteriaPath;
import easycriteria.path.Entity;
import easycriteria.path.Root;

import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Select<S> extends QueryCondition {

    /*
     * Selectクラスのコンストラクタに渡す要素のパス
     * TupleSelectCriteriaの場合、Selectするクラスのパス
     */
    protected CriteriaPath[] path;

    //クエリ
    protected CriteriaQuery<S> query;

    //ルート
    protected jakarta.persistence.criteria.Root<?> root;

    //Join
    protected List<Join<?, ?>> joinList = new ArrayList<>();

    protected SelectType selectType;

    protected Select(Class<S> constructClass, CriteriaBuilder criteriaBuilder, CriteriaPath... path) {
        this.criteriaBuilder = criteriaBuilder;
        this.query = criteriaBuilder.createQuery(constructClass);
        this.path = path;
        this.selectType = SelectType.NEW;
    }

    protected Select(CriteriaQuery<S> query, CriteriaBuilder criteriaBuilder, Root<S> path) {
        this.criteriaBuilder = criteriaBuilder;
        this.query = query;
        this.path = new CriteriaPath[]{path};
        this.selectType = SelectType.SELECT;
    }

    protected Select(CriteriaQuery<S> query, CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
        this.query = query;
        this.path = new CriteriaPath[0];
        this.selectType = SelectType.SELECT;
    }

    protected Select(CriteriaQuery<S> query, CriteriaBuilder criteriaBuilder, SelectType selectType, CriteriaPath... path) {
        this.criteriaBuilder = criteriaBuilder;
        this.query = query;
        this.path = path;
        this.selectType = selectType;
    }

    //-----SELECT句-------
    //Entityクラスを取得する場合
    public static <S> Select<S> select(CriteriaBuilder criteriaBuilder, Class<S> entity) {
        if (entity.getAnnotation(jakarta.persistence.Entity.class) == null) {
            throw new RuntimeException("This class is not entity.");
        }
        return new Select(criteriaBuilder.createQuery(entity), criteriaBuilder);
    }

    //Entityのパラメータを取得する場合
    public static <S> Select<S> select(CriteriaBuilder criteriaBuilder, Root<S> path) {
        return new Select(criteriaBuilder.createQuery(path.getJavaType()), criteriaBuilder, path);
    }

    //Tupleクラスを取得する場合
    public static TupleSelect tuple(CriteriaBuilder criteriaBuilder, CriteriaPath... tuplePath) {
        return new TupleSelect(criteriaBuilder.createTupleQuery(), criteriaBuilder, tuplePath);
    }

    //クラスを生成する場合
    public static <S> Select<S> construct(CriteriaBuilder criteriaBuilder, Class<S> constructClass, CriteriaPath... path) {
        return new Select(constructClass, criteriaBuilder, path);
    }

    public Select<S> distinct() {
        this.query.distinct(true);
        return this;
    }

    //-----FROM句-------
    public <F> Select<S> from(Class<F> fromEntity) {
        if (fromEntity.getAnnotation(jakarta.persistence.Entity.class) == null) {
            throw new RuntimeException("This class is not entity.");
        }
        this.root = query.from(fromEntity);
        return this;
    }

    //-----クエリの取得-------
    public CriteriaQuery<S> getQuery() {

        switch (this.selectType) {
            case SELECT:
                if (path.length == 0) {
                    return query;
                }
                return this.query.select(getSelection(this.path[0]));

            case NEW:
                Selection<?>[] selections = new Selection[this.path.length];
                for (int i = 0; i < this.path.length; i++) {
                    selections[i] = this.getSelection(this.path[i]);
                }

                return this.query.select(criteriaBuilder.construct(query.getResultType(), selections));

            default:
                throw new RuntimeException("Not Found SelectType");

        }
    }

    //-----JOIN句-----
    //-----JOIN-----
    public Select<S> join(Class<?> parent, SingularAttribute attribute) {
        joinList.add(this.getBaseRoot(parent).join(attribute));
        return this;
    }

    public Select<S> join(Class<?> parent, CollectionAttribute attribute) {
        joinList.add(this.getBaseRoot(parent).join(attribute));
        return this;
    }

    public Select<S> join(Class<?> parent, ListAttribute attribute) {
        joinList.add(this.getBaseRoot(parent).join(attribute));
        return this;
    }

    public Select<S> join(Class<?> parent, SetAttribute attribute) {
        joinList.add(this.getBaseRoot(parent).join(attribute));
        return this;
    }

    public Select<S> fetchJoin(Class<?> parent, SingularAttribute attribute) {
        joinList.add((Join) this.getBaseRoot(parent).fetch(attribute));
        return this;

    }

    //-----FETCH-----
    public Select<S> fetchJoin(Class<?> parent, CollectionAttribute attribute) {
        joinList.add((Join) this.getBaseRoot(parent).fetch(attribute));
        return this;
    }

    public Select<S> fetchJoin(Class<?> parent, ListAttribute attribute) {
        joinList.add((Join) this.getBaseRoot(parent).fetch(attribute));
        return this;
    }

    public Select<S> fetchJoin(Class<?> parent, SetAttribute attribute) {
        joinList.add((Join) this.getBaseRoot(parent).fetch(attribute));
        return this;
    }

    //-----FETCH LEFT JOIN-----
    public Select<S> fetchLeftJoin(Class<?> parent, SingularAttribute attribute) {
        joinList.add((Join) this.getBaseRoot(parent).fetch(attribute, JoinType.LEFT));
        return this;
    }

    public Select<S> fetchLeftJoin(Class<?> parent, CollectionAttribute attribute) {
        joinList.add((Join) this.getBaseRoot(parent).fetch(attribute, JoinType.LEFT));
        return this;
    }

    public Select<S> fetchLeftJoin(Class<?> parent, ListAttribute attribute) {
        joinList.add((Join) this.getBaseRoot(parent).fetch(attribute, JoinType.LEFT));
        return this;
    }

    public Select<S> fetchLeftJoin(Class<?> parent, SetAttribute attribute) {
        joinList.add((Join) this.getBaseRoot(parent).fetch(attribute, JoinType.LEFT));
        return this;
    }

    //-----ON-----
    public Select<S> on(Class<?> joinClass, Predicate... predicate) {
        Join join = (Join) this.getBaseRoot(joinClass);
        join.on(predicate);
        return this;

    }

    //-----WEHRE句------
    public Select<S> where(Function<Select<S>,Predicate>...functions) {
        Predicate[] predicates = Stream.of(functions)
                .map(func -> func.apply(this))
                .toArray(Predicate[]::new);
        this.query.where(predicates);

        return this;
    }


    //-----サブクエリ------
    public <T> SubSelect<T> subQuery(Root<T> path) {
        return new SubSelect(this.criteriaBuilder, this.query.subquery(path.getJavaType()), path);
    }

    //-----ORDER BY------
    public Select<S> orderBy(Order... order) {
        this.query.orderBy(order);
        return this;
    }

    public Order asc(Attribute... attribute) {
        return criteriaBuilder.asc(this.getExpression(attribute));
    }

    public Order desc(Attribute... attribute) {
        return criteriaBuilder.desc(this.getExpression(attribute));
    }

    //--------------------------------------------------------
    //AttributeをもとにExpressionを返す
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
        } else {
            for (int i = 0; i < joinList.size(); i++) {
                if (joinList.get(i).getJavaType() == firstClass) {
                    baseRoot = joinList.get(i);
                }
            }
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

    //EntityのもととなるRootを返す
    protected From getBaseRoot(Class<?> entity) {
        From baseRoot = null;

        if (this.root.getJavaType() == entity) {

            baseRoot = this.root;
        } else {
            for (int i = 0; i < joinList.size(); i++) {
                if (joinList.get(i).getJavaType() == entity) {
                    baseRoot = joinList.get(i);
                }
            }
        }
        if (baseRoot == null) {
            throw new RuntimeException("Not Found Root");
        }

        return baseRoot;
    }

    protected Selection getSelection(CriteriaPath path) {
        switch (path.getType()) {
            case ENTITY:
                Entity entityPath = (Entity) path;
                return this.getBaseRoot(entityPath.getEntityClass());
            case ROOT:
                Root rootPath = (Root) path;
                return this.getExpression(rootPath.getArrayAttribute());

            case MAX:
                Root maxPath = (Root) path;
                return this.max(maxPath);

            case MIN:
                Root minPath = (Root) path;
                return this.min(minPath);

            default:
                throw new RuntimeException("Not Found PathType");

        }
    }

}
