package com.appreators.game.barrelball.utils;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class ParticleSystem {
	// パーティクル
	private Particle[] particles;
	int capacity;
	
	public ParticleSystem(int capacity, int particleLifeSpan) {
		this.capacity = capacity;
		particles = new Particle[capacity];
		for(int i=0;i<capacity;i++){
			particles[i] = new Particle();
			particles[i].life_span = particleLifeSpan;
		}
	}
	
	public void add(float x, float y, float size, float move_x, float move_y){
		for(int i=0;i<capacity;i++){
			if (!particles[i].is_active){
				// 非アクティブのパーティクルを探す
				particles[i].is_active = true;
				particles[i].x = x;
				particles[i].y = y;
				particles[i].size = size;
				particles[i].move_x = move_x;
				particles[i].move_y = move_y;
				break;
			}
		}
	}
	
	public void update(){
		for(Particle particle : particles){
			if(particle.is_active)
				particle.update();
		}
	}
		
	public void draw(GL10 gl, int texture) {
		//頂点の配列
		//1つのパーティクルあたり6頂点×2要素
		float[] vertices = GraphicUtil.getVertices(6 * 2 * capacity);
	 
		//色の配列
		//1つのパーティクルあたりの6頂点×4要素(r,g,b,a)×最大のパーティクル数
		float[] colors = GraphicUtil.getColors(6 * 4 * capacity);
	 
		//テクスチャマッピングの配列
		//1つのパーティクルあたり6頂点×2要素(x,y)×最大のパーティクル数
		float[] coords = GraphicUtil.getCoords(6 * 2 * capacity);
	 
		//アクティブなパーティクルのカウント
		int vertexIndex = 0;
		int colorIndex = 0;
		int texCoordIndex = 0;
	 
		int activeParticleCount = 0;
	 
		for (int i = 0; i < capacity; i++) {
			// 　状態がアクティブのパーティクルのみ描画します
			if (particles[i].is_active) {
				//頂点座標を追加します
				float centerX = particles[i].x;
				float centerY = particles[i].y;
				float size = particles[i].size;
				float vLeft = -0.5f * size + centerX;
				float vRight = 0.5f * size + centerX;
				float vTop = 0.5f * size + centerY;
				float vBottom = -0.5f* size + centerY;
	 
				//ポリゴン1
				vertices[vertexIndex++] = vLeft;
				vertices[vertexIndex++] = vTop;
				vertices[vertexIndex++] = vRight;
				vertices[vertexIndex++] = vTop;
				vertices[vertexIndex++] = vLeft;
				vertices[vertexIndex++] = vBottom;
	 
				//ポリゴン2
				vertices[vertexIndex++] = vRight;
				vertices[vertexIndex++] = vTop;
				vertices[vertexIndex++] = vLeft;
				vertices[vertexIndex++] = vBottom;
				vertices[vertexIndex++] = vRight;
				vertices[vertexIndex++] = vBottom;
	 
				//色
				float lifePercentage = (float)particles[i].frame_number/(float)particles[i].life_span;
				float alpha;
				if (lifePercentage <= 0.5f) {
					alpha = lifePercentage * 2.0f;
				} else {
					alpha = 1.0f - (lifePercentage - 0.5f) * 2.0f;
				}
	 
				for (int j = 0; j < 6; j++) {
					colors[colorIndex++] = 1.0f;
					colors[colorIndex++] = 1.0f;
					colors[colorIndex++] = 1.0f;
					colors[colorIndex++] = alpha;
				}
	 
				//マッピング座標
				//ポリゴン1
				coords[texCoordIndex++] = 0.0f;
				coords[texCoordIndex++] = 0.0f;
				coords[texCoordIndex++] = 1.0f;
				coords[texCoordIndex++] = 0.0f;
				coords[texCoordIndex++] = 0.0f;
				coords[texCoordIndex++] = 1.0f;
				//ポリゴン2
				coords[texCoordIndex++] = 1.0f;
				coords[texCoordIndex++] = 0.0f;
				coords[texCoordIndex++] = 0.0f;
				coords[texCoordIndex++] = 1.0f;
				coords[texCoordIndex++] = 1.0f;
				coords[texCoordIndex++] = 1.0f;
	 
				//アクティブパーティクルの数を数えます
				activeParticleCount++;
			}
		}
	 
		FloatBuffer verticesBuffer = GraphicUtil.makeVerticesBuffer(vertices);
		FloatBuffer colorBuffer = GraphicUtil.makeColorsBuffer(colors);
		FloatBuffer coordBuffer = GraphicUtil.makeTexCoordsBuffer(coords);
	 
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, verticesBuffer);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	 
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, coordBuffer);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	 
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, activeParticleCount * 6);
	 
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

}
