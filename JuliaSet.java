/**
 * Displays the graphical representation of the Julia Set
 * for a certain complex number: f(x, y) = x + iy
 * 
 * Finds the Basin of Attraction Set, A(infinity) = {...}, 
 * and determines when f(x, y) has a limit of infinity
 * (Basin of Attraction has an attractor == infinity)
 *
 * @author  Shalin Upadhyay
 * @since   2016-09-12
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class JuliaSet extends JPanel {

	// Object to store output screen
	private BufferedImage canvas;

	// Basic Complex Function: z = f(cRe, cIm) = cRe + cIm*i
	private static double cRe, cIm;

	// Adjusts the screen to the coordinates (moveX, moveY)
	// to focus on a certain portion of the Julia Set
	private static double moveX, moveY;

	// Magnification factor into a certain portion of the Julia Set
	private static double zoom = 1;

	// Width and Height for JFrame
	private static int width, height;
	
	// Number of iterations for the function f(z) to perform
	// to determine when the Basin of Attraction occurs
	private static int maxIterations;

	public JuliaSet() {

		/* ADJUSTABLE VALUES */

		this.width = 1000;
		this.height = 1000;

		// Real Value
		this.cRe = -0.7;
		// Real Coefficient for Imaginary Value
		this.cIm = 0.27015;

		this.moveX = 0;
		this.moveY = 0;

		this.zoom = 1;
		
		this.maxIterations = 250;
		
		/* ADJUSTABLE VALUES */

		this.canvas = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);

		colorJulia(this.width, this.height, this.moveX, this.moveY, this.zoom, this.maxIterations);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(canvas, null, null);
	}

	public Dimension getPreferredSize() {
		return new Dimension(canvas.getWidth(), canvas.getHeight());
	}
	
	public void colorJulia(int w, int h, double moveX, double moveY, double zoom, int maxIterations) {

		// Color object for color at pixel (x, y)
		Color color;
		
		// Integer value (RGB) for pixel (x, y)
		int colorVal;
		
		// Current iteration of f(x, y)
		int iter;
		
		// New and old complex numbers
		double nRe, nIm, oRe, oIm;
		
		// Radius of disk to check within for Basin of Attraction
		double rad;

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < w; j++) {

				double numRe = 1.5 * j - 0.75 * w;
				double denomRe = (zoom * w) / 2;
	
				nRe = moveX + (numRe / denomRe);
		
				double numIm = (2 * i - h) / 2;
				double denomIm = (zoom * h) / 2;
		
				nIm = moveY + (numIm / denomIm);

				for (iter = 0; iter < maxIterations; iter++) {
					
					oRe = nRe;
					oIm = nIm;

					nRe = oRe * oRe - oIm * oIm + cRe;
					nIm = 2 * oRe * oIm + cIm;
					
					rad = nRe * nRe + nIm * nIm;
					
					if (rad > 4) {
						break;
					}
				}

				// If the current iteration is below maxIterations, the complex value will be painted a value
				// to represent that it reached infinity in 'iter < maxIteration' function iterations (f^iter(z))
				colorVal = (iter < maxIterations) ? 1 : 0;

				// HSB/HSV Color value for certain values
				color = Color.getHSBColor(iter % 256, 255, iter * colorVal);

				// Changes the color of the pixel at location (x, y)
				canvas.setRGB(j, i, color.getRGB());
			}
		}

		// Repaints the canvas with colors for Julia Set
		repaint();
	}

	public static void main(String[] args) {

		// Object for JuliaSet class
		JuliaSet js = new JuliaSet();

		JFrame frame = new JFrame("Julia Set: " + cRe + " + " + cIm + "i");

		// Adds the drawn Julia Set to the JFrame
		frame.add(js);

		// Makes the output window to fit the preferred size
		frame.pack();

		// Makes the JFrame visible
		frame.setVisible(true);

		// Prevents JFrame window from being resized
		frame.setResizable(false);

		// Ends the program when output window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
