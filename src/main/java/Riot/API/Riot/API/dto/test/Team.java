package Riot.API.Riot.API.dto.test;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
//@SequenceGenerator(
//        name="temateammm_seq",
//        sequenceName = "teammm_seq",
//        initialValue = 20,allocationSize = 100
//)
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "hamsterteam_id")
    private Long id;

    private String teamname;

    @OneToMany(mappedBy = "team")
    private List<Member> members;

}
