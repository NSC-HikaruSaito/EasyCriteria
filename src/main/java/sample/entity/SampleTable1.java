package sample.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "HOGE_SAMPLE1")
@NoArgsConstructor
@Getter
@Setter
public class SampleTable1 {
    @EmbeddedId
    private SampleTable1PK pk;

    @Column(name = "SAMPLE_EXPLANATION")
    private String explanation;

}
