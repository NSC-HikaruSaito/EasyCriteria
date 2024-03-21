package sample.entity;


import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SampleTable1PK.class)
public class SampleTable1PK_ {
    public static volatile SingularAttribute<SampleTable1PK, String> code;
    public static volatile SingularAttribute<SampleTable1PK, Integer> type;

}
