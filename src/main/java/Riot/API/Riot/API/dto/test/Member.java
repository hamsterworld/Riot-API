package Riot.API.Riot.API.dto.test;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
//@SequenceGenerator(
//        name="membermemberrr_seq",
//        sequenceName = "memberrr_seq",
//        initialValue = 20,allocationSize = 100
//)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "hamster_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn
    private Team team;

}
