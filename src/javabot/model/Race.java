package javabot.model;

/**
 * User: PC
 * Date: 2.12.2012
 * Time: 13:45
 */
public enum  Race {
    ZERG(0), TERRAN(1), PROTOSS(2);
    private int ID;

    Race(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public static Race fromID(int id){
        switch (id){
            case 0: return ZERG;
            case 1:return TERRAN;
            case 2: return PROTOSS;
        }
        return null;
    }
}
