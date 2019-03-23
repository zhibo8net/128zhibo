package website2018.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import website2018.base.BaseEndPoint;
import website2018.dto.web.IssueQuestionContentWebDTO;
import website2018.dto.web.IssueQuestionWebDTO;
import website2018.dto.web.IssueWebDTO;
import website2018.service.issue.IssueService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IssueController extends BaseEndPoint {


    @Autowired
    private IssueService issueService;

    @RequestMapping(value = "/jingcai")
    public String jingcai( Model model,HttpServletRequest request, HttpServletResponse response) {

      IssueWebDTO issueWebDTO= issueService.queryLastIssue(request);
        if(issueWebDTO!=null){
            for(IssueQuestionWebDTO issueQuestionWebDTO:issueWebDTO.issueQuestionList){
                for(IssueQuestionContentWebDTO issueQuestionContentWebDTO :issueQuestionWebDTO.issueQuestionContentList){
                    if(StringUtils.isNotEmpty(issueQuestionWebDTO.inputContent)){
                        try{
                            Double d=Double.parseDouble(issueQuestionWebDTO.inputContent);

                                if(StringUtils.equals(issueQuestionContentWebDTO.questionContent,"主队")){

                                    issueQuestionContentWebDTO.questionContent= issueQuestionContentWebDTO.questionContent+((-d)>=0d?("+"+(-d)):(-d));
                                }else if(StringUtils.equals(issueQuestionContentWebDTO.questionContent,"客队")){
                                      issueQuestionContentWebDTO.questionContent= issueQuestionContentWebDTO.questionContent+(d>=0d?("+"+d):d);
                                  }else{
                                    issueQuestionContentWebDTO.questionContent= issueQuestionContentWebDTO.questionContent+(d);

                                }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        }


        model.addAttribute("issue", issueWebDTO);
        return "jingcai";
    }
    @RequestMapping(value = "/jingcainotice")
    public String jingcainotice( Model model,HttpServletRequest request, HttpServletResponse response) {

        IssueWebDTO issueWebDTO= issueService.queryLastJingcainotice();

        model.addAttribute("issue", issueWebDTO);
        return "jingcainotice";
    }
    }
