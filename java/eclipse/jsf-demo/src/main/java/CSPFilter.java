 import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
 
public class CSPFilter implements Filter {
 
	private static boolean isCSPEnabled = false;
	private static String cspPolicy = "";
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // invoked when an instance of this filter is created by the container
        // used to initialize resources, read parameters...
    	String cspEnabled = filterConfig.getServletContext().getInitParameter("loxon.CSP");
    	isCSPEnabled = "true".equals(cspEnabled) ? true : false;
    	
    	cspPolicy = filterConfig.getServletContext().getInitParameter("loxon.CSP_POLICY");
    }
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // invoked when a matching request sent to the server
        // used to intercept the request and transform the response
        if (isCSPEnabled) {
	    	System.out.println("CSPFilter doFilter");
	        HttpServletResponse httpResponse = (HttpServletResponse)response;
	         
	        CharResponseWrapper wrapper = new CharResponseWrapper(httpResponse);
	        
	        chain.doFilter(request, wrapper);
	         
	        PrintWriter responseWriter = httpResponse.getWriter();
	        
	        httpResponse.addHeader("Content-Security-Policy", cspPolicy+" 'nonce-hellononce'");
	        
	        
	        if (wrapper.getContentType().contains("text/html")) {
	            CharArrayWriter charWriter = new CharArrayWriter();
	            
	            String originalContent = wrapper.toString();
	            
	            String regex = "<script(.*?)>";
	            String replacement = "<script $1 nonce=\"hellononce\">";
	            originalContent = originalContent.replaceAll(regex, replacement);
	            
	            charWriter.write(originalContent);
	              
	            String alteredContent = charWriter.toString();
	            httpResponse.setContentLength(alteredContent.length());
	            responseWriter.write(alteredContent);  
	            responseWriter.flush();
	            responseWriter.close();         
	        }        
	        
	        wrapper.flushBuffer();
	        wrapper.closeWriter();
        
        } else {
        	chain.doFilter(request, response);
        }
    }  
    
    @Override
    public void destroy() {
        // invoked when the filter instance is being destroyed by the container
        // used clean up resources
        System.out.println("HelloFilter destroy ");

    }
}