package game.model.blocktypes;

import java.awt.Color;

import game.model.BlockController;

public class OBlock extends BlockController{

    @Override
    protected void initModel()
    {
        shape = new char[][] { 
			{'O', 'O'}, 
			{'O', 'O'}
		};
		color = Color.YELLOW;
    }

    public OBlock() {
		shape = new char[][] { 
			{'O', 'O'}, 
			{'O', 'O'}
		};
		color = Color.YELLOW;
	}
    
}