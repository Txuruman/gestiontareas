package es.securitasdirect.tareas.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by jel on 18/06/2015.
 */
public class CustomInput extends SimpleTagSupport {
    @Override
    public void doTag() throws JspException, IOException {
            JspWriter out = getJspContext().getOut();
            out.println("Hello Custom Tag!");

    }
}
