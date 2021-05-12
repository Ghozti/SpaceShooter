package ghozti.game;

public class Game extends com.badlogic.gdx.Game {

	Screen screen;

	@Override
	public void create() {
		//The purpose of this class is to just get things started, all the work will be done in other classes.
		screen = new Screen();
		setScreen(screen);//will set this as the game screen
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
		screen.dispose();
	}

	@Override
	public void resize(int width, int height) {
		screen.resize(width, height);
	}
}
