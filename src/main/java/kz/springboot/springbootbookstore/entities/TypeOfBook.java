package kz.springboot.springbootbookstore.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "t_type_of_book")
public class TypeOfBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_of_type")
    private String nameOfType;
}
