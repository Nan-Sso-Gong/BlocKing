package game.model.blocktypes;

import java.awt.Color;

import game.model.BlockController;

public class ZBlock extends BlockController{

    public ZBlock() {
		shape = new char[][] { 
			{'O', 'O', ' '},
			{' ', 'O', 'O'}
		};
		color = Color.RED;
		color_colorBlindMode = Color.RED;
	}
    
}