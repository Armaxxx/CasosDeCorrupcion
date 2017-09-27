package AJAX;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletGeneradorCajas extends HttpServlet {
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
            out.println("<div><p>Ciudadano n&uacute;mero:</p><p id=\"numCiudadano_"+(x+1)+"\">"+(x+1)+"</p><br>"
            + "<p>Registro de poblaci&oacute;n<span>*</span></p>"
            + "<input class=\"input-100\" type=\"text\" name=\"rp_"+(x+1)+"\"><br>\n" +
"            <p>Nombre del ciudadano<span>*</span></p>"
            + "<input class=\"input-100\" type=\"text\" name=\"nombreC_"+(x+1)+"\"><br>\n" +
"            <p>Direcci&oacute;n del ciudadano<span>*</span></p>"
            + "<input class=\"input-100\" type=\"text\" name=\"direccionC_"+(x+1)+"\"><br>\n" +
"            <p>Patrimonio en Millones<span>*</span><p>"
            + "<input class=\"input-100\" type=\"text\" name=\"patrimonio_"+(x+1)+"\"><br>\n" +
"            <p>Cargo<span>*</span></p>"
            + "<input class=\"input-100\" type=\"text\" name=\"cargo_"+(x+1)+"\"><br>\n" +
"            <p>Nacionalidad<span>*</span></p>"
            + "<input class=\"input-100\" type=\"text\" name=\"nacionalidad_"+(x+1)+"\"><br>\n" +
"            <p>Pertenece a algun partido?</p><br>\n" +
"            <input type=\"radio\" name=\"pregunta_"+(x+1)+"\" value=\"si\" onclick=\"PartidoConstruir("+(x+1)+")\"/> SI<br>\n" +
"            <input type=\"radio\" name=\"pregunta_"+(x+1)+"\" value=\"no\" checked onclick=\"PartidoConstruir("+(x+1)+")\"/> NO<br>\n" +
"            <div id=\"formP_"+(x+1)+"\">\n" +
"                <div id=\"puesto_"+(x+1)+"\">\n" +
"                </div>\n" +
"            </div>"
        + "<br><hr><br>"
        + "</div>");
        }
    }
}