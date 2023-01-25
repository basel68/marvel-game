package GUI2;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import model.world.Cover;
import model.world.Champion;
import model.world.Damageable;


import java.util.ArrayList;

import engine.Game;

public class Board {
	Game game;
    GridPane board;
    String theme;
    public ArrayList<Square> squares = new ArrayList<>();

    public Board(GridPane board, String theme,Game game,ImageView[]imgs){
        this.board = board;
        this.theme = theme;
        this.game = game;
       
       makeBoard(imgs);
    }
    


    public void makeBoard(ImageView[] imgs){
    	Object[][] b = game.getBoard();
        for(int i=0; i<b.length; i++){
            for(int j=0; j<b[i].length; j++){
            	Damageable x = (Damageable) b[4-i][4-j];
                Square square = new Square(i,j,(Damageable) b[4-i][4-j]);
                if(x instanceof Cover) {
                	Tooltip hp = new Tooltip(x.getCurrentHP()+" hp");

               Image cover=new Image("file:./images/Cover.png");
               ImageView view8= new ImageView(cover);
               view8.setFitWidth(50);
               view8.setFitHeight(50);
                square.getChildren().add(view8);
                	Tooltip.install(view8,hp);
                }else if(x instanceof Champion){
                	Tooltip hp = new Tooltip(x.getCurrentHP()+" hp");
                	Champion c=(Champion)x;
                	for(int k=0;k<imgs.length;k++) {
                	if(c.getName().equals(imgs[k].getId())) {
                		ImageView p=imgs[k];
                		p.setFitWidth(75);
                		p.setFitHeight(75);
                		Tooltip.install(p,hp);
                    	square.getChildren().add(p);
                	}
                	}
                	
                	
//                    if(x==game.getTurnOrder().peekMin()) {
//                    	p.setStyle("-fx-background-color:red");
//                    }else {
//                    	p.setStyle("");
//                    }
                }
//                square.setName("Square" + i + j);
                square.setPrefHeight(75);
                square.setPrefWidth(75);
                square.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
                setTheme(square, theme, i, j);
                board.add(square, j, i, 1, 1);
                squares.add(square);
            }
        }
    }

    private void setTheme(Square square, String theme, int i, int j){
        Color color1 = Color.web("#ffffff00");
        Color color2 = Color.web("#ffffff00");

        switch (theme) {
            case "Coral" -> {
                color1 = Color.web("#b1e4b9");
                color2 = Color.web("#70a2a3");
            }
            case "Dusk" -> {
                color1 = Color.web("#cbb7ae");
                color2 = Color.web("#716677");
            }
            case "Wheat" -> {
                color1 = Color.web("#eaefce");
                color2 = Color.web("#bbbe65");
            }
            case "Marine" -> {
                color1 = Color.web("#9dacff");
                color2 = Color.web("#6f74d2");
            }
            case "Emerald" -> {
                color1 = Color.web("#adbd90");
                color2 = Color.web("#6e8f72");
            }
            case "Sandcastle" -> {
                color1 = Color.web("#e4c16f");
                color2 = Color.web("#b88b4a");
            }
        }

        if((i+j)%2==0){
            square.setBackground(new Background(new BackgroundFill(color1, CornerRadii.EMPTY, Insets.EMPTY)));
        }else{
            square.setBackground(new Background(new BackgroundFill(color2, CornerRadii.EMPTY, Insets.EMPTY)));
        }

    }
}

//    private void addPiece(Square square, Piece piece){
//        square.getChildren().add(piece);
//        square.occupied = true;
//    }
