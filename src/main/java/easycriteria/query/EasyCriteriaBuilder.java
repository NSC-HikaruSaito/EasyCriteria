package easycriteria.query;

import easycriteria.path.CriteriaPath;
import easycriteria.path.Root;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;

public class EasyCriteriaBuilder {
    private EntityManager entityManager;

    private EasyCriteriaBuilder(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public static EasyCriteriaBuilder of(EntityManager entityManager){
        return new EasyCriteriaBuilder(entityManager);
    }

    public <S> Select<S> buildSelect(Class<S> entity) {
        return Select.select(this.entityManager.getCriteriaBuilder(), entity);
    }

    public <S> Select<S> buildSelect(Root<S> path) {
        return Select.select(this.entityManager.getCriteriaBuilder(), path);
    }

    public <S> Select<S> buildSelectConstruct(Class<S> constructClass, CriteriaPath... path) {
        return Select.construct(this.entityManager.getCriteriaBuilder(),constructClass, path);
    }

    public Select<Tuple> buildTuple(CriteriaPath... tuplePath) {
        return Select.tuple(this.entityManager.getCriteriaBuilder(), tuplePath);
    }

    public <U> Update<U> buildUpdate(Class<U> entity) {
        return new Update(this.entityManager.getCriteriaBuilder(), entity);
    }

}
