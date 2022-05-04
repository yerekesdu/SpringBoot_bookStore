package kz.springboot.springbootbookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "t_books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String bookName;

    @Column(columnDefinition="TEXT")
    private String description;

    private int price;

    private int amount;

    @Column(length = 100)
    private String author;

    @ManyToOne(fetch = FetchType.EAGER)
    private TypeOfBook typeOfBook;
}
