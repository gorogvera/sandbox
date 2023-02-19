
 import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	            
	            // 1. add NONCE to <script> tags
	            String regex = "<script(.*?)>";
	            String replacement = "<script $1 nonce=\"hellononce\">";
	            originalContent = originalContent.replaceAll(regex, replacement);
	            
	            // 2. change button onclick="myFunction()"
	            regex = "<button(.*?)onclick=\"(.*?)\"(.*?)>";
	            Pattern pattern = Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
	            Matcher matcher = pattern.matcher(originalContent);
	            while (matcher.find())
	            {
	            	String buttonID = "valamilyengeneraltuniquebuttonid";
	            	String functionName = matcher.group(2);
	            	String stringToReplace = matcher.group(0);
	            	System.out.println("Matcher: " + matcher.group(0) + "," + matcher.group(1) + "," + matcher.group(3));
	            	System.out.println("Function name:"+functionName);
	            	Pattern idPattern = Pattern.compile("id=\"(.*?)\"",Pattern.CASE_INSENSITIVE);
	            	Matcher idMatcher = idPattern.matcher(matcher.group(1));
	            	boolean idExists = false;
	            	if (idMatcher.find()) {
	            		buttonID = idMatcher.group(1);
	            		idExists = true;
	            	} else {
	            		idMatcher = idPattern.matcher(matcher.group(3));
	            		if (idMatcher.find()) {
	            			buttonID = idMatcher.group(1);
	            			idExists = true;
	            		} else {
	            			idExists = false;
	            		}
	            	}
	            	replacement = "<button"+matcher.group(1)+matcher.group(3);
	            	if (!idExists) replacement += " id=\""+buttonID+"\"";
	            	replacement += ">";
	            	originalContent = originalContent.replace(stringToReplace, replacement);
	            	
	            	// add document.getElementById("buttoncsp").onclick = function() {myFunction()};
	                String arr[] = functionName.split("\\(");
	                String modifiedFunctionName = arr[0];
	                System.out.println("Function name" + functionName);
	            	String rx = "function( +)"+modifiedFunctionName+"\\(";
	            	Pattern ptrn = Pattern.compile(rx,Pattern.CASE_INSENSITIVE);
		            Matcher mtchr = ptrn.matcher(originalContent);
		            if (mtchr.find()) {
		            	System.out.println("found match:"+mtchr.group(0));
		            	replacement = "document.getElementById(\""+buttonID+"\").onclick = function() {"+functionName+"};\n" + mtchr.group(0);
		            	originalContent = originalContent.replace(mtchr.group(0), replacement);
		            } else {
		            	// add PF function
		            	// 1. look for script tag
		            	rx = "<script(.*?)>((.*?)\""+buttonID+"\"(.*?)</script>)";
		            	ptrn = Pattern.compile(rx,Pattern.CASE_INSENSITIVE);
		            	mtchr = ptrn.matcher(originalContent);
		            	System.out.println("PF button id:"+buttonID);
		            	if (mtchr.find()) {
		            		System.out.println("found PF match:"+mtchr.group(0));
		            		functionName = functionName.replaceAll("&quot;", "\"");
		            		//$('#btnHello').on('click', sayHello());
		            		//$(document.getElementById('j_idt8:beginDateTxt'))
		            		String modifiedFunction = "$('"+buttonID+"').on('click',function(event){"+functionName+"});";
			            	replacement = "<script"+mtchr.group(1)+">"+modifiedFunction+"\n"+mtchr.group(2);
		            		
		            		//replacement = "<script"+mtchr.group(1)+">"+"document.getElementById(\""+buttonID+"\").onclick = function() {"+functionName+"};\n" + mtchr.group(2);
			            	originalContent = originalContent.replace(mtchr.group(0), replacement);
		            	}
		            }
	            }
	            
	            charWriter.write(originalContent);
	              
	            String alteredContent = charWriter.toString();
	            httpResponse.setContentLength(alteredContent.length());
	            responseWriter.write(alteredContent);       
	        }        
        
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