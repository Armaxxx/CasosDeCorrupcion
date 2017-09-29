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

public class ServletBajas extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/casoscorrup","postgres", "cartoon16");
            System.out.println("Conexion Creada");
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            String query,codigo;
            codigo = request.getParameter("codigo");
            
            query = "delete from tiene_implicados where codigo = '"+codigo+"'";
            stmt.executeUpdate(query);

            query = "delete from investigado_por where codigo = '"+codigo+"'";
            stmt.executeUpdate(query);
            
            query = "delete from casoscorru where codigo = '"+codigo+"'";
            stmt.executeUpdate(query);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Baja de Caso</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Caso BORRADO</h1>");
            out.println("</body>");
            out.println("</html>");
            response.setHeader("Refresh", "3;url=index.html");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServletBajas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ServletBajas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
