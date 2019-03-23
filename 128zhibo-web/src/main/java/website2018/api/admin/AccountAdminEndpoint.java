package website2018.api.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.utils.mapper.BeanMapper;
import org.springside.modules.web.MediaTypes;

import website2018.base.BaseEndPoint;
import website2018.base.BaseService;
import website2018.domain.Account;
import website2018.dto.admin.AccountAdminDTO;
import website2018.filter.RestFilter;
import website2018.service.admin.AccountService;

// Spring Restful MVC Controller的标识, 直接输出内容，不调用template引擎.
@RestController
public class AccountAdminEndpoint extends BaseEndPoint {

    private static Logger logger = LoggerFactory.getLogger(AccountAdminEndpoint.class);

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/api/admin/accounts", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<AccountAdminDTO> listAllAccount() {

        assertSuper();

        List<Account> accounts = accountService.findAll(buildSpecification(BaseService.base(), Account.class));

        return BeanMapper.mapList(accounts, Account.class, AccountAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/accounts/page", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Page<AccountAdminDTO> listByPage(HttpServletRequest request, Pageable pageable) {

        assertSuper();

        Page<Account> orders = accountService.findAll(buildSpecification(request, Account.class), new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), getSort("id", "DIRE")));

        List<AccountAdminDTO> dtos = BeanMapper.mapList(orders, Account.class, AccountAdminDTO.class);

        Page<AccountAdminDTO> dtoPage = new PageImpl(dtos, pageable, orders.getTotalElements());

        logService.log("查询用户账号表", null);

        return dtoPage;
    }

    @RequestMapping(value = "/api/admin/accounts/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public AccountAdminDTO listOneAccount(@PathVariable("id") Long id) {

        assertSuper();

        Account Account = accountService.findOne(id);

        return BeanMapper.map(Account, AccountAdminDTO.class);
    }

    @RequestMapping(value = "/api/admin/accounts", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void createAccount(@RequestBody AccountAdminDTO accountAdminDTO) {

        assertSuper();

        Account account = BeanMapper.map(accountAdminDTO, Account.class);
        account.password = accountAdminDTO.plainPassword;

        account.addTime = new Date();
        accountService.create(account);
        
        logService.log("添加用户账号", "/accountForm/" + account.id);
    }

    @RequestMapping(value = "/api/admin/accounts/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON_UTF_8)
    public void modifyAccount(@RequestBody AccountAdminDTO accountAdminDTO) {

        assertSuper();

        Account account = BeanMapper.map(accountAdminDTO, Account.class);
        account.password = accountAdminDTO.plainPassword;

        account.addTime = new Date();
        accountService.modify(account);

        logService.log("修改用户账号", "/accountForm/" + account.id);
    }

    @RequestMapping(value = "/api/admin/accounts/{id}", method = RequestMethod.DELETE)
    public void deleteAccount(@PathVariable("id") Long id) {

        assertSuper();

        accountService.delete(id);

        logService.log("删除用户账号", null);
    }

    @RequestMapping(value = "/api/admin/accounts/resetPassword", method = RequestMethod.POST)
    public void resetPassword(String oldPassword, String newPassword) {

        assertAdmin();

        accountService.resetPassword(oldPassword, newPassword);

        logService.log("修改密码", "/accountForm/" + RestFilter.accountHolder.get().id);
    }

}
