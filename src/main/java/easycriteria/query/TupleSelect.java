package easycriteria.query;

import easycriteria.path.CriteriaPath;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Selection;

public class TupleSelect extends Select<Tuple> {

    public TupleSelect(CriteriaQuery<Tuple> query, CriteriaBuilder criteriaBuilder, CriteriaPath... path) {
        super(query, criteriaBuilder, SelectType.TUPLE, path);
    }

    @Override
    public CriteriaQuery<Tuple> getQuery() {
        Selection<?>[] selections = new Selection[this.path.length];

        for (int i = 0; i < this.path.length; i++) {
            selections[i] = this.getSelection(this.path[i]);
        }

        return this.query.select(this.criteriaBuilder.tuple(selections));

    }

}
