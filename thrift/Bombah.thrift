namespace java org.riksa.bombah.thrift

enum Direction {
    N  = 0,
    NE = 1,
    E  = 2,
    SE = 3,
    S  = 4,
    SW = 5,
    W  = 6,
    NW = 7
}

enum Tile {
    NONE = 0,
    BUFF_BOMB = 1,
    BUFF_FLAME = 2,
    BUFF_CHAIN = 3,
    BUFF_FOOT = 4,
    DEBUFF = 5,
    FIRE = 6,

    I_GREY = 7, // Indestructible grey brick
    D_GREY = 8, // Destructible grey brick

    // TODO:
}

struct BombState {
    1: byte blastSize,
    2: double xCoordinate,
    3: double yCoordinate,
    4: byte ticksRemaining,
    5: bool moving,
    6: Direction direction
}

enum Disease {
    NONE = 0,
    FAST_BOMB = 1,
    SLOW_BOMB = 2,
    NO_BOMB = 3,
    FAST = 4,
    SLOW = 5
//    REVERSE_DIR = 6  // not needed for AIs, just complicating implementation
}

exception GameOverException {

}

exception YouAreDeadException {

}

exception TimeoutException {

}

struct PlayerState {
    1:byte bomb_size, // just the buffs, actual size might be different because of diseases
    2:byte bomb_amount,
    3:bool foot,
    4:bool chain,
    5:Disease disease,
    6:bool alive,
    7:double x,
    8:double y
}

struct MapState {
    1: list<Tile> tiles,
    2: list<BombState> bombs,
    3: list<PlayerState> players,
    4: i32 ticksRemaining,
}

struct Coordinate {
    1: byte x,
    2: byte y
}

struct GameInfo {
    1: byte mapWidth,
    2: byte mapHeight,
    3: byte playerIndex,
    4: list<Coordinate> startingPositions,
    5: i32 ticksTotal,
    6: i32 ticksPerSecond
}

/**
  * Fine grained controller
  */
struct ControllerState {
    1: bool directionPadDown, // Direction pad pressed to direction
    2: Direction direction,
    3: bool key1Down, // Drop bomb pressed
    4: bool key2Down  // Stop bomb pressed
}

struct ControllerResult {
    1: PlayerState myState,
    2: MapState mapState
}

/**
  * Simpler movement
  */
struct MoveAction {
    1: Direction direction
}

struct MoveActionResult {
    1: PlayerState myState,
    2: MapState mapState
}

struct BombAction {
    1: bool chainBombs
}

struct BombActionResult {
    1: PlayerState myState,
    2: MapState mapState
}

service BombahService {
	string ping()

	/**
	 * Event from controller
	 */
	ControllerResult controllerEvent(1:ControllerState controllerState) throws (1: YouAreDeadException youAreDead, 2: GameOverException gameOver);
	MoveActionResult move(1: MoveAction moveAction ) throws (1: YouAreDeadException youAreDead, 2: GameOverException gameOver);
	BombActionResult bomb(1: BombAction bombAction) throws (1: YouAreDeadException youAreDead, 2: GameOverException gameOver);
//	ChainActionResult chainActions(1: ChainAction chainAction) throws (1: YouAreDeadException youAreDead, 2: GameOverException gameOver);

    GameInfo joinGame() throws (1:TimeoutException timeOutException);
    void waitForStart() throws (1:TimeoutException timeOutException);

	MapState getMapState();

}