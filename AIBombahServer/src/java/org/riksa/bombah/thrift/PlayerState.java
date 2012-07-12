/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.riksa.bombah.thrift;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerState implements org.apache.thrift.TBase<PlayerState, PlayerState._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PlayerState");

  private static final org.apache.thrift.protocol.TField BOMB_SIZE_FIELD_DESC = new org.apache.thrift.protocol.TField("bombSize", org.apache.thrift.protocol.TType.BYTE, (short)1);
  private static final org.apache.thrift.protocol.TField BOMB_AMOUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("bombAmount", org.apache.thrift.protocol.TType.BYTE, (short)2);
  private static final org.apache.thrift.protocol.TField FOOT_FIELD_DESC = new org.apache.thrift.protocol.TField("foot", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField CHAIN_FIELD_DESC = new org.apache.thrift.protocol.TField("chain", org.apache.thrift.protocol.TType.BOOL, (short)4);
  private static final org.apache.thrift.protocol.TField DISEASE_FIELD_DESC = new org.apache.thrift.protocol.TField("disease", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField ALIVE_FIELD_DESC = new org.apache.thrift.protocol.TField("alive", org.apache.thrift.protocol.TType.BOOL, (short)6);
  private static final org.apache.thrift.protocol.TField X_FIELD_DESC = new org.apache.thrift.protocol.TField("x", org.apache.thrift.protocol.TType.DOUBLE, (short)7);
  private static final org.apache.thrift.protocol.TField Y_FIELD_DESC = new org.apache.thrift.protocol.TField("y", org.apache.thrift.protocol.TType.DOUBLE, (short)8);
  private static final org.apache.thrift.protocol.TField PLAYER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("playerId", org.apache.thrift.protocol.TType.I32, (short)9);
  private static final org.apache.thrift.protocol.TField DISEASE_TICKS_FIELD_DESC = new org.apache.thrift.protocol.TField("diseaseTicks", org.apache.thrift.protocol.TType.I32, (short)10);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PlayerStateStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PlayerStateTupleSchemeFactory());
  }

  public byte bombSize; // required
  public byte bombAmount; // required
  public boolean foot; // required
  public boolean chain; // required
  /**
   * 
   * @see Disease
   */
  public Disease disease; // required
  public boolean alive; // required
  public double x; // required
  public double y; // required
  public int playerId; // required
  public int diseaseTicks; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BOMB_SIZE((short)1, "bombSize"),
    BOMB_AMOUNT((short)2, "bombAmount"),
    FOOT((short)3, "foot"),
    CHAIN((short)4, "chain"),
    /**
     * 
     * @see Disease
     */
    DISEASE((short)5, "disease"),
    ALIVE((short)6, "alive"),
    X((short)7, "x"),
    Y((short)8, "y"),
    PLAYER_ID((short)9, "playerId"),
    DISEASE_TICKS((short)10, "diseaseTicks");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // BOMB_SIZE
          return BOMB_SIZE;
        case 2: // BOMB_AMOUNT
          return BOMB_AMOUNT;
        case 3: // FOOT
          return FOOT;
        case 4: // CHAIN
          return CHAIN;
        case 5: // DISEASE
          return DISEASE;
        case 6: // ALIVE
          return ALIVE;
        case 7: // X
          return X;
        case 8: // Y
          return Y;
        case 9: // PLAYER_ID
          return PLAYER_ID;
        case 10: // DISEASE_TICKS
          return DISEASE_TICKS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __BOMBSIZE_ISSET_ID = 0;
  private static final int __BOMBAMOUNT_ISSET_ID = 1;
  private static final int __FOOT_ISSET_ID = 2;
  private static final int __CHAIN_ISSET_ID = 3;
  private static final int __ALIVE_ISSET_ID = 4;
  private static final int __X_ISSET_ID = 5;
  private static final int __Y_ISSET_ID = 6;
  private static final int __PLAYERID_ISSET_ID = 7;
  private static final int __DISEASETICKS_ISSET_ID = 8;
  private BitSet __isset_bit_vector = new BitSet(9);
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BOMB_SIZE, new org.apache.thrift.meta_data.FieldMetaData("bombSize", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.BOMB_AMOUNT, new org.apache.thrift.meta_data.FieldMetaData("bombAmount", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BYTE)));
    tmpMap.put(_Fields.FOOT, new org.apache.thrift.meta_data.FieldMetaData("foot", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.CHAIN, new org.apache.thrift.meta_data.FieldMetaData("chain", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.DISEASE, new org.apache.thrift.meta_data.FieldMetaData("disease", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, Disease.class)));
    tmpMap.put(_Fields.ALIVE, new org.apache.thrift.meta_data.FieldMetaData("alive", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.X, new org.apache.thrift.meta_data.FieldMetaData("x", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.Y, new org.apache.thrift.meta_data.FieldMetaData("y", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.PLAYER_ID, new org.apache.thrift.meta_data.FieldMetaData("playerId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DISEASE_TICKS, new org.apache.thrift.meta_data.FieldMetaData("diseaseTicks", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PlayerState.class, metaDataMap);
  }

  public PlayerState() {
  }

  public PlayerState(
    byte bombSize,
    byte bombAmount,
    boolean foot,
    boolean chain,
    Disease disease,
    boolean alive,
    double x,
    double y,
    int playerId,
    int diseaseTicks)
  {
    this();
    this.bombSize = bombSize;
    setBombSizeIsSet(true);
    this.bombAmount = bombAmount;
    setBombAmountIsSet(true);
    this.foot = foot;
    setFootIsSet(true);
    this.chain = chain;
    setChainIsSet(true);
    this.disease = disease;
    this.alive = alive;
    setAliveIsSet(true);
    this.x = x;
    setXIsSet(true);
    this.y = y;
    setYIsSet(true);
    this.playerId = playerId;
    setPlayerIdIsSet(true);
    this.diseaseTicks = diseaseTicks;
    setDiseaseTicksIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PlayerState(PlayerState other) {
    __isset_bit_vector.clear();
    __isset_bit_vector.or(other.__isset_bit_vector);
    this.bombSize = other.bombSize;
    this.bombAmount = other.bombAmount;
    this.foot = other.foot;
    this.chain = other.chain;
    if (other.isSetDisease()) {
      this.disease = other.disease;
    }
    this.alive = other.alive;
    this.x = other.x;
    this.y = other.y;
    this.playerId = other.playerId;
    this.diseaseTicks = other.diseaseTicks;
  }

  public PlayerState deepCopy() {
    return new PlayerState(this);
  }

  @Override
  public void clear() {
    setBombSizeIsSet(false);
    this.bombSize = 0;
    setBombAmountIsSet(false);
    this.bombAmount = 0;
    setFootIsSet(false);
    this.foot = false;
    setChainIsSet(false);
    this.chain = false;
    this.disease = null;
    setAliveIsSet(false);
    this.alive = false;
    setXIsSet(false);
    this.x = 0.0;
    setYIsSet(false);
    this.y = 0.0;
    setPlayerIdIsSet(false);
    this.playerId = 0;
    setDiseaseTicksIsSet(false);
    this.diseaseTicks = 0;
  }

  public byte getBombSize() {
    return this.bombSize;
  }

  public PlayerState setBombSize(byte bombSize) {
    this.bombSize = bombSize;
    setBombSizeIsSet(true);
    return this;
  }

  public void unsetBombSize() {
    __isset_bit_vector.clear(__BOMBSIZE_ISSET_ID);
  }

  /** Returns true if field bombSize is set (has been assigned a value) and false otherwise */
  public boolean isSetBombSize() {
    return __isset_bit_vector.get(__BOMBSIZE_ISSET_ID);
  }

  public void setBombSizeIsSet(boolean value) {
    __isset_bit_vector.set(__BOMBSIZE_ISSET_ID, value);
  }

  public byte getBombAmount() {
    return this.bombAmount;
  }

  public PlayerState setBombAmount(byte bombAmount) {
    this.bombAmount = bombAmount;
    setBombAmountIsSet(true);
    return this;
  }

  public void unsetBombAmount() {
    __isset_bit_vector.clear(__BOMBAMOUNT_ISSET_ID);
  }

  /** Returns true if field bombAmount is set (has been assigned a value) and false otherwise */
  public boolean isSetBombAmount() {
    return __isset_bit_vector.get(__BOMBAMOUNT_ISSET_ID);
  }

  public void setBombAmountIsSet(boolean value) {
    __isset_bit_vector.set(__BOMBAMOUNT_ISSET_ID, value);
  }

  public boolean isFoot() {
    return this.foot;
  }

  public PlayerState setFoot(boolean foot) {
    this.foot = foot;
    setFootIsSet(true);
    return this;
  }

  public void unsetFoot() {
    __isset_bit_vector.clear(__FOOT_ISSET_ID);
  }

  /** Returns true if field foot is set (has been assigned a value) and false otherwise */
  public boolean isSetFoot() {
    return __isset_bit_vector.get(__FOOT_ISSET_ID);
  }

  public void setFootIsSet(boolean value) {
    __isset_bit_vector.set(__FOOT_ISSET_ID, value);
  }

  public boolean isChain() {
    return this.chain;
  }

  public PlayerState setChain(boolean chain) {
    this.chain = chain;
    setChainIsSet(true);
    return this;
  }

  public void unsetChain() {
    __isset_bit_vector.clear(__CHAIN_ISSET_ID);
  }

  /** Returns true if field chain is set (has been assigned a value) and false otherwise */
  public boolean isSetChain() {
    return __isset_bit_vector.get(__CHAIN_ISSET_ID);
  }

  public void setChainIsSet(boolean value) {
    __isset_bit_vector.set(__CHAIN_ISSET_ID, value);
  }

  /**
   * 
   * @see Disease
   */
  public Disease getDisease() {
    return this.disease;
  }

  /**
   * 
   * @see Disease
   */
  public PlayerState setDisease(Disease disease) {
    this.disease = disease;
    return this;
  }

  public void unsetDisease() {
    this.disease = null;
  }

  /** Returns true if field disease is set (has been assigned a value) and false otherwise */
  public boolean isSetDisease() {
    return this.disease != null;
  }

  public void setDiseaseIsSet(boolean value) {
    if (!value) {
      this.disease = null;
    }
  }

  public boolean isAlive() {
    return this.alive;
  }

  public PlayerState setAlive(boolean alive) {
    this.alive = alive;
    setAliveIsSet(true);
    return this;
  }

  public void unsetAlive() {
    __isset_bit_vector.clear(__ALIVE_ISSET_ID);
  }

  /** Returns true if field alive is set (has been assigned a value) and false otherwise */
  public boolean isSetAlive() {
    return __isset_bit_vector.get(__ALIVE_ISSET_ID);
  }

  public void setAliveIsSet(boolean value) {
    __isset_bit_vector.set(__ALIVE_ISSET_ID, value);
  }

  public double getX() {
    return this.x;
  }

  public PlayerState setX(double x) {
    this.x = x;
    setXIsSet(true);
    return this;
  }

  public void unsetX() {
    __isset_bit_vector.clear(__X_ISSET_ID);
  }

  /** Returns true if field x is set (has been assigned a value) and false otherwise */
  public boolean isSetX() {
    return __isset_bit_vector.get(__X_ISSET_ID);
  }

  public void setXIsSet(boolean value) {
    __isset_bit_vector.set(__X_ISSET_ID, value);
  }

  public double getY() {
    return this.y;
  }

  public PlayerState setY(double y) {
    this.y = y;
    setYIsSet(true);
    return this;
  }

  public void unsetY() {
    __isset_bit_vector.clear(__Y_ISSET_ID);
  }

  /** Returns true if field y is set (has been assigned a value) and false otherwise */
  public boolean isSetY() {
    return __isset_bit_vector.get(__Y_ISSET_ID);
  }

  public void setYIsSet(boolean value) {
    __isset_bit_vector.set(__Y_ISSET_ID, value);
  }

  public int getPlayerId() {
    return this.playerId;
  }

  public PlayerState setPlayerId(int playerId) {
    this.playerId = playerId;
    setPlayerIdIsSet(true);
    return this;
  }

  public void unsetPlayerId() {
    __isset_bit_vector.clear(__PLAYERID_ISSET_ID);
  }

  /** Returns true if field playerId is set (has been assigned a value) and false otherwise */
  public boolean isSetPlayerId() {
    return __isset_bit_vector.get(__PLAYERID_ISSET_ID);
  }

  public void setPlayerIdIsSet(boolean value) {
    __isset_bit_vector.set(__PLAYERID_ISSET_ID, value);
  }

  public int getDiseaseTicks() {
    return this.diseaseTicks;
  }

  public PlayerState setDiseaseTicks(int diseaseTicks) {
    this.diseaseTicks = diseaseTicks;
    setDiseaseTicksIsSet(true);
    return this;
  }

  public void unsetDiseaseTicks() {
    __isset_bit_vector.clear(__DISEASETICKS_ISSET_ID);
  }

  /** Returns true if field diseaseTicks is set (has been assigned a value) and false otherwise */
  public boolean isSetDiseaseTicks() {
    return __isset_bit_vector.get(__DISEASETICKS_ISSET_ID);
  }

  public void setDiseaseTicksIsSet(boolean value) {
    __isset_bit_vector.set(__DISEASETICKS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case BOMB_SIZE:
      if (value == null) {
        unsetBombSize();
      } else {
        setBombSize((Byte)value);
      }
      break;

    case BOMB_AMOUNT:
      if (value == null) {
        unsetBombAmount();
      } else {
        setBombAmount((Byte)value);
      }
      break;

    case FOOT:
      if (value == null) {
        unsetFoot();
      } else {
        setFoot((Boolean)value);
      }
      break;

    case CHAIN:
      if (value == null) {
        unsetChain();
      } else {
        setChain((Boolean)value);
      }
      break;

    case DISEASE:
      if (value == null) {
        unsetDisease();
      } else {
        setDisease((Disease)value);
      }
      break;

    case ALIVE:
      if (value == null) {
        unsetAlive();
      } else {
        setAlive((Boolean)value);
      }
      break;

    case X:
      if (value == null) {
        unsetX();
      } else {
        setX((Double)value);
      }
      break;

    case Y:
      if (value == null) {
        unsetY();
      } else {
        setY((Double)value);
      }
      break;

    case PLAYER_ID:
      if (value == null) {
        unsetPlayerId();
      } else {
        setPlayerId((Integer)value);
      }
      break;

    case DISEASE_TICKS:
      if (value == null) {
        unsetDiseaseTicks();
      } else {
        setDiseaseTicks((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case BOMB_SIZE:
      return Byte.valueOf(getBombSize());

    case BOMB_AMOUNT:
      return Byte.valueOf(getBombAmount());

    case FOOT:
      return Boolean.valueOf(isFoot());

    case CHAIN:
      return Boolean.valueOf(isChain());

    case DISEASE:
      return getDisease();

    case ALIVE:
      return Boolean.valueOf(isAlive());

    case X:
      return Double.valueOf(getX());

    case Y:
      return Double.valueOf(getY());

    case PLAYER_ID:
      return Integer.valueOf(getPlayerId());

    case DISEASE_TICKS:
      return Integer.valueOf(getDiseaseTicks());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case BOMB_SIZE:
      return isSetBombSize();
    case BOMB_AMOUNT:
      return isSetBombAmount();
    case FOOT:
      return isSetFoot();
    case CHAIN:
      return isSetChain();
    case DISEASE:
      return isSetDisease();
    case ALIVE:
      return isSetAlive();
    case X:
      return isSetX();
    case Y:
      return isSetY();
    case PLAYER_ID:
      return isSetPlayerId();
    case DISEASE_TICKS:
      return isSetDiseaseTicks();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PlayerState)
      return this.equals((PlayerState)that);
    return false;
  }

  public boolean equals(PlayerState that) {
    if (that == null)
      return false;

    boolean this_present_bombSize = true;
    boolean that_present_bombSize = true;
    if (this_present_bombSize || that_present_bombSize) {
      if (!(this_present_bombSize && that_present_bombSize))
        return false;
      if (this.bombSize != that.bombSize)
        return false;
    }

    boolean this_present_bombAmount = true;
    boolean that_present_bombAmount = true;
    if (this_present_bombAmount || that_present_bombAmount) {
      if (!(this_present_bombAmount && that_present_bombAmount))
        return false;
      if (this.bombAmount != that.bombAmount)
        return false;
    }

    boolean this_present_foot = true;
    boolean that_present_foot = true;
    if (this_present_foot || that_present_foot) {
      if (!(this_present_foot && that_present_foot))
        return false;
      if (this.foot != that.foot)
        return false;
    }

    boolean this_present_chain = true;
    boolean that_present_chain = true;
    if (this_present_chain || that_present_chain) {
      if (!(this_present_chain && that_present_chain))
        return false;
      if (this.chain != that.chain)
        return false;
    }

    boolean this_present_disease = true && this.isSetDisease();
    boolean that_present_disease = true && that.isSetDisease();
    if (this_present_disease || that_present_disease) {
      if (!(this_present_disease && that_present_disease))
        return false;
      if (!this.disease.equals(that.disease))
        return false;
    }

    boolean this_present_alive = true;
    boolean that_present_alive = true;
    if (this_present_alive || that_present_alive) {
      if (!(this_present_alive && that_present_alive))
        return false;
      if (this.alive != that.alive)
        return false;
    }

    boolean this_present_x = true;
    boolean that_present_x = true;
    if (this_present_x || that_present_x) {
      if (!(this_present_x && that_present_x))
        return false;
      if (this.x != that.x)
        return false;
    }

    boolean this_present_y = true;
    boolean that_present_y = true;
    if (this_present_y || that_present_y) {
      if (!(this_present_y && that_present_y))
        return false;
      if (this.y != that.y)
        return false;
    }

    boolean this_present_playerId = true;
    boolean that_present_playerId = true;
    if (this_present_playerId || that_present_playerId) {
      if (!(this_present_playerId && that_present_playerId))
        return false;
      if (this.playerId != that.playerId)
        return false;
    }

    boolean this_present_diseaseTicks = true;
    boolean that_present_diseaseTicks = true;
    if (this_present_diseaseTicks || that_present_diseaseTicks) {
      if (!(this_present_diseaseTicks && that_present_diseaseTicks))
        return false;
      if (this.diseaseTicks != that.diseaseTicks)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(PlayerState other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    PlayerState typedOther = (PlayerState)other;

    lastComparison = Boolean.valueOf(isSetBombSize()).compareTo(typedOther.isSetBombSize());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBombSize()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bombSize, typedOther.bombSize);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBombAmount()).compareTo(typedOther.isSetBombAmount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBombAmount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bombAmount, typedOther.bombAmount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFoot()).compareTo(typedOther.isSetFoot());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFoot()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.foot, typedOther.foot);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetChain()).compareTo(typedOther.isSetChain());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetChain()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.chain, typedOther.chain);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDisease()).compareTo(typedOther.isSetDisease());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDisease()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.disease, typedOther.disease);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAlive()).compareTo(typedOther.isSetAlive());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAlive()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.alive, typedOther.alive);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetX()).compareTo(typedOther.isSetX());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetX()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.x, typedOther.x);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetY()).compareTo(typedOther.isSetY());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetY()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.y, typedOther.y);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPlayerId()).compareTo(typedOther.isSetPlayerId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPlayerId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.playerId, typedOther.playerId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDiseaseTicks()).compareTo(typedOther.isSetDiseaseTicks());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDiseaseTicks()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.diseaseTicks, typedOther.diseaseTicks);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("PlayerState(");
    boolean first = true;

    sb.append("bombSize:");
    sb.append(this.bombSize);
    first = false;
    if (!first) sb.append(", ");
    sb.append("bombAmount:");
    sb.append(this.bombAmount);
    first = false;
    if (!first) sb.append(", ");
    sb.append("foot:");
    sb.append(this.foot);
    first = false;
    if (!first) sb.append(", ");
    sb.append("chain:");
    sb.append(this.chain);
    first = false;
    if (!first) sb.append(", ");
    sb.append("disease:");
    if (this.disease == null) {
      sb.append("null");
    } else {
      sb.append(this.disease);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("alive:");
    sb.append(this.alive);
    first = false;
    if (!first) sb.append(", ");
    sb.append("x:");
    sb.append(this.x);
    first = false;
    if (!first) sb.append(", ");
    sb.append("y:");
    sb.append(this.y);
    first = false;
    if (!first) sb.append(", ");
    sb.append("playerId:");
    sb.append(this.playerId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("diseaseTicks:");
    sb.append(this.diseaseTicks);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bit_vector = new BitSet(1);
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PlayerStateStandardSchemeFactory implements SchemeFactory {
    public PlayerStateStandardScheme getScheme() {
      return new PlayerStateStandardScheme();
    }
  }

  private static class PlayerStateStandardScheme extends StandardScheme<PlayerState> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PlayerState struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BOMB_SIZE
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.bombSize = iprot.readByte();
              struct.setBombSizeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // BOMB_AMOUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.BYTE) {
              struct.bombAmount = iprot.readByte();
              struct.setBombAmountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FOOT
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.foot = iprot.readBool();
              struct.setFootIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CHAIN
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.chain = iprot.readBool();
              struct.setChainIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DISEASE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.disease = Disease.findByValue(iprot.readI32());
              struct.setDiseaseIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ALIVE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.alive = iprot.readBool();
              struct.setAliveIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // X
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.x = iprot.readDouble();
              struct.setXIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // Y
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.y = iprot.readDouble();
              struct.setYIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 9: // PLAYER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.playerId = iprot.readI32();
              struct.setPlayerIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 10: // DISEASE_TICKS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.diseaseTicks = iprot.readI32();
              struct.setDiseaseTicksIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, PlayerState struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(BOMB_SIZE_FIELD_DESC);
      oprot.writeByte(struct.bombSize);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(BOMB_AMOUNT_FIELD_DESC);
      oprot.writeByte(struct.bombAmount);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(FOOT_FIELD_DESC);
      oprot.writeBool(struct.foot);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CHAIN_FIELD_DESC);
      oprot.writeBool(struct.chain);
      oprot.writeFieldEnd();
      if (struct.disease != null) {
        oprot.writeFieldBegin(DISEASE_FIELD_DESC);
        oprot.writeI32(struct.disease.getValue());
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(ALIVE_FIELD_DESC);
      oprot.writeBool(struct.alive);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(X_FIELD_DESC);
      oprot.writeDouble(struct.x);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(Y_FIELD_DESC);
      oprot.writeDouble(struct.y);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(PLAYER_ID_FIELD_DESC);
      oprot.writeI32(struct.playerId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DISEASE_TICKS_FIELD_DESC);
      oprot.writeI32(struct.diseaseTicks);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PlayerStateTupleSchemeFactory implements SchemeFactory {
    public PlayerStateTupleScheme getScheme() {
      return new PlayerStateTupleScheme();
    }
  }

  private static class PlayerStateTupleScheme extends TupleScheme<PlayerState> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PlayerState struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetBombSize()) {
        optionals.set(0);
      }
      if (struct.isSetBombAmount()) {
        optionals.set(1);
      }
      if (struct.isSetFoot()) {
        optionals.set(2);
      }
      if (struct.isSetChain()) {
        optionals.set(3);
      }
      if (struct.isSetDisease()) {
        optionals.set(4);
      }
      if (struct.isSetAlive()) {
        optionals.set(5);
      }
      if (struct.isSetX()) {
        optionals.set(6);
      }
      if (struct.isSetY()) {
        optionals.set(7);
      }
      if (struct.isSetPlayerId()) {
        optionals.set(8);
      }
      if (struct.isSetDiseaseTicks()) {
        optionals.set(9);
      }
      oprot.writeBitSet(optionals, 10);
      if (struct.isSetBombSize()) {
        oprot.writeByte(struct.bombSize);
      }
      if (struct.isSetBombAmount()) {
        oprot.writeByte(struct.bombAmount);
      }
      if (struct.isSetFoot()) {
        oprot.writeBool(struct.foot);
      }
      if (struct.isSetChain()) {
        oprot.writeBool(struct.chain);
      }
      if (struct.isSetDisease()) {
        oprot.writeI32(struct.disease.getValue());
      }
      if (struct.isSetAlive()) {
        oprot.writeBool(struct.alive);
      }
      if (struct.isSetX()) {
        oprot.writeDouble(struct.x);
      }
      if (struct.isSetY()) {
        oprot.writeDouble(struct.y);
      }
      if (struct.isSetPlayerId()) {
        oprot.writeI32(struct.playerId);
      }
      if (struct.isSetDiseaseTicks()) {
        oprot.writeI32(struct.diseaseTicks);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PlayerState struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(10);
      if (incoming.get(0)) {
        struct.bombSize = iprot.readByte();
        struct.setBombSizeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.bombAmount = iprot.readByte();
        struct.setBombAmountIsSet(true);
      }
      if (incoming.get(2)) {
        struct.foot = iprot.readBool();
        struct.setFootIsSet(true);
      }
      if (incoming.get(3)) {
        struct.chain = iprot.readBool();
        struct.setChainIsSet(true);
      }
      if (incoming.get(4)) {
        struct.disease = Disease.findByValue(iprot.readI32());
        struct.setDiseaseIsSet(true);
      }
      if (incoming.get(5)) {
        struct.alive = iprot.readBool();
        struct.setAliveIsSet(true);
      }
      if (incoming.get(6)) {
        struct.x = iprot.readDouble();
        struct.setXIsSet(true);
      }
      if (incoming.get(7)) {
        struct.y = iprot.readDouble();
        struct.setYIsSet(true);
      }
      if (incoming.get(8)) {
        struct.playerId = iprot.readI32();
        struct.setPlayerIdIsSet(true);
      }
      if (incoming.get(9)) {
        struct.diseaseTicks = iprot.readI32();
        struct.setDiseaseTicksIsSet(true);
      }
    }
  }

}

