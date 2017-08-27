import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;



public class snake {
	//set head tail and size and the start Node
	private Node head = null;
	private Node tail = null;
	private int size =0;
	private yard y;
	

	
	private Node n = new Node(30, 30, dir.D);
	
	public snake(yard y){
		head = n;
		tail = n;
		size = 1;
		this.y = y;
	}
	
	public void addToTail() {
		Node node = null;
		switch(tail.Dir) {
		case L :
			node = new Node(tail.row, tail.clo + 1, tail.Dir);
			break;
		case U :
			node = new Node(tail.row + 1, tail.clo, tail.Dir);
			break;
		case R :
			node = new Node(tail.row, tail.clo - 1, tail.Dir);
			break;
		case D :
			node = new Node(tail.row - 1, tail.clo, tail.Dir);
			break;
		}
		tail.next = node;
		node.prev = tail;
		tail = node;
		size ++;
	}
	
	public void addToHead(){
		Node node = null;
		switch(head.Dir){
		case L:
			node = new Node(head.row, head.clo-1, head.Dir);
			break;
		case R:
			node = new Node(head.row, head.clo+1, head.Dir);
			break;
		case U:
			node= new Node(head.row-1, head.clo, head.Dir);
			break;
		case D:
			node = new Node(head.row+1, head.clo, head.Dir);
			break;
		}
		node.next = head;
		head.prev = node;
		head = node;
		size++;
	}
	
	//draw snake
	public void draw(Graphics g){
		if(size <= 0) return;
			move();
		for(Node n = head; n != null; n = n.next){
			n.draw(g);
		}
	}	
	
	

	private void checkDead() {
		if(head.row >= y.ROWS || head.row < 0 || head.clo >= y.CLOS || head.clo <= 3)
			y.stop();
		
		if(size >= 2){
			for(n = head; n.next != null; n = n.next){
				if (head.row == n.next.row && head.clo == n.next.clo) y.stop();
			}	
		}
	}

	public void eat(egg e){
		if(this.getRect().intersects(e.getRect())){
			e.reappear();
			this.addToHead();
			y.setScore(y.getScore() + 1);
		}

	}
	
	public Rectangle getRect(){
		return new Rectangle(yard.BLOCKW * head.clo, yard.BLOCKW * head.row, head.w, head.h);
	}
	
	public void move(){
		addToHead();
		deleteFromTail();
		checkDead();
	}
	
	private void deleteFromTail() {
		if(size == 0) return;
		tail = tail.prev;
		tail.next = null;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key){
		case KeyEvent.VK_LEFT:
			if(head.Dir != dir.R)
				head.Dir = dir.L;
			break;
		case KeyEvent.VK_RIGHT:
			if(head.Dir != dir.L)
				head.Dir = dir.R;
			break;
		case KeyEvent.VK_UP:
			if(head.Dir != dir.D)
				head.Dir = dir.U;
			break;
		case KeyEvent.VK_DOWN:
			if(head.Dir != dir.U)
				head.Dir = dir.D;
			break;
		}
	}
	
	class Node{
		//node: set the next row clo w h
		Node next = null;
		Node prev = null; 
		int row, clo;
		dir Dir = dir.U;
		int w = yard.BLOCKW;
		int h = yard.BLOCKW;
		
		//node's construct function
		Node(int row, int clo, dir Dir){
			this.Dir = Dir;
			this.row = row;
			this.clo = clo;
		}

		//draw Node
		void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.RED);
			g.fillRect(yard.BLOCKW * clo, yard.BLOCKW * row, w, h);
			g.setColor(c);
		}
	}
}
