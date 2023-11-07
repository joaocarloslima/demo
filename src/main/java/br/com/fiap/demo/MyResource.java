package br.com.fiap.demo;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }
    
    /**
     * Método de exemplo de acesso a banco de dados
     * O seu projeto deverá ter uma classe DAO para acesso ao banco de dados
     * O seu projeto deverá ter um conection factory para gerenciar a conexão com o banco de dados
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("veiculos")
    public Response getVeiculos() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        var con = DriverManager.getConnection("jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL", "pf1389", "fiap23");
        var stmt = con.createStatement();
        var rs = stmt.executeQuery("select * from veiculos");

        var veiculos = new ArrayList<Veiculo>();

        while (rs.next()) {
            veiculos.add(new Veiculo(
                rs.getLong("id"), 
                rs.getString("marca"), 
                rs.getString("modelo"), 
                rs.getInt("ano")
            ));
        }

    	return Response.ok(veiculos).build();
    }
    
}
