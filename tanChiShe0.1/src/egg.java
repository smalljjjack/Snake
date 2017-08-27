import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class egg {
	int clo, row;
	int w = yard.BLOCKW;
	int h = yard.BLOCKW;
	private static Random r = new Random(); 
	
	private Color color = Color.GREEN;
	
	public egg(int clo, int row){
		this.clo = clo;
		this.row = row;
	}
	
	public egg(){
		this(r.nextInt(yard.ROWS-3) +3 , r.nextInt(yard.CLOS)) ;
	}
	
	public void reappear(){
		this.row = r.nextInt(yard.ROWS-3) + 3;
		this.clo = r.nextInt(yard.CLOS+3);
	}
	
	public Rectangle getRect(){
		return new Rectangle(yard.BLOCKW * this.clo, yard.BLOCKW * this.row, this.w, this.h);
	}
		
	void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(yard.BLOCKW * clo, yard.BLOCKW * row, w, h);
		g.setColor(c);
		if(color == Color.GREEN) color = Color.YELLOW;
		else color = Color.GREEN;	
	}

	public void setClo(int clo) {
		this.clo = clo;
		// TODO Auto-generated method stub
	}
	public void setRow(int row){
		this.row = row;
	}
	
	public int getClo(){
		return clo;
	}
	
	public int getRow(){
		return row;
	}
}
