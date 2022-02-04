package com.example.demo;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Methods {

    static final HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {

        /* El objeto ClassicHttpResponse incluye toda la información de una respuesta Http: cuerpo, código, cabeceras...*/
        @Override
        public String handleResponse(final ClassicHttpResponse response) throws IOException {
            final int status = response.getCode();

            if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {

                final HttpEntity entity = response.getEntity();
                try {
                    if (entity != null) {
                        return EntityUtils.toString(entity);
                    }
                    else
                        return null;
                } catch (final ParseException ex) {
                    throw new ClientProtocolException(ex);
                }
            } else {
                throw new ClientProtocolException("Unexpected response status: " + status);
            }
        }

    };

    String urlGetAllJugadores="http://localhost:8080/jugadores/";
    String urlGetAllGames ="http://localhost:8080/juegos";

    String resultado = "";
    String texto= "";

    final CloseableHttpClient httpClient = HttpClients.createDefault();
    //funciona
    public void getAllPlayers (TextArea textArea){

        HttpGet httpGet = new HttpGet(urlGetAllJugadores);
        textArea.clear();

        try (CloseableHttpResponse response1 = httpClient.execute(httpGet)) {

           //System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
            HttpEntity entity1 = response1.getEntity();

            resultado = EntityUtils.toString(entity1);

            JSONArray jsonArray = new JSONArray(resultado);
            for(int i =0; i< jsonArray.length(); i++){
                if(jsonArray.get(i) instanceof JSONObject){
                    JSONObject jsnObj = (JSONObject)jsonArray.get(i);
                    textArea.appendText(jsnObj.toString() + "\n" + "\n");

                }
            }

            EntityUtils.consume(entity1);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //funciona
    public void getPlayerById(TextArea textArea, TextField idTextField) {

        String id = idTextField.getText();
        String urlGetPlayerById="http://localhost:8080/jugadores/{id}";
        try {
            urlGetPlayerById = urlGetPlayerById.replace("{id}", URLEncoder.encode(id,"UTF-8"));

            HttpGet getRequest = new HttpGet(urlGetPlayerById);
                try (CloseableHttpResponse response1 = httpClient.execute(getRequest)) {

                    //System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
                    HttpEntity entity1 = response1.getEntity();

                    resultado = EntityUtils.toString(entity1);

                    JSONObject json = new JSONObject(resultado);

                    texto = json.toString();

                    textArea.setText(texto);

                    EntityUtils.consume(entity1);
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    //funciona
    public void getAllGames (TextArea textArea){
        HttpGet httpGet = new HttpGet(urlGetAllGames);
        textArea.clear();

        try (CloseableHttpResponse response1 = httpClient.execute(httpGet)) {

            //System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
            HttpEntity entity1 = response1.getEntity();

            resultado = EntityUtils.toString(entity1);

            JSONArray jsonArray = new JSONArray(resultado);
            for(int i =0; i< jsonArray.length(); i++){
                if(jsonArray.get(i) instanceof JSONObject){
                    JSONObject jsnObj = (JSONObject)jsonArray.get(i);
                    textArea.appendText(jsnObj.toString() + "\n" + "\n");
                }
            }

            EntityUtils.consume(entity1);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //funciona
    public void getGameById (TextArea textArea, TextField idTextField){
        String id = idTextField.getText();
        String urlGetGameById="http://localhost:8080/juegos/{CodJuego}";
        try {
            urlGetGameById = urlGetGameById.replace("{CodJuego}", URLEncoder.encode(id,"UTF-8"));

            HttpGet getRequest = new HttpGet(urlGetGameById);
            try (CloseableHttpResponse response1 = httpClient.execute(getRequest)) {

                //System.out.println(response1.getCode() + " " + response1.getReasonPhrase());
                HttpEntity entity1 = response1.getEntity();

                resultado = EntityUtils.toString(entity1);

                JSONObject json = new JSONObject(resultado);

                texto = json.toString();

                textArea.setText(texto);

                EntityUtils.consume(entity1);
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //funciona
    public void postNewPlayer(TextArea textArea , TextField textFieldMail, TextField textFieldName) {

        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/jugadores/");
            JSONObject player = new JSONObject();

            String name = textFieldName.getText();
            String email = textFieldMail.getText();

            player.accumulate("avatar", "si");
            player.accumulate("nombre", name);
            player.accumulate("correo", email);
            player.accumulate("edad", 18);

            StringEntity entity = new StringEntity(player.toString(), StandardCharsets.UTF_8);
            httpPost.setEntity(entity);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset = UTF-8");

            CloseableHttpResponse response = httpClient.execute(httpPost);

            textArea.setText(response.getCode() + " " + player);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    //not-null property references a null or transient value : entity.Juegos.companiesByDesarrolladora
    public void postNewGame(TextArea textArea, TextField textFieldMail, TextField textFieldName){

        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/juegos");
            JSONObject game = new JSONObject();

            String name = textFieldName.getText();
            int genero = Integer.valueOf(textFieldMail.getText());

            Date fecha= new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("YYYY-MM-dd");

            //cod_juego,nombre,fecha_lanzamiento,precio,genero,pegi,desarrolladora,distribuidora,portada
            game.accumulate("nombre", name);
            game.accumulate("fechaLanzamiento", dateFormat.format(fecha));
            game.accumulate("precio", 123);
            game.accumulate("genero", genero);
            game.accumulate("pegi", 15);
            game.accumulate("companiesByDesarrolladora", " ");
            game.accumulate("distribuidora", " ");
            game.accumulate("portada", "Si");

            StringEntity entity = new StringEntity(game.toString(), StandardCharsets.UTF_8);
            httpPost.setEntity(entity);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset = UTF-8");

            CloseableHttpResponse response = httpClient.execute(httpPost);
            textArea.setText(response.getCode() + " " + game);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //servidor
    public void deleteGameById (TextArea textArea, TextField idTextField){
        String id = idTextField.getText();
        String urlDeleteGame="http://localhost:8080/juegos/{CodJuego}";

        try {
            urlDeleteGame = urlDeleteGame.replace("{CodJuego}", URLEncoder.encode(id,"UTF-8"));
            HttpDelete delRequest = new HttpDelete(urlDeleteGame);
            try (CloseableHttpResponse response1 = httpClient.execute(delRequest)) {

                textArea.setText(response1.getCode() + "GM " );

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }
    //servidor
    public void deletePlayerById (TextArea textArea, TextField idTextField){
        String id = idTextField.getText();
        String urlDeletePlayer="http://localhost:8080/jugadores/{id}";

        try {
            urlDeletePlayer = urlDeletePlayer.replace("{id}", URLEncoder.encode(id,"UTF-8"));
            HttpDelete delRequest = new HttpDelete(urlDeletePlayer);
            try (CloseableHttpResponse response1 = httpClient.execute(delRequest)) {

                textArea.setText(response1.getCode() + "PL " );

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    //va pero no entiendo las relaciones del server , se ha probado distribuidora y companiesByDesarrolladora
    public void updateGameById (TextArea textArea, TextField textFieldId, TextField textFieldMail, TextField textFieldName){
        try {
            String id = textFieldId.getText();
            String url = "http://localhost:8080/juegos/{CodJuego}";
            url= url.replace("{CodJuego}", URLEncoder.encode(id,"UTF-8"));

            HttpPut httpPut = new HttpPut(url);
            JSONObject game = new JSONObject();

            String name = textFieldName.getText();
            int genero = Integer.valueOf(textFieldMail.getText());

            Date fecha= new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("YYYY-MM-dd");

            //cod_juego,nombre,fecha_lanzamiento,precio,genero,pegi,desarrolladora,distribuidora,portada
            game.accumulate("nombre", name);
            game.accumulate("fechaLanzamiento", dateFormat.format(fecha));
            game.accumulate("precio", 123);
            game.accumulate("genero", genero);
            game.accumulate("pegi", 15);
            game.accumulate("companiesByDesarrolladora", 830); //game.accumulate("distribuidora", 830);
            //game.accumulate("distribuidora", 242);
            game.accumulate("portada", "Si");

            StringEntity entity = new StringEntity(game.toString(), StandardCharsets.UTF_8);
            httpPut.setEntity(entity);

            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json; charset = UTF-8");

            CloseableHttpResponse response = httpClient.execute(httpPut);
            textArea.setText(response.getCode() + " " + game);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //funciona
    public void updatePlayerById (TextArea textArea, TextField textFieldId, TextField textFieldMail, TextField textFieldName){
        try {

            String id = textFieldId.getText();
            String url = "http://localhost:8080/jugadores/{id}";
            url= url.replace("{id}", URLEncoder.encode(id,"UTF-8"));

            HttpPut httpPut = new HttpPut(url);
            JSONObject player = new JSONObject();

            String name = textFieldName.getText();
            String email = textFieldMail.getText();

            //codJugador,nombre,correo
            player.accumulate("avatar", " ");
            player.accumulate("nombre", name);
            player.accumulate("correo", email);
            player.accumulate("edad", 18);

            StringEntity entity = new StringEntity(player.toString(), StandardCharsets.UTF_8);
            httpPut.setEntity(entity);

            httpPut.setHeader("Accept", "application/json");
            httpPut.setHeader("Content-type", "application/json; charset = UTF-8");

            CloseableHttpResponse response = httpClient.execute(httpPut);

            textArea.setText(response.getCode() + " " + player);

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
