package Cesar;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Algorithme implements Cesar {
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
        boolean commentaireTrouveCrypt = false;
        boolean commentaireTrouveDecrypt = false;
        boolean crypter = true; // Par défaut, crypter les lignes
        boolean decrypter = true; // Par défaut, crypter les lignes

        try (BufferedReader reader = new BufferedReader(new FileReader(nameFile))) {
            String ligne;
            String prevLine = null;

            // Lire le contenu ligne par ligne
            while ((ligne = reader.readLine()) != null) {
                if (ligne.contains("<!--crypt-->")) {
                    // Si le commentaire <!--crypt--> est présent, désactivez le cryptage
                    crypter = false;
                    commentaireTrouveCrypt = true; // Marquez que le commentaire <!--crypt--> a été trouvé
                } else if (ligne.contains("<!--decrypt-->")) {
                    // Si le commentaire <!--decrypt--> est présent, désactivez le décryptage
                    decrypter = false;
                    commentaireTrouveDecrypt = true; // Marquez que le commentaire <!--decrypt--> a été trouvé
                }

                // Conservez la ligne telle quelle, sans la modification de chiffrement/déchiffrement
                lignes.add(ligne);
                prevLine = ligne;
            }

            int lastIndex = lignes.size() - 1;

            // Ajoutez le commentaire à la fin du tableau
            if ("crypt".equals(option)) {
                if (!commentaireTrouveCrypt) {
                    lignes.add("<!--crypt-->");
                }
                if (prevLine != null && prevLine.equals("<!--decrypt-->")) {
                    lignes.remove(lastIndex );
                }
            } else if ("decrypt".equals(option)) {
                if (!commentaireTrouveDecrypt) {
                    lignes.add("<!--decrypt-->");
                }
                if (prevLine != null && prevLine.equals("<!--crypt-->")) {
                    lignes.remove(lastIndex );
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Modifier les lignes au besoin
        for (int i = 0; i < lignes.size(); i++) {
            String ligne = lignes.get(i);
            if (!ligne.contains("<!--crypt-->") && !ligne.contains("<!--decrypt-->")) {
                // Traitez le texte (chiffrement ou déchiffrement) si le cryptage ou le décryptage est activé
                StringBuilder ligneModifiee = new StringBuilder();
                for (int j = 0; j < ligne.length(); j++) {
                    char caractere = ligne.charAt(j);
                    int valeurASCII = (int) caractere;

                    if ("crypt".equals(option)) {
                        if (crypter) {
                            valeurASCII += numberCesar;
                        }
                    } else if ("decrypt".equals(option)) {
                        if (decrypter) {
                            valeurASCII -= numberCesar;
                        }
                    }

                    ligneModifiee.append((char) valeurASCII);
                }
                lignes.set(i, ligneModifiee.toString());
            }
        }

        // Écrivez les lignes (texte modifié) dans un nouveau fichier
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameFile))) {
            for (String ligne : lignes) {
                writer.write(ligne);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
