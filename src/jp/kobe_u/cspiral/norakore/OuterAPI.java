package jp.kobe_u.cspiral.norakore;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.MultiPart;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import jp.kobe_u.cspiral.norakore.model.*;
import jp.kobe_u.cspiral.norakore.util.DBUtils;

import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sun.jersey.core.util.Base64;

public class OuterAPI {
    private static final String Nanineko_URL = "http://whatcat.ap.mextractr.net/api_query";
    private static final String Nanineko_user = "user";
    private static final String Nanineko_pass = "pass";

    public static String getNanineko(ByteArrayInputStream picture) {
        String auth = "-u " + Nanineko_user + ":" + Nanineko_user;
        ProcessBuilder pb = new ProcessBuilder("curl", auth, "-F image=@-", Nanineko_URL);
        pb.redirectErrorStream(true);
        String result;
        try {
            Process process = pb.start();

            // input picture to curl
            OutputStream stdin = process.getOutputStream();
            IOUtils.copy(picture, stdin);

            // read curl result
            InputStream stdout = process.getInputStream();
            result = IOUtils.toString(stdout, Charset.defaultCharset());
        } catch (IOException e) {
            result = "failed.";
        }

        // MultiPart multiPart = new MultiPart();
        // ClientConfig config = new DefaultClientConfig();
        // Client client = Client.create(config);
        // client.addFilter(new HTTPBasicAuthFilter("medalhkr", "chorich"));
        // client.addFilter(new LoggingFilter(System.out));
        // WebResource service = client.resource(UriBuilder.fromUri(url).build());

        // multiPart.bodyPart(new BodyPart(picture, MediaType.APPLICATION_OCTET_STREAM_TYPE));

        // WebResource.Builder builder = service.type(MediaType.MULTIPART_FORM_DATA).
        //     accept(MediaType.APPLICATION_JSON);
        // String res = builder.post(String.class, multiPart);

        return result;
    }
}
