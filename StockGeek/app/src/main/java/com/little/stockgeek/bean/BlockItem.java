package com.little.stockgeek.bean;

/**
 * Created by tusm on 15/8/2.
 */
public class BlockItem {

    String blockName;
    String blockRange; //
    String blockSumHand;
    String blockSumPrice;

    public BlockItem( String blockName,String blockRange,String blockSumHand, String blockSumPrice) {
        this.blockName = blockName;
        this.blockRange = blockRange + "%";
        this.blockSumHand = blockSumHand;
        this.blockSumPrice = blockSumPrice;
    }


    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getBlockRange() {
        return blockRange;
    }

    public void setBlockRange(String blockRange) {
        this.blockRange = blockRange;
    }

    public String getBlockSumHand() {
        return blockSumHand;
    }

    public void setBlockSumHand(String blockSumHand) {
        this.blockSumHand = blockSumHand;
    }

    public String getBlockSumPrice() {
        return blockSumPrice;
    }

    public void setBlockSumPrice(String blockSumPrice) {
        this.blockSumPrice = blockSumPrice;
    }


}
