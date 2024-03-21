# EasyCriteria

## Overview
EasyCriteriaは、JavaのCriteria APIの複雑さを簡素化するためのラッパーライブラリです。  
元々は社内でCriteria APIを利用していましたが、学習コストの高さに苦戦する人が多かったです。  
ただ、SQLの構文（例えば、SelectがあってFrom句があってその後にWhere句があって）はみんな分かる状態であることに着目し、
SQLの構文と同じようにメソッドチェーンして組めるようにしました。  
部内の人間（約30人ほど）はみんなこれを使ってくれるようになりました。  
少しだけ可読性が良くなって学習コストが下がったかな？  

EasyCriteria is a wrapper library designed to simplify the complexity of the Java Criteria API.  
 Originally, we were using the Criteria API internally, but many struggled with its high learning curve.  
However, I realized that everyone was familiar with SQL syntax (for example, having a Select followed by a From clause, then a Where clause), so we developed EasyCriteria to allow method chaining in a way similar to SQL syntax.  
Everyone in the department (about 30 people) started using this.  
A little better readability and lower learning costs?

## Prerequisites
- Java 11 or higher

## Sample
[SampleRepository.java](https://github.com/NSC-HikaruSaito/EasyCriteria/blob/main/src/main/java/sample/repository/SampleRepository.java)

### Select Entity Class
Before
```java
public class BeforeSample {

    @PersistenceContext(unitName="Sample")
    private EntityManager em;

    public void sample() {
        CriteriaBuilder builder = this.em.getCriteriaBuilder();
        CriteriaQuery<SampleTable1> query = builder.createQuery(SampleTable1.class);

        Root<SampleTable1> root = query.from(SampleTable1.class);

        query.select(root);
        query.where(
                root.get(SampleTable1_.pk).get(SampleTable1PK_.code).in(codeList),
                builder.equal(root.get(SampleTable1_.pk).get(SampleTable1PK_.type), 1),
                builder.like(root.get(SampleTable1_.explanation), "%sample%")
        );

        this.em.createQuery(query).getResultList().forEach(System.out::println);
    }
}
```
After
```java
public class AfterSample {

    @PersistenceContext(unitName="Sample")
    private EntityManager em;

    public void sample() {
        Select<SampleTable1> query = EasyCriteriaBuilder.of(em).buildSelect(SampleTable1.class)
                .from(SampleTable1.class)
                .where(
                        q -> q.in(Root.path(SampleTable1_.pk).get(SampleTable1PK_.code), codeList),
                        q -> q.equal(Root.path(SampleTable1_.pk).get(SampleTable1PK_.type), 1),
                        q -> q.like(Root.path(SampleTable1_.explanation), "%sample%")
                );

        this.em.createQuery(query.getQuery()).getResultList().forEach(System.out::println);
    }
}
```
