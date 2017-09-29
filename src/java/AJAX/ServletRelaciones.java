package AJAX;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletRelaciones extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/casoscorrup","postgres", "cartoon16");
            System.out.println("Conexion Creada");
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String tipo = request.getParameter("tipo");
            int x,rowcount;
            String[] nombrePeriodico,nombrePartido;
            ResultSet rs;
            
            rs = stmt.executeQuery("Select nombren from periodico");
            x=0;
            rowcount=0;
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
            nombrePeriodico = new String[rowcount];
            while(rs.next()){
                nombrePeriodico[x] = rs.getString(1);
                x++;
            }
            
            rs = stmt.executeQuery("Select nombreP from partido_politico");
            x=0;
            rowcount=0;
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
            nombrePartido = new String[rowcount];
            while(rs.next()){
                nombrePartido[x] = rs.getString(1);
                x++;
            }
            
            response.setContentType("text/html;charset=UTF-8");         
            PrintWriter out=response.getWriter();
            
            if(tipo.matches("papp")){
                out.println("<form action=\"ServletRelacion?tipo=papp\" method=\"post\">\n" +
"                    <select name=\"periodico\">\n");
                for(String periodico: nombrePeriodico){
                    out.println("<option value='"+periodico+"'>"+periodico+"</option>");
                }
                out.println("</select>\n" +
"                    <p>Tiene una relaci&oacute;n con el Partido</p>\n" +
"                    <select name=\"partidopolitico\">\n");
                for(String partido: nombrePartido){
                            out.println("<option value='"+partido+"'>"+partido+"</option>");
                }
                out.println("</select>\n" +
"                    <button type=\"submit\" class=\"w3-btn w3-blue\">Relacionar</button>\n" +
"                </form>");
            }else{
                out.println("<form action=\"ServletRelacion?tipo=ppap\" method=\"post\">\n" +
"                    <select name=\"partidopolitico\">\n");
                for(String periodico: nombrePartido){
                    out.println("<option value='"+periodico+"'>"+periodico+"</option>");
                }
                out.println("</select>\n" +
"                    <p>Tiene una relaci&oacute;n con el Periodico</p>\n" +
"                    <select name=\"periodico\">\n");
                for(String partido: nombrePeriodico){
                            out.println("<option value='"+partido+"'>"+partido+"</option>");
                }
                out.println("</select>\n" +
"                    <button type=\"submit\" class=\"w3-btn w3-blue\">Relacionar</button>\n" +
"                </form>");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletRelaciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletRelaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}