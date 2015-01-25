package dungeoncrawler.main;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

public class Model {
	public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
	public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
	public ArrayList<ModelFace> faces = new ArrayList<ModelFace>();
	public ArrayList<Vector3f> texcoords = new ArrayList<Vector3f>();

	public void draw(Texture t){
		if(faces.size() > 0){
			t.bind();
			GL11.glBegin(GL11.GL_TRIANGLES);

			for(int i = 0; i < faces.size(); i++){
				ModelFace face = faces.get(i);
				
				Vector3f n1 = normals.get((int)face.normal.x - 1);
				GL11.glNormal3f(n1.x, n1.y, n1.z);
				
				Vector3f v1 = vertices.get((int) face.vertex.x -1);
				GL11.glTexCoord3f(v1.x, v1.y, v1.z);
				GL11.glVertex3f(v1.x, v1.y, v1.z);
				
				
				Vector3f n2 = normals.get((int)face.normal.y - 1);
				GL11.glNormal3f(n2.x, n2.y, n2.z);
				
				Vector3f v2 = vertices.get((int) face.vertex.y -1);
				GL11.glTexCoord3f(v2.x, v2.y, v2.z);
				GL11.glVertex3f(v2.x, v2.y, v2.z);
				
				
				
				Vector3f n3 = normals.get((int)face.normal.z - 1);
				GL11.glNormal3f(n3.x, n3.y, n3.z);
				
				Vector3f v3 = vertices.get((int) face.vertex.z -1);
				GL11.glTexCoord3f(v3.x, v3.y, v3.z);
				GL11.glVertex3f(v3.x, v3.y, v3.z);
				
				
				
			}

			GL11.glEnd();
		}
	}
}
