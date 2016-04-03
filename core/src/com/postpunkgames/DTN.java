package com.postpunkgames;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class DTN extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	
	OrthographicCamera cam;
	Viewport viewport;
	
	int VIEWPORT_WIDTH = 1920;
	int VIEWPORT_HEIGHT = 1080;
	
	ShaderProgram shader;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("bg.jpg");
		
		//camera setup
		cam = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
		cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
		cam.update();
		
		//viewport
		viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, cam);
		
		//shader setup
		shader.pedantic = false;
		shader = new ShaderProgram(Gdx.files.internal("shaders/vertex.vertex"), Gdx.files.internal("shaders/fragment.fragment"));
		
		if( !shader.isCompiled() ) {
			Gdx.app.error( "ShaderLoader", shader.getLog() );
			System.exit( -1 );
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		Gdx.gl.glEnable(Gdx.gl20.GL_BLEND);
		Gdx.gl.glBlendFunc(Gdx.gl20.GL_SRC_ALPHA, Gdx.gl20.GL_ONE_MINUS_SRC_ALPHA);

		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		shader.begin();
		shader.setUniformMatrix("u_projTrans", cam.combined);
		shader.end();
		
		
		batch.begin();
		batch.setShader(shader);
		batch.draw(img, 0, 0);
		batch.setShader(null);
		batch.end();
	}
}
