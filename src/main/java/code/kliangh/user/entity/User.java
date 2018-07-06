package code.kliangh.user.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    private static final long serialVersionUID = 2019590492905741320L;

    @Id
    @Column(name = "uid", nullable = false)
    private String uid;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "remark")
    private String remark;
}
