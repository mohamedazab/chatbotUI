package extras;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.*;
import java.io.*;

/**
 * Created by moham on 11/27/2017.
 */

public class httpReqRes {
    public httpReqRes(){

    }
    public static String[]welcomeMsg(String Stringurl){
        String[] results=new String[]{"problem","sorry looks like I am sleeping try again later"};

        try{
            URL url =new URL(Stringurl);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(8000);
            connection.setRequestMethod("GET");
            connection.connect();
            System.out.println("trying to connect");
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                System.out.println("connected");
                InputStream in = new BufferedInputStream(connection.getInputStream());
                results=new String[2];
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = r.readLine())!=null) {
                    sb.append(line);
                }
                JSONObject respJson=new JSONObject(sb.toString());
                String uuid = respJson.getString("uuid");
                String  message=respJson.getString("message");
                results[0]=uuid;
                results[1]=message;
            }
        }catch (Exception e){
            System.out.println("fail");
            return results;
        }

    return results;
    }
    public static String sendPostRequest(String StringUrl,String uuid,String message){
        String results="Sorry I am sleeping now try again later";
        JSONObject req=new JSONObject();
        try {
            req.put("message",message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try{
            URL url =new URL(StringUrl);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();

            connection.setConnectTimeout(8000);
            connection.setRequestProperty("authorization", uuid);
            connection.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            connection.setRequestMethod("POST");
            OutputStreamWriter wr= new OutputStreamWriter(connection.getOutputStream());
            wr.write(req.toString());
            wr.flush();
            wr.close();
            //put json req
           // connection.s
            System.out.println("trying to connect");
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                System.out.println("connected");
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = r.readLine())!=null) {
                    sb.append(line);
                }
                JSONObject respJson=new JSONObject(sb.toString());
                results=respJson.getString("message");
            }
        }catch (Exception e){
            System.out.println("fail");
            return results;
        }

        return results;

    }


}
