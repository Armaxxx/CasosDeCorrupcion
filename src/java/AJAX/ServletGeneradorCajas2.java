package AJAX;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletGeneradorCajas2 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        int valorCaja = Integer.parseInt(request.getParameter("valor"));
        if(valorCaja <= 0){
            valorCaja = 0;
        }
        PrintWriter out=response.getWriter();
        for(int x=0;x<valorCaja;x++){
            out.println("<div><p>Pa&iacute;s n&uacute;mero:</p><p id=\"numPais_"+(x+1)+"\">"+(x+1)+"</p><br>"
            + "<p>Pa&iacute;s implicado:<span>*</span></p>"
            + "<input class=\"input-100\" type=\"text\" name=\"pais_"+(x+1)+"\"><br>\n");
        }
    }
}