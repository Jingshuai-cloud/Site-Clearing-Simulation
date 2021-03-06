package Object;

import java.util.ArrayList;
import java.util.HashMap;

public class Bulldozer {
   private Site site;
   private int protectedTree;
   private int paintDamage;
   private ArrayList<Character> historyPath = new ArrayList();
   private HashMap<String,String> position = new HashMap<>();

    //initialize the bulldozer position
    public Bulldozer(){
        position.put("X", "-1");
        position.put("Y", "0");
        position.put("facing","EAST");
        protectedTree = 0;
        paintDamage = 0;
    }

    public boolean advance(int step){
        //get [1,0] when facing east...then caculate the next coordinate
        Integer[] caculateNextPosition = getNextPositionCaculateIndex();
        int newX = 0, newY = 0;

        for(int i =0; i< step; i++){
            //recaculate every step of advance
            newX = Integer.parseInt(this.position.get("X")) + caculateNextPosition[0];
            newY = Integer.parseInt(this.position.get("Y")) + caculateNextPosition[1];
            //get position status from site
            String positionStatus = site.getPositionStatus(newX, newY);
            switch(positionStatus){
                case "OUT_OF_BORDER":
                    System.out.println("Bulldozer is out of the site!");
                    return false;

                case "PROTECTED_TREE":
                    protectedTree++;
                    System.out.println("Bulldozer come cross a protected tree!");
                    return false;

                case "VALID":
                    moveToNextStep(newX, newY, i, step);
                   // System.out.println(this.position);
                    break;

                default:
                    System.out.println("Position Status has something wrong!");
            }

        }
        return true;
    }


    //define how to caculate next position according to current facing
    public Integer[] getNextPositionCaculateIndex(){
        HashMap<String, Integer[]> nextBlock = new HashMap<>();
        nextBlock.put("NORTH", new Integer[]{0, -1});
        nextBlock.put("SOUTH", new Integer[]{0, 1});
        nextBlock.put("WEST", new Integer[]{-1, 0});
        nextBlock.put("EAST", new Integer[]{1, 0});

        String facing = this.position.get("facing");
        Integer[] nextPositionIndex = nextBlock.get(facing);
        return nextPositionIndex;
    }

    //when position is valid, change the position
    public void moveToNextStep(int newX, int newY, int i, int step){
        char land = site.getLand(newX, newY);
        if(land == 't' && i != step - 1){
            paintDamage ++;
        }
        historyPath.add(land);
        position.replace("X", String.valueOf(newX));
        position.replace("Y",String.valueOf(newY));
        //clear the site to C
        site.clearSite(newX,newY);
    }

    public void turn (String command){
        HashMap<String, Integer> turnIndex = new HashMap<>();
        turnIndex.put("L",0);
        turnIndex.put("R",1);
        int direction = turnIndex.get(command);

        HashMap<String, String[]> nextFacing = new HashMap<>();
        nextFacing.put("NORTH", new String[]{"WEST", "EAST"});
        nextFacing.put("EAST", new String[]{"NORTH", "SOUTH"});
        nextFacing.put("SOUTH", new String[]{"EAST", "WEST"});
        nextFacing.put("WEST", new String[]{"SOUTH", "NORTH"});

        String facing = this.position.get("facing");
        String newFacing = nextFacing.get(facing)[direction];

        this.position.replace("facing", newFacing);
    }

    public void setSite(Site site){
        this.site = site;
    }

    public void setHistoryPath(ArrayList<Character> historyPath) {
        this.historyPath = historyPath;
    }

    public Site getSite(){
        return site;
    }

    public void setProtectedTree(int protectedTree) {
        this.protectedTree = protectedTree;
    }

    public void setPaintDamage(int paintDamage) {
        this.paintDamage = paintDamage;
    }

    public int getProtectedTree(){
        return protectedTree;
    }

    public int getPaintDamage(){
        return paintDamage;
    }

    public ArrayList<Character> getHistoryPath(){
        return historyPath;
    }

    public HashMap<String,String> getPosition(){
        return position;
    }



}


