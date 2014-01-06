package widgets;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Paint;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import figures.Drawing;
import figures.creationListeners.AbstractCreationListener;
import figures.enums.FigureType;
import figures.enums.LineType;
import figures.enums.PaintToType;

/**
 * Fenêtre principale de l'application
 * @author davidroussel
 */
public class EditorFrame extends JFrame
{
	/**
	 * Le modèle de dessin sous-jacent;
	 */
	protected Drawing drawingModel;

	/**
	 * La zone de dessin dans laquelle seront dessinées les figures.
	 * On a besoin d'une référence à la zone de dessin (contrairement aux
	 * autres widgets) car il faut lui affecter un xxxCreationListener en
	 * fonction de la figure choisie dans la liste des figures possibles.
	 */
	protected DrawingPanel drawingPanel;

	/**
	 * Le creationListener à mettre en place dans le drawingPanel en fonction
	 * du type de figure choisie;
	 */
	protected AbstractCreationListener creationListener;

	/**
	 * Le label dans la barre d'état en bas dans lequel on affiche les
	 * conseils utilisateur pour créer une figure
	 */
	protected JLabel tipLabel;

	/**
	 * L'index de l'élément sélectionné par défaut le type de figure
	 */
	private final static int defaultFigureTypeIndex = 0;

	/**
	 * Les noms des couleurs de remplissage à utiliser pour remplir
	 * la [labeled]combobox des couleurs de remplissage
	 */
	protected final static String[] fillColorNames = {
		"Black",
		"Red",
		"Orange",
		"Yellow",
		"Green",
		"Cyan",
		"Blue",
		"Magenta",
		"Others",
		"None"
	};

	/**
	 * Les couleurs de remplissage à utiliser en fonction de l'élément
	 * sélectionné dans la [labeled]combobox des couleurs de remplissage
	 */
	protected final static Paint[] fillPaints = {
		Color.black,
		Color.red,
		Color.orange,
		Color.yellow,
		Color.green,
		Color.cyan,
		Color.blue,
		Color.magenta,
		null, // Color selected by a JColorChooser
		null // No Color
	};

	/**
	 * L'index de l'élément sélectionné par défaut dans les couleurs de
	 * remplissage
	 */
	private final static int defaultFillColorIndex = 0; // black

	/**
	 * L'index de la couleur de remplissage à choisir avec un
	 * {@link JColorChooser}
	 */
	private final static int specialFillColorIndex = 8;

	/**
	 * Les noms des couleurs de trait à utiliser pour remplir
	 * la [labeled]combobox des couleurs de trait
	 */
	protected final static String[] edgeColorNames = {
		"Magenta",
		"Red",
		"Orange",
		"Yellow",
		"Green",
		"Cyan",
		"Blue",
		"Black",
		"Others"
	};

	/**
	 * Les couleurs de trait à utiliser en fonction de l'élément
	 * sélectionné dans la [labeled]combobox des couleurs de trait
	 */
	protected final static Paint[] edgePaints = {
		Color.magenta,
		Color.red,
		Color.orange,
		Color.yellow,
		Color.green,
		Color.cyan,
		Color.blue,
		Color.black,
		null // Color selected by a JColorChooser
	};

	/**
	 * L'index de l'élément sélectionné par défaut dans les couleurs de
	 * trait
	 */
	private final static int defaultEdgeColorIndex = 6; // blue;

	/**
	 * L'index de la couleur de remplissage à choisir avec un
	 * {@link JColorChooser}
	 */
	private final static int specialEdgeColorIndex = 8;

	/**
	 * L'index de l'élément sélectionné par défaut dans les types de
	 * trait
	 */
	private final static int defaultEdgeTypeIndex = 1; // solid

	/**
	 * La largeur de trait par défaut
	 */
	private final static int defaultEdgeWidth = 4;

	/**
	 * Largeur de trait minimum
	 */
	private final static int minEdgeWidth = 1;

	/**
	 * Largeur de trait maximum
	 */
	private final static int maxEdgeWidth = 30;

	/**
	 * l'incrément entre deux largeurs de trait
	 */
	private final static int stepEdgeWidth = 1;

	/**
	 * Action déclenchée lorsque l'on clique sur le bouton quit ou sur l'item
	 * de menu quit.
	 * @note les actions son intanciées ici plutôt que dans le constructeur
	 * pour laisser le champ libre à WindowBuilder qui écrira le constructeur
	 */
	private final Action quitAction = new QuitAction();

	/**
	 * Action déclenchée lorsque l'on clique sur le bouton undo ou sur l'item
	 * de menu undo
	 */
	private final Action undoAction = new UndoAction();

	/**
	 * Action déclenchée lorsque l'on clique sur le bouton clear ou sur l'item
	 * de menu clear
	 */
	private final Action clearAction = new ClearAction();

	/**
	 * Action déclenchée lorsque l'on clique sur le bouton about ou sur l'item
	 * de menu about
	 */
	private final Action aboutAction = new AboutAction();

	/**
	 * Constructeur de la fenètre de l'éditeur.
	 * Construit les widgets et assigne les actions et autres listeners
	 * aux widgets
	 * @throws HeadlessException
	 */
	public EditorFrame() throws HeadlessException
	{
		/*
		 * TODO Construire l'interface graphique en utilisant WindowBuilder:
		 * Menu Contextuel -> Open With -> WindowBuilder Editor puis
		 * aller dans l'onglet Design
		 */

		/*
		 * TODO n'oubliez pas d'instancier vos attributs
		 */

		/*
		 * TODO mettez en place un BorderLayout qui permettra d'arranger
		 * les wigdets
		 */

		// --------------------------------------------------------------------
		// Toolbar en haut
		// --------------------------------------------------------------------

		/*
		 * TODO Créer la barre d'outils en haut (qui n'en est pas une)
		 * et ajoutez 3 boutons
		 * 	- Undo auquel vous attacherez l'action undoAction
		 * 	- Clear auquel vous attacherez l'action clearAction
		 * 	- un springer pour coller le bouton suivant à droite
		 * 	- Quit auquel vous attacherez l'action quitAction
		 */

		// --------------------------------------------------------------------
		// Barre d'état en bas
		// --------------------------------------------------------------------

		/*
		 * TODO Créez le panel en bas et ajoutez
		 * 	- le tipLabel dans lequel afficher les conseils utilisateurs lors
		 * 		de la création d'une nouvelle figure
		 * 	- un springer
		 * 	- le coordLabel dans lequel afficher les coordonnées du pointeur
		 */

		// --------------------------------------------------------------------
		// Panneau de contrôle à gauche
		// --------------------------------------------------------------------

		/*
		 * TODO Créez le panel à gauche et ajoutez y
		 * 	- le JLabeledComboBox du choix du type de figure à créer
		 * 	- le JLabeledComboBox du choix de la couleur de remplissage
		 * 	- le JLabeledComboBox du choix de la couleur de trait
		 * 	- le JLabeledComboBox du choix du type de lignes en utilisant les
		 * 		LineType.stringValues() comme libellés
		 * 	- un sous panel contenant:
		 * 		- le label "Width"
		 * 		- un spinner pour régler l'épaisseur de trait en utilisant
		 * 			defaultEdgeWidth, minEdgeWidth, maxEdgeWidth et
		 * 			stepEdgeWidth comme valeurs du SpinnerNumberModel
		 * 	- un InfoPanel dans lequel on pourra afficher les informations
		 * 	relatives à la figure sous le curseur (réalisé par un listener de
		 * 	DrawingPanel).
		 */

		// --------------------------------------------------------------------
		// Zone de dessin
		// --------------------------------------------------------------------

		/*
		 * TODO Créez la zone de dessin au centre
		 * 	- un JScrollPane dans lequel on placera
		 * 		- le DrawinPanel drawingPanel en lui fournissant le coordLabel
		 * 		et l'infoPanel afin qu'il puisse y afficher des informations
		 */

		// --------------------------------------------------------------------
		// Barre de menus
		// --------------------------------------------------------------------

		/*
		 * TODO Créez une barre de menus (JMenuBar) dans laquelle vous ajouterez
		 * 	- Un JMenu Drawing contenant
		 * 		- un JMenuItem "Cancel" auquel vous attacherez l'action undoAction
		 * 		- un JMenuItem "Clear" auquel vous attacherez l'action clearAction
		 * 	- Un Jmenu Help contenant
		 * 		- un JMenuItem "About ..." auquel vous attacherez l'action
		 * 		aboutAction
		 */

		// --------------------------------------------------------------------
		// Ajout des contrôleurs aux widgets
		// TODO pour connaître les Listeners applicable à un widget
		// dans WindowBuilder, sélectionnez un widger de l'UI puis Menu
		// Conextuel -> Add event handler
		// --------------------------------------------------------------------

		/*
		 * TODO ajoutez les Listeners adéquats aux différents widgets créés ci-
		 * dessus
		 * 	- figureTypeComboBox : ajoutez un ShapeItemListener
		 * 	- fillPaintComboBox : ajoutez un ColorItemListener(... PaintToType.FILL)
		 * 	- edgePaintComboBox : ajoutez un ColorItemListener(... PaintToType.EDGE)
		 * 	- edgeTypeCombobox : ajoutez un EdgeTypeListener
		 * 	- edgeWidthSpinner : ajoutez un EdgeWidthListener
		 */
	}

	/**
	 * Action pour quitter l'application
	 * @author davidroussel
	 */
	private class QuitAction extends AbstractAction
	{
		/**
		 * Constructeur de l'action pour quitter l'application.
		 * Met en place le raccourci clavier, l'icône et la description
		 * de l'action
		 */
		public QuitAction()
		{
			putValue(NAME, "Quit");
			/*
			 * Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()
			 * 	= InputEvent.CTRL_MASK on win/linux
			 *  = InputEvent.META_MASK on mac os
			 */
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Q,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			putValue(LARGE_ICON_KEY,
					new ImageIcon(EditorFrame.class
							.getResource("/images/Quit.png")));
			putValue(SMALL_ICON,
					new ImageIcon(EditorFrame.class
							.getResource("/images/Quit_small.png")));
			putValue(SHORT_DESCRIPTION, "Quits the application");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param e l'évènement déclenchant l'action. Peut provenir d'un bouton
		 *            ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			/*
			 * TODO Action à effectuer lorsque l'action "undo" est cliquée :
			 * sortir avec un System.exit() (pas très propre, mais fonctionne)
			 */
		}
	}

	/**
	 * Action réalisée pour effacer la dernière figure du dessin.
	 */
	private class UndoAction extends AbstractAction
	{
		/**
		 * Constructeur de l'action effacer la dernière figure du dessin
		 * Met en place le raccourci clavier, l'icône et la description
		 * de l'action
		 */
		public UndoAction()
		{
			putValue(NAME, "Undo");
			/*
			 * Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()
			 * 	= InputEvent.CTRL_MASK on win/linux
			 *  = InputEvent.META_MASK on mac os
			 */
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_Z,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			putValue(LARGE_ICON_KEY,
					new ImageIcon(EditorFrame.class
							.getResource("/images/Undo.png")));
			putValue(SMALL_ICON,
					new ImageIcon(EditorFrame.class
							.getResource("/images/Undo_small.png")));
			putValue(SHORT_DESCRIPTION, "Undo last drawing");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param e l'évènement déclenchant l'action. Peut provenir d'un bouton
		 *            ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			/*
			 * TODO Action à effectuer lorsque l'action "undo" est cliquée :
			 * retirer la dernière figure dessinée dans drawingModel
			 */
		}
	}

	/**
	 * Action réalisée pour effacer toutes les figures du dessin
	 */
	private class ClearAction extends AbstractAction
	{
		/**
		 * Constructeur de l'action pour effacer toutes les figures du dessin
		 * Met en place le raccourci clavier, l'icône et la description
		 * de l'action
		 */
		public ClearAction()
		{
			putValue(NAME, "Clear");
			/*
			 * Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()
			 * 	= InputEvent.CTRL_MASK on win/linux
			 *  = InputEvent.META_MASK on mac os
			 */
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_D,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			putValue(LARGE_ICON_KEY, new ImageIcon(
					EditorFrame.class.getResource("/images/Delete.png")));
			putValue(SMALL_ICON, new ImageIcon(
					EditorFrame.class.getResource("/images/Delete_small.png")));
			putValue(SHORT_DESCRIPTION, "Erase all drawings");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param e l'évènement déclenchant l'action. Peut provenir d'un bouton
		 *            ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			/*
			 * TODO Action à effectuer lorsque l'action "clear" est cliquée :
			 * Effacer toutes les figures du drawingModel
			 */
		}
	}

	/**
	 * Action réalisée pour afficher la boite de dialogue "A propos ..."
	 */
	private class AboutAction extends AbstractAction
	{
		/**
		 * Constructeur de l'action pour afficher la boite de dialogue
		 * "A propos ..." Met en place le raccourci clavier, l'icône et la
		 * description de l'action
		 */
		public AboutAction() {
			putValue(NAME, "About ...");
			putValue(LARGE_ICON_KEY, new ImageIcon(
					EditorFrame.class.getResource("/images/About.png")));
			putValue(SMALL_ICON, new ImageIcon(
					EditorFrame.class.getResource("/images/About_small.png")));
			putValue(SHORT_DESCRIPTION, "App information");
		}

		/**
		 * Opérations réalisées par l'action
		 * @param e l'évènement déclenchant l'action. Peut provenir d'un bouton
		 *            ou d'un item de menu
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{
			/*
			 * TODO Action à effectuer lorsque l'action "about" est cliquée :
			 * Ouvrir un MessageDialog (JOptionPane.showMessageDialog(...)) de
			 * type JOptionPane.INFORMATION_MESSAGE
			 */
		}
	}

	/**
	 * Contrôleur d'évènement permettant de modifier le type de figures à
	 * dessiner.
	 * @note dépends de #drawingModel et #infoLabel qui doivent être non
	 * null avant instanciation
	 */
	private class ShapeItemListener implements ItemListener
	{
		/**
		 * Constructeur valué du contrôleur.
		 * Initialise le type de dessin dans {@link EditorFrame#drawingModel}
		 * et crée le {@link AbstractCreationListener} correspondant.
		 * @param initialIndex l'index du type de forme sélectionné afin de
		 * mettre en place le bon creationListener dans le
		 * {@link EditorFrame#drawingPanel}.
		 */
		public ShapeItemListener(FigureType type)
		{
			/*
			 * TODO Mise en place du type de figure dans drawingModel
			 */

			/*
			 * TODO obtention d'un XXXCreationLsitener avec
			 * creationListener = type.getCreationListener(...)
			 * (voir l'enum FigureType)
			 */

			/*
			 * TODO ajout de ce creationListener au drawinPanel
			 */
		}

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox items = (JComboBox) e.getSource();
			int index = items.getSelectedIndex();
			int stateChange = e.getStateChange();
			FigureType figureType = FigureType.fromInteger(index);
			switch (stateChange)
			{
				case ItemEvent.SELECTED:
					/*
					 * TODO Mise en place d'un nouveau type de figure dans
					 * drawingModel
					 */

					/*
					 * TODO Mise en place d'un nouveau type de creationListener
					 * dans drawingPanel après avoir enlevé l'ancien
					 */
					break;
			}
		}

	}

	/**
	 * Contrôleur d'évènements permettant de modifier la couleur du trait
	 * @note utilise #drawingModel qui doit être non null avant instanciation
	 */
	private class ColorItemListener implements ItemListener
	{
		/**
		 * Ce à quoi s'applique la couleur choisie.
		 * Soit au rmplissage, soit au trait.
		 */
		private PaintToType applyTo;

		/**
		 * La dernière couleur choisie (pour le {@link JColorChooser})
		 */
		private Color lastColor;

		/**
		 * Le tableau des couleurs possibles
		 */
		private Paint[] colors;

		/**
		 * L'index de la couleur spéciale à choisir avec un {@link JColorChooser}
		 */
		private final int customColorIndex;

		/**
		 * L'index de la dernière couleur sélectionnée dans le combobox.
		 * Afin de pouvoir y revenir si jamais le {@link JColorChooser} est
		 * annulé.
		 */
		private int lastSelectedIndex;

		/**
		 * la couleur choisie
		 */
		private Paint paint;

		/**
		 * Constructeur du contrôleur d'évènements d'un combobox permettant
		 * de choisir la couleur de templissage
		 * @param colors le tableau des couleurs possibles
		 * @param selectedIndex l'index de l'élément actuellement sélectionné
		 * @param customColorIndex l'index de la couleur spéciale parmis les
		 * colors à définir à l'aide d'un {@link JColorChooser}.
		 * @param applyTo Ce à quoi s'applique la couleur (le remplissage ou
		 * bien le trait)
		 */
		public ColorItemListener(Paint[] colors,
		                         int selectedIndex,
		                         int customColorIndex,
		                         PaintToType applyTo)
		{
			this.colors = colors;
			lastSelectedIndex = selectedIndex;
			this.customColorIndex = customColorIndex;
			this.applyTo = applyTo;
			lastColor = (Color) colors[selectedIndex];
			paint = colors[selectedIndex];

			applyTo.applyPaintTo(paint, drawingModel);
		}

		/**
		 * Actions à réaliser lorsque l'élément sélectionné du combox change
		 * @param e l'évènement de changement d'item du combobox
		 */
		@Override
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox combo = (JComboBox) e.getSource();
			int index = combo.getSelectedIndex();

			if ((index >= 0) && (index < colors.length))
			{
				if ((e.getStateChange() == ItemEvent.SELECTED))
				{
					// New color has been selected
					if (index == customColorIndex) // Custom color from chooser
					{
						Color chosenColor = JColorChooser.showDialog(combo,
								"Choose " + applyTo.toString() + " Color",
								lastColor);
						if (chosenColor != null)
						{
							paint = chosenColor;
						}
						else
						{
							// ColorChooser has been cancelled we should go
							// back to last selected index
							combo.setSelectedIndex(lastSelectedIndex);

							// paint does not change
						}
					}
					else // regular color
					{
						paint = colors[index];
					}

					lastColor = (Color)paint;
					applyTo.applyPaintTo(paint, drawingModel);
				}
				else if (e.getStateChange() == ItemEvent.DESELECTED)
				{
					// Old color has been delesected
					if ((index >= 0 ) && (index < customColorIndex))
					{
						lastColor = (Color)edgePaints[index];
						lastSelectedIndex = index;
					}
				}
			}
			else
			{
				System.err.println("Unknown " + applyTo.toString()
						+ " color index : " + index);
			}
		}
	}


	/**
	 * Contrôleur d'évènements permettant de modifier le type de trait (normal,
	 * pointillé, sans trait)
	 * @note utilise #drawingModel qui doit être non null avant instanciation
	 */
	private class EdgeTypeListener implements ItemListener
	{
		/**
		 * Le type de trait à mettre en place
		 */
		private LineType edgeType;

		public EdgeTypeListener(LineType type)
		{
			/*
			 * TODO Mise en place du type de trait dans drawingModel
			 */
		}

		@Override
		public void itemStateChanged(ItemEvent e)
		{
			JComboBox items = (JComboBox) e.getSource();
			int index = items.getSelectedIndex();

			if (e.getStateChange() == ItemEvent.SELECTED)
			{
				/*
				 * TODO Mise en place du LineType correspondant à l'index
				 * dans drawingModel
				 */
			}
		}
	}

	/**
	 * Contrôleur d'évènement permettant de modifier la taille du trait
	 * en fonction des valeurs d'un {@link JSpinner}
	 */
	private class EdgeWidthListener implements ChangeListener
	{
		/**
		 * Constructeur du contrôleur d'évènements contrôlant l'épaisseur du
		 * trait
		 * @param initialValue la valeur initiale de la largeur du trait à
		 * appliquer au dessin (EditorFrame#drawingModel)
		 */
		public EdgeWidthListener(int initialValue)
		{
			/*
			 * TODO Mise en place de l'épaisseur de trait dans drawingModel
			 */
		}

		/**
		 * Actions à réaliser lorsque la valeur du spinner change
		 * @param e l'évènement de changement de valeur du spinner
		 */
		@Override
		public void stateChanged(ChangeEvent e)
		{
			JSpinner spinner = (JSpinner) e.getSource();
			SpinnerNumberModel spinnerModel =
					(SpinnerNumberModel) spinner.getModel();

			/*
			 * TODO Mise en place de l'épaisseur de trait dans drawingModel
			 */
		}
	}
}