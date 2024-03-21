package sample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class SampleTable1PK {

    @Column(name = "SAMPLE_CODE")
    private String code;

    @Column(name = "SAMPLE_TYPE")
    private int type;

}

