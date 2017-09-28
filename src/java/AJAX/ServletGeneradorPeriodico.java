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

public class ServletGeneradorPeriodico extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/casoscorrup","postgres", "cartoon16");
            System.out.println("Conexion Creada");
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs;
            
            rs = stmt.executeQuery("Select nombren from periodico");
            
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out=response.getWriter();
            while(rs.next()){
                out.println("<option value='"+rs.getString("nombren")+"'>"+rs.getString("nombren")+"</option>");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletGeneradorPeriodico.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletGeneradorPeriodico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}