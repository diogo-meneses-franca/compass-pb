import boardgame.Board;
import boardgame.Position;

public class Main {
    public static void main(String[] args) {
        Position pos = new Position(3, 5);
        System.out.println(pos);

        Board board = new Board(8,8);
    }
}