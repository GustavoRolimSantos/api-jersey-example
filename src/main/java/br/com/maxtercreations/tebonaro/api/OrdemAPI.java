package br.com.maxtercreations.tebonaro.api;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.maxtercreations.tebonaro.AjudaTebonaro;
import br.com.maxtercreations.tebonaro.mysql.MySQLManager;

@Path("v1/dev/ordem")
public class OrdemAPI {

	private MySQLManager mysqlManager = AjudaTebonaro.getManager().getMySQLManager();

	@Context
	private HttpServletRequest httpRequest;

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(String json) {

		JSONObject request = new JSONObject(json);

		try {
			mysqlManager.getMySQL().executeUpdate("INSERT INTO `restapi`.`ordem` (`Id`, `Numero`, `Descricao`, `Verba`) VALUES ('" + request.get("id").toString() + "', '" + request.get("numero").toString() + "', '"
					+ request.get("descricao").toString() + "', '" + request.get("verba").toString() + "');");

		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		JSONObject response = new JSONObject();
		response.put("status", "Belaaaa");

		return Response.status(Status.OK).entity(response.toString()).build();
	}

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search(@QueryParam("id") String id) {
		System.out.println(id + " ============");
		JSONObject response = new JSONObject();

		try {
			ResultSet resultSet = mysqlManager.getMySQL().executeQuery("SELECT `Id`,  `Numero`, `Descricao`, `Verba` FROM `restapi`.`ordem` WHERE Id='" + id + "'");

			while (resultSet.next()) {
				response.put("numero", resultSet.getString("Numero"));
				response.put("descricao", resultSet.getString("Descricao"));
				response.put("verba", resultSet.getString("Verba"));
			}

		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		response.put("status", "Belaaaa");

		return Response.status(Status.OK).entity(response.toString()).build();
	}

	@POST
	@Path("/search2")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search2(@QueryParam("id") String id) {
		System.out.println(id + " ============");
		JSONArray response = new JSONArray();

		try {
			ResultSet resultSet1 = mysqlManager.getMySQL().executeQuery("SELECT * FROM ordem WHERE Id='" + id + "'");


			while (resultSet1.next()) {

				JSONObject obj = new JSONObject();
				obj.put("id", resultSet1.getString("Id"));
				obj.put("numero", resultSet1.getString("Numero"));
				obj.put("descricao", resultSet1.getString("Descricao"));
				obj.put("verba", resultSet1.getString("verba"));
				
				ResultSet resultSet2 = mysqlManager.getMySQL().executeQuery("SELECT * FROM pessoa WHERE OrdemId='" + resultSet1.getString("PessoaId") + "'");
				JSONArray array = new JSONArray();

				while (resultSet2.next()) {
					JSONObject contribuidor = new JSONObject();
					contribuidor.put("id", resultSet2.getString("Id"));
					contribuidor.put("nome", resultSet2.getString("Nome"));

					array.put(contribuidor);
					
				}
				JSONObject obj2 = new JSONObject();
				obj2.put("contribuidor", array);
				
				response.put(obj2);

				response.put(obj);
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.status(Status.OK).entity(response.toString()).build();
	}
	
	@POST
	@Path("/search3")
	@Produces(MediaType.APPLICATION_JSON)
	public Response search3(@QueryParam("id") String id) {
		System.out.println(id + " ============");
		JSONArray response = new JSONArray();

		try {
			ResultSet resultSet1 = mysqlManager.getMySQL().executeQuery("SELECT * FROM ordem WHERE PessoaId = '" + id + "'");
			
			
			while (resultSet1.next()) {
				JSONObject obj = new JSONObject();
				obj.put("id", resultSet1.getString("Id"));
				obj.put("numero", resultSet1.getString("Numero"));
				response.put(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return Response.status(Status.OK).entity(response.toString()).build();
	}

}