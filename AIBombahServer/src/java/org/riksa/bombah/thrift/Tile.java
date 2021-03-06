/**
 * Autogenerated by Thrift Compiler (0.8.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.riksa.bombah.thrift;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum Tile implements org.apache.thrift.TEnum {
  NONE(0),
  BUFF_BOMB(1),
  BUFF_FLAME(2),
  BUFF_CHAIN(3),
  BUFF_FOOT(4),
  DEBUFF(5),
  FIRE(6),
  INDESTRUCTIBLE(7),
  DESTRUCTIBLE(8),
  BOMB(9);

  private final int value;

  private Tile(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static Tile findByValue(int value) { 
    switch (value) {
      case 0:
        return NONE;
      case 1:
        return BUFF_BOMB;
      case 2:
        return BUFF_FLAME;
      case 3:
        return BUFF_CHAIN;
      case 4:
        return BUFF_FOOT;
      case 5:
        return DEBUFF;
      case 6:
        return FIRE;
      case 7:
        return INDESTRUCTIBLE;
      case 8:
        return DESTRUCTIBLE;
      case 9:
        return BOMB;
      default:
        return null;
    }
  }
}
