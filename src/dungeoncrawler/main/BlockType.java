package dungeoncrawler.main;

import org.newdawn.slick.opengl.Texture;

public enum BlockType {
	
	AIR(null,null,null,null,null,null),
	BRICK(TextureBank.BRICKS,TextureBank.BRICKS,TextureBank.BRICKS,TextureBank.BRICKS,TextureBank.BRICKS,TextureBank.BRICKS),
	STONE(TextureBank.STONE,TextureBank.STONE,TextureBank.STONE,TextureBank.STONE,TextureBank.STONE,TextureBank.STONE),
	GRASS(TextureBank.GRASS,TextureBank.GRASS,TextureBank.GRASS,TextureBank.GRASS,TextureBank.GRASS,TextureBank.GRASS),
	SAND(TextureBank.SAND,TextureBank.SAND,TextureBank.SAND,TextureBank.SAND,TextureBank.SAND,TextureBank.SAND);
	
	public Texture top;
	public Texture bottom;
	public Texture left;
	public Texture right;
	public Texture back;
	public Texture front;
	BlockType(Texture top, Texture bottom, Texture left, Texture right, Texture back, Texture front){
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.back = back;
		this.front = front;
	}
	
	
}
