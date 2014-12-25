package stray;

import java.util.HashMap;

public class Levels {
	private static Levels instance;

	private Levels() {
	}

	public static Levels instance() {
		if (instance == null) {
			instance = new Levels();
			instance.loadResources();
		}
		return instance;
	}

	public HashMap<Integer, LevelData> levels = new HashMap<Integer, LevelData>();
	public HashMap<LevelData, Integer> reverse = new HashMap<LevelData, Integer>();
	
	private void loadResources() {
		levels.clear();
		
		add(new LevelData("level1").setCutscene("controls"));
		add(new LevelData("level2"));
		add(new LevelData("level3"));
		add(new LevelData("level4"));
		add(new LevelData("level5"));
		add(new LevelData("level6"));
	}
	
	private int num = 0;
	
	private void add(LevelData l){
		levels.put(num, l);
		reverse.put(l, num);
		num++;
	}
}