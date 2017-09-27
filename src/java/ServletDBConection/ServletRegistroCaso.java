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

public class ServletRegistroCaso extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        try {
            Class.forName("org.postgresql.Driver");
            ResultSet rs;
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/casoscorrup","postgres", "cartoon16");
            System.out.println("Conexion Creada");
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            
            String nombrecomun,descripcion,millones,sede_pais,nss,query;
            String codigoCaso;
            
            nss = request.getParameter("nss");
            String nombreJ,direccionJ,fechaNac,fechaIni,dictamen;
            nombreJ = request.getParameter("nombreJ");
            direccionJ = request.getParameter("direccionJ");
            fechaNac = request.getParameter("fechaNac");
            fechaIni = request.getParameter("fechaIni");
            query = "Insert into juez values ('"+nss+"','"+nombreJ+"','"+direccionJ+"','"+fechaNac+"','"+fechaIni+"')";
            System.out.println("Juez pre");
            stmt.executeUpdate(query);
            System.out.println("Juez post");
            if(request.getParameter("casoT").matches("si")){
                dictamen = request.getParameter("dictamen");
            }else{
                dictamen = "sin terminar";
            }
            
            nombrecomun = request.getParameter("nombrecomun");
            descripcion = request.getParameter("descripcion");
            millones = request.getParameter("millones");
            sede_pais = request.getParameter("sede_pais");
            System.out.println("PreInsercion de Caso");
            query = "Insert into casoscorru (nombrecomun,descripcion,millones,pais_sede,nss) values ('"+nombrecomun+"','"+descripcion+"',"+millones+",'"+sede_pais+"','"+nss+"');";
            stmt.executeUpdate(query);
            System.out.println("Caso Insertado");
            rs = stmt.executeQuery("Select Codigo from casoscorru");
            System.out.println("caso consultado");
            if(rs.last()){
                codigoCaso = rs.getString("codigo");
                System.out.println("Existe tupla anterior de valor "+codigoCaso);
            }else{
                codigoCaso = "1";
                System.out.println("no hay tupla anterior, primera tupla no debe ser posible");
            }
            System.out.println("Pre investigado");
            query = "Insert into investigado_por values ('"+codigoCaso+"','"+nss+"','"+dictamen+"')";
            stmt.executeUpdate(query);
            System.out.println("post investigado");
            
            String curp,nombreC,direccionC,patrimonio,cargo,nacionalidad;
            int numC;
            numC = Integer.parseInt(request.getParameter("numero"));
            for(int x = 0 ; x < numC ; x++){
                System.out.println("Ciudadano "+x);
                curp = request.getParameter("rp_"+(x+1));
                nombreC = request.getParameter("nombreC_"+(x+1));
                direccionC = request.getParameter("direccionC_"+(x+1));
                patrimonio = request.getParameter("patrimonio_"+(x+1));
                cargo = request.getParameter("cargo_"+(x+1));
                nacionalidad = request.getParameter("nacionalidad_"+(x+1));
                query = "Insert into ciudadanos values ('"+curp+"','"+nombreC+"','"+direccionC+"','"+patrimonio+"')";
                stmt.executeUpdate(query);
                System.out.println("Ciudadano "+x+" agregado");
                query = "Insert into tiene_implicados values ('"+codigoCaso+"','"+curp+"','"+cargo+"')";
                stmt.executeUpdate(query);
                System.out.println("Ciudadano "+x+" relacionado al caso");
                if(request.getParameter("pregunta_"+(x+1)).matches("si")){
                    String nombreP,direccionP,telefonoP,puesto;
                    nombreP = request.getParameter("nombreP_"+(x+1));
                    direccionP = request.getParameter("direccionP_"+(x+1));
                    telefonoP = request.getParameter("telefonoP_"+(x+1));
                    query = "Insert into partido_politico values ('"+nombreP+"','"+direccionP+"','"+telefonoP+"')";
                    System.out.println("Ciudadano "+x+" Pertenece a partido");
                    stmt.executeUpdate(query);
                    System.out.println("Partido "+x+" Agregado");
                    if(request.getParameter("pregunta2_"+(x+1)).matches("si")){
                        puesto = request.getParameter("puesto"+(x+1));
                        System.out.println("Tiene puesto en Partido "+x);
                    }else{
                        puesto = "no";
                    }
                    System.out.println("Ciudadano "+x+" Afiliado pre");
                    query = "Insert into ciudadano_afiliado values ('"+nombreP+"','"+curp+"','"+puesto+"')";
                    System.out.println("Ciudadano "+x+" Afiliado post");
                    stmt.executeUpdate(query);
                    
                }
            }
            
            String nombreP,direccionP,tiraje,fechaDesc;
            nombreP = request.getParameter("nombreP");
            direccionP = request.getParameter("direccionP");
            tiraje = request.getParameter("tiraje");
            fechaDesc = request.getParameter("fechaDesc");
            query = "Insert into periodico values ('"+nombreP+"','"+direccionP+"','"+tiraje+"')";
            System.out.println("Periodico Pre");
            stmt.executeUpdate(query);
            System.out.println("Periodico Post");
            query = "Insert into descubierto values ('"+codigoCaso+"','"+nombreP+"','"+fechaDesc+"')";
            System.out.println("Descubierto Pre");
            stmt.executeUpdate(query);
            System.out.println("Descubierto Post");
            
            String paisImp;
            int numP;
            numP = Integer.parseInt(request.getParameter("numero2"));
            for(int x = 0 ; x < numP ; x++){
                paisImp = request.getParameter("pais_"+(x+1));
                System.out.println("Pre Pais_"+(x+1));
                query = "Insert into paises_implicados values ('"+codigoCaso+"','"+paisImp+"')";
                stmt.executeUpdate(query);
                System.out.println("POST Pais_"+(x+1));
            }
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>RegistroCasos</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Caso Registrado</h1>");
            out.println("</body");           
            out.println("</html>");
            response.setHeader("Refresh", "3;url=index.html");
            connection.close();
        } catch (ClassNotFoundException | SQLException ex) {
            out.println("No ps no");
            System.out.println("no se logro la conexion");
            Logger.getLogger(ServletRegistroCaso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}