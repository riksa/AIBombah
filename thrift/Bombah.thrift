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
    I_GREY = 0, // Indestructible grey brick
    D_GREY = 1, // Destructible grey brick
    // TODO:
}

struct TileState {
    1: Tile tile
    2: byte xCoordinate,
    3: byte yCoordinate
}

struct BombState {
    1: byte blastSize,
    2: byte xCoordinate,
    3: byte yCoordinate,
    4: byte ticksRemaining
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

struct PlayerState {
    1:i32 bomb_size, // just the buffs, actual size might be different because of diseases
    2:i32 bomb_amount,
    3:bool foot,
    4:bool chain,
    5:Disease disease,
    6:bool alive,
    7:double xCoordinate,
    8:double yCoordinate
}

struct MapState {
    1: set<TileState> indestructibleTiles,
    2: set<TileState> destructibleTiles,
    3: set<BombState> bombs,
    4: list<PlayerState> players,
    5: i32 ticksRemaining,
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

}