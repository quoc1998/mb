package com.backend.bank.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "id_Sequence")
    @SequenceGenerator(name = "id_Sequence", sequenceName = "ID_SEQ")
    @Column(name = "user_id", nullable = false)
    private int id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "created_at", nullable = true)
    private Date createdAt;



    @Column(name = "deleted_at")
    private Date deletedAt;
//    // MapopedBy trỏ tới tên biến Address ở trong Person.
    @ManyToMany(fetch = FetchType.EAGER)
    //fetch = FetchType.LAZY tức là khi bạn find, select đối tượng Company từ database thì nó sẽ không lấy các đối tượng Employee liên quan
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonBackReference

    private List<Role> roles;


    @LazyCollection(LazyCollectionOption.FALSE)//su dung khi moi quan he
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonBackReference

    List<UserPrivilege> userPrivileges;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
