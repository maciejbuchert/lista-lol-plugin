package pl.icehost.serverlist;

import com.squareup.okhttp.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Test {

    public static void main(String[] args) {
        try {
            /*
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://codebit.pl/api/v1/game_servers/minecraft/get/prizes")
                    .addHeader("Authorization", "Bearer mjFBApNM6PsiIXXUpbuwN1ru")
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());


             */
/*
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"transaction_hash\": \"56b5ac1e5b9046af83452a396a533bbfe5ce260e\"\n}");
            Request request = new Request.Builder()
                    .url("https://codebit.pl/api/v1/game_servers/minecraft/claim/prize")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer mjFBApNM6PsiIXXUpbuwN1ru")
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());

*/

            if (false) {
                OkHttpClient client = new OkHttpClient();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\n    \"method\": \"cashbill\",\n    \"customer_email\": \"maciejbuchert1@gmail.com\",\n    \"action\": \"games_server\",\n    \"currency\": \"PLN\",\n    \"action_data\": {\n        \"server_id\": 10,\n        \"days\": 8,\n        \"player_nickname\": \"rex89m\"\n    },\n    \"amount\": 6,\n    \"return_url\": \"http://codebit.pl/payments\"\n}");
                Request request = new Request.Builder()
                        .url("https://codebit.pl/api/v1/payment")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = client.newCall(request).execute();
                System.out.println(response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
