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

public class Constants {

  /**
   * 25 ticks per second at normal rate
   */
  public static final int TICKS_PER_SECOND = 50;

  /**
   * movement takes 5 ticks / tile
   */
  public static final int TICKS_PER_TILE = 10;

  /**
   * normal bomb explodes in 150 ticks (3s)
   * fast bomb in half that time
   * slow bomb takes double that
   */
  public static final int TICKS_BOMB = 150;

}