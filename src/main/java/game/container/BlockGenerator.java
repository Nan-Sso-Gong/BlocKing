package game.container;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import game.model.BlockController;
import game.model.BlockModel;
import game.model.blocktypes.IBlock;
import game.model.blocktypes.JBlock;
import game.model.blocktypes.LBlock;
import game.model.blocktypes.OBlock;
import game.model.blocktypes.SBlock;
import game.model.blocktypes.TBlock;
import game.model.blocktypes.ZBlock;

import game.manager.BoardManager;
import game.manager.gametype.GameManager_NormalMode;

public class BlockGenerator {

	public Queue<BlockController> blockQueue = new LinkedList<>(); //앞으로 생성할 블록들

    private static BlockGenerator instance = new BlockGenerator();
    
    public static BlockGenerator getInstance() {
        return instance;
    }

	public static int block;

	public int getBlock(){
		return block;
	}


    
    public BlockController getRandomBlock() {
		Random rnd = new Random(System.currentTimeMillis());
		 block = rnd.nextInt(7);
		switch(block) {
		case 0:
			return new IBlock();
		case 1:
			return new JBlock();
		case 2:
			return new LBlock();
		case 3:
			return new ZBlock();
		case 4:
			return new SBlock();
		case 5:
			return new TBlock();
		case 6:
			return new OBlock();			
		}
		return new LBlock();
	}

    private void initNewBlockPos(BlockController curBlock, int row, int col)
    {
        BoardManager.getInstance().setBlockPos(curBlock, row, col);
    }


    public void addBlock() {
		BlockController newBlock = getRandomBlock();
		blockQueue.add(newBlock);
	}



	public BlockController createBlock() {
		BlockController curBlock = blockQueue.poll();
		initNewBlockPos(curBlock, 0, 5);
		GameManager_NormalMode.getInstance().curBlock = curBlock;
		return curBlock;
	}

	public void initBlockQueue() {
		addBlock();
		addBlock();
	}

	public Queue<BlockController> getBlockQueue() {
		return blockQueue;
	}
}
