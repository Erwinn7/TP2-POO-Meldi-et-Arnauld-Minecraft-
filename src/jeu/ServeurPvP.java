package jeu;
import composants.Joueur;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.io.ObjectInputStream;



public class ServeurPvP {
    private ArrayList<String> joueurs = new ArrayList<>();
    public static final int SERVER_PORT=8000;

    public void demarrerServeur(Joueur j1 ,Joueur j2) throws IOException {
    	try {
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
	        System.out.println("Serveur PvP démarré sur le port " + SERVER_PORT);
	
	        // Acceptation de deux joueurs
	        System.out.println("En attente des joueurs...");
	        j1.seConnecter();
	        Socket socketJoueur1 = serverSocket.accept();
	        ObjectInputStream ois1 = new ObjectInputStream(socketJoueur1.getInputStream());
	        String nomJ1 = (String) ois1.readObject();
	        System.out.println(nomJ1+" connecté !");
	        joueurs.add(nomJ1);
	
	        j2.seConnecter();
	        Socket socketJoueur2 = serverSocket.accept();
	        ObjectInputStream ois2 = new ObjectInputStream(socketJoueur2.getInputStream());
	        String nomJ2 = (String) ois2.readObject();
	        System.out.println(nomJ2+" connecté !");
	        joueurs.add(nomJ2);

	        //serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
    }


}
