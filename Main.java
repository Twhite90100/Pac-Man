package application;

import java.io.File;
import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;

public class Main extends Application {
	
	private Stage fenetrePrincipal = new Stage();
    private int screenWidth = (int) Screen.getPrimary().getBounds().getWidth();
    private int screenHeight = (int) Screen.getPrimary().getBounds().getHeight();
    private int sceneWidth = 0;
    private int sceneHeight = 0;
    private boolean pleinEcran;
    private String etatSon="On";
    
	@Override
	public void start(Stage accueil) {
		try {	
			setBoiteDeDialogueAffichage();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	private void setBoiteDeDialogueAffichage() {
	
		Alert dBox = new Alert(AlertType.CONFIRMATION);
		dBox.setTitle("PAC-MAN : Affichage");
		dBox.setHeaderText("Plein ecran ou Mode fenetré");
		dBox.setContentText("Choisir le mode d'affichage :");
		
		ButtonType btnPleinEcran = new ButtonType("Plein Ecran");
		ButtonType btnModeFenetre = new ButtonType("Mode Fenetré");
		ButtonType btnQuitter = new ButtonType("Quitter");
		
		dBox.getButtonTypes().setAll(btnPleinEcran,btnModeFenetre,btnQuitter);
		Optional<ButtonType> choice = dBox.showAndWait();
		
		if (choice.get() == btnPleinEcran) {
			pleinEcran = true;
			fenetrePrincipal.setFullScreenExitHint("");
			setAcceuilScene();
		}
		else if (choice.get() == btnModeFenetre) {
			pleinEcran = false;
			setAcceuilScene();
		}
		else if (choice.get() == btnQuitter) {
			fenetrePrincipal.close();
		}
	}
	
	private void setBoiteDeDialogueQuitter() {
		
		Alert dBox = new Alert(AlertType.CONFIRMATION);
		dBox.setTitle("PAC-MAN");
		dBox.setHeaderText("Quitter le jeu ?");
		
		ButtonType btnRetour = new ButtonType("Retour");
		ButtonType btnQuitter = new ButtonType("Quitter");
		
		dBox.getButtonTypes().setAll(btnQuitter,btnRetour);
		Optional<ButtonType> choice = dBox.showAndWait();
		
		if (choice.get() == btnRetour) {
			
		}
		else if (choice.get() == btnQuitter) {
			fenetrePrincipal.close();
		}

	}
	
	private void setAcceuilScene() {
		
		AffichagePleinEcranOuFenetre();
		
		// Creation de la fenetre et de la scene
		final Group accueilGroup = new Group();
		final Scene accueilStart = new Scene(accueilGroup,sceneWidth,sceneHeight);
		accueilStart.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		fenetrePrincipal.setTitle("PAC-MAN : START");
		fenetrePrincipal.setScene(accueilStart);
		
		// Affichage Image Background Accueil
		Image backgroundAccueilStart = new Image("image(s)/background(s)/imgBackgroundAccueil.jpg");
		ImageView backgroundAccueilStartView = new ImageView(backgroundAccueilStart);
		backgroundAccueilStartView.setFitHeight(sceneHeight);
		backgroundAccueilStartView.setFitWidth(sceneWidth);
		backgroundAccueilStartView.isPreserveRatio();
		
		if(pleinEcran==true){
			backgroundAccueilStartView.setFitHeight(screenHeight);
			backgroundAccueilStartView.setFitWidth(screenWidth);
			backgroundAccueilStartView.isPreserveRatio();
		}
		
		// Declaration et affichage label version
		String versionActuelle = new String();
		versionActuelle = "alpha-test_1.0.0";
		Label version = new Label("version:"+versionActuelle);
		
		// Declaration et placement de la vbox
		VBox vboxAccueil = new VBox();
		vboxAccueil.setLayoutX(sceneWidth/2-100);
		vboxAccueil.setLayoutY(sceneHeight/2+sceneHeight/4);
		
		if(pleinEcran==true){
			vboxAccueil.setLayoutX(screenWidth/2-100);
			vboxAccueil.setLayoutY(screenHeight/2+screenHeight/4);
		}	
		
		// Declaration du Bouton START
		Image boutonStart = new Image("image(s)/bouton(s)/BoutonSTART.png");
		ImageView boutonStartView = new ImageView(boutonStart);
		Button start = new Button();
		start.setGraphic(boutonStartView);	
		vboxAccueil.getChildren().add(start);
		
		start.setOnAction(event -> {
			StopMusiqueEcranStart();
			fenetrePrincipal.close();
			setMenuScene();
		});
		
		start.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranStart();
				fenetrePrincipal.close();
				setMenuScene();
			}
		});		
		
		accueilGroup.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ESCAPE)) {
				quitterJeu();
			}
		});
		
		Image boutonSoundOn = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOn.png");
		ImageView boutonSoundOnView = new ImageView(boutonSoundOn);
		Image boutonSoundOff = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOff.png");
		ImageView boutonSoundOffView = new ImageView(boutonSoundOff);
		
		// Lancement du theme de l'ecran start
		StartMusiqueEcranStart();
		
		// Declaration du Bouton Sound
		Button sound = new Button();
		sound.setGraphic(boutonSoundOnView);
		sound.setLayoutX(sceneWidth-125);
		sound.setLayoutY(10);
		if(pleinEcran==true){
			sound.setLayoutX(screenWidth-125);
			sound.setLayoutY(10);
		}
		
		sound.setOnAction(event -> {
			switch(etatSon) {
			case "On": sound.setGraphic(boutonSoundOffView);
					   etatSon="Off";
					   StopMusiqueEcranStart();
					   break;
			case "Off": sound.setGraphic(boutonSoundOnView);
						etatSon="On";
						StartMusiqueEcranStart();
						break;
			}
			
		});
		
		sound.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				switch(etatSon) {
				case "On": sound.setGraphic(boutonSoundOffView);
						   etatSon="Off";
						   StopMusiqueEcranStart();
						   break;
				case "Off": sound.setGraphic(boutonSoundOnView);
							etatSon="On";
							StartMusiqueEcranStart();
							break;
				}
			}
		});
		
		accueilGroup.getChildren().addAll(backgroundAccueilStartView,vboxAccueil,sound,version);
		fenetrePrincipal.show();
	}
	private void setMenuScene() {
		AffichagePleinEcranOuFenetre();
		final Group menuGroup = new Group();
		final Scene menu = new Scene(menuGroup,sceneWidth,sceneHeight);
		fenetrePrincipal.setTitle("PAC-MAN : MENU");
		fenetrePrincipal.setScene(menu);
		
		// Affichage Image Background Accueil
		Image backgroundMenu = new Image("image(s)/background(s)/imgBackgroundMenu.jpg");
		ImageView backgroundMenuView = new ImageView(backgroundMenu);
		backgroundMenuView.setFitHeight(sceneHeight);
		backgroundMenuView.setFitWidth(sceneWidth);
		backgroundMenuView.isPreserveRatio();
		
		if(pleinEcran==true){
			backgroundMenuView.setFitHeight(screenHeight);
			backgroundMenuView.setFitWidth(screenWidth);
			backgroundMenuView.isPreserveRatio();
		}
		
		menuGroup.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ESCAPE)) {
				quitterJeu();
			}
		});
		
		Image boutonSoundOn = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOn.png");
		ImageView boutonSoundOnView = new ImageView(boutonSoundOn);
		Image boutonSoundOff = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOff.png");
		ImageView boutonSoundOffView = new ImageView(boutonSoundOff);
		
		// Lancement du theme de l'ecran Menu
		StartMusiqueEcranMenu();
		
		// Declaration du Bouton Sound
		Button sound = new Button();
		sound.setGraphic(boutonSoundOnView);
		sound.setLayoutX(sceneWidth-125);
		sound.setLayoutY(10);
		
		if(pleinEcran==true){
			sound.setLayoutX(screenWidth-125);
			sound.setLayoutY(10);
		}
		
		sound.setOnAction(event -> {
			switch(etatSon) {
			case "On": sound.setGraphic(boutonSoundOffView);
					   etatSon="Off";
					   StopMusiqueEcranMenu();
					   break;
			case "Off": sound.setGraphic(boutonSoundOnView);
						etatSon="On";
						StartMusiqueEcranMenu();
						break;
			}
			
		});
		
		sound.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				switch(etatSon) {
				case "On": sound.setGraphic(boutonSoundOffView);
						   etatSon="Off";
						   StopMusiqueEcranMenu();
						   break;
				case "Off": sound.setGraphic(boutonSoundOnView);
							etatSon="On";
							StartMusiqueEcranMenu();
							break;
				}
			}
		});
		
		// Declaration et placement de la vbox
		VBox vboxMenu = new VBox();
		vboxMenu.setLayoutX(sceneWidth/2-100);
		vboxMenu.setLayoutY(sceneHeight/6);
		vboxMenu.setSpacing(sceneHeight/16);
		
		if(pleinEcran==true){
			vboxMenu.setLayoutX(screenWidth/2-100);
			vboxMenu.setLayoutY(screenHeight/6);
			vboxMenu.setSpacing(screenHeight/16);
		}
		
		// Declaration du Bouton Jouer
		Button jouer = new Button();
		Image boutonJouer = new Image("image(s)/bouton(s)/BoutonJOUER.png");
		ImageView boutonJouerView = new ImageView(boutonJouer);
		jouer.setGraphic(boutonJouerView);
		vboxMenu.getChildren().add(jouer);
		
		jouer.setOnAction(event -> {
			StopMusiqueEcranMenu();
			fenetrePrincipal.close();
			setModeJoueurScene();
		});
		
		jouer.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranMenu();
				fenetrePrincipal.close();
				setModeJoueurScene();
			}
		});	
		
		// Declaration du Bouton Classement
		Button classement = new Button();
		Image boutonClassement = new Image("image(s)/bouton(s)/BoutonCLASSEMENT.png");
		ImageView boutonClassementView = new ImageView(boutonClassement);
		classement.setGraphic(boutonClassementView);
		vboxMenu.getChildren().add(classement);
		
		classement.setOnAction(event -> {
			StopMusiqueEcranMenu();
			fenetrePrincipal.close();
			setClassementScene();
		});
		
		classement.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranMenu();
				fenetrePrincipal.close();
				setClassementScene();
			}
		});	
		
		// Declaration du Bouton Options
		Button options = new Button();
		Image boutonOptions = new Image("image(s)/bouton(s)/BoutonOPTIONS.png");
		ImageView boutonOptionsView = new ImageView(boutonOptions);
		options.setGraphic(boutonOptionsView);
		vboxMenu.getChildren().add(options);
		
		options.setOnAction(event -> {
			StopMusiqueEcranMenu();
			fenetrePrincipal.close();
			setOptionsScene();
		});
		
		options.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranMenu();
				fenetrePrincipal.close();
				setOptionsScene();
			}
		});	
		
		// Declaration du Bouton Quitter
		Button quitter = new Button();
		Image boutonQuitter = new Image("image(s)/bouton(s)/BoutonQUITTER.png");
		ImageView boutonQuitterView = new ImageView(boutonQuitter);
		quitter.setGraphic(boutonQuitterView);
		vboxMenu.getChildren().add(quitter);
		
		quitter.setOnAction(event -> {
			StopMusiqueEcranMenu();
			fenetrePrincipal.close();
		});
		
		quitter.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranMenu();
				fenetrePrincipal.close();
			}
		});		
		
		menuGroup.getChildren().addAll(backgroundMenuView,vboxMenu,sound);
		fenetrePrincipal.show();
	}
	private void setModeJoueurScene() {
		AffichagePleinEcranOuFenetre();
		final Group modeJoueurGroup = new Group();
		final Scene modeJoueur = new Scene(modeJoueurGroup,sceneWidth,sceneHeight);
		fenetrePrincipal.setTitle("PAC-MAN : MODE JOUEUR(S)");
		fenetrePrincipal.setScene(modeJoueur);
		
		// Affichage Image Background ModeJoueur
		Image backgroundModeJoueur = new Image("image(s)/background(s)/imgBackgroundModeJoueur.jpg");
		ImageView backgroundModeJoueurView = new ImageView(backgroundModeJoueur);
		backgroundModeJoueurView.setFitHeight(sceneHeight);
		backgroundModeJoueurView.setFitWidth(sceneWidth);
		backgroundModeJoueurView.isPreserveRatio();
		
		if(pleinEcran==true){
			backgroundModeJoueurView.setFitHeight(screenHeight);
			backgroundModeJoueurView.setFitWidth(screenWidth);
			backgroundModeJoueurView.isPreserveRatio();
		}
		
		modeJoueurGroup.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ESCAPE)) {
				quitterJeu();
			}
		});
		
		Image boutonSoundOn = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOn.png");
		ImageView boutonSoundOnView = new ImageView(boutonSoundOn);
		Image boutonSoundOff = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOff.png");
		ImageView boutonSoundOffView = new ImageView(boutonSoundOff);
		
		// Lancement du theme de l'ecran ModeJoueur
		StartMusiqueEcranModeJoueur();
		
		// Declaration du Bouton Sound
		Button sound = new Button();
		sound.setGraphic(boutonSoundOnView);
		sound.setLayoutX(sceneWidth-125);
		sound.setLayoutY(10);
		
		if(pleinEcran==true){
			sound.setLayoutX(screenWidth-125);
			sound.setLayoutY(10);
		}
		
		sound.setOnAction(event -> {
			switch(etatSon) {
			case "On": sound.setGraphic(boutonSoundOffView);
					   etatSon="Off";
					   StopMusiqueEcranModeJoueur();
					   break;
			case "Off": sound.setGraphic(boutonSoundOnView);
						etatSon="On";
						StartMusiqueEcranModeJoueur();
						break;
			}
			
		});
		
		sound.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				switch(etatSon) {
				case "On": sound.setGraphic(boutonSoundOffView);
						   etatSon="Off";
						   StopMusiqueEcranModeJoueur();
						   break;
				case "Off": sound.setGraphic(boutonSoundOnView);
							etatSon="On";
							StartMusiqueEcranModeJoueur();
							break;
				}
			}
		});
		
		// Declaration et placement de la vbox
		VBox vboxModeJoueur = new VBox();
		vboxModeJoueur.setLayoutX(sceneWidth/2-100);
		vboxModeJoueur.setLayoutY(sceneHeight/6);
		vboxModeJoueur.setSpacing(sceneHeight/16);
		
		if(pleinEcran==true){
			vboxModeJoueur.setLayoutX(screenWidth/2-100);
			vboxModeJoueur.setLayoutY(screenHeight/6);
			vboxModeJoueur.setSpacing(screenHeight/16);
		}
		
		// Declaration du Bouton Menu
		Button menu = new Button();
		Image boutonMenu = new Image("image(s)/bouton(s)/BoutonMENU.png");
		ImageView boutonMenuView = new ImageView(boutonMenu);
		menu.setGraphic(boutonMenuView);
		vboxModeJoueur.getChildren().add(menu);
		
		menu.setOnAction(event -> {
			StopMusiqueEcranModeJoueur();
			fenetrePrincipal.close();
			setMenuScene();
		});
		
		menu.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranModeJoueur();
				fenetrePrincipal.close();
				setMenuScene();
			}
		});	
		
		// Declaration du Bouton Solo
		Button solo = new Button();
		Image boutonSolo = new Image("image(s)/bouton(s)/BoutonSOLO.png");
		ImageView boutonSoloView = new ImageView(boutonSolo);
		solo.setGraphic(boutonSoloView);
		vboxModeJoueur.getChildren().add(solo);
		
		solo.setOnAction(event -> {
			StopMusiqueEcranModeJoueur();
			fenetrePrincipal.close();
			setModeSoloModeDeJeuScene();
		});
		solo.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranModeJoueur();
				fenetrePrincipal.close();
				setModeSoloModeDeJeuScene();
			}
		});
		
		// Declaration du Bouton Multi
		Button multi = new Button();
		Image boutonMulti = new Image("image(s)/bouton(s)/BoutonMULTI.png");
		ImageView boutonMultiView = new ImageView(boutonMulti);
		multi.setGraphic(boutonMultiView);
		vboxModeJoueur.getChildren().add(multi);
		
		multi.setOnAction(event -> {
			StopMusiqueEcranModeJoueur();
			fenetrePrincipal.close();
			setModeMultiModeDeJeuScene();
		});
		multi.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranModeJoueur();
				fenetrePrincipal.close();
				setModeMultiModeDeJeuScene();
			}
		});	
		
		// Declaration du Bouton Quitter
		Button quitter = new Button();
		Image boutonQuitter = new Image("image(s)/bouton(s)/BoutonQUITTER.png");
		ImageView boutonQuitterView = new ImageView(boutonQuitter);
		quitter.setGraphic(boutonQuitterView);
		vboxModeJoueur.getChildren().add(quitter);
		
		quitter.setOnAction(event -> {
			StopMusiqueEcranModeJoueur();
			fenetrePrincipal.close();
		});
		
		quitter.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranModeJoueur();
				fenetrePrincipal.close();
			}
		});	
		
		modeJoueurGroup.getChildren().addAll(backgroundModeJoueurView,vboxModeJoueur,sound);
		fenetrePrincipal.show();
	}
	private void setModeSoloModeDeJeuScene() {
		AffichagePleinEcranOuFenetre();
		final Group modeSoloModeDeJeuGroup = new Group();
		final Scene modeSoloModeDeJeu = new Scene(modeSoloModeDeJeuGroup,sceneWidth,sceneHeight);
		fenetrePrincipal.setTitle("PAC-MAN : SOLO - MODE DE JEU ");
		fenetrePrincipal.setScene(modeSoloModeDeJeu);
		
		// Affichage Image Background ModeSoloModeDeJeu
		Image backgroundModeSoloModeDeJeu = new Image("image(s)/background(s)/imgBackgroundModeSoloModeDeJeu.png");
		ImageView backgroundModeSoloModeDeJeuView = new ImageView(backgroundModeSoloModeDeJeu);
		backgroundModeSoloModeDeJeuView.setFitHeight(sceneHeight);
		backgroundModeSoloModeDeJeuView.setFitWidth(sceneWidth);
		backgroundModeSoloModeDeJeuView.isPreserveRatio();
		
		if(pleinEcran==true){
			backgroundModeSoloModeDeJeuView.setFitHeight(screenHeight);
			backgroundModeSoloModeDeJeuView.setFitWidth(screenWidth);
			backgroundModeSoloModeDeJeuView.isPreserveRatio();
		}
		
		modeSoloModeDeJeuGroup.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ESCAPE)) {
				quitterJeu();
			}
		});
		
		Image boutonSoundOn = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOn.png");
		ImageView boutonSoundOnView = new ImageView(boutonSoundOn);
		Image boutonSoundOff = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOff.png");
		ImageView boutonSoundOffView = new ImageView(boutonSoundOff);
		
		// Lancement du theme de l'ecran ModeSolo
		StartMusiqueEcranModeSoloModeDeJeu();
		
		// Declaration du Bouton Sound
		Button sound = new Button();
		sound.setGraphic(boutonSoundOnView);
		sound.setLayoutX(sceneWidth-125);
		sound.setLayoutY(10);
		
		if(pleinEcran==true){
			sound.setLayoutX(screenWidth-125);
			sound.setLayoutY(10);
		}
		
		sound.setOnAction(event -> {
			switch(etatSon) {
			case "On": sound.setGraphic(boutonSoundOffView);
					   etatSon="Off";
					   StopMusiqueEcranModeSoloModeDeJeu();
					   break;
			case "Off": sound.setGraphic(boutonSoundOnView);
						etatSon="On";
						StartMusiqueEcranModeSoloModeDeJeu();
						break;
			}
			
		});
		
		sound.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				switch(etatSon) {
				case "On": sound.setGraphic(boutonSoundOffView);
						   etatSon="Off";
						   StopMusiqueEcranModeSoloModeDeJeu();
						   break;
				case "Off": sound.setGraphic(boutonSoundOnView);
							etatSon="On";
							StartMusiqueEcranModeSoloModeDeJeu();
							break;
				}
			}
		});
		
		// Declaration et placement de la vbox
		VBox vboxModeSoloModeDeJeu = new VBox();
		vboxModeSoloModeDeJeu.setLayoutX(sceneWidth/2-100);
		vboxModeSoloModeDeJeu.setLayoutY(sceneHeight/6);
		vboxModeSoloModeDeJeu.setSpacing(sceneHeight/16);
		
		if(pleinEcran==true){
			vboxModeSoloModeDeJeu.setLayoutX(screenWidth/2-100);
			vboxModeSoloModeDeJeu.setLayoutY(screenHeight/6);
			vboxModeSoloModeDeJeu.setSpacing(screenHeight/16);
		}
		
		// Declaration du Bouton Menu
		Button menu = new Button();
		Image boutonMenu = new Image("image(s)/bouton(s)/BoutonMENU.png");
		ImageView boutonMenuView = new ImageView(boutonMenu);
		menu.setGraphic(boutonMenuView);
		vboxModeSoloModeDeJeu.getChildren().add(menu);
		
		menu.setOnAction(event -> {
			StopMusiqueEcranModeSoloModeDeJeu();
			fenetrePrincipal.close();
			setMenuScene();
		});
		menu.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranModeSoloModeDeJeu();
				fenetrePrincipal.close();
				setMenuScene();
			}
		});	
		
		// Declaration du Bouton Remastered
		Button remastered = new Button();
		Image boutonRemastered = new Image("image(s)/bouton(s)/BoutonREMASTERED.png");
		ImageView boutonRemasteredView = new ImageView(boutonRemastered);
		remastered.setGraphic(boutonRemasteredView);
		vboxModeSoloModeDeJeu.getChildren().add(remastered);
		
		remastered.setOnAction(event -> {
			StopMusiqueEcranModeSoloModeDeJeu();
			fenetrePrincipal.close();
		});
		remastered.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranModeSoloModeDeJeu();
				fenetrePrincipal.close();
			}
		});	
		
		// Declaration du Bouton Remake
		Button remake = new Button();
		Image boutonRemake = new Image("image(s)/bouton(s)/BoutonREMAKE.png");
		ImageView boutonRemakeView = new ImageView(boutonRemake);
		remake.setGraphic(boutonRemakeView);
		vboxModeSoloModeDeJeu.getChildren().add(remake);
		
		remake.setOnAction(event -> {
			StopMusiqueEcranModeSoloModeDeJeu();
			fenetrePrincipal.close();
		});
		remake.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranModeSoloModeDeJeu();
				fenetrePrincipal.close();
			}
		});	
		
		// Declaration du Bouton Retour
		Button retour = new Button();
		Image boutonRetour = new Image("image(s)/bouton(s)/BoutonRETOUR.png");
		ImageView boutonRetourView = new ImageView(boutonRetour);
		retour.setGraphic(boutonRetourView);
		vboxModeSoloModeDeJeu.getChildren().add(retour);
		
		retour.setOnAction(event -> {
			StopMusiqueEcranModeSoloModeDeJeu();
			fenetrePrincipal.close();
			setModeJoueurScene();
		});
		retour.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranModeSoloModeDeJeu();
				fenetrePrincipal.close();
				setModeJoueurScene();
			}
		});	
		
		// Declaration du Bouton Quitter
		Button quitter = new Button();
		Image boutonQuitter = new Image("image(s)/bouton(s)/BoutonQUITTER.png");
		ImageView boutonQuitterView = new ImageView(boutonQuitter);
		quitter.setGraphic(boutonQuitterView);
		vboxModeSoloModeDeJeu.getChildren().add(quitter);
		
		quitter.setOnAction(event -> {
			StopMusiqueEcranModeSoloModeDeJeu();
			fenetrePrincipal.close();
		});
		
		quitter.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranModeSoloModeDeJeu();
				fenetrePrincipal.close();
			}
		});	
		
		modeSoloModeDeJeuGroup.getChildren().addAll(backgroundModeSoloModeDeJeuView,vboxModeSoloModeDeJeu,sound);
		fenetrePrincipal.show();
	}
	private void setModeMultiModeDeJeuScene() {
		AffichagePleinEcranOuFenetre();
		final Group modeMultiModeDeJeuGroup = new Group();
		final Scene modeMultiModeDeJeu = new Scene(modeMultiModeDeJeuGroup,sceneWidth,sceneHeight);
		fenetrePrincipal.setTitle("PAC-MAN : MULTI - MODE DE JEU ");
		fenetrePrincipal.setScene(modeMultiModeDeJeu);
		
		// Affichage Image Background ModeSoloModeDeJeu
		Image backgroundModeMultiModeDeJeu = new Image("image(s)/background(s)/imgBackgroundModeMultiModeDeJeu.png");
		ImageView backgroundModeMultiModeDeJeuView = new ImageView(backgroundModeMultiModeDeJeu);
		backgroundModeMultiModeDeJeuView.setFitHeight(sceneHeight);
		backgroundModeMultiModeDeJeuView.setFitWidth(sceneWidth);
		backgroundModeMultiModeDeJeuView.isPreserveRatio();
		
		if(pleinEcran==true){
			backgroundModeMultiModeDeJeuView.setFitHeight(screenHeight);
			backgroundModeMultiModeDeJeuView.setFitWidth(screenWidth);
			backgroundModeMultiModeDeJeuView.isPreserveRatio();
		}
		
		modeMultiModeDeJeuGroup.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ESCAPE)) {
				quitterJeu();
			}
		});
		
		Image boutonSoundOn = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOn.png");
		ImageView boutonSoundOnView = new ImageView(boutonSoundOn);
		Image boutonSoundOff = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOff.png");
		ImageView boutonSoundOffView = new ImageView(boutonSoundOff);
		
		// Lancement du theme de l'ecran ModeMulti
		StartMusiqueEcranModeMultiModeDeJeu();
		
		// Declaration du Bouton Sound
		Button sound = new Button();
		sound.setGraphic(boutonSoundOnView);
		sound.setLayoutX(sceneWidth-125);
		sound.setLayoutY(10);
		
		if(pleinEcran==true){
			sound.setLayoutX(screenWidth-125);
			sound.setLayoutY(10);
		}
		
		sound.setOnAction(event -> {
			switch(etatSon) {
			case "On": sound.setGraphic(boutonSoundOffView);
					   etatSon="Off";
					   StopMusiqueEcranModeMultiModeDeJeu();
					   break;
			case "Off": sound.setGraphic(boutonSoundOnView);
						etatSon="On";
						StartMusiqueEcranModeMultiModeDeJeu();
						break;
			}
			
		});
		
		sound.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				switch(etatSon) {
				case "On": sound.setGraphic(boutonSoundOffView);
						   etatSon="Off";
						   StopMusiqueEcranModeMultiModeDeJeu();
						   break;
				case "Off": sound.setGraphic(boutonSoundOnView);
							etatSon="On";
							StartMusiqueEcranModeMultiModeDeJeu();
							break;
				}
			}
		});
		
		// Declaration et placement de la vbox
				VBox vboxModeMultiModeDeJeu = new VBox();
				vboxModeMultiModeDeJeu.setLayoutX(sceneWidth/2-100);
				vboxModeMultiModeDeJeu.setLayoutY(sceneHeight/6);
				vboxModeMultiModeDeJeu.setSpacing(sceneHeight/16);
				
				if(pleinEcran==true){
					vboxModeMultiModeDeJeu.setLayoutX(screenWidth/2-100);
					vboxModeMultiModeDeJeu.setLayoutY(screenHeight/6);
					vboxModeMultiModeDeJeu.setSpacing(screenHeight/16);
				}
				
				// Declaration du Bouton Menu
				Button menu = new Button();
				Image boutonMenu = new Image("image(s)/bouton(s)/BoutonMENU.png");
				ImageView boutonMenuView = new ImageView(boutonMenu);
				menu.setGraphic(boutonMenuView);
				vboxModeMultiModeDeJeu.getChildren().add(menu);
				
				menu.setOnAction(event -> {
					StopMusiqueEcranModeMultiModeDeJeu();
					fenetrePrincipal.close();
					setMenuScene();
				});
				menu.setOnKeyPressed(ke -> {
					KeyCode keyCode = ke.getCode();
					if(keyCode.equals(KeyCode.ENTER)) {
						StopMusiqueEcranModeMultiModeDeJeu();
						fenetrePrincipal.close();
						setMenuScene();
					}
				});	
				
				// Declaration du Bouton MultiEnLocal
				Button multiEnLocal = new Button();
				Image boutonMultiEnLocal = new Image("image(s)/bouton(s)/BoutonMULTIENLOCAL.png");
				ImageView boutonMultiEnLocalView = new ImageView(boutonMultiEnLocal);
				multiEnLocal.setGraphic(boutonMultiEnLocalView);
				vboxModeMultiModeDeJeu.getChildren().add(multiEnLocal);
				
				multiEnLocal.setOnAction(event -> {
					StopMusiqueEcranModeMultiModeDeJeu();
					fenetrePrincipal.close();
				});
				multiEnLocal.setOnKeyPressed(ke -> {
					KeyCode keyCode = ke.getCode();
					if(keyCode.equals(KeyCode.ENTER)) {
						StopMusiqueEcranModeMultiModeDeJeu();
						fenetrePrincipal.close();
					}
				});	
				
				// Declaration du Bouton MultiEnLocal
				Button multiEnLigne = new Button();
				Image boutonMultiEnLigne = new Image("image(s)/bouton(s)/BoutonMULTIENLIGNE.png");
				ImageView boutonMultiEnLigneView = new ImageView(boutonMultiEnLigne);
				multiEnLigne.setGraphic(boutonMultiEnLigneView);
				vboxModeMultiModeDeJeu.getChildren().add(multiEnLigne);
				
				multiEnLigne.setOnAction(event -> {
					StopMusiqueEcranModeMultiModeDeJeu();
					fenetrePrincipal.close();
				});
				multiEnLigne.setOnKeyPressed(ke -> {
					KeyCode keyCode = ke.getCode();
					if(keyCode.equals(KeyCode.ENTER)) {
						StopMusiqueEcranModeMultiModeDeJeu();
						fenetrePrincipal.close();
					}
				});
				
				// Declaration du Bouton Retour
				Button retour = new Button();
				Image boutonRetour = new Image("image(s)/bouton(s)/BoutonRETOUR.png");
				ImageView boutonRetourView = new ImageView(boutonRetour);
				retour.setGraphic(boutonRetourView);
				vboxModeMultiModeDeJeu.getChildren().add(retour);
				
				retour.setOnAction(event -> {
					StopMusiqueEcranModeMultiModeDeJeu();
					fenetrePrincipal.close();
					setModeJoueurScene();
				});
				retour.setOnKeyPressed(ke -> {
					KeyCode keyCode = ke.getCode();
					if(keyCode.equals(KeyCode.ENTER)) {
						StopMusiqueEcranModeMultiModeDeJeu();
						fenetrePrincipal.close();
						setModeJoueurScene();
					}
				});	
				
				// Declaration du Bouton Quitter
				Button quitter = new Button();
				Image boutonQuitter = new Image("image(s)/bouton(s)/BoutonQUITTER.png");
				ImageView boutonQuitterView = new ImageView(boutonQuitter);
				quitter.setGraphic(boutonQuitterView);
				vboxModeMultiModeDeJeu.getChildren().add(quitter);
				
				quitter.setOnAction(event -> {
					StopMusiqueEcranModeMultiModeDeJeu();
					fenetrePrincipal.close();
				});
				
				quitter.setOnKeyPressed(ke -> {
					KeyCode keyCode = ke.getCode();
					if(keyCode.equals(KeyCode.ENTER)) {
						StopMusiqueEcranModeMultiModeDeJeu();
						fenetrePrincipal.close();
					}
				});	
		modeMultiModeDeJeuGroup.getChildren().addAll(backgroundModeMultiModeDeJeuView,vboxModeMultiModeDeJeu,sound);
		fenetrePrincipal.show();
	}
	private void setClassementScene() {
		AffichagePleinEcranOuFenetre();
		
		final Group classementGroup = new Group();
		final Scene classement = new Scene(classementGroup,sceneWidth,sceneHeight);
		fenetrePrincipal.setTitle("PAC-MAN : CLASSEMENT");
		fenetrePrincipal.setScene(classement);
		
		classementGroup.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();			
			if(keyCode.equals(KeyCode.ESCAPE)) {
				quitterJeu();
			}
		});
		
		// Affichage Image Background Accueil
		Image backgroundClassement = new Image("image(s)/background(s)/imgBackgroundClassement.jpg");
		ImageView backgroundClassementView = new ImageView(backgroundClassement);
		backgroundClassementView.setFitHeight(sceneHeight);
		backgroundClassementView.setFitWidth(sceneWidth);
		backgroundClassementView.isPreserveRatio();
		
		if(pleinEcran==true) {
			backgroundClassementView.setFitHeight(screenHeight);
			backgroundClassementView.setFitWidth(screenWidth);
			backgroundClassementView.isPreserveRatio();
		}
		
		Image boutonSoundOn = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOn.png");
		ImageView boutonSoundOnView = new ImageView(boutonSoundOn);
		Image boutonSoundOff = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOff.png");
		ImageView boutonSoundOffView = new ImageView(boutonSoundOff);
		
		// Lancement du theme de l'ecran Classement
		StartMusiqueEcranClassement();
		
		// Declaration du Bouton Sound
		Button sound = new Button();
		sound.setGraphic(boutonSoundOnView);
		sound.setLayoutX(sceneWidth-125);
		sound.setLayoutY(10);
		
		if(pleinEcran==true){
			sound.setLayoutX(screenWidth-125);
			sound.setLayoutY(10);
		}
		
		sound.setOnAction(event -> {
			switch(etatSon) {
			case "On": sound.setGraphic(boutonSoundOffView);
					   etatSon="Off";
					   StopMusiqueEcranClassement();
					   break;
			case "Off": sound.setGraphic(boutonSoundOnView);
						etatSon="On";
						StartMusiqueEcranClassement();
						break;
			}
			
		});
		
		sound.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				switch(etatSon) {
				case "On": sound.setGraphic(boutonSoundOffView);
						   etatSon="Off";
						   StopMusiqueEcranClassement();
						   break;
				case "Off": sound.setGraphic(boutonSoundOnView);
							etatSon="On";
							StartMusiqueEcranClassement();
							break;
				}
			}
		});
		
		// Declaration et placement de la vbox
		VBox vboxClassement = new VBox();
		vboxClassement.setLayoutX(sceneWidth/2-100);
		vboxClassement.setLayoutY(sceneHeight/6);
		vboxClassement.setSpacing(sceneHeight/16);
		
		if(pleinEcran==true){
			vboxClassement.setLayoutX(screenWidth/2-100);
			vboxClassement.setLayoutY(screenHeight/6);
			vboxClassement.setSpacing(screenHeight/16);
		}
		
		// Declaration du Bouton Menu
		Button menu = new Button();
		Image boutonMenu = new Image("image(s)/bouton(s)/BoutonMENU.png");
		ImageView boutonMenuView = new ImageView(boutonMenu);
		menu.setGraphic(boutonMenuView);
		vboxClassement.getChildren().add(menu);
		
		menu.setOnAction(event -> {
			StopMusiqueEcranClassement();
			fenetrePrincipal.close();
			setMenuScene();
		});
		menu.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranClassement();
				fenetrePrincipal.close();
				setMenuScene();
			}
		});	
		
		// Declaration du Bouton Quitter
		Button quitter = new Button();
		Image boutonQuitter = new Image("image(s)/bouton(s)/BoutonQUITTER.png");
		ImageView boutonQuitterView = new ImageView(boutonQuitter);
		quitter.setGraphic(boutonQuitterView);
		vboxClassement.getChildren().add(quitter);
		
		quitter.setOnAction(event -> {
			StopMusiqueEcranClassement();
			fenetrePrincipal.close();
		});
		
		quitter.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranClassement();
				fenetrePrincipal.close();
			}
		});	
		
		classementGroup.getChildren().addAll(backgroundClassementView,vboxClassement,sound);
		fenetrePrincipal.show();
	}
	
	private void setOptionsScene() {
		
		AffichagePleinEcranOuFenetre();
		
		final Group optionsGroup = new Group();
		final Scene options = new Scene(optionsGroup,sceneWidth,sceneHeight);
		fenetrePrincipal.setTitle("PAC-MAN : OPTIONS");
		fenetrePrincipal.setScene(options);
		
		optionsGroup.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();			
			if(keyCode.equals(KeyCode.ESCAPE)) {
				
				quitterJeu();
			}
		});
		
		// Affichage Image Background Options
		Image backgroundOptions = new Image("image(s)/background(s)/imgBackgroundOptions.png");
		ImageView backgroundOptionsView = new ImageView(backgroundOptions);
		backgroundOptionsView.setFitHeight(sceneHeight);
		backgroundOptionsView.setFitWidth(sceneWidth);
		backgroundOptionsView.isPreserveRatio();
		
		if(pleinEcran==true){
			backgroundOptionsView.setFitHeight(screenHeight);
			backgroundOptionsView.setFitWidth(screenWidth);
			backgroundOptionsView.isPreserveRatio();
		}
		
		Image boutonSoundOn = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOn.png");
		ImageView boutonSoundOnView = new ImageView(boutonSoundOn);
		Image boutonSoundOff = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOff.png");
		ImageView boutonSoundOffView = new ImageView(boutonSoundOff);
		
		// Lancement du theme de l'ecran Options
		StartMusiqueEcranOptions();
		
		// Declaration du Bouton Sound
		Button sound = new Button();
		sound.setGraphic(boutonSoundOnView);
		sound.setLayoutX(sceneWidth-125);
		sound.setLayoutY(10);
		
		if(pleinEcran==true){
			sound.setLayoutX(screenWidth-125);
			sound.setLayoutY(10);
		}
		
		sound.setOnAction(event -> {
			switch(etatSon) {
			case "On": sound.setGraphic(boutonSoundOffView);
					   etatSon="Off";
					   StopMusiqueEcranOptions();
					   break;
			case "Off": sound.setGraphic(boutonSoundOnView);
						etatSon="On";
						StartMusiqueEcranOptions();
						break;
			}
			
		});
		
		sound.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				switch(etatSon) {
				case "On": sound.setGraphic(boutonSoundOffView);
						   etatSon="Off";
						   StopMusiqueEcranOptions();
						   break;
				case "Off": sound.setGraphic(boutonSoundOnView);
							etatSon="On";
							StartMusiqueEcranOptions();
							break;
				}
			}
		});
		
		// Declaration et placement de la vbox
		VBox vboxOptions = new VBox();
		vboxOptions.setLayoutX(sceneWidth/2-100);
		vboxOptions.setLayoutY(sceneHeight/6);
		vboxOptions.setSpacing(sceneHeight/16);
		
		if(pleinEcran==true){
			vboxOptions.setLayoutX(screenWidth/2-100);
			vboxOptions.setLayoutY(screenHeight/6);
			vboxOptions.setSpacing(screenHeight/16);
		}
				
		// Declaration du Bouton Menu
		Button menu = new Button();
		Image boutonMenu = new Image("image(s)/bouton(s)/BoutonMENU.png");
		ImageView boutonMenuView = new ImageView(boutonMenu);
		menu.setGraphic(boutonMenuView);
		vboxOptions.getChildren().add(menu);
		
		menu.setOnAction(event -> {
			StopMusiqueEcranOptions();
			fenetrePrincipal.close();
			setMenuScene();
		});
		menu.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranOptions();
				fenetrePrincipal.close();
				setMenuScene();
			}
		});		
		
		// Declaration du Bouton Controles
		Button controles = new Button();
		Image boutonControle = new Image("image(s)/bouton(s)/BoutonCONTROLES.png");
		ImageView boutonControleView = new ImageView(boutonControle);
		controles.setGraphic(boutonControleView);
		vboxOptions.getChildren().add(controles);
		
		controles.setOnAction(event -> {
			StopMusiqueEcranOptions();
			fenetrePrincipal.close();
			setControlesScene();
		});
		
		controles.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranOptions();
				fenetrePrincipal.close();
				setControlesScene();
			}
		});
		
		// Declaration du Bouton Credits
		Button credits = new Button();
		Image boutonCredits = new Image("image(s)/bouton(s)/BoutonCREDITS.png");
		ImageView boutonCreditsView = new ImageView(boutonCredits);
		credits.setGraphic(boutonCreditsView);
		vboxOptions.getChildren().add(credits);
		
		// Declaration du Bouton Quitter
		Button quitter = new Button();
		Image boutonQuitter = new Image("image(s)/bouton(s)/BoutonQUITTER.png");
		ImageView boutonQuitterView = new ImageView(boutonQuitter);
		quitter.setGraphic(boutonQuitterView);
		vboxOptions.getChildren().add(quitter);
		
		quitter.setOnAction(event -> {
			StopMusiqueEcranOptions();
			fenetrePrincipal.close();
		});
		
		quitter.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranOptions();
				fenetrePrincipal.close();
			}
		});		
		
		optionsGroup.getChildren().addAll(backgroundOptionsView,vboxOptions,sound);
		fenetrePrincipal.show();
	}
	private void setControlesScene() {
		AffichagePleinEcranOuFenetre();
		
		final Group controlesGroup = new Group();
		final Scene controles = new Scene(controlesGroup,sceneWidth,sceneHeight);
		fenetrePrincipal.setTitle("PAC-MAN : OPTIONS - CONTROLES");
		fenetrePrincipal.setScene(controles);
		
		controlesGroup.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();			
			if(keyCode.equals(KeyCode.ESCAPE)) {
				
				quitterJeu();
			}
		});
		
		// Affichage Image Background Controles
		Image backgroundControles = new Image("image(s)/background(s)/imgBackgroundControles.jpg");
		ImageView backgroundControlesView = new ImageView(backgroundControles);
		backgroundControlesView.setFitHeight(sceneHeight);
		backgroundControlesView.setFitWidth(sceneWidth);
		backgroundControlesView.isPreserveRatio();
		
		if(pleinEcran==true){
			backgroundControlesView.setFitHeight(screenHeight);
			backgroundControlesView.setFitWidth(screenWidth);
			backgroundControlesView.isPreserveRatio();
		}
		
		Image boutonSoundOn = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOn.png");
		ImageView boutonSoundOnView = new ImageView(boutonSoundOn);
		Image boutonSoundOff = new Image("image(s)/icone(s)/sound(s)/BoutonSoundOff.png");
		ImageView boutonSoundOffView = new ImageView(boutonSoundOff);
		
		// Lancement du theme de l'ecran Controles
		StartMusiqueEcranControles();
		
		// Declaration du Bouton Sound
		Button sound = new Button();
		sound.setGraphic(boutonSoundOnView);
		sound.setLayoutX(sceneWidth-125);
		sound.setLayoutY(10);
		
		if(pleinEcran==true){
			sound.setLayoutX(screenWidth-125);
			sound.setLayoutY(10);
		}
		
		sound.setOnAction(event -> {
			switch(etatSon) {
			case "On": sound.setGraphic(boutonSoundOffView);
					   etatSon="Off";
					   StopMusiqueEcranControles();
					   break;
			case "Off": sound.setGraphic(boutonSoundOnView);
						etatSon="On";
						StartMusiqueEcranControles();
						break;
			}
			
		});
		
		sound.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				switch(etatSon) {
				case "On": sound.setGraphic(boutonSoundOffView);
						   etatSon="Off";
						   StopMusiqueEcranControles();
						   break;
				case "Off": sound.setGraphic(boutonSoundOnView);
							etatSon="On";
							StartMusiqueEcranControles();
							break;
				}
			}
		});
		
		// Declaration et placement de la vbox
		VBox vboxControles = new VBox();
		vboxControles.setLayoutX(sceneWidth/2-100);
		vboxControles.setLayoutY(sceneHeight/6);
		vboxControles.setSpacing(sceneHeight/16);
		
		if(pleinEcran==true){
			vboxControles.setLayoutX(screenWidth/2-100);
			vboxControles.setLayoutY(screenHeight/6);
			vboxControles.setSpacing(screenHeight/16);
		}
		
		// Declaration du Bouton Menu
		Button menu = new Button();
		Image boutonMenu = new Image("image(s)/bouton(s)/BoutonMENU.png");
		ImageView boutonMenuView = new ImageView(boutonMenu);
		menu.setGraphic(boutonMenuView);
		vboxControles.getChildren().add(menu);
		
		menu.setOnAction(event -> {
			StopMusiqueEcranControles();
			fenetrePrincipal.close();
			setMenuScene();
		});
		menu.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranControles();
				fenetrePrincipal.close();
				setMenuScene();
			}
		});	
		
		// Declaration du Bouton Retour
		Button retour = new Button();
		Image boutonRetour = new Image("image(s)/bouton(s)/BoutonRETOUR.png");
		ImageView boutonRetourView = new ImageView(boutonRetour);
		retour.setGraphic(boutonRetourView);
		vboxControles.getChildren().add(retour);
		
		retour.setOnAction(event -> {
			StopMusiqueEcranControles();
			fenetrePrincipal.close();
			setOptionsScene();
		});
		retour.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranControles();
				fenetrePrincipal.close();
				setOptionsScene();
			}
		});	
		
		// Declaration du Bouton Quitter
		Button quitter = new Button();
		Image boutonQuitter = new Image("image(s)/bouton(s)/BoutonQUITTER.png");
		ImageView boutonQuitterView = new ImageView(boutonQuitter);
		quitter.setGraphic(boutonQuitterView);
		vboxControles.getChildren().add(quitter);
		
		quitter.setOnAction(event -> {
			StopMusiqueEcranControles();
			fenetrePrincipal.close();
		});
		
		quitter.setOnKeyPressed(ke -> {
			KeyCode keyCode = ke.getCode();
			if(keyCode.equals(KeyCode.ENTER)) {
				StopMusiqueEcranControles();
				fenetrePrincipal.close();
			}
		});	
		
		controlesGroup.getChildren().addAll(backgroundControlesView,vboxControles,sound);
		fenetrePrincipal.show();
	}
	private void quitterJeu() {
			if(pleinEcran==true) {
			fenetrePrincipal.setFullScreen(true);
			}
			setBoiteDeDialogueQuitter();
	}
	private void ResponsiveSize() {
		
        if (screenWidth <= 800 && screenHeight <= 600) {
            sceneWidth = 600;
            sceneHeight = 350;
            fenetrePrincipal.setResizable(false);
            fenetrePrincipal.centerOnScreen();
            
        } else if (screenWidth <= 1280 && screenHeight <= 768) {
            sceneWidth = 800;
            sceneHeight = 450;
            fenetrePrincipal.setResizable(false);
            fenetrePrincipal.centerOnScreen();
        } else if (screenWidth <= 1920 && screenHeight <= 1080) {
            sceneWidth = 1200;
            sceneHeight = 850;
            fenetrePrincipal.setResizable(false);
            fenetrePrincipal.centerOnScreen();
        }
	}
	
	private void AffichagePleinEcranOuFenetre() {
		
		if(pleinEcran==true){
			fenetrePrincipal.setFullScreen(true);
			fenetrePrincipal.setResizable(false);
			fenetrePrincipal.centerOnScreen();
		}
		else if(pleinEcran==false) {
			ResponsiveSize();
		}
	}
	private void StartMusiqueEcranStart() {
		AudioClip musiqueEcranStart = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranStart.mp3").toURI().toString());
		musiqueEcranStart.play();
	}
	private void StopMusiqueEcranStart() {
		AudioClip musiqueEcranStart = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranStart.mp3").toURI().toString());
		musiqueEcranStart.stop();
	}
	private void StartMusiqueEcranMenu() {
		AudioClip musiqueEcranMenu = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranMenu.mp3").toURI().toString());
		musiqueEcranMenu.play();
	}
	private void StopMusiqueEcranMenu() {
		AudioClip musiqueEcranMenu = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranMenu.mp3").toURI().toString());
		musiqueEcranMenu.stop();
	}
	private void StartMusiqueEcranModeJoueur() {
		AudioClip musiqueEcranModeJoueur = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranModeJoueur.mp3").toURI().toString());
		musiqueEcranModeJoueur.play();
	}
	private void StopMusiqueEcranModeJoueur() {
		AudioClip musiqueEcranModeJoueur = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranModeJoueur.mp3").toURI().toString());
		musiqueEcranModeJoueur.stop();
	}
	private void StartMusiqueEcranModeSoloModeDeJeu() {
		AudioClip musiqueEcranModeSoloModeDeJeu = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranModeSoloModeDeJeu.mp3").toURI().toString());
		musiqueEcranModeSoloModeDeJeu.play();
	}
	private void StopMusiqueEcranModeSoloModeDeJeu() {
		AudioClip musiqueEcranModeSoloModeDeJeu = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranModeSoloModeDeJeu.mp3").toURI().toString());
		musiqueEcranModeSoloModeDeJeu.stop();
	}
	private void StartMusiqueEcranModeMultiModeDeJeu() {
		AudioClip musiqueEcranModeMultiModeDeJeu = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranModeMultiModeDeJeu.mp3").toURI().toString());
		musiqueEcranModeMultiModeDeJeu.play();
	}
	private void StopMusiqueEcranModeMultiModeDeJeu() {
		AudioClip musiqueEcranModeMultiModeDeJeu = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranModeMultiModeDeJeu.mp3").toURI().toString());
		musiqueEcranModeMultiModeDeJeu.stop();
	}
	private void StartMusiqueEcranClassement() {
		AudioClip musiqueEcranClassement = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranClassement.mp3").toURI().toString());
		musiqueEcranClassement.play();
	}
	private void StopMusiqueEcranClassement() {
		AudioClip musiqueEcranClassement = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranClassement.mp3").toURI().toString());
		musiqueEcranClassement.stop();
	}
	private void StartMusiqueEcranOptions() {
		AudioClip musiqueEcranMenu = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranOptions.mp3").toURI().toString());
		musiqueEcranMenu.play();
	}
	private void StopMusiqueEcranOptions() {
		AudioClip musiqueEcranMenu = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranOptions.mp3").toURI().toString());
		musiqueEcranMenu.stop();
	}
	private void StartMusiqueEcranControles() {
		AudioClip musiqueEcranControles = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranControles.mp3").toURI().toString());
		musiqueEcranControles.play();
	}
	private void StopMusiqueEcranControles() {
		AudioClip musiqueEcranControles = new AudioClip(new File("src/musique(s)/ecran(s)/MusiqueEcranControles.mp3").toURI().toString());
		musiqueEcranControles.stop();
	}
	public static void main(String[] args) {
		launch(args);
		
	}
}
