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

public class MoveActionResult implements org.apache.thrift.TBase<MoveActionResult, MoveActionResult._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("MoveActionResult");

  private static final org.apache.thrift.protocol.TField MY_STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("myState", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField MAP_STATE_FIELD_DESC = new org.apache.thrift.protocol.TField("mapState", org.apache.thrift.protocol.TType.STRUCT, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new MoveActionResultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new MoveActionResultTupleSchemeFactory());
  }

  public PlayerState myState; // required
  public MapState mapState; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    MY_STATE((short)1, "myState"),
    MAP_STATE((short)2, "mapState");

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
        case 1: // MY_STATE
          return MY_STATE;
        case 2: // MAP_STATE
          return MAP_STATE;
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.MY_STATE, new org.apache.thrift.meta_data.FieldMetaData("myState", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PlayerState.class)));
    tmpMap.put(_Fields.MAP_STATE, new org.apache.thrift.meta_data.FieldMetaData("mapState", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, MapState.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(MoveActionResult.class, metaDataMap);
  }

  public MoveActionResult() {
  }

  public MoveActionResult(
    PlayerState myState,
    MapState mapState)
  {
    this();
    this.myState = myState;
    this.mapState = mapState;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public MoveActionResult(MoveActionResult other) {
    if (other.isSetMyState()) {
      this.myState = new PlayerState(other.myState);
    }
    if (other.isSetMapState()) {
      this.mapState = new MapState(other.mapState);
    }
  }

  public MoveActionResult deepCopy() {
    return new MoveActionResult(this);
  }

  @Override
  public void clear() {
    this.myState = null;
    this.mapState = null;
  }

  public PlayerState getMyState() {
    return this.myState;
  }

  public MoveActionResult setMyState(PlayerState myState) {
    this.myState = myState;
    return this;
  }

  public void unsetMyState() {
    this.myState = null;
  }

  /** Returns true if field myState is set (has been assigned a value) and false otherwise */
  public boolean isSetMyState() {
    return this.myState != null;
  }

  public void setMyStateIsSet(boolean value) {
    if (!value) {
      this.myState = null;
    }
  }

  public MapState getMapState() {
    return this.mapState;
  }

  public MoveActionResult setMapState(MapState mapState) {
    this.mapState = mapState;
    return this;
  }

  public void unsetMapState() {
    this.mapState = null;
  }

  /** Returns true if field mapState is set (has been assigned a value) and false otherwise */
  public boolean isSetMapState() {
    return this.mapState != null;
  }

  public void setMapStateIsSet(boolean value) {
    if (!value) {
      this.mapState = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case MY_STATE:
      if (value == null) {
        unsetMyState();
      } else {
        setMyState((PlayerState)value);
      }
      break;

    case MAP_STATE:
      if (value == null) {
        unsetMapState();
      } else {
        setMapState((MapState)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case MY_STATE:
      return getMyState();

    case MAP_STATE:
      return getMapState();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case MY_STATE:
      return isSetMyState();
    case MAP_STATE:
      return isSetMapState();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof MoveActionResult)
      return this.equals((MoveActionResult)that);
    return false;
  }

  public boolean equals(MoveActionResult that) {
    if (that == null)
      return false;

    boolean this_present_myState = true && this.isSetMyState();
    boolean that_present_myState = true && that.isSetMyState();
    if (this_present_myState || that_present_myState) {
      if (!(this_present_myState && that_present_myState))
        return false;
      if (!this.myState.equals(that.myState))
        return false;
    }

    boolean this_present_mapState = true && this.isSetMapState();
    boolean that_present_mapState = true && that.isSetMapState();
    if (this_present_mapState || that_present_mapState) {
      if (!(this_present_mapState && that_present_mapState))
        return false;
      if (!this.mapState.equals(that.mapState))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(MoveActionResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    MoveActionResult typedOther = (MoveActionResult)other;

    lastComparison = Boolean.valueOf(isSetMyState()).compareTo(typedOther.isSetMyState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMyState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.myState, typedOther.myState);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMapState()).compareTo(typedOther.isSetMapState());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMapState()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.mapState, typedOther.mapState);
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
    StringBuilder sb = new StringBuilder("MoveActionResult(");
    boolean first = true;

    sb.append("myState:");
    if (this.myState == null) {
      sb.append("null");
    } else {
      sb.append(this.myState);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("mapState:");
    if (this.mapState == null) {
      sb.append("null");
    } else {
      sb.append(this.mapState);
    }
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class MoveActionResultStandardSchemeFactory implements SchemeFactory {
    public MoveActionResultStandardScheme getScheme() {
      return new MoveActionResultStandardScheme();
    }
  }

  private static class MoveActionResultStandardScheme extends StandardScheme<MoveActionResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, MoveActionResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // MY_STATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.myState = new PlayerState();
              struct.myState.read(iprot);
              struct.setMyStateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // MAP_STATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.mapState = new MapState();
              struct.mapState.read(iprot);
              struct.setMapStateIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, MoveActionResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.myState != null) {
        oprot.writeFieldBegin(MY_STATE_FIELD_DESC);
        struct.myState.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.mapState != null) {
        oprot.writeFieldBegin(MAP_STATE_FIELD_DESC);
        struct.mapState.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class MoveActionResultTupleSchemeFactory implements SchemeFactory {
    public MoveActionResultTupleScheme getScheme() {
      return new MoveActionResultTupleScheme();
    }
  }

  private static class MoveActionResultTupleScheme extends TupleScheme<MoveActionResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, MoveActionResult struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetMyState()) {
        optionals.set(0);
      }
      if (struct.isSetMapState()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetMyState()) {
        struct.myState.write(oprot);
      }
      if (struct.isSetMapState()) {
        struct.mapState.write(oprot);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, MoveActionResult struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.myState = new PlayerState();
        struct.myState.read(iprot);
        struct.setMyStateIsSet(true);
      }
      if (incoming.get(1)) {
        struct.mapState = new MapState();
        struct.mapState.read(iprot);
        struct.setMapStateIsSet(true);
      }
    }
  }

}
