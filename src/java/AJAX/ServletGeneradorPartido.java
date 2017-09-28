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

public class ServletGeneradorPartido extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/casoscorrup","postgres", "cartoon16");
            System.out.println("Conexion Creada");
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            
            response.setContentType("text/html;charset=UTF-8");
            String num = request.getParameter("num");
            PrintWriter out=response.getWriter();
            
            rs = stmt.executeQuery("Select nombreP from partido_politico");
            if (rs.next()){
                out.println("<div><select id='selectpartido_"+num+"' name='partidopol_"+num+"'>");
                out.println("<option value='"+rs.getString("nombreP")+"'>"+rs.getString("nombreP")+"</option>");
                while(rs.next()){
                    out.println("<option value='"+rs.getString("nombreP")+"'>"+rs.getString("nombreP")+"</option>");
                }
                out.println("</select></div>");
                out.println("<div id='otroPartido_"+num+"'>\n</div>");
            }
                out.println("<p>Puesto dentro del partido?</p><br>\n" +
                                "                <input type=\"radio\" name=\"pregunta2_"+num+"\" value=\"si\" onclick=\"PartidoConstruirCargo("+num+")\"/> SI<br>\n" +
                                "                <input type=\"radio\" name=\"pregunta2_"+num+"\" value=\"no\" checked onclick=\"PartidoConstruirCargo("+num+")\"/> NO<br>\n" +
                                "                <div id=\"puesto_"+num+"\">\n" +
                                "                </div>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletGeneradorPartido.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletGeneradorPartido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}