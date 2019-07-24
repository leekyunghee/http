import java.util.HashMap;
import java.util.Map;

public class HttpParser {

    public static String[] getRequestLine(String header) {
        String[] requestHeader = header.split("\\s");
        return requestHeader;
    }

    public static String[] postRequestLine(String header) {
        String[] requestHeader = header.split("\\s");
        return requestHeader;
    }

    public static Map< String, String> getQueryString(String queryString) {
        String[] result = queryString.split("\\s");
        String url = result[1];
        Integer index = url.indexOf("?");
        String parameter = url.substring(index + 1);
        String[] params = parameter.split("=");

        Map< String, String> map = new HashMap();

        map.put(params[0], params[1]);

        return map;
    }

    public static Map< String, String> postRequestBody(String body) {
        String[] requestBody = body.split("\\n");
        String key = requestBody[6];

        String[] array = key.split("&");

        for(int i=0; i < array.length; i++) {
            System.out.println(array[i]);
        }

        String id = array[0];
        String password = array[1];
        String name = array[2];

        String[] key1 = id.split("=");
        String[] key2 = password.split("=");
        String[] key3 = name.split("=");

        Map< String, String> map = new HashMap();
        map.put(key1[0], key1[1]);
        map.put(key2[0], key2[1]);
        map.put(key3[0], key3[1]);      // 패턴 뽑아내기

        return map;
    }
}
