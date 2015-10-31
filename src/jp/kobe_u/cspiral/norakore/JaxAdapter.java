package jp.kobe_u.cspiral.norakore;

import jp.kobe_u.cspiral.norakore.model.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
public class JaxAdapter {

	private final NorakoreController controller = new NorakoreController();

	// にゃばたー（簡易）のリストを取得
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/nyavatar")
	public Response nyavatar(@QueryParam("lon") double lon, @QueryParam("lat") double lat) {
        NyavatarList result = controller.searchNyavatar(lon, lat);
		return Response.status(200).entity(result).build();
	}

	// ユーザの保有するにゃばたー（簡易）のリストの取得
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/mynyavatar")
	public Response mynyavatar(@QueryParam("userID") String userID) {
        NyavatarList result;
        try {
            result = controller.getUsersNyavatar(userID);
        } catch (Exception e) {
            ErrorResult err = new ErrorResult(e.getMessage());
            return Response.status(400).entity(err).build();
        }
		return Response.status(200).entity(result).build();
	}

	// にゃばたー（詳細）取得
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/nyavatardetail")
	public Response nyavatardetail(@QueryParam("nyavatarID") String nyavatarID, @QueryParam("userID") String userID) {
        NyavatarDetail result = controller.getNyavatarDetail(nyavatarID, userID);
		return Response.status(200).entity(result).build();
	}

    // にゃばたー登録
    // パラメータは{name, picture, location}が必須，typeはどっちでもよい
	@POST
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/register")
	public Response register(
            @FormParam("userID") String userID,
            @FormParam("name") String name,
            @FormParam("type") String type,
            @FormParam("picture") String picture, // base64
            @FormParam("lon") double lon,
            @FormParam("lat") double lat) {
        //String nya_id;
        RegisterResult result = new RegisterResult();
        try {
            result = controller.registerNyavatar(userID, name, type, picture, lon, lat);
        } catch (Exception e) {
            ErrorResult err = new ErrorResult(e.getMessage());
            return Response.status(400).entity(err).build();
        }

        //result.setNyavatarID(nya_id);
        //result.setBonitos(10); // TODO: 値をちゃんとやる
		return Response.status(200).entity(result).build();
	}

    // 写真をアップロードする
    // アップロードするのみ．類似チェックなどは別APIで．
    @POST @Consumes("image/jepg")
	@Produces({MediaType.APPLICATION_JSON})
    @Path("/picture")
    public Response uploadPictre(@FormParam("src") String data) {
        String id = controller.saveImage(data, "picture");
        return Response.status(200).entity("{\"picture\":\"" + id + "\"}").build();
    }

    @POST @Consumes("image/png")
	@Produces({MediaType.APPLICATION_JSON})
    @Path("/icon")
    public Response uploadIcon(@FormParam("src") String data) {
        String id = controller.saveImage(data, "icon");
        return Response.status(200).entity("{\"icon\":\"" + id + "\"}").build();
    }

	@GET
	@Produces("image/jepg")
	@Path("/picture/{id}.jpg")
	public Response picture(@PathParam("id") String id) {
		ByteArrayOutputStream baos = controller.getImage(id, "picture");
		if (baos == null) {
			return Response.status(404).entity("photo not found.").build();
		}
		byte[] data = baos.toByteArray();
		return Response.ok(new ByteArrayInputStream(data)).build();
	}

	@GET
	@Produces("image/png")
	@Path("/icon/{id}.png")
	public Response icon(@PathParam("id") String id) {
		ByteArrayOutputStream baos = controller.getImage(id, "icon");
		if (baos == null) {
			return Response.status(404).entity("icon not found.").build();
		}
		byte[] data = baos.toByteArray();
		return Response.ok(new ByteArrayInputStream(data)).build();
	}

	/**
	 * ./api/ へのアクセスを ./api/application.wadl（APIの仕様書） にリダイレクトする
	 * @return
	 * @throws URISyntaxException
	 */
	@GET
	@Path("/")
	public Response redirect() throws URISyntaxException{
		URI uri = new URI("application.wadl");
		return Response.seeOther(uri).build();
	}

}
