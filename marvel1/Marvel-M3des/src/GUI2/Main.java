package GUI2;
	
import java.io.IOException;

import engine.Game;
import engine.Player;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Cover;
import model.world.Damageable;
import model.world.Direction;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;



public class Main extends Application {
	Player first;
	Player second;
	Game g;
	GridPane x= new GridPane() ;
	int i;
	int j;
	ListView<String> listview;
	Board board;
	public void alertthrow(String error) {
		Alert a=new Alert(AlertType.ERROR);
		a.setHeaderText(error);
		a.setHeight(100);
		a.setWidth(600);
		a.showAndWait();
	}
	
	public void updatecast(HBox abilities,HBox n,HBox q,VBox v2,Stage primaryStage,ImageView[]imgs) {
		abilities.getChildren().clear();
		abilities.getChildren().add(new Label("Abilities: "));
 		for(int j=0;j<3;j++)	{
 			Ability ab=g.getCurrentChampion().getAbilities().get(j);
 		if(g.getCurrentChampion().getAbilities().get(j).getCastArea().equals(AreaOfEffect.DIRECTIONAL)) {
 				MenuButton ability=new MenuButton(g.getCurrentChampion().getAbilities().get(j).getName());
 				MenuItem castup= new MenuItem("up");
		 		MenuItem castdown= new MenuItem("down");
		 		MenuItem castleft= new MenuItem("left");
		 		MenuItem castright= new MenuItem("right");
		 		ability.getItems().addAll(castup,castdown,castright,castleft);
		 		castup.setOnAction(event->{
		 			try {
						g.castAbility(ab,Direction.UP);
					} catch (NotEnoughResourcesException | AbilityUseException
							| CloneNotSupportedException e1) {
						alertthrow(e1.getMessage());
					}
		 			 checkgameover(primaryStage);
		 			board.makeBoard(imgs);
		 			updateInfo(n,q,v2,imgs);
		 		});
		 			
		 			
		 		
		 		castdown.setOnAction(event->{
		 			try {
		 				g.castAbility(ab,Direction.DOWN);
					} catch (NotEnoughResourcesException | AbilityUseException
							| CloneNotSupportedException e1) {
						alertthrow(e1.getMessage());
					}
		 			 checkgameover(primaryStage);
		 			board.makeBoard(imgs);
		 			updateInfo(n,q,v2,imgs);
		 			
		 		});
		 		castleft.setOnAction(event->{
		 			try {
		 				g.castAbility(ab,Direction.LEFT);
					} catch (NotEnoughResourcesException | AbilityUseException
							| CloneNotSupportedException e1) {
						alertthrow(e1.getMessage());
					}
		 			 checkgameover(primaryStage);
		 			board.makeBoard(imgs);
		 			updateInfo(n,q,v2,imgs);
		 		});
		 		castright.setOnAction(event->{
		 			try {
		 				g.castAbility(ab,Direction.RIGHT);
					} catch (NotEnoughResourcesException | AbilityUseException
							| CloneNotSupportedException e1) {
						alertthrow(e1.getMessage());
					}
		 			 checkgameover(primaryStage);
		 			board.makeBoard(imgs);
		 			updateInfo(n,q,v2,imgs);
		 		});
		 		abilities.getChildren().add(ability);
 			}
 		else if(g.getCurrentChampion().getAbilities().get(j).getCastArea().equals(AreaOfEffect.SINGLETARGET)){ 
 			Button ability=new Button(g.getCurrentChampion().getAbilities().get(j).getName());
 			ability.setOnAction(event->{
	 			
 				
 					VBox single=new VBox();
 					TextField x= new TextField();
 					TextField y= new TextField();
 					Label sin=new Label("please enter x and y respectively");
 					Button lastnext=new Button("cast ability");
 					lastnext.setOnAction(e->{
 						String a= x.getText();
 		 				String c= y.getText();
 		 				int a1= Integer.parseInt(a);
 		 				int c1= Integer.parseInt(c);
 		 				try {
							g.castAbility(ab,a1,c1);
						} catch (NotEnoughResourcesException | AbilityUseException | InvalidTargetException
								| CloneNotSupportedException e1) {
							// TODO Auto-generated catch block
							alertthrow(e1.getMessage());
						}
 					});
 					single.getChildren().addAll(sin,x,y,lastnext);
	 				
	 				
	 				
	 				Popup po=new Popup();
	 				po.getContent().add(single);
	 				po.show(primaryStage);
	 				po.setAutoHide(true);
	 				
	 				
				
 				checkgameover(primaryStage);
	 			board.makeBoard(imgs);
	 			updateInfo(n,q,v2,imgs);
	 		});
 			abilities.getChildren().add(ability);
 			}
 		else {
 			Button ability=new Button(g.getCurrentChampion().getAbilities().get(j).getName());
 			ability.setOnAction(event->{
	 			try {
	 				g.castAbility(ab);
				} catch (NotEnoughResourcesException | AbilityUseException
						| CloneNotSupportedException e1) {
					alertthrow(e1.getMessage());
				}
	 			 checkgameover(primaryStage);
	 			board.makeBoard(imgs);
	 			updateInfo(n,q,v2,imgs);
	 		});
 			abilities.getChildren().add(ability);
 			}
 		}
		
	}
	public void updateInfo(HBox n,HBox q,VBox v2,ImageView []imgs) {
		q.getChildren().clear();
		v2.getChildren().clear();
		
		ImageView f1= new ImageView(imgs[0].getImage());
		f1.setFitWidth(75);
		f1.setFitHeight(75);
		ImageView s1= new ImageView(imgs[1].getImage()); 	
		s1.setFitWidth(75);
		s1.setFitHeight(75);
		ImageView t1= new ImageView(imgs[2].getImage());
		t1.setFitWidth(75);
		t1.setFitHeight(75);
		ImageView f2=new ImageView(imgs[3].getImage());
		f2.setFitWidth(75);
		f2.setFitHeight(75);
		ImageView s2= new ImageView(imgs[4].getImage()); 		
		s2.setFitWidth(75);
		s2.setFitHeight(75);
		ImageView t2= new ImageView(imgs[5].getImage());
		t2.setFitWidth(75);
		t2.setFitHeight(75);
		HBox oo=new HBox();
		oo.getChildren().addAll(f1,s1,t1,f2,s2,t2);
		oo.setSpacing(60);
		Label d= new Label(getinfo(first.getTeam().get(0).getName()));
 		Label y= new Label(getinfo(first.getTeam().get(1).getName()));
 		Label z= new Label(getinfo(first.getTeam().get(2).getName()));
 		Label current= new Label("Current Champion: "+ g.getCurrentChampion().getName());
 		
 		v2.getChildren().add(current);
 		v2.getChildren().addAll(oo);
 		q.getChildren().addAll(d,y,z);
 		q.setAlignment(Pos.CENTER_LEFT);
		Label w= new Label(getinfo(second.getTeam().get(0).getName()));
 		Label m= new Label(getinfo(second.getTeam().get(1).getName()));
 		Label o= new Label(getinfo(second.getTeam().get(2).getName()));
 		q.getChildren().addAll(w,m,o);
 		q.setAlignment(Pos.CENTER_RIGHT);
 		v2.getChildren().add(q);
 		n.getChildren().add(v2);
 		
 		
	}
	public void checkgameover(Stage primaryStage) {
		if(g.checkGameOver()!=null) {
				Label gameover=new Label(g.checkGameOver()+ " Won");
				VBox last1=new VBox();
				last1.getChildren().add(gameover);
				Scene last= new Scene(last1);
				primaryStage.setScene(last);
				primaryStage.show();
				
			}
	}
	
	public void listener(Button b,int x1,Stage primaryStage,Scene fourthscene,	VBox v,Scene fifthscene,HBox n,ImageView []imgs) {
		b.setOnAction(e->{
			if(x1==1) {
			for(Champion c: first.getTeam()) {
	    		if(b.getId().equals(c.getName())) {
	    			first.setLeader(c);
	    			
	    			primaryStage.setScene(fourthscene);
					primaryStage.show();
	    			
	    			break;
	    		}
	    	}}
			else {
				for(Champion c: second.getTeam()) {
		    		if(b.getId().equals(c.getName())) {
		    			second.setLeader(c);
		    			
		    			
		    			break;
		    		}
		    	}
				
				
				VBox v2=new VBox();
				g=new Game(first,second);
				x.setAlignment(Pos.CENTER);
		 		board= new Board( x ,"Coral",g,imgs);
		 		Label p= new Label("leader:" + first.getLeader().getName());
		 		Label l= new Label("leader:" + second.getLeader().getName());
		 		
		 		v.getChildren().addAll(l,new Label("" +second.getName()+'\n'+ "("+ second.getTeam().get(0).getName()+" " +second.getTeam().get(1).getName()+ " "+ second.getTeam().get(2).getName()+")"));
		 		v.getChildren().add(x);	 		
		 		v.setAlignment(Pos.CENTER);
		 		v.getChildren().addAll(p,new Label("" +first.getName()+'\n'+ "("+ first .getTeam().get(0).getName()+" " +first .getTeam().get(1).getName()+ " "+ first .getTeam().get(2).getName()+")"));
		 		n.getChildren().add(v);
		 		HBox q= new HBox();
		 		updateInfo(n,q,v2,imgs);
		 		
		 		 MenuButton move=new MenuButton("Move");
			 		MenuItem up= new MenuItem("up");
			 		MenuItem down= new MenuItem("down");
			 		MenuItem left= new MenuItem("right");
			 		MenuItem right= new MenuItem("left");
			 		move.getItems().addAll(up,down,left,right);
			 		up.setOnAction(event->{
			 			try {
							g.move(Direction.UP);
						} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
							alertthrow(e1.getMessage());
						}	
			 		   checkgameover(primaryStage);
			 			board.makeBoard(imgs);
			 			updateInfo(n,q,v2,imgs);
			 		});
			 		down.setOnAction(event->{
			 			try {
							g.move(Direction.DOWN);
						} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
							alertthrow(e1.getMessage());
						}
			 			 checkgameover(primaryStage);
			 			board.makeBoard(imgs);
			 			updateInfo(n,q,v2,imgs);
			 		});
			 		right.setOnAction(event->{
			 			try {
							g.move(Direction.RIGHT);
						} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
							alertthrow(e1.getMessage());
						}
			 			 checkgameover(primaryStage);
			 			board.makeBoard(imgs);
			 			updateInfo(n,q,v2,imgs);
			 		});
			 		left.setOnAction(event->{
			 			try {
							g.move(Direction.LEFT);
						} catch (NotEnoughResourcesException | UnallowedMovementException e1) {
							alertthrow(e1.getMessage());
						}
			 			 checkgameover(primaryStage);
			 			board.makeBoard(imgs);
			 			updateInfo(n,q,v2,imgs);
			 		});
					
			 		MenuButton attack=new MenuButton("Attack");
			 		MenuItem up1= new MenuItem("up");
			 		MenuItem down1= new MenuItem("down");
			 		MenuItem left1= new MenuItem("right");
			 		MenuItem right1= new MenuItem("left");
			 		attack.getItems().addAll(up1,down1,left1,right1);
			 		up1.setOnAction(event->{
			 			try {
							g.attack(Direction.UP);
						} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
							alertthrow(e1.getMessage());
						}
			 			 checkgameover(primaryStage);
			 			board.makeBoard(imgs);
			 			updateInfo(n,q,v2,imgs);
			 		});
			 		down1.setOnAction(event->{
			 			try {
							g.attack(Direction.DOWN);
						} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
							alertthrow(e1.getMessage());
						}
			 			 checkgameover(primaryStage);
			 			board.makeBoard(imgs);
			 			updateInfo(n,q,v2,imgs);
			 			
			 		});
			 		left1.setOnAction(event->{
			 			try {
							g.attack(Direction.LEFT);
						} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
							alertthrow(e1.getMessage());
						}
			 			 checkgameover(primaryStage);
			 			board.makeBoard(imgs);
			 			updateInfo(n,q,v2,imgs);
			 		});
			 		right1.setOnAction(event->{
			 			try {
							g.attack(Direction.RIGHT);
						} catch (NotEnoughResourcesException | ChampionDisarmedException | InvalidTargetException e1) {
							alertthrow(e1.getMessage());
						}
			 			 checkgameover(primaryStage);
			 			board.makeBoard(imgs);
			 			updateInfo(n,q,v2,imgs);
			 		});
			 		
			 		HBox abilities=new HBox();
			 		Label l101= new Label("Abilities:");
			 		abilities.getChildren().add(l101);
			 		for(int j=0;j<3;j++)	{
			 			Ability ab=g.getCurrentChampion().getAbilities().get(j);
			 		if(g.getCurrentChampion().getAbilities().get(j).getCastArea().equals(AreaOfEffect.DIRECTIONAL)) {
			 				MenuButton ability=new MenuButton(g.getCurrentChampion().getAbilities().get(j).getName());
			 				
			 				MenuItem castup= new MenuItem("up");
					 		MenuItem castdown= new MenuItem("down");
					 		MenuItem castleft= new MenuItem("left");
					 		MenuItem castright= new MenuItem("right");
					 		attack.getItems().addAll(up1,down1,left1,right1);
					 		castup.setOnAction(event->{
					 			try {
									g.castAbility(ab,Direction.UP);
								} catch (NotEnoughResourcesException | AbilityUseException
										| CloneNotSupportedException e1) {
									alertthrow(e1.getMessage());
								}
					 			 checkgameover(primaryStage);
					 			board.makeBoard(imgs);
					 			updateInfo(n,q,v2,imgs);
					 		});
					 			
					 			
					 		
					 		castdown.setOnAction(event->{
					 			try {
					 				g.castAbility(ab,Direction.DOWN);
								} catch (NotEnoughResourcesException | AbilityUseException
										| CloneNotSupportedException e1) {
									alertthrow(e1.getMessage());
								}
					 			 checkgameover(primaryStage);
					 			board.makeBoard(imgs);
					 			updateInfo(n,q,v2,imgs);
					 			
					 		});
					 		castleft.setOnAction(event->{
					 			try {
					 				g.castAbility(ab,Direction.LEFT);
								} catch (NotEnoughResourcesException | AbilityUseException
										| CloneNotSupportedException e1) {
									alertthrow(e1.getMessage());
								}
					 			 checkgameover(primaryStage);
					 			board.makeBoard(imgs);
					 			updateInfo(n,q,v2,imgs);
					 		});
					 		castright.setOnAction(event->{
					 			try {
					 				g.castAbility(ab,Direction.RIGHT);
								} catch (NotEnoughResourcesException | AbilityUseException
										| CloneNotSupportedException e1) {
									alertthrow(e1.getMessage());
								}
					 			 checkgameover(primaryStage);
					 			board.makeBoard(imgs);
					 			updateInfo(n,q,v2,imgs);
					 		});
					 		abilities.getChildren().add(ability);
			 			}
			 		else if(g.getCurrentChampion().getAbilities().get(j).getCastArea().equals(AreaOfEffect.SINGLETARGET)){ 
			 			Button ability=new Button(g.getCurrentChampion().getAbilities().get(j).getName());
			 			ability.setOnAction(event->{
				 			
			 				
			 					VBox single=new VBox();
			 					TextField x= new TextField();
			 					TextField y= new TextField();
			 					Label sin=new Label("please enter x and y respectively");
			 					Button lastnext=new Button("cast ability");
			 					lastnext.setOnAction(ev->{
			 						String a= x.getText();
			 		 				String c= y.getText();
			 		 				int a1= Integer.parseInt(a);
			 		 				int c1= Integer.parseInt(c);
			 		 				try {
										g.castAbility(ab,a1,c1);
									} catch (NotEnoughResourcesException | AbilityUseException | InvalidTargetException
											| CloneNotSupportedException e1) {
										// TODO Auto-generated catch block
										alertthrow(e1.getMessage());
									}
			 					});
			 					single.getChildren().addAll(sin,x,y,lastnext);
				 				
				 				
				 				
				 				Popup po=new Popup();
				 				po.getContent().add(single);
				 				po.show(primaryStage);
				 				po.setAutoHide(true);
				 				
				 				
							
			 				checkgameover(primaryStage);
				 			board.makeBoard(imgs);
				 			updateInfo(n,q,v2,imgs);
				 		});
			 			abilities.getChildren().add(ability);
			 			}
			 		else {
			 			Button ability=new Button(g.getCurrentChampion().getAbilities().get(j).getName());
			 			ability.setOnAction(event->{
				 			try {
				 				g.castAbility(ab);
							} catch (NotEnoughResourcesException | AbilityUseException
									| CloneNotSupportedException e1) {
								alertthrow(e1.getMessage());
							}
				 			 checkgameover(primaryStage);
				 			board.makeBoard(imgs);
				 			updateInfo(n,q,v2,imgs);
				 		});
			 			abilities.getChildren().add(ability);
			 			}
			 		}
			 		Button leader= new Button("useleaderability");
			 		leader.setOnAction(event->{
			 			try {
							g.useLeaderAbility();
						} catch (LeaderNotCurrentException e1) {
							
							alertthrow(e1.getMessage());
						} catch (LeaderAbilityAlreadyUsedException e1) {
							
							alertthrow(e1.getMessage());
						}
			 			 checkgameover(primaryStage);
			 			board.makeBoard(imgs);
			 			updateInfo(n,q,v2,imgs);
			 		});
			 		
				 		Button endturn=new Button("End Turn");
				 		endturn.setOnAction(event->{
				 			g.endTurn();
				 			board.makeBoard(imgs);
				 			updatecast(abilities,n,q,v,primaryStage,imgs);
				 			updateInfo(n,q,v2,imgs);
				 		});
			 		
				 		HBox temp101=new HBox();
				 		Label l102=new Label("Actions:");
				 		temp101.getChildren().add(l102);
				 		temp101.getChildren().addAll(move,attack,endturn);
				 		temp101.getChildren().add(leader);
				 		move.setAlignment(Pos.CENTER);
				 		attack.setAlignment(Pos.CENTER);
				 		endturn.setAlignment(Pos.CENTER);
				 		temp101.setSpacing(20);
				 		abilities.setSpacing(20);
				 		temp101.setAlignment(Pos.CENTER);
				 		abilities.setAlignment(Pos.CENTER);
				 		v.setSpacing(20);
				 		
				 		v.getChildren().addAll(temp101,abilities);

		 
		 		primaryStage.setScene(fifthscene);
				primaryStage.show();
				
				
			}
		
			
		});
		
	}
	public String getinfo(String name) {
		String s= "";
		for(Champion c: Game.getAvailableChampions()) {
			if (c.getName().equals(name)) {
				s+=c.getName() + '\n' +" Health points:"+ c.getCurrentHP() + '\n'+" Attack Damage: "+ c.getAttackDamage()+ '\n' +" Attack Range:"+ c.getAttackRange()+ '\n' +" Speed: "+ c.getSpeed() +'\n'+"Current Action Points:"+c.getCurrentActionPoints()+'\n'+"Current Mana"+c.getMana()+ '\n'+getinfo2(c.getName()); 
				break;
			}
		
		}
		return s;
	}
	public String getinfo2(String name) {
		String s= "";
		for(Champion c: Game.getAvailableChampions()) {
			if (c.getName().equals(name)) {
				for(int i=0;i<c.getAbilities().size();i++) {
				s+='\n' + " Abilities:"+ c.getAbilities().get(i).getName()+'\n'+"Mana Cost:"+ c.getAbilities().get(i).getManaCost()+'\n'+"RequiredActionPoints"+ c.getAbilities().get(i).getRequiredActionPoints()+'\n'+"Current CoolDown:"+c.getAbilities().get(i).getCurrentCooldown()+'\n'+"Cast Range:"+c.getAbilities().get(i).getCastRange()+'\n'+"Base Cooldown:"+c.getAbilities().get(i).getBaseCooldown()+'\n'+"Cast Area:"+c.getAbilities().get(i).getCastArea();
				}
				break;
			}
		
		}
		return s;
	}
	
	public void listener(ImageView view,VBox v,VBox v1,VBox v2,HBox h1,HBox h2,ImageView[] imgs) {
		 view.setOnMouseClicked(e->{
			 if(i<3) {
		    	for(Champion c: Game.getAvailableChampions()) {
		    		if(view.getId().equals(c.getName())) {
		    			first.getTeam().add(c);
		    			Image temp= view.getImage();
		    			ImageView temp1= new ImageView(temp);
		    			temp1.setFitWidth(100);
		    		    temp1.setFitHeight(100);
		    		    ImageView temp2=new ImageView(temp);
		    		    temp2.setFitWidth(200);
		    		    temp2.setFitHeight(200);
		    		    ImageView temp3=new ImageView(temp);
		    		    temp3.setId(view.getId());
		    		    imgs[i]= temp3;
		    		    v1.getChildren().add(temp1);
		    		    h1.getChildren().add(temp2);
		    			view.setVisible(false);
		    			break;
		    		}
		    	}
			     i++;
		    }
			 else if(i<6) {
				 for(Champion c: Game.getAvailableChampions()) {
			    		if(view.getId().equals(c.getName())) {
			    			second.getTeam().add(c);
			    			Image temp= view.getImage();
			    			ImageView temp1=new ImageView(temp);
			    			temp1.setFitWidth(100);
			    		    temp1.setFitHeight(100);
			    		    ImageView temp2=new ImageView(temp);
			    		    temp2.setFitWidth(200);
			    		    temp2.setFitHeight(200);
			    		    ImageView temp3=new ImageView(temp);
			    		    temp3.setId(view.getId());
			    		    imgs[i]= temp3;
			    		     v2.getChildren().add(temp1);
			    		     h2.getChildren().add(temp2);
			    			view.setVisible(false);
			    			break;
			    		}	
			    	}
			     i++;				 
			 }	 
		 });
	}	
	
	public void start(Stage primaryStage) {
		try {
			Image img1 = new Image("file:./images/startscreen.jpg");
			BackgroundImage bb = new BackgroundImage(img1, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100,100,true,true,true,true));
			Background bb2=new Background(bb);
			VBox parent =new VBox();
			parent.setSpacing(500);
			BackgroundImage bg = new BackgroundImage( new Image("file:./images/choose.jpeg"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100,100,true,true,true,true));
			Background bg2=new Background(bg);
			parent.setBackground(bg2);
			Scene firstscene= new Scene(parent,1350,700);
			VBox vs=new VBox();
			Image img2 = new Image("file:./images/startgame.jpg");
			ImageView startgame = new ImageView(img2);
			startgame.setFitHeight(150);
			startgame.setFitWidth(150);
			startgame.setOnMouseClicked(e->{
				primaryStage.setScene(firstscene);
				primaryStage.show();
			});
			Scene start=new Scene(vs,1350,700);
			vs.setBackground(bb2);
			vs.getChildren().add(startgame);
			vs.setAlignment(Pos.BOTTOM_CENTER);
		    VBox v3 =new VBox();
		    Scene thirdscene= new Scene(v3,1350,700);
		    VBox v4 = new VBox();
		    Scene fourthscene= new Scene(v4,1350,700);
		    VBox v5= new VBox();
		    HBox n5= new HBox();
		    Scene fifthscene = new Scene(n5,1350,700);
		    Image kk = new Image("file:./images/boardback.jfif");
			BackgroundImage kk1 = new BackgroundImage(kk, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100,100,true,true,true,true));
			Background kk2=new Background(kk1);
			n5.setBackground(kk2);
		    Label fpLabel=new Label("FIRST PLAYER");
			fpLabel.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD,FontPosture.ITALIC, 14.0));
			fpLabel.setTextFill(Color.web("Red"));
            HBox write=new HBox();
            write.setAlignment(Pos.CENTER);
			VBox pl = new VBox(fpLabel);
			TextField text= new TextField();
			pl.getChildren().add(text);
			pl.setAlignment(Pos.CENTER);
			text.setFont(Font.font(STYLESHEET_CASPIAN, FontPosture.ITALIC, 12.0));
			fpLabel.setAlignment(Pos.CENTER);
			text.setAlignment(Pos.CENTER);
			write.getChildren().add(pl);
			Label spLabel=new Label("SECOND PLAYER");
			spLabel.setTextFill(Color.web("Red"));
			spLabel.setAlignment(Pos.CENTER);
			spLabel.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD,FontPosture.ITALIC, 14.0));
			VBox pl2 = new VBox(spLabel);
			pl2.setAlignment(Pos.CENTER);
			TextField text2= new TextField();
			text2.setFont(Font.font(STYLESHEET_CASPIAN, FontPosture.ITALIC, 12.0));
			text2.setAlignment(Pos.CENTER);
			pl2.getChildren().add(text2);
			write.setSpacing(500);
			write.getChildren().addAll(pl2);
			parent.getChildren().add(write);
			ImageView choosechampions = new ImageView("file:./images/choosechampion.crdownload");
			choosechampions.setFitHeight(150);
			choosechampions.setFitWidth(150);
			HBox movebutton=new HBox(choosechampions);
			movebutton.setAlignment(Pos.BOTTOM_CENTER);
			parent.getChildren().add(movebutton);
			i=0;
		    Image i1 = new Image("file:./images/Captain America.png");
		    ImageView view= new ImageView(i1);
		    view.setId("Captain America");
		    view.setFitWidth(100);
		    view.setFitHeight(100);
		    Image i2 = new Image("file:./images/Deadpool.png");
		    ImageView view2= new ImageView(i2);
		    view2.setId("Deadpool");
		    view2.setFitWidth(100);
		    view2.setFitHeight(100);
		    Image i3 = new Image("file:./images/Dr Strange.png");
		    ImageView view3= new ImageView(i3);
		    view3.setId("Dr Strange");
		    view3.setFitWidth(100);
		    view3.setFitHeight(100);
		    Image i4 = new Image("file:./images/Electro.png");
		    ImageView view4= new ImageView(i4);
		    view4.setId("Electro");
		    view4.setFitWidth(100);
		    view4.setFitHeight(100);
		    Image i5 = new Image("file:./images/Ghost Rider");
		    ImageView view5= new ImageView(i5);
		    view5.setId("Ghost Rider");
		    view5.setFitWidth(100);
		    view5.setFitHeight(100);
		    Image i6 = new Image("file:./images/Hela.png");
		    ImageView view6= new ImageView(i6);
		    view6.setId("Hela");
		    view6.setFitWidth(100);
		    view6.setFitHeight(100); 
		    Image i7 = new Image("file:./images/Hulk.png");
		    ImageView view7= new ImageView(i7);
		    view7.setId("Hulk");
		    view7.setFitWidth(100);
		    view7.setFitHeight(100);
		    Image i8 = new Image("file:./images/Ice.png");
		    ImageView view8= new ImageView(i8);
		    view8.setId("Iceman");
		    view8.setFitWidth(100);
		    view8.setFitHeight(100);
		    Image i9 = new Image("file:./images/Iron Man.png");
		    ImageView view9= new ImageView(i9);
		    view9.setId("Ironman");
		    view9.setFitWidth(100);
		    view9.setFitHeight(100);
		    Image i00 = new Image("file:./images/Loki.png");
		    ImageView view00= new ImageView(i00);
		    view00.setId("Loki");
		    view00.setFitWidth(100);
		    view00.setFitHeight(100);
		    Image i01 = new Image("file:./images/Quicksilver.png");
		    ImageView view01= new ImageView(i01);
		    view01.setId("Quicksilver");
		    view01.setFitWidth(100);
		    view01.setFitHeight(100);
		    Image i02 = new Image("file:./images/SpiderMan.png");
		    ImageView view02= new ImageView(i02);
		    view02.setId("Spiderman");
		    view02.setFitWidth(100);
		    view02.setFitHeight(100);
		    Image i03 = new Image("file:./images/Thor.png");
		    ImageView view03= new ImageView(i03);
		    view03.setId("Thor");
		    view03.setFitWidth(100);
		    view03.setFitHeight(100);
		    Image i04 = new Image("file:./images/Venom.png");
		    ImageView view04= new ImageView(i04);
		    view04.setId("Venom");
		    view04.setFitWidth(100);
		    view04.setFitHeight(100);
		    Image i05 = new Image("file:./images/Yellow.png");
		    ImageView view05= new ImageView(i05);
		    view05.setId("Yellow Jacket");
		    view05.setFitWidth(100);
		    view05.setFitHeight(100);
		    VBox firstchamp=new VBox();
		    VBox secchamp=new VBox();
		    VBox v2 =new VBox();
		    HBox possibleleader1=new HBox();
		    HBox possibleleader2=new HBox();
		    ImageView [] imgs=new ImageView[6];
		    listener(view,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view2,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view3,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view4,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view5,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view6,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view7,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view8,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view9,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view00,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view01,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view02,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view03,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view04,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    listener(view05,v2,firstchamp,secchamp,possibleleader1,possibleleader2,imgs);
		    
			HBox z = new HBox();
			HBox z2 = new HBox();
			HBox z5 = new HBox();
			z.getChildren().add(view);
			z.getChildren().add(view2);
			z.getChildren().add(view3);
			z.setSpacing(20);
			z.getChildren().add(view4);
			z.getChildren().add(view5);
			z.getChildren().add(view6);
			z.getChildren().add(view7);
			z.getChildren().add(view8);
			z.getChildren().add(view9);
			z5.getChildren().add(view00);
			z5.getChildren().add(view01);
			z5.getChildren().add(view02);
			z5.getChildren().add(view03);
			z5.getChildren().add(view04);
			z5.getChildren().add(view05);
			z.setAlignment(Pos.BOTTOM_CENTER);
			z5.setAlignment(Pos.BOTTOM_CENTER);
			z5.setSpacing(20);
			firstchamp.setSpacing(20);
			secchamp.setSpacing(20);
			z2.getChildren().addAll(firstchamp,secchamp);
			v2.getChildren().add(z);
			v2.getChildren().add(z5);
			z2.setAlignment(Pos.CENTER);
			z2.setSpacing(800);
			
			HBox namesselect=new HBox();
			namesselect.setSpacing(780);
			namesselect.setAlignment(Pos.CENTER);
			v2.setSpacing(20.0);
			firstchamp.setAlignment(Pos.CENTER);
			secchamp.setAlignment(Pos.CENTER);
			Button button2= new Button("Choose Leaders");
			HBox bbbbb=new HBox();
			bbbbb.getChildren().add(button2);
			bbbbb.setAlignment(Pos.CENTER);
			button2.setAlignment(Pos.CENTER);
			v2.getChildren().addAll(namesselect,z2,bbbbb);
			Image img3 = new Image("file:./images/tain.jpg");
			BackgroundImage bb3 = new BackgroundImage(img3, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100,100,true,true,true,true));
			Background bb4=new Background(bb3);
			v2.setBackground(bb4);
			Scene secondscene= new Scene(v2,1350,700);
			  button2.setOnAction(e->{
				  if(i==6) {
					 
					  Button button3= new Button(first.getTeam().get(0).getName());
					  Button button4= new Button(first.getTeam().get(1).getName());
					  Button button5= new Button(first.getTeam().get(2).getName());
					  Button button6= new Button(second.getTeam().get(0).getName());
					  Button button7= new Button(second.getTeam().get(1).getName());
					  Button button8= new Button(second.getTeam().get(2).getName());
					  button3.setId(first.getTeam().get(0).getName());
					  button4.setId(first.getTeam().get(1).getName());
					  button5.setId(first.getTeam().get(1).getName());
					  button6.setId(second.getTeam().get(2).getName());
					  button7.setId(second.getTeam().get(2).getName());
					  button8.setId(second.getTeam().get(2).getName());
					  listener(button3,1,primaryStage,fourthscene,v5,fifthscene,n5,imgs);
					  listener(button4,1,primaryStage,fourthscene,v5,fifthscene,n5,imgs);
					  listener(button5,1,primaryStage,fourthscene,v5,fifthscene,n5,imgs);
					  listener(button6,2,primaryStage,fourthscene,v5,fifthscene,n5,imgs);
					  listener(button7,2,primaryStage,fourthscene,v5,fifthscene,n5,imgs);
					  listener(button8,2,primaryStage,fourthscene,v5,fifthscene,n5,imgs);
					 
					  
					  HBox chooseplayer1= new HBox();
					  Image img4 = new Image("file:./images/bkbk.jpg");
						BackgroundImage bb5 = new BackgroundImage(img4, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100,100,true,true,true,true));
						Background bb6=new Background(bb5);
					
						v3.setBackground(bb6);
					  Label l3=new Label(first.getName()+" Choose a leader");
					  l3.setTextFill(Color.web("Red"));
					  l3.setAlignment(Pos.TOP_CENTER);
					l3.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.EXTRA_BOLD,FontPosture.ITALIC , 20));
					  v3.getChildren().add(l3);
					  chooseplayer1.getChildren().addAll(button3,button4,button5);
					  chooseplayer1.setAlignment(Pos.CENTER);
					  chooseplayer1.setSpacing(240);
					  possibleleader1.setAlignment(Pos.CENTER);
					  possibleleader1.setSpacing(100);
					  v3.getChildren().add(possibleleader1);
					  v3.getChildren().add(chooseplayer1);
					  v3.setAlignment(Pos.CENTER);
					  HBox chooseplayer2= new HBox();
					  Label l2=new Label(second.getName()+" Choose a leader");
					  l2.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.EXTRA_BOLD,FontPosture.ITALIC , 20));
					  l2.setTextFill(Color.web("Red"));
					  l2.setAlignment(Pos.TOP_CENTER);
					  v4.getChildren().add(l2);
					  chooseplayer2.getChildren().addAll(button6,button7,button8);
					  chooseplayer2.setAlignment(Pos.CENTER);
					  chooseplayer2.setSpacing(240);
					  possibleleader2.setAlignment(Pos.CENTER);
					  possibleleader2.setSpacing(100);
					  v4.getChildren().add(possibleleader2);
					  v4.getChildren().add(chooseplayer2);
					  v4.setAlignment(Pos.CENTER);
					  v3.setSpacing(40);
					  v4.setSpacing(40);
					  v4.setBackground(bb6);
						HBox h2= new HBox();
						HBox h3= new HBox();
						h3.setAlignment(Pos.CENTER);
						h3.setSpacing(220);
						v3.getChildren().addAll(h3);
						for(Champion c: first.getTeam()) {
							Label l = new Label();
							String s = "";
							s+=" Health points:"+ c.getCurrentHP() + '\n'+" Attack Damage: "+ c.getAttackDamage() + '\n' +" Attack Range:"+ c.getAttackRange()+ '\n' +" Speed: "+ c.getSpeed() ;
							l.setText(s);
							l.setTextFill(Color.web("Red"));
							l.setFont(Font.font(STYLESHEET_CASPIAN,FontWeight.BOLD, 15));
							h3.getChildren().add(l);
						}
						h2.setAlignment(Pos.TOP_CENTER);
						h2.setSpacing(220);
						v4.getChildren().addAll(h2);
						for(Champion c: second.getTeam()) {
							Label l = new Label();
							String s = "";
							s+=" Health points:"+ c.getCurrentHP() + '\n'+" Attack Damage: "+ c.getAttackDamage()+ '\n' +" Attack Range:"+ c.getAttackRange()+ '\n' +" Speed: "+ c.getSpeed() ;
							l.setText(s);
							l.setTextFill(Color.web("Red"));
							l.setFont(Font.font(STYLESHEET_CASPIAN,FontWeight.BOLD, 15));
							h2.getChildren().add(l);
						}
						
						primaryStage.setScene(thirdscene);
						primaryStage.show();
				  }
				  });
			  
			  
		 
		    
		    
			
			choosechampions.setOnMouseClicked(e->{
				try {
					Game.loadAbilities("Abilities.csv");
					Game.loadChampions("Champions.csv");
					Tooltip t1= new Tooltip(getinfo(view.getId()));
				    t1.setMinSize(200,200);
				    Tooltip.install(view, t1);
				    Tooltip t2= new Tooltip(getinfo(view2.getId()));
				    t2.setMinSize(200,200);
				    Tooltip.install(view2, t2);
				    Tooltip t3= new Tooltip(getinfo("Dr Strange"));
				    t3.setMinSize(200,200);
				    Tooltip.install(view3, t3);
				    Tooltip t4= new Tooltip(getinfo(view4.getId()));
				    t4.setMinSize(200,200);
				    Tooltip.install(view4, t4);
				    Tooltip t5= new Tooltip(getinfo(view5.getId()));
				    t5.setMinSize(200,200);
				    Tooltip.install(view5, t5);
				    Tooltip t6= new Tooltip(getinfo(view6.getId()));
				    t6.setMinSize(200,200);
				    Tooltip.install(view6, t6);
				    
				    Tooltip t7= new Tooltip(getinfo(view7.getId()));
				    t7.setMinSize(200,200);
				    Tooltip.install(view7, t7);
				    Tooltip t8= new Tooltip(getinfo(view8.getId()));
				    t8.setMinSize(200,200);
				    Tooltip.install(view8, t8);
				    Tooltip t9= new Tooltip(getinfo(view9.getId()));
				    t9.setMinSize(200,200);
				    Tooltip.install(view9, t9);
				    Tooltip t10= new Tooltip(getinfo(view00.getId()));
				    t10.setMinSize(200,200);
				    Tooltip.install(view00, t10);
				    Tooltip t11= new Tooltip(getinfo(view01.getId()));
				    t11.setMinSize(200,200);
				    Tooltip.install(view01, t11);
				    Tooltip t12= new Tooltip(getinfo(view02.getId()));
				    t12.setMinSize(200,200);
				    Tooltip.install(view02, t12);
				    Tooltip t13= new Tooltip(getinfo(view03.getId()));
				    t13.setMinSize(200,200);
				    Tooltip.install(view03, t13);
				    Tooltip t14= new Tooltip(getinfo(view04.getId()));
				    t14.setMinSize(200,200);
				    Tooltip.install(view04, t14);
				    Tooltip t15= new Tooltip(getinfo(view05.getId()));
				    t15.setMinSize(200,200);
				    Tooltip.install(view05, t15);
				}
			 		
			 			
					
				 catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				first= new Player(text.getText());
				second= new Player(text2.getText());
				Label temp111=new Label(text.getText()+"'s Team");
				Label temp222=new Label(text2.getText()+"'s Team");
				temp111.setFont(Font.font(STYLESHEET_CASPIAN,FontWeight.BOLD, FontPosture.ITALIC, 20));
				temp222.setFont(Font.font(STYLESHEET_CASPIAN,FontWeight.BOLD, FontPosture.ITALIC, 20));
				temp222.setTextFill(Color.web("White"));
				temp111.setTextFill(Color.web("Red"));
				namesselect.getChildren().addAll(temp111,temp222);
				primaryStage.setScene(secondscene);
				primaryStage.show();
				
				
			});
			
			
			
			primaryStage.setScene(start);
			primaryStage.show();
	    	
			
			
			
			
			
		/*	BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());*/
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
