package dungeoncrawler.main;

import org.newdawn.slick.opengl.Texture;

public enum BlockType {
	
	AIR(null,null,null,null,null,null,false),
	BRICK(TextureBank.BRICKS,TextureBank.BRICKS,TextureBank.BRICKS,TextureBank.BRICKS,TextureBank.BRICKS,TextureBank.BRICKS,true),
	STONE(TextureBank.STONE,TextureBank.STONE,TextureBank.STONE,TextureBank.STONE,TextureBank.STONE,TextureBank.STONE,true),
	GRASS(TextureBank.GRASS,TextureBank.DIRT,TextureBank.DIRT,TextureBank.DIRT,TextureBank.DIRT,TextureBank.DIRT,true),
	SAND(TextureBank.SAND,TextureBank.SAND,TextureBank.SAND,TextureBank.SAND,TextureBank.SAND,TextureBank.SAND,true);
	
	public Texture top;
	public Texture bottom;
	public Texture left;
	public Texture right;
	public Texture back;
	public Texture front;
	public boolean solid;
	BlockType(Texture top, Texture bottom, Texture left, Texture right, Texture back, Texture front, boolean solid){
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.back = back;
		this.front = front;
		this.solid = solid;

	}
	
	
}
