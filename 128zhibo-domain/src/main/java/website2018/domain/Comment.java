package website2018.domain;


import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "zhibo_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;
    public Date updateTime;
    public Integer type;
    public Integer userType;

    public Integer relId;
    public String comment;
    @OneToOne
    @JoinColumn(name = "user_id")
    public User user;


}
