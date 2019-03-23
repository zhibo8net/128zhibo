package website2018.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.utils.text.EncodeUtil;
import org.springside.modules.utils.text.HashUtil;

import website2018.domain.Account;
import website2018.exception.ErrorCode;
import website2018.exception.ServiceException;
import website2018.filter.RestFilter;
import website2018.repository.AccountDao;

@Service
public class AccountService {

    @Autowired
    AccountDao accountDao;

    @Transactional(readOnly = true)
    public List<Account> findAll(Specification spec) {
        return accountDao.findAll(spec);
    }

    @Transactional(readOnly = true)
    public Page<Account> findAll(Pageable pageable) {
        return accountDao.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Account> findAll(Specification<Account> specification, Pageable pageable) {
        return accountDao.findAll(specification, pageable);
    }

    @Transactional(readOnly = true)
    public Account findOne(Long id) {
        return accountDao.findOne(id);
    }

    @Transactional
    public void create(Account account) {
        account.password = hashPassword(account.password);
        accountDao.save(account);
    }

    @Transactional
    public void modify(Account account) {

        Account orginalAccount = accountDao.findOne(account.id);

        if (orginalAccount == null) {
            throw new ServiceException("管理员不存在", ErrorCode.BAD_REQUEST);
        }
        
        orginalAccount.mobile = account.mobile;
        orginalAccount.name = account.name;
        if(account.password != null) {//如果要修改密码
            orginalAccount.password = hashPassword(account.password);
        }
        orginalAccount.status = account.status;
        
        accountDao.save(orginalAccount);
    }

    @Transactional
    public void delete(Long id) {
        Account account = accountDao.findOne(id);

        if (account == null) {
            throw new ServiceException("电视不存在", ErrorCode.BAD_REQUEST);
        }

        accountDao.delete(id);
    }

    private String hashPassword(String password) {
        return EncodeUtil.encodeBase64(HashUtil.sha1(password));
    }

    public void resetPassword(String oldPassword, String newPassword) {
        Account currentAccount = RestFilter.accountHolder.get();
        if(currentAccount.password.equals(hashPassword(oldPassword))) {//旧密码输入正确
            String newHash = hashPassword(newPassword);
            currentAccount.password = newHash;
            accountDao.save(currentAccount);
        }else {
            throw new ServiceException("旧密码输入错误", ErrorCode.UNAUTHORIZED);
        }
    }
    
}
