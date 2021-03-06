package figures.enums;

import java.awt.BasicStroke;
import java.awt.Paint;
import java.awt.geom.Point2D;

import javax.swing.JLabel;

import figures.AbstractFigure;
import figures.Circle;
import figures.Drawing;
import figures.Ellipse;
import figures.Polygon;
import figures.Rectangle;
import figures.RoundedRectangle;
import figures.creationListeners.AbstractCreationListener;
import figures.creationListeners.PolygonShapeListener;
import figures.creationListeners.RoundedRectangleShapeListener;
import figures.creationListeners.ShapeListener;

/**
 * Enumeration des différentes figures possibles
 *
 * @author davidroussel
 */
public enum FigureType
{
	/**
	 * Les différents types de figures
	 */
	CIRCLE, ELLIPSE, RECTANGLE, ROUNDED_RECTANGLE, POLYGON;

	/**
	 * Obtention d'une instance de figure correspondant au type
	 *
	 * @param stroke la césure du trait (ou pas de trait si null)
	 * @param edge la couleur du trait (ou pas de trait si null)
	 * @param fill la couleur de remplissage (ou pas de remplissage si null)
	 * @param p le premier point de la figure
	 * @return une nouvelle instance de figure correspondant à la valeur de cet
	 *         enum
	 * @throws AssertionError si la valeur de cet enum n'est pas prévue
	 */
	public AbstractFigure getFigure(BasicStroke stroke,
			Paint edge,
			Paint fill,
			Point2D p) throws AssertionError
	{
		switch (this)
		{
			case CIRCLE:
				/** Créé un cercle de rayon null */
				return new Circle(stroke, edge, fill, 0.0, p);
			case ELLIPSE:
				/** Créé éllipse de demi grand et petit axe null */
				return new Ellipse(stroke, edge, fill, p, 0, 0);
			case RECTANGLE:
				return new Rectangle(stroke, edge, fill, p.getX(), p.getY());
			case ROUNDED_RECTANGLE:
				return new RoundedRectangle(stroke, edge, fill, p);
			case POLYGON:
				return new Polygon(stroke, edge, fill, p);
		}

		throw new AssertionError("FigureType unknown assertion: " + this);
	}

	/**
	 * Obtention d'un CreationListener adequat pour la valeur de cet enum
	 *
	 * @param model le modèle de dessin à modifier
	 * @param tipLabel le label dans lequel afficher les conseils utilisateur
	 * @return une nouvelle instance de CreationListener adéquate pour le type
	 *         de figure de cet enum.
	 * @throws AssertionError si la valeur de cet enum n'est pas prévue
	 */
	public AbstractCreationListener getCreationListener(Drawing model,
			JLabel tipLabel) throws AssertionError
	{
		switch (this)
		{
			case CIRCLE:
			case ELLIPSE:
			case RECTANGLE:
				return new ShapeListener(model, tipLabel, 2);
			case ROUNDED_RECTANGLE:
				return new RoundedRectangleShapeListener(model, tipLabel, 3);
			case POLYGON:
				return new PolygonShapeListener(model, tipLabel, 3);
		}

		throw new AssertionError("FigureType unknown assertion: " + this);
	}

	/**
	 * Représentation sous forme de chaine de caractères
	 *
	 * @return une chaine de caractère représentant la valeur de cet enum
	 * @throws AssertionError si la valeur de cet enum n'est pas prévue
	 */
	@Override
	public String toString() throws AssertionError
	{
		switch (this)
		{
			case CIRCLE:
				return new String("Circle");
			case ELLIPSE:
				return new String("Ellipse");
			case RECTANGLE:
				return new String("Rectangle");
			case ROUNDED_RECTANGLE:
				return new String("Rounded Rectangle");
			case POLYGON:
				return new String("Polygon");
		}

		throw new AssertionError("FigureType unknown assertion: " + this);
	}

	/**
	 * Otention d'un tableau de chaine de caractères contenant l'ensemble des
	 * nom des figures (à utiliser pour le remplissage de combobox)
	 *
	 * @return un tableau de chaine de caractères contenant l'ensemble des nom
	 *         des figures
	 */
	public static String[] stringValues()
	{
		FigureType[] values = FigureType.values();
		String[] stringValues = new String[values.length];

		for (int i = 0; i < values.length; i++)
		{
			stringValues[i] = values[i].toString();
		}

		return stringValues;
	}

	/**
	 * Conversion d'un entier en FigureType
	 *
	 * @param i l'entier à convertir en FigureType
	 * @return le FigureType correspondant à l'entier
	 */
	public static FigureType fromInteger(int i)
	{
		switch (i)
		{
			case 0:
				return CIRCLE;
			case 1:
				return ELLIPSE;
			case 2:
				return RECTANGLE;
			case 3:
				return ROUNDED_RECTANGLE;
			case 4:
				return POLYGON;
			default:
				return POLYGON;
		}
	}
}
