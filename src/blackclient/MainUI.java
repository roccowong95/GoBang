package blackclient;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class MainUI extends JFrame {
	public static int[][] chesses = new int[15][15];
	public static int[][] global_score = new int[15][15];

	MainUI() {
		for (int i = 0; i < config1.ROWS; i++) {
			for (int j = 0; j < config1.COLUMNS; j++) {
				chesses[i][j] = 0;
				global_score[i][j] = 0;
			}
		}
		// this.setTitle("黑方五子棋");
		// this.setSize(500, 500);
		// this.setResizable(false);
		// this.setDefaultCloseOperation(3);
		// this.setVisible(true);

	}

	public void initUI() {
		this.setTitle("黑方五子棋");
		this.setSize(700, 700);
		this.setResizable(false);
		this.setDefaultCloseOperation(3);
		this.setVisible(true);
	}

	public void redraw() {
		repaint();
	}

	public void drawBoard(Graphics g) {
		for (int i = 0; i < config1.ROWS; i++)
			g.drawLine(config1.SIZE, config1.SIZE * (i + 1), 450, config1.SIZE
					* (i + 1));
		for (int j = 0; j < config1.COLUMNS; j++)
			g.drawLine(config1.SIZE * (j + 1), config1.SIZE, config1.SIZE
					* (j + 1), 450);
		for (int i = 0; i < config1.ROWS; i++) {
			for (int j = 0; j < config1.COLUMNS; j++) {
				if (chesses[i][j] == 1) {
					g.setColor(Color.BLACK);
					g.fillOval((i + 1) * config1.SIZE - config1.CHESS_SIZE / 2,
							(j + 1) * config1.SIZE - config1.CHESS_SIZE / 2,
							config1.CHESS_SIZE, config1.CHESS_SIZE);

				} else if (chesses[i][j] == -1) {
					g.setColor(Color.WHITE);
					g.fillOval((i + 1) * config1.SIZE - config1.CHESS_SIZE / 2,
							(j + 1) * config1.SIZE - config1.CHESS_SIZE / 2,
							config1.CHESS_SIZE, config1.CHESS_SIZE);
				}
				// g.fillOval(i*50-20, j*50-20, 40, 40);
				// g.drawOval(i*50-20, j*50-20, 40, 40);
			}
		}

	}

	/*
	 * public static void main(String[] args){ MainUI mainui=new MainUI();
	 * //mainui.redraw(); }
	 */

}
