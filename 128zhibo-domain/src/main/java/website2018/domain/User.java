package website2018.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/9/24.
 */
@Entity
@Table(name = "zhibo_user")
public class User {

    public String userName;

    public String userNickName;

    public String mobile;

    public String password;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Date addTime;
    public Date updateTime;


    public String userLink;


    public String userEmail;
}
