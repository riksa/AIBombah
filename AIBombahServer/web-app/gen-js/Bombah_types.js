//
// Autogenerated by Thrift Compiler (0.8.0)
//
// DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
//

Direction = {
'N' : 0,
'NE' : 1,
'E' : 2,
'SE' : 3,
'S' : 4,
'SW' : 5,
'W' : 6,
'NW' : 7
};
Tile = {
'NONE' : 0,
'BUFF_BOMB' : 1,
'BUFF_FLAME' : 2,
'BUFF_CHAIN' : 3,
'BUFF_FOOT' : 4,
'DEBUFF' : 5,
'FLAME' : 6,
'INDESTRUCTIBLE' : 7,
'DESTRUCTIBLE' : 8
};
Disease = {
'NONE' : 0,
'FAST_BOMB' : 1,
'SLOW_BOMB' : 2,
'NO_BOMB' : 3,
'FAST' : 4,
'SLOW' : 5
};
Coordinate = function(args) {
  this.x = null;
  this.y = null;
  if (args) {
    if (args.x !== undefined) {
      this.x = args.x;
    }
    if (args.y !== undefined) {
      this.y = args.y;
    }
  }
};
Coordinate.prototype = {};
Coordinate.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.BYTE) {
        this.x = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.BYTE) {
        this.y = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

Coordinate.prototype.write = function(output) {
  output.writeStructBegin('Coordinate');
  if (this.x) {
    output.writeFieldBegin('x', Thrift.Type.BYTE, 1);
    output.writeByte(this.x);
    output.writeFieldEnd();
  }
  if (this.y) {
    output.writeFieldBegin('y', Thrift.Type.BYTE, 2);
    output.writeByte(this.y);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

Position = function(args) {
  this.x = null;
  this.y = null;
  if (args) {
    if (args.x !== undefined) {
      this.x = args.x;
    }
    if (args.y !== undefined) {
      this.y = args.y;
    }
  }
};
Position.prototype = {};
Position.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.DOUBLE) {
        this.x = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.DOUBLE) {
        this.y = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

Position.prototype.write = function(output) {
  output.writeStructBegin('Position');
  if (this.x) {
    output.writeFieldBegin('x', Thrift.Type.DOUBLE, 1);
    output.writeDouble(this.x);
    output.writeFieldEnd();
  }
  if (this.y) {
    output.writeFieldBegin('y', Thrift.Type.DOUBLE, 2);
    output.writeDouble(this.y);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BombState = function(args) {
  this.blastSize = null;
  this.xCoordinate = null;
  this.yCoordinate = null;
  this.ticksRemaining = null;
  this.moving = null;
  this.direction = null;
  this.owner = null;
  if (args) {
    if (args.blastSize !== undefined) {
      this.blastSize = args.blastSize;
    }
    if (args.xCoordinate !== undefined) {
      this.xCoordinate = args.xCoordinate;
    }
    if (args.yCoordinate !== undefined) {
      this.yCoordinate = args.yCoordinate;
    }
    if (args.ticksRemaining !== undefined) {
      this.ticksRemaining = args.ticksRemaining;
    }
    if (args.moving !== undefined) {
      this.moving = args.moving;
    }
    if (args.direction !== undefined) {
      this.direction = args.direction;
    }
    if (args.owner !== undefined) {
      this.owner = args.owner;
    }
  }
};
BombState.prototype = {};
BombState.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.BYTE) {
        this.blastSize = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.DOUBLE) {
        this.xCoordinate = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.DOUBLE) {
        this.yCoordinate = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.I32) {
        this.ticksRemaining = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.BOOL) {
        this.moving = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 6:
      if (ftype == Thrift.Type.I32) {
        this.direction = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 7:
      if (ftype == Thrift.Type.I32) {
        this.owner = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BombState.prototype.write = function(output) {
  output.writeStructBegin('BombState');
  if (this.blastSize) {
    output.writeFieldBegin('blastSize', Thrift.Type.BYTE, 1);
    output.writeByte(this.blastSize);
    output.writeFieldEnd();
  }
  if (this.xCoordinate) {
    output.writeFieldBegin('xCoordinate', Thrift.Type.DOUBLE, 2);
    output.writeDouble(this.xCoordinate);
    output.writeFieldEnd();
  }
  if (this.yCoordinate) {
    output.writeFieldBegin('yCoordinate', Thrift.Type.DOUBLE, 3);
    output.writeDouble(this.yCoordinate);
    output.writeFieldEnd();
  }
  if (this.ticksRemaining) {
    output.writeFieldBegin('ticksRemaining', Thrift.Type.I32, 4);
    output.writeI32(this.ticksRemaining);
    output.writeFieldEnd();
  }
  if (this.moving) {
    output.writeFieldBegin('moving', Thrift.Type.BOOL, 5);
    output.writeBool(this.moving);
    output.writeFieldEnd();
  }
  if (this.direction) {
    output.writeFieldBegin('direction', Thrift.Type.I32, 6);
    output.writeI32(this.direction);
    output.writeFieldEnd();
  }
  if (this.owner) {
    output.writeFieldBegin('owner', Thrift.Type.I32, 7);
    output.writeI32(this.owner);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

FlameState = function(args) {
  this.coordinate = null;
  this.ticksRemaining = null;
  if (args) {
    if (args.coordinate !== undefined) {
      this.coordinate = args.coordinate;
    }
    if (args.ticksRemaining !== undefined) {
      this.ticksRemaining = args.ticksRemaining;
    }
  }
};
FlameState.prototype = {};
FlameState.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.coordinate = new Coordinate();
        this.coordinate.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.I32) {
        this.ticksRemaining = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

FlameState.prototype.write = function(output) {
  output.writeStructBegin('FlameState');
  if (this.coordinate) {
    output.writeFieldBegin('coordinate', Thrift.Type.STRUCT, 1);
    this.coordinate.write(output);
    output.writeFieldEnd();
  }
  if (this.ticksRemaining) {
    output.writeFieldBegin('ticksRemaining', Thrift.Type.I32, 2);
    output.writeI32(this.ticksRemaining);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

GameOverException = function(args) {
};
Thrift.inherits(GameOverException, Thrift.TException);
GameOverException.prototype.name = 'GameOverException';
GameOverException.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    input.skip(ftype);
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

GameOverException.prototype.write = function(output) {
  output.writeStructBegin('GameOverException');
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

YouAreDeadException = function(args) {
};
Thrift.inherits(YouAreDeadException, Thrift.TException);
YouAreDeadException.prototype.name = 'YouAreDeadException';
YouAreDeadException.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    input.skip(ftype);
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

YouAreDeadException.prototype.write = function(output) {
  output.writeStructBegin('YouAreDeadException');
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

TimeoutException = function(args) {
};
Thrift.inherits(TimeoutException, Thrift.TException);
TimeoutException.prototype.name = 'TimeoutException';
TimeoutException.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    input.skip(ftype);
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

TimeoutException.prototype.write = function(output) {
  output.writeStructBegin('TimeoutException');
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

PlayerState = function(args) {
  this.bombSize = null;
  this.bombAmount = null;
  this.foot = null;
  this.chain = null;
  this.disease = null;
  this.alive = null;
  this.x = null;
  this.y = null;
  this.playerId = null;
  if (args) {
    if (args.bombSize !== undefined) {
      this.bombSize = args.bombSize;
    }
    if (args.bombAmount !== undefined) {
      this.bombAmount = args.bombAmount;
    }
    if (args.foot !== undefined) {
      this.foot = args.foot;
    }
    if (args.chain !== undefined) {
      this.chain = args.chain;
    }
    if (args.disease !== undefined) {
      this.disease = args.disease;
    }
    if (args.alive !== undefined) {
      this.alive = args.alive;
    }
    if (args.x !== undefined) {
      this.x = args.x;
    }
    if (args.y !== undefined) {
      this.y = args.y;
    }
    if (args.playerId !== undefined) {
      this.playerId = args.playerId;
    }
  }
};
PlayerState.prototype = {};
PlayerState.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.BYTE) {
        this.bombSize = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.BYTE) {
        this.bombAmount = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.BOOL) {
        this.foot = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.BOOL) {
        this.chain = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.I32) {
        this.disease = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 6:
      if (ftype == Thrift.Type.BOOL) {
        this.alive = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 7:
      if (ftype == Thrift.Type.DOUBLE) {
        this.x = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 8:
      if (ftype == Thrift.Type.DOUBLE) {
        this.y = input.readDouble().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 9:
      if (ftype == Thrift.Type.I32) {
        this.playerId = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

PlayerState.prototype.write = function(output) {
  output.writeStructBegin('PlayerState');
  if (this.bombSize) {
    output.writeFieldBegin('bombSize', Thrift.Type.BYTE, 1);
    output.writeByte(this.bombSize);
    output.writeFieldEnd();
  }
  if (this.bombAmount) {
    output.writeFieldBegin('bombAmount', Thrift.Type.BYTE, 2);
    output.writeByte(this.bombAmount);
    output.writeFieldEnd();
  }
  if (this.foot) {
    output.writeFieldBegin('foot', Thrift.Type.BOOL, 3);
    output.writeBool(this.foot);
    output.writeFieldEnd();
  }
  if (this.chain) {
    output.writeFieldBegin('chain', Thrift.Type.BOOL, 4);
    output.writeBool(this.chain);
    output.writeFieldEnd();
  }
  if (this.disease) {
    output.writeFieldBegin('disease', Thrift.Type.I32, 5);
    output.writeI32(this.disease);
    output.writeFieldEnd();
  }
  if (this.alive) {
    output.writeFieldBegin('alive', Thrift.Type.BOOL, 6);
    output.writeBool(this.alive);
    output.writeFieldEnd();
  }
  if (this.x) {
    output.writeFieldBegin('x', Thrift.Type.DOUBLE, 7);
    output.writeDouble(this.x);
    output.writeFieldEnd();
  }
  if (this.y) {
    output.writeFieldBegin('y', Thrift.Type.DOUBLE, 8);
    output.writeDouble(this.y);
    output.writeFieldEnd();
  }
  if (this.playerId) {
    output.writeFieldBegin('playerId', Thrift.Type.I32, 9);
    output.writeI32(this.playerId);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

MapState = function(args) {
  this.tiles = null;
  this.bombs = null;
  this.players = null;
  this.currentTick = null;
  this.flames = null;
  if (args) {
    if (args.tiles !== undefined) {
      this.tiles = args.tiles;
    }
    if (args.bombs !== undefined) {
      this.bombs = args.bombs;
    }
    if (args.players !== undefined) {
      this.players = args.players;
    }
    if (args.currentTick !== undefined) {
      this.currentTick = args.currentTick;
    }
    if (args.flames !== undefined) {
      this.flames = args.flames;
    }
  }
};
MapState.prototype = {};
MapState.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.LIST) {
        var _size0 = 0;
        var _rtmp34;
        this.tiles = [];
        var _etype3 = 0;
        _rtmp34 = input.readListBegin();
        _etype3 = _rtmp34.etype;
        _size0 = _rtmp34.size;
        for (var _i5 = 0; _i5 < _size0; ++_i5)
        {
          var elem6 = null;
          elem6 = input.readI32().value;
          this.tiles.push(elem6);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.LIST) {
        var _size7 = 0;
        var _rtmp311;
        this.bombs = [];
        var _etype10 = 0;
        _rtmp311 = input.readListBegin();
        _etype10 = _rtmp311.etype;
        _size7 = _rtmp311.size;
        for (var _i12 = 0; _i12 < _size7; ++_i12)
        {
          var elem13 = null;
          elem13 = new BombState();
          elem13.read(input);
          this.bombs.push(elem13);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.LIST) {
        var _size14 = 0;
        var _rtmp318;
        this.players = [];
        var _etype17 = 0;
        _rtmp318 = input.readListBegin();
        _etype17 = _rtmp318.etype;
        _size14 = _rtmp318.size;
        for (var _i19 = 0; _i19 < _size14; ++_i19)
        {
          var elem20 = null;
          elem20 = new PlayerState();
          elem20.read(input);
          this.players.push(elem20);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.I32) {
        this.currentTick = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.LIST) {
        var _size21 = 0;
        var _rtmp325;
        this.flames = [];
        var _etype24 = 0;
        _rtmp325 = input.readListBegin();
        _etype24 = _rtmp325.etype;
        _size21 = _rtmp325.size;
        for (var _i26 = 0; _i26 < _size21; ++_i26)
        {
          var elem27 = null;
          elem27 = new FlameState();
          elem27.read(input);
          this.flames.push(elem27);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

MapState.prototype.write = function(output) {
  output.writeStructBegin('MapState');
  if (this.tiles) {
    output.writeFieldBegin('tiles', Thrift.Type.LIST, 1);
    output.writeListBegin(Thrift.Type.I32, this.tiles.length);
    for (var iter28 in this.tiles)
    {
      if (this.tiles.hasOwnProperty(iter28))
      {
        iter28 = this.tiles[iter28];
        output.writeI32(iter28);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  if (this.bombs) {
    output.writeFieldBegin('bombs', Thrift.Type.LIST, 2);
    output.writeListBegin(Thrift.Type.STRUCT, this.bombs.length);
    for (var iter29 in this.bombs)
    {
      if (this.bombs.hasOwnProperty(iter29))
      {
        iter29 = this.bombs[iter29];
        iter29.write(output);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  if (this.players) {
    output.writeFieldBegin('players', Thrift.Type.LIST, 3);
    output.writeListBegin(Thrift.Type.STRUCT, this.players.length);
    for (var iter30 in this.players)
    {
      if (this.players.hasOwnProperty(iter30))
      {
        iter30 = this.players[iter30];
        iter30.write(output);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  if (this.currentTick) {
    output.writeFieldBegin('currentTick', Thrift.Type.I32, 4);
    output.writeI32(this.currentTick);
    output.writeFieldEnd();
  }
  if (this.flames) {
    output.writeFieldBegin('flames', Thrift.Type.LIST, 5);
    output.writeListBegin(Thrift.Type.STRUCT, this.flames.length);
    for (var iter31 in this.flames)
    {
      if (this.flames.hasOwnProperty(iter31))
      {
        iter31 = this.flames[iter31];
        iter31.write(output);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

GameInfo = function(args) {
  this.mapWidth = null;
  this.mapHeight = null;
  this.tiles = null;
  this.startingPositions = null;
  this.ticksTotal = null;
  this.ticksPerSecond = null;
  this.gameId = null;
  this.playerId = null;
  if (args) {
    if (args.mapWidth !== undefined) {
      this.mapWidth = args.mapWidth;
    }
    if (args.mapHeight !== undefined) {
      this.mapHeight = args.mapHeight;
    }
    if (args.tiles !== undefined) {
      this.tiles = args.tiles;
    }
    if (args.startingPositions !== undefined) {
      this.startingPositions = args.startingPositions;
    }
    if (args.ticksTotal !== undefined) {
      this.ticksTotal = args.ticksTotal;
    }
    if (args.ticksPerSecond !== undefined) {
      this.ticksPerSecond = args.ticksPerSecond;
    }
    if (args.gameId !== undefined) {
      this.gameId = args.gameId;
    }
    if (args.playerId !== undefined) {
      this.playerId = args.playerId;
    }
  }
};
GameInfo.prototype = {};
GameInfo.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.BYTE) {
        this.mapWidth = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.BYTE) {
        this.mapHeight = input.readByte().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.LIST) {
        var _size32 = 0;
        var _rtmp336;
        this.tiles = [];
        var _etype35 = 0;
        _rtmp336 = input.readListBegin();
        _etype35 = _rtmp336.etype;
        _size32 = _rtmp336.size;
        for (var _i37 = 0; _i37 < _size32; ++_i37)
        {
          var elem38 = null;
          elem38 = input.readI32().value;
          this.tiles.push(elem38);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.LIST) {
        var _size39 = 0;
        var _rtmp343;
        this.startingPositions = [];
        var _etype42 = 0;
        _rtmp343 = input.readListBegin();
        _etype42 = _rtmp343.etype;
        _size39 = _rtmp343.size;
        for (var _i44 = 0; _i44 < _size39; ++_i44)
        {
          var elem45 = null;
          elem45 = new Coordinate();
          elem45.read(input);
          this.startingPositions.push(elem45);
        }
        input.readListEnd();
      } else {
        input.skip(ftype);
      }
      break;
      case 5:
      if (ftype == Thrift.Type.I32) {
        this.ticksTotal = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 6:
      if (ftype == Thrift.Type.I32) {
        this.ticksPerSecond = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 7:
      if (ftype == Thrift.Type.I32) {
        this.gameId = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 8:
      if (ftype == Thrift.Type.I32) {
        this.playerId = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

GameInfo.prototype.write = function(output) {
  output.writeStructBegin('GameInfo');
  if (this.mapWidth) {
    output.writeFieldBegin('mapWidth', Thrift.Type.BYTE, 1);
    output.writeByte(this.mapWidth);
    output.writeFieldEnd();
  }
  if (this.mapHeight) {
    output.writeFieldBegin('mapHeight', Thrift.Type.BYTE, 2);
    output.writeByte(this.mapHeight);
    output.writeFieldEnd();
  }
  if (this.tiles) {
    output.writeFieldBegin('tiles', Thrift.Type.LIST, 3);
    output.writeListBegin(Thrift.Type.I32, this.tiles.length);
    for (var iter46 in this.tiles)
    {
      if (this.tiles.hasOwnProperty(iter46))
      {
        iter46 = this.tiles[iter46];
        output.writeI32(iter46);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  if (this.startingPositions) {
    output.writeFieldBegin('startingPositions', Thrift.Type.LIST, 4);
    output.writeListBegin(Thrift.Type.STRUCT, this.startingPositions.length);
    for (var iter47 in this.startingPositions)
    {
      if (this.startingPositions.hasOwnProperty(iter47))
      {
        iter47 = this.startingPositions[iter47];
        iter47.write(output);
      }
    }
    output.writeListEnd();
    output.writeFieldEnd();
  }
  if (this.ticksTotal) {
    output.writeFieldBegin('ticksTotal', Thrift.Type.I32, 5);
    output.writeI32(this.ticksTotal);
    output.writeFieldEnd();
  }
  if (this.ticksPerSecond) {
    output.writeFieldBegin('ticksPerSecond', Thrift.Type.I32, 6);
    output.writeI32(this.ticksPerSecond);
    output.writeFieldEnd();
  }
  if (this.gameId) {
    output.writeFieldBegin('gameId', Thrift.Type.I32, 7);
    output.writeI32(this.gameId);
    output.writeFieldEnd();
  }
  if (this.playerId) {
    output.writeFieldBegin('playerId', Thrift.Type.I32, 8);
    output.writeI32(this.playerId);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

ControllerState = function(args) {
  this.directionPadDown = null;
  this.direction = null;
  this.key1Down = null;
  this.key2Down = null;
  if (args) {
    if (args.directionPadDown !== undefined) {
      this.directionPadDown = args.directionPadDown;
    }
    if (args.direction !== undefined) {
      this.direction = args.direction;
    }
    if (args.key1Down !== undefined) {
      this.key1Down = args.key1Down;
    }
    if (args.key2Down !== undefined) {
      this.key2Down = args.key2Down;
    }
  }
};
ControllerState.prototype = {};
ControllerState.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.BOOL) {
        this.directionPadDown = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.I32) {
        this.direction = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 3:
      if (ftype == Thrift.Type.BOOL) {
        this.key1Down = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 4:
      if (ftype == Thrift.Type.BOOL) {
        this.key2Down = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

ControllerState.prototype.write = function(output) {
  output.writeStructBegin('ControllerState');
  if (this.directionPadDown) {
    output.writeFieldBegin('directionPadDown', Thrift.Type.BOOL, 1);
    output.writeBool(this.directionPadDown);
    output.writeFieldEnd();
  }
  if (this.direction) {
    output.writeFieldBegin('direction', Thrift.Type.I32, 2);
    output.writeI32(this.direction);
    output.writeFieldEnd();
  }
  if (this.key1Down) {
    output.writeFieldBegin('key1Down', Thrift.Type.BOOL, 3);
    output.writeBool(this.key1Down);
    output.writeFieldEnd();
  }
  if (this.key2Down) {
    output.writeFieldBegin('key2Down', Thrift.Type.BOOL, 4);
    output.writeBool(this.key2Down);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

ControllerResult = function(args) {
  this.myState = null;
  this.mapState = null;
  if (args) {
    if (args.myState !== undefined) {
      this.myState = args.myState;
    }
    if (args.mapState !== undefined) {
      this.mapState = args.mapState;
    }
  }
};
ControllerResult.prototype = {};
ControllerResult.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.myState = new PlayerState();
        this.myState.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRUCT) {
        this.mapState = new MapState();
        this.mapState.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

ControllerResult.prototype.write = function(output) {
  output.writeStructBegin('ControllerResult');
  if (this.myState) {
    output.writeFieldBegin('myState', Thrift.Type.STRUCT, 1);
    this.myState.write(output);
    output.writeFieldEnd();
  }
  if (this.mapState) {
    output.writeFieldBegin('mapState', Thrift.Type.STRUCT, 2);
    this.mapState.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

MoveAction = function(args) {
  this.direction = null;
  if (args) {
    if (args.direction !== undefined) {
      this.direction = args.direction;
    }
  }
};
MoveAction.prototype = {};
MoveAction.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.I32) {
        this.direction = input.readI32().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

MoveAction.prototype.write = function(output) {
  output.writeStructBegin('MoveAction');
  if (this.direction) {
    output.writeFieldBegin('direction', Thrift.Type.I32, 1);
    output.writeI32(this.direction);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

MoveActionResult = function(args) {
  this.myState = null;
  this.mapState = null;
  if (args) {
    if (args.myState !== undefined) {
      this.myState = args.myState;
    }
    if (args.mapState !== undefined) {
      this.mapState = args.mapState;
    }
  }
};
MoveActionResult.prototype = {};
MoveActionResult.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.myState = new PlayerState();
        this.myState.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRUCT) {
        this.mapState = new MapState();
        this.mapState.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

MoveActionResult.prototype.write = function(output) {
  output.writeStructBegin('MoveActionResult');
  if (this.myState) {
    output.writeFieldBegin('myState', Thrift.Type.STRUCT, 1);
    this.myState.write(output);
    output.writeFieldEnd();
  }
  if (this.mapState) {
    output.writeFieldBegin('mapState', Thrift.Type.STRUCT, 2);
    this.mapState.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BombAction = function(args) {
  this.chainBombs = null;
  if (args) {
    if (args.chainBombs !== undefined) {
      this.chainBombs = args.chainBombs;
    }
  }
};
BombAction.prototype = {};
BombAction.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.BOOL) {
        this.chainBombs = input.readBool().value;
      } else {
        input.skip(ftype);
      }
      break;
      case 0:
        input.skip(ftype);
        break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BombAction.prototype.write = function(output) {
  output.writeStructBegin('BombAction');
  if (this.chainBombs) {
    output.writeFieldBegin('chainBombs', Thrift.Type.BOOL, 1);
    output.writeBool(this.chainBombs);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

BombActionResult = function(args) {
  this.myState = null;
  this.mapState = null;
  if (args) {
    if (args.myState !== undefined) {
      this.myState = args.myState;
    }
    if (args.mapState !== undefined) {
      this.mapState = args.mapState;
    }
  }
};
BombActionResult.prototype = {};
BombActionResult.prototype.read = function(input) {
  input.readStructBegin();
  while (true)
  {
    var ret = input.readFieldBegin();
    var fname = ret.fname;
    var ftype = ret.ftype;
    var fid = ret.fid;
    if (ftype == Thrift.Type.STOP) {
      break;
    }
    switch (fid)
    {
      case 1:
      if (ftype == Thrift.Type.STRUCT) {
        this.myState = new PlayerState();
        this.myState.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      case 2:
      if (ftype == Thrift.Type.STRUCT) {
        this.mapState = new MapState();
        this.mapState.read(input);
      } else {
        input.skip(ftype);
      }
      break;
      default:
        input.skip(ftype);
    }
    input.readFieldEnd();
  }
  input.readStructEnd();
  return;
};

BombActionResult.prototype.write = function(output) {
  output.writeStructBegin('BombActionResult');
  if (this.myState) {
    output.writeFieldBegin('myState', Thrift.Type.STRUCT, 1);
    this.myState.write(output);
    output.writeFieldEnd();
  }
  if (this.mapState) {
    output.writeFieldBegin('mapState', Thrift.Type.STRUCT, 2);
    this.mapState.write(output);
    output.writeFieldEnd();
  }
  output.writeFieldStop();
  output.writeStructEnd();
  return;
};

TICKS_PER_SECOND = 50;
TICKS_PER_TILE = 12;
TICKS_BOMB = 150;
TICKS_FLAME = 50;
