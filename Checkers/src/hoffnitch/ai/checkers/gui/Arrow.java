package hoffnitch.ai.checkers.gui;

import java.awt.Point;
import java.awt.Polygon;

public class Arrow
{
	private static int length = 80;
	private static int lineWidth = 12;
	private static int headWidth = 40;
	private static int headLength = 30;
	static int xBase[];
	static int yBase[];
	
	static {
		setBasePositions();
	}

	// set base positions
	public static void setBasePositions()
	{
		xBase = new int[7];
		yBase = new int[7];
		
		xBase[0] = 0;
		yBase[0] = lineWidth / 2;

		xBase[1] = length - headLength;
		yBase[1] = yBase[0];

		xBase[2] = xBase[1];
		yBase[2] = headWidth / 2;
		
		xBase[3] = length;
		yBase[3] = 0;
		
		// fill the rest with the mirror of the first 3 points
		for (int i = 4; i < 7; i++)
		{
			xBase[i] = xBase[6 - i];
			yBase[i] = -yBase[6 - i];
		}
	}
	
	private Point coordinates;
	private double angle;
	private int xPoints[];
	private int yPoints[];
	
	public Arrow(Point coordinates, double angle)
	{
		xPoints = new int[7];
		yPoints = new int[7];
		this.coordinates = coordinates;
		this.angle = angle;
		setPoints();
	}
	
	private void setPoints()
	{
		for (int i = 0; i < 7; i++)
		{
			xPoints[i] = coordinates.x + (int)(Math.cos(angle) * xBase[i] - Math.sin(angle) * yBase[i]);
			yPoints[i] = coordinates.y + (int)(Math.cos(angle) * yBase[i] + Math.sin(angle) * xBase[i]);
		}
	}
	
	public Polygon toPolygon()
	{
		return new Polygon(xPoints, yPoints, 7);
	}
	
}
