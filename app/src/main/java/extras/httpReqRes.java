package extras;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.*;
import java.io.*;

/**
 * Created by moham on 11/27/2017.
 */

public class httpReqRes
{
    public httpReqRes() { }
    public static String[]welcomeMsg(String Stringurl)
    {
        String[] results=new String[]{"problem","sorry looks like I am sleeping try again later"};
        try
        {
            URL url =new URL(Stringurl);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(8000);
            connection.setRequestMethod("GET");
            connection.connect();
            System.out.println("trying to connect");
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                System.out.println("connected");
                InputStream in = new BufferedInputStream(connection.getInputStream());
                results=new String[2];
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = r.readLine())!=null)
                {
                    sb.append(line);
                }
                JSONObject respJson=new JSONObject(sb.toString());
                String uuid = respJson.getString("uuid");
                String  message=respJson.getString("message");
                results[0]=uuid;
                results[1]=message;
            }
        } catch (Exception e) {
            System.out.println("fail");
            return results;
        }
        results[1]+=" "+setEmojis(results[1]);
        return results;
    }

    ////get emojis for messages
    public static String  setEmojis(String s){
        String[]bad=new String[]{" can't "," invalid "," no requests "," not "," didn't "};
        int []sadArray=new int[]{0x1F622,0x1F620,0x1F624,0};
        int []goodArray=new int[]{0x1F603,0x1F60A,0x1F64F,0};
        int unicode=goodArray[(int)(Math.random()*3)];

        for (int i = 0; i <bad.length ; i++) {
            if(s.contains(bad[i])){
             int random = (int )(Math.random() * 4);//random number 0--2
            unicode=sadArray[random];

            }
        }

        //do not always send them
        String state= unicode==0?"":new String(Character.toChars(unicode));
        return state;

    }

    public static String sendPostRequest(String StringUrl,String uuid,String message)
    {
        String results="Sorry I am sleeping now try again later";
        JSONObject req=new JSONObject();
        try
        {
            req.put("message",message);
            Log.println(Log.ERROR,"Message sent",message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try
        {
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
            // put json req
            // connection.s
            System.out.println("trying to connect");
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                System.out.println("connected");
                InputStream in = new BufferedInputStream(connection.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;
                while((line = r.readLine())!=null)
                {
                    sb.append(line);
                }
                JSONObject respJson=new JSONObject(sb.toString());
                results=respJson.getString("message");
            }
        } catch (Exception e) {
            System.out.println("fail");
            return results;
        }
        return results+" "+setEmojis(results);
    }
}
