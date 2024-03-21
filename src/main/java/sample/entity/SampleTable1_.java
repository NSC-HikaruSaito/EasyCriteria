package sample.entity;


import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SampleTable1.class)
public class SampleTable1_ {
    public static volatile SingularAttribute<SampleTable1, SampleTable1PK> pk;
    public static volatile SingularAttribute<SampleTable1, String> explanation;

}
