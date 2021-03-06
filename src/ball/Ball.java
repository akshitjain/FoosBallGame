package ball;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import menu.MenuPanel;
import playground.PlayGround;

public class Ball{
	public PlayGround pg;
	
	int time;
	int speed;
	int signx;
	int signy;
	int magx;
	int magy;
	JFrame frame;
	
	
	public Ball(PlayGround pg){
		this.pg = pg;
		signx = -1;
		signy = -1;
		speed = 2;
		time = 10;
		magx = 1;
		magy = 1;
	}
	public int getsignx(){
		return this.signx;
	}

	public void move(){
		final int teamOne[][] = pg.getTeamOnePlayers();
		final int teamTwo[][] = pg.getTeamTwoPlayers();
		Timer timer1 = new Timer();
		timer1.scheduleAtFixedRate(new TimerTask() {
		int xpos;
		int ypos;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				pg.setXpos(pg.getXpos() + signx*(speed)*magx);
				pg.setYpos(pg.getYpos() + signy*(speed)*magy);
				
				if((pg.getXpos() < 8 || pg.getXpos() > 1024-8)&&(pg.getYpos() < 2 || pg.getYpos() >683 - 4)){
					signx=(-1)*signx;
					signy=(-1)*signy;
				}
				else if(pg.getXpos() < 8 || pg.getXpos() > 1024-8)
					signx = (-1)*signx;
				else if(pg.getYpos() < 2 || pg.getYpos() >683 - 4)
					signy = (-1)*signy;
				xpos = pg.getXpos();
				ypos = pg.getYpos();
				if(pg.getYpos() > pg.getGoalPost1() && pg.getYpos() < pg.getGoalPost2()){
					if(pg.getXpos() < 8){
						//Goal Of PLayer 2
						pg.setScorePlayer1(pg.getScorePlayer1() + 1);
						//condition check CPU or player 2
						pg.setXpos(10);
						pg.setYpos(342-15);
						//this.cancel();
						pg.setyPlayer2pos(342);
						pg.setyPlayer1pos(342);
						
						if(pg.getScorePlayer1() == 5){
							getFrame();
							try {
//								JFrame frame = new JFrame();
//								frame.setSize(1024,683);
								final JLabel background = new JLabel(new ImageIcon
										(ImageIO.read(new File("Images/winner.jpg"))));
								background.setLayout(new BorderLayout());
								background.setText("Player 2 Wins");
								background.setFont(new Font("Monaco",Font.PLAIN,40));
								frame.setContentPane(background);
//								frame.setVisible(true);
								frame.setVisible(true);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					}
					else if(pg.getXpos() > 1024 -8){
						//Goal of PLayer 1
						pg.setScorePlayer2(pg.getScorePlayer2()+1);
						pg.setXpos(974);
						pg.setYpos(322);
						pg.setyPlayer2pos(342);
						pg.setyPlayer1pos(342);
						pg.setXpos(pg.getXpos() + -1*(speed)*1);
						pg.setYpos(pg.getYpos() + -1*(speed)*1);
						if(pg.getScorePlayer2() == 5){
							getFrame();
							//Game over Player 1 wins
							try {
								final JLabel background = new JLabel(new ImageIcon
										(ImageIO.read(new File("Images/winner.jpg"))));
								background.setLayout(new BorderLayout());
								background.setText("Player 1 Wins");
								background.setFont(new Font("Monaco",Font.PLAIN,40));
								frame.setContentPane(background);
								frame.setVisible(true);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							this.cancel();
						}
					}
				}
				for (int i = 0; i < 11; i++) {
					if(		   xpos > teamOne[i][0] 
							&& xpos < teamOne[i][0] + 30
							&& ypos > teamOne[i][1] + pg.getyPlayer1pos()
							&& ypos < teamOne[i][1] + pg.getyPlayer1pos()+30){
						if(signx == 1){
							if(pg.getPlayer1Direction() == 0 && signy == 1){
								signy = (-1)*signy;
							}
							
							else if(pg.getPlayer1Direction() == 1 && signy == -1){
								signy = (-1)*signy;
							}
							signx = (-1)*signx;
							magx =1;
							speed = 2;
						}
						else if (signx == -1){
							if(pg.getPlayer1Direction() == 0 && signy == 1){
								signy = (-1)*signy;
							}
							else if(pg.getPlayer1Direction() == 1 && signy == -1){
								signy = (-1)*signy;
							}
							magx =3;
							speed = 1;
						}				
					}else if ( (xpos > teamTwo[i][0] )
							&& (xpos < teamTwo[i][0] + 30)
							&& (ypos > teamTwo[i][1] + pg.getyPlayer2pos())
							&& (ypos < teamTwo[i][1] + pg.getyPlayer2pos()+30)){
						if(signx == -1){
							if(pg.getPlayer2Direction() == 0 && signy == 1){
								signy = (-1)*signy;
							}
							else if(pg.getPlayer2Direction() == 1 && signy == -1){
								signy = (-1)*signy;
							}
							signx = (-1)*signx;
							magx = 1;
							speed = 2;
						}
						else if(signx == 1){
							if(pg.getPlayer2Direction() == 0 && signy == 1){
								signy = (-1)*signy;
							}
							else if(pg.getPlayer2Direction() == 1 && signy == -1){
								signy = (-1)*signy;
							}
							magx =3;
							speed = 1;
						}				
					}			
				}
				pg.repaint();			
			}
		}, 0, time);		
	}
	
	private void getFrame() {
		this.frame = MenuPanel.staticFrame;
	}
}