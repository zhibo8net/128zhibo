package website2018.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;
import website2018.base.BaseEndPoint;
import website2018.dto.CommentDTO;
import website2018.dto.user.ReturnResponse;
import website2018.dto.user.UserDTO;
import website2018.service.comment.CommentService;
import website2018.service.user.UserLoginService;
import website2018.utils.SysConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class CommentController extends BaseEndPoint {
    
    @Autowired
    CommentService commentService;


    @RequestMapping(value = "/api/user/addComment", produces = MediaTypes.JSON_UTF_8)
    public ReturnResponse addComment(@RequestBody(required=false) CommentDTO commentDTO,HttpServletRequest request) {



        return commentService.addComment(commentDTO,request);
    }
    @RequestMapping(value = "/api/user/getCommentAllList", produces = MediaTypes.JSON_UTF_8)
    public List<CommentDTO> getCommentAllList(@RequestBody(required=false) CommentDTO commentDTO,HttpServletRequest request) {


        return commentService.findCommentDTOByRelId(commentDTO.relId, commentDTO.type,commentDTO.userType,0);
    }
    @RequestMapping(value = "/api/user/getCommentPageList", produces = MediaTypes.JSON_UTF_8)
    public List<CommentDTO> getCommentPageList(@RequestBody(required=false) CommentDTO commentDTO,HttpServletRequest request) {


        return commentService.findCommentDTOByRelId(commentDTO.relId, commentDTO.type,commentDTO.userType,commentDTO.pageNumber);
    }


    }
