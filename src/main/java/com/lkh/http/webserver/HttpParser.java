package com.lkh.http.webserver;

import java.util.HashMap;
import java.util.Map;

public class HttpParser {

    public static String[] getRequestLine(String header) {
        if (header == null) throw new IllegalArgumentException();
        String[] requestHeader = header.split("\\s");
        return requestHeader;
    }

    public static Map<String, String> getRequestParameters(String request) {
        String data = getQueryByMethod(request);
        return getRequestParams(data);
    }

    private static String getQueryByMethod(String request) {
        // METHOD, URL, VERSION
        String[] requestLine = getRequestLine(request);

        if("POST".equals(requestLine[0]))  {
            return getRequestBody(request);
        }
        // else 제거
        String url = requestLine[1];
        return url.substring(url.indexOf("?")+1);
    }

    private static String getRequestBody(String request) {
        String[] lines =  request.split("\n");

        boolean isBody = false;
        String body = "";
        // 들여쓰기는 1번이 원칙
        for (String line : lines) {
            if ("".equals(line)) {
                isBody = true;
                continue;
            }

            if (!isBody) continue;

            body += line;
        }

        return body;
    }

    private static Map<String, String> getRequestParams(String data) {
        Map<String, String> params = new HashMap<String, String>();
        String[] splitData = data.split("&");
        for (String keyValue : splitData) {
            String[] result = keyValue.split("=");
            params.put(result[0], result[1]);
        }
        return params;
    }

}

