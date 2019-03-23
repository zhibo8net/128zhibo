package website2018.repository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import website2018.domain.Account;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountDaoTest {

    @Autowired
    private AccountDao accountDao;

    @Test
    public void findAll() {
        System.out.println(((List<Account>) accountDao.findAll()).size());
    }

    public static void main(String[] args) {
        String s = "[QQ] 01月29日 法甲第23轮 马赛vs摩纳哥 上半场录像 地址：http://v.qq.com/iframe/player.html?tiny=0&auto=1&vid=z0025jet38w";
        String pattern = "(英超|意甲|西甲|法甲|德甲|中超|欧冠|NBA|CBA)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(s);
        if (m.find()) {
            String g = m.group();
            System.out.println(g);
        }
    }
}
