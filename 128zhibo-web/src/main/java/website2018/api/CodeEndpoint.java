package website2018.api;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import freemarker.template.Configuration;
import freemarker.template.Template;
import website2018.code.EntityDefination;

//访问这里使用代码生成器：http://localhost:7070/#/code
@RestController
public class CodeEndpoint {
    
    private static String BASE_PATH = "/Users/qidafang/Desktop/zhibo8_code/";
    private static String JAVA_PATH = "/main/java/website2018/";
    private static String PROJECT_NAME = "zhibo";
    
    Configuration cfg;
    
    @PostConstruct
    private void init() throws Exception{
        FreeMarkerConfigurationFactoryBean f = new FreeMarkerConfigurationFactoryBean();
        f.setTemplateLoaderPath("classpath:/templates/codeTemplates");
        cfg = f.createConfiguration();
    }
    
    @RequestMapping(value = "/api/admin/code", method = RequestMethod.POST, consumes = MediaTypes.JSON_UTF_8)
    public void code(@RequestBody EntityDefination entityDefination) throws Exception {
        FileUtils.deleteQuietly(new File(BASE_PATH));
        
        entityDefination.big = entityDefination.english;
        entityDefination.small = small(entityDefination.english);
        entityDefination.table = table(entityDefination.english);
        
        page(entityDefination, "entity.txt", JAVA_PATH + "domain/" + entityDefination.big + ".java");
        page(entityDefination, "dto.txt", JAVA_PATH + "dto/admin/" + entityDefination.big + "AdminDTO.java");
        page(entityDefination, "dao.txt", JAVA_PATH + "repository/" + entityDefination.big + "Dao.java");
        page(entityDefination, "service.txt", JAVA_PATH + "service/admin/" + entityDefination.big + "Service.java");
        page(entityDefination, "endpoint.txt", JAVA_PATH + "api/admin/" + entityDefination.big + "AdminEndpoint.java");
        
        page(entityDefination, "_page_list.txt", entityDefination.big + "List.vue");
        page(entityDefination, "_page_form.txt", entityDefination.big + "Form.vue");
        page(entityDefination, "_config.txt", entityDefination.big + "config.txt");
        
    }

    /**
     * 生成单个页面
     * @param entityDefination 类定义
     * @param templateFileName 模板文件名
     * @param fileName 在生成目标目录下的路径+文件名
     * @throws Exception
     */
    private void page(EntityDefination entityDefination, String templateFileName, String fileName) throws Exception{

        Map model = new HashMap();
        model.put("entity", entityDefination);
        
        Template temp = cfg.getTemplate(templateFileName);
        
        String result = FreeMarkerTemplateUtils.processTemplateIntoString(temp, model);
        
        FileUtils.write(new File(BASE_PATH + fileName), result);
    }
    
    //Test -> test
    private String small(String className) {
        StringBuilder sb = new StringBuilder();
        String f= StringUtils.lowerCase(CharUtils.toString(className.charAt(0)));
        sb.append(f).append(className.substring(1));
        return sb.toString();
    }

    //TestFood -> zhibo_test_food
    private String table(String className){
        String s = PROJECT_NAME + className;
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < s.length(); i++){
            char c = s.charAt(i);
            if(Character.isUpperCase(c)){
                result.append("_").append(Character.toLowerCase(c));
            }else{
                result.append(c);
            }
        }
        return result.toString();
    }

}
