package com.example.demo;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    Button buttonGetJugadores = new Button("GetAllJugadores");
    Button buttonGetPlayerById = new Button("GetPlayerById");

    Button buttonGetAllGames = new Button("GetAllGames");
    Button buttonGetGameById = new Button("GetGameById");

    Button buttonPostNewPlayer = new Button("PostNewPlayer");
    Button buttonPostNewGame = new Button("PostNewGame");

    Button buttonDeleteGameById = new Button("DeleteGameById");
    Button buttonDeletePlayerById = new Button("DeletePlayerById");

    Button buttonUpdateGameById = new Button("UpdateGameById");
    Button buttonUpdatePlayerById = new Button("UpdatePlayerById");

    TextArea info = new TextArea();

    Methods methods = new Methods();

    TextField textFieldId = new TextField();
    TextField textFieldName = new TextField();
    TextField textFieldMail = new TextField();

    @Override
    public void start(Stage stage) {

        BorderPane bp = new BorderPane();

        info.setPrefSize(Double.MAX_VALUE,Double.MAX_VALUE);
        info.setWrapText(true);
        bp.setCenter(info);

        buttonGetJugadores.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        buttonGetPlayerById.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        buttonGetAllGames.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        buttonGetGameById.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        buttonPostNewPlayer.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        buttonPostNewGame.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        buttonDeletePlayerById.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        buttonDeleteGameById.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        buttonUpdatePlayerById.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        buttonUpdateGameById.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        //izquierda
        VBox vBox = new VBox();
        vBox.getChildren().addAll(buttonGetJugadores,buttonGetPlayerById,buttonGetAllGames,buttonGetGameById,buttonPostNewPlayer,buttonPostNewGame,buttonDeleteGameById,buttonDeletePlayerById,buttonUpdateGameById,buttonUpdatePlayerById);
        vBox.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        bp.setLeft(vBox);

        //abajo
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(20));
        hBox.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);
        hBox.setSpacing(10);

        textFieldId.setMaxSize(100,Double.MAX_VALUE);
        textFieldName.setMaxSize(100,Double.MAX_VALUE);
        textFieldMail.setMaxSize(100,Double.MAX_VALUE);

        Label lbId = new Label("ID: ");
        lbId.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        Label lbName = new Label(" ");
        lbName.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        Label lbMail = new Label(" ");
        lbMail.setMaxSize(Double.MAX_VALUE,Double.MAX_VALUE);

        hBox.getChildren().addAll(lbId,textFieldId,lbName,textFieldName,lbMail,textFieldMail);
        bp.setBottom(hBox);

        //derecha
        Button habJuegos = new Button("Habilitar Juegos");
        Button habJugadores = new Button("Habilitar Jugadores");

        VBox VBoxDer = new VBox();
        hBox.setSpacing(10);

        habJuegos.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //llamamos las funciones
                accionesJuegos();
                habJuegos.setText("Valores Juegos = true");
                habJugadores.setText("Valores Jugadores = false");
                lbName.setText("Nombre");
                lbMail.setText("Genero");

            }
        });

        habJugadores.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //llamamos las funciones
                accionesJugadores();
                habJugadores.setText("Valores Jugadores = true");
                habJuegos.setText("Valores Juegos = false");
                lbName.setText("NombreJugador");
                lbMail.setText("Email");

            }
        });

        habJuegos.setMaxSize(200,50);
        habJugadores.setMaxSize(200,50);
        VBoxDer.getChildren().addAll(habJuegos,habJugadores);
        bp.setRight(VBoxDer);

        //scene
        Scene scene = new Scene(bp, 700, 650);
        stage.setTitle("Cliente");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

    public void accionesJuegos(){

        buttonGetAllGames.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                methods.getAllGames(info);
            }
        });

        buttonGetGameById.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                methods.getGameById(info,textFieldId);
            }
        });

        buttonPostNewGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                methods.postNewGame(info,textFieldMail,textFieldName);
            }
        });

        buttonDeleteGameById.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                methods.deleteGameById(info,textFieldId);
            }
        });

        buttonUpdateGameById.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                methods.updateGameById(info,textFieldId,textFieldMail,textFieldName);
            }
        });
    }

    private void accionesJugadores() {
        buttonPostNewPlayer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                methods.postNewPlayer(info,textFieldMail,textFieldName);

            }
        });

        buttonGetPlayerById.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                methods.getPlayerById(info,textFieldId);
            }
        });

        buttonGetJugadores.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                methods.getAllPlayers(info);
            }
        });

        buttonDeletePlayerById.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                methods.deletePlayerById(info,textFieldId);
            }
        });
        buttonUpdatePlayerById.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                methods.updatePlayerById(info,textFieldId,textFieldMail,textFieldName);
            }
        });


    }


}