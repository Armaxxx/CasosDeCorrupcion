package ServletDBConection;

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

public class ServletRelacion extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/casoscorrup","postgres", "cartoon16");
            System.out.println("Conexion Creada");
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String query,nombrePer,nombrePar,tipo = request.getParameter("tipo");
            nombrePar = request.getParameter("partidopolitico");
            nombrePer = request.getParameter("periodico");
            if(tipo.matches("papp")){
                query = "Insert into periodico_afinidad_partido values ('"+nombrePer+"','"+nombrePar+"')";
            }else{
                query = "Insert into partido_afinidad_periodico values ('"+nombrePar+"','"+nombrePer+"')";
            }
            stmt.executeUpdate(query);         
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Registro Relacion</title>");
                out.println("</head>");
                out.println("<body>");  
                out.println("<h1>Relacion Registrada</h1>");
                out.println("</body");
                out.println("</html>");
                response.setHeader("Refresh", "3;url=index.html");
            connection.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletRelacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Registro Relacion</title>");
                out.println("</head>");
                out.println("<body>");  
                out.println("<h1>Relacion Registrada Anteriormente</h1>");
                out.println("</body");
                out.println("</html>");
                response.setHeader("Refresh", "3;url=index.html");
            Logger.getLogger(ServletRelacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
