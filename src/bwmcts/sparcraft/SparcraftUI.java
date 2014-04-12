package bwmcts.sparcraft;

import javabot.types.UnitType.UnitTypes;

import javax.swing.*;

import bwmcts.combat.UctLogic;
import bwmcts.sparcraft.players.Player;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class SparcraftUI extends JComponent {
	
	int offSetX=250;
	int offSetY=10;
	
	
	HashMap<String,Image> images=new HashMap<String,Image>();
	Image background;
	String dirPath="img\\";
	//Image Terran_Marine;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameState _state;
	public int c = 6;
	private Player p1;
	private Player p2;
	
	private static SparcraftUI instance;

	public SparcraftUI(Player p1, Player p2) {
		JFrame f = new JFrame();
        f.setSize(1000,700);
        f.setTitle("Sparcraft in JAVA");
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(this);    
        f.setVisible(true);
		for (UnitTypes u: UnitTypes.values()){

			images.put(u.toString(), Toolkit.getDefaultToolkit().getImage(dirPath+"units\\"+u.toString()+".png"));
		}
		int  i=(int)(Math.random()*10 % 4); 
		background=Toolkit.getDefaultToolkit().getImage(dirPath+"ground\\ground"+(i>0?i:"")+".png");
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public static synchronized SparcraftUI getUI(GameState state) {
        if (instance == null) {
            instance = new SparcraftUI(null, null);
        }
        instance.setGameState(state);
        
        return instance;
    }
	
	public static synchronized SparcraftUI getUI(GameState state, Player p1, Player p2) {
        if (instance == null) {
            instance = new SparcraftUI(p1, p2);
        }
        instance.setGameState(state);
        
        return instance;
    }
	
	public void paint(Graphics g){
		Map map=_state.getMap();
		if (map!=null){
			if (background !=null){
				g.drawImage(background, offSetX, offSetY, map.getPixelWidth()*2, map.getPixelHeight()*2,this);
			} else {
				g.drawRect(offSetX, offSetY, map.getPixelWidth()*2, map.getPixelHeight()*2);
			}
			drawScaleForMap(g, map.getPixelWidth()*2, map.getPixelHeight()*2);
		}
		
		g.setColor(Color.blue);
		int k=0;
		for (Unit a : _state.getAllUnit()[0]){
			if (a!=null && a.isAlive()){
				Image i=images.get(a.type().getName().replaceAll(" ", "_"));
				Position p=a.currentPosition(_state.getTime());
				if (i!=null){
					drawImageOnPosition(g,i,p);
			    } else{
			    	g.drawOval(p.getX()-2+offSetX, p.getY()-2+offSetY, 4, 4);
			    }
				if (a._previousAction!=null && a._previousAction._moveType==UnitActionTypes.ATTACK){
					g.drawLine(p.getX()-2+offSetX, p.getY()-2+offSetY, a._previousAction.pos().getX()-2+offSetX, a._previousAction.pos().getY()-2+offSetY);
				}
				drawUnitInformation(g,a,++k,p);
			}
			
		}
		g.setColor(Color.red);
		for (Unit a : _state.getAllUnit()[1]){
			if (a!=null && a.isAlive()){
				Image i=images.get(a.type().getName().replaceAll(" ", "_"));
				Position p=a.currentPosition(_state.getTime());
				if (i!=null){
					drawImageOnPosition(g,i,p);
			    } else {
			    	g.drawOval(p.getX()-2+offSetX, p.getY()-2+offSetY, 4, 4);
			    }
				if (a._previousAction!=null && a._previousAction._moveType==UnitActionTypes.ATTACK){
					g.drawLine(p.getX()-2+offSetX, p.getY()-2+offSetY, a._previousAction.pos().getX()-2+offSetX, a._previousAction.pos().getY()-2+offSetY);
				}
				drawUnitInformation(g,a,++k,p);
			}
			
		} 
		
		if (p1 instanceof UctLogic){
			List<List<Unit>> clustersP1 = ((UctLogic)p1).getClusters();
			if (clustersP1!=null)
				drawClusters(g, clustersP1);	
		}
		if (p2 instanceof UctLogic){
			List<List<Unit>> clustersP2 = ((UctLogic)p2).getClusters();
			if (clustersP2!=null)
				drawClusters(g, clustersP2);
		}
	    
	    
	}
	
	private void drawClusters(Graphics g, List<List<Unit>> clusters) {
	    int clusterId=0;
	    if (clusters == null)
	    	return;
	    for (List<Unit> units : clusters){
	    	g.setColor(getColor(clusterId++));
	    	for (Unit a : units){
	    		g.drawOval(a.currentPosition(_state.getTime()).getX()-12+offSetX, a.currentPosition(_state.getTime()).getY()-12+offSetY, 24, 24);
	    		g.drawOval(a.currentPosition(_state.getTime()).getX()-13+offSetX, a.currentPosition(_state.getTime()).getY()-13+offSetY, 26, 26);
	    		g.drawOval(a.currentPosition(_state.getTime()).getX()-14+offSetX, a.currentPosition(_state.getTime()).getY()-14+offSetY, 28, 28);
	    	}
	    }
	}

	private void drawScaleForMap(Graphics g, int pixelWidth, int pixelHeight) {
		for (int i=0; i<(int)pixelWidth+1;i+=50){
			if (i!=0 && i % 100==0){
				g.drawString(String.valueOf(i), offSetX+i, offSetY);
			}
			g.drawLine(offSetX+i, offSetY, offSetX+i, offSetY-10);
		}
		for (int i=0; i<(int)pixelHeight+1;i+=50){
			if (i!=0 && i % 100==0){
				g.drawString(String.valueOf(i), offSetX-20, offSetY+i);
			}
			g.drawLine(offSetX-10, offSetY+i, offSetX, offSetY+i);
		}
	}


	private void drawUnitInformation(Graphics g, Unit u, int i, Position p) {
		g.drawString(u.getId()+":"+u.name()+" HP:"+u.currentHP()+" A:"+u.getArmor()+" D:"+u.damageGround()+"/"+u.damageAir(), 3, i*20);
		
		g.drawRect(p.getX()+offSetX-15,p.getY()-15+offSetY, (int)(30*u._currentHP/u.maxHP()), 1);
		
	}

	public void setGameState(GameState state){
		_state=state;
	}
	
	private void drawImageOnPosition(Graphics g,Image i, Position p){
		int width=i.getWidth(this);
		int height=i.getHeight(this);

		g.drawImage(i, p.getX()+offSetX-(int)(width/2), p.getY()+offSetY-(int)(height/2), width, height, this);
	}
	
	private Color getColor(int i){
		
		switch (i){
			case 0:
				return Color.CYAN;
			case 1:
				return Color.GREEN;
			case 2:
				return Color.WHITE;
			case 3:
				return Color.PINK;
			case 4:
				return Color.YELLOW;
			case 5:
				return Color.LIGHT_GRAY;
			case 6:
				return Color.RED;
			case 7:
				return Color.BLUE;
			case 8:
				return Color.ORANGE;
			default:
				return Color.BLACK;
				
		}
	}
}
