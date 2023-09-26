package Cesar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Algorithme  implements Cesar{
	public String option;
	private String nameFile;
	protected String password;
	 
	public Algorithme(String option, String nameFile, String password) {
		this.option = option;
		this.nameFile = nameFile;
		this.password = password;
	}
	
	 @Override
	public void chooseAlgo(int numberCesar) {
		algo(numberCesar);
	}
	
	 private void algo(int numberCesar) {
        List<String> lignes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(nameFile))) {
            String ligne;
            numberCesar %= 26;

            // Lisez le contenu ligne par ligne
            while ((ligne = reader.readLine()) != null) {
                System.out.println(ligne);

                StringBuilder ligneModifiee = new StringBuilder();

                // Parcourez chaque caractère de la ligne
                for (int i = 0; i < ligne.length(); i++) {
                    char caractere = ligne.charAt(i);

                    // Vérifiez si le caractère actuel est un espace
                    if (caractere == ' ') {
                        // Si c'est le cas, ajoutez-le tel quel
                        ligneModifiee.append(caractere);
                    } else {
                        // Sinon, ajoutez 5 à la valeur ASCII des autres caractères
                        int valeurASCII = (int) caractere;
                        if("crypt".equals(option)) {                        	
                        	valeurASCII += numberCesar;
                        } else {
                        	valeurASCII -= numberCesar;
                        }
                        ligneModifiee.append((char) valeurASCII);
                    }
                }

                // Ajoutez la ligne modifiée à la liste
                lignes.add(ligneModifiee.toString());
                System.out.println("---------------------");
                System.out.println(ligneModifiee);
            }

            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameFile))) {
            // Parcourez chaque ligne modifiée et écrivez-la dans le fichier
            for (String ligneModifiee : lignes) {
                writer.write(ligneModifiee);
                writer.newLine(); // Ajoutez un retour à la ligne après chaque ligne
            }

            System.out.println("Le fichier a été écrit avec succès !");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	
	
	
	
}
