package kr.co.crewmate.core.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * html value값  Pattern 스트링으로 변환 
 *
 */
public class MaskTag extends BodyTagSupport
{
	private static final long serialVersionUID = 1857516127702403618L;
	private String pattern;

	public void setPattern(String pattern)
	{
		this.pattern = pattern;
	}

	public int doAfterBody() throws JspException
	{
		try
		{
			JspWriter out = getPreviousOut();
			BodyContent body = getBodyContent();
			String value = body.getString();

			if((value != null) && !value.equals("") && (pattern != null) && !pattern.equals(""))
			{
				String tmpStr = "";
				char[] valueChar = value.toCharArray();
				char[] patternChar = pattern.toCharArray();

				if((valueChar != null) && (valueChar.length > 0) && (patternChar != null) && (patternChar.length > 0))
				{
					int i = 0;
					int k = 0;

					while (k < patternChar.length)
					{
						if(patternChar[k] == '#')
						{
							if(i < valueChar.length)
							{
								tmpStr += valueChar[i];
								i++;
							}

							k++;
						}
						else
						{
							while ((k < patternChar.length) && (patternChar[k] != '#'))
							{
								tmpStr += patternChar[k];
								k++;
							}
						}
					}
				}

				out.print(tmpStr);
			}
		}
		catch(Exception ex)
		{
			// throw new JspTagException("MaskTag : " + ex.getMessage());
		}

		return SKIP_BODY;
	}
}