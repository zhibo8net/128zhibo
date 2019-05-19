package website2018.api.support;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.DefaultErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springside.modules.utils.mapper.JsonMapper;
import org.springside.modules.web.MediaTypes;

/**
 * 重载替换Spring Boot默认的BasicErrorController, 增加日志并让错误返回方式统一.
 * 
 * @author calvin
 */
@Controller
public class ErrorPageController implements ErrorController {

	private static Logger logger = LoggerFactory.getLogger(ErrorPageController.class);

	@Value("${error.path:/error}")
	private String errorPath;

	private JsonMapper jsonMapper = new JsonMapper();

	private ErrorAttributes errorAttributes = new DefaultErrorAttributes();

	@RequestMapping(value = "${error.path:/error}", produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public void handle(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String, Object> attributes = getErrorAttributes(request);

		ErrorResult result = new ErrorResult();
		result.code = (int) attributes.get("status");
		result.message = (String) attributes.get("error");
		logError(attributes, request);
//		return result;

		if(!isAjaxRequest(request)){
			//logger.error("错误请求地址："+request.getRequestURI());
			//request.getRequestDispatcher("/").forward(request,response);
			response.sendRedirect("/");
		}else{
			//logger.error("错误请求地址："+request.getRequestURI());
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = null ;
			try{

				out = response.getWriter();
				out.append(JSONObject.toJSONString(result));

			}
			catch (Exception e) {
				e.printStackTrace();
				response.sendError(500);

			}
		}

	}
	public static boolean isAjaxRequest(HttpServletRequest request) {
		String requestedWith = request.getHeader("x-requested-with");
		if (requestedWith != null && requestedWith.equalsIgnoreCase("XMLHttpRequest")) {
			return true;
		} else {
			return false;
		}
	}
	private Map<String, Object> getErrorAttributes(HttpServletRequest request) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return this.errorAttributes.getErrorAttributes(requestAttributes, false);
	}

	private void logError(Map<String, Object> attributes, HttpServletRequest request) {
		attributes.put("from", request.getRemoteAddr());
		logger.error("错误请求地址"+jsonMapper.toJson(attributes));
	}

	@Override
	public String getErrorPath() {
		return this.errorPath;
	}
}
