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
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.sun.jersey.core.util.Base64;

public class OuterAPI {

    public static String getNanineko(String pictureID) {
        // 何猫APIをプロキシする内部APIを呼ぶ
        String url = "http://localhost/norakore/exapi/nanineko";

        Client client = new Client();
        WebResource resource = client.resource(url).queryParam("pictureID", pictureID);
        String response = resource.get(String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<List<String>> mapped = mapper.readValue(response, List.class);
            return mapped.get(0).get(0);
        } catch (Exception e) {
            return "不明";
        }
    }

    public static String nekoSearch(String area) {
        String url = "http://api.neko-search.com/v1/";

        Client client = new Client();
        client.addFilter(new LoggingFilter(System.out));
        WebResource resource = client.resource(url).queryParam("area", area).queryParam("type", "search");
        String response = resource.get(String.class);

        return response;
    }
}
