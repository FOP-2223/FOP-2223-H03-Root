package h03;

import fopbot.Direction;

class CallToTutorRobotConstructorIntIntDirectionInt {
    private final int x;
    private final int y;
    private final Direction direction;
    private final int numberOfCoins;

    CallToTutorRobotConstructorIntIntDirectionInt(int x, int y, Direction direction, int numberOfCoins) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.numberOfCoins = numberOfCoins;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getNumberOfCoins() {
        return numberOfCoins;
    }
}
