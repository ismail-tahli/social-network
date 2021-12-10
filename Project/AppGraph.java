package Project;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;


public class AppGraph {

    // ATTRIBUTS
    
    private Graphe model;
    private JFrame mainFrame;
    
    private JList<Utilisateur> userList;
    private DefaultListModel<Utilisateur> userListModel;
    private JList<Page> pageList;
    private DefaultListModel<Page> pageListModel;
    
    private JButton addUser;
    private JButton addPage;
    private JButton removeUser;
    private JButton removePage;
    private JButton userInfo;
    private JButton pageInfo;
    private JButton followButton;
    private JButton unfollowButton;
    private JButton setAdmin;
    private JButton removeAdmin;
    private JButton verticesSortedByName;
    private JButton verticesSortedByDegree;
    private JButton verticesSortedByPageRank;
    private JButton edgesButton;
    private JButton listAdjButton;

    private JTextField findField;
    private JTextField sourceField;
    private JTextField avgAgeField;
    
    private JMenuItem load;
    private JMenuItem save;
    private JMenuItem about;
    private JMenuItem exit;
    
    private JFileChooser fileChooser;
    
    // CONSTRUCTEUR
    
    public AppGraph() {
        createModel();
        createView();
        placeComponents();
        createController();
    }
    
    // COMMANDES
    
    public void display() {
        refresh();
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
    
    // OUTILS
    
    // Instanciation du modèle "Un graphe Vide"
    private void createModel() {
        model = new Graphe();
    }
    
    // Instanciation des composants majeurs
    private void createView() {
    	
        int width = 800;
        int height = 600;
        
        mainFrame = new JFrame("AppGraph");
        mainFrame.setPreferredSize(new Dimension(width, height));
        
        userListModel = new DefaultListModel<Utilisateur>();
        pageListModel = new DefaultListModel<Page>();
        
        userList = new JList<Utilisateur>(userListModel);
        userList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        pageList = new JList<Page>(pageListModel);
        pageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        addUser = new JButton("Ajouter Utilisateur");
        //addUser.setBorder(new RoundedBorder(20));
        //addUser.setForeground(Color.BLUE);
        addPage = new JButton("Ajouter Page");
        removeUser = new JButton("Supprimer Utilisateur");
        removePage = new JButton("Supprimer Page");
        userInfo = new JButton("Infos Utilisateur"); //Nom complet/age 
        pageInfo = new JButton("Infos Page");//Nombre d'admins et ses noms
        followButton = new JButton("Suivre");
        unfollowButton = new JButton("Arrêter de suivre");
        setAdmin = new JButton("Ajouter Admin");
        removeAdmin = new JButton("Supprimer Admin");
        verticesSortedByName = new JButton("Ensemble des sommets triés par nom");
        verticesSortedByDegree = new JButton("Ensemble des sommets triés par degré sortant");
        verticesSortedByPageRank = new JButton("Ensemble des sommets triés par PageRank");
        edgesButton = new JButton("Ensemble des arcs");
        listAdjButton = new JButton("La liste d'adjacence");
        findField = new JTextField(10);
        sourceField = new JTextField(10);
        avgAgeField = new JTextField(3);
        avgAgeField.setEditable(false);

        fileChooser = new JFileChooser();
        
        load = new JMenuItem("Charger");
        save = new JMenuItem("Sauvegarder");
        about = new JMenuItem("About AppGraph");
        exit = new JMenuItem("Quitter");
        
    }
    
    
    
    // Placement des composants
    private void placeComponents() {
        // Les deux JScrollPane pour un défilement vertical de JList d'utilisateurs et des pages
        JPanel p = new JPanel(new GridLayout(2,2)); {
            JPanel r = new JPanel(new BorderLayout()); {
                r.add(new JLabel("Utilisateurs"), BorderLayout.NORTH);
                r.add(new JScrollPane(userList), BorderLayout.CENTER);
            p.add(r);
            r = new JPanel(new BorderLayout()); {
                r.add(new JLabel("Pages"), BorderLayout.NORTH);
                r.add(new JScrollPane(pageList), BorderLayout.CENTER);
            }
            p.add(r);
            }
            JPanel s = new JPanel(new FlowLayout()); {
                JPanel t = new JPanel(new GridLayout(0,1)); {
                    t.add(addUser);
                    t.add(removeUser);
                    t.add(userInfo);
                       t.add(followButton);
                       t.add(unfollowButton);
                   }
                   s.add(t);
            }
            p.add(s);
            s = new JPanel(new FlowLayout()); {
                  JPanel t = new JPanel(new GridLayout(0,1)); {
                       t.add(addPage);
                       t.add(removePage);
                       t.add(pageInfo);
                       t.add(setAdmin);
                       t.add(removeAdmin);
                   }
                  s.add(t);
            }
            p.add(s);
        }
        p.setBorder(BorderFactory.createEtchedBorder());
        mainFrame.add(p, BorderLayout.CENTER);
        //JPanel pour les actions d'utilisateurs et des pages.
        p = new JPanel(new GridBagLayout()); {
            JPanel q = new JPanel(new GridLayout(0,1)); {
                    q.add(verticesSortedByName);
                    q.add(verticesSortedByDegree);
                    q.add(verticesSortedByPageRank);
                    q.add(edgesButton);
                    q.add(listAdjButton);
                    JPanel z = new JPanel(); {
                        z.add(new JLabel("Trouver : "));
                        z.add(findField);
                    }
                    q.add(z);
                    z = new JPanel(); {
                        z.add(new JLabel("Distance de : "));
                        z.add(sourceField);
                    }
                    q.add(z);
                    z = new JPanel(); {
                        z.add(new JLabel("Âge moyen : "));
                        z.add(avgAgeField);
                    }
                    q.add(z);
            }
            p.add(q);
        }
                    //On ajoutera un Button pour Tracer le Graphe
        mainFrame.add(p, BorderLayout.EAST);
        JMenuBar menu = new JMenuBar();{
        	JMenu m = new JMenu("Options");{
        		m.add(load);
        		m.add(save);
        		m.addSeparator();
        		m.add(exit);
        	}
        	menu.add(m);
        	m = new JMenu("Aide");{
        		m.add(about);
        	}
        	menu.add(m);
        }
        mainFrame.setJMenuBar(menu);     
    }
    
    // Factorisation pour montrer des sommets
    private void showUser(Utilisateur u) {
        String Info = "Nom: " + u.getFullName() 
                    + "\nAge: " + u.getAge() 
                    + "\nSuit: " + u.getFollowList().toString() 
                    + "\nPage Rank: " + model.pageRank().get(u)
                    + "\nDegré sortant: " + u.getFollowList().size();
        
        showInfoDialog(Info, "Infos de " + u.getFullName());
    }
    
    private void showPage(Page p) {
    	String strInfo = "Nom: " + p.getName()
        + "\nAdmins: " + p.getAdmins().toString()
        + "\nPage Rank: " + model.pageRank().get(p);

    	showInfoDialog(strInfo, "Infos de la page " + p.getName());
    }

    // Création du controlleur
    private void createController() {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ((Observable)model).addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                refresh();
            }
        });
        
        addUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               /* String firstname = JOptionPane.showInputDialog(null, "Entrez le prénom:");
                String name = JOptionPane.showInputDialog(null, "Entrez le nom:");
                String age = JOptionPane.showInputDialog(null, "Entrez :");
                */
                JTextField firstname = new JTextField(10);
                JTextField name = new JTextField(10);
                JTextField age = new JTextField(2);
                JPanel input = new JPanel(new GridLayout(0,1)); {
                	input.add(new JLabel("Entrez le prénom :"));
                	input.add(firstname);
                	input.add(new JLabel("Entrez le nom :"));
                	input.add(name);
                	input.add(new JLabel("Entrez l'âge :"));
                	input.add(age);
                }
                int result = JOptionPane.showConfirmDialog(null, input, 
                		"Ajouter Utilisateur", JOptionPane.OK_CANCEL_OPTION);
                if(result == JOptionPane.OK_OPTION) {
                	String prenom = firstname.getText();
                	String nom = name.getText();
                	if (prenom.length() > 0 && nom.length() > 0 
                			&& age.getText().length() > 0) {
                		if(isNumeric(age.getText())) {
	                    	int ag = Integer.valueOf(age.getText());
	                    	Utilisateur u = new Utilisateur(nom, prenom, ag);
	                    	if(u != null) {
	                    		if(model.isTaken(u)) {
	                    			JOptionPane.showMessageDialog(null, "Erreur : Le nom existe déjà",
	                    											"Le nom du sommet que vous voulez ajouter existe dèja.\n"
	                    											+ "Choisissez un autre.", JOptionPane.ERROR_MESSAGE);
	                    		}else {
	                    		model.addSommet(u);
	                    		}
	                    	}
                		}else {
                			JOptionPane.showMessageDialog(null, "L'âge entrée n'est pas valide.", 
                					"Erreur : Age invalide", JOptionPane.ERROR_MESSAGE);
                		}
                    }
                }
            }
        });
        
        addPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(null, "Entrez le nom de la page");
                if (name != null && name.length() > 0) { //name == null means que j'ai cliqué sur anuller
                	Page u = new Page(name);
                	//Factorisation possible
            		if(model.isTaken(u)) {
            			JOptionPane.showMessageDialog(null, "Erreur : Le nom existe déjà",
            											"Le nom du sommet que vous voulez ajouter existe dèja.\n"
            											+ "Choisissez un autre.", JOptionPane.ERROR_MESSAGE);
            		}else {
            		model.addSommet(u);
            		}
                } 
                //Sinon je ne fais rien
            }
        });
        //On peut factoriser les deux
        removeUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sommet s = userList.getSelectedValue();
                if (s != null) {
                    model.removeSommet(s);
                }
            }
        });
        
        removePage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sommet s = pageList.getSelectedValue();
                if (s != null) {
                    model.removeSommet(s);
                }
            }
        });
        
        userInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sommet s = userList.getSelectedValue();
                if (s != null) {
                	showUser((Utilisateur) s);
                }
            }
        });
        
        pageInfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Sommet s = pageList.getSelectedValue();
                if (s != null) {
                    showPage((Page) s);
                }
            }
        });

        findField.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Sommet s = model.getVerticeByName(findField.getText());
        		if (s != null) {
        			if (s instanceof Utilisateur) {
        				showUser((Utilisateur) s);
        			} else {
        				showPage((Page) s);
        			}
        		}
        	}
        });
        sourceField.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		Sommet s = model.getVerticeByName(sourceField.getText());
    			StringBuilder str = new StringBuilder("");
        		if(s != null) {
        			model.computeSmallestDistanceFrom(s);
        			for(Sommet sommet : model.getSommets()) {
        				str.append(sommet);
        				str.append(" ==> ");
        				str.append(sommet.getDistance(s));
        				str.append("\n");
        			}
            		showInfoDialog(str.toString(), "Les distances de "+s.getName()+" à tous les sommets.");
        		}
        	}
        });
        
        followButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Utilisateur u = (Utilisateur) userList.getSelectedValue();
                
                if (u != null) {
                    Set<Sommet> verticesSet = new HashSet<Sommet>();
                    for (Sommet s : model.getSommets()) {
                        if (!u.getFollowList().contains(s) && s != u) {
                            verticesSet.add(s);
                        }
                    }
                    
                    Object[] verticesList = verticesSet.toArray();
                    
                    if (verticesList.length > 0) {
                        Sommet s = (Sommet) JOptionPane.showInputDialog(null,
                                            "Choisissez un utilisateur ou une page à suivre.",
                                            "Suivre un utilisateur ou une page",
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            verticesList,
                                            verticesList[0]);
                        if (s != null) {
                            model.addArc(u, s);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Cet utilisateur suit déjà tout le monde",
                                "Erreur!",
                                JOptionPane.CANCEL_OPTION);
                    }
                }
            }
        });
        
        unfollowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Utilisateur u = (Utilisateur) userList.getSelectedValue();
                
                if (u != null) {
                    Object[] verticesList = u.getFollowList().toArray();
                    
                    if (verticesList.length > 0) {
                        Sommet s = (Sommet) JOptionPane.showInputDialog(null,
                                            "Choisissez un utilisateur ou une page à ne plus suivre.",
                                            "Arrêter de suivre un utilisateur ou une page",
                                            JOptionPane.QUESTION_MESSAGE,
                                            null,
                                            verticesList,
                                            verticesList[0]);
                        if (s != null) {
                            model.removeArc(u, s);//Rédifinir la méthode removeArc
                        }
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Cet utilisateur ne suit personne.",
                                "Impossible !",
                                JOptionPane.CANCEL_OPTION);
                    }
                }
            }
        });
        
        setAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Page s = (Page) pageList.getSelectedValue();
                if (s != null) {
                    Set<Utilisateur> userSet = new HashSet<Utilisateur>();
                    for (Sommet v : model.getSommets()) {
                        if (v instanceof Utilisateur && !s.getAdmins().contains(v)) {
                                userSet.add((Utilisateur) v);
                        }
                    }
                    
                    Object[] userList = userSet.toArray();
                    
                    if (userList.length > 0) {
                        Utilisateur u = (Utilisateur) JOptionPane.showInputDialog(null,
                                                "Choisissez un utilisateur à ajouter comme administrateur.",
                                                "Ajout d'administrateur",
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                userList,
                                                userList[0]);
                        if (u != null) {
                            s.addAdmin(u);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Vous ne pouvez plus ajouter d'admin à cette page",
                                "Impossible !",
                                JOptionPane.CLOSED_OPTION);
                    }
                }
            }
        });
        
        removeAdmin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Page s = (Page) pageList.getSelectedValue();
                if (s != null) {
                    Object[] listAdmins = s.getAdmins().toArray();
                    if (listAdmins.length > 0) {
                        Utilisateur u = (Utilisateur) JOptionPane.showInputDialog(null,
                                                "Choisissez un admin à enlever.",
                                                "Suppression d'administrateur",
                                                JOptionPane.QUESTION_MESSAGE,
                                                null,
                                                listAdmins,
                                                listAdmins[0]);
                        if (u != null) {
                            s.removeAdmin(u);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, 
                                "Vous ne pouvez plus supprimer d'admin de cette page",
                                "Impossible !",
                                JOptionPane.CANCEL_OPTION);
                    }
                }
            }
        });
        
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showSaveDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        model.saveGraph(fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, 
                                "Erreur lors de la sauvegarde du graphe.",
                                "Erreur!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
       
        
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(mainFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        model.loadGraph(fileChooser.getSelectedFile());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null,
                                "Erreur lors de l'ouverture du fichier.",
                                "Erreur!",
                                JOptionPane.ERROR_MESSAGE);
                    } catch (BadSyntaxException ex) {
                        JOptionPane.showMessageDialog(null, 
                                "Le fichier est corrompu",
                                "Erreur!",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        about.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				showInfoDialog("Projet de Graphe\nUniversité de ROUEN\nProgrammé par :\n Ismail TAHLI \n Thomas Romeyer \n Youssef Myje", "About AppGraph");
			}	
        });
        
        exit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}	
        });

        verticesSortedByName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                showInfoDialog(model.sortedByName().toString(), "Sommets triés par nom");
            }
        });
        
        verticesSortedByDegree.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {                
                showInfoDialog(model.sortedByDegre().toString(), "Sommets triés par degré");
            }
        });
        
        verticesSortedByPageRank.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInfoDialog(model.sortedByRank().toString(), "Sommets triés par PageRank");
            }
        });
        
        listAdjButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	//Calcule la liste d'adjacence
        		model.adjListInit(model.getSommets());
            	StringBuilder listAdj = new StringBuilder("");
            	for(Sommet s : model.getSommets()) {
            		listAdj.append(s);
            		listAdj.append(":");
            		listAdj.append(model.getListAdj().get(s));
            		listAdj.append("\n");
            	}
                showInfoDialog(listAdj.toString(), "La liste d'adjacence");
            }
        });
        
        edgesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showInfoDialog(model.getArcsSet().toString(), "Ensemble des arcs du graphe");
            }
        });
    }
    
    //OUTILS


    private void refresh() {        
        userListModel.removeAllElements();
        pageListModel.removeAllElements();
        
    	verticesSortedByName.setEnabled(false);
    	verticesSortedByDegree.setEnabled(false);
    	verticesSortedByPageRank.setEnabled(false);
    	edgesButton.setEnabled(false);
    	listAdjButton.setEnabled(false);
    	
        if(model.getSommetNb() > 0) {
        	verticesSortedByName.setEnabled(true);
        	verticesSortedByDegree.setEnabled(true);
        	verticesSortedByPageRank.setEnabled(true);
        	listAdjButton.setEnabled(true);
        	if(model.arcsNb() > 0) {
        		edgesButton.setEnabled(true);
        	}
        }
        
        avgAgeField.setText("");
        if (model.getUtilisateurNb() > 0) {
        	avgAgeField.setText(String.valueOf(model.avgAge()));
        }

        for (Sommet s : model.getSommets()) {
            if (s instanceof Utilisateur) {
                userListModel.addElement((Utilisateur) s);
            } else {
                pageListModel.addElement((Page) s);
            }
        }
    }
    
    /*
     * Teste si une chaine de caractère est numérique
     */
    private boolean isNumeric(String str) { 
		  try {  
			  Integer.parseInt(str);  
			  return true;
		  } catch(NumberFormatException e){  
			  return false;  
		  }  
    }
    
    private void showInfoDialog(String str, String dialogTitle) {
        JTextArea txtArea = new JTextArea(str);
        txtArea.setEditable(false);
        txtArea.setWrapStyleWord(true);
        
        JScrollPane sp = new JScrollPane(txtArea);
        sp.setPreferredSize(new Dimension(300, 100));
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        JOptionPane.showMessageDialog(null,
                                        sp,
                                        dialogTitle,
                                        JOptionPane.INFORMATION_MESSAGE);   
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AppGraph().display();
            }
        });
    }
}
