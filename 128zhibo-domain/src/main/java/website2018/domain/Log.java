package website2018.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import website2018.base.BaseEntity;

@Entity
@Table(name = "zhibo_log")
public class Log extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Date addTime;

    @ManyToOne
    @JoinColumn(name = "account_id")
    public Account account;
    public String ip;
    public String behaviour;
    public String link;
}
