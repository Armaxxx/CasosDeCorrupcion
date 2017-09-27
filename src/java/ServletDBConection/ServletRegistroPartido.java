package ServletDBConection;

import java.sql.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletRegistroPartido extends HttpServlet {
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
            
            String nombreP,direccionP,telefonoP,query;
            nombreP = request.getParameter("nombreP");
            direccionP = request.getParameter("direccionP");
            telefonoP = request.getParameter("telefonoP");
            query = "Insert into partido_politico values ('"+nombreP+"','"+direccionP+"','"+telefonoP+"')";
            System.out.println("PrePartido");
            stmt.executeUpdate(query);
            System.out.println("PostPartido");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Registro Patido</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Partido Registrado</h1>");
            out.println("</body");           
            out.println("</html>");
            response.setHeader("Refresh", "3;url=index.html");
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Registro Partido</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Partido Registrado Anteriormente</h1>");
            out.println("</body");           
            out.println("</html>");
            response.setHeader("Refresh", "3;url=index.html");
            System.out.println("no se logro la conexion");
            Logger.getLogger(ServletRegistroPartido.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}