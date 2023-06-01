/*
 * Marek Wiśniewski 338782
 * Board - klasa planszy
 * 31.05.2023
 */
package scrabble.pure;


public class Board {
    Tile[][] tiles;
    Hand discard;
    private void paintTiles(int m,boolean w,int[][] xy)
    {
        for(int[] i : xy)
        {
            tiles[i[0]][i[1]].setMultiplier(m,w);
        }
    }
    public Board() {
        tiles = new Tile[15][15];
        for(int i = 0; i < 15*15; i++)
        {
            tiles[i%15][i/15] = new Tile();
        }
        discard = new Hand();
        
        paintTiles(3,true,new int[][]{
            {0,0},{0,7},{0,14},{7,14},{14,14},{14,7},{14,0},{7,0}});
        paintTiles(2,true,new int[][]{
            {7,7},
            {1,1},{2,2},{3,3},{4,4},
            {1,13},{2,12},{3,11},{4,10},
            {10,10},{11,11},{12,12},{13,13},
            {13,1},{12,2},{11,3},{10,4}});
        paintTiles(3,false,new int[][]{
            {5,1},{9,1},{13,5},{13,9},{9,13},{5,13},{1,9},{1,5},
            {5,5},{5,9},{9,5},{9,9}});
        paintTiles(2, false,new int[][]{
            {3,0},{11,0},{14,3},{14,11},{11,14},{3,14},{0,11},{0,3},
            {6,2},{7,3},{8,2},
            {12,6},{11,7},{12,8},
            {8,12},{7,11},{6,12},
            {2,8},{3,7},{2,6},
            {6,6},{8,6},{8,8},{6,8}});
    }
    public Tile getTile(int x,int y)
    {
        if(x < 0 || y < 0 || x >= 15 || y >= 15)
        return null;
        return tiles[x][y];
    }
    public Tile getTile(int x,int y,Boolean reverse)
    {
        if(reverse == null || reverse) return getTile(y,x);
        else return getTile(x,y);
    }
    public Hand getDiscard()
    {
        return discard;
    }
}
