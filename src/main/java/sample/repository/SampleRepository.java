package sample.repository;


import easycriteria.path.Root;
import easycriteria.query.EasyCriteriaBuilder;
import easycriteria.query.Select;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import sample.entity.*;

import java.util.Arrays;
import java.util.List;

public class SampleRepository {

    @PersistenceContext(unitName = "Sample")
    private EntityManager em;

    /**
     * Select Entity Class
     */
    public void sample1() {
        List<String> codeList = Arrays.asList("001", "002", "003");

//        CriteriaBuilder builder = this.em.getCriteriaBuilder();
//        CriteriaQuery<SampleTable1> query = builder.createQuery(SampleTable1.class);
//
//        jakarta.persistence.criteria.Root<SampleTable1> root = query.from(SampleTable1.class);
//
//        query.select(root);
//        query.where(
//                root.get(SampleTable1_.pk).get(SampleTable1PK_.code).in(codeList),
//                builder.equal(root.get(SampleTable1_.pk).get(SampleTable1PK_.type), 1),
//                builder.like(root.get(SampleTable1_.explanation), "%sample%")
//        );
//
//        this.em.createQuery(query).getResultList().forEach(System.out::println);


        Select<SampleTable1> query = EasyCriteriaBuilder.of(em).buildSelect(SampleTable1.class)
                .from(SampleTable1.class)
                .where(
                        q -> q.in(Root.path(SampleTable1_.pk).get(SampleTable1PK_.code), codeList),
                        q -> q.equal(Root.path(SampleTable1_.pk).get(SampleTable1PK_.type), 1),
                        q -> q.like(Root.path(SampleTable1_.explanation), "%sample%")
                );

        this.em.createQuery(query.getQuery()).getResultList().forEach(System.out::println);

    }


    /**
     * Select Entity's Value
     */
    public void sample2() {

//        CriteriaBuilder builder = this.em.getCriteriaBuilder();
//        CriteriaQuery<String> query = builder.createQuery(String.class);
//
//        jakarta.persistence.criteria.Root<SampleTable1> root = query.from(SampleTable1.class);
//        query.select(root.get(SampleTable1_.pk).get(SampleTable1PK_.code));
//        query.where(
//                builder.equal(root.get(SampleTable1_.pk).get(SampleTable1PK_.type), 1)
//        );
//
//        this.em.createQuery(query).getResultList().forEach(System.out::println);


        Select<String> query = EasyCriteriaBuilder.of(em).buildSelect((Root.path(SampleTable1_.pk).get(SampleTable1PK_.code)))
                .from(SampleTable1.class)
                .where(
                        q -> q.equal(Root.path(SampleTable1_.pk).get(SampleTable1PK_.type), 1)
                );

        this.em.createQuery(query.getQuery()).getResultList().forEach(System.out::println);

    }


    /**
     * Constructor Expression
     */
    public void sample3() {

//        CriteriaBuilder builder = this.em.getCriteriaBuilder();
//        CriteriaQuery<SampleDto> query = builder.createQuery(SampleDto.class);
//
//        jakarta.persistence.criteria.Root<SampleTable1> root = query.from(SampleTable1.class);
//        query.select(
//                builder.construct(
//                        SampleDto.class,
//                        root.get(SampleTable1_.pk).get(SampleTable1PK_.code),
//                        root.get(SampleTable1_.pk).get(SampleTable1PK_.type)
//                )
//        );
//        query.where(
//                builder.equal(root.get(SampleTable1_.pk).get(SampleTable1PK_.type), 1)
//        );
//
//        this.em.createQuery(query).getResultList().forEach(System.out::println);


        Select<SampleDto> query = EasyCriteriaBuilder.of(em).buildSelectConstruct(
                        SampleDto.class,
                        Root.path(SampleTable1_.pk).get(SampleTable1PK_.code),
                        Root.path(SampleTable1_.pk).get(SampleTable1PK_.type)
                )
                .from(SampleTable1.class)
                .where(
                        q -> q.equal(Root.path(SampleTable1_.pk).get(SampleTable1PK_.type), 1)
                );

        this.em.createQuery(query.getQuery()).getResultList().forEach(System.out::println);

    }

    public class SampleDto {
        private String code;
        private int type; // 例として、TestTableにある他のフィールド

        public SampleDto(String code, int type) {
            this.code = code;
            this.type = type;
        }
    }


}
