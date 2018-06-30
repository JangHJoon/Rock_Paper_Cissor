package io.github.JangHJoon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

class MyGame extends JFrame{
	
	private static final long serialVersionUID = 2452543865305562359L;
	
	private static final int NO_SELECTED = -1;
	private static final int IDX_ROCK = 0;
	private static final int IDX_SCISSORS = 1;
	private static final int IDX_PAPER = 2;
	
	private final static String DIR = "img\\";
	

	private int winNum;
	private int loseNum;
	private int drawNum;
	
	private ImageIcon[] imgMJB;
	private ImageIcon[] imgYellowMJB;
	private ImageIcon[] imgGifMJB;	
	private ImageIcon imgRMJB;

	private JLabel[] lblMJB;	
	private JLabel lblPlayer;
	private JLabel lblComputer;	
		
	private int selected;
	private int entered;
	
	private JButton btnStart;
	
	private JTextField tfResult;
	
	private HashMap<Integer, Integer> winMap;
	
	public MyGame(){
		init();
		setDisplay();
		addListener();
		showFrame();
	}
	
	private void init(){
		
		winNum = 0;
		loseNum = 0;
		drawNum = 0;
	

		imgMJB = new ImageIcon[3];		
		imgYellowMJB = new ImageIcon[3];
		imgGifMJB = new ImageIcon[3];
		
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img;
		
		for(int i=0; i<3 ; i++){
			img = kit.getImage(DIR + "MJB"+i+".jpg");
			imgMJB[i] = new ImageIcon(img);
			
			img = kit.getImage(DIR + "yMJB"+i+".jpg");
			imgYellowMJB[i] = new ImageIcon(img);
			
			img = kit.getImage(DIR + "MJB" + i + ".gif");
			imgGifMJB[i] = new ImageIcon(img);
		}
		img = kit.getImage(DIR + "rMJB.gif");
		imgRMJB = new ImageIcon(img);
		
		
		lblPlayer = new JLabel("", JLabel.CENTER);
		lblPlayer.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
		lblPlayer.setVerticalTextPosition(JLabel.CENTER);
		lblPlayer.setHorizontalTextPosition(JLabel.CENTER);
		lblPlayer.setPreferredSize(new Dimension(120,120));
		lblPlayer.setBorder(new TitledBorder(new LineBorder(Color.BLACK),"유저"));		
		img = kit.getImage("q.jpg");
		lblPlayer.setIcon(new ImageIcon(img));
		
		lblComputer = new JLabel("", JLabel.CENTER);
		lblComputer.setFont(new Font(Font.DIALOG, Font.BOLD, 40));
		lblComputer.setVerticalTextPosition(JLabel.CENTER);
		lblComputer.setHorizontalTextPosition(JLabel.CENTER);
		lblComputer.setPreferredSize(new Dimension(120,120));
		lblComputer.setBorder(new TitledBorder(new LineBorder(Color.BLACK),"컴퓨터"));		
		lblComputer.setIcon(new ImageIcon(img));
				
				
			
		
		lblMJB = new JLabel[3];
		
		String[] strMJB = {"묵","찌","빠"};
		
		for(int i=0 ; i<3 ;i++){
			lblMJB[i] = new JLabel("", JLabel.CENTER);
			lblMJB[i].setIcon(imgMJB[i]);
			lblMJB[i].setToolTipText(strMJB[i]);
			lblMJB[i].setPreferredSize(new Dimension(100,100));
		}
		

		selected = NO_SELECTED;
		entered = NO_SELECTED;
		
		btnStart  = new JButton("시작");	
		
		tfResult = new JTextField("사용자의 전적 : " + winNum + "승 " + loseNum + "패 " + drawNum + "무");
		tfResult.setEditable(false);
		
		winMap = new HashMap<Integer,Integer>();
		winMap.put(IDX_ROCK, IDX_SCISSORS);
		winMap.put(IDX_SCISSORS, IDX_PAPER);
		winMap.put(IDX_PAPER, IDX_ROCK);
	
	}
	
	private void setDisplay(){
		
		// North
		
		JPanel pnlMJB = new JPanel();
		pnlMJB.setBackground(Color.WHITE);
		pnlMJB.setBorder(new TitledBorder(new LineBorder(Color.black), "선택", TitledBorder.CENTER, TitledBorder.BELOW_TOP));
		pnlMJB.setPreferredSize(new Dimension(300, 150));
		for(JLabel lbl: lblMJB){
			pnlMJB.add(lbl);
		}
		
		JPanel pnlBtn = new JPanel();
		pnlBtn.setBackground(Color.WHITE);
		pnlBtn.add(btnStart);

		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(pnlMJB, BorderLayout.NORTH);
		pnlNorth.add(pnlBtn, BorderLayout.CENTER);

		
		// Center
		
		JPanel pnlResult = new JPanel(new BorderLayout());		
		pnlResult.setBackground(Color.WHITE);

		JLabel lblVS = new JLabel("VS", JLabel.CENTER);
		lblVS.setFont(new Font(Font.DIALOG, Font.ITALIC|Font.BOLD, 40));
				
		pnlResult.add(lblPlayer , BorderLayout.WEST);
		pnlResult.add(lblVS, BorderLayout.CENTER);
		pnlResult.add(lblComputer, BorderLayout.EAST);

		
		// add
		
		add(pnlNorth, BorderLayout.NORTH);
		add(pnlResult, BorderLayout.CENTER);		
		add(tfResult, BorderLayout.SOUTH);

						
		
	}
	
	
	private void addListener(){
		
		MouseAdapter listener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				lblPlayer.setText("");
				lblComputer.setText("");
				
				System.out.println("Mouse Click" + entered);
				
				if(selected != NO_SELECTED){
					lblMJB[selected].setIcon(imgMJB[selected]);
				}
				
				selected = entered;
				
				lblMJB[selected].setIcon(imgYellowMJB[selected]);
				lblPlayer.setIcon(imgMJB[selected]);
				lblComputer.setIcon(imgRMJB);
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				


				JLabel src = (JLabel)e.getSource();
				
				int idx;
				for(idx=0 ; idx<3 ;idx++){
					if(lblMJB[idx] ==src){
						System.out.println("Mouse Entered" + idx);
						break;
					}
				}				

				entered = idx;
				
				if(selected != entered){
					lblMJB[idx].setIcon(imgGifMJB[idx]);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				System.out.println("Mouse Exited" + entered);
								
				if(selected != entered){
					lblMJB[entered].setIcon(imgMJB[entered]);
					
				}
				
				entered = NO_SELECTED;
				
				
			}
		};
		
		for(JLabel lbl: lblMJB){
			lbl.addMouseListener(listener);
		}
		
		
		btnStart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(selected != NO_SELECTED){
					
					Random r = new Random();
					
					int randomIdx =r.nextInt(3);
					
					lblComputer.setIcon(imgMJB[randomIdx]);					
					lblComputer.setForeground(Color.WHITE);
					lblPlayer.setIcon(imgMJB[selected]);				
					lblPlayer.setForeground(Color.WHITE);
					
					
					
					if(selected == randomIdx){
						System.out.println("비겼습니다.");
						lblPlayer.setText("Draw");
						lblPlayer.setForeground(Color.GREEN);
						
						lblComputer.setText("Draw");
						lblComputer.setForeground(Color.GREEN);
						drawNum++;
						
						
					} else if(winMap.get(selected)==randomIdx){
						System.out.println("이겼습니다.");
						lblPlayer.setText("Win");
						lblPlayer.setForeground(Color.RED);
						
						lblComputer.setText("Lose");
						lblComputer.setForeground(Color.GRAY);
						winNum++;
						
					} else {
						System.out.println("졌습니다.");
						lblPlayer.setText("Lose");
						lblPlayer.setForeground(Color.GRAY);
						
						lblComputer.setText("Win");
						lblComputer.setForeground(Color.RED);
						loseNum++;
					}
					
					
					tfResult.setText("유저의 전적 : " + winNum + "승 " + loseNum + "패 " + drawNum + "무(승률 : " + 
								String.valueOf((double)(winNum)*100/(winNum + loseNum + drawNum)) + "%)");
					
					
				}				
			}
		});
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				int answer = JOptionPane.showConfirmDialog(
						MyGame.this, "종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(answer == JOptionPane.YES_OPTION){
					System.exit(0);
				}
			}
			
		});
		
	}
	private void showFrame(){
		setTitle("MJBGame");
		setSize(350,400);
		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}
	
}

public class Play {
	
	public static void main(String[] args){
		new MyGame();
		
	}

}
