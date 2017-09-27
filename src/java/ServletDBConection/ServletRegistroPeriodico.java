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

public class ServletRegistroPeriodico extends HttpServlet {
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
            
            String nombreP,direccionP,query;
            nombreP = request.getParameter("nombreP");
            direccionP = request.getParameter("direccionP");
            
            query = "Insert into periodico values ('"+nombreP+"','"+direccionP+"')";
            System.out.println("Periodico Pre");
            stmt.executeUpdate(query);
            System.out.println("Periodico Post");
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Registro Periodico</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Periodico Registrado</h1>");
            out.println("</body");           
            out.println("</html>");
            response.setHeader("Refresh", "3;url=index.html");
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Registro Periodico</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Periodico Registrado Anteriormente</h1>");
            out.println("</body");           
            out.println("</html>");
            response.setHeader("Refresh", "3;url=index.html");
            System.out.println("no se logro la conexion");
            Logger.getLogger(ServletRegistroPeriodico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}