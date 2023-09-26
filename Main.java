package Cesar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.mindrot.jbcrypt.BCrypt;

public class Main {
	public static String filename;
	public static boolean afficheeConnexion = false;
	public static boolean afficheeInscription = false;
	public static boolean afficheeConfirmInscription = false;
	
    public static void main(String[] args) {
    	//je crée mon appli
        JFrame frame = new JFrame("CryptMyFile");
        //je fais en sorte que quand j'appuie sur la croix l'appli se ferme bien
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //je met au mileu de l'écran mon appli
        frame.getContentPane().setLayout(new FlowLayout());

        
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ImageIcon originalIcon = new ImageIcon("icon.png");
        ImageIcon resizedIcon = resizeIcon(originalIcon, 20, 20);

        //je crée une box à la vertical où je mets à l'interieur des composants (c'est comme une div en display flex)
        Box connexionBox = Box.createVerticalBox();
        Box boxUsername = Box.createVerticalBox();
        JLabel username = new JLabel("Username");
        JTextField textUsername = new JTextField();
        JLabel errorUsernameLabel = new JLabel("");
        textUsername.setPreferredSize(new Dimension(200, textUsername.getPreferredSize().height));
        boxUsername.add(username);
        boxUsername.add(textUsername);
        boxUsername.add(errorUsernameLabel);

        Box boxPassword = Box.createVerticalBox();
        JLabel password = new JLabel("Password");
        JLabel passwordIcon = new JLabel(resizedIcon);
        JPasswordField textPassword = new JPasswordField();
        JLabel errorPasswordLabel = new JLabel("");
        textPassword.setPreferredSize(new Dimension(200, textPassword.getPreferredSize().height));
        boxPassword.add(password);
        boxPassword.add(textPassword);
        boxPassword.add(passwordIcon);
        boxPassword.add(errorPasswordLabel);

        JButton connexion = new JButton("Connexion");
        JButton connecteMoi = new JButton("Je me connecte");
        
        
        Box inscriptionBox = Box.createVerticalBox();
        Box boxUsernameInsert = Box.createVerticalBox();
        JLabel usernameInsert = new JLabel("Username");
        JTextField textUsernameInsert = new JTextField();
        JLabel errorUsernameInsertLabel = new JLabel("");
        textUsernameInsert.setPreferredSize(new Dimension(200, textUsername.getPreferredSize().height));
        boxUsernameInsert.add(usernameInsert);
        boxUsernameInsert.add(textUsernameInsert);
        boxUsernameInsert.add(errorUsernameInsertLabel);
        
        Box boxPasswordInsert = Box.createVerticalBox();
        JLabel passwordInsert = new JLabel("Password");
        JPasswordField textPasswordInsert = new JPasswordField();
        JLabel passwordIconInsert = new JLabel(resizedIcon);
        JLabel errorPasswordInsertLabel = new JLabel("");
        JLabel confirmPasswordInsert = new JLabel("Confirm Password");
        JPasswordField confirmTextPasswordInsert = new JPasswordField();
        JLabel confirmpasswordIconInsert = new JLabel(resizedIcon);
        JLabel confirmErrorPasswordInsertLabel = new JLabel("");
        textPasswordInsert.setPreferredSize(new Dimension(200, textPassword.getPreferredSize().height));
        boxPasswordInsert.add(passwordInsert);
        boxPasswordInsert.add(textPasswordInsert);
        boxPasswordInsert.add(passwordIconInsert);
        boxPasswordInsert.add(errorPasswordInsertLabel);
        boxPasswordInsert.add(Box.createVerticalStrut(20));
        boxPasswordInsert.add(confirmPasswordInsert);
        boxPasswordInsert.add(confirmTextPasswordInsert);
        boxPasswordInsert.add(confirmpasswordIconInsert);
        boxPasswordInsert.add(confirmErrorPasswordInsertLabel);
        

        JButton inscrisMoi = new JButton("Je m'inscris");
        JButton inscription = new JButton("Inscription");


        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel uploadPanel = new JPanel(new FlowLayout());
        JButton uploadButton  = new JButton("Upload un fichier");
        Box boxUpload = Box.createVerticalBox();
        JLabel filenameLabel = new JLabel("");
        JButton crypteButton  = new JButton("Crypter le fichier");
        JButton decrypteButton  = new JButton("Decrypter le fichier");
        
        
      
        
        buttonPanel.add(inscrisMoi);
     // Rafraîchissez le JPanel de boutons pour refléter les changements
        buttonPanel.revalidate();
       
        connexionBox.add(boxUsername);
        //je met des margin bottom de 20 px au dessous de ma box
        connexionBox.add(Box.createVerticalStrut(20));
        connexionBox.add(boxPassword);
        connexionBox.add(Box.createVerticalStrut(20));
        connexionBox.add(connexion);
        
        inscriptionBox.add(boxUsernameInsert);
        inscriptionBox.add(Box.createVerticalStrut(20));
        inscriptionBox.add(boxPasswordInsert);
        inscriptionBox.add(Box.createVerticalStrut(20));
        inscriptionBox.add(inscription);
       
        panel.add(connexionBox);
        
        frame.add(panel);
        frame.add(buttonPanel); 
        frame.add(uploadPanel);
        frame.setSize(750, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        
       
        connexion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Select user = new Select(textUsername.getText());
                String enteredPassword  = textPassword.getText();
                
                if(user.getSelection()[0][0] != null) {
                	if(isPasswordValid(enteredPassword,(String) user.getSelection()[0][1],(String) user.getSelection()[0][2])) {                		
                		// Rafraîchissez le JPanel de boutons pour refléter les changements
                		buttonPanel.revalidate();
                		boxUpload.add(uploadButton);
                		boxUpload.add(filenameLabel);
                		frame.add(boxUpload);
                		frame.remove(panel);
                		frame.remove(buttonPanel);
                		//j'actualise mon appli
                		frame.repaint();
                	} else {
                		errorPasswordLabel.setText("Mot de passe incorrect");
                		errorPasswordLabel.setForeground(Color.red);
                	}
                	
                } else {
                	errorUsernameLabel.setText("L'identifiant n'existe pas");
                	errorUsernameLabel.setForeground(Color.red);
                }
            }
        });
        
        
        inscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textUsernameInsert.getText();
                String password = textPasswordInsert.getText();
                String confirmPassword = confirmTextPasswordInsert.getText();
                
                if(username != null && isValidEmail(username)) {                	
                	if(password.equals(confirmPassword)) {
                		Insert insert = new Insert(username, password);
                		if (insert.getInsert()) {
                			buttonPanel.revalidate();
                			boxUpload.add(uploadButton);
                			boxUpload.add(filenameLabel);
                			frame.add(boxUpload);
                			frame.remove(panel);
                			frame.remove(buttonPanel);
                			frame.repaint();
                		} else {
                			errorUsernameInsertLabel.setText("Il y a eu une erreur pour la creation de votre compte");
                			errorPasswordInsertLabel.setText("");
                		}
                	} else {
                		errorUsernameInsertLabel.setText("");
                		errorPasswordInsertLabel.setText("Le mot de passe n'est pas pareil");
                	}
                } else {
                	errorUsernameInsertLabel.setText("Vous devez mettre un mail");
        			errorPasswordInsertLabel.setText("");
                }
                
            }
        });
        
        inscrisMoi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonPanel.revalidate();
				panel.add(inscriptionBox);
            	panel.remove(connexionBox);
            	buttonPanel.add(connecteMoi);
            	buttonPanel.remove(inscrisMoi);
            	frame.repaint();
			}
		});
        
        
        connecteMoi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buttonPanel.revalidate();
				panel.add(connexionBox);
				panel.remove(inscriptionBox);
            	buttonPanel.add(inscrisMoi);
            	buttonPanel.remove(connecteMoi);
            	frame.repaint();
			}
		});
        
        
        passwordIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(afficheeConnexion) {                    	
                	afficheeConnexion = false;
                	textPassword.setEchoChar('*');
                } else {
                	afficheeConnexion = true;
                	textPassword.setEchoChar((char) 0);
                }
            }
        });
        
        passwordIconInsert.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(afficheeConnexion) {                    	
                	afficheeConnexion = false;
                	textPasswordInsert.setEchoChar('*');
                } else {
                	afficheeConnexion = true;
                	textPasswordInsert.setEchoChar((char) 0);
                }
            }
        });
        
        confirmpasswordIconInsert.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                
                if(afficheeConnexion) {                    	
                	afficheeConnexion = false;
                	confirmTextPasswordInsert.setEchoChar('*');
                } else {
                	afficheeConnexion = true;
                	confirmTextPasswordInsert.setEchoChar((char) 0);
                }
            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	//j'affiche un menu pour selectionner un fichier
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                
                //je regarde si le fichier selectionner est bien un fichier
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                	//je prends le chemin où ce trouve ce fichier
                    File selectedFile = fileChooser.getSelectedFile();
                    filename = selectedFile.getName();
                    
                    //je met un texte en bas de mon bouton pour dire le fichier sélectionné
                    filenameLabel.setText("Fichier sélectionné : " + filename);
                    filename = selectedFile.getAbsolutePath();
                    boxUpload.add(crypteButton);
                    boxUpload.add(decrypteButton);
                    frame.repaint();
                }
            }
        });
        
        crypteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Algorithme algo = new Algorithme("crypt", filename, "coucouToi");
                algo.chooseAlgo();
            }
        });
        
        decrypteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Algorithme algo = new Algorithme("decrypt", filename, "coucouToi");
                algo.chooseAlgo();
            }
        });

        
    }
    
    
   
    public static boolean isValidEmail(String email) {
        // Expression régulière pour valider une adresse e-mail
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";

        // Compilez la regex en un modèle
        Pattern pattern = Pattern.compile(regex);

        // Créez un objet Matcher
        Matcher matcher = pattern.matcher(email);

        // Vérifiez si la chaîne correspond à la regex
        return matcher.matches();
    }
    
   
    public static boolean isPasswordValid(String enteredPassword, String storedHashedPassword, String storedSalt) {
        // Utilisez le sel (salt) stocké pour hacher le mot de passe saisi
        String hashedEnteredPassword = BCrypt.hashpw(enteredPassword, storedSalt);

        // Comparez le résultat haché avec le mot de passe hashé stocké
        return hashedEnteredPassword.equals(storedHashedPassword);
    }
    
    // Méthode pour redimensionner une icône
    public static ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}

