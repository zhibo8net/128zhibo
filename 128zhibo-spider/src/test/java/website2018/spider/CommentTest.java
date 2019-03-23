package website2018.spider;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import website2018.dto.CommentDTO;
import website2018.dto.user.ReturnResponse;
import website2018.service.comment.CommentService;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class  CommentTest {
    @Autowired
    CommentService commentService;


    public void addComment() throws Exception {

        CommentDTO commentDTO=new CommentDTO();
        commentDTO.type=1;//1视频 2新闻
        commentDTO.userType=1;//1球迷  2彩迷
        commentDTO.relId=123;//当type=1 是表示视频ID  type=2 新闻ID
        commentDTO.comment="dddddddddddddddddd";
        ReturnResponse returnResponse= commentService.addComment(commentDTO, null);
        System.out.println(returnResponse);
    }
    @Test
    public void getCommentAllList() throws Exception {

        CommentDTO commentDTO=new CommentDTO();
        commentDTO.type=1;//1视频 2新闻
        commentDTO.userType=1;//1球迷  2彩迷
        commentDTO.relId=123;//当type=1 是表示视频ID  type=2 新闻ID

        List<CommentDTO> list= commentService.findCommentDTOByRelId(commentDTO.relId, commentDTO.type, commentDTO.userType, 0);
        System.out.println(list);
    }


    @Test
    public void getCommentPageList() throws Exception {

        CommentDTO commentDTO=new CommentDTO();
        commentDTO.type=1;//1视频 2新闻
        commentDTO.userType=1;//1球迷  2彩迷
        commentDTO.relId=123;//当type=1 是表示视频ID  type=2 新闻ID
        commentDTO.pageNumber=1;
        List<CommentDTO> list= commentService.findCommentDTOByRelId(commentDTO.relId,commentDTO.type,commentDTO.userType,0);
        System.out.println(list);
    }
}
