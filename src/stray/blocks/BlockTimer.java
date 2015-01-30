package stray.blocks;

import stray.Main;
import stray.Translator;
import stray.conversation.Conversations;
import stray.util.AssetMap;
import stray.world.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;

public class BlockTimer extends Block implements AffectsColour {

	public BlockTimer(String path, String col) {
		super(path);
		colour = col;
	}

	String colour = "";

	public static final float SECONDS = 5f;

	@Override
	public void render(World world, int x, int y) {
		super.render(world, x, y);
		world.main.font.setColor(Color.WHITE);
		if (world.main.getScreen() == Main.LEVELEDITOR) return;
		if (world.getMeta(x, y) != null) {
			world.main.drawCentered(String.format("%.1f",
					((Integer.parseInt(world.getMeta(x, y)) * 1f) / Main.TICKS)), x
					* world.tilesizex - world.camera.camerax + (World.tilesizex / 2), Main
					.convertY((y * world.tilesizey - world.camera.cameray) - 30));
		}
		if (Block.entityIntersects(world, x, y, world.getPlayer())) {
			world.main.drawCentered(Translator.getMsg("block.triggertimer"), x * world.tilesizex
					- world.camera.camerax + (World.tilesizex / 2),
					Main.convertY((y * world.tilesizey - world.camera.cameray) - 15));

			if (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.W)) {
				world.setMeta(Math.round(SECONDS * Main.TICKS) + "", x, y);
			}
		}
	}

	@Override
	public void tickUpdate(World world, int x, int y) {
		if (world.getMeta(x, y) != null) {
			int i = Integer.parseInt(world.getMeta(x, y));

			if (i <= 0) {
				world.setMeta(null, x, y);
				if (!world.global.getValue(colour).equals("") && !BlockSwitch.areOtherBlocksOn(world, x, y, colour)) {
					world.global.setValue(colour, "");
					Block.playSound(x, y, world.camera.camerax, world.camera.cameray,
							world.main.manager.get(AssetMap.get("switchsfx"), Sound.class), 1, 0.6f, false);
				}
			} else {
				if (i % ((int) Main.TICKS) == 0) {
					Block.playSound(x, y, world.camera.camerax, world.camera.cameray,
							world.main.manager.get(AssetMap.get("switchsfx"), Sound.class), 1, 0.8f, false);
				} else if ((i + ((int) Main.TICKS) / 3f) % ((int) Main.TICKS) == 0) {
					Block.playSound(x, y, world.camera.camerax, world.camera.cameray,
							world.main.manager.get(AssetMap.get("switchsfx"), Sound.class), 1, 1f, false);
				}

				if (!world.global.getValue(colour).equals("on")) {
					world.global.setValue(colour, "on");
				}
			}

			i--;
			if (world.getMeta(x, y) != null) world.setMeta(i + "", x, y);
		}
	}

	@Override
	public boolean colourOn(World world, int x, int y) {
		return world.getMeta(x, y) != null;
	}

	@Override
	public String getColour(World world, int x, int y) {
		return colour;
	}
}
