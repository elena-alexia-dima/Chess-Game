package chess.model;

import chess.exceptions.InvalidCommandException;

public class Position implements Comparable<Position>{
    private char x;
    private int y;

    public Position(char x, int y) {
        if(x < 'A' || x > 'H'){
            throw new InvalidCommandException("Alege o coloana intre A si H");
        }
        if(y < 1 || y > 8) {
            throw new InvalidCommandException("Alege o linie intre 1 si 8");
        }
        this.x = x;
        this.y = y;
    }

    public char getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean equals(Object o){
        if(o == this) {
            return true;
        }
        if(o== null || !(o instanceof Position)) {
            return false;
        }
        Position p = (Position) o;
        return x == p.x && y == p.y;

    }

    public int compareTo(Position p) {
        if(this.y != p.y) {
            return Integer.compare(this.y, p.y);
        }
        return Character.compare(this.x, p.x);
    }

    @Override
    public String toString() {
        return "" + x + y;
    }
}
