package kr.co.crewmate.core.web.taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kr.co.crewmate.core.model.ListResult;
import kr.co.crewmate.core.util.HttpUtil;

/**
 * 페이징 처리 관련 태그
 * @author crewmate
 *
 */
@SuppressWarnings("serial")
public class PaginationTag extends TagSupport
{
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private String templatePath		= "/WEB-INF/jsp/common/pagination/";
	private String listResultName;
	private String template			= "default.jsp";
	private String paramPage		= "page";
	private String paramListSize	= "listSize";
	private String paramPageSize	= "pageSize";
	private String addParam			= "";
	private String function;
	
	
	public void setTemplate(String template) {				this.template = template;				}
	public void setListResultName(String listResultName) {	this.listResultName = listResultName;	}
	public void setParamPage(String paramPage) {			this.paramPage = paramPage;				}
	public void setParamListSize(String paramListSize) {	this.paramListSize = paramListSize;		}
	public void setParamPageSize(String paramPageSize) {	this.paramPageSize = paramPageSize;		}
	public void setAddParam(String addParam){				this.addParam = addParam;				}
	public void setFunction(String function) {				this.function = function;				}

	
	public int doStartTag() throws JspTagException {
		return SKIP_BODY;
	}

	
	@SuppressWarnings("rawtypes")
	public int doEndTag() throws JspTagException {
		ServletRequest request	= pageContext.getRequest();
		ListResult listResult	= (ListResult) HttpUtil.lookup(pageContext, this.listResultName, "request");

		if(listResult != null){
			Map<String, Object> dataMap	= new HashMap<String, Object>();
			dataMap.put("listResult", listResult);
			dataMap.put("paramPage", this.paramPage);
			dataMap.put("paramListSize", StringUtils.isNotEmpty(request.getParameter(this.paramListSize)) ? "&" + this.paramListSize + "=" + request.getParameter(this.paramListSize) : "");
			dataMap.put("paramPageSize", StringUtils.isNotEmpty(request.getParameter(this.paramPageSize)) ? "&" + this.paramPageSize + "=" + request.getParameter(this.paramPageSize) : "");
			
			String paramStr = HttpUtil.getParamStr(request, this.paramPage + "," + this.paramListSize + "," + this.paramPageSize);
			dataMap.put("paramStr", StringUtils.isNotEmpty(paramStr) ? "&" + paramStr  + this.addParam : "" + this.addParam);
			dataMap.put("function", this.function);

			try {
				String result = HttpUtil.dispatch((HttpServletRequest)request, (HttpServletResponse)this.pageContext.getResponse(), dataMap, this.templatePath + this.template);
				pageContext.getOut().print(result);
			} catch (ServletException se) {
				log.error("PaginationTag Exception : ", se.getMessage());
			} catch (IOException e) {
				log.error("PaginationTag Exception : ", e.getMessage());
			}
		}

		return EVAL_PAGE;
	}
}