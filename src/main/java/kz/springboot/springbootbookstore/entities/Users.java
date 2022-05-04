package kz.springboot.springbootbookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "user_avatar")
    private String userAvatar;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Roles> roles;

}
