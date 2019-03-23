package website2018.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;
import website2018.base.BaseEndPoint;
import website2018.dto.user.ReturnResponse;
import website2018.dto.web.AddIssueDTO;
import website2018.dto.web.IssueWebDTO;
import website2018.service.issue.IssueService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IssueRestController extends BaseEndPoint {


    @Autowired
    private IssueService issueService;
    @RequestMapping(value = "/api/user/addIssue", produces = MediaTypes.JSON_UTF_8)
    public ReturnResponse addIssue(@RequestBody(required = false) AddIssueDTO addIssueDTO, HttpServletRequest request) {


        return issueService.addIssue(addIssueDTO,request);
    }

    @RequestMapping(value = "/test1")
    public IssueWebDTO test1( HttpServletRequest request) {

        IssueWebDTO issueWebDTO= issueService.queryLastIssue(request);
        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(issueWebDTO));
        return issueWebDTO;
    }
    @RequestMapping(value = "/test2")
    public IssueWebDTO test2( HttpServletRequest request) {

        IssueWebDTO issueWebDTO= issueService.queryLastJingcainotice();
        System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(issueWebDTO));
        return issueWebDTO;
    }
}
