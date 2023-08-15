import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;

class Reader extends JFrame implements ActionListener {

	// public global variables, casue whos gonna hack a text reader?
	JFrame frame;
	JTextArea textArea;
	static JLabel output;
	static JLabel percent;
	// page starts paused
	static boolean pause = true;
	static String[] splitText;
	static int currentWordCount;
	public static int speed = 0;

	public Reader() {
		
		Border border = BorderFactory.createLineBorder(Color.DARK_GRAY);
		
		// initalize frame
		frame = new JFrame("Nic's Quick Reading Helper");
		frame.setSize(960, 540);
		frame.setLayout(null);
		frame.setResizable(false);
        
		//panels########################################################################
		JPanel panel = new JPanel();  
        panel.setBounds(520,20,400,260);    
        panel.setBorder(border);
        JPanel panel2 = new JPanel();  
        panel2.setBounds(520,300,400,100);    
        panel2.setBorder(border);
        
		// butons #########################################################################
		JButton b1 = new JButton("Play/Restart");
		b1.setBounds(645, 50, 150, 50);
		b1.addActionListener(this);

		JButton b2 = new JButton("Back");
		b2.setBounds(540, 130, 100, 50);
		b2.addActionListener(this);

		JButton b3 = new JButton("Pause/Unpause");
		b3.setBounds(645, 130, 150, 50);
		b3.addActionListener(this);

		JButton b4 = new JButton("Forward");
		b4.setBounds(800, 130, 100, 50);
		b4.addActionListener(this);

		JButton b5 = new JButton("Slower");
		b5.setBounds(590, 200, 100, 50);
		b5.addActionListener(this);

		JButton b6 = new JButton("Faster");
		b6.setBounds(750, 200, 100, 50);
		b6.addActionListener(this);

		//text ############################################################################
		output = new JLabel("text");
		Font font = new Font("Arial", Font.BOLD, 25);
		output.setFont(font);
		output.setBounds(525, 300, 1000, 100);
		
		percent = new JLabel("0%");
		percent.setFont(font);
		percent.setBounds(850, 300, 1000, 100);
		
	
		//text box ##############################################################################
		textArea = new JTextArea(20, 20);
		JScrollPane scrollableTextArea = new JScrollPane(textArea);
		scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableTextArea.setBounds(20, 20, 480, 460);
		
		//change all colors cause i have problems and like dark mode #############################
		frame.getContentPane().setBackground(Color.gray);
		final Color BUTON_COLOR = new Color(230,230,230);
		final Color TEXT_COLOR = Color.DARK_GRAY;
		b1.setBackground(BUTON_COLOR);
		b2.setBackground(BUTON_COLOR);
		b3.setBackground(BUTON_COLOR);
		b4.setBackground(BUTON_COLOR);
		b5.setBackground(BUTON_COLOR);
		b6.setBackground(BUTON_COLOR);
		output.setForeground(TEXT_COLOR);
		percent.setForeground(TEXT_COLOR);
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setForeground(Color.WHITE);
		textArea.setCaretColor(Color.white);
		panel.setBackground(Color.LIGHT_GRAY);
		panel2.setBackground(Color.WHITE);
		scrollableTextArea.getVerticalScrollBar().setBackground(Color.gray);
		scrollableTextArea.getHorizontalScrollBar().setBackground(Color.gray);
		
		// add components to frame#################################################################
		frame.add(b1);
		frame.add(b2);
		frame.add(b3);
		frame.add(b4);
		frame.add(b5);
		frame.add(b6);
        frame.add(output);
		frame.add(percent);
		frame.add(scrollableTextArea);
		frame.add(panel);
		frame.add(panel2);
		
		//signature because why not? #################################################################
		JLabel signiture = new JLabel("Created by: Cousin Nic");
		signiture.setFont(font);
		signiture.setBounds(520, 430, 1000, 20);
		signiture.setForeground(new Color(110,110,110));
		frame.add(signiture);
		
		//final frame settings########################################################################
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		Reader frame = new Reader();
		run();
	}

	public static void run() {

		// run loop forever
		while (true) {
			//System.out.println(speed);
			// only work if un-paused
			if (!pause) {
				currentWordCount++;

				// check if at end of page
				if (currentWordCount == splitText.length - 1) {
					pause = true;
				}

				// save word for ease of use
				String word = splitText[currentWordCount];
				// print word
				calculateAndPrintOut();
				// determine wait depending on speed setting and punctuation
				if (word.contains(".")) {
					sleep(600);
				} else if (word.contains(",")) {
					sleep(300);
				}
				sleep(((200 + speed) + (word.length() * (40+(speed/5)))));
				//System.out.println(((200 + speed) + (word.length() * (40+(speed/5)))));
			} else {
				sleep(100);
			}
		}

	}
	
	public static void calculateAndPrintOut() {
		String word = splitText[currentWordCount];
		output.setText(word);

		// create and print progress percent
		String percentString = String.valueOf((double) (currentWordCount + 1.0) / splitText.length)
				.concat("00");
		percent.setText(
				percentString.charAt(2) + "" + percentString.charAt(3) + "." + percentString.charAt(4) + "%");
		if (percentString.equals("1.000")) {
			percent.setText("100%");
		}
		

	}

	public static void sleep(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//begin printing entered text
		if (e.getActionCommand().equals("Play/Restart")) {
			String text = textArea.getText();
			splitText = text.replace("\n", " ").split(" ");
			currentWordCount = -1;
			pause = false;
		}
		
		//skip back 10 words
		if (e.getActionCommand().equals("Back")) {
			if (splitText != null) {
				currentWordCount -= 10;
				if (currentWordCount < 0) {
					currentWordCount = 0;
				}
				calculateAndPrintOut();
			}
		}
		
		//pause print progress
		if (e.getActionCommand().equals("Pause/Unpause")) {
			if (splitText != null) {
				if (currentWordCount + 1 != splitText.length) {
					if (pause) {
						pause = false;
					} else {
						pause = true;
					}
				}
			}
		}
		
		//skip forward 10 words
		if (e.getActionCommand().equals("Forward")) {
			if (splitText != null) {
				currentWordCount += 10;
				if (splitText.length < currentWordCount) {
					currentWordCount = splitText.length-1;
					
				}
				calculateAndPrintOut();
			}
		}

		//slow down
		if (e.getActionCommand().equals("Slower")) {
			speed += 25;
		}
		
		//speed up
		if (e.getActionCommand().equals("Faster")) {
			if(speed > -150) {
				speed -= 25;
				}
		}

	}
}