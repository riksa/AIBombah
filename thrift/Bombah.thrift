namespace java org.riksa.bombah.thrift

/**
 * 50 ticks per second at normal rate
 */
const i32 TICKS_PER_SECOND = 50
/**
 * movement takes 12 ticks / tile
 */
const i32 TICKS_PER_TILE   = 20
/**
 * normal bomb explodes in 150 ticks (3s)
 * fast bomb in half that time
 * slow bomb takes double that
 */
const i32 TICKS_BOMB      = 150

/**
 * if bomb is in flames, it will explode in 2 ticks
 */
const i32 TICKS_BOMB_IN_FLAMES = 2

/**
 * flame is alive for 50 ticks
 */
const i32 TICKS_FLAME     = 50

/**
 * Length of a fresh infection
 */
const i32 TICKS_DISEASE   = 1000


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

struct Coordinate {
    1: byte x,
    2: byte y
}

struct Position {
	1: double x,
	2: double y
}

enum Tile {
    NONE = 0,
    BUFF_BOMB = 1,
    BUFF_FLAME = 2,
    BUFF_CHAIN = 3,
    BUFF_FOOT = 4,
    DEBUFF = 5,
	FIRE = 6, // does not appear in MapState.tiles list at the moment
	
    INDESTRUCTIBLE = 7, // Indestructible grey brick
    DESTRUCTIBLE = 8, // Destructible grey brick
	BOMB = 9 // does not appear in MapState.tiles list at the moment

    // TODO:
}

struct BombState {
    1: byte blastSize,
    2: double xCoordinate,
    3: double yCoordinate,
    4: i32 ticksRemaining,
    5: bool moving,
    6: Direction direction,
    7: i32 owner
}

struct FlameState {
    1: Coordinate coordinate,
    2: i32 ticksRemaining,
    3: bool burningBlock
}

enum Disease {
    NONE = 0,
    FAST_BOMB = 1,
    SLOW_BOMB = 2,
    NO_BOMB = 3,
    FAST = 4,
    SLOW = 5
	DIARRHEA = 6,
//    REVERSE_DIR = 7  // not needed for AIs, just complicating implementation
}

exception GameOverException {

}

exception YouAreDeadException {

}

struct PlayerState {
    1:byte bombSize, // just the buffs, actual size might be different because of diseases
    2:byte bombAmount,
    3:bool foot,
    4:bool chain,
    5:Disease disease,
    6:bool alive,
    7:double x,
    8:double y,
    9:i32 playerId,
    10:i32 diseaseTicks
}

struct MapState {
    1: list<Tile> tiles,
    2: list<BombState> bombs,
    3: list<PlayerState> players,
    4: i32 currentTick,
    5: list<FlameState> flames,
}

struct GameInfo {
    1: byte mapWidth,
    2: byte mapHeight,
    3: list<Tile> tiles,
    4: list<Coordinate> startingPositions,
    5: i32 ticksTotal,
    6: i32 ticksPerSecond,
	7: i32 gameId,
	8: i32 playerId
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
	ControllerResult controllerEvent(1: i32 playerId, 2:ControllerState controllerState) throws (1: YouAreDeadException youAreDead, 2: GameOverException gameOver);
	MoveActionResult move(1: i32 playerId, 2: MoveAction moveAction ) throws (1: YouAreDeadException youAreDead, 2: GameOverException gameOver);
	BombActionResult bomb(1: i32 playerId, 2: BombAction bombAction) throws (1: YouAreDeadException youAreDead, 2: GameOverException gameOver);
//	ChainActionResult chainActions(1: i32 playerId, 2: ChainAction chainAction) throws (1: YouAreDeadException youAreDead, 2: GameOverException gameOver);

// GameId not used yet, for future use. Just use -1...
    MapState waitTicks( 1:i32 gameId, 2: i32 ticks ) throws (1: YouAreDeadException youAreDead, 2: GameOverException gameOver);
    MapState waitForTick( 1:i32 gameId, 2: i32 tick ) throws (1: YouAreDeadException youAreDead, 2: GameOverException gameOver);
    GameInfo joinGame( 1:i32 gameId) throws (1:GameOverException gameOverException);
	GameInfo getGameInfo(1:i32 gameId) throws (1:GameOverException gameOverException);
	void debugResetGame(1:i32 gameId);

    void waitForStart(1:i32 gameId) throws (1:GameOverException gameOverException);

	MapState getMapState(1:i32 gameId) throws (1: GameOverException gameOver);

}
