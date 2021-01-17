import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;

public class ConnectFour {
	JFrame frame;
	JPanel panel;
	final int rowTiles = 6;
	final int colTiles = 7;
	static int[][] grid = new int[6][7];
	int row, col, rowSelected, colSelected = 0;
	int pTurn = 0;
	// int dialogButton = JOptionPane.YES_NO_OPTION;
	String[] dialogButton = {"Да.", "Не."};
	int dialogResult;
	boolean win = false;
	CircleButton[][] button = new CircleButton[rowTiles][colTiles];
	JButton clear;
	JLabel whoWon;
	GridLayout myGrid = new GridLayout(7,7,10,10);

	public ConnectFour() {
		frame = new JFrame("Connect Four");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 700, 600);
		panel = new JPanel();
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(new Color(14,77,146));
		panel.setLayout(myGrid);
		panel.setBackground(new Color(14,77,146));
		whoWon = new JLabel("");
		clear = new JButton("Изчисти");
		clear.addActionListener(new clearListener());
		clear.setPreferredSize(new Dimension(100,100));

		// Стойност -1, когато играчът няма право да маркира кръга (Няма нищо под него)
		// Стойност 0, когато кръгът е свободен и може да бъде маркиран
		// Стойност 1 за Играч 1 и стойност 2 за Играч 2
		for (int x = rowTiles - 2; x >= 0; x--) {
			for (int y = colTiles - 1; y >= 0; y--) {
				grid[x][y] = -1;
			}
		}

		for (row = 0; row <= rowTiles - 1; row++) {
			for (col = 0; col <= colTiles - 1; col++) {
				button[row][col] = new CircleButton("", false, "White");
				button[row][col].addActionListener(new buttonListener());
				panel.add(button[row][col]);
			}
		}

		panel.add(whoWon);
		panel.add(clear);
		frame.setContentPane(panel);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void BoardClear()
	{
		for (int x = rowTiles - 1; x >= 0; x--) {
			for (int y = colTiles - 1; y >= 0; y--) {
				grid[x][y] = -1;
				button[x][y].restart();
			}
		}
		
		for (int y = colTiles - 1; y >= 0; y--) {
			grid[5][y] = 0;
		}
		
		whoWon.setText("");
		win = false;
	}
	
	public void Restart(int player)
	{
		String message="Играч "+player+" спечели! Искате ли да изиграете още една игра?";
		dialogResult = JOptionPane.showOptionDialog(null,message , "ПОБЕДИТЕЛ!!!!!!", JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE, null, dialogButton, null);
		if(dialogResult == JOptionPane.YES_OPTION)
		{
			for (int x = rowTiles - 1; x >=0; x--) {		// Нулира
				for (int y = colTiles - 1; y >= 0; y--) {	// всичко!
					grid[x][y] = -1;
				}
			}
			
		BoardClear();
				
		}
		else 
		{
			System.exit(0);
		}
		
	}
	

	class buttonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			for (row = rowTiles-1; row >= 0; row--) {
				for (col = colTiles-1; col >= 0; col--) {
					if (button[row][col] == event.getSource()) {		// Оцветява в розово,
						if (pTurn % 2 == 0 && grid[row][col] == 0) {	// когато играчът кликне.
							button[row][col].setColor("Yellow");			// <------------------
							grid[row][col] = 1;
							try {
								grid[row-1][col] = 0;
							}
							catch (ArrayIndexOutOfBoundsException e) {
								System.err.println("Dostignahte vurha");
							}
							if (checkWin())  {
								System.err.println("Igrach 1 specheli!");
								Restart(1);
							}
							pTurn = pTurn + 1; // Ред на другия играч!
							break;
						}
						if (pTurn % 2 == 1 && grid[row][col] == 0) {
							button[row][col].setColor("Red");
							grid[row][col] = 2;
							try {
								grid[row-1][col] = 0;
							}
							catch (ArrayIndexOutOfBoundsException e) {
								System.err.println("Dostignahte vurha!");
							}
							if (checkWin()) {
								System.err.println("Igrach 2 specheli!");
								Restart(2);
							}
							pTurn = pTurn + 1; // Дава ред на на другия играч!
							break;
						}
						else {
							JOptionPane.showMessageDialog(null, "Не може там :)");

							System.err.println("Ne moje tam");
						}
					}
				}
			}
		}
	}

	class clearListener implements ActionListener {					// Рестартиране на играта
		public void actionPerformed(ActionEvent event) {
			
			BoardClear();
			
		}
	}

	public boolean checkWin() { // Проверка за хоризонтална победа
		for (int x=0; x<6; x++) {
			for (int y=0; y<4; y++) {
				if (grid[x][y] != 0 && grid[x][y] != -1 &&
				grid[x][y] == grid[x][y+1] &&
				grid[x][y] == grid[x][y+2] &&
				grid[x][y] == grid[x][y+3]) {
					win = true;
				}
			}
		}
		for (int x=0; x<3; x++) { // Проверка за вертикална победа
			for (int y=0; y<7; y++) {
				if (grid[x][y] != 0 && grid[x][y] != -1 &&
				grid[x][y] == grid[x+1][y] &&
				grid[x][y] == grid[x+2][y] &&
				grid[x][y] == grid[x+3][y]) {
					win = true;
				}
			}
		}
		for (int x=0; x<3; x++) {	// Проверка за диагонална победа във възходящ ред
			for (int y=0; y<4; y++) {
				if (grid[x][y] != 0 && grid[x][y] != -1 &&
				grid[x][y] == grid[x+1][y+1] &&
				grid[x][y] == grid[x+2][y+2] &&
				grid[x][y] == grid[x+3][y+3]) {
					win = true;
				}
			}
		}
		for (int x=3; x<6; x++) {	// Проверка за диагонална победа в низходящ ред
			for (int y=0; y<4; y++) {
			if (grid[x][y] != 0 && grid[x][y] != -1 &&
				grid[x][y] == grid[x-1][y+1] &&
				grid[x][y] == grid[x-2][y+2] &&
				grid[x][y] == grid[x-3][y+3]) {
					win = true;
				}
			}
		}
		return win;
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//JFrame.setDefaultLookAndFeelDecorated(true);
				ConnectFour window = new ConnectFour();
				window.frame.setVisible(true);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}