/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.zarca.covellog.interfaces.web.security;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
 
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
 
public class DefaultFilter implements Filter {
 
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // invoked when an instance of this filter is created by the container
        // used to initialize resources, read parameters...
         
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        if (request instanceof HttpServletRequest) {
            String url = ((HttpServletRequest)request).getRequestURL().toString();
            String queryString = ((HttpServletRequest)request).getQueryString();
            String valores = "";
            Map<String, String[]> map = ((HttpServletRequest)request).getParameterMap();
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String key = entry.getKey();
                String[] value = entry.getValue();
                String avalores = "[";
                for (String item : value) {
                    avalores = avalores + item + ",";
                }
                avalores = avalores + "]";
                valores = valores + key + ": " + avalores + "; ";
            }
            System.out.println(url + "?" + queryString);
            System.out.println("Valores: " + valores);
            
            valores = "";
            Enumeration<String> atts = ((HttpServletRequest)request).getAttributeNames();
            
            while (atts.hasMoreElements()) {
                String val = atts.nextElement();
                valores = valores + val + "," + ((HttpServletRequest)request).getAttribute(val);
            }
            System.out.println("Valores2: " + valores);
        } else {
            System.out.println("OTRO servlet "  + request.getProtocol());
        }
        chain.doFilter(request, response);  // invokes next filter in the chain
    }
 
    @Override
    public void destroy() {
        // invoked when the filter instance is being destroyed by the container
        // used clean up resources
 
    }
}
