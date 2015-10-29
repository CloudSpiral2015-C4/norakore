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

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/nyavatar")
	public Response nyavatar(@QueryParam("x") double x, @QueryParam("y") double y) {
        // TODO: paramはLocationにすべきか？jsonだとPOSTでしか入力できない
        Report result = controller.searchNyavatar(x, y);
		return Response.status(200).entity(result).build();
	}

    // にゃばたー登録
    // パラメータは{name, picture, location}が必須，typeはどっちでもよい
	@POST @Consumes("application/json")
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/register")
	public Response register(NyavatarDetail nyavatar) {
        if (nyavatar == null) {
            return Response.status(400).entity("parameter 'nyavatar' is required.").build();
        }
        String nya_id = controller.registerNyavatar(nyavatar);
		return Response.status(200).entity("{\"nyavatar\":\"" + nya_id + "\"}").build();
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
