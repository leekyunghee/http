package com.lkh.http.webserver;

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

    public static Map<String, String> getRequestData(String body) {

        if(body.contains("POST"))  {
            return postRequestBody(body);
        } else {
            String[] result = body.split("\\s");
            String url = result[1];
            Integer index = url.indexOf("?");
            String parameter = url.substring(index + 1);
            String[] params = parameter.split("=");

            Map< String, String> map = new HashMap();

            map.put(params[0], params[1]);

            return map;
        }
    }

    /**
     * @since 190725(목) 패턴 뽑아내기
     * @description Request가 post일 경우 호출한다.
     *              String 배열만큼 map으로 담는다.
     * @param body
     * @return map
     */
    public static Map< String, String> postRequestBody(String body) {
        String[] requestBody = body.split("\\n");
        String key = requestBody[6];

        String[] array = key.split("&");

        Map< String, String> map = new HashMap();

        for(String item : array) {
            String[] result = item.split("=");
            map.put(result[0], result[1]);
        }

        return map;
    }

}
